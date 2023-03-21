package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.Altitude.AltitudeProfile;
import ca.mcmaster.cas.se2aa4.island.AquiferGen.AddAquifers;
import ca.mcmaster.cas.se2aa4.island.BeachGen.AddBeaches;
import ca.mcmaster.cas.se2aa4.island.Biomes.BaseBiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Biomes.BiomeProfile;
import ca.mcmaster.cas.se2aa4.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.EdgeTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.RGBExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Humidity.TileHumidifier;
import ca.mcmaster.cas.se2aa4.island.LakeGen.AddLakes;
import ca.mcmaster.cas.se2aa4.island.RiverGen.AddRivers;
import ca.mcmaster.cas.se2aa4.island.Shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class IslandGenerator {
    private static final Extractor rgbEx = new RGBExtractor();
    private static final Extractor tileTagsEx = new TileTagExtractor();
    private static final Extractor altEx = new AltitudeExtractor();
    private static final Extractor edgeEx = new EdgeTagExtractor();

    public static Structs.Mesh Generate(Configuration config){
        Structs.Mesh mesh = config.inputMesh;
        Shape islandShape = config.shapeObj;
        AltitudeProfile altProfile = config.altProfile;
        BiomeProfile biomeProfile = config.biomeProfile;
        int numLakes = config.num_lakes;
        int numAquifers = config.num_aquifers;
        int numRivers = config.num_rivers;
        islandShape.create();
        List<Structs.Polygon> pList = mesh.getPolygonsList();
        List<Structs.Vertex> vList = mesh.getVerticesList();
        List<Structs.Polygon> landTiles = new ArrayList<>();
        List<Structs.Polygon> newList = new ArrayList<>();


        for (Structs.Polygon p: pList) {
            int indexc = p.getCentroidIdx();
            Structs.Vertex v = vList.get(indexc);
            if(!islandShape.contains(v.getX(), v.getY())){
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("43,101,236").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("ocean").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                newList.add(pColoredModify);
            }  else {
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("144,238,144").build();
                Structs.Property tileTag = Structs.Property.newBuilder().setKey("tile_tag").setValue("land").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(tileTag).build();
                newList.add(pColoredModify);
                landTiles.add(pColoredModify);
            }
        }
        List<Object> altLists = new ArrayList<>();
        altLists = altProfile.addAltitudeValues(newList,mesh.getSegmentsList(),vList);

        newList = (List<Structs.Polygon>) altLists.get(0);
        newList = AddBeaches.addBeaches(landTiles, newList);
        landTiles = updateLandTiles(newList);
        newList = AddLakes.addLakes(landTiles, numLakes, newList);
        landTiles = updateLandTiles(newList);
        newList = AddAquifers.addAquifers(landTiles, numAquifers, newList);
        vList = (List<Structs.Vertex>) altLists.get(2);
        List<Structs.Segment> sList = (List<Structs.Segment>) altLists.get(1);
        sList = AddRivers.addRivers(newList, sList, vList, numRivers);
        //for(Structs.Segment s : sList) System.out.println(edgeEx.extractValues(s.getPropertiesList()));
        newList = TileHumidifier.setHumidities(newList, sList);
        newList = biomeProfile.addBiomes(newList);
        List<Object> lakeList = AddLakes.fixLakeAltitudes(numLakes, newList, sList, vList);
        newList = (List<Polygon>) lakeList.get(0);
        vList = (List<Vertex>) lakeList.get(1);
        return Structs.Mesh.newBuilder().addAllPolygons(newList).addAllSegments(sList).addAllVertices(vList).build();
    }

    private static List<Structs.Polygon> updateLandTiles(List<Structs.Polygon> tiles){
        List<Structs.Polygon> landTiles = new ArrayList<>();
        for(Structs.Polygon p : tiles){
            if(tileTagsEx.extractValues(p.getPropertiesList()).equals("land")) landTiles.add(p);
        }
        return landTiles;
    }
}

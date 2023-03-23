package ca.mcmaster.cas.se2aa4.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.Altitude.AltitudeProfile;
import ca.mcmaster.cas.se2aa4.island.AquiferGen.AddAquifers;
import ca.mcmaster.cas.se2aa4.island.BeachGen.AddBeaches;
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
    
    private static final Extractor tileTagsEx = new TileTagExtractor();
    static Structs.Mesh mesh;
    static Shape islandShape;
    static AltitudeProfile altProfile;
    static BiomeProfile biomeProfile;
    static int numLakes;
    static int numAquifers;
    static int numRivers;
    public static Structs.Mesh Generate(Configuration config){
        setConfigValues(config);
        islandShape.create();
        List<Structs.Polygon> pList = mesh.getPolygonsList();
        List<Structs.Vertex> vList = mesh.getVerticesList();

        List<List<Structs.Polygon>> seperatedTiles = findLandTiles.seperateTiles(islandShape, pList, vList);
        List<Structs.Polygon> pModList = seperatedTiles.get(0);
        List<Structs.Polygon> landTiles;

        List<Object> altLists;
        altLists = altProfile.addAltitudeValues(pModList,mesh.getSegmentsList(),vList);
        pModList = (List<Structs.Polygon>) altLists.get(0);
        vList = (List<Structs.Vertex>) altLists.get(2);

        landTiles = updateLandTiles(pModList);
        pModList = AddLakes.addLakes(landTiles, numLakes, pModList);
        landTiles = updateLandTiles(pModList);
        pModList = AddAquifers.addAquifers(landTiles, numAquifers, pModList);

        List<Structs.Segment> sList = (List<Structs.Segment>) altLists.get(1);
        sList = AddRivers.addRivers(pModList, sList, vList, numRivers);
        pModList = TileHumidifier.setHumidities(pModList, sList);
        pModList = biomeProfile.addBiomes(pModList);

        List<Object> lakeList = AddLakes.fixLakeAltitudes(numLakes, pModList, sList, vList);
        pModList = (List<Polygon>) lakeList.get(0);
        vList = (List<Vertex>) lakeList.get(1);
        return Structs.Mesh.newBuilder().addAllPolygons(pModList).addAllSegments(sList).addAllVertices(vList).build();
    }
    private static void setConfigValues(Configuration config){
        mesh = config.inputMesh;
        islandShape = config.shapeObj;
        altProfile = config.altProfile;
        biomeProfile = config.biomeProfile;
        numLakes = config.num_lakes;
        numAquifers = config.num_aquifers;
        numRivers = config.num_rivers;
    }
    private static List<Structs.Polygon> updateLandTiles(List<Structs.Polygon> tiles){
        List<Structs.Polygon> landTiles = new ArrayList<>();
        for(Structs.Polygon p : tiles){
            if(tileTagsEx.extractValues(p.getPropertiesList()).equals("land")) landTiles.add(p);
        }
        return landTiles;
    }
}

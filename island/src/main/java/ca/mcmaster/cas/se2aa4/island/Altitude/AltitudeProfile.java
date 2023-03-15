package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;

import java.util.List;
import java.util.Random;
import java.util.Set;

public interface AltitudeProfile {
    Extractor altEx = new AltitudeExtractor();
    Extractor tagEx = new TileTagExtractor();
    Random rng = new Random();
    public List<Object> addAltitudeValues(List<Structs.Polygon> plist, List<Structs.Segment> slist, List<Structs.Vertex> vlist);
    default public List<Structs.Vertex> smoothenVertices(List<Structs.Polygon> plist, List<Structs.Vertex> vlist, VertexPolygonConnections vpc){
         // TODO Auto-generated method stub
         for(Structs.Vertex v : vlist){
            Set<Integer> connections = vpc.getConnections(v, vlist);
            if(connections == null) continue;
            int averageAlt = 0;
            for(Integer i : connections) {
                String alt = altEx.extractValues(plist.get(i).getPropertiesList());
                try {
                    averageAlt += Integer.parseInt(alt) / connections.size();
                } catch(Exception e) {
                    averageAlt = 0;
                    break;
                }
            }
            Structs.Property altitude = Structs.Property.newBuilder().setKey("altitude").setValue(averageAlt + "").build();
            Structs.Vertex smoothV = Structs.Vertex.newBuilder(v).addProperties(altitude).build();
            vlist.set(vlist.indexOf(v), smoothV);
        }
        return vlist;
    }
}

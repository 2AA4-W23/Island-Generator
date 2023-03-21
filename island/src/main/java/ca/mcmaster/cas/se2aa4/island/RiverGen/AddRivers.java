package ca.mcmaster.cas.se2aa4.island.RiverGen;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexGraph;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;

public class AddRivers {

    private static Extractor tileTagEx = new TileTagExtractor(); 
    private static Extractor altEx = new AltitudeExtractor();
    private static Random rng = new Random();

    public static List<Structs.Segment> addRivers(List<Structs.Polygon> tiles, List<Structs.Segment> segments, List<Structs.Vertex> vertices, int numRivers){
        VertexGraph vvGraph = new VertexGraph(segments, vertices);
        VertexPolygonConnections vpc = new VertexPolygonConnections(tiles, segments, vertices);
        Structs.Vertex initialPoint;
       
       List<Structs.Segment> newSegments = new ArrayList<>();
       newSegments.addAll(segments);
        for(int i = 0; i < numRivers; i++) {
            int riverSize = 0;
            List<Structs.Segment> river = new ArrayList<>();
            do initialPoint = vertices.get(rng.nextInt(vertices.size()));
            while (isWaterVertex(initialPoint, vertices, vpc, tiles));

            Queue<Structs.Vertex> queue = new LinkedList<>();
            Set<Structs.Vertex> visitedVertices = new HashSet<>();
            Map<Structs.Vertex, Structs.Vertex> parent = new HashMap<>();

            queue.add(initialPoint);
            visitedVertices.add(initialPoint);
            Structs.Vertex riverVertex = null;

            int tries = 0;
            while (!queue.isEmpty() && tries < 100) {
                tries++;
                Structs.Vertex currentVertex = queue.remove();
                if (isWaterVertex(currentVertex, vertices, vpc, tiles)) {
                    riverVertex = currentVertex;
                    break;
                }
                Structs.Vertex nextVertex = getNextVertex(currentVertex, vertices, vvGraph);
                if (nextVertex == null) continue;
                visitedVertices.add(nextVertex);
                parent.put(nextVertex, currentVertex);
                queue.add(nextVertex);
            }
            if (riverVertex != null) {
                List<Structs.Segment> frwdSegs = new ArrayList<>();
                List<Structs.Segment> bcwdSegs = new ArrayList<>();
                Structs.Vertex currentVertex = riverVertex;
                while (parent.containsKey(currentVertex)) {
                    Structs.Vertex parentVertex = parent.get(currentVertex);
                    Structs.Segment riverSegment = vvGraph.getSegment(currentVertex, parentVertex, vertices);
                    if (riverSegment != null) {
                        frwdSegs.add(riverSegment);
                    }
                    currentVertex = parentVertex;
                }
                currentVertex = riverVertex;
                while (parent.containsKey(currentVertex)) {
                    Structs.Vertex parentVertex = parent.get(currentVertex);
                    Structs.Segment riverSegment = vvGraph.getSegment(parentVertex, currentVertex, vertices);
                    if (riverSegment != null) {
                        bcwdSegs.add(riverSegment);
                    }
                    currentVertex = parentVertex;
                }
                river.addAll(frwdSegs);
                river.addAll(bcwdSegs);
                riverSize = river.size();
            }
            if (riverSize == 0) {
                i--;
                continue;
            }
            river = addRiverProps(river, newSegments, i + 1);
            System.out.println("ended river " + (i+1) + " with size " + riverSize);
        }
        return newSegments;
    }

    private static List<Structs.Segment> addRiverProps(List<Structs.Segment> river, List<Structs.Segment> allSegments, int riverNumber) {
        for (Structs.Segment s : river) {
            System.out.println("seg added");
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("255,0,255").build();
            Structs.Property segTag = Structs.Property.newBuilder().setKey("seg_tag").setValue("river").build();
            Structs.Property riverNum = Structs.Property.newBuilder().setKey("river_num").setValue(riverNumber + "").build();
            Structs.Segment riverSeg = Structs.Segment.newBuilder(s).addProperties(color).addProperties(segTag).addProperties(riverNum).build();
            allSegments.set(allSegments.indexOf(s), riverSeg);
        }
        return river;
    }    

    private static boolean isWaterVertex(Structs.Vertex v, List<Structs.Vertex> vertices, VertexPolygonConnections G, List<Structs.Polygon> tiles) {
        Set<Integer> connections = G.getConnections(v, vertices);
        if(connections == null) return false;
        for(Integer i : connections) {
            Structs.Polygon connectedTile = tiles.get(i);
            String tag = tileTagEx.extractValues(connectedTile.getPropertiesList());
            //System.out.println(tag);
            if(tag.equals("lake") || tag.equals("ocean")){
                return true;
            }
        }
        return false;
    }

    private static Structs.Vertex getNextVertex(Structs.Vertex v, List<Structs.Vertex> vertices, VertexGraph G) {
        Set<Integer> connections = G.getConnections(v, vertices);
        Structs.Vertex minAltVertex = v;
        int minAlt = getAltitude(v);
        if(connections == null) {
            return v;
        }
        for(Integer i : connections) {
            Structs.Vertex connectedVertex = vertices.get(i);
            int alt = getAltitude(connectedVertex);
            if(alt < minAlt && alt < getAltitude(minAltVertex)) {
                minAltVertex = connectedVertex;
                minAlt = alt;
            }
        }
        return minAltVertex;
    }

    private static int getAltitude(Structs.Vertex v) {
        try {
            return Integer.parseInt(altEx.extractValues(v.getPropertiesList()));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

package ca.mcmaster.cas.se2aa4.island.RiverGen;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.EdgeTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.RiverThicknessExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexGraph;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

public class AddRivers {

    private static Extractor tileTagEx = new TileTagExtractor(); 
    private static Extractor edgeTagEx = new EdgeTagExtractor(); 
    private static Extractor thickEx = new RiverThicknessExtractor(); 
    private static Extractor altEx = new AltitudeExtractor();
    private static Random rng = RandomNumber.getRandomInstance();
    public static List<Structs.Segment> addRivers(List<Structs.Polygon> tiles, List<Structs.Segment> segments, List<Structs.Vertex> vertices, int numRivers){
       List<Structs.Segment> newSegments = new ArrayList<>();
       newSegments.addAll(segments);
       Set<Integer> riverVertices = new HashSet<>();
       VertexGraph vvGraph = new VertexGraph(newSegments, vertices);
       VertexPolygonConnections vpc = new VertexPolygonConnections(tiles, segments, vertices);
       int endorI = 1;
        for(int i = 0; i < numRivers; i++) {
            Structs.Vertex initialPoint;
            int thickness = rng.nextInt(2,5);
            List<Structs.Segment> river = new ArrayList<>();
            do initialPoint = vertices.get(rng.nextInt(vertices.size()));
            while (isWaterVertex(initialPoint, vertices, vpc, tiles) || riverVertices.contains(vertices.indexOf(initialPoint)));
            
            List<Object> traversalResults = traverseVertices(initialPoint, vertices, tiles, newSegments, vpc, vvGraph);
            Map<Structs.Vertex, Structs.Vertex> parent = (Map<Vertex, Vertex>) traversalResults.get(0);
            newSegments = (List<Segment>) traversalResults.get(1);
            Structs.Vertex riverVertex = (Structs.Vertex) traversalResults.get(2); 
            Structs.Vertex endorLakeVertex = (Structs.Vertex) traversalResults.get(3); 
            
            if (riverVertex != null) {
                Structs.Vertex currentVertex = riverVertex;
                List<Structs.Segment> frwdSegs = new ArrayList<>();
                List<Structs.Segment> bcwdSegs = new ArrayList<>();
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
            }
            if (river.size() == 0) {
                i--;
                continue;
            }   
            newSegments = addRiverProps(river, newSegments, i + 1, thickness);
            if(EndorheicLake.addEndorheicLake(tiles, vertices, newSegments, endorLakeVertex, vpc, endorI)) endorI++;
            vvGraph.refreshSegments(newSegments);
        }
        return newSegments;
    }

    private static  List<Object> traverseVertices(
        Structs.Vertex initialPoint, 
        List<Structs.Vertex> vertices, 
        List<Structs.Polygon> tiles, 
        List<Structs.Segment> segments, 
        VertexPolygonConnections vpc, 
        VertexGraph vvGraph) {
        Queue<Structs.Vertex> queue = new LinkedList<>();
            Set<Structs.Vertex> visitedVertices = new HashSet<>();
            Map<Structs.Vertex, Structs.Vertex> parent = new HashMap<>();
            queue.add(initialPoint);
            visitedVertices.add(initialPoint);
            Structs.Vertex riverVertex = null;
            Structs.Vertex endorLakeVertex = null;

            int riverLen = 0;
            int maxLen = rng.nextInt(20,30);
            while (!queue.isEmpty()) {
                Structs.Vertex currentVertex = queue.remove();
                if (isWaterVertex(currentVertex, vertices, vpc, tiles)) {
                    riverVertex = currentVertex;    
                    break;
                }
                Structs.Vertex nextVertex = getNextVertex(currentVertex, vertices, vvGraph);
                if (nextVertex == null || visitedVertices.contains(nextVertex)) continue;
                visitedVertices.add(nextVertex);
                parent.put(nextVertex, currentVertex);
                queue.add(nextVertex);
                riverLen++;
                if(maxLen < riverLen) {
                    Structs.Polygon tile = EndorheicLake.getInitialLakeTile(tiles, vertices, segments, currentVertex, vpc);
                    if(tile == null) continue;
                    endorLakeVertex = currentVertex;
                    riverVertex = currentVertex;  
                    break;
                }
            }
            List<Object> results = new ArrayList<>();
            results.add(parent);
            results.add(segments);
            results.add(riverVertex);
            results.add(endorLakeVertex);
            return results;
    }

    private static List<Structs.Segment> addRiverProps(List<Structs.Segment> river, List<Structs.Segment> allSegments, int riverNumber, int thickness) {
        for (Structs.Segment s : river) {
            Structs.Segment riverSeg = PropertyAdder.addProperty(s, "rgb_color", "10,100,255");
            riverSeg = PropertyAdder.addProperty(riverSeg, "seg_tag", "river");
            riverSeg = PropertyAdder.addProperty(riverSeg, "thickness", getThickness(s, allSegments, thickness) + "");
            int index = allSegments.indexOf(s);
            if(index != -1) allSegments.set(index, riverSeg); //prevents occasional OutOfBoundsErrors
            else System.out.println("Did not add river "  + riverNumber + " due to error");
        }
        //System.out.println("River " + riverNumber + " has size " + river.size());
        return allSegments;
    }  
    
    private static int getThickness(Structs.Segment segment, List<Structs.Segment> allSegments, int thick) {
        String existingThickness = thickEx.extractValues(segment.getPropertiesList());
        String tag = edgeTagEx.extractValues(segment.getPropertiesList());
        if(tag.equals("river")){
            try{
                thick = Math.max(Integer.parseInt(existingThickness), thick) + 1;
            } catch (Exception e )  {}
        }
        //System.out.println(thick);
        return thick;
    }  

    public static boolean isWaterVertex(Structs.Vertex v, List<Structs.Vertex> vertices, VertexPolygonConnections G, List<Structs.Polygon> tiles) {
        Set<Integer> connections = G.getConnections(v, vertices);
        if(connections == null) return false;
        for(Integer i : connections) {
            Structs.Polygon connectedTile = tiles.get(i);
            String tag = tileTagEx.extractValues(connectedTile.getPropertiesList());
            if(tag.equals("lake") || tag.equals("ocean") || tag.equals("endor_lake")){
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

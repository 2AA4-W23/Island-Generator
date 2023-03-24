package ca.mcmaster.cas.se2aa4.island.RiverGen;

import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.EdgeTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.RiverExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.RiverThicknessExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexGraph;
import ca.mcmaster.cas.se2aa4.island.Graph.VertexPolygonConnections;
import ca.mcmaster.cas.se2aa4.island.LakeGen.AddLakes;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.island.RandomNumberGenerator.RandomNumber;

public class AddRivers {

    private static Extractor tileTagEx = new TileTagExtractor(); 
    private static Extractor edgeTagEx = new EdgeTagExtractor(); 
    private static Extractor riverEx = new RiverExtractor(); 
    private static Extractor thickEx = new RiverThicknessExtractor(); 
    private static Extractor altEx = new AltitudeExtractor();
    private static Random rng = RandomNumber.getRandomInstance();
    private static Extractor tileTagsEx = new TileTagExtractor();

    public static List<Structs.Segment> addRivers(List<Structs.Polygon> tiles, List<Structs.Segment> segments, List<Structs.Vertex> vertices, int numRivers){
       List<Structs.Segment> newSegments = new ArrayList<>();
       newSegments.addAll(segments);
       Set<Integer> riverVertices = new HashSet<>();
       VertexGraph vvGraph = new VertexGraph(newSegments, vertices);
       VertexPolygonConnections vpc = new VertexPolygonConnections(tiles, segments, vertices);
        for(int i = 0; i < numRivers; i++) {
            Structs.Vertex initialPoint;
            int thickness = rng.nextInt(2,5);
            List<Structs.Segment> river = new ArrayList<>();
            do initialPoint = vertices.get(rng.nextInt(vertices.size()));
            while (isWaterVertex(initialPoint, vertices, vpc, tiles) || riverVertices.contains(vertices.indexOf(initialPoint)));
            
            Queue<Structs.Vertex> queue = new LinkedList<>();
            Set<Structs.Vertex> visitedVertices = new HashSet<>();
            Map<Structs.Vertex, Structs.Vertex> parent = new HashMap<>();

            queue.add(initialPoint);
            visitedVertices.add(initialPoint);
            riverVertices.add(vertices.indexOf(initialPoint));
            Structs.Vertex riverVertex = null;
            Structs.Vertex endorLakeVertex = null;

            int riverLen = 0;
            while (!queue.isEmpty()) {
                Structs.Vertex currentVertex = queue.remove();
                if (isWaterVertex(currentVertex, vertices, vpc, tiles)) {
                    riverVertex = currentVertex;    
                    break;
                }
                Structs.Vertex nextVertex = getNextVertex(currentVertex, vertices, vvGraph);
                if (nextVertex == null || visitedVertices.contains(nextVertex)) continue;
                visitedVertices.add(nextVertex);
                riverVertices.add(vertices.indexOf(nextVertex));
                parent.put(nextVertex, currentVertex);
                queue.add(nextVertex);
                riverLen++;
                if(rng.nextInt(10,15) < riverLen) {
                    Structs.Polygon tile = getInitialLakeTile(tiles, vertices, newSegments, nextVertex, vpc);
                    if(tile == null) continue;
                    endorLakeVertex = nextVertex;
                    riverVertex = currentVertex;  
                    break;
                }
            }
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
            vvGraph.refreshSegments(newSegments);
            addEndorheicLake(tiles, vertices, newSegments, endorLakeVertex, vpc);
        }
        return newSegments;
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

    private static boolean isWaterVertex(Structs.Vertex v, List<Structs.Vertex> vertices, VertexPolygonConnections G, List<Structs.Polygon> tiles) {
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

    private static void addEndorheicLake(List<Structs.Polygon> tiles, List<Structs.Vertex> vertices, List<Structs.Segment> segments, Structs.Vertex initialPoint, VertexPolygonConnections G){
        if(initialPoint == null) return;
        Structs.Polygon initialTile = getInitialLakeTile(tiles, vertices, segments, initialPoint, G);
        if(initialTile == null) return;
        List<Structs.Polygon> tilesInLake = new ArrayList<>();
        tilesInLake.add(initialTile);
        int lakeSize = rng.nextInt(2,5);
        int checks = 0;
        while(tilesInLake.size() < lakeSize && checks < 100){
            Structs.Polygon nextTile = tilesInLake.get(rng.nextInt(tilesInLake.size()));
            Structs.Polygon tileToAdd = tiles.get(nextTile.getNeighborIdxs(rng.nextInt(nextTile.getNeighborIdxsList().size())));
            checks++;
            if(!checkEligibleLakeTile(tileToAdd, tiles, segments)) continue;
            tilesInLake.add(tileToAdd);
        }
        for(Structs.Polygon p : tilesInLake){
            Structs.Polygon pLake = PropertyAdder.addProperty(p, "rgb_color", "255,0,0");
            pLake = PropertyAdder.addProperty(pLake, "tile_tag", "endor_lake");
            int idx = tiles.indexOf(p);
            if(idx != -1) tiles.set(idx, pLake);
            else System.out.println("Endorheic lake skipped due to error");
        }
        return;
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

    private static boolean checkEligibleLakeTile(Structs.Polygon tile, List<Structs.Polygon> tiles, List<Structs.Segment> segments) {
        if(tile == null) return false;
        for(int idx : tile.getNeighborIdxsList()){
            String tag = tileTagsEx.extractValues(tiles.get(idx).getPropertiesList());
            if(!tag.equals("land")) return false;
            for(Integer i : tile.getSegmentIdxsList()){
                Segment s = segments.get(i);
                String segTag = edgeTagEx.extractValues(s.getPropertiesList());
                if(segTag.equals("river")) return false;
            }
        }
        return true;
    }

    private static Structs.Polygon getInitialLakeTile(List<Structs.Polygon> tiles, List<Structs.Vertex> vertices, List<Structs.Segment> segments, Structs.Vertex initialPoint, VertexPolygonConnections G){
        Structs.Polygon initialTile = null;
        Set<Integer> connections = G.getConnections(initialPoint, vertices);
        if(connections == null || connections.size() == 0) return null;
        for(Integer i : connections) {
            initialTile = tiles.get(i);
            if(!checkEligibleLakeTile(initialTile, tiles, segments)) initialTile = null;
            else break;
        }
        if(initialTile == null) return null;
        if(G.isNonAdjacentWaterTile(initialTile, tiles)) return null;
        return initialTile;
    }
}

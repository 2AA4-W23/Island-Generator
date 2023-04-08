package ca.mcmaster.cas.se2aa4.island.CityGen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph.CentroidNode;
import ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph.MeshGraph;
import ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph.SegmentPath;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Path;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;;

public class RoadAdder{
    public static List<Segment> addRoads(CentroidNode hub, List<CentroidNode> nodes, MeshGraph graph){
        Set<Segment> roads = new HashSet<>();
        for(CentroidNode city : nodes){
            if(city.id == hub.id) continue;
            Path<CentroidNode> road = graph.shortestPath(city, hub);
            roads.addAll(SegmentPath.toSegmentList(road));
        }
        List<Segment> newRoadSegments = new ArrayList<>();
        for(Segment s : roads){
            Segment roadS = PropertyAdder.addProperty(s, "seg_tag", "road");
            roadS = PropertyAdder.addProperty(roadS, "rgb_color", "160,160,160");
            roadS = PropertyAdder.addProperty(roadS, "thickness", "3");
            newRoadSegments.add(roadS);
        }
        return newRoadSegments;
    }

}

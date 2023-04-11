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

    //constructs road segments
    public static List<Segment> addRoads(CentroidNode hub, List<CentroidNode> nodes, MeshGraph graph){
        Set<Segment> roads = new HashSet<>();
        for(CentroidNode city : nodes){
            if(city.id == hub.id) continue; //dont compute path from hub to hub
            Path<CentroidNode> road = graph.shortestPath(city, hub); //find shortest path between city and hub
            roads.addAll(SegmentPath.toSegmentList(road)); //convert to segments and add to list
        }

        List<Segment> newRoadSegments = new ArrayList<>();
        for(Segment s : roads){ //add all properties for visualizer
            Segment roadS = PropertyAdder.addProperty(s, "seg_tag", "road");
            roadS = PropertyAdder.addProperty(roadS, "rgb_color", "160,160,160");
            roadS = PropertyAdder.addProperty(roadS, "thickness", "3");
            newRoadSegments.add(roadS); //add to all segments
        }
        return newRoadSegments;
    }

}

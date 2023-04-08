package ca.mcmaster.cas.se2aa4.island.CityGen;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph.CentroidNode;
import ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph.SegmentPath;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;

public class Road extends SegmentPath {

    public Road(LinkedList<CentroidNode> path){
        super(path);
    }

    public Road(){
        super();
    }

    public List<Segment> getRoadSegments(List<Segment> roadSegments){
        List<Segment> newRoadSegments = new ArrayList<>();
        for(Segment s : roadSegments){
            Segment roadS = PropertyAdder.addProperty(s, "seg_tag", "road");
            roadS = PropertyAdder.addProperty(s, "rgb_color", "128,128,128");
            roadS = PropertyAdder.addProperty(s, "thickness", "4");
            newRoadSegments.add(roadS);
        }
        return newRoadSegments;
    }
}

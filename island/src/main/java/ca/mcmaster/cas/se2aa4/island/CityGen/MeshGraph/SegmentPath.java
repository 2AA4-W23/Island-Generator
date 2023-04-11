package ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Path;

public class SegmentPath extends Path<CentroidNode> {

    //constructs a list of new segments connecting nodes in a path
    public static List<Segment> toSegmentList(Path<CentroidNode> p){
        if(p.size() < 0) return new ArrayList<>();
        List<CentroidNode> path = p.getList();
        List<Segment> segments = new ArrayList<>();
        CentroidNode previousNode = path.get(0);
        for(CentroidNode currentNode : path){
            if(currentNode == previousNode) continue;
            Segment s = Segment.newBuilder().setV1Idx(previousNode.centroidIdx).setV2Idx(currentNode.centroidIdx).build();
            //construct segment between adjacent nodes in a path
            segments.add(s);
            previousNode = currentNode;
        }
        return Collections.unmodifiableList(segments); //ensure path segments are not modified
    }
}

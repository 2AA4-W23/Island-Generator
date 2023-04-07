package ca.mcmaster.cas.se2aa4.island.CityGen.MeshGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Path;

public class SegmentPath extends Path<CentroidNode> {
    
    public List<Segment> toSegmentList(){
        List<Segment> segments = new ArrayList<>();
        CentroidNode previousNode = this.path.getFirst();
        for(CentroidNode currentNode : this.path){
            if(currentNode == previousNode) continue;
            Segment s = Segment.newBuilder().setV1Idx(previousNode.id).setV2Idx(currentNode.id).build();
            segments.add(s);
            previousNode = currentNode;
        }
        return Collections.unmodifiableList(segments);
    }
}

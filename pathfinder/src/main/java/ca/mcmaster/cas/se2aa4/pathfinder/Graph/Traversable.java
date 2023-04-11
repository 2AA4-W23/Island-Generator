package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public interface Traversable<N extends Node> {
    public default Path<N> shortestPath(N start, N end){
        if(end == null || start == null) { //ensure nodes are valid
            return new Path<>();
        }
        Queue<Path<N>> queue = new LinkedList<>(); //queue for BFS 
        Map<N, Integer> distance = new HashMap<>(); //distances 
        Path<N> startPath = new Path<N>().appendNode(start); //initial path containing start node
        distance.put(start, 0); //distance from start to start is 0
        queue.add(startPath); //add initial path to queue
        while(!queue.isEmpty()){
            Path<N> path = queue.remove();
            N current = path.getLast(); //get last node in path for processings neighbors
            if(current == null) return new Path<>(); 
            if(current.id == end.id) return path; //found path from start to end, return
            for(N next : getNeighbors(current)){ //iterate thru all neighbors
                if(distance.containsKey(next)) continue; //dont process already visited nodes
                queue.add(path.appendNode(next)); //add new path to queue
                distance.put(next, distance.get(current) + 1); //update distances
            }
        }
        return new Path<>();
    }

    public boolean isNeighbor(N a, N b);
    public Set<N> getNeighbors(N a);
}

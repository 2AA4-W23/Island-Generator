package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public interface Traversable<N extends Node> {
    public default Path<N> shortestPath(N start, N end){
        Queue<Path<N>> queue = new LinkedList<>();
        Map<N, Integer> distance = new HashMap<>();
        Path<N> startPath = new Path<N>().appendNode(start);
        distance.put(start, 0);
        queue.add(startPath);
        while(!queue.isEmpty()){
            Path<N> path = queue.remove();
            N current = path.getLast();
            if(current == null) return new Path<>();
            if(current.id == end.id) return path;
            for(N next : getNeighbors(current)){
                if(distance.containsKey(next)) continue;
                queue.add(path.appendNode(next));
                distance.put(next, distance.get(current) + 1);
            }
        }
        return new Path<>();
    }

    public boolean isNeighbor(N a, N b);
    public Set<N> getNeighbors(N a);
}

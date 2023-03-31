package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph<N extends Node, E extends Edge<N>> implements Traversable<N> {

    private Map<N, Set<N>> adjacencyList;
    private final int size;
    
    public Graph(List<N> nodes, List<E> edges){
        adjacencyList = new HashMap<>();
        for(N node : nodes) adjacencyList.put(node, new HashSet<>());
        for(E edge : edges){
            N v1 = edge.v1, v2 = edge.v2;
            addEdge(v1, v2);
            if(!edge.isDirected()) addEdge(v2, v1);
        }
        size = nodes.size();
    }

    @Override
    public List<N> shortestPath(N start, N end) {
        Queue<LinkedList<N>> queue = new LinkedList<>();
        Map<N, Integer> distance = new HashMap<>();
        LinkedList<N> startPath = new LinkedList<>();
        startPath.add(start);
        distance.put(start, 0);
        queue.add(startPath);
        while(!queue.isEmpty()){
            LinkedList<N> path = queue.remove();
            N current = path.getLast();
            if(current.id == end.id) return path;
            for(N next : getNeighbors(current)){
                if(distance.containsKey(next)) continue;
                LinkedList<N> nextPath = new LinkedList<>();
                nextPath.addAll(path);
                nextPath.add(next);
                queue.add(nextPath);
                distance.put(next, distance.get(current) + 1);
            }
        }
        return null;
    }

    @Override
    public boolean isNeighbor(N v1, N v2){
        return adjacencyList.get(v1).contains(v2);
    }

    @Override
    public Set<N> getNeighbors(N a) {
        return adjacencyList.get(a);
    }

    private void addEdge(N v1, N v2){
        adjacencyList.get(v1).add(v2);
    }
       
    public int size(){
        return size;
    }
}

package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph<N, E extends Edge<N>> implements Traversable<N> {

    private Map<N, N> adjacencyList;

    public Graph(List<N> nodes, List<E> edges){
        for(E edge : edges){
            adjacencyList.put(edge.getV1(), edge.getV2());
        }
    }

    @Override
    public List<N> shortestPath(N start, N end) {
        List<N> path = new ArrayList<>();
        return path;
    }

    public boolean isConnected(N v1, N v2){
        return adjacencyList.get(v1) == v2;
    }
       
}

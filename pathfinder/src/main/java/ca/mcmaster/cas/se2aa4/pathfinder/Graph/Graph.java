package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<N extends Node, E extends Edge<N>> implements Traversable<N> {

    protected Map<Integer, Set<N>> adjacencyList;
    private final int size;
    protected List<N> nodes;
    protected List<E> edges;

    public Graph(List<N> nodes, List<E> edges){
        this(nodes);
        this.edges = edges;
        updateAdjacencyList(edges);
    }

    protected void updateAdjacencyList(List<E> edges) {
        for(E edge : edges){
            N v1 = edge.v1, v2 = edge.v2;
            addEdge(v1, v2);
        }
    }

    public Graph(List<N> nodes){
        this.nodes = nodes;
        adjacencyList = new HashMap<>();
        size = nodes.size();
        for(N node : nodes) adjacencyList.put(node.id, new HashSet<>());
    }

    public void addUndirectedEdge(int a, int b){
        N n1 = nodes.get(a), n2 = nodes.get(b);
        addEdge(n1, n2);
        addEdge(n2, n1);
    }

    public E getEdge(int a, int b){
        for(E e : edges){
            if(e.v1.id == a && e.v2.id == b) return e;
            if(e.v2.id == a && e.v1.id == b) return e;
        }
        return null;
    }

    public E getEdge(int i){
        return edges.get(i);
    }

    public N getNode(int id){
        for(N n : nodes){
            if(n.id == id) return n;
        }
        return null;
    }

    public Path<N> shortestPath(int a, int b){
        N n1 = getNode(a);
        N n2 = getNode(b);
        return shortestPath(n1, n2);
    }

    @Override
    public boolean isNeighbor(N v1, N v2){
        return adjacencyList.get(v1.id).contains(v2);
    }

    @Override
    public Set<N> getNeighbors(N a) {
        return adjacencyList.containsKey(a.id) ? adjacencyList.get(a.id) : new HashSet<>();
    }

    protected void addEdge(N v1, N v2){
        if(!adjacencyList.containsKey(v1.id)) adjacencyList.put(v1.id, new HashSet<>());
        if(!adjacencyList.containsKey(v2.id)) adjacencyList.put(v2.id, new HashSet<>());
        adjacencyList.get(v1.id).add(v2);
        adjacencyList.get(v2.id).add(v1);
    }
       
    public int size(){
        return size;
    }
}

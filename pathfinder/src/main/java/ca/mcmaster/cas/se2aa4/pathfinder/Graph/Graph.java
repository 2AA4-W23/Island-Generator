package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph implements Traversable {

    private Map<Node, Set<Node>> adjacencyList;
    private final int size;
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph(List<Node> nodes, List<Edge> edges){
        this.nodes = nodes;
        this.edges = edges;
        adjacencyList = new HashMap<>();
        for(Node node : nodes) adjacencyList.put(node, new HashSet<>());
        for(Edge edge : edges){
            Node v1 = edge.v1, v2 = edge.v2;
            addEdge(v1, v2);
            if(!edge.isDirected()) addEdge(v2, v1);
        }
        size = nodes.size();
    }

    public Graph(int n){
        nodes = new ArrayList<>();
        adjacencyList = new HashMap<>();
        size = n;
        for(int i = 0; i < n; i++) nodes.add(new Node(i));
        for(Node node : nodes) adjacencyList.put(node, new HashSet<>());
    }

    public void addDirectedEdge(int a, int b){
        Node n1 = nodes.get(a), n2 = nodes.get(b);
        addEdge(n1, n2);
    }


    public void addUndirectedEdge(int a, int b){
        Node n1 = nodes.get(a), n2 = nodes.get(b);
        addEdge(n1, n2);
        addEdge(n2, n1);
    }

    public Edge getEdge(int a, int b){
        for(Edge e : edges){
            if(e.v1.id == a && e.v2.id == b) return e;
            if(e.v2.id == a && e.v1.id == b) return e;
        }
        return null;
    }

    public Edge getEdge(int i){
        return edges.get(i);
    }

    public Node getNode(int id){
        for(Node n : nodes){
            if(n.id == id) return n;
        }
        return null;
    }

    public Path shortestPath(int a, int b){
        Node n1 = getNode(a);
        Node n2 = getNode(b);
        return shortestPath(n1, n2);
    }

    @Override
    public boolean isNeighbor(Node v1, Node v2){
        return adjacencyList.get(v1).contains(v2);
    }

    @Override
    public Set<Node> getNeighbors(Node a) {
        return adjacencyList.get(a);
    }

    private void addEdge(Node v1, Node v2){
        adjacencyList.get(v1).add(v2);
    }
       
    public int size(){
        return size;
    }
}

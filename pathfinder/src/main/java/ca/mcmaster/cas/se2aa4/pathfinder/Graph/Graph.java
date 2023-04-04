package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph implements Traversable {

    private Map<Node, Set<Node>> adjacencyList;
    private final int size;
    private List<Node> nodes;

    public Graph(List<Node> nodes, List<Edge> edges){
        this.nodes = nodes;
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

    @Override
    public List<Node> shortestPath(Node start, Node end) {
        Queue<LinkedList<Node>> queue = new LinkedList<>();
        Map<Node, Integer> distance = new HashMap<>();
        LinkedList<Node> startPath = new LinkedList<>();
        startPath.add(start);
        distance.put(start, 0);
        queue.add(startPath);
        while(!queue.isEmpty()){
            LinkedList<Node> path = queue.remove();
            Node current = path.getLast();
            if(current.id == end.id) return path;
            for(Node next : getNeighbors(current)){
                if(distance.containsKey(next)) continue;
                LinkedList<Node> nextPath = new LinkedList<>();
                nextPath.addAll(path);
                nextPath.add(next);
                queue.add(nextPath);
                distance.put(next, distance.get(current) + 1);
            }
        }
        return null;
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

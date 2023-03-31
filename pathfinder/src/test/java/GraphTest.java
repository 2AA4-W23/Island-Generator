import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Node;

public class GraphTest {

    private static Graph<Node, Edge<Node>> G;
    private static List<Node> nodes = new ArrayList<>();
    private static List<Edge<Node>> edges = new ArrayList<>();

    @BeforeAll
    public static void init(){
        for(int i = 1; i <= 9; i++){
            nodes.add(new Node(i));
        }
        edges.add(new Edge<Node>(nodes.get(0), nodes.get(3)));
        edges.add(new Edge<Node>(nodes.get(0), nodes.get(4)));
        edges.add(new Edge<Node>(nodes.get(0), nodes.get(6)));
        edges.add(new Edge<Node>(nodes.get(0), nodes.get(8)));
        edges.add(new Edge<Node>(nodes.get(1), nodes.get(4)));
        edges.add(new Edge<Node>(nodes.get(3), nodes.get(4)));
        edges.add(new Edge<Node>(nodes.get(3), nodes.get(5)));
        edges.add(new Edge<Node>(nodes.get(5), nodes.get(2)));
        edges.add(new Edge<Node>(nodes.get(7), nodes.get(8)));
        G = new Graph<>(nodes, edges);
    }

    @Test
    public void neighbors(){
        assertEquals(G.getNeighbors(nodes.get(0)).size(), 4);
        assertEquals(G.getNeighbors(nodes.get(8)).size(), 2);
        assertEquals(G.getNeighbors(nodes.get(2)).size(), 1);
    }

    @Test
    public void shortestPath(){
        List<Node> path1 = G.shortestPath(nodes.get(0) , nodes.get(2));
        for(Node n : path1) System.out.print(n.id + " ");
        System.out.println();
        List<Node> path2 = G.shortestPath(nodes.get(5) , nodes.get(4));
        for(Node n : path2) System.out.print(n.id + " ");
        System.out.println();
        List<Node> path3 = G.shortestPath(nodes.get(2) , nodes.get(7));
        for(Node n : path3) System.out.print(n.id + " ");
        System.out.println();
        assertEquals(path1.size(), 4);
        assertEquals(path2.size(), 3);
        assertEquals(path3.size(), 6);
    }
}

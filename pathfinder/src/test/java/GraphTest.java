import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Node;

public class GraphTest {

    private static Graph G;
    private static List<Node> nodes = new ArrayList<>();
    private static List<Edge> edges = new ArrayList<>();

    @BeforeAll
    public static void init(){
        for(int i = 1; i <= 9; i++){
            nodes.add(new Node(i));
        }
        edges.add(new Edge(nodes.get(0), nodes.get(3)));
        edges.add(new Edge(nodes.get(0), nodes.get(4)));
        edges.add(new Edge(nodes.get(0), nodes.get(6)));
        edges.add(new Edge(nodes.get(0), nodes.get(8)));
        edges.add(new Edge(nodes.get(1), nodes.get(4)));
        edges.add(new Edge(nodes.get(3), nodes.get(4)));
        edges.add(new Edge(nodes.get(3), nodes.get(5)));
        edges.add(new Edge(nodes.get(5), nodes.get(2)));
        edges.add(new Edge(nodes.get(7), nodes.get(8)));
        G = new Graph(nodes, edges);
    }

    @Test
    public void neighbors(){
        assertEquals(G.getNeighbors(nodes.get(0)).size(), 4);
        assertEquals(G.getNeighbors(nodes.get(8)).size(), 2);
        assertEquals(G.getNeighbors(nodes.get(2)).size(), 1);
        assertTrue(G.isNeighbor(nodes.get(0), nodes.get(6)));
        assertTrue(G.isNeighbor(nodes.get(7), nodes.get(8)));
        assertFalse(G.isNeighbor(nodes.get(3), nodes.get(1)));
    }

    @Test
    public void shortestPath(){
        List<Node> path1 = G.shortestPath(nodes.get(0) , nodes.get(2));
        List<Node> path2 = G.shortestPath(nodes.get(5) , nodes.get(4));
        List<Node> path3 = G.shortestPath(nodes.get(2) , nodes.get(7));
        assertEquals(path1.size(), 4);
        assertEquals(path2.size(), 3);
        assertEquals(path3.size(), 6);
        nodes.add(new Node(10));
        assertNull(G.shortestPath(nodes.get(0), nodes.get(9)));
    }
}

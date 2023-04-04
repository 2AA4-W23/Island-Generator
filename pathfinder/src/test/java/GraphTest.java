import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Edge;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Graph;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Node;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Path;

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
        Path path1 = G.shortestPath(nodes.get(0) , nodes.get(2));
        Path path2 = G.shortestPath(nodes.get(5) , nodes.get(4));
        Path path3 = G.shortestPath(nodes.get(2) , nodes.get(7));
        assertEquals(path1.size(), 3);
        assertEquals(path2.size(), 2);
        assertEquals(path3.size(), 5);
        nodes.add(new Node(10));
        assertNull(G.shortestPath(nodes.get(0), nodes.get(9)));
    }

    @Test
    public void getters(){
        assertNotNull(G.getNode(1));
        assertNotNull(G.getNode(6));
        assertNotNull(G.getNode(9));
        assertNull(G.getNode(20));
        Edge e = G.getEdge(1, 5);
        assertEquals(e.v1.id, 1);
        assertEquals(e.v2.id, 5);
        assertNull(G.getEdge(8, 1));
    }
}

package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.List;
import java.util.Set;

public interface Traversable {
    public List<Node> shortestPath(Node start, Node end);
    public boolean isNeighbor(Node a, Node b);
    public Set<Node> getNeighbors(Node a);
}

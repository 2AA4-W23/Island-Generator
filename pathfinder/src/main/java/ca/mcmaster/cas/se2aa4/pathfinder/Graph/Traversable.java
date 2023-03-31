package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.List;
import java.util.Set;

public interface Traversable <T> {
    public List<T> shortestPath(T start, T end);
    public boolean isNeighbor(T a, T b);
    public Set<T> getNeighbors(T a);
}

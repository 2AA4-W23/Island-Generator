package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.List;

public interface Traversable <T> {
    public List<T> shortestPath(T start, T end);
}

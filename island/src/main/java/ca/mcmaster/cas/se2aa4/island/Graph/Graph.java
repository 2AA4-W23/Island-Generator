package ca.mcmaster.cas.se2aa4.island.Graph;

import java.util.HashSet;
import java.util.Set;

public abstract class Graph {

    protected int size;
    protected Set<Integer>[] adjacencyList;

    public boolean isConnected(int i1, int i2) {
        try {
            return adjacencyList[i1].contains(i2);
        } catch (Exception e) {
            return false;
        }
    }
    public Set<Integer> getConnections(int i) {
        try {
            return adjacencyList[i];
        } catch (Exception e) {
            return new HashSet<>();
        }
    }
    public int size() {
        return this.size;
    }
}

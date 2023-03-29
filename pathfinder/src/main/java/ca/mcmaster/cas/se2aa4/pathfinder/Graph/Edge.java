package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

public class Edge<N> {
    private final N v1;
    private final N v2;
    private boolean directed = false;
    public final int weight;

    public Edge(N v1, N v2, int weight, boolean directed){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
        this.directed = directed;
    }

    public Edge(N v1, N v2){
        this(v1, v2, 1, false);
    }

    public boolean isDirected(){
        return directed;
    }

    public N getV1(){
        return v1;
    }

    public N getV2(){
        return v2;
    }
}

package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

public class Edge<N extends Node> extends Component{
    public final N v1;
    public final N v2;
    public final boolean directed;
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

    public Edge(N v1, N v2, boolean directed){
        this(v1, v2, 1, directed);
    }

    public boolean isDirected(){
        return directed;
    }
}

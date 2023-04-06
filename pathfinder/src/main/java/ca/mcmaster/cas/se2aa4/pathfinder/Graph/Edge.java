package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

public class Edge<N extends Node> extends Component{
    public final N v1;
    public final N v2;
    public final boolean directed;

    public Edge(N v1, N v2, boolean directed){
        this.v1 = v1;
        this.v2 = v2;
        this.directed = directed;
    }

    public Edge(N v1, N v2){
        this(v1, v2, false);
    }

    public boolean isDirected(){
        return directed;
    }
}

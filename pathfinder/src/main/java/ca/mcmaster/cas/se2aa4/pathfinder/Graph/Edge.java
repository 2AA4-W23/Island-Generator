package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

public class Edge<N extends Node> extends Component{
    public final N v1;
    public final N v2;

    public Edge(N v1, N v2){ //set nodes
        this.v1 = v1;
        this.v2 = v2;
    }
}

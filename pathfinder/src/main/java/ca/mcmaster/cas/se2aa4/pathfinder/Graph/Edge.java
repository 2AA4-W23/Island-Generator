package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

public class Edge extends Component{
    public final Node v1;
    public final Node v2;
    public final boolean directed;
    public final int weight;

    public Edge(Node v1, Node v2, int weight, boolean directed){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
        this.directed = directed;
    }

    public Edge(Node v1, Node v2){
        this(v1, v2, 1, false);
    }

    public Edge(Node v1, Node v2, boolean directed){
        this(v1, v2, 1, directed);
    }

    public boolean isDirected(){
        return directed;
    }
}

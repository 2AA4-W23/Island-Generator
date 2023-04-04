package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

public class Edge extends Component{
    public final Node v1;
    public final Node v2;
    public final boolean directed;

    public Edge(Node v1, Node v2, boolean directed){
        this.v1 = v1;
        this.v2 = v2;
        this.directed = directed;
    }

    public Edge(Node v1, Node v2){
        this(v1, v2, false);
    }
    
    public boolean isDirected(){
        return directed;
    }
}

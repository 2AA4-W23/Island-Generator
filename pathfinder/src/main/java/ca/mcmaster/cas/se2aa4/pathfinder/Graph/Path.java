package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Path<N extends Node> {
    protected final LinkedList<N> path;
    private final int size;

    //initialize empty path
    public Path(){
        this(new LinkedList<>());
    }

    public Path(LinkedList<N> path){
        this.path = path;
        this.size = path.size() - 1; 
        //number of edges in path is 1 less than number of nodes ()
    }

    public List<N> getList(){
        return Collections.unmodifiableList(path); //ensure list is not modified
    }


    //get last item in path
    public N getLast(){
        return path.get(size);
    }

    //return new path with additional node
    public Path<N> appendNode(N node){
        LinkedList<N> newPath = new LinkedList<>();
        newPath.addAll(path);
        newPath.add(node);
        return new Path<N>(newPath); 
    }

    public int size(){
        return size;
    }
}

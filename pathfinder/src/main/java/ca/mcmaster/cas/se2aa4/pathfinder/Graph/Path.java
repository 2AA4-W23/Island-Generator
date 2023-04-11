package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Path<N extends Node> {
    protected final LinkedList<N> path;
    private final int size;

    public Path(){
        this(new LinkedList<>());
    }

    public Path(LinkedList<N> path){
        this.path = path;
        this.size = path.size() - 1;
    }

    public List<N> getList(){
        return Collections.unmodifiableList(path);
    }

    public N getLast(){
        return path.get(size);
    }

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

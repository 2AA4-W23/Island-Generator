package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private final LinkedList<Node> path;
    private final int size;

    public Path(){
        path = new LinkedList<>();
        size = -1;
    }

    private Path(LinkedList<Node> path){
        this.path = path;
        this.size = path.size() - 1;
    }

    public List<Node> getList(){
        return Collections.unmodifiableList(path);
    }

    public Node getLast(){
        return path.get(size);
    }

    public Path appendNode(Node node){
        LinkedList<Node> newPath = new LinkedList<>();
        newPath.addAll(path);
        newPath.add(node);
        return new Path(newPath);
    }

    public int size(){
        return size;
    }

    @Override
    public String toString(){
        String pathString = "";
        for(Node n : path) pathString += n.id + " ";
        return pathString;
    }
}

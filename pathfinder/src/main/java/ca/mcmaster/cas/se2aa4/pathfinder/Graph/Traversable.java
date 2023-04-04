package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public interface Traversable {
    public default Path shortestPath(Node start, Node end){
        Queue<Path> queue = new LinkedList<>();
        Map<Node, Integer> distance = new HashMap<>();
        Path startPath = new Path().appendNode(start);
        distance.put(start, 0);
        queue.add(startPath);
        while(!queue.isEmpty()){
            Path path = queue.remove();
            Node current = path.getLast();
            if(current.id == end.id) return path;
            for(Node next : getNeighbors(current)){
                if(distance.containsKey(next)) continue;
                queue.add(path.appendNode(next));
                distance.put(next, distance.get(current) + 1);
            }
        }
        return null;
    }
    public boolean isNeighbor(Node a, Node b);
    public Set<Node> getNeighbors(Node a);
}

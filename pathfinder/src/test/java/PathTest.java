import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Node;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Path;

public class PathTest {

    static Path<Node> emptyPath;
    static Path<Node> nonEmptyPath;

    @BeforeAll
    public static void init(){
        emptyPath = new Path<>();
        nonEmptyPath = emptyPath.appendNode(new Node(0)).appendNode(new Node(1)).appendNode(new Node(2)); 
    }

    @Test
    public void sizeTest(){
        assertEquals(emptyPath.size(), -1);
        assertEquals(nonEmptyPath.size(), 2);
        nonEmptyPath.appendNode(new Node(3)).appendNode(new Node(4));
        assertEquals(nonEmptyPath.size(), 2);
        Path<Node> newPath = nonEmptyPath.appendNode(new Node(10));
        assertEquals(newPath.size(), 3);
    }

    @Test
    public void appendTest(){
        Path<Node> newPath1 = nonEmptyPath.appendNode(new Node(10));
        Path<Node> newPath2 = emptyPath.appendNode(new Node(15));
        assertEquals(newPath1.getLast().id, 10);
        assertEquals(newPath2.getLast().id, 15);
        assertEquals(newPath1.size(), nonEmptyPath.size() + 1);
        assertEquals(newPath2.size(), emptyPath.size() + 1);
    }

    @Test
    public void listTest(){
        List<Node> list = nonEmptyPath.getList();
        assertEquals(list.size(), nonEmptyPath.size() + 1);
        assertEquals(list.get(0).id, 0);
        assertEquals(list.get(1).id, 1);
        assertEquals(list.get(1).id, 1);
        try{
            list.add(new Node(20));
            assertEquals(1, 2);
        } catch(UnsupportedOperationException e){}
    }
}

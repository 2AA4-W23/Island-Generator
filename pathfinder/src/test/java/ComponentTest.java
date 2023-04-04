import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Attribute;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Component;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph.Node;

public class ComponentTest {
    private static Component c;

    @BeforeAll
    public static void init(){
        c = new Node(0);
    }

    @Test
    public void attributes(){
        c.addAttribute(new Attribute("test", "123"));
        c.addAttribute(new Attribute("name", "node"));
        c.addAttribute(new Attribute("color", "red"));
        assertEquals(c.getAttribute("test").value, c.getValue("test"));
        assertEquals(c.getAttribute("name").value, c.getValue("name"));
        assertNotNull(c.getAttribute("color"));
        assertNull(c.getAttribute("not_an_attribute"));
    }
}

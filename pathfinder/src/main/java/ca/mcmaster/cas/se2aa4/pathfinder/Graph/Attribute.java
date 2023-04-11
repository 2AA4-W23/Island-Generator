package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

public class Attribute {
    public final String key;
    public final String value;
    //unmodifiable attributes

    public Attribute(String key, String value){ //initialize values
        this.key = key;
        this.value = value;
    }
}

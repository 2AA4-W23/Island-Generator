package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.List;

public class Attribute {
    public final String key;
    public final String value;

    public Attribute(String key, String value){
        this.key = key;
        this.value = value;
    }

    public static String getAttributeValue(List<Attribute> attributes, String key){
        for(Attribute a : attributes){
            if(a.key.equals(key)) return a.value;
        }
        return null;
    }
}

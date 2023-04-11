package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    private List<Attribute> attributes = new ArrayList<>();

    public void addAttribute(Attribute a){ //adds a new attribute to list
        Attribute existing = getAttribute(a.key); //get existing attribute if it exists
        if(existing == null) attributes.add(a); //add attribute 
        else {
            attributes.set(attributes.indexOf(existing), a); //overwrite existing attribute
        }
    }

    public Attribute getAttribute(String key){
        for(Attribute a : attributes){
            if(a.key.equals(key)) return a; //return attribute if exists
        }
        return null; //return null if doesnt exist
    }

    public String getValue(String key){
        for(Attribute a : attributes){
            if(a.key.equals(key)) return a.value; //find and return value associated with key
        }
        return "null"; //return "null" (not null to avoid NullPointerExceptions) 
    }
}

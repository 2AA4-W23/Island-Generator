package ca.mcmaster.cas.se2aa4.pathfinder.Graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    private List<Attribute> attributes = new ArrayList<>();

    protected void addAttribute(Attribute a){
        Attribute existing = getAttribute(a.key);
        if(existing == null) attributes.add(a);
        else {
            attributes.set(attributes.indexOf(existing), a);
        }
    }

    protected Attribute getAttribute(String key){
        for(Attribute a : attributes){
            if(a.key.equals(key)) return a;
        }
        return null;
    }

    protected String getValue(String key){
        for(Attribute a : attributes){
            if(a.key.equals(key)) return a.value;
        }
        return null;
    }
}
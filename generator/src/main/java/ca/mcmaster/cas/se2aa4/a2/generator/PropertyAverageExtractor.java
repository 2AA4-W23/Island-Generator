package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class PropertyAverageExtractor implements Extractor{

    @Override
    public String extractValues(List<Structs.Property> properties1, List<Structs.Property> properties2, String key) {
        String val1 = null;
        String val2 = null;
        for (Structs.Property p : properties1) {
            if (p.getKey().equals(key)) {
                val1 = p.getValue();
            }
        }
        for (Structs.Property p : properties2) {
            if (p.getKey().equals(key)) {
                val2 = p.getValue();
            }
        }
        if(key.equals("rgb_color")) {
            if (val1 == null)
                val1 = "0,0,0";
            if (val2 == null)
                val2 = "0,0,0";
            String[] raw1 = val1.split(",");
            String[] raw2 = val2.split(",");
            int red = (Integer.parseInt(raw1[0]) + Integer.parseInt(raw2[0])) / 2;
            int green = (Integer.parseInt(raw1[1]) + Integer.parseInt(raw2[1])) / 2;
            int blue = (Integer.parseInt(raw1[2]) + Integer.parseInt(raw2[2])) / 2;
            String color = red + "," + green + "," + blue;
            return color;
        } else if (key.equals("thickness")) {
            if (val1 == null)
                val1 = "3";
            if (val2 == null)
                val2 = "3";
            int average = (Integer.parseInt(val1) + Integer.parseInt(val2)) / 2;
            return Integer.toString(average);
        } else if (key.equals("alpha")) {
            if (val1 == null)
                val1 = "50";
            if (val2 == null)
                val2 = "50";
            int average = (Integer.parseInt(val1) + Integer.parseInt(val2)) / 2;
            return Integer.toString(average);
        }
        return null;
    }

    @Override
    public String extractValues(List<List<Structs.Property>> properties, String key) {
        return null;
    }

//    @Override
//    public double[] centroidPolygons() throws {
//        throw new ExecutionControl.NotImplementedException("Not Neccesary for this Extractor");
//        return new double[0];
//    }
}

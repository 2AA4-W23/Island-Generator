package ca.mcmaster.cas.se2aa4.a2.generator.extractors;

import ca.mcmaster.cas.se2aa4.a2.generator.extractors.Extractor;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class ListPropertyAverageExtractor implements Extractor {
    @Override
    public String extractValues(List<Structs.Property> properties1, List<Structs.Property> properties2, String key) {
        return null;
    }

    @Override
    public String extractValues(List<List<Structs.Property>> properties, String key) {
        String result = "";
        for(List<Structs.Property> props : properties) {
            String prop = "";
            int counted = props.size() * 2;
            for(Structs.Property p : props){
                if(p.getKey().equals(key)) {
                    prop = p.getValue();
                }
            }
            if(key.equals("rgb_color")) {
                if(prop.equals("")){
                    prop = "0,0,0";
                }
                String[] raw = prop.split(",");
                int red = Integer.parseInt(raw[0]) ;
                int green = Integer.parseInt(raw[1]);
                int blue = Integer.parseInt(raw[2]);
                String[] currentRaw = result.split(",");
                int currentRed,currentBlue,currentGreen;
                if(!result.equals("")){
                    currentRed = Integer.parseInt(currentRaw[0]) / counted;
                    currentGreen = Integer.parseInt(currentRaw[1]) / counted;
                    currentBlue = Integer.parseInt(currentRaw[2]) / counted;
                } else {
                    currentRed = 0;
                    currentGreen = 0;
                    currentBlue = 0;
                }
                result = (red / counted + currentRed)  + "," + (green/ counted + currentGreen) + "," + (blue/ counted + currentBlue);
            } else if (key.equals("thickness")) {
                if(prop.equals("")){
                    prop = "3";
                }
                if(result.equals("")) result = "0";
                result = Integer.parseInt(result) + (Integer.parseInt(prop) /counted)+ "";
            } else if (key.equals("alpha")) {
                if (prop.equals("")){
                    prop = "75";
                }
                if(result.equals("")) result = "0";
                result = Integer.parseInt(result) + (Integer.parseInt(prop) / counted) + "";
            }
        }
        return result;
    }
}

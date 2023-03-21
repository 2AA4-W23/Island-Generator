package ca.mcmaster.cas.se2aa4.island.Biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;

public class ArcticBiomeProfile implements BiomeProfile{
    private final double hmax = 250;
    private final double hmin = 0;
    private final double amax = 10;
    private final double amin = -10;
    @Override
    public List addBiomes(List<Structs.Polygon> pList) {
        double minAlt = 100000;
        double maxAlt = 0;
        double minHumid = 100000;
        double maxHumid = 0;
        List<Structs.Polygon> modList = new ArrayList<>();
        for(Structs.Polygon p: pList){
            if(!tileTagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                minHumid = Math.min(Double.parseDouble(humidEx.extractValues(p.getPropertiesList())), minHumid);
                minAlt = Math.min(Double.parseDouble(altEx.extractValues(p.getPropertiesList())), minAlt);
                maxHumid = Math.max(Double.parseDouble(humidEx.extractValues(p.getPropertiesList())), maxHumid);
                maxAlt = Math.max(Double.parseDouble(altEx.extractValues(p.getPropertiesList())), maxAlt);
            }
        }
        for(Structs.Polygon p: pList) {
            if (!tileTagEx.extractValues(p.getPropertiesList()).equals("ocean") && !tileTagEx.extractValues(p.getPropertiesList()).equals("lake")) {
                double alt = Double.parseDouble(altEx.extractValues(p.getPropertiesList()));
                double humid = Double.parseDouble(humidEx.extractValues(p.getPropertiesList()));
                double altMod = amin + (((alt - minAlt) * (amax - amin)) / (maxAlt - minAlt));
                double humidMod = hmin + (((humid - minHumid) * (hmax - hmin)) / (maxHumid - minHumid));
//                System.out.println("A: " + altMod + " H: " + humidMod);
                ArcticBiome b;
                if (altMod < -5){
                    b = ArcticBiome.TUNDRA;
                } else if (humidMod < 100) {
                    b = ArcticBiome.STEPPES;
                } else if (altMod > 5) {
                    b = ArcticBiome.FOREST;
                } else if (altMod > 0) {
                    b = ArcticBiome.BOREAL;
                } else {
                    b = ArcticBiome.TAIGA;
                }
                Structs.Property biomeTag = Structs.Property.newBuilder().setKey("biome").setValue(b.biome_name).build();
                Structs.Property rgbTag = Structs.Property.newBuilder().setKey("rgb_color").setValue(b.rgb_color).build();
                Structs.Polygon bModP = Structs.Polygon.newBuilder(p).addProperties(biomeTag).addProperties(rgbTag).build();
                modList.add(bModP);
            } else {
                modList.add(p);
            }

        }

        return modList;
    }
}

package ca.mcmaster.cas.se2aa4.island.Biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;

public class TropicalBiomeProfile implements BiomeProfile{
    private final double hmax = 450;
    private final double hmin = 0;
    private final double amax = 30;
    private final double amin = 15;

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
                TropicalBiome b;
                if(humidMod < 50){
                    b = TropicalBiome.DESERT;
                } else if (altMod > 20 && humidMod > 280) {
                    b = TropicalBiome.RAINFOREST;
                } else if (altMod > 15 && humidMod > 250) {
                    b = TropicalBiome.LUSHFOREST;
                } else if (humidMod > 150) {
                    b = TropicalBiome.MANGROVE;
                } else {
                    b = TropicalBiome.SAVANNA;
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

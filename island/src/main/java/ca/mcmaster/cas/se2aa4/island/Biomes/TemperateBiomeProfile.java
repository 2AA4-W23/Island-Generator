package ca.mcmaster.cas.se2aa4.island.Biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;

public class TemperateBiomeProfile implements BiomeProfile{
    private final double hmax = 350;
    private final double hmin = 0;
    private final double amax = 15;
    private final double amin = 5;
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
                TemperateBiome b;
                if(humidMod < 50){
                    b = TemperateBiome.TEMPDESERT;
                } else if (humidMod > 250) {
                    b = TemperateBiome.TEMPRAINFOREST;
                } else if (10>altMod && altMod>5 && humidMod > 150) {
                    b = TemperateBiome.WETLANDS;
                } else if (altMod > 10 && humidMod > 125) {
                    b = TemperateBiome.FOREST;
                } else {
                    b = TemperateBiome.PRAIRIES;
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

package ca.mcmaster.cas.se2aa4.island.Biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Properties.PropertyAdder;

import java.util.ArrayList;
import java.util.List;

public abstract class BiomeTemplate implements BiomeProfile{
    protected double hmax;
    protected double hmin;
    protected double amax;
    protected double amin;
    protected enum Biome{
    }
    @Override
    public List<Structs.Polygon> addBiomes(List<Structs.Polygon> pList) {
        List<Structs.Polygon> modList = new ArrayList<>();
        setBiomeRange();
        double[] range = findRange(pList);
        for(Structs.Polygon p: pList) {
            if (!tileTagEx.extractValues(p.getPropertiesList()).equals("ocean") && !tileTagEx.extractValues(p.getPropertiesList()).equals("lake")) {
                double[] modifiedVals = modifiedInputs(p, range);
                String[] biomeVals = identifyBiome(modifiedVals);
                Structs.Polygon bModP = PropertyAdder.addProperty(p,"biome",biomeVals[0]);
                bModP = PropertyAdder.addProperty(bModP,"rgb_color",biomeVals[1]);
                modList.add(bModP);
            } else {
                modList.add(p);
            }
        }
        return modList;
    }
    protected double[] modifiedInputs(Structs.Polygon p, double[] range){
        double alt = Double.parseDouble(altEx.extractValues(p.getPropertiesList()));
        double humid = Double.parseDouble(humidEx.extractValues(p.getPropertiesList()));
        double altMod = amin + (((alt - range[2]) * (amax - amin)) / (range[3] - range[2]));
        double humidMod = hmin + (((humid - range[0]) * (hmax - hmin)) / (range[1] - range[0]));
        return new double[]{altMod, humidMod};
    }
    protected double[] findRange(List<Structs.Polygon> pList){
        double minAlt = Double.POSITIVE_INFINITY;
        double maxAlt = 0;
        double minHumid = Double.POSITIVE_INFINITY;
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
        return new double[]{minHumid, maxHumid, minAlt, maxAlt};
    }
}

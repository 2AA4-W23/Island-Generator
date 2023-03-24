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
        BiomeValCalculator calc = new BiomeValCalculator(hmax, hmin, amax, amin);
        double[] range = calc.findRange(pList);
        for(Structs.Polygon p: pList) {
            String tag = tileTagEx.extractValues(p.getPropertiesList());
            if (!tag.equals("ocean") && !tag.equals("lake") && !tag.equals("endor_lake") ) {
                double[] modifiedVals = calc.modifiedInputs(p, range);
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
}

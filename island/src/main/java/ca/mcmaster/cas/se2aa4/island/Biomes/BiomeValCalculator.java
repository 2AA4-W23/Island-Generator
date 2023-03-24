package ca.mcmaster.cas.se2aa4.island.Biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.island.Extractors.AltitudeExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.Extractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.HumidityExtractor;
import ca.mcmaster.cas.se2aa4.island.Extractors.TileTagExtractor;

import java.util.List;

public class BiomeValCalculator {
    protected double hmax;
    protected double hmin;
    protected double amax;
    protected double amin;
    Extractor tileTagEx = new TileTagExtractor();
    Extractor altEx = new AltitudeExtractor();
    Extractor humidEx = new HumidityExtractor();
    public BiomeValCalculator(double hmax, double hmin, double amax, double amin){
        this.hmax = hmax;
        this.hmin = hmin;
        this.amax = amax;
        this.amin = amin;
    }
    public double[] modifiedInputs(Structs.Polygon p, double[] range){
        double alt = Double.parseDouble(altEx.extractValues(p.getPropertiesList()));
        double humid = Double.parseDouble(humidEx.extractValues(p.getPropertiesList()));
        double altMod = amax + (((alt - range[2]) * (amin - amax)) / (range[3] - range[2]));
        double humidMod = hmin + (((humid - range[0]) * (hmax - hmin)) / (range[1] - range[0]));
        return new double[]{altMod, humidMod};
    }
    public double[] findRange(List<Structs.Polygon> pList){
        double minAlt = Double.POSITIVE_INFINITY;
        double maxAlt = 0;
        double minHumid = Double.POSITIVE_INFINITY;
        double maxHumid = 0;
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

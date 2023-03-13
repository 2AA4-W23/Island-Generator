package ca.mcmaster.cas.se2aa4.island.Altitude;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomAltitude implements AltitudeProfile{

    @Override
    public List addAltitudeValues(List<Structs.Polygon> plist, List<Structs.Segment> sList, List<Structs.Vertex> vlist) {
        List<Structs.Polygon> pModList = new ArrayList<>();
        List <Structs.Vertex> vList = new ArrayList<>(vlist);
        int altConst = 500;

        for(Structs.Polygon p:plist){
            int sum = 0;
            if(tagEx.extractValues(p.getPropertiesList()).equals("ocean")){
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue("43,101,236").build();
                Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue("0").build();
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(color).addProperties(altTag).build();
                pModList.add(pColoredModify);
            } else {
                Set<Integer> vInts = new HashSet<>();
                for(int i = 0; i < p.getSegmentIdxsCount(); i++){
                    Structs.Segment seg = sList.get(p.getSegmentIdxs(i));
                    vInts.add(seg.getV1Idx());
                    vInts.add(seg.getV2Idx());
                }
                for(Integer i: vInts){
                    Structs.Vertex v = vList.get(i);
                    if(altEx.extractValues(v.getPropertiesList()).equals("null")){
                        int altVal = rng.nextInt(0,5000);
                        Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue(Integer.toString(altVal)).build();
                        Structs.Vertex mV = Structs.Vertex.newBuilder(v).addProperties(altTag).build();
                        vList.set(i, mV);
                    }
                }
                for(Integer i: vInts){
                    sum += Integer.valueOf(altEx.extractValues(vList.get(i).getPropertiesList()));
                }
                int average = sum/vInts.size();
                Structs.Property altTag = Structs.Property.newBuilder().setKey("altitude").setValue(Integer.toString(average)).build();
                Structs.Property color;
//                if(average < 1000){
//                    color = Structs.Property.newBuilder().setKey("rgb_color").setValue("242,250,137").build();
//                } else if (average < 2000) {
//                    color = Structs.Property.newBuilder().setKey("rgb_color").setValue("97,227,77").build();
//                } else if (average < 3000) {
//                    color = Structs.Property.newBuilder().setKey("rgb_color").setValue("232,108,6").build();
//                } else if (average < 4000) {
//                    color = Structs.Property.newBuilder().setKey("rgb_color").setValue("232,60,26").build();
//                } else {
//                    color = Structs.Property.newBuilder().setKey("rgb_color").setValue("232,26,26").build();
//                }
                Structs.Polygon pColoredModify = Structs.Polygon.newBuilder(p).addProperties(altTag).build();
                pModList.add(pColoredModify);
            }
        }
        List ansList = new ArrayList<>();
        ansList.add(pModList);
        ansList.add(sList);
        ansList.add(vlist);
        return ansList;
    }


}

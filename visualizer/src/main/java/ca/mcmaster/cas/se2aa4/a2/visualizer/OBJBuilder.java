package ca.mcmaster.cas.se2aa4.a2.visualizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.random.RandomGenerator;

import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class OBJBuilder {

    Random bag = new Random();
    Set<String> ids = new HashSet<>();


    public void generateOBJ(Mesh mesh) throws IOException {
        List<Vertex> VertexList = mesh.getVerticesList();
        List<Segment> SegmentList = mesh.getSegmentsList();
        List<Polygon> PolygonList = mesh.getPolygonsList();
        File old_obj = new File("mesh.obj");
        File old_mtl = new File("mesh.mtl");
        old_obj.delete();
        old_mtl.delete();
        new File("mesh.obj");
        new File("mesh.mtl");

        FileWriter fwo = new FileWriter("mesh.obj", true);
        FileWriter fwm = new FileWriter("mesh.mtl", true);
       
        fwo.write("mtllib mesh.mtl\n");

        List<String> vertexSet = new ArrayList<>();

        double minZ = 9999999;
        for(Vertex v : VertexList) {
            for(Property p : v.getPropertiesList()) {
                if(p.getKey().equals("altitude")) {
                    double z = Double.parseDouble(p.getValue()) / 1500.0;
                    if(z != 0.0) minZ = Math.min(minZ, z);
                }
            }
        }
        System.out.println(minZ);
        for(Vertex v : VertexList) {
            // String mtlName = getMtlName();
            // fwo.write("usemtl " + mtlName + "\n");
            double x = v.getX() / 100.0;
            double y = v.getY() / 100.0;
            Random rng = new Random();
            double z = rng.nextDouble(0.0,0.1);
            x = Math.round(x * 1000.0) / 1000.0;
            y = Math.round(y * 1000.0) / 1000.0;
            for(Property p : v.getPropertiesList()) {
                if(p.getKey().equals("altitude")) {
                    z = Double.parseDouble(p.getValue()) / 1500.0;
                    if(z > 0.0) z-= minZ;
                }
            }
            z = Math.round(z * 100000.0) / 100000.0;
            if(x > 6.0 || y > 6.0 || x < -1.0 || y < -1.0) continue;
            fwo.write("v " + x + " " + z + " " + y + extractPropString(v.getPropertiesList(), "rbg_color") + "\n");
            vertexSet.add(x + " " + y);
        }

        fwo.write("\nvn 0.00 0.00 1.00\n\n");

        for(Polygon p : PolygonList){ 
            int j = 0;
            Coordinate points[] = new Coordinate[p.getSegmentIdxsCount()];
            Set<Integer> added = new HashSet<>();
            for(Integer i : p.getSegmentIdxsList()){
                Integer v1 = SegmentList.get(i).getV1Idx();
                Integer v2 = SegmentList.get(i).getV2Idx();
//                System.out.println("Segment " + i);
                try{
                    if(!added.contains(v1)){
                        double x = VertexList.get(SegmentList.get(i).getV1Idx()).getX() / 100.0;
                        double y = VertexList.get(SegmentList.get(i).getV1Idx()).getY() / 100.0;
                        x = Math.round(x * 1000.0) / 1000.0;
                        y = Math.round(y * 1000.0) / 1000.0;
                        points[j] = new Coordinate(x, y);
                        added.add(v1);
                        j++;
                    }
                    if(!added.contains(v2)){
                        double x = VertexList.get(SegmentList.get(i).getV2Idx()).getX() / 100.0;
                        double y = VertexList.get(SegmentList.get(i).getV2Idx()).getY() / 100.0;
                        x = Math.round(x * 1000.0) / 1000.0;
                        y = Math.round(y * 1000.0) / 1000.0;
                        points[j] = new Coordinate(x, y);
                        added.add(v2);
                        j++;
                    }
                } catch(Exception e){
                    break;
                }
            }
            if(j != 0) {
                ConvexHull cv = new ConvexHull(points, new GeometryFactory());
                Coordinate[] orderedPoints = cv.getConvexHull().getCoordinates();    
                String polyString = "f ";
                for(int i = 0; i < orderedPoints.length; i++) {
                    double x = orderedPoints[i].getX();
                    double y = orderedPoints[i].getY();
                    x = Math.round(x * 1000.0) / 1000.0;
                    y = Math.round(y * 1000.0) / 1000.0;
                    int idx = vertexSet.indexOf(orderedPoints[i].getX() + " " + orderedPoints[i].getY()) + 1;
                    if(idx == 0) {
                        polyString = "";
                        break;
                    }
                    polyString += idx + "//1 ";
                }
                if(!polyString.equals("")) {
                    String mtlName = getMtlName();
                    fwo.write("usemtl " + mtlName + "\n"); 
                    fwm.write("newmtl " + mtlName + "\n");
                    fwm.write("\tKa " + extractPropString(p.getPropertiesList(), "rgb_color") + "\n");
                    fwm.write("\tKd " + extractPropString(p.getPropertiesList(), "rgb_color") + "\n");
                    fwm.write("\tKs 0.5 0.5 0.5\n");
                    fwm.write("\td " + extractPropString(p.getPropertiesList(), "alpha") + "\n\n");
                    fwo.write(polyString + "\n\n");
                }
            }
        }
        fwo.close();
        fwm.close();
        System.out.println("mesh.obj created");
    }

    // fwm.write("new mtl " + mtlName + "\n");
    //         fwm.write("Ka " + extractPropString(v.getPropertiesList(), "rbg_color") + "\n");
    //         fwm.write("d  " + extractPropString(v.getPropertiesList(), "alpha") + "\n");

    public String getMtlName(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String id = "";
        for(int i = 0; i < 10; i++){
            id += chars.charAt(bag.nextInt(0,26));
        }
        if(ids.contains(id)) {
            id = getMtlName();
        }
        return id;
    }
    
    public String extractPropString(List<Property> p, String key){
        String propString = "";
        for(Property prop : p){
            //System.out.println(prop.getKey());
            if(prop.getKey().equals(key)){
                propString = prop.getValue();
            }
        }
        if (propString == "") return propString;
        if(key.equals("rgb_color")){
            String[] raw = propString.split(",");
            double r = (double) Integer.parseInt(raw[0])/ 255;
            double g = (double) Integer.parseInt(raw[1])/ 255;
            double b = (double) Integer.parseInt(raw[2])/ 255;
            r = Math.round(r * 100.0) / 10.0;
            g = Math.round(g * 100.0) / 10.0;
            b = Math.round(b * 100.0) / 10.0;
            propString = r + " " + g + " " + b;
            //System.out.println("found");
        }
        if(key.equals("alpha")){
            double alphaVal = (double) (Double.parseDouble(propString)) / 255 ;
            alphaVal = Math.round(alphaVal * 100.0) / 100.0;
            propString = alphaVal + "";
            //System.out.println("found");
        }

        return propString;
    }
}

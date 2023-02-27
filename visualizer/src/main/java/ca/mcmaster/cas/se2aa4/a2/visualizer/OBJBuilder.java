package ca.mcmaster.cas.se2aa4.a2.visualizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        File obj = new File("mesh.obj");
        File mtl = new File("mesh.mtl");
        PrintWriter writer = new PrintWriter("mesh.obj");
        writer.print("");
        writer.close();
        PrintWriter writer2 = new PrintWriter("mesh.mtl");
        writer2.print("");
        writer2.close();

        FileWriter fwo = new FileWriter("mesh.obj", true);
        FileWriter fwm = new FileWriter("mesh.mtl", true);
       
        fwo.write("mtllib mesh.mtl\n");

        for(Vertex v : VertexList) {
            // String mtlName = getMtlName();
            // fwo.write("usemtl " + mtlName + "\n");
            fwo.write("v " + v.getX() + " " + v.getY() + " 0 " + extractPropString(v.getPropertiesList(), "rbg_color") + "\n");
        }

        fwo.write("\nvn 0.00 0.00 1.00\n\n");

        for(Polygon p : PolygonList){ 
            String mtlName = getMtlName();
            fwo.write("usemtl " + mtlName + "\n"); 
            fwm.write("newmtl " + mtlName + "\n");
            fwm.write("\tKa " + extractPropString(p.getPropertiesList(), "color") + "\n");
            fwm.write("\td " + extractPropString(p.getPropertiesList(), "alpha") + "\n\n");
            fwo.write("f ");
            for(Integer i : p.getSegmentIdxsList()){
                int vIdx = SegmentList.get(i).getV1Idx() + 1;
                fwo.write(vIdx + "//1 ");
            }
            fwo.write("\n\n");
        }
        
        fwo.close();
        fwm.close();
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
        if(key.equals("color")){
            String[] raw = propString.split(",");
            double r = (double) Integer.parseInt(raw[0])/ 255;
            double g = (double) Integer.parseInt(raw[1])/ 255;
            double b = (double) Integer.parseInt(raw[2])/ 255;
            r = Math.round(r * 100.0) / 100.0;
            g = Math.round(g * 100.0) / 100.0;
            b = Math.round(b * 100.0) / 100.0;
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

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.cli.ParseException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.generator.*;
import ca.mcmaster.cas.se2aa4.a2.visualizer.Visualize;

public class GUI extends JFrame implements ActionListener{

    private Container c;
    private JLabel title;
    private JLabel meshTitle;
    private JComboBox<String> meshSelect;
    private Random rng = new Random();

    private String meshTypes[] = {
        "Grid", "Irregular"
    };

    private JTextField polygons;
    private JLabel polyTitle;
    private JLabel llyodTitle;
    private JTextField numLloyd;
    private JLabel meshSection1Title;
    private JTextField meshOutput;
    private JLabel meshOutputTitle;
    private JButton fileButton;

    private String meshOutputPath = null;
    private String meshInputPath = null;
    private String svgOutputPath = null;
    
    private JButton generateButton;
    private JLabel visualizerTitle;

    private Font sectionTitle = new Font("Arial", Font.PLAIN, 18);
    private Font optionTitle = new Font("Arial", Font.PLAIN, 15);
    private Font textField = new Font("Arial", Font.PLAIN, 12);
    private JLabel inputMeshTitle;
    private JTextField inputMesh;
    private JButton inputMeshButton;
    private JLabel outputSvgTitle;
    private JButton outputSvgButton;
    private JTextField outputSvg;
    private JButton visualizeButton;
    private JLabel objTitle;
    private JCheckBox enableObj;
    private JLabel heatmapTitle;
    private JComboBox<String> heatmapSelect;
    private String[] heatmapTypes = {
        "None", "Altitude", "Humidity"
    };
    private JLabel generatingText;
    private JLabel visualizingText;
    private JLabel islandTitle;
    private JLabel inputMeshTitle2;
    private JTextField inputMesh2;
    private JButton inputMeshFile;
    private JLabel outputIslandTitle;
    private JTextField outputIsland;
    private JButton outputIslandButton;
    private String islandOutputPath;
    private JLabel shapeTitle;
    private JComboBox<String> shapeSelect;
    private String[] shapes = {
        "Circle", "Rectangle", "Irregular"
    };
    private JLabel altitudeTitle;
    private JComboBox<String> altitudeSelect;
    private String[] altitudes = {
        "Mountain", "Volcano", "Random"
    };
    private JLabel biomeTitle;
    private JComboBox<String> biomeSelect;
    private String[] biomes = {
        "Base", "Temperate", "Tropical", "Arctic"
    };
    private JLabel soilTitle;
    private JComboBox<String> soilSelect;
    private String[] soils = {
        "Wet", "Dry"
    };
    private JLabel lakesTitle;
    private JTextField numLakes;
    private JLabel aquifersTitle;
    private JTextField numAquifers;
    private JLabel riversTitle;
    private JTextField numRivers;
    private JButton islandButton;
    private JTextField islandGeneratingText;
    private JLabel citiesTitle;
    private JTextField numCities;
    private JLabel seedTitle;
    private JTextField seed;

    private long seed1;

    public GUI(){
        setTitle("Island Generator");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Island Generator");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300,30);
        title.setLocation(300, 30);
        c.add(title);

        meshSection1Title = new JLabel("Mesh generator");
        meshSection1Title.setFont(sectionTitle);
        meshSection1Title.setSize(150, 30);
        meshSection1Title.setLocation(100, 100);
        meshSection1Title.setToolTipText("<html>Creates a random mesh of polygons using <br>the below parameters</html>");
        c.add(meshSection1Title);
        
        meshTitle = new JLabel("Mesh Type");
        meshTitle.setFont(optionTitle);
        meshTitle.setSize(150,20);
        meshTitle.setLocation(100, 150);
        meshTitle.setToolTipText(
            "<html>The type of mesh to create.<br> Grid: Creates a mesh of squares <br> Irregular: Creates a mesh of irregular polygons</html>"
        );
        c.add(meshTitle);

        meshSelect = new JComboBox<>(meshTypes);
        meshSelect.setFont(textField);
        meshSelect.setSize(100,20);
        meshSelect.setLocation(250, 150);
        meshSelect.setToolTipText(meshTitle.getToolTipText());
        c.add(meshSelect);

        polyTitle = new JLabel("Number of Polygons");
        polyTitle.setFont(optionTitle);
        polyTitle.setSize(150,20);
        polyTitle.setLocation(100, 180);
        polyTitle.setToolTipText("<html>Number of polygons to include.<br>Note: Does not apply to grid meshes</html>");
        c.add(polyTitle);
    

        polygons = new JTextField();
        polygons.setFont(textField);
        polygons.setSize(60, 20);
        polygons.setLocation(250, 180);
        polygons.setToolTipText(polyTitle.getToolTipText());
        c.add(polygons);

        llyodTitle = new JLabel("Llyod Relaxations");
        llyodTitle.setFont(optionTitle);
        llyodTitle.setSize(150,20);
        llyodTitle.setLocation(100, 210);
        llyodTitle.setToolTipText("<html>Number of iterations for Lloyd relaxation.<br>This reduces how pointy the polygons are.<br>Note: Does not apply to grid meshes</html>");
        c.add(llyodTitle);

        numLloyd = new JTextField();
        numLloyd.setFont(textField);
        numLloyd.setSize(40, 20);
        numLloyd.setLocation(250, 210);
        numLloyd.setToolTipText(llyodTitle.getToolTipText());
        c.add(numLloyd);

        meshOutputTitle = new JLabel("Save as ");
        meshOutputTitle.setFont(optionTitle);
        meshOutputTitle.setSize(150, 20);
        meshOutputTitle.setLocation(100, 240);
        c.add(meshOutputTitle);

        meshOutput = new JTextField();
        meshOutput.setFont(textField);
        meshOutput.setSize(150,20);
        meshOutput.setLocation(250, 240);
        meshOutput.setToolTipText("Location to save mesh file");
        c.add(meshOutput);
        
        fileButton = new JButton("Select file", null);
        fileButton.setSize(50, 20);
        fileButton.setLocation(190, 240);
        fileButton.addActionListener(this);
        c.add(fileButton);

        generateButton = new JButton("Generate");
        generateButton.setSize(100,20);
        generateButton.setLocation(100, 270);
        generateButton.addActionListener(this);
        c.add(generateButton);

        generatingText = new JLabel();
        generatingText.setSize(300, 20);
        generatingText.setFont(textField);
        generatingText.setLocation(100, 290);
        c.add(generatingText);

        visualizerTitle = new JLabel("Visualizer");
        visualizerTitle.setFont(sectionTitle);
        visualizerTitle.setSize(150, 20);
        visualizerTitle.setLocation(100, 320);
        visualizerTitle.setToolTipText("Tool to convert meshes into viewable SVG files");
        c.add(visualizerTitle);

        inputMeshTitle = new JLabel("Input Mesh");
        inputMeshTitle.setFont(optionTitle);
        inputMeshTitle.setSize(150, 20);
        inputMeshTitle.setLocation(100, 350);
        c.add(inputMeshTitle);

        inputMesh = new JTextField();
        inputMesh.setFont(textField);
        inputMesh.setSize(150, 20);
        inputMesh.setLocation(250, 350);
        inputMesh.setToolTipText("Location of mesh to view");
        c.add(inputMesh);

        inputMeshButton = new JButton("Select File", null);
        inputMeshButton.setSize(50, 20);
        inputMeshButton.addActionListener(this);
        inputMeshButton.setLocation(190, 350);
        c.add(inputMeshButton);

        outputSvgTitle = new JLabel("Save as");
        outputSvgTitle.setFont(optionTitle);
        outputSvgTitle.setSize(150, 20);
        outputSvgTitle.setLocation(100, 380);
        c.add(outputSvgTitle);

        outputSvg = new JTextField();
        outputSvg.setFont(textField);
        outputSvg.setSize(150, 20);
        outputSvg.setLocation(250, 380);
        outputSvg.setToolTipText("Location to save mesh file");;
        c.add(outputSvg);

        outputSvgButton = new JButton("Select File", null);
        outputSvgButton.setSize(50, 20);
        outputSvgButton.addActionListener(this);
        outputSvgButton.setLocation(190, 380);
        c.add(outputSvgButton);

        objTitle = new JLabel("Output OBJ File");
        objTitle.setFont(optionTitle);
        objTitle.setSize(150, 20);
        objTitle.setLocation(100, 410);
        c.add(objTitle);

        enableObj = new JCheckBox();
        enableObj.setSize(25, 25);
        enableObj.setLocation(210, 408);
        enableObj.setToolTipText("<html>If checked, creates an OBJ and MTL file from the mesh.<br>These files are viewable in programs like Blender.</html>");
        c.add(enableObj);

        heatmapTitle = new JLabel("Heatmap Type");
        heatmapTitle.setFont(optionTitle);
        heatmapTitle.setSize(150,20);
        heatmapTitle.setLocation(100, 440);
        heatmapTitle.setToolTipText("<html>Creates a heatmap of the selected property.<br>Does not apply to non-island meshes.</html>");
        c.add(heatmapTitle);

        heatmapSelect = new JComboBox<>(heatmapTypes);
        heatmapSelect.setFont(textField);
        heatmapSelect.setSize(100,20);
        heatmapSelect.setLocation(250, 440);
        heatmapSelect.setToolTipText(heatmapTitle.getToolTipText());
        c.add(heatmapSelect);

        visualizeButton = new JButton("Create SVG");
        visualizeButton.setSize(100,20);
        visualizeButton.setLocation(100, 470);
        visualizeButton.addActionListener(this);
        c.add(visualizeButton);

        visualizingText = new JLabel();
        visualizingText.setSize(300, 20);
        visualizingText.setFont(textField);
        visualizingText.setLocation(100, 490);
        c.add(visualizingText);

        islandTitle = new JLabel("Island Builder");
        islandTitle.setFont(sectionTitle);
        islandTitle.setSize(150, 20);
        islandTitle.setLocation(500, 100);
        c.add(islandTitle);

        inputMeshTitle2 = new JLabel("Input Mesh");
        inputMeshTitle2.setFont(optionTitle);
        inputMeshTitle2.setSize(150, 20);
        inputMeshTitle2.setLocation(500, 150);
        c.add(inputMeshTitle2);

        inputMesh2 = new JTextField();
        inputMesh2.setFont(textField);
        inputMesh2.setSize(150, 20);
        inputMesh2.setLocation(650, 150);
        inputMesh2.setToolTipText("<html>Mesh to generate island on.<br>Note: Using a mesh with existing island data will <br>cause glitches. Please use a blank mesh.</html>");
        c.add(inputMesh2);

        inputMeshFile = new JButton("Select File", null);
        inputMeshFile.setSize(50, 20);
        inputMeshFile.addActionListener(this);
        inputMeshFile.setLocation(590, 150);
        c.add(inputMeshFile);

        outputIslandTitle = new JLabel("Save as");
        outputIslandTitle.setFont(optionTitle);
        outputIslandTitle.setSize(150, 20);
        outputIslandTitle.setLocation(500, 180);
        c.add(outputIslandTitle);

        outputIsland = new JTextField();
        outputIsland.setFont(textField);
        outputIsland.setSize(150, 20);
        outputIsland.setLocation(650, 180);
        outputIsland.setToolTipText("Location to save island mesh file");;
        c.add(outputIsland);

        outputIslandButton = new JButton("Select File", null);
        outputIslandButton.setSize(50, 20);
        outputIslandButton.addActionListener(this);
        outputIslandButton.setLocation(590, 180);
        c.add(outputIslandButton);

        shapeTitle = new JLabel("Island Shape");
        shapeTitle.setFont(optionTitle);
        shapeTitle.setSize(150,20);
        shapeTitle.setLocation(500, 210);
        shapeTitle.setToolTipText("Shape of the generated island");
        c.add(shapeTitle);

        shapeSelect = new JComboBox<>(shapes);
        shapeSelect.setFont(textField);
        shapeSelect.setSize(100,20);
        shapeSelect.setLocation(650, 210);
        shapeSelect.setToolTipText(shapeTitle.getToolTipText());
        c.add(shapeSelect);

        altitudeTitle = new JLabel("Altitude Profile");
        altitudeTitle.setFont(optionTitle);
        altitudeTitle.setSize(150,20);
        altitudeTitle.setLocation(500, 240);
        altitudeTitle.setToolTipText("<html>Altitude profile of the generated island.<br>The altitude directly affects the temperature of an area</html>");
        c.add(altitudeTitle);

        altitudeSelect = new JComboBox<>(altitudes);
        altitudeSelect.setFont(textField);
        altitudeSelect.setSize(100,20);
        altitudeSelect.setLocation(650, 240);
        altitudeSelect.setToolTipText(altitudeTitle.getToolTipText());
        c.add(altitudeSelect);

        biomeTitle = new JLabel("Biome Profile");
        biomeTitle.setFont(optionTitle);
        biomeTitle.setSize(150,20);
        biomeTitle.setLocation(500, 270);
        biomeTitle.setToolTipText("Biome profile of the generated island");
        c.add(biomeTitle);

        biomeSelect = new JComboBox<>(biomes);
        biomeSelect.setFont(textField);
        biomeSelect.setSize(100,20);
        biomeSelect.setLocation(650, 270);
        biomeSelect.setToolTipText(biomeTitle.getToolTipText());
        c.add(biomeSelect);

        soilTitle = new JLabel("Soil Moisture Profile");
        soilTitle.setFont(optionTitle);
        soilTitle.setSize(150,20);
        soilTitle.setLocation(500, 300);
        soilTitle.setToolTipText("<html>Soil profile of the generated island.<br>Wet: Soil absorbs lots of moisture, but reduces how far the moisture travels.<br>Dry: Soil absorbs little moisture but allows it to propogate further.</html>");
        c.add(soilTitle);

        soilSelect = new JComboBox<>(soils);
        soilSelect.setFont(textField);
        soilSelect.setSize(100,20);
        soilSelect.setLocation(650, 300);
        soilSelect.setToolTipText(soilTitle.getToolTipText());
        c.add(soilSelect);

        lakesTitle = new JLabel("Number of lakes");
        lakesTitle.setFont(optionTitle);
        lakesTitle.setSize(150,20);
        lakesTitle.setLocation(500, 330);
        lakesTitle.setToolTipText("<html>Maximum number of lakes to spawn.<br>Lakes provide humidity to surrounding tiles.<br>Note: Max number of lakes is 20</html>");
        c.add(lakesTitle);

        numLakes = new JTextField();
        numLakes.setFont(textField);
        numLakes.setSize(40, 20);
        numLakes.setLocation(650, 330);
        numLakes.setToolTipText(lakesTitle.getToolTipText());
        c.add(numLakes);

        aquifersTitle = new JLabel("Number of aquifers");
        aquifersTitle.setFont(optionTitle);
        aquifersTitle.setSize(150,20);
        aquifersTitle.setLocation(500, 360);
        aquifersTitle.setToolTipText("<html>Maximum number of aquifers to spawn.<br>Like lakes, they provide humidity to surrounding tiles but are underground.<br>Note: Max number of aquifers is 20</html>");
        c.add(aquifersTitle);

        numAquifers = new JTextField();
        numAquifers.setFont(textField);
        numAquifers.setSize(40, 20);
        numAquifers.setLocation(650, 360);
        numAquifers.setToolTipText(aquifersTitle.getToolTipText());
        c.add(numAquifers);

        riversTitle = new JLabel("Number of rivers");
        riversTitle.setFont(optionTitle);
        riversTitle.setSize(150,20);
        riversTitle.setLocation(500, 390);
        riversTitle.setToolTipText("<html>Maximum number of rivers to spawn.<br>Like lakes and aquifers, they provide humidity to <br>surrounding tiles.<br>Rivers flow from high to low altitude and stop when <br>they hit the ocean or a lake.<br>They may also stop and form their own small lake.<br>Rivers can also merge with other rivers to <br>create thicker ones.<br>Note: Max number of rivers is 30</html>");
        c.add(riversTitle);

        numRivers = new JTextField();
        numRivers.setFont(textField);
        numRivers.setSize(40, 20);
        numRivers.setLocation(650, 390);
        numRivers.setToolTipText(riversTitle.getToolTipText());
        c.add(numRivers);

        citiesTitle = new JLabel("Number of cities");
        citiesTitle.setFont(optionTitle);
        citiesTitle.setSize(150,20);
        citiesTitle.setLocation(500, 420);
        citiesTitle.setToolTipText("<html>Maximum number of cities to spawn.<br>Cities spawn with random sizes and names and <br>one is randomly chosen to be the capital.<br>Road networks are generated between the capital <br>and all other cities.<br>Note: Max number of cities is 30</html>");
        c.add(citiesTitle);

        numCities = new JTextField();
        numCities.setFont(textField);
        numCities.setSize(40, 20);
        numCities.setLocation(650, 420);
        numCities.setToolTipText(citiesTitle.getToolTipText());
        c.add(numCities);

        seedTitle = new JLabel("Seed");
        seedTitle.setFont(optionTitle);
        seedTitle.setSize(150,20);
        seedTitle.setLocation(500, 450);
        seedTitle.setToolTipText("Seed for generating island.");
        c.add(seedTitle);

        seed = new JTextField();
        seed.setFont(textField);
        seed.setSize(150, 20);
        seed.setLocation(650, 450);
        seed.setToolTipText(seedTitle.getToolTipText());
        c.add(seed);

        islandButton = new JButton("Generate Island");
        islandButton.setSize(130,20);
        islandButton.setLocation(500, 480);
        islandButton.addActionListener(this);
        c.add(islandButton);

        islandGeneratingText = new JTextField();
        islandGeneratingText.setEditable(false);
        islandGeneratingText.setBorder(null);
        islandGeneratingText.setSize(300, 20);
        islandGeneratingText.setFont(textField);
        islandGeneratingText.setLocation(500, 500);
        c.add(islandGeneratingText);

        setVisible(true);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fileButton){
            JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView());
            int r = jf.showSaveDialog(null);
            if(r == JFileChooser.APPROVE_OPTION){
                meshOutputPath = jf.getSelectedFile().getAbsolutePath();
                if(!meshOutputPath.substring(meshOutputPath.length() - 5).equals(".mesh")) meshOutputPath += ".mesh";
                System.out.print(meshOutputPath);
                meshOutput.setText(meshOutputPath);
            }
        }

        else if(e.getSource() == generateButton){
            generatingText.setForeground(Color.BLACK);
            generatingText.setText("Generating...");
            String meshType = meshSelect.getSelectedItem().toString().toLowerCase();
            String numPolygons = polygons.getText();
            String numRelax = numLloyd.getText();
            meshOutputPath = meshOutput.getText();
            if(meshOutputPath.isEmpty()) {
                generatingText.setForeground(Color.RED);
                generatingText.setText("Please select a save location.");
                return;
            }
            try {
                if(!meshOutputPath.substring(meshOutputPath.length() - 5).equals(".mesh")) meshOutputPath += ".mesh";
                if(numRelax.isEmpty()) numRelax = "1";
                if(numPolygons.isEmpty()) numPolygons = "200";
                System.out.println(numPolygons + " " + numRelax);
                String[] args = {meshOutputPath, "-mt", meshType, "-np", numPolygons, "-lr", numRelax};
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            Generate.runGenerator(args);
                            generatingText.setForeground(Color.GREEN);
                            generatingText.setText("Mesh created");
                            meshInputPath = meshOutputPath;
                            inputMesh.setText(meshInputPath);
                            inputMesh2.setText(meshInputPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                            generatingText.setForeground(Color.RED);
                            generatingText.setText("An error occured. Please check parameters and try again.");
                        }
                    }
                });
            } catch (Exception ex) {
                generatingText.setForeground(Color.RED);
                generatingText.setText("An error occured. Please check parameters and try again.");
                return;
            }
        }

        else if(e.getSource() == inputMeshButton){
            JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView());
            jf.setFileFilter(new FileNameExtensionFilter("MESH FILE", "mesh"));
            int r = jf.showOpenDialog(null);
            if(r == JFileChooser.APPROVE_OPTION){
                meshInputPath = jf.getSelectedFile().getAbsolutePath();
                if(!meshInputPath.substring(meshInputPath.length() - 5).equals(".mesh")) return;
                System.out.print(meshOutputPath);
                inputMesh.setText(meshInputPath);
                inputMesh2.setText(meshInputPath);
            }
        }

        else if(e.getSource() == outputSvgButton){
            JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView());
            int r = jf.showSaveDialog(null);
            if(r == JFileChooser.APPROVE_OPTION){
                svgOutputPath = jf.getSelectedFile().getAbsolutePath();
                if(!svgOutputPath.substring(svgOutputPath.length() - 4).equals(".svg")) svgOutputPath += ".svg";
                System.out.print(svgOutputPath);
                outputSvg.setText(svgOutputPath);
            }
        }

        else if(e.getSource() == visualizeButton){
            System.out.println("Visualizing");
            String heatmap = heatmapSelect.getSelectedItem().toString();
            boolean obj = enableObj.isSelected();
            String argString = "";
            if(svgOutputPath == null) {
                visualizingText.setForeground(Color.RED);
                visualizingText.setText("Please select a save location.");
                return;
            }
            if(meshInputPath == null) {
                visualizingText.setForeground(Color.RED);
                visualizingText.setText("Please select an input mesh.");
                return;
            }
            argString += meshInputPath + "~" + svgOutputPath + "~-A~255";
            if(heatmap.equals("Altitude")) argString += "~-Alt";
            else if(heatmap.equals("Humidity")) argString += "~-Humid";
            if(obj) argString += "~-O";
            try {
                Visualize.createSVG(argString.split("~"));
                Desktop d = Desktop.getDesktop();
                d.open(new File(svgOutputPath));
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            visualizingText.setForeground(Color.GREEN);
            visualizingText.setText("SVG Created.");
            System.out.println("Done");
        }
        else if(e.getSource() == inputMeshFile) {
            JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView());
            jf.setFileFilter(new FileNameExtensionFilter("MESH FILE", "mesh"));
            int r = jf.showOpenDialog(null);
            if(r == JFileChooser.APPROVE_OPTION){
                meshInputPath = jf.getSelectedFile().getAbsolutePath();
                if(!meshInputPath.substring(meshInputPath.length() - 5).equals(".mesh")) return;
                System.out.print(meshOutputPath);
                inputMesh2.setText(meshInputPath);
            }
        }
        else if(e.getSource() == outputIslandButton) {
            JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView());
            int r = jf.showSaveDialog(null);
            if(r == JFileChooser.APPROVE_OPTION){
                islandOutputPath = jf.getSelectedFile().getAbsolutePath();
                if(!islandOutputPath.substring(islandOutputPath.length() - 5).equals(".mesh")) islandOutputPath += ".mesh";
                System.out.print(islandOutputPath);
                outputIsland.setText(islandOutputPath);
            }
        }
        else if(e.getSource() == islandButton) {
            islandGeneratingText.setForeground(Color.BLACK);
            islandGeneratingText.setText("Generating...");
            islandOutputPath = outputIsland.getText();
            meshInputPath = inputMesh2.getText();
            String shape = shapeSelect.getSelectedItem().toString();
            String biome = biomeSelect.getSelectedItem().toString();
            String alt = altitudeSelect.getSelectedItem().toString();
            String soil = soilSelect.getSelectedItem().toString();
            String lakes = numLakes.getText();
            String aqs = numAquifers.getText();
            String rivers = numRivers.getText();
            String cities = numCities.getText();
            String sd = seed.getText();
            if(islandOutputPath.isEmpty()) {
                islandGeneratingText.setForeground(Color.RED);
                islandGeneratingText.setText("Please select a save location.");
                return;
            }
            if(meshInputPath.isEmpty()) {
                islandGeneratingText.setForeground(Color.RED);
                islandGeneratingText.setText("Please select an input mesh.");
                return;
            }
            try {
                if(!islandOutputPath.substring(islandOutputPath.length() - 5).equals(".mesh")) islandOutputPath += ".mesh";
                String args = "-i " + meshInputPath + " -o " + islandOutputPath;
                args += " --shape " + shape;
                args += " --biome " + biome;
                args += " --altitude " + alt;
                args += " --soil " + soil;
                if(!lakes.isEmpty()) args += " --lakes " + lakes;
                if(!aqs.isEmpty()) args += " --aquifers " + aqs;
                if(!rivers.isEmpty()) args += " --rivers " + rivers;
                if(!cities.isEmpty()) args += " --cities " + cities;
                if(!sd.isEmpty()) args += " --seed " + sd;
                String[] args2 = args.split(" ");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            seed1 = Generator.generate(args2);
                            islandGeneratingText.setForeground(Color.GREEN);
                            islandGeneratingText.setText("Island mesh created with seed " + seed1);
                            meshInputPath = islandOutputPath;
                            inputMesh.setText(meshInputPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                            islandGeneratingText.setForeground(Color.RED);
                            islandGeneratingText.setText("An error occured. Please check parameters and try again.");
                        }
                    }
                });
            } catch (Exception ex) {
                islandGeneratingText.setForeground(Color.RED);
                islandGeneratingText.setText("An error occured. Please check parameters and try again.");
                return;
            }
        }
    }
}

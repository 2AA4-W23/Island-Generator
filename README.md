# Assignment A4: Urbanism

- Ibrahim Quraishi [quraishi@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with thefeature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, a file named `visualizer.jar` in the `visualizer` one, and a file named `island.jar` in the `island` one

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator
mosser@azrael generator % java -jar generator.jar sample.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator %
```

To choose the type of mesh generated include the `-mt` tag with a string of either grid or irregular for each respective mesh. If the tag is not provided it will default to a grid mesh.

```
mosser@azrael generator % java -jar generator.jar sample.mesh -mt <mesh_type>
```

To modify the number of polygons/centroids that are generated, use the `-np` tag followed by a value which is an Integer. If the tag is not provided it will default to a random number between 300 and 750.

```
mosser@azrael generator % java -jar generator.jar sample.mesh -np <value>
```

To modify the number of times llyod relaxation is done, use the `-lr` tag followed by a value which is an Integer. If the tag is not provided it will default to 10 iterations.

```
mosser@azrael generator % java -jar generator.jar sample.mesh -lr <value>
```

To view all available options and how to interact with generator tool, use the `-h`, or `--help` tags.

```
mosser@azrael generator % java -jar generator.jar sample.mesh -h
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```

To visualize the connections between neighbouring polygons and the general mesh in debug mode include the tag `-X`. This tag is not mandatory and only needs to be included if we are trying to run in debug mode.

```
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg -X
```
To change the thickness of the components, use the tag `-T` followed by the thickness value. If not specified, vertex thicknesses are randomized and segments average the thickness of its vertices.

```
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg -T <value>
```

To change the transparency/alpha of the components, use the tag `-A` followed by the alpha value (0-255). If not specified, vertex alphas are randomized, segments average the alpha of their vertices, and polygons average the alpha of their segments.

```
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg -A <value>
```

To generate a Wavefront .obj and .mtl file, use the tag `-O`. This file can be viewed in programs like Blender.

```
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg -O
```

To view all available options and how to interact with visualizer tool, use the `-h`, or `--help` tags.

```
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg -h
```

To viualize the SVG file:

- Open it with a web browser
- Convert it into something else with tool slike `rsvg-convert`

### Scenario

To generate and visualize an irregular mesh, use the above commands in their respective directories with the associated tags. For instance, to generate/visualize an irregular mesh with 500 polygons/centroids, 15 lloyd relaxations, component thickness of 5, and alpha value of 250 in debug mode: 

```
mosser@azrael A2 % cd generator
mosser@azrael generator % java -jar generator.jar sample.mesh -mt irregular -np 500 -lr 15

... (debug information printed to stdout) ...

mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator %
```

Followed by:

```
mosser@azrael A2 % cd ../visualizer
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg -X -T 5 -A 250

... (debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```

If the tags are used in a different order, the result will be the same. This example implements all the possible tags, but they can easily be ommitted, in which case the program will act accordingly and generate/visualize the mesh based on the default values. 

### Island

To generate a lagoon, use the `-m` or `--mode` tags (Omitting this tag will generate a normal island).

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --mode lagoon
```

To control the shape of the island, use the tag `--shape` followed by the specified shape (circle, rectangle, irregular).

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular
```

To control the altitude of the island, use the tag `--altitude` followed by the altimetric profile (random, volcano, mountain).

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular --altitude volcano
```

To modify the number of lakes, use the tag `--lakes` followed by a number up to 15. It will default to a randomized number between 2 and 10.

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular --altitude volcano --lakes 5
```

To modify the number of rivers, use the tag `--rivers` followed by a number up to 30. It will default to a randomized number between 2 and 30.

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular --altitude volcano --lakes 5 --rivers 30
```

To modify the number of aquifers, use the tag `--aquifers` followed by a number up to 15. It will default to a randomized number between 2 and 10.

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular --altitude volcano --lakes 5 --rivers 30 --aquifers 2
```

To control the soil absoprtion levels, use the tag `--soil` followed by the absorption profile (wet, dry). It will default to the wet soil profile.

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular --altitude volcano --lakes 5 --rivers 30 --aquifers 2 --
```

To control the biome distribution, use the tag `--biomes` followed by the biome profile (Arctic, Temperate, Tropical, Base). It will default to the base biome profile.

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular --altitude volcano --lakes 5 --aquifers 2 --rivers 30 --***soil*** --biomes Arctic
```

To regenerate specific islands, use the tag `--seed` followed by the seed ID (ID is given after generation).

```
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular --altitude volcano --lakes 5 --aquifers 2 --rivers 30 --biomes Arctic --seed 1203
```

### Visualizer

The visualizer works similar to before, with the same tags, only that `island.mesh` is being visualized. There are also new visualizer tags specifically for island generation.

To view the island as a humidity heatmap, use the tag `-Humid`.

```
mosser@azrael visualizer % java -jar visualizer.jar ../island/island.mesh sample.svg -A 255 -Humid
```

To view the island as an altitude heatmap, use the tag `-Alt`.

```
mosser@azrael visualizer % java -jar visualizer.jar ../island/island.mesh sample.svg -A 255 -Alt
```

To generate Wavefront .obj and .mtl file with altitude, use the tag `-O`. As before, this can be viewed in Blender with the new depth dimension.

```
mosser@azrael visualizer % java -jar visualizer.jar ../island/island.mesh sample.svg -A 255 -O
```

### Scenario

To generate and visualize an island, use the above commands in their respective directories with the associated tags. In this scenario we will generate an Arctic island with an irregular shape, volcanic altitude, 10 lakes, 5 aquifiers, and 30 rivers:

```
mosser@azrael A2 % cd generator
mosser@azrael generator % java -jar generator.jar sample.mesh -mt irregular -np 500 -lr 15

... (debug information printed to stdout) ...

mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator %
```

Followed by:

```
mosser@azrael A2 % cd ../island
mosser@azrael island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh --shape irregular --altitude volcano --lakes 10 --aquifers 5 --rivers 30 --biomes Arctic

... (debug information printed to stdout) ...

mosser@azrael island % ls -lh island.mesh
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael island %
```

Finally:

```
mosser@azrael A2 % cd ../visualizer
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg -A 250 -O

... (debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```


## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

For a feature to be considered done it must be work without throwing any exceptions. and implement the desired functionality. Also any features dependent on the done feature must be able to use it without problems.

### Product Backlog

| Id  | Feature title                                                     | Who?      | Start      | End        | Status |
| :-: | ----------------------------------------------------------------- | --------- | ---------- | ---------- | ------ |
| F01 | Draw one Segment                                                  | Virochaan | 02/07/2023 | 02/07/2023 | D      |
| F02 | Draw All Segments                                                 | Virochaan | 02/07/2023 | 02/07/2023 | D      |
| F03 | Draw all Segment and average the colours                          | Virochaan | 02/07/2023 | 02/07/2023 | D      |
| F04 | Segment precision of 2 decimal places                             | Omar      | 02/07/2023 | 02/07/2023 | D      |
| F05 | Draw one polygon from segments                                    | Ibrahim   | 02/09/2023 | 02/10/2023 | D      |
| F06 | Draw all polygons from segments                                   | Ibrahim   | 02/10/2023 | 02/10/2023 | D      |
| F07 | Polygons reference index of neighbors                             | Virochaan | 02/12/2023 | 02/12/2023 | D      |
| F08 | Polygons reference their centroid                                 | Virochaan | 02/13/2023 | 02/13/2023 | D      |
| F09 | Coloured Vertices, Segments, Polygons                             | Ibrahim   | 02/11/2023 |02/13/2023  | D      |
| F10 | Vertices, Segments and Polygons can have different thicknesses    | Ibrahim   | 02/16/2023 |02/17/2023  | D      |
| F11 | Vertices, Segments and Polygons can have different transparencies | Ibrahim   | 02/17/2023 |02/18/2023  | D      |
| F12 | Debug visualization mode                                          | Virochaan | 02/13/2023 | 02/13/2023 | D      |
| F13 | Random centroid vertex distribution                               | Ibrahim   | 02/18/2023 | 02/18/2023 | D      |
| F14 | Construct segments and polygons from Voronoi diagram              | Ibrahim   | 02/18/2023 | 02/21/2023 | D      |
| F15 | Apply Llyod relaxation any number of times                        | Virochaan | 02/20/2023 | 02/21/2023 | D      |
| F16 | Polygons reference neighbors using Delaunay's Triangulation       | Virochaan | 02/22/2023 | 02/23/2023 | D      |
| F17 | Convex hull computation for segment ordering                      | Ibrahim   | 02/21/2023 | 02/21/2023 | D      |
| F18 | Select mesh type (grid or irregular)                              | Ibrahim   | 02/18/2023 | 02/18/2023 | D      |
| F19 | Select number of polygons                                         | Virochaan | 02/21/2023 | 02/21/2023 | D      |
| F20 | Select relaxation levels                                          | Virochaan | 02/21/2023 | 02/21/2023 | D      |
| F21 | Command line help argument                                        | Omar      | 02/22/2023 | 02/22/2023 | D      |
| F22 | Generate obj files from mesh                                      | Ibrahim   | 02/26/2023 | 02/27/2023 | D      |
| MVP1| Generate Single Circular Island                                   | Virochaan | 03/06/2023 | 03/7/2023  | D      |
| MVP2| Generate Lagoon within Island                                     | Virochaan | 03/06/2023 | 03/7/2023  | D      | 
| MVP2| Tag tiles near water as beaches                                   | Virochaan | 03/06/2023 | 03/7/2023  | D      |
| F23 | Accept command line to choose shape Mechanism                     | Virochaan | 03/7/2023  | 03/7/2023  | D      |
| F24 | Generate Rectangular Shaped Island                                | Virochaan | 03/7/2023  | 03/7/2023  | D      |
| F25 | Generate Irregular (Concave) Polygon for island generation        | Virochaan | 03/7/2023  | 03/10/2023 | D      |
| F26 | Accept command line to choose altitude profile                    | Virochaan | 03/13/2023 | 03/13/2023 | D      |
| F27 | Define basic/random altimetric profile                            | Virochaan | 03/13/2023 | 03/13/2023 | D      |
| F28 | Define Volcano Altimetric profile                                 | Virochaan | 03/13/2023 | 03/13/2023 | D      |
| F29 | Define Mountain Altimetric Profile                                | Virochaan | 03/13/2023 | 03/13/2023 | D      |
| F30 | Island can have random number of lakes                            | Ibrahim   | 03/10/2023 | 03/14/2023 | D      |
| F31 | Lakes affect surrounding vegetation and humidity                  | Ibrahim   | 03/13/2023 | 03/15/2023 | D      |
| F33 | Rivers start at vertex and flow to lowest point                   | Omar      | 03/15/2023 | 03/15/2023 | D      |
| F34 | Rivers add moisture to the surrounding tiles                      | Ibrahim   | 03/15/2023 | 03/15/2023 | D      |
| F35 | Rivers create endorheic lakes                                     | Ibrahim   | 03/23/2023 | 03/24/2023 | D      |
| F36 | Accept command line to choose number of rivers                    | Omar      | 03/15/2023 | 03/15/2023 | D      |
| F37 | Rivers have random discharge level (size/thickness)               | Ibrahim   | 03/21/2023 | 03/21/2023 | D      |
| F38 | Rivers can combine into thicker ones, accumulating more moisture  | Ibrahim   | 03/21/2023 | 03/21/2023 | D      |
| F39 | Island has random aquifers number of aquifers                     | Ibrahim   | 03/14/2023 | 03/14/2023 | D      |
| F40 | Aquifers add moisture (humidity) to the surrounding tiles         | Ibrahim   | 03/14/2023 | 03/14/2023 | D      |
| F41 | Command Line argument to set number of aquifers                   | Ibrahim   | 03/14/2023 | 03/14/2023 | D      |
| F42 | Soil can absorb humidity from bodies of water based on distance   | Ibrahim   | 03/24/2023 | 03/24/2023 | D      |
| F43 | Accept command line to choose absorption profile                  | Ibrahim   | 03/24/2023 | 03/24/2023 | D      |
| F44 | Modify soil absorption for wet soil profile for greater range of humidity         | Ibrahim   | 03/24/2023 | 03/24/2023 | D      |
| F45 | Modify soil absorption for dry soil profile for limited range of humidity         | Ibrahim   | 03/24/2023 | 03/24/2023 | D      |
| F46 | Tiles are assigned base biomes based on humidity levels and temperature/elevation | Virochaan | 03/14/2023 | 03/14/2023 | D      |
| F47 | Select Biomes distribution using the --biomes command line argument               | Virochaan | 03/21/2023 | 03/21/2023 | D      |
| F48 | Use Arctic Biomes and biome calculation model with artic for the --biomes input   | Virochaan | 03/21/2023 | 03/21/2023 | D      |
| F49 | Use Tropical Biomes and biome calculation model with Tropical for the --biomes input    | Virochaan | 03/21/2023 | 03/21/2023 | D      |
| F50 | Use Temperate Biomes and biome calculation model with Temperate for the --biomes input  | Virochaan | 03/21/2023 | 03/21/2023 | D      |
| F51 | Islands are generated using set seed which is outputted to user                                           | Virochaan | 03/24/2023 | 03/24/2023 | D      |
| F52 | Accept command line arg to allow user to choose seeds to generate specific islands and control randomness | Virochaan|03/24/2023 | 03/24/2023 | D      |
| F53 | Accept help command line to view all possible options             | Omar      | 03/10/2023 | 03/10/2023 | D      |
| F54 | Heatmap mode to visualize altitudes and humidity                                                          | Virochaan | 03/13/2023 |       03/26/2023     | D     |
| F55 | Generated OBJ file shows altitudes                                                                        | Ibrahim   | 03/13/2023 | 03/26/2023 |D    |

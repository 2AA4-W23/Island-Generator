# Assignment A2: Mesh Generator

- Virochaan Ravichandran Gowri [ravicv3@mcmaster.ca]
- Ibrahim Quraishi [quraishi@mcmaster.ca]
- Omar Al-Asfar [alasfaro@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with thefeature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one.

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
| F31 | Lakes affect surrounding vegetation and humidity                  | Ibrahim   | 03/13/2023 |            | S      |
| F33 | Rivers start at vertex and flow to lowest point                   | Omar      | 03/15/2023 | 03/15/2023 | S     |
| F36 | Accept command line to choose number of rivers                     | Omar      | 03/15/2023 | 03/15/2023 | D      |
| F39 | Island has random aquifers number of aquifers                     | Ibrahim   | 03/14/2023 | 03/14/2023 | D      |
| F40 | Aquifers add moisture (humidity) to the surrounding tiles         | Ibrahim   | 03/14/2023 | 03/14/2023 | D      |
| F54 | Heatmap mode to visualize altitudes and humidity                  | Virochaan | 03/13/2023 |            | S      |

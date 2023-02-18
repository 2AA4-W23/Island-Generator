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

To viualize the SVG file:

- Open it with a web browser
- Convert it into something else with tool slike `rsvg-convert`

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
| F12 | Activate debug visualization mode from command line               | Virochaan | 02/13/2023 | 02/13/2023 | D      |

# Assignment A4: Pathfinder Library

- Ibrahim Quraishi [quraishi@mcmaster.ca]

## Constructing a graph

First, create a list of Nodes, each with an ID (typically from 0 to size - 1).

```java
List<Node> nodes = new ArrayList<>(); //create node list
for(int i = 0; i < 10; i++) nodes.add(new Node(i)) //add nodes to list
```

Next, create a list of Edges, specifying which nodes you want to connect and the properties of the edge.

```java
List<Edge> edges = new ArrayList<>(); //create edge list
edges.add(new Edge(nodes.get(1), nodes.get(2))); //connect nodes 1 and 2 with an edge
edges.add(new Edge(nodes.get(3), nodes.get(2))); //connect nodes 3 and 2 with an edge
...
```

Now, use these lists to construct a graph.

```java
Graph<Node, Edge> g = new Graph<>(nodes, edges);
```

Alternatively, you can construct the graph with only a list of nodes and construct edges as follows.

```java
Graph<Node, Edge> g = new Graph<>(nodes);
g.addUndirectedEdge(1, 2); //connect nodes 1 and 2 with an undirected edge
g.addUndirectedEdge(3, 2); //connect nodes 1 and 2 with an undirected edge
```

## Getters

To get a node by ID:

```java
Node n = G.getNode(id);
```

To get an edge by the nodes it connects:

```java
Edge e = G.getEdge(id1, id2);
```

Alternatively, if the index of the edge is known, you can use that to retrieve it. The index of edges are the order it was added in starting from 0.

```java
Edge e = G.getEdge(i);
```

## Adding Attributes

Nodes and edges can hold attributes based on a key and value system. To create an attribute:

```java
String key = "color";
String value = "255,102,50"
Attribute a = new Attribute(key, value);
```

The key and value of an attribute can be accessed directly but cannot be edited once created.

```java
System.out.println(a.key); //"color"
System.out.println(a.value); //"255,102,50"
a.key = "rgb" //throws an error
```

To add the attribute to an edge or node:

```java
Node n = G.getNode(3);
n.addAttribute(a);
```

To retrieve an attribute from a node or edge with a given key:

```java
String key = "color";
Attribute color = n.getAttribute(key);
```

Alternatively, you can retrieve the attribute's value directly from the node or edge.

```java
String value = n.getValue(key);
```

## Shortest Paths

To get the shortest path between two nodes:

```java
Node a = G.getNode(1); 
Node b = G.getNode(2);
Path shortestPath = G.shortestPath(a, b);
```

The Path object can be viewed as an unmodifiable list.

```java
List<Node> path = shortestPath.getList();
```

You can also get the length of the path, or view it as a string. 
A path with size 0 indicates the start and end node are the same. A path with size -1 indicates there is no path between the nodes.

```java
System.out.println(path.size()); //prints length of path
System.out.println(path.toString()); //prints all nodes in path by ID
```


The nodes in a Path object cannot be modified, but you can construct a new path object by adding another node.

```java
Path newPath = shortestPath.appendNode(new Node(id));
```


## Extending the library

Since the Graph data structure allows for the use of any class that extends edges and nodes, these components can be easily extended to add additional functionality. For example, we can create a directed graph as follows

```java
class DirectedEdge<N> extends Edge<N> {
    public DirectedEdge(N v1, N v2){
        super(v1, v2);
    }   
}

class DirectedGraph<N> extends Graph<N, DirectedEdge> {
    public DirectedGraph(List<N> nodes, List<E> edges){
        super(node, edges);
    }

    @Override
    protected void addEdge(N v1, N v2){
        if(!adjacencyList.containsKey(v1.id)) adjacencyList.put(v1.id, new HashSet<>());
        adjacencyList.get(v1.id).add(v2);
    }
    
    @Override
    protected void addDirectedEdge(int v1, int v2){
        Node a = getNode(v1);
        Node b = getNode(v2);
        addEdge(v1, v2);
    }
}
```

DirectedEdge acts mostly the same as an Edge, but WeightedGraph has an updated addEdge mechanism which adds 1-way connections to the internal adjacency list instead of a 2-way connection. There is also a public addDirectedEdge function for users of the class to add edges externally.

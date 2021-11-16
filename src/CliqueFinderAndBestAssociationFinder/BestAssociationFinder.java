package CliqueFinderAndBestAssociationFinder;

import java.util.*;

/**
 * The class CliqueFinderAndBestAssociationFinder.BestAssociationFinder is used to calculate the best Association betweeen two vertex and the vertices along
 * the way. The CliqueFinderAndBestAssociationFinder.BestAssociationFinder will accept a n x n association table.
 *
 * @author Pulin Angurala & Raymond Li
 */
public class BestAssociationFinder
{
    private Graph graph;
    private final double associationTables[][];
    private double weights[][];

    public BestAssociationFinder(double[][] associationTables)
    {
        graph = new Graph();
        this.associationTables = associationTables;
        weights = new double[associationTables.length][associationTables.length];
    }

    /**
     * The method weightTable is used to create another association table however rather having association the weight
     * table will use weights.
     *
     * @param associationTables a table of association between vertices.
     * @return a 2D array representing the weights between the association.
     */
    public double[][] weightTable(double[][] associationTables)
    {
        for (int i = 0; i < associationTables.length; i++)
        {
            for (int j = 0; j < associationTables.length; j++)
            {
                if (associationTables[i][j] != 0.0)
                {
                    weights[i][j] = Math.abs(Math.log(associationTables[i][j]));
                }
                else
                {
                    weights[i][j] = 0.0;
                }
            }
        }
        return weights;
    }

    /**
     * The method creatingGraph is used to create a graph out of the weight table.
     *
     * @param weights a double 2D array of weights
     * @return a graph representing the weight table
     */
    public Graph creatingGraph(double[][] weights)
    {
        for (int i = 0; i < weights.length; i++)
        {
            graph.addVertex(i);
        }
        //Creating the edges
        for(int j = 0 ; j < weights.length; j ++)
        {
            for(int k = 0; k < weights.length; k++)
            {
                if(j < k && weights[j][k] !=0)
                {
                    graph.addEdges(graph.getVertices().get(j),graph.getVertices().get(k), weights[j][k]);
                }
            }
        }
        return graph;
    }

    /**
     * The method getShortestPathTree is used to find the shortest pathing between to vertices. The method will
     * calculate both the shortest pathing the intermediate vertices.
     *
     * @param source the starting vertex.
     * @param destination the ending vertex.
     * @return a list of vertices.
     */
    public ArrayList<Edge> getShortestPathTree(Vertex source, Vertex destination)
    {
        final Map<Vertex, Double> shortestPathEstimates = new HashMap<>();
        Map<Vertex, Edge> leastEdges = new HashMap<>();
        Comparator<Vertex> comparator = new Comparator<Vertex>()
        {
            public int compare(Vertex o1, Vertex o2) {
                return (int) (shortestPathEstimates.get(o1) - shortestPathEstimates.get(o2));
            }
        };
        PriorityQueue<Vertex> queue = new PriorityQueue<>(graph.getVertices().size(), comparator);
        for (Vertex vertex : graph.getVertices())
        {
            leastEdges.put(vertex, null);
            if (vertex != source)
            {
                shortestPathEstimates.put(vertex, Double.MAX_VALUE);
                queue.add(vertex);
            }
            else
            {
                shortestPathEstimates.put(source, 0.0);
            }
        }
        ArrayList<Vertex> knownSPVertices = new ArrayList<>();
        knownSPVertices.add(source);

        ArrayList<Edge> spt = new ArrayList<>();
        Vertex addedVertex = source;
        while (queue.size() > 0)
        {
            for (Edge edge : addedVertex.getIncidentEdges())
            {
                Vertex v = edge.oppositeVertex(addedVertex);
                double newEstimate = shortestPathEstimates.get(addedVertex) + edge.getWeight();
                if (!knownSPVertices.contains(v) && newEstimate < shortestPathEstimates.get(v))
                {
                    Iterator<Vertex> iterator = queue.iterator();
                    boolean found = false;
                    while (!found && iterator.hasNext())
                    {
                        found = (iterator.next() == v);
                    }
                    if (found)
                    {
                        iterator.remove();
                        shortestPathEstimates.put(v, newEstimate);
                        queue.add(v);
                        leastEdges.put(v, edge);
                    }
                }
            }
            addedVertex = queue.poll();
            knownSPVertices.add(addedVertex);
            spt.add(leastEdges.get(addedVertex));
        }

        ArrayList<Edge> orderedTree = orderingShortestPathTree(spt , source);
        ArrayList<Edge> edgeList = new ArrayList<>();
        Vertex startVertex = source;
        Vertex endVertex = destination;
        while(endVertex != startVertex)
        {
            for(Edge e: orderedTree)
            {
                if(e.getVertexE() == endVertex)
                {
                    endVertex = e.getVertexS();
                    edgeList.add(e);
                }
            }
        }

        ArrayList<Edge> shortestPath = new ArrayList<>();
        ArrayList<Vertex> vertexShortestPathing = new ArrayList<>();
        double weight= 0.0;
        for(int i = edgeList.size() -1 ; i >= 0; i--)
        {
            weight+= edgeList.get(i).getWeight();
            if(!vertexShortestPathing.contains(edgeList.get(i).getVertexS()))
            {
                vertexShortestPathing.add(edgeList.get(i).getVertexS());
            }
            if(!vertexShortestPathing.contains(edgeList.get(i).getVertexE()))
            {
                vertexShortestPathing.add(edgeList.get(i).getVertexE());
            }
            shortestPath.add(edgeList.get(i));
        }
        for(Edge edges: shortestPath)
        {
            System.out.println(" " + edges);
        }
        System.out.println("The Vertices are: ");
        for(Vertex vertex: vertexShortestPathing)
        {
            System.out.print(" "+ vertex);
        }
        System.out.println("The total weight is: " + weight);
        return shortestPath;
    }

    /**
     * The method orderingShorestPathTree is used to order the edges of the shortestPathTree.
     *
     * @param unsortedTree a list of the shortest path edges.
     * @param source the starting vertex.
     * @return a list of ordered shortest path tree
     */
    public ArrayList<Edge> orderingShortestPathTree(ArrayList<Edge> unsortedTree, Vertex source)
    {
        int size = unsortedTree.size();
        ArrayList<Edge> shortestPathTree = new ArrayList<>();
        ArrayList<Vertex> level = new ArrayList<>();
        ArrayList<Edge> copyUnsortedTree = new ArrayList<>(unsortedTree);

        for(int i = 0 ; i < unsortedTree.size(); i++)
        {
            if(unsortedTree.get(i).getVertexS() == source)
            {
                shortestPathTree.add(unsortedTree.get(i));
                level.add(unsortedTree.get(i).getVertexE());
            }
            else if(unsortedTree.get(i).getVertexE() == source)
            {
                Edge sortingEdge = new Edge(source, unsortedTree.get(i).getVertexS());
                shortestPathTree.add(sortingEdge);
                unsortedTree.set(i,sortingEdge);
                copyUnsortedTree.set(i, sortingEdge);
                level.add(unsortedTree.get(i).getVertexE());
            }
        }
        System.out.println();

        for(Vertex vertex: level)
        {
            for(Edge edge: copyUnsortedTree)
            {
                if(vertex == edge.getVertexE() && source == edge.getVertexS())
                {
                    unsortedTree.remove(edge);
                }
            }
        }

        ArrayList<Vertex> newLevel = new ArrayList<>();
        while(shortestPathTree.size() != size)
        {
            for(int i = 0 ; i < unsortedTree.size(); i++)
            {
                Edge edge = unsortedTree.get(i);
                for(Vertex v: level)
                {
                    if (!shortestPathTree.contains(edge) && edge.getVertexS() == v)
                    {
                        shortestPathTree.add(edge);
                        newLevel.add(edge.getVertexE());
                    }
                    else if ((!shortestPathTree.contains(edge)) && (edge.getVertexE() == v))
                    {
                        Edge sortingEdge = new Edge(edge.getVertexE(), edge.getVertexS(), edge.getWeight());
                        shortestPathTree.add(sortingEdge);
                        unsortedTree.set(i, sortingEdge);
                        copyUnsortedTree.set(i, sortingEdge);
                        newLevel.add(edge.getVertexS());
                    }
                }
            }
            System.out.println();
            level = newLevel;
            newLevel = new ArrayList<>();

            for(Vertex vertex: level)
            {
                for(Edge edge: copyUnsortedTree)
                {
                    if(vertex == edge.getVertexE() && source == edge.getVertexS())
                    {
                        unsortedTree.remove(edge);
                    }
                }
            }
        }
        return shortestPathTree;
    }

    /**
     * The main method is used for testing functionality of the CliqueFinderAndBestAssociationFinder.BestAssociationFinder
     * @param args
     */
    public static void main(String[] args)
    {
        Random rand = new Random();
        System.out.println("---------------------------------------Test One------------------------------------------");

        double associationTables1[][] =
                {
                        {0.0, 0.5, 0.4, 0.0, 0.0, 0.0},
                        {0.5, 0.0, 0.0, 0.4, 0.0, 0.0},
                        {0.4, 0.0, 0.0, 0.3, 0.5, 0.0},
                        {0.0, 0.4, 0.3, 0.0, 0.8, 0.0},
                        {0.0, 0.0, 0.5, 0.8, 0.0, 0.7},
                        {0.0, 0.0, 0.0, 0.0, 0.7, 0.0}
                };

        Graph weightGraph1;
        BestAssociationFinder BAF1 = new BestAssociationFinder(associationTables1);
        BAF1.weightTable(associationTables1);
        weightGraph1 = BAF1.creatingGraph(BAF1.weights);
        System.out.println();
        BAF1.getShortestPathTree(weightGraph1.getVertices().get(0), weightGraph1.getVertices().get(5));

        System.out.println("---------------------------------------Test Two------------------------------------------");

        double associationTable2[][] = new double[20][20];
        for(int i = 0 ; i < 20 ; i ++)
        {
            for(int j = 0 ; j < 20 ; j++)
            {
                int number = rand.nextInt(4);
                if(number != 0) {
                    associationTable2[i][j] = rand.nextDouble();
                }
            }
        }

        Graph weightGraph2;
        BestAssociationFinder BAF2 = new BestAssociationFinder(associationTable2);
        BAF2.weightTable(associationTable2);
        weightGraph2 = BAF2.creatingGraph(BAF2.weights);
        System.out.println();
        BAF2.getShortestPathTree(weightGraph2.getVertices().get(1), weightGraph2.getVertices().get(19));
    }
}
package CliqueFinderAndBestAssociationFinder;

import CommunityClusterFinder.AssociationTable;

import java.util.ArrayList;

/**
 * The class CliqueFinderAndBestAssociationFinder.Graph is used to create a graph which consist of list of edges and vertices.
 *
 * @author Pulin Angurala & Raymond Li
 */
public class Graph
{
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Vertex> vertices = new ArrayList<>();

    public Graph()
    {}

    public Graph(AssociationTable table)
    {
        if (table.getAssociationTable().length != 0)
        {
            for (int i = 0; i < table.getSize(); i++)
            {
                addVertex(i);
            }
        }
    }

    /**
     * The method addEdges will be used to add the edges to the list of edge.
     *
     * @param start  the starting vertex.
     * @param end    the ending vertex.
     * @param weight the weight of the edge.
     */
    public void addEdges(Vertex start, Vertex end, Double weight)
    {
        edges.add(new Edge(start, end, weight));
    }

    /**
     * The method addVertex will be used to add the vertex to the list of vertex.
     *
     * @param vertex a vertex.
     */
    public void addVertex(double vertex)
    {
        vertices.add(new Vertex(vertex));
    }

    /**
     * The method getEdge is used to get the list of edges.
     *
     * @return a list of edge.
     */
    public ArrayList<Edge> getEdges()
    {
        return edges;
    }

    /**
     * The method getVertices will be used to get the list of vertices
     *
     * @return a list of vertex
     */
    public ArrayList<Vertex> getVertices()
    {
        return vertices;
    }
}
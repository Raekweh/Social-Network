package CommunityClusterFinder;

import java.util.ArrayList;

/**
 * The Graph Class is used to create a graph which consist of list of edges and vertices.
 *
 * @author Pulin Angurala & Raymond Li
 */
public class Graph {
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
    public void addVertex(int vertex)
    {
        vertices.add(new Vertex(vertex));
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
}
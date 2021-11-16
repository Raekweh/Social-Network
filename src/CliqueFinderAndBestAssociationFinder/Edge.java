package CliqueFinderAndBestAssociationFinder;

/**
 * The class CliqueFinderAndBestAssociationFinder.Edge is used to create an edge between two vertices. The edge will have a weight which consist of a
 * starting edge and an ending edge.
 *
 * @author Pulin Angurala & Raymond Li
 */
public class Edge
{
    private double weight;
    private Vertex vertexS, vertexE;

    /**
     * This edge constructor is used for creating edges that do not have a weight such as the clique finder. The
     * incident edges will be created for this instance for both starting and ending vertex. The neighbours of the
     * starting and ending vertex will also be created by passing its opposite/adjacent vertex.
     *
     * @param vertexS the starting vertex or vertex 1.
     * @param vertexE the ending vertex or vertex 2.
     */
    public Edge(Vertex vertexS, Vertex vertexE)
    {
        this.vertexS = vertexS;
        this.vertexE = vertexE;
        vertexS.incidentEdges(this);
        vertexE.incidentEdges(this);
        vertexS.creatingNeighbours(vertexE);
        vertexE.creatingNeighbours(vertexS);
    }

    /**
     * This edge constructor is use for creating edges that do have weights such as the Best association finder. The
     * incident edges will be created for this instance for both starting and ending vertex.
     *
     * @param vertexS the starting vertex or vertex 1.
     * @param vertexE the ending vertex or vertex 2.
     * @param weight  the weight of the edge between vertex 1 and vertex 2.
     */
    public Edge(Vertex vertexS, Vertex vertexE, double weight)
    {
        this.vertexS = vertexS;
        this.vertexE = vertexE;
        vertexS.incidentEdges(this);
        vertexE.incidentEdges(this);
        this.weight = weight;
    }

    /**
     * The method getWeight is used to get the weight of the edge between two vertices.
     *
     * @return a double representing the weight of the edge.
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * The method getVertexS is used to get the starting vertex.
     *
     * @return the starting vertex.
     */
    public Vertex getVertexS()
    {
        return vertexS;
    }

    /**
     * The method getVertexE is used to get the ending vertex.
     *
     * @return the ending vertex.
     */
    public Vertex getVertexE()
    {
        return vertexE;
    }

    /**
     * The method opposite vertex is used to get the opposite or adjacent vertex.
     *
     * @param vertex a vertex.
     * @return the opposite vertex/adjacent vertex.
     */
    public Vertex oppositeVertex(Vertex vertex)
    {
        if (vertex == vertexS) {
            return vertexE;
        } else if (vertex == vertexE) {
            return vertexS;
        }
        return null;
    }

    /**
     * The method toString is used to return a string representing the components used in the CliqueFinderAndBestAssociationFinder.Edge class.
     *
     * @return a string of components.
     */
    @Override
    public String toString()
    {
        return "Edge{" + "weight=" + weight + ", "+ vertexS +","+vertexE + '}';
    }
}
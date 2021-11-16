package CliqueFinderAndBestAssociationFinder;

import java.util.ArrayList;

/**
 * The class vertex is used to create a vertex.
 *
 * @author Pulin Angurala & Raymond Li
 */
public class Vertex
{
    private ArrayList<Edge> incidentEdges;
    private double number;
    private ArrayList<Vertex> neighbours;
    private int degree;

    public Vertex(double number)
    {
        incidentEdges = new ArrayList<Edge>();
        neighbours = new ArrayList<>();
        this.number = number;
    }

    /**
     * The method getNumber is used to get the number of the vertex.
     *
     * @return a double representing the vertex.
     */
    public double getNumber()
    {
        return number;
    }

    /**
     * The method incidentEdges is used to add the incident edges into a list
     *
     * @param edge a edge.
     */
    public void incidentEdges(Edge edge)
    {
        incidentEdges.add(edge);
    }

    /**
     * The method getIncidentEdges is used to get the list of incident edges
     *
     * @return a list of edges
     */
    public ArrayList<Edge> getIncidentEdges()
    {
        return incidentEdges;
    }

    /**
     * The method getNeighbours is used to get the list of neighbours.
     *
     * @return a list of vertex
     */
    public ArrayList<Vertex> getNeighbours()
    {
        return neighbours;
    }


    /**
     * The method getDegree is used to ge the degree of a vertex
     *
     * @return an integer that represents the degree
     */
    public int getDegree()
    {
        return degree;
    }

    /**
     * The method creatingNeighbour is used to create a list of neighbours of the vertex which will also calculate the
     * degree of the vertex.
     *
     * @param vertex a vertex
     */
    public void creatingNeighbours(Vertex vertex)
    {
        this.neighbours.add(vertex);
        this.degree++;
    }

    /**
     * The method toString is used to return a string representing the components of the CliqueFinderAndBestAssociationFinder.Vertex class
     *
     * @return a string of components
     */
    @Override
    public String toString()
    {
        return "Vertex{"+ number + '}';
    }
}
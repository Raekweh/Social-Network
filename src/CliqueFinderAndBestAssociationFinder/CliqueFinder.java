package CliqueFinderAndBestAssociationFinder;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to find the largest clique social network. The clique finder will take in a n x n association
 * table. The clique finder will use the n x n association table to find the maximal cliques in an undirected graph by
 * using the Bron-Kerbosch algorithm. The Bron-Kerbosh uses recursion to grow the clique R of vertices by adding one
 * CliqueFinderAndBestAssociationFinder.Vertex to R from the set of vertices of P. Maintaining X of vertices  is no longer considered.
 *
 * @author Pulin Angurala & Raymond Li
 */
public class CliqueFinder
{
    double[][] associationTables;

    public CliqueFinder(double[][] associationTables)
    {
        this.associationTables = associationTables;
    }

    /**
     * The method intersection is used to find all the elements that are common in both list provided.
     * If the elements are not common or the same within the list provided then they are not part of the intersection.
     *
     * @param firstAL  The first list.
     * @param secondAL The second list.
     * @return a list of vertex that are intersecting.
     */
    public ArrayList<Vertex> intersection(ArrayList<Vertex> firstAL, ArrayList<Vertex> secondAL)
    {
        ArrayList<Vertex> holdAL = new ArrayList<>();
        for (Vertex firstList : firstAL)
        {
            for (Vertex secondList : secondAL)
            {
                if (firstList.getNumber() == secondList.getNumber())
                {
                    holdAL.add(firstList);
                }
            }
        }
        return holdAL;
    }

    /**
     * The method Union is used to find all elements that are contained within both list provided.
     * If the elements are repeating then the element will only be considered once.
     *
     * @param firstAL  The first list.
     * @param secondAL The second list.
     * @return a list of vertex that are union.
     */
    public ArrayList<Vertex> union(ArrayList<Vertex> firstAL, ArrayList<Vertex> secondAL)
    {
        ArrayList<Vertex> holdAL = new ArrayList<>(firstAL);
        for (Vertex secondList : secondAL)
        {
            if (!holdAL.contains(secondList))
            {
                holdAL.add(secondList);
            }
        }
        return holdAL;
    }

    /**
     * The method printClique is used to print out all the maximum cliques
     *
     * @param R the list R
     */
    public void printClique(ArrayList<Vertex> R)
    {
        System.out.println(" The maximal clique is: ");
        for (Vertex v : R)
        {
            System.out.println(" " + v);
        }
        System.out.println();
    }

    /**
     * The method getHighestDegreeVertex is used to find the vertex with the highest degree
     *
     * @param g a list
     * @return a vertex with the highest degree of vertex
     */
    public Vertex getHighestDegreeVertex(ArrayList<Vertex> g)
    {
        Vertex maxVertex = g.get(0);
        for (int i = 1; i < g.size(); i++)
        {
            if (maxVertex.getDegree() < g.get(i).getDegree())
            {
                maxVertex = g.get(i);
            }
        }
        return maxVertex;
    }

    /**
     * The method bronKerbosch implmenets the Bron-Kerosch algorithm.
     * The Bron-Kerosch algorithm consist of three list R, P and X. The algorithm will check if list P and X are empty.
     * It will then proceed to get the pivot vertex. The pivot vertex will be used to remove the pivot vertex neighbours
     * from the list P. The Bron-Kerosch uses recursion to get all the maximum cliques by iteration through the list P.
     *
     * @param R a clique list of vertices
     * @param P a clique list of vertices
     * @param X a clique list of vertices
     */
    public void bronKerbosch(ArrayList<Vertex> R, ArrayList<Vertex> P, ArrayList<Vertex> X)
    {
        if ((P.size() == 0) && (X.size() == 0))
        {
            printClique(R);
            return;
        }

        ArrayList<Vertex> P1 = new ArrayList<>(P);
        //Pivot point
        Vertex U = getHighestDegreeVertex(union(P, X));

        for (Vertex uNbr : U.getNeighbours())
        {
            P.remove(uNbr);
        }

        for (Vertex v : P)
        {
            R.add(v);
            bronKerbosch(R, intersection(P1, v.getNeighbours()), intersection(X, v.getNeighbours()));
            R.remove(v);
            P1.remove(v);
            X.add(v);
        }
    }

    /**
     * The main method is used for testing functionality of the CliqueFinderAndBestAssociationFinder.CliqueFinder
     *
     * @param args
     */
    public static void main(String[] args) //Need to check if its correct
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
        CliqueFinder CFT1 = new CliqueFinder(associationTables1);
        ArrayList<Vertex> X = new ArrayList<>();
        ArrayList<Vertex> P = new ArrayList<>();
        ArrayList<Vertex> R = new ArrayList<>();

        //Populating the vertex
        for (int i = 0; i < associationTables1.length; i++)
        {
            P.add(new Vertex(i));
        }
        //Populating the edges
        for (int j = 0; j < associationTables1.length; j++)
        {
            for (int k = 0; k < associationTables1.length; k++)
            {
                if (j < k && associationTables1[j][k] != 0)
                {
                    Edge edge = new Edge(P.get(j), P.get(k));
                }
            }
        }
        CFT1.bronKerbosch(R, P, X);

        System.out.println("---------------------------------------Test Two------------------------------------------");
        double AssociationTable2[][] = new double[20][20];
        for(int i = 0 ; i < 20 ; i ++)
        {
            for(int j = 0 ; j < 20 ; j++)
            {
                int number = rand.nextInt(4);
                if(number != 0) {
                    AssociationTable2[i][j] = rand.nextDouble();
                }
            }
        }

        CliqueFinder CFT2 = new CliqueFinder(AssociationTable2);
        X = new ArrayList<>();
        P = new ArrayList<>();
        R = new ArrayList<>();

        for (int i = 0; i < AssociationTable2.length; i++)
        {
            P.add(new Vertex(i));
        }

        for (int j = 0; j < AssociationTable2.length; j++)
        {
            for (int k = 0; k < AssociationTable2.length; k++)
            {
                if (j < k && AssociationTable2[j][k] != 0)
                {
                    Edge edge = new Edge(P.get(j), P.get(k));
                }
            }
        }
        CFT2.bronKerbosch(R, P, X);
    }
}
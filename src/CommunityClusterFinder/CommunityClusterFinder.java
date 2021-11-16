package CommunityClusterFinder;
import java.util.ArrayList;
import java.util.Random;

/**
 * The CommunityClusterFinder Class is used to form community
 * clusters from a given graph. This class performs complete
 * linkage clustering to form a dendrogram of clusters.
 * @Author Raymond Li & Pulin Angurala
 */
public class CommunityClusterFinder {
    public static ArrayList<Vertex<Integer>> visted = new ArrayList<>();
    public static ArrayList<Edge<Integer>> smallestRoutes = new ArrayList<>();

    public static void main(String[] args) {
        testCaseGenerator(5);
        testCaseGenerator(10);
        testCaseGenerator(15);
    }


    /**
     * The findCommunityClusters method is used to create and merge clusters
     * within a graph by recalculating proximity tables and distances.
     * @param g is passed a graph object that contains associated vertices.
     * @param associationTable is passed an association table with the distances between
     *                         vertices.
     */
    public static void findCommunityClusters(Graph g, AssociationTable associationTable) {
        ClusterSet<Integer> clusterSet = new ClusterSet<Integer>();
        AllPairsFloydWarshall allPairsFloydWarshall = new AllPairsFloydWarshall(associationTable.getWeights());
        double[][] shortestPaths = allPairsFloydWarshall.getArray();
        double[][] proximityTable;
        ArrayList<Vertex<Integer>> vertices = new ArrayList<>();
        double smallestDistance = Integer.MAX_VALUE;
        int vI;
        int vJ;

        for (int i = 0; i < g.getVertices().size(); i++) {
            clusterSet.makeSet((int) g.getVertices().get(i).getElement());
        }

        System.out.println("Initial Set:\n"+clusterSet+" - Size: "+clusterSet.getSize());
        System.out.println("\nFinding Clusters...");

        for (Integer e: clusterSet.getKeys()){
            vertices.add(new Vertex<>(e));
        }

        while(clusterSet.getSize() != 1){
            proximityTable = new double[clusterSet.getSize()][clusterSet.getSize()];

            vI = -1;
            vJ = -1;

           for (int i = 0; i < g.getVertices().size(); i++){
               for (int j = 0; j < g.getVertices().size(); j++){
                   Vertex<Integer> v1 = new Vertex<>(clusterSet.findSet(i));
                   Vertex<Integer> v2 = new Vertex<>(clusterSet.findSet(j));

                   if (v1.getElement() != null && v2.getElement() != null) {
                       if (!v1.getElement().equals(v2.getElement())) {

                           double d = shortestPaths[v1.getElement()][v2.getElement()];

                           if (d > proximityTable[indexOf(vertices, v1)][indexOf(vertices, v2)]) {
                               proximityTable[indexOf(vertices, v1)][indexOf(vertices, v2)] = d;

                               if (d < smallestDistance || ((vI == indexOf(vertices, v1)
                                       && vJ == indexOf(vertices, v2) ) || (vI == indexOf(vertices, v2)
                                       && vJ == indexOf(vertices, v1) ))) {
                                   smallestDistance = d;
                                   vI = v1.getElement();
                                   vJ = v2.getElement();
                               }
                           }
                       }
                   }
               }
           }

           if (vI > -1 && vJ >-1) {
               clusterSet.union(vI, vJ);
               vertices.clear();

               for (Integer e: clusterSet.getKeys()){
                   vertices.add(new Vertex<>(e));
               }

               System.out.println("\n"+clusterSet+" - Size: "+clusterSet.getSize());
               System.out.println("Clusters "+vI+" and "+vJ+" have been merged as they have the smallest\n" +
                       "distance of "+smallestDistance+" in this cluster set");
               smallestDistance = Integer.MAX_VALUE;
           }
        }
        System.out.println("------------------------------------------------------------------\n");
    }

    /**
     * The printArray method is used to print a given 2D array
     * in the form a grid / matrix.
     * @param array is passed a 2D array of double type numbers.
     */
    public static void printArray(double[][] array){
        String s = "";

        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array.length; j++){
                s+= array[i][j]+" ";
            }
            s+= "\n";
        }

        System.out.println(s);
    }

    /**
     * The indexOf method returns the index of the representative
     * of a cluster.
     * @param vertices is passed the vertices present in the cluster set.
     * @param v is passed the vertex object to find the index of.
     * @return the index of the vertex object passed as the argument.
     */
    public static int indexOf(ArrayList<Vertex<Integer>> vertices, Vertex<Integer> v){
        for (int i = 0; i  < vertices.size(); i++){
            if (vertices.get(i).getElement().equals(v.getElement())){
                return i;
            }
        }
        return -1;
    }


    /**
     * The testCaseGenerator method is used to generate random NxN
     * association tables for simulating the community cluster finding
     * activity.
     * @param size
     */
    public static void testCaseGenerator(int size){
        double[][] associationTable = new double[size][size];

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (i == j){
                    associationTable[i][j] = 0;
                }
                else{
                    int pass = new Random().nextInt(2);
                    if (pass == 0) {
//                        double value = new Random().nextDouble();

                        double value = (Math.round(new Random().nextDouble() * 1000.0) / 1000.0);

                        associationTable[j][i] = value;
                        associationTable[i][j] = value;
                    }
                }
            }
        }

        System.out.println("Association table for random generated "+size+"x"+size+" vertices");
        printArray(associationTable);

        AssociationTable table = new AssociationTable(associationTable);
        Graph g = new Graph(table);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table.getAssociationTable()[i][j] != 0) {
                    g.addEdges(g.getVertices().get(i), g.getVertices().get(j),
                            table.getAssociationTable()[i][j]);
                }
            }
        }

        findCommunityClusters(g, table);
    }
}
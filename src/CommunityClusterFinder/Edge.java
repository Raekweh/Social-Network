package CommunityClusterFinder;

/**
 * The Edge class is used to edge links between two vertices
 * @param <E> is passed a Vertex element type.
 * @Author Raymond Li & Pulin Angurala
 */
public class Edge<E> {
    private Vertex<E> v1, v2;
    private double weight;

    public Edge(Vertex<E> v1, Vertex<E> v2, double weight){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public Vertex<E> getV1() {
        return v1;
    }

    public Vertex<E> getV2() {
        return v2;
    }

    public String toString(){
        return "("+v1+", "+v2+")"+": "+weight;
    }
}
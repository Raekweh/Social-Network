package CommunityClusterFinder;

/**
 * The Vertex class is used to create vertex points
 * in a graph.
 * @param <E> is passed the generic element type for a vertex
 * @Author Raymond Li & Pulin Angurala
 */
public class Vertex<E>{
    private E element;

    public Vertex(E element){
        this.element = element;
    }

    public String toString(){
        return element.toString();
    }

    public E getElement() {
        return element;
    }
}
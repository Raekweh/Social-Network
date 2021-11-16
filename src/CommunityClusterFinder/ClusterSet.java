package CommunityClusterFinder;

import java.util.HashMap;
import java.util.HashSet;

/**
 * the ClusterSet class is used to create a disjoint set
 * of clusters for a given graph.
 * @param <E>
 * @Author Raymond Li & Pulin Angurala
 */
public class ClusterSet<E> implements DisjointSetsADT<E> {
    private HashMap<E, HashSet<E>> map;
    private int size;

    public ClusterSet(){
        map = new HashMap<>();
        size = 0;
    }

    /**
     Creates a new set containing just the element x where x is
     presumed not to be in any set in the collection
     @param x The element to place in the set
     @return A representative of the set (must be x)
     */
    @Override
    public E makeSet(E x) {
        HashSet<E> set = new HashSet<>();
        set.add(x);
        map.put(x, set);
        size++;
        return x;
    }

    /**
     Forms the union of the sets which currently contain the
     elements x and y
     @param x, y Elements in each set to union (merge) together
     @return A representative of the set
     */
    @Override
    public E union(E x, E y) {
        HashSet<E> unionSet = map.get(x);
        HashSet<E> ySet = map.get(y);
        unionSet.add(y);

        if (ySet != null) {
            while (!ySet.isEmpty()) {
                unionSet.add(ySet.iterator().next());
                ySet.remove(ySet.iterator().next());
            }
        }

        map.remove(y);
        size--;
        return x;
    }

    /**
     * The getKeys method is used to return a set of
     * all the keys contained in the disjoint set.
     * @return the set of keys contained in this set.
     */
    public HashSet<E> getKeys(){
        HashSet<E> set = new HashSet<>();
        set.addAll(map.keySet());
        return set;
    }

    /**
     Returns a representative of the set which currently contains x
     @param x The element in the set
     @return A representative of the set
     */
    @Override
    public E findSet(E x){
        for (E e: map.keySet()){
            HashSet<E> set = map.get(e);
            if (set.contains(x) || e.equals(x)){
                return e;
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    public String toString(){
        return map.toString();
    }
}
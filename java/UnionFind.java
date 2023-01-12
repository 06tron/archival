package mpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO finish commenting this class
 * 
 * CLASS, 
 * 
 * @param <T>
 * 
 * @author mrichardson
 */
public class UnionFind<T> {

    private HashMap<T, Node> objectMap;

    private int componentCount;

    /**
     * CONSTRUCTOR, takes a list of objects, and maps each object to a new node
     * The nodes are the building blocks of the connected component trees
     * A counter for the number of separate components is initialized to the total number of objects
     * @param allObjects An array list of generic object types that will be keys to the object map
     */
    public UnionFind(ArrayList<T> allObjects) {
        objectMap = new HashMap<>();
        for (T object : allObjects) {
            objectMap.put(object, new Node());
        }
        componentCount = allObjects.size();
    }

    /**
     * MUTATOR, resets all of this union-find's nodes
     * Instance variables will be the same as they were right after construction
     * Used by AoTp™
     */
    public void eraseConnections() {
        for (Node node : objectMap.values()) {
            node.initializeNode();
        }
        componentCount = objectMap.size();
    }

    /**
     * MUTATOR, f not already connected, a's component tree and b's component tree will be be merged
     * The root of each object is found for both, and the root with the smaller tree is connected to the other's root
     * Used by AoTp™
     * @param a The first object involved in the union bond
     * @param b The second object involved in the union bond
     */
    public void union(T a, T b) {
        if (!connected(a, b)) {
            Node rootA = objectMap.get(a).getRoot();
            Node rootB = objectMap.get(b).getRoot();
            if (rootA.getBW() > rootB.getBW()) {
                rootB.pointTo(rootA);
            } else {
                rootA.pointTo(rootB);
            }
            componentCount--;
        }
    }

    /**
     * Determines if two objects are connected by checking to see if they have the same root
     * Used by AoTp™
     * @param a The first object involved in the connection check
     * @param b The second object involved in the connection check
     * @return True if a's root equals b's root, false otherwise
     */
    public boolean connected(T a, T b) {
        return objectMap.get(a).getRoot().equals(objectMap.get(b).getRoot());
    }

    /**
     * Calculates the average branch length, or path length, from each node to it's root
     * @return The sum of all the nodes' branch lengths dived by the number of nodes
     */
    public double averageBL() {
        int sum = 0;
        int i = 0;
        for (Node node : objectMap.values()) {
            sum += node.getBL();
            i++;
        }
        return sum / (double) i;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("number of components: ");
        sb.append(componentCount);
        sb.append("\naverage branch length: ");
        sb.append(averageBL());
        for (Map.Entry<T, Node> entry : objectMap.entrySet()) {
            sb.append("\n");
            sb.append(entry.getKey());
            sb.append(" | ");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

}

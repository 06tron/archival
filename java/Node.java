package mpc;

/**
 * CLASS, represents a tree's node. Designed for a connected component tree of a union-find object
 * 
 * @author mrichardson
 */
public class Node {

	/**
	 * VARIABLE, stores the total number of nodes created, so that each node has a
	 * unique id number
	 */
	private static int totalNodes = 0;

	/**
	 * VARIABLE, the id number of this node. Only used by this class's toString()
	 * method as a form of identification
	 */
	private final int ID;

	/**
	 * VARIABLE, true if this node has no parent node, false otherwise
	 */
	private boolean isRoot;

	/**
	 * VARIABLE, the node above this node in a tree, may be null
	 */
	private Node parent;

	/**
	 * VARIABLE, The number of nodes on the connected component tree below and
	 * including this node
	 */
	private int branchWeight;

	/**
	 * CONSTRUCTOR, each node is assigned a unique id so that the nth instance of
	 * Node will have an id of n. isRoot is set to true, the parent remains
	 * uninitialized, and branchWeight is set to 1
	 */
	public Node() {
		ID = ++totalNodes;
		initializeNode();
	}

	/**
	 * MUTATOR, Sets all of this node's instance variables, except for parent, which
	 * is initialized by pointTo(). A node starts with no connections, so it is the
	 * root of its own tree with branch weight 1
	 */
	public void initializeNode() {
		parent = null;
		isRoot = true;
		branchWeight = 1;
	}

	/**
	 * MUTATOR, Assigns the parent value for this node. A node with a parent is not
	 * the tree's root. The target node's branch weight is set to the sum of the two
	 * node's branch weights
	 * 
	 * @param target The node that will become this node's parent
	 */
	public void pointTo(Node target) {
		parent = target;
		isRoot = false;
		target.branchWeight += branchWeight;
	}

	/**
	 * ACCESSOR & MUTATOR, Calls the getRoot() function for each node on the path
	 * from this node to its tree's root. Each accessed node will now point to the
	 * root, in order to shorten paths for the future
	 * 
	 * @return The root of the connected component tree this node is a part of
	 */
	public Node getRoot() {
		if (isRoot) {
			return this;
		} else {
			Node root = parent.getRoot();
			return parent = root;
		}
	}

	/**
	 * ACCESSOR, Returns the size of the connected component tree branch past and
	 * including this node
	 * 
	 * @return The total number of nodes that are or eventually point to this node
	 */
	public int getBW() {
		return branchWeight;
	}

	/**
	 * ACCESSOR, finds the length of the path from this node to its tree's root.
	 * This method does not edit the nodes' parents because getBL() is used only for
	 * tree analysis
	 * 
	 * @return The number of moves between nodes required to get from this node to
	 *         its root
	 */
	public int getBL() {
		int length = 0;
		if (!isRoot) {
			length += 1 + parent.getBL();
		}
		return length;
	}

	@Override
	public String toString() {
		if (isRoot) {
			return Integer.toString(ID, Character.MAX_RADIX);
		} else {
			return Integer.toString(ID, Character.MAX_RADIX) + " -> " + parent;
		}
	}

}

import java.util.ArrayList;

/**
 * A graph node that contains references to it's out-bound edge nodes.
 * <p>
 * Use {@linkplain #addOutEdge(Node)} to add an out-bound edge node and
 * {@linkplain #getOutEdges()} to retrieve said out-bound edge nodes.
 * 
 * @author Jonathan A. Henly
 * @version 1.00 2015-03-24
 **/
public class Node {
	// private member variables
	private String id;
	private ArrayList<Node> outEdges;

	/**
	 * Default constructor.
	 **/
	public Node() {
		this.id = "";
		this.outEdges = new ArrayList<Node>();
	}

	/**
	 * Overloaded constructor that sets the id.
	 * 
	 * @param id
	 *            - string representation of this <code>Node</code> instance's
	 *            id.
	 **/
	public Node(String id) {
		this();
		this.id = id;
	}

	/**
	 * Get this <code>Node</code> instance's id.
	 * 
	 * @return string representing this <code>Node</code>'s id.
	 **/
	public String getId() {
		return this.id;
	}

	/**
	 * Get the number of nodes that this node is pointing to.
	 * 
	 * @return int representing the number of out-bound edges.
	 **/
	public int getOutEdgeCount() {
		return this.outEdges.size();
	}

	/**
	 * Adds a <code>Node</code> instance to this <code>Node</code> instance's
	 * collection of out-bound edge nodes.
	 * 
	 * @param node
	 *            - the <code>Node</code> instance to add.
	 **/
	public void addOutEdge(Node node) {
		this.outEdges.add(node);
	}

	/**
	 * Gets this instance's collection of out-bound edge nodes.
	 * 
	 * @return a <code>Node</code> array containing this instance's collection
	 *         of edge nodes.
	 **/
	public Node[] getOutEdges() {
		return this.outEdges.toArray(new Node[this.outEdges.size()]);
	}

	@Override
	public int hashCode() {
		// return the immutable member string's hash-code
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// if the object is compared with itself then return true
		if (obj == this) {
			return true;
		}

		// check if obj is an instance of Node or not
		// "null instanceof [type]" also returns false
		if (!(obj instanceof Node)) {
			return false;
		}

		// typecast obj to Complex so that we can compare data members
		Node that = (Node) obj;

		// compare the id strings and return accordingly
		return this.getId().equals(that.getId());
	}

	@Override
	public String toString() {
		return this.id;
	}

}
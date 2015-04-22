import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * The <code>DiGraph</code> class represents a directed graph.
 * <p>
 * The {@link #parseData} function parses data into a <code>DiGraph</code>
 * object. Once the data has been parsed, use the {@link #topSort} function to
 * topologically sort the directed graph. After the graph has been topologically
 * sorted use the {@link #topSortToString} function to retrieve a
 * <code>String</code> representation of the topologically sorted graph.
 * 
 * @author Jonathan A. Henly
 * @version 1.00 2015-03-24
 **/
public class DiGraph {
	// private member variables
	private HashMap<String, Node> theNodes = null;
	private HashMap<Node, Integer> graph = null;
	private Queue<Node> zeroEdged = null;
	private ArrayList<Node> sortedNodes = null;
	private String graphString;

	/**
	 * Default constructor.
	 **/
	public DiGraph() {
		this.theNodes = new HashMap<String, Node>();
		this.graph = new HashMap<Node, Integer>();
		this.zeroEdged = new LinkedList<Node>();
		this.sortedNodes = new ArrayList<Node>();
		this.graphString = "[Empty Graph]";
	} // Graph()

	/**
	 * Parses CSV (Comma Separated Variable) data, representing a directed
	 * graph, into a <code>Graph</code> object.
	 * <p>
	 * The format of the data should resemble the following -
	 * <p>
	 * Data:
	 * <ul>
	 * {@code D,A,B}<br>
	 * {@code   A,B}<br>
	 * {@code   B,C}
	 * </ul>
	 * Meaning:
	 * <ul>
	 * {@code D} points to {@code A}<br>
	 * {@code D} points to {@code B}<br>
	 * {@code A} points to {@code B}<br>
	 * {@code B} points to {@code C}
	 * </ul>
	 * Graph:
	 * <ul>
	 * <code>D---B---C</code><br>
	 * <code>&nbsp;\&nbsp;/</code><br>
	 * <code>&nbsp;&nbsp;A</code> <br>
	 * note: left points to right
	 * </ul>
	 * 
	 * @param data
	 *            - <code>Scanner</code> object containing the data to be
	 *            parsed.
	 **/
	public void parseData(Scanner data) {
		// reset the #toString string
		this.graphString = "";

		// keep reading data until there's no more
		while (data.hasNext()) {
			// pass one line of data to the helper function #parseLine
			this.parseLine(data.nextLine());
		}
		data.close();

		// iterate over the collection of nodes, adding nodes with zero in-bound
		// edges to the queue of zero in-bound edge nodes
		for (Node node : this.theNodes.values()) {
			if (this.graph.get(node) == 0) {
				this.zeroEdged.add(node);
			}
		}
	} // void parseData(Scanner data)

	/**
	 * Private helper function used by {@linkplain #parseData}.
	 * 
	 * @param line
	 *            - one line of a string to parse.
	 **/
	private void parseLine(String line) {
		// declare local variables
		Scanner parser = new Scanner(line);
		Node firstNode = null;

		// update the #toString string
		this.graphString += line + "\n";

		// set the delimiter to equal one or more spaces, commas, carriage
		// returns or new lines
		parser.useDelimiter("[\\s,\r\n]+");

		// parse the first node in the string
		if (parser.hasNext()) {
			firstNode = this.getAddNode(parser.next());
		}

		// parse the remaining nodes and add an out-bound edge node
		// reference to the first node for each
		while (parser.hasNext()) {
			Node adjNode = this.getAddNode(parser.next());
			firstNode.addOutEdge(adjNode);

			// add one to the out-bound edge node's in-bound count
			this.graph.put(adjNode, this.graph.get(adjNode) + 1);
		}

		// close the scanner
		parser.close();
	} // void parseLine(String line)

	/**
	 * If the <code>Node</code> being requested is not in the collection of
	 * already created <code>Node</code>s, then <code>getAddNode</code> will
	 * create a new </code>Node</code>, add it to the collection of already
	 * created <code>Node</code>s and add it to the graph with an in-bound edge
	 * count of zero.
	 * 
	 * @param which
	 *            - string that equals the id of the <code>Node</code> to
	 *            retrieve.
	 * @return the <code>Node</code> with an id that equals the parameter
	 *         string.
	 **/
	private Node getAddNode(String which) {
		/*
		 * If the requested node exists then return it. If it doesn't exist then
		 * create a new node and add it to both the all-nodes HashMap and the
		 * graph HashMap, and return it.
		 */
		if (this.theNodes.containsKey(which)) {
			return this.theNodes.get(which);
		} else {
			Node node = new Node(which);
			this.theNodes.put(which, node);
			this.graph.put(node, 0);
			return node;
		}
	} // Node getAddNode(String which)

	/**
	 * Topologically sorts this graph using a linear algorithm.
	 * <p>
	 * The algorithm uses a {@link HashMap} is used to decrement the number of
	 * in-bound edges in <code>O(1)</code> also, a {@link Queue} is used to
	 * queue the nodes with zero in-bound edges.
	 * <p>
	 * <strong>Topological Sort Example:</strong>
	 * <ul>
	 * Graph:
	 * <ul>
	 * <code>D---B---C</code><br>
	 * <code>&nbsp;\&nbsp;/</code><br>
	 * <code>&nbsp;&nbsp;A</code> <br>
	 * note: left points to right
	 * </ul>
	 * Sorted Order:
	 * <ul>
	 * <code>D,A,B,C;</code>
	 * </ul>
	 * </ul>
	 * 
	 * @see {@linkplain HashMap}
	 * @see {@linkplain Queue}
	 **/
	public void topSort() {
		// declare local variables
		Node current = null;

		/*
		 * Continue enqueueing zero in-bound edge nodes and decrementing their
		 * out-bound edge nodes in-bound values.
		 */
		while (!this.zeroEdged.isEmpty()) {
			current = this.zeroEdged.poll();
			this.sortedNodes.add(current);

			/*
			 * Iterate through the current zero in-bound edge node's out-bound
			 * edge nodes decrementing their HashMap in-bound edge value. If the
			 * out-bound edge node's in-bound edge value equals zero then
			 * enqueue that node.
			 */
			for (Node edgeNode : current.getOutEdges()) {
				int edgeCount = this.graph.get(edgeNode) - 1;
				this.graph.put(edgeNode, edgeCount);

				if (edgeCount == 0) {
					this.zeroEdged.add(edgeNode);
				}
			}

		}

	} // void topSort()

	/**
	 * If the graph can be topologically sorted, then a string representation of
	 * the graph's topological sort is returned. If the graph cannot be
	 * topologically sorted, then
	 * <em>"There isn't a topological ordering for this graph."</em> &nbsp;is
	 * returned.
	 * <p>
	 * <strong>Example:</strong>
	 * <ul>
	 * Graph:
	 * <ul>
	 * <code>D---B---C</code><br>
	 * <code>&nbsp;\&nbsp;/</code><br>
	 * <code>&nbsp;&nbsp;A</code><br>
	 * note: left points to right
	 * </ul>
	 * Returned String:
	 * <ul>
	 * <code>D,A,B,C;</code>
	 * </ul>
	 * </ul>
	 * 
	 * @return string representation of a <code>Graph</code> object.
	 **/
	public String topSortToString() {
		// declare local variables
		String s = "";

		// if the amount of sorted nodes does not equal the total amount of
		// nodes then this graph is not an acyclic digraph
		if (this.sortedNodes.size() != this.theNodes.size()) {
			s = "There isn't a topological ordering for this graph.";
		} else {
			for (int i = 0; i < this.sortedNodes.size(); i++) {
				s += this.sortedNodes.get(i).toString();
				s += (i < this.sortedNodes.size() - 1) ? ',' : "";
			}
			s += ';';
		}

		return s;
	} // String topSortToString()

	/**
	 * Returns a string of the input used to create the <code>Graph</code>
	 * object.
	 * <p>
	 * <strong>Example:</strong>
	 * <ul>
	 * D,A,B<br>
	 * A,B<br>
	 * B,C<br>
	 * </ul>
	 * 
	 * @return string representation of a <code>Graph</code> object.
	 **/
	@Override
	public String toString() {
		return this.graphString;
	} // String toString()

}

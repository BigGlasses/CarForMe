package objects;

import java.util.ArrayList;

/**
 *  Node
 * @author Brandon
 *
 */
public abstract class Node {
	private ArrayList<Node> connections;
	protected final String name;
	/**
	 * Constructs a Node with an identifying String.
	 * @param s
	 */
	public Node(String s) {
		name = s;
		connections = new ArrayList<Node>();
	}
	
	/**
	 * Accessor method for the Node's name.
	 * @return
	 */
	public String getName() {
		return name;
	}


	/**
	 * Points this node to another node.
	 * @param f
	 */
	public void addConnection(Node f) {
		if (!connections.contains(f)) {
			connections.add(f);
		}
	}

	/**
	 * Removes a connection from this Node.
	 * @param f
	 */
	public void removeConnection(Node f) {
		if (!connections.contains(f)) {
			connections.remove(f);
		}
	}

	/**
	 * Returns a list of all the connections.
	 * @return 
	 */
	public ArrayList<Node> getConnections() {
		return connections;
	}
}

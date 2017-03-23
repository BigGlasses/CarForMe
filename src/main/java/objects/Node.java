package objects;

import java.util.ArrayList;

public abstract class Node {
	private ArrayList<Node> connections;
	protected final String name;

	public Node(String s) {
		name = s;
		connections = new ArrayList<Node>();
	}
	
	public String getName() {
		return name;
	}


	public void addConnection(Node f) {
		if (!connections.contains(f)) {
			connections.add(f);
		}
	}

	public void removeConnection(Node f) {
		if (!connections.contains(f)) {
			connections.remove(f);
		}
	}

	public ArrayList<Node> getConnections() {
		return connections;
	}
}

package objects;

import java.util.ArrayList;

public class FieldNode extends Node implements Comparable {
	public final int maxDepth = 3;

	public FieldNode(String s) {
		super(s.toLowerCase());

	}
	
	/**
	 * Compares the name's of the FieldNodes
	 * @param f
	 * @return
	 */
	public int compareTo(FieldNode f) {
		return this.name.compareTo(f.getName());
	}

	/**
	 * Checks if the String is equal to this Node's identifier.
	 * @param f
	 * @return
	 */
	public boolean equals(FieldNode f) {
		return this.name.equals(f.getName());
	}

	/**
	 * Checks if the String is equal to this Node's identifier.
	 * @param f
	 * @return
	 */
	public boolean equals(String s) {
		return this.name.equals(s.toLowerCase());
	}

	@Override
	public int compareTo(Object o) {
		return this.compareTo((FieldNode) o);
	}
}

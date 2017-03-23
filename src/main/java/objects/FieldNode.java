package objects;

import java.util.ArrayList;

public class FieldNode extends Node implements Comparable {
	public final int maxDepth = 3;

	public FieldNode(String s) {
		super(s.toLowerCase());

	}

	public int compareTo(FieldNode f) {
		return this.name.compareTo(f.getName());
	}

	public boolean equals(FieldNode f) {
		return this.name.equals(f.getName());
	}

	public boolean equals(String s) {
		return this.name.equals(s.toLowerCase());
	}

	@Override
	public int compareTo(Object o) {
		return this.compareTo((FieldNode) o);
	}
}

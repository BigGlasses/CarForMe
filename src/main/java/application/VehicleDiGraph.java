package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import objects.Node;
import objects.FieldNode;
import objects.Vehicle;
import objects.VehicleNode;

public class VehicleDiGraph {
	private static Map<String, FieldNode> fieldDictionary;
	private static ArrayList<String> fieldStrings;

	public final static double COST_INCREMENTS = 500;
	public final static double MAX_COST = 25000;

	public static void init() {
		fieldDictionary = new HashMap<String, FieldNode>();
		fieldStrings = new ArrayList<String>();
		for (int i = 0; i * COST_INCREMENTS < MAX_COST; i++) {
			FieldNode n = addField(String.format("cost:$%.2f", i * COST_INCREMENTS));
			if (i > 0)
				connect(n, fieldDictionary.get(String.format("cost:$%.2f", (i - 1) * COST_INCREMENTS)));
		}
	}

	private static FieldNode addField(String s) {
		if (s.equals("")) return null;
		FieldNode n = new FieldNode(s.toLowerCase());
		fieldStrings.add(s.toLowerCase());
		//System.out.println("Added Field: " + s.toLowerCase());
		fieldDictionary.put(s.toLowerCase(), n);
		return n;
	}

	public static FieldNode getNode(String s) {
		return fieldDictionary.get(s.toLowerCase());
	}

	public static void matchFields(HashSet <VehicleNode> v, String[] hardFields, String[] negativeHardFields){
		// Remove all that do not match hard fields.
		for (String s : hardFields) {
			FieldNode n = VehicleDiGraph.getNode(s.toLowerCase());
			// This is binary deletion
			v.removeIf(p -> !n.checkVehicleChild(p));
		}
		// Remove all that are in negative hard fields.
		for (String s : negativeHardFields) {
			FieldNode n = VehicleDiGraph.getNode(s.toLowerCase());
			// This is binary deletion
			v.removeIf(p -> n.checkVehicleChild(p));
		}
	}
	
	public static Vehicle[] searchVehicles(String[] softFields, String[] hardFields, String [] negativeHardFields) {
		HashSet <VehicleNode> VehicleTrail = new HashSet<VehicleNode>();
		int depth = 0;
		ArrayList<String> allSoftFields = new ArrayList<String>();
		ArrayList<String> newSoftFields = new ArrayList<String>();

		for (String s : softFields) {
			allSoftFields.add(s);
		}

		// Breadth first search
		while (VehicleTrail.size() < 12 && depth < 10) {
			for (String s : softFields) {
				Node current = fieldDictionary.get(s);
				if (current == null) continue;
				for (Node a : current.getConnections()) {
					if ((a instanceof VehicleNode)) {
						VehicleTrail.add((VehicleNode) a);
					} else if ((a instanceof FieldNode)) {
						if (!allSoftFields.contains(a.getName())) {
							allSoftFields.add(a.getName());
							newSoftFields.add(a.getName());
						}
					}
				}
			}
			depth++;
			matchFields(VehicleTrail, hardFields, negativeHardFields);
			String[] a = new String[newSoftFields.size()];
			softFields = newSoftFields.toArray(a);
			newSoftFields.clear();
		}


		VehicleNode[] a = new VehicleNode[VehicleTrail.size()];
		VehicleNode[] vehiclesPreOut = VehicleTrail.toArray(a);
		Vehicle[] vehiclesOut = new Vehicle[Math.min(12, vehiclesPreOut.length)]; // Implement a mergesort here
		for (int i = 0; i < vehiclesOut.length; i++) {
			if (i < vehiclesPreOut.length)
				vehiclesOut[i] = vehiclesPreOut[i].v;
		}
		return vehiclesOut;
	}

	public static VehicleNode createVehicle(Vehicle v) {
		VehicleNode v2 = new VehicleNode(v);
		if (!fieldDictionary.containsKey(v2.v.make)) {
			addField(v2.v.make);
		}
		if (!fieldDictionary.containsKey(v2.v.fuelType)) {
			addField(v2.v.fuelType);
		}
		connect(fieldDictionary.get(v2.v.make), v2);
		connect(fieldDictionary.get(v2.v.fuelType), v2);
		for (String tag : v2.v.getTags()) {
			if (!fieldDictionary.containsKey(tag))
				addField(tag);
			connect(fieldDictionary.get(tag), v2);
		}

		String cost = String.format("cost:$%.2f", ((int) (v2.v.cost / COST_INCREMENTS)) * COST_INCREMENTS);
		connect(fieldDictionary.get(cost), v2);
		return v2;
	}

	public static String[] allFields() {
		String [] out = fieldStrings.toArray(new String[fieldStrings.size()]);
		Arrays.sort(out);
		return out;

	}

	public static void printFields() {
		for (String n : fieldDictionary.keySet()) {
			System.out.println(n);
		}
	}

	public static void connect(Node f, Node f2) {
		if (f != null && f2 != null){
		f.addConnection(f2);
		f2.addConnection(f);
		}
	}

	public static void disconnect(Node f, Node f2) {
		f.removeConnection(f2);
		f2.removeConnection(f);
	}

	public static void disconnectAll(Node f) {
		for (Node n : f.getConnections()) {
			n.removeConnection(f);
			f.removeConnection(n); // This line might be unnecessary.
		}
	}

}

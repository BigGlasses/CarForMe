package objects;

import java.util.ArrayList;

import input.CarInputReader;
import input.VehicleParser;
/**
 * Node containing search field information.
 * @author Brandon
 */
public class FieldNode extends Node implements Comparable {
	public final int maxDepth = 3;
	ArrayList <Vehicle> vehicleChildren;
	
	/**
	 * Creates a node with a string identifier.
	 * @param s Identifier of the node.
	 */
	public FieldNode(String s) {
		super(s.toLowerCase());
		vehicleChildren = new ArrayList <Vehicle>();

	}

	/**
	 * Points this node to another node.
	 * If the second node is a VehicleNode, it is added as a child.
	 * @param f
	 */
	public void addConnection(Node f) {
		super.addConnection(f);
		if (f.getClass() == VehicleNode.class){
			addVehicleChild((VehicleNode) f);
		}
	}
	/**
	 * Points this node to another node.
	 * If the second node is a VehicleNode, it is added as a child.
	 * @param f
	 */
	public void removeConnection(Node f) {
		super.removeConnection(f);
		if (f.getClass() == VehicleNode.class){
			vehicleChildren.remove(f);
		}
	}
	
	/**
	 * Compares the name's of the FieldNodes
	 * 
	 * @param f
	 * @return
	 */
	public int compareTo(FieldNode f) {
		return this.name.compareTo(f.getName());
	}

	/**
	 * Does a binary insertion to add a VehicleNode to this FieldNode
	 * @param v
	 * @return
	 */
	public void addVehicleChild(VehicleNode v){
		CarInputReader.addVehicle(vehicleChildren, v.v.getDatapoint());
	}
	
	/**
	 * Does a binary search to determine if a VehicleNode belongs to this FieldNode
	 * @param v
	 * @return
	 */
	public boolean checkVehicleChild(VehicleNode v){
		int index = CarInputReader.searchVehiclesIndex(vehicleChildren, v.v.getDatapoint());
		if (index < 0 || index >= vehicleChildren.size()){
			return false;
		}
		return (vehicleChildren.get(index).compareTo(v.v) == 0);
	}

	/**
	 * Checks if the String is equal to this Node's identifier.
	 * 
	 * @param f
	 * @return
	 */
	public boolean equals(FieldNode f) {
		return this.name.equals(f.getName());
	}

	/**
	 * Checks if the String is equal to this Node's identifier.
	 * 
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

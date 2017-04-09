package objects;

import java.util.ArrayList;


/**
 * Node containing vehicle object information.
 * @author Brandon
 */
public class VehicleNode extends Node {
	public final Vehicle v;

	public VehicleNode(Vehicle v) {
		super(v.toString());
		this.v = v;
	}

}

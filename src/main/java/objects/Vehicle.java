package objects;

import java.util.ArrayList;
import java.util.Random;

public class Vehicle {
	public String model;
	public String make;
	public String fuelType;
	public String fuelType1;
	public double cost;
	public double kmPerLiter;
	private ArrayList<VehicleDataPoint> dp;
	private ArrayList<String> tags;

	/**
	 * C
	 * 
	 * @param model
	 * @param mak
	 * @param fuelType
	 */
	public Vehicle(String model, String make, String fuelType) {
		this.model = model;
		this.make = make;
		this.fuelType = fuelType;
		this.fuelType1 = "";
		Random r = new Random();
		this.cost = 1000 + r.nextInt(4000);
		this.kmPerLiter = (7000.0 + r.nextInt(4000)) / (1000.0 * 100.0); // Gives a
																	// random km
																	// per liter
		dp = new ArrayList<VehicleDataPoint>();
		tags = new ArrayList<String>();
	}

	/**
	 * Constructs a Vehicle from a VehicleDataPoint, copying its attributes.
	 * 
	 * @param v
	 */
	public Vehicle(VehicleDataPoint v) {
		this(v.model, v.make, v.fuelType);
		this.addData(v);
	}

	public void addData(VehicleDataPoint v) {
		dp.add(v);
	}

	public void addTag(String s) {
		//Make sure tag is not a number
		try{
			Double.parseDouble(s);
		}
		catch (Exception e){
		if (!tags.contains(s.toLowerCase()))
			tags.add(s.toLowerCase());
		}
	}

	public String[] getTags() {
		return tags.toArray(new String[tags.size()]);
	}

	public int compareTo(Vehicle v) {
		if (greater(v.model, this.model.toLowerCase()))
			return 1;
		else if (v.model.equals(model.toLowerCase())) {
			if (greater(v.make, make.toLowerCase()))
				return 1;
			else if (v.fuelType.equals(fuelType.toLowerCase())) {
				if (greater(v.fuelType, fuelType.toLowerCase()))
					return 1;
				else if (v.fuelType1.equals(fuelType1.toLowerCase())) {
					if (greater(v.fuelType1, fuelType1.toLowerCase()))
						return 1;
					else if (v.fuelType1.equals(fuelType1.toLowerCase()))
						return 0;
					else
						return -1;
				} else
					return -1;
			} else
				return -1;
		} else
			return -1;
	}

	public String toString() {
		return String.format("[%s] [%s] [%s] [$%.2f] %s", this.make, this.model, this.fuelType, this.cost,
				this.tags.toString());
	}

	public VehicleDataPoint getDp() {
		return this.dp.get(0);
	}
	
	public VehicleJSON toJSON(){
		VehicleJSON j = new VehicleJSON();
		j.manufacturer = make;
		j.model = model;
		j.image = "";
		j.cost = cost;
		return j;
	}

	public int compareTo(VehicleDataPoint v) {
		if (greater(v.model, this.model.toLowerCase()) && !v.model.equals(""))
			return 1;
		else if (this.model.startsWith(v.model.toLowerCase()) || v.model.equals("")) {
			if (greater(v.make, make.toLowerCase()) && !v.make.equals(""))
				return 1;
			else if (v.make.equals(make.toLowerCase()) || v.make.equals("")) {
				if (greater(v.fuelType, fuelType.toLowerCase()) && !v.fuelType.equals(""))
					return 1;
				else if (v.fuelType.equals(fuelType.toLowerCase()) || v.fuelType.equals("")) {
					if (greater(v.fuelType1, fuelType1.toLowerCase()) && !v.fuelType1.equals(""))
						return 1;
					else if (v.fuelType1.equals(fuelType1.toLowerCase()) || v.fuelType1.equals(""))
						return 0;
					else
						return -1;
				} else
					return -1;
			} else
				return -1;
		} else
			return -1;
	}

	private boolean greater(Comparable a, Comparable b) {
		return (a.compareTo(b)) > 0;
	}
}

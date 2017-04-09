package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import application.VehicleDiGraph;

/**
 * Contains unique vehicle information.
 * @author Brandon
 *
 */
public class Vehicle {
	public final String model;
	public final String make;
	public final String fuelType;
	public final String fuelType1;
	public final String year;
	public double cost;
	public double kmPerLiter;
	private ArrayList<VehicleDataPoint> dp;
	private ArrayList<String> tags;

	/**
	 * Creates a Vehicle
	 * 
	 * @param model
	 * @param mak
	 * @param fuelType
	 */
	public Vehicle(String model, String make, String fuelType, String year) {
		this.model = model;
		this.make = make;
		this.fuelType = fuelType;
		this.fuelType1 = "";
		this.year = year;
		Random r = new Random();
		this.cost = r.nextInt( (int) VehicleDiGraph.MAX_COST );
		this.kmPerLiter = (7000.0 + r.nextInt(7000)) / (1000.0); // Gives a
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
		this(v.model, v.make, v.fuelType, v.year);
		this.addData(v);
	}

	public void addData(VehicleDataPoint v) {
		dp.add(v);
	}

	public void addTag(String s) {
		// Make sure tag is not a number
		if (!s.matches("[-+]?\\d*\\.?\\d+")) // This is taken from http://stackoverflow.com/questions/14206768/how-to-check-if-a-string-is-numeric
			if (!tags.contains(s.toLowerCase()))
				tags.add(s.toLowerCase());
	}

	public String[] getTags() {
		return tags.toArray(new String[tags.size()]);
	}



	public String toString() {
		return String.format("[%s] [%s] [%s] [$%.2f] %s", this.year + " " + this.make, this.model, this.fuelType, this.cost,
				this.tags.toString());
	}

	public VehicleDataPoint getDatapoint() {
		return this.dp.get(0);
	}

	/**
	 * 
	 * @return A JSON equivalent to this Vehicle Object.
	 */
	public VehicleJSON toJSON() {
		VehicleJSON j = new VehicleJSON();
		j.manufacturer = make;
		j.model = model;
		j.image = "";
		j.cost = cost;
		String[] tags = getTags();
		Arrays.sort(tags);
		j.tags = tags;
		j.fuelType = this.fuelType;
		return j;
	}

	public int compareTo(Vehicle v) {
		if (greater(v.model, this.model.toLowerCase()))
			return 1;
		else if (v.model.equals(model.toLowerCase())) {
			if (greater(v.make, make.toLowerCase()))
				return 1;
			else if (v.make.equals(make.toLowerCase())) {
				if (greater(v.fuelType, fuelType.toLowerCase()))
					return 1;
				else if (v.fuelType.equals(fuelType.toLowerCase())) {
					if (greater(v.year, year.toLowerCase()))
						return 1;
					else if (v.year.equals(year.toLowerCase()))
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
					if (greater(v.year, year.toLowerCase()) && !v.year.equals(""))
						return 1;
					else if (v.year.equals(year.toLowerCase()) || v.year.equals(""))
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

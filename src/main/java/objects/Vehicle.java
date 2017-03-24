package objects;


import java.util.Random;

public class Vehicle {
	public String model;
	public String make;
	public String fuelType;
	public String fuelType1;
	public double cost;
	public double kmPerLiter;
	
	public Vehicle(String model, String make, String fuelType) {
		this.model = model;
		this.make = make;
		this.fuelType = fuelType;
		this.fuelType1 = "";
		Random r = new Random();
		this.cost = 1000 + r.nextInt(4000);
		this.kmPerLiter = (7000 + r.nextInt(4000))/(1000*100); // Gives a random km per liter
	}
	
	public Vehicle(VehicleDataPoint v) {
		this.model = v.model;
		this.make = v.make;
		this.fuelType = v.fuelType;
		this.fuelType1 = v.fuelType1;
		Random r = new Random();
		this.cost = 1000 + r.nextInt(4000);
		this.kmPerLiter = (7000 + r.nextInt(4000))/(1000*100); // Gives a random km per liter
		this.addData(v);
	}

	public void addData(VehicleDataPoint v) {
		
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
	public String toString(){
		return String.format("%s %s %s %.2f", this.make, this.model, this.fuelType1,this.cost);
		
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

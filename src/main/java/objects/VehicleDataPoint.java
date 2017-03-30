package objects;

public class VehicleDataPoint {
	public String model;
	public String make;
	public String fuelType;
	public String fuelType1;

	public VehicleDataPoint(String model, String make, String fuelType) {
		this.model = model.toLowerCase();
		this.make = make.toLowerCase();
		this.fuelType = fuelType.toLowerCase();
		this.fuelType1 = "";
	}
}

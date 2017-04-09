package objects;

/**
 * Contains temporary vehicle information.
 * @author Brandon
 *
 */
public class VehicleDataPoint {
	public String model;
	public String make;
	public String fuelType;
	public String fuelType1;
	public String year;

	public VehicleDataPoint(String model, String make, String fuelType, String year) {
		this.model = model.toLowerCase();
		this.make = make.toLowerCase();
		this.fuelType = fuelType.toLowerCase();
		this.fuelType1 = "";
		this.year = year;
	}
}

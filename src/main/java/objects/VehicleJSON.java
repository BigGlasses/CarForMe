package objects;

/**
 * Simple representation of a vehicle for front end.
 * @author Brandon
 *
 */
public class VehicleJSON implements Comparable{
	public String manufacturer;
	public String model;
	public String image;
	public double cost;
	public int gasPerYear;
	public String [] tags;
	public String fuelType;
	
	public VehicleJSON(){
		
	}

	public int compareTo(VehicleJSON vj){
		return (int) ((this.cost + this.gasPerYear) - (vj.cost + vj.gasPerYear));
	}
	
	@Override
	public int compareTo(Object vj){
		return (int) ((this.cost + this.gasPerYear) - (((VehicleJSON) vj).cost + ((VehicleJSON) vj).gasPerYear));
	}
}	

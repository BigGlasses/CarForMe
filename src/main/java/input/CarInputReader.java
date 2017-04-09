package input;

import java.util.ArrayList;

import objects.Vehicle;
import objects.VehicleDataPoint;

public class CarInputReader {
	public static void read(ArrayList<Vehicle> allVehicles) {
	}
	/**
	 * Adds a vehicle to the list, using a binary insertion. If the vehicle is
	 * already in the list, the VehicleDataPoint is instead added to the
	 * Vehicle.
	 * 
	 * @param v
	 *            Vehicle data point to add.
	 */
	public static Vehicle addVehicle(ArrayList<Vehicle> vehiclesList, VehicleDataPoint v) {
		int lo = 0;
		int hi = vehiclesList.size() - 1;
		int cp = 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			cp = vehiclesList.get(mid).compareTo(v);
			if (cp > 0)
				hi = mid - 1;
			else if (cp < 0)
				lo = mid + 1;
			else
				break;
		}
		int mid = lo + (hi - lo) / 2;
		if (cp == 0) {
			vehiclesList.get(mid).addData(v);
			return vehiclesList.get(mid);
		} else {

			Vehicle newV = new Vehicle(v);
			if (vehiclesList.size() == 0)
				vehiclesList.add(newV);
			else if (cp < 0)
				vehiclesList.add(mid, newV);
			else
				vehiclesList.add(mid + 1, newV);
			return newV;
		}
	}

	/**
	 * Finds the index of a Vehicle using a binary search.
	 * 
	 * @param v
	 *            VehicleDataPoint to look for.
	 * @return Index of Vehicle.
	 */
	public static int searchVehiclesIndex(ArrayList<Vehicle> vehiclesList, VehicleDataPoint v) {
		int lo = 0;
		int hi = vehiclesList.size() - 1;
		int cp = 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			cp = vehiclesList.get(mid).compareTo(v);
			if (cp > 0)
				hi = mid - 1;
			else if (cp < 0)
				lo = mid + 1;
			else
				break;
		}
		int mid = lo + (hi - lo) / 2;
		if (cp == 0)
			return mid;
		else {
			if (vehiclesList.size() == 0)
				return 0;
			else if (cp < 0)
				return mid;
			else
				return mid + 1;
		}
	}
}

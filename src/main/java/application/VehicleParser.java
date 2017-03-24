package application;
/**
 * Used to read from the CSV files and create a list of Vehicle objects.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import objects.Vehicle;
import objects.VehicleDataPoint;

public class VehicleParser {
	public static ArrayList<Vehicle> allVehicles;

	/**
	 * Reads the file and puts the Vehicles into an ordered list.
	 * @throws FileNotFoundException Throws if parse.txt doesn't exist.
	 */
	public static void init() throws FileNotFoundException {
		allVehicles = new ArrayList<Vehicle>();
		FileReader n = new FileReader("parsed.txt");
		Scanner carReader = new Scanner(n);
		while (carReader.hasNext()) {
			String l = carReader.nextLine();
			Scanner lineReader = new Scanner(l).useDelimiter(",");
			String model = lineReader.next();
			String make = lineReader.next();
			String fuel = lineReader.next();
			VehicleDataPoint v = new VehicleDataPoint(model, make, fuel);
			addVehicle(v);
			lineReader.close();
		}
		carReader.close();

		VehicleDiGraph.init();
		for (Vehicle v : allVehicles) {
			VehicleDiGraph.createVehicle(v);
		}
	}

	/**
	 * Adds a vehicle to the list, using a binary insertion.
	 * If the vehicle is already in the list, the VehicleDataPoint is instead added to the Vehicle.
	 * @param v Vehicle data point to add.
	 */
	public static void addVehicle(VehicleDataPoint v) {
		int lo = 0;
		int hi = allVehicles.size() - 1;
		int cp = 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			cp = allVehicles.get(mid).compareTo(v);
			if (cp > 0)
				hi = mid - 1;
			else if (cp < 0)
				lo = mid + 1;
			else
				break;
		}
		int mid = lo + (hi - lo) / 2;
		if (cp == 0)
			allVehicles.get(mid).addData(v);
		else {

			Vehicle newV = new Vehicle(v);
			if (allVehicles.size() == 0)
				allVehicles.add(newV);
			else if (cp < 0)
				allVehicles.add(mid, newV);
			else
				allVehicles.add(mid + 1, newV);
		}
	}

	/**
	 * Finds the index of a Vehicle using a binary search.
	 * @param v VehicleDataPoint to look for.
	 * @return Index of Vehicle.
	 */
	public static int searchVehiclesIndex(VehicleDataPoint v) {
		int lo = 0;
		int hi = allVehicles.size() - 1;
		int cp = 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			cp = allVehicles.get(mid).compareTo(v);
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

			Vehicle newV = new Vehicle(v);
			if (allVehicles.size() == 0)
				return 0;
			else if (cp < 0)
				return mid;
			else
				return mid + 1;
		}
	}
}

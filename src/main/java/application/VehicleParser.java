package application;
/**
 * Used to read from the CSV files and create a list of Vehicle objects.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVReader;

import objects.Vehicle;
import objects.VehicleDataPoint;

public class VehicleParser {
	public static ArrayList<Vehicle> allVehicles;

	/**
	 * Reads the file and puts the Vehicles into an ordered list.
	 * @throws IOException 
	 */
	public static void init() throws IOException {
		allVehicles = new ArrayList<Vehicle>();
		FileReader n = new FileReader("parsed.txt");

		CSVReader reader = new CSVReader(new FileReader("datasets/vehicles.csv"));
		String[] nextLine;
		reader.readNext(); // Skip header
		while ((nextLine = reader.readNext()) != null) {
			// Grab car information
			String manufacturer = nextLine [26 + 20 - 1]; //AU MODEL 
			String model = nextLine [26 + 21 - 1]; //AV MODEL 
			String fuel = nextLine [10];
			VehicleDataPoint v = new VehicleDataPoint(model, manufacturer, fuel);
			addVehicle(allVehicles, v);
			Vehicle ve = allVehicles.get(searchVehiclesIndex(allVehicles, v));
			ve.addTag(nextLine [52 + 10 - 1]); //BK VEHICLE SIZE
			ve.addTag(nextLine [52 + 5 - 1]); //BF TRANY
			ve.addTag(nextLine [25 - 1]); //Y wheel drive
		}
		reader.close();
		
		reader = new CSVReader(new FileReader("datasets/data.csv"));
		reader.readNext(); // Skip header
		while ((nextLine = reader.readNext()) != null) {
			// Grab car information
			String manufacturer = nextLine [2];
			String model = nextLine [3];
			String fuel = nextLine [10];
			VehicleDataPoint v = new VehicleDataPoint(model, manufacturer, fuel);
			addVehicle(allVehicles, v);
			Vehicle ve = allVehicles.get(searchVehiclesIndex(allVehicles, v));
			ve.addTag(nextLine [8]);
		}
		reader.close();

		VehicleDiGraph.init();
		for (Vehicle v : allVehicles) {
			System.out.println(v);
			VehicleDiGraph.createVehicle(v);
		}
	}

	/**
	 * Adds a vehicle to the list, using a binary insertion.
	 * If the vehicle is already in the list, the VehicleDataPoint is instead added to the Vehicle.
	 * @param v Vehicle data point to add.
	 */
	public static void addVehicle(ArrayList <Vehicle> vehiclesList, VehicleDataPoint v) {
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
			vehiclesList.get(mid).addData(v);
		else {

			Vehicle newV = new Vehicle(v);
			if (vehiclesList.size() == 0)
				vehiclesList.add(newV);
			else if (cp < 0)
				vehiclesList.add(mid, newV);
			else
				vehiclesList.add(mid + 1, newV);
		}
	}

	/**
	 * Finds the index of a Vehicle using a binary search.
	 * @param v VehicleDataPoint to look for.
	 * @return Index of Vehicle.
	 */
	public static int searchVehiclesIndex(ArrayList <Vehicle> vehiclesList,VehicleDataPoint v) {
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
			Vehicle newV = new Vehicle(v);
			if (vehiclesList.size() == 0)
				return 0;
			else if (cp < 0)
				return mid;
			else
				return mid + 1;
		}
	}
}

package application;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import application.VehicleDiGraph;
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


		//ClassLoader classLoader = VehicleParser.class.getClassLoader();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String f = classLoader.getResource("vehicles.csv").getFile();
		f = f.replace("%20", " ");
		CSVReader reader = new CSVReader(new FileReader(f));
		String[] nextLine;
		reader.readNext(); // Skip header
		while ((nextLine = reader.readNext()) != null) {
			// Grab car information
			String manufacturer = nextLine [26 + 20]; //AU MODEL 
			String model = nextLine [26 + 21]; //AV MODEL 
			String fuel = nextLine [31];
			String year = nextLine [52 + 11];
			VehicleDataPoint v = new VehicleDataPoint(model, manufacturer, fuel, year);
			Vehicle ve = addVehicle(allVehicles, v);
			//Vehicle ve = allVehicles.get(searchVehiclesIndex(allVehicles, v));
			ve.addTag(nextLine [52 + 11 - 1]); //BK VEHICLE SIZE
			ve.addTag("Year:" + year); //BK VEHICLE YEAR
			ve.addTag(nextLine [52 + 6 - 1]); //BF TRANY
			ve.addTag(nextLine [24]); //Y wheel drive
			ve.addTag(fuel); //Y wheel drive
		}
		reader.close();

		classLoader = Thread.currentThread().getContextClassLoader();
		f = classLoader.getResource("data.csv").getFile();
		f = f.replace("%20", " ");
		reader = new CSVReader(new FileReader(f));
		reader.readNext(); // Skip header
		while ((nextLine = reader.readNext()) != null) {
			// Grab car information
			String manufacturer = nextLine [2];
			String model = nextLine [3];
			String fuel = nextLine [10];
			String year = nextLine [1];
			VehicleDataPoint v = new VehicleDataPoint(model, manufacturer, fuel, year);
			Vehicle ve = addVehicle(allVehicles, v);
			ve.addTag("Year:" + year); //BK VEHICLE YEAR
			ve.addTag(nextLine [8]);
			ve.addTag(fuel.toLowerCase());
		}
		reader.close();

		classLoader = Thread.currentThread().getContextClassLoader();
		f = classLoader.getResource("fuelconsumption.csv").getFile();
		f = f.replace("%20", " ");
		reader = new CSVReader(new FileReader(f));
		reader.readNext(); // Skip header
		reader.readNext(); // Skip header #2
		while ((nextLine = reader.readNext()) != null) {
			// Grab car information
			String manufacturer = nextLine [1];
			String model = nextLine [2];
			String fuel = nextLine [7];
			String year = nextLine [0];
			switch (fuel){
			case "X":
				fuel = "Regular Gasoline";
				break;
			case "Z":
				fuel = "Premium Gasoline";
				break;
			case "D":
				fuel = "Diesel";
				break;
			case "E":
				fuel = "Ethanol (E85)";
				break;
			case "N":
				fuel = "Natural Gas";
				break;
			}
			VehicleDataPoint v = new VehicleDataPoint(model, manufacturer, fuel, year);
			
			Vehicle ve = addVehicle(allVehicles, v);
			ve.kmPerLiter = Double.parseDouble(nextLine [10]);
			ve.addTag("Year:" + year); //BK VEHICLE YEAR
			ve.addTag(nextLine [8]);
			ve.addTag(fuel.toLowerCase());
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
	public static Vehicle addVehicle(ArrayList <Vehicle> vehiclesList, VehicleDataPoint v) {
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
		if (cp == 0){
			vehiclesList.get(mid).addData(v);
			return vehiclesList.get(mid);
		}
		else {

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
			if (vehiclesList.size() == 0)
				return 0;
			else if (cp < 0)
				return mid;
			else
				return mid + 1;
		}
	}
}

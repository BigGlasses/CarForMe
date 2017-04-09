package input;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import application.VehicleDiGraph;
import objects.Vehicle;
import objects.VehicleDataPoint;
/**
 *	Loads input files data and car prices, initializes VehicleDiGraph.
 * @author Brandon
 */
public class VehicleParser {
	public static ArrayList<Vehicle> allVehicles;

	/**
	 * Reads the file and puts the Vehicles into a graph.
	 * 
	 * @throws IOException
	 */
	public static void init() throws IOException {
		allVehicles = new ArrayList<Vehicle>();
		VehiclesReader.read(allVehicles);
		DataReader.read(allVehicles);
		ConsumptionReader.read(allVehicles);
		CarPriceGetter.updatePrices(allVehicles);
		

		
		
		VehicleDiGraph.init();
		for (Vehicle v : allVehicles) {
			System.out.println(v);
			VehicleDiGraph.createVehicle(v);
		}
	}

	
}

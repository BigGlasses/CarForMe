package input;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import objects.Vehicle;
import objects.VehicleDataPoint;

/**
 * This reads the vehicles csv file.
 * 
 * @author Brandon
 *
 */

public class VehiclesReader extends CarInputReader {

	public static void read(ArrayList<Vehicle> allVehicles) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String f = classLoader.getResource("vehicles.csv").getFile();
		f = f.replace("%20", " ");
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(f));
			String[] nextLine;
			reader.readNext(); // Skip header
			while ((nextLine = reader.readNext()) != null) {
				// Grab car information
				String manufacturer = nextLine[26 + 20]; // AU MODEL
				String model = nextLine[26 + 21]; // AV MODEL
				String fuel = nextLine[31];
				String year = nextLine[52 + 11];
				VehicleDataPoint v = new VehicleDataPoint(model, manufacturer, fuel, year);
				Vehicle ve = addVehicle(allVehicles, v);
				ve.addTag(nextLine[52 + 11 - 1]); // BK VEHICLE SIZE
				ve.addTag("Year:" + year); // BK VEHICLE YEAR
				ve.addTag(nextLine[52 + 6 - 1]); // BF TRANY
				ve.addTag(nextLine[24]); // Y wheel drive
				ve.addTag(fuel.toLowerCase()); // Y wheel drive\
				double mpg = Double.parseDouble(nextLine[15]);
				ve.kmPerLiter = mpg * 0.425144;
				reader.close();
			}
		} catch (Exception e) {
		}

	}

}

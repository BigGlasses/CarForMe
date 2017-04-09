package input;

import java.io.FileReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import objects.Vehicle;
import objects.VehicleDataPoint;
/**
 * This reads the fuelconsumption csv file.
 * @author Brandon
 *
 */
public class ConsumptionReader extends CarInputReader {

	public static void read(ArrayList<Vehicle> allVehicles) {
		try {
			String[] nextLine;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			classLoader = Thread.currentThread().getContextClassLoader();
			String f = classLoader.getResource("fuelconsumption.csv").getFile();
			f = f.replace("%20", " ");
			CSVReader reader = new CSVReader(new FileReader(f));
			reader.readNext(); // Skip header
			reader.readNext(); // Skip header #2
			while ((nextLine = reader.readNext()) != null) {
				// Grab car information
				String manufacturer = nextLine[1];
				String model = nextLine[2];
				String fuel = nextLine[7];
				String year = nextLine[0];
				switch (fuel) {
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
				double literPer100km = Double.parseDouble(nextLine[10]);
				ve.kmPerLiter = 100 / literPer100km;
				ve.addTag("Year:" + year); // BK VEHICLE YEAR
				ve.addTag(nextLine[8]);
				ve.addTag(fuel.toLowerCase());
			}
			reader.close();
		}
		catch (Exception e){
			
		}
	}

}

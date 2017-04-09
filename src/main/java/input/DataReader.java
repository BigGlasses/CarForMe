package input;

import java.io.FileReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import objects.Vehicle;
import objects.VehicleDataPoint;
/**
 * This reads the data csv file.
 * @author Brandon
 *
 */

public class DataReader extends CarInputReader{

	public static void read(ArrayList<Vehicle> allVehicles) {
		try {
			String[] nextLine;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			classLoader = Thread.currentThread().getContextClassLoader();
			String f = classLoader.getResource("data.csv").getFile();
			f = f.replace("%20", " ");
			CSVReader reader = new CSVReader(new FileReader(f));
			reader.readNext(); // Skip header
			while ((nextLine = reader.readNext()) != null) {
				// Grab car information
				String manufacturer = nextLine[2];
				String model = nextLine[3];
				String fuel = nextLine[10];
				String year = nextLine[1];
				VehicleDataPoint v = new VehicleDataPoint(model, manufacturer, fuel, year);
				Vehicle ve = addVehicle(allVehicles, v);
				ve.addTag("Year:" + year); // BK VEHICLE YEAR
				ve.addTag(nextLine[8]);
				ve.addTag(fuel.toLowerCase());
				//System.out.println(nextLine[13]);
				if (nextLine.equals("")) {
					double literPer100km = Double.parseDouble(nextLine[13]);
					ve.kmPerLiter = 100 / literPer100km;
				}
			}
			reader.close();
		}
		catch (Exception e){
			
		}
		
	}

}

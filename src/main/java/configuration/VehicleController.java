package configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.tomcat.jni.Time;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import application.CarImageThread;
import application.VehicleDiGraph;
import application.jsonGetter;
import input.VehicleParser;
import objects.FieldNode;
import objects.Node;
import objects.Vehicle;
import objects.VehicleDataPoint;
import objects.VehicleJSON;
import objects.VehicleNode;

/**
 * This controls the /vehicles endpoint.
 * @author Brandon
 */
@Controller
@RequestMapping("/vehicles")
public class VehicleController {
	private static final double REGULAR_GASOLINE_PRICE = 0.99;
	private static final double PREMIUM_GASOLINE_PRICE = 1.10;
	private static final double DIESEL_PRICE = 1.09;
	private static final double ELECTRICITY_PRICE = 0.1;

	private static final double AVG_GASOLINE_LITER_PER_KM = 12;
	private static final double AVG_DIESEL_LITER_PER_KM = 12;
	private static final double AVG_ELECTRICITY_LITER_PER_KM = 1;
	
	private static final double MAX_IMAGE_QUERY_TIME = 5000;

	private Random dice = new Random();

	/**
	 * Returns a random vehicle from the data set.
	 * vehicles/randomVehicle endpoint.
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/randomVehicle", method = RequestMethod.GET)
	public @ResponseBody Vehicle randomVehicle(
			@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
		Vehicle v = VehicleParser.allVehicles.get(dice.nextInt(VehicleParser.allVehicles.size()));
		return v;
	}

	/**
	 * Performs a breadth first search for a Vehicle using the VehicleDiGraph.
	 * vehicles/search endpoint
	 * 
	 * @param manufacturer
	 *            Car manufacturer
	 * @param model
	 *            Car model
	 * @param budget
	 *            Money to spend
	 * @param travelDistance
	 *            Kilometers traveled per week
	 * @return List of maximum 15 most suited vehicles.
	 * @throws Exception
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody VehicleJSON[] search(
			@RequestParam(value = "manufacturer", required = false, defaultValue = "") String manufacturer,
			@RequestParam(value = "model", required = false, defaultValue = "") String model,
			@RequestParam(value = "budget", required = false, defaultValue = "") String budget,
			@RequestParam(value = "kmperweek", required = false, defaultValue = "") String travelDistance,
			@RequestParam(value = "wantFields", required = false, defaultValue = "") String wantFields,
			@RequestParam(value = "getImage", required = false, defaultValue = "") String getImage,
			@RequestParam(value = "dontwantFields", required = false, defaultValue = "") String dontWantFields)
			throws Exception {
		
		int upFront = Integer.parseInt(budget);
		if (upFront < 0){
			upFront = 0;
		}
		else if (upFront > VehicleDiGraph.MAX_COST){
			upFront = (int) VehicleDiGraph.MAX_COST;
		}
		int kmperweek;
		ArrayList<String> softFields = new ArrayList<String>();
		ArrayList<String> hardFields = new ArrayList<String>();
		ArrayList<String> negativeHardFields = new ArrayList<String>();
		if (travelDistance.equals(""))
			kmperweek = 200;
		else
			kmperweek = Integer.parseInt(travelDistance);
		 if (kmperweek <= 0){
			 kmperweek = 0;
		 }
		 if (kmperweek >= 500){
			 kmperweek = 500;
		 }

		int estimatedGasPerYear = (int) (52 * kmperweek / AVG_GASOLINE_LITER_PER_KM * REGULAR_GASOLINE_PRICE);
		int estimatedElectricPerYear = (int) (52 * kmperweek / AVG_ELECTRICITY_LITER_PER_KM * ELECTRICITY_PRICE);
		int estimatedDieselPerYear = (int) (52 * kmperweek / AVG_DIESEL_LITER_PER_KM * DIESEL_PRICE);
		
		
		String costG = String.format("cost:$%.2f",
				((int) ((upFront - estimatedGasPerYear) / VehicleDiGraph.COST_INCREMENTS))
						* VehicleDiGraph.COST_INCREMENTS);
		String costE = String.format("cost:$%.2f",
				((int) ((upFront - estimatedElectricPerYear) / VehicleDiGraph.COST_INCREMENTS))
						* VehicleDiGraph.COST_INCREMENTS);
		String costD = String.format("cost:$%.2f",
				((int) ((upFront - estimatedDieselPerYear) / VehicleDiGraph.COST_INCREMENTS))
						* VehicleDiGraph.COST_INCREMENTS);
		
		softFields.add(costG);
		
		softFields.add(costE);
		
		softFields.add(costD);
		
		Collections.shuffle(softFields);

		Scanner fieldReader = new Scanner(wantFields).useDelimiter(";");
		while (fieldReader.hasNext()) {
			String s = fieldReader.next();
			hardFields.add(s);
		}
		fieldReader.close();
		fieldReader = new Scanner(dontWantFields).useDelimiter(";");
		while (fieldReader.hasNext()) {
			negativeHardFields.add(fieldReader.next());
		}
		fieldReader.close();

		String[] a = new String[softFields.size()];
		String[] b = new String[hardFields.size()];
		String[] c = new String[negativeHardFields.size()];
		Vehicle[] vs = VehicleDiGraph.searchVehicles(softFields.toArray(a), hardFields.toArray(b), negativeHardFields.toArray(c));
		
		
		VehicleJSON[] out = new VehicleJSON[vs.length];
		
		//Create threads to poll google for images.
		CarImageThread[] imageGetters = new CarImageThread[vs.length];
		
		
		for (int i = 0; i < out.length; i++) {
			out[i] = vs[i].toJSON();
			
			
			// Get the image from GOOGLE CUSTOM SEARCH
			String q = "";
			q = out[i].manufacturer + " " + out[i].model;
			q = q.replaceAll("\\s+", "%20");
			String link = "https://www.googleapis.com/customsearch/v1?q=" + q
					+ "&cx=004748682743789405605%3Aswfov2xvt6m&key=AIzaSyDjU2ImybIvLHhibwboU2LiikSxxxzi8TI";
			imageGetters[i] = new CarImageThread(link);
			imageGetters[i].start(); // Start getting the image

			String fuel = out[i].fuelType;
			out[i].image = "images/genericcar.jpg";
			
			// Calculate the fuel cost based on the vehicle's gas type.
			if (fuel.indexOf("electric") != -1) {
				out[i].gasPerYear = (int) (52 * kmperweek / vs[i].kmPerLiter * ELECTRICITY_PRICE);
			} else if (fuel.indexOf("premium gasoline") != -1) {
				out[i].gasPerYear = (int) (52 * kmperweek / vs[i].kmPerLiter * PREMIUM_GASOLINE_PRICE);

			} else if (fuel.indexOf("regular gasoline") != -1) {
				out[i].gasPerYear = (int) (52 * kmperweek / vs[i].kmPerLiter * REGULAR_GASOLINE_PRICE);

			} else if (fuel.indexOf("diesel") != -1) {
				out[i].gasPerYear = (int) (52 * kmperweek / vs[i].kmPerLiter * DIESEL_PRICE);
			} else {
				out[i].gasPerYear = (int) (52 * kmperweek / vs[i].kmPerLiter * REGULAR_GASOLINE_PRICE);
			}
		}
		
		
		// Make sure that images are found in the thread in a reasonable time.
		boolean allImagesGotten =  false;
		double imageTime = System.currentTimeMillis();
		while (!allImagesGotten){
			allImagesGotten = true;
			if (System.currentTimeMillis() - imageTime > MAX_IMAGE_QUERY_TIME){
				break; // Escape if the threads take too long
			}
			for (CarImageThread gett : imageGetters){
				if (gett.isAlive()){
					allImagesGotten = false;
					Thread.sleep(100);
					break;
				}
			}
		}
		System.out.println("Image Querying took " + (System.currentTimeMillis() - imageTime) + " ms" );
		
		for (int i = 0; i < out.length; i++) {
			out[i].image = imageGetters[i].img;
			imageGetters[i].interrupt();
		}
		Arrays.sort(out);
		return out;
	}

	/**
	 * Returns all the connections for a given Field in VehicleDiGraph name.
	 * vehicles/connections endpoint
	 * @param fieldName
	 *            String of field to get connections of.
	 * @return List of connections.
	 */
	@RequestMapping(value = "/connections", method = RequestMethod.GET)
	public @ResponseBody String[] conn(
			@RequestParam(value = "field", required = false, defaultValue = "") String fieldName) {
		ArrayList<Node> f = (ArrayList) VehicleDiGraph.getNode(fieldName).getConnections();
		String[] vl = new String[f.size()];

		for (int i = 0; i < f.size(); i++) {
			vl[i] = f.get(i).getName();
		}
		return vl;

	}

	/**
	 * Returns all the connections for a given Field in VehicleDiGraph name.
	 * vehicles/tags endpoint
	 * @param fieldName
	 *            String of field to get connections of.
	 * @return List of connections.
	 */
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public @ResponseBody String[] allTags() {
		String[] tags = VehicleDiGraph.allFields();
		return tags;

	}

}
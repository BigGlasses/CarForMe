package configuration;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import application.VehicleDiGraph;
import application.VehicleParser;
import application.jsonGetter;
import objects.FieldNode;
import objects.Node;
import objects.Vehicle;
import objects.VehicleDataPoint;
import objects.VehicleJSON;
import objects.VehicleNode;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

	private Random dice = new Random();
	/**
	 * Returns a random vehicle from the data set.
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
	 * @param manufacturer Car manufacturer
	 * @param model Car model
	 * @param budget Money to spend
	 * @param travelDistance Kilometers traveled per week
	 * @return List of maximum 10 most suited vehicles.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody VehicleJSON[] search(
			@RequestParam(value = "manufacturer", required = false, defaultValue = "") String manufacturer,
			@RequestParam(value = "model", required = false, defaultValue = "") String model,
			@RequestParam(value = "budget", required = false, defaultValue = "") String budget,
			@RequestParam(value = "kmperweek", required = false, defaultValue = "") String travelDistance) throws Exception {
		int c = Integer.parseInt(budget);
		String cost = String.format("cost:$%.2f",
				((int) (c / VehicleDiGraph.costIncrements)) * VehicleDiGraph.costIncrements);
		ArrayList<String> softFields = new ArrayList<String>();
		softFields.add(cost);
		ArrayList<String> hardFields = new ArrayList<String>();
		if (!manufacturer.equals(""))
		{
			hardFields.add(manufacturer);
		}
		
		String [] a = new String [softFields.size()];
		String [] b = new String [hardFields.size()];
		Vehicle [] vs = VehicleDiGraph.searchVehicles(softFields.toArray(a), hardFields.toArray(b));
		VehicleJSON [] out = new VehicleJSON [vs.length];
		for (int i = 0; i < out.length ; i ++){
			out [i] = vs [i].toJSON();
			
			// Get the image from GOOGLE CUSTOM SEARCH
			String q = "";
			q = out [i].manufacturer + " " + out [i].model;
			q = q.replaceAll("\\s+", "%20");
			JSONObject json = jsonGetter.getJSON("https://www.googleapis.com/customsearch/v1?q=" + q + "&cx=004748682743789405605%3Aswfov2xvt6m&key=AIzaSyDjU2ImybIvLHhibwboU2LiikSxxxzi8TI");
			
			json = (json.getJSONArray("items")).getJSONObject(0);
			String img = (json.getJSONObject("pagemap").getJSONArray("cse_image").getJSONObject(0).getString("src"));
			out[i].image = img;
			}
		return out;
	}

	/**
	 * Returns all the connections for a given Field in VehicleDiGraph name.
	 * @param fieldName String of field to get connections of.
	 * @return List of connections.
	 */
	@RequestMapping(value = "/connections",
			method = RequestMethod.GET)
	public @ResponseBody String[] conn(@RequestParam(value = "field", required = false, defaultValue = "") String fieldName) {
		ArrayList<Node> f = (ArrayList) VehicleDiGraph.getNode(fieldName).getConnections();
		String[] vl = new String[f.size()];

		for (int i = 0; i < f.size(); i++) {
			vl[i] = f.get(i).getName();
		}
		System.out.println(f.size());
		return vl;

		// return
		// VehicleParser.allVehicles.get(VehicleParser.searchVehiclesIndex(v)%VehicleParser.allVehicles.size());
	}

}
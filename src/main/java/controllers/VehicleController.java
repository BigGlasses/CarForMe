package controllers;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import application.VehicleDiGraph;
import application.VehicleParser;
import objects.FieldNode;
import objects.Node;
import objects.Vehicle;
import objects.VehicleDataPoint;
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
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody Vehicle[] search(
			@RequestParam(value = "manufacturer", required = false, defaultValue = "") String manufacturer,
			@RequestParam(value = "model", required = false, defaultValue = "") String model,
			@RequestParam(value = "budget", required = false, defaultValue = "") String budget,
			@RequestParam(value = "kmperweek", required = false, defaultValue = "") String travelDistance) {
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
		return VehicleDiGraph.searchVehicles(softFields.toArray(a), hardFields.toArray(b));

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
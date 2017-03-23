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

	@RequestMapping(value = "/randomVehicle", method = RequestMethod.GET)
	public @ResponseBody Vehicle randomVehicle(
			@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
		Vehicle v = VehicleParser.allVehicles.get(dice.nextInt(VehicleParser.allVehicles.size()));
		return v;
	}

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

	@RequestMapping(value = "/connections", method = RequestMethod.GET)
	public @ResponseBody String[] conn() {
		Vehicle v = new Vehicle("", "", "");
		VehicleNode vn = VehicleDiGraph.createVehicle(v);
		ArrayList<Node> f = (ArrayList) vn.getConnections();
		int size = f.size();
		for (int i = 0; i < size; i++) {
			for (Node n : f.get(i).getConnections())
				f.add(n);
		}
		String[] vl = new String[f.size()];

		for (int i = 0; i < f.size(); i++) {
			vl[i] = f.get(i).getName();
		}
		System.out.println(f.size());
		VehicleDiGraph.disconnectAll(vn);
		return vl;

		// return
		// VehicleParser.allVehicles.get(VehicleParser.searchVehiclesIndex(v)%VehicleParser.allVehicles.size());
	}

	/**
	 * @RequestMapping(value = "/bye", method=RequestMethod.GET)
	 *                       public @ResponseBody Greeting
	 *                       sayBye(@RequestParam(value="name", required=false,
	 *                       defaultValue="Sdtranger") String name) { return new
	 *                       Greeting(counter.incrementAndGet(),
	 *                       String.format(template, name)); }
	 **/

}
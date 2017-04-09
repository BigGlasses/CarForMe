package input;

import java.util.ArrayList;
import java.util.Random;

import application.VehicleDiGraph;
import objects.Vehicle;

/**
 * This will be implemented when/if we receive API access.
 * @param allVehicles
 */
public class CarPriceGetter {
	/**
	 * This will be implemented when/if we receive API access.
	 * @param allVehicles
	 */
	public static void updatePrices(ArrayList<Vehicle> allVehicles){
		Random r = new Random();
		for (Vehicle v: allVehicles)
			v.cost = r.nextInt( (int) VehicleDiGraph.MAX_COST );
		
	}
}

package Controllers;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Application.VehicleParser;
import Objects.Vehicle;
import Objects.VehicleDataPoint;


@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private Random dice = new Random();
    @RequestMapping(value = "/randomVehicle", method=RequestMethod.GET)
    public @ResponseBody Vehicle randomVehicle(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
    	Vehicle v = VehicleParser.allVehicles.get(dice.nextInt(VehicleParser.allVehicles.size()));
        return v;
    }
    
    @RequestMapping(value = "/search", method=RequestMethod.GET)
    public @ResponseBody Vehicle search(@RequestParam(value="make", required=false, defaultValue="") String make, @RequestParam(value="model", required=false, defaultValue="" ) String model) {
    	VehicleDataPoint v = new VehicleDataPoint(model, make, "");
        return VehicleParser.allVehicles.get(VehicleParser.searchVehiclesIndex(v)%VehicleParser.allVehicles.size());
    }
    
    /**
    @RequestMapping(value = "/bye", method=RequestMethod.GET)
    public @ResponseBody Greeting sayBye(@RequestParam(value="name", required=false, defaultValue="Sdtranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }**/

}
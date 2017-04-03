package application;

import org.json.JSONObject;

import objects.VehicleJSON;

public class CarImageThread extends Thread {
	private String link;
	public String img;

	public CarImageThread(String link) {
		this.link = link;
		this.img = null;
	}

	@Override
	public void run() {
		JSONObject json;
		String jString = "";
		img = "images/genericcar.jpg";
		try {
			json = jsonGetter.getJSON(link);
			jString = json.toString();
			json = (json.getJSONArray("items")).getJSONObject(0);
			img = (json.getJSONObject("pagemap").getJSONArray("cse_image").getJSONObject(0).getString("src"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(jString);
			img = "images/genericcar.jpg";
		}
	}
}
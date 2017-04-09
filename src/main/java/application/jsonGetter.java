package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Reads jsons from URLs.
 * Found on stackoverflow.
 * @author Brandon
 *
 */
public class jsonGetter {
	private static String readUrl(String urlString) throws Exception { // God
																		// bless
																		// stack
																		// overflow
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public static JSONObject getJSON(String link) throws Exception {
		try {
			JSONObject json = new JSONObject(readUrl(link));
			return json;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

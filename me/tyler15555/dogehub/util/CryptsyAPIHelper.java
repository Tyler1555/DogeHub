package me.tyler15555.dogehub.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class CryptsyAPIHelper {

private static HashMap<String, String> apiMapCryptsy = new HashMap<String, String>();
	
	/**
	 * Loads api values into the Hashmap
	 */
	public static void init() {
		apiMapCryptsy.put("market", "singlemarketdata&marketid=132");
	}
	
	/**
	 * Makes a connection to the Cryptsy API
	 * @param apiArgument Determines what value the API will get for us
	 * @param usesAPIKey Whether or not this response needs an API Key
	 */
	private static String connectToAPI(String apiArgument, boolean usesAPIKey) {
		HttpURLConnection cryptsyAPIConnection = null;
		StringBuffer returningResponse = null;
		try {
			URL cryptsyAPIURL = new URL("http://pubapi.cryptsy.com/api.php?method=" + apiArgument);
			cryptsyAPIConnection = (HttpURLConnection)cryptsyAPIURL.openConnection();
			if(usesAPIKey) {
				cryptsyAPIConnection.setRequestMethod("POST");
			} else {
				cryptsyAPIConnection.setRequestMethod("GET");
			}
			cryptsyAPIConnection.setUseCaches(true); //Cryptsy will shut us out after a while without this for some reason
			cryptsyAPIConnection.setDoInput(true);
			cryptsyAPIConnection.setDoOutput(true);
			
			InputStream is = cryptsyAPIConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while((line = reader.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			reader.close();
			returningResponse = response;
		} catch(Exception e) {
			System.out.println("Couldn't connect to the Cryptsy API due to: " + e.getMessage());
			e.printStackTrace();
			return "[E]";
		} finally {
			cryptsyAPIConnection.disconnect();
		}
		return returningResponse.toString();
	}
	
	/**
	 * Does the rather messy job of extracting data from the JSON that Cryptsy returns
	 * @param requestedData The data you want from the JSON 
	 * @param dataToParse The JSON Data
	 * @return The requested data out of the JSON data provided
	 */
	@SuppressWarnings("rawtypes")
	private static String parseCryptsyData(String requestedData, String dataToParse, boolean getTradeData) {
		if(dataToParse.equals("[E]")) {
			return "Sorry, an error occured while trying to connect to the Cryptsy API, please try again later.";
		}
		JsonParserFactory factory = JsonParserFactory.getInstance();
		JSONParser parser = factory.newJsonParser();
		Map data = parser.parseJson(dataToParse);
		HashMap returnedData = (HashMap) data.get("return"); //Since the nested JSON is stored as HashMaps inside of HashMaps, you need a ton of HashMaps to get the data. Thanks Obama...
		HashMap markets = (HashMap)returnedData.get("markets");
		HashMap dogeInfo = (HashMap)markets.get("DOGE");
		if(getTradeData) {
			ArrayList tradeData_tmp = (ArrayList)dogeInfo.get("recenttrades");
			HashMap tradeData = (HashMap)tradeData_tmp.get(2);
			return (String) tradeData.get(requestedData);
		}
		return (String)dogeInfo.get(requestedData);
	}
	
	
	public static String getMarketVolume() {
		return parseCryptsyData("volume", connectToAPI(apiMapCryptsy.get("market"), false), false);
	}
	
	public static String getMarketPrice() {
		return parseCryptsyData("price", connectToAPI(apiMapCryptsy.get("market"), false), true);
	}
	
}

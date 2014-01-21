package me.tyler15555.dogehub.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class DogeAPIHelper {

	/**
	 * Easy to get API listing
	 */
	
	private static HashMap<String, String> apiMapDoge = new HashMap<String, String>();
	
	/**
	 * Loads api values into the Hashmap
	 */
	public static void init() {
		apiMapDoge.put("diff", "get_difficulty");
		apiMapDoge.put("block", "get_current_block");
		apiMapDoge.put("wallet", "get_balance");
		apiMapDoge.put("address", "get_my_addresses");
	}
	
	/**
	 * Makes a connection to DogeAPI
	 * @param apiArgument Determines what value the API will get for us
	 * @param usesAPIKey Whether or not this response needs an API Key
	 */
	private static String connectToAPI(String apiArgument, boolean usesAPIKey, String apiKey) {
		HttpURLConnection dogeAPIConnection = null;
		StringBuffer returningResponse = null;
		try {
			URL dogeAPIURL = null;
			if(usesAPIKey) {
				dogeAPIURL = new URL("https://www.dogeapi.com/wow/?api_key=" + apiKey + "&a=" + apiArgument);
			} else {
				dogeAPIURL = new URL("https://www.dogeapi.com/wow/?a=" + apiArgument);
			}
			dogeAPIConnection = (HttpURLConnection)dogeAPIURL.openConnection();
			dogeAPIConnection.setRequestMethod("GET");
			dogeAPIConnection.setUseCaches(false);
			dogeAPIConnection.setDoInput(true);
			dogeAPIConnection.setDoOutput(true);
			
			InputStream is = dogeAPIConnection.getInputStream();
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
			System.out.println("Couldn't connect to DogeAPI.com due to: " + e.getMessage());
			e.printStackTrace();
		} finally {
			dogeAPIConnection.disconnect();
		}
		return returningResponse.toString();
	}
	
	public static String getDifficulty() {
		return connectToAPI(apiMapDoge.get("diff"), false, null);
	}
	
	public static String getCurrentBlock() {
		return connectToAPI(apiMapDoge.get("block"), false, null);
	}
	
	//At the moment, these both just use my API key as there is currently no code to fetch a user's API key from a file
	public static String getWalletBalance() {
		return connectToAPI(apiMapDoge.get("wallet"), true, "ap4vcw1nm0lo01t8b8lc181x7u0");
	}
	
	public static String getWalletAddress() {
		return connectToAPI(apiMapDoge.get("address"), true, "ap4vcw1nm0lo01t8b8lc181x7u0").replace('[', ' ').replace(']', ' ');
	}
	
}

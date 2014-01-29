package me.tyler15555.dogehub.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class DogeChainAPIHelper {

	/**
	 * Easy to get API listing
	 */
	
	private static HashMap<String, String> apiMapDogeChain = new HashMap<String, String>();
	
	/**
	 * Loads api values into the Hashmap
	 */
	public static void init() {
		apiMapDogeChain.put("bal", "addressbalance/");
	}
	
	/**
	 * Makes a connection to the DogeChain API
	 */
	private static String connectToAPI(String apiArgument) {
		HttpURLConnection dogeChainAPIConnection = null;
		StringBuffer returningResponse = null;
		try {
			URL dogeChainAPIURL = new URL("http://www.dogechain.info/chain/CHAIN/q/" + apiArgument);
			dogeChainAPIConnection = (HttpURLConnection)dogeChainAPIURL.openConnection();
			dogeChainAPIConnection.setRequestMethod("GET");
			dogeChainAPIConnection.setUseCaches(false);
			dogeChainAPIConnection.setDoInput(true);
			dogeChainAPIConnection.setDoOutput(true);
			
			InputStream is = dogeChainAPIConnection.getInputStream();
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
			System.out.println("Couldn't connect to dogeChainAPI.com due to: " + e.getMessage());
			e.printStackTrace();
			return "Sorry, a error occured trying to connect to the Dogechain API, please try again later.";
		} finally {
			dogeChainAPIConnection.disconnect();
		}
		return returningResponse.toString();
	}
	
	public static String getWalletBalance(String walletAddress) {
		return connectToAPI(apiMapDogeChain.get("bal") + walletAddress);
	}
	
}

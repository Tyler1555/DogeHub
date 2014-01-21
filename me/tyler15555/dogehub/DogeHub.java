package me.tyler15555.dogehub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import me.tyler15555.dogehub.util.CryptsyAPIHelper;
import me.tyler15555.dogehub.util.DogeAPIHelper;



public class DogeHub {
	
	public DogeHub() {
		
	}
	
	//Almost this entire method is a place holder
	public static void main(String[] args) {
		setup();
		DogeAPIHelper.init();
		CryptsyAPIHelper.init();
		System.out.println("Current Difficulty: " + DogeAPIHelper.getDifficulty());
		System.out.println("Current Block: " + DogeAPIHelper.getCurrentBlock());
		System.out.println("Wallet Balance: " + DogeAPIHelper.getWalletBalance());
		System.out.println("Market Volume: " + CryptsyAPIHelper.getMarketVolume());
		System.out.println("Market Price(In BTC): " + CryptsyAPIHelper.getMarketPrice());
		System.out.println("Address: " + DogeAPIHelper.getWalletAddress());
	}
	
	/**
	 * Does first time setup. i.e Creating data directory, account file, etc
	 */
	private static void setup() {
		File dataDir = new File(System.getenv("USERPROFILE") + "\\My Documents\\DogeHubData");
		File dataFile = new File(dataDir.getPath() + "\\account.txt");
		if(!dataDir.exists()) {
			System.out.println("Didn't find " + dataDir.getPath() + " . Creating now...");
			dataDir.mkdir();
			System.out.println("Created data directory!");
		}
		if(!dataFile.exists()) {
			System.out.println("Didn't find " + dataFile.getPath() + " . Creating now...");
			try {
				dataFile.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
				writer.write("#NOTE: All lines starting with a hash sign are skipped!");
				writer.newLine();
				writer.write("#Please do not delete all of the contents of this file! If you really messed something up, delete this file and restart Dogehub, a new file will be generated");
				writer.write("#Toggles data from Cryptsy on/off. Values should be set to true or false. Note, if you disable this data, market price and many other things will not work! In the future, we will not be dependent on Cryptsy for this data");
				writer.newLine();
				writer.write("useCryptsyData=true");
				writer.newLine();
				writer.write("#Your DogeAPI api key, you will need one if you want to use the DogeAPI wallet functions of this program.");
				writer.newLine();
				writer.write("apiKey=0");
				writer.newLine();
				writer.write("#The Address to your wallet, this wallet does not need to be hosted by DogeAPI.");
				writer.newLine();
				writer.write("walletAddress=0");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Error! Couldn't create and/or write to data file because: " + e.getMessage());
				e.printStackTrace();
			}
			System.out.println("Created data file!");
		}
	}

}

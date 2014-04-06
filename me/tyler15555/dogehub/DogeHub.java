package me.tyler15555.dogehub;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import me.tyler15555.dogehub.util.CryptsyAPIHelper;
import me.tyler15555.dogehub.util.DogeAPIHelper;
import me.tyler15555.dogehub.util.DogeChainAPIHelper;



public class DogeHub {
	
	public DogeHub() {
		
	}
	
	public static void main(String[] args) {
		DogeAPIHelper.init();
		CryptsyAPIHelper.init();
		DogeChainAPIHelper.init();
		createDogeHubGUI();
	}
	
	private static void createDogeHubGUI() {
		URL dogecoinIconURL = null;
		try {
			dogecoinIconURL = new URL("http://dogecoin.com/img/dogecoin-300.png");
		} catch (MalformedURLException e1) {
			System.out.println("[Dogehub] An error occured getting the Dogecoin icon, don't worry, this isn't a big deal");
			e1.printStackTrace();
		}
		JFrame dogeFrame = new JFrame("Dogehub");
		JLabel titleLabel = new JLabel("Wow, such data", new ImageIcon(dogecoinIconURL), JLabel.CENTER);
		JLabel diffLabel = new JLabel("Difficulty: " + DogeAPIHelper.getDifficulty());
		
		dogeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dogeFrame.pack();
		dogeFrame.setVisible(true);
		dogeFrame.setIconImage(new ImageIcon(dogecoinIconURL).getImage());
		
		titleLabel.setSize(50, 50);
		titleLabel.setVisible(true);
		titleLabel.setVerticalAlignment(JLabel.TOP);
		
		diffLabel.setVisible(true);
		
		dogeFrame.add(titleLabel);
		dogeFrame.add(diffLabel);
		dogeFrame.setSize(720, 640);
	}
	
    /* Currently unused as at the moment file reading functionality is broken
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
				writer.write("useCryptsyData=true");
				writer.newLine();
				writer.write("#Your DogeAPI api key");
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
			} finally {
				System.out.println("Created data file!");
			}
		}
		System.out.println("Starting to read data file...");
		DataHandler.readDataFile(dataFile);
		System.out.println("USE CRYPTSY DATA:" + DataHandler.getUseCryptsy());
	} */
}

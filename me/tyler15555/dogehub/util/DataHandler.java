package me.tyler15555.dogehub.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//At the moment this code is terribly broken for some reason so this class is unused
public class DataHandler {

	private static boolean useCryptsyData;
	
	public static void readDataFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			String line = scanner.next();
			
            while(line != null) {
            	System.out.println(line);
            	scanner.next();
            }
            scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Dogehub could not find it's data file! Many features will not work!");
			e.printStackTrace();
		} finally {
			System.out.println("Finished reading data file");
		}
	}
	
	public static boolean getUseCryptsy() {
		return useCryptsyData;
	}
	
}

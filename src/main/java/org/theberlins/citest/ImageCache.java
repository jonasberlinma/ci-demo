package org.theberlins.citest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class ImageCache {

	private static ImageCache imageCache = null;
	private static Vector<String[]> lines = new Vector<String[]>();;
	private static boolean isLoaded = false;
	private static Random random = null;

	private ImageCache() {
	}

	public static synchronized void setFileName(String fileName) throws IOException {
		if (!isLoaded) {
			random = new Random();
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			while (reader.ready()) {
				String line = reader.readLine();
				String[] entries = line.split(",");
				lines.addElement(entries);
			}
			reader.close();
			isLoaded = true;
		}
	}

	public static ImageCache getInstance() {
		if (imageCache == null) {
			imageCache = new ImageCache();
		}
		return imageCache;
	}
	public String[] getRandomEntry(){
		return lines.elementAt(random.nextInt(10000));
	}
}

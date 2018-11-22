package org.theberlins.citest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ImageRecognitionCaller extends Thread {

	private String dataFile;
	private String URL;
	private Logger log;
	private boolean showGraphics;
	private ImageRecognitionRenderer renderer;

	public ImageRecognitionCaller(String URL, String dataFile, Logger log, boolean showGraphics) {
		this.URL = URL;
		this.dataFile = dataFile;
		this.log = log;
		this.showGraphics = showGraphics;
		if (showGraphics) {
			renderer = new ImageRecognitionRenderer();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(dataFile));

			int sampleNo = 0;
			while (reader.ready()) {

				String line = reader.readLine();
				sampleNo++;
				String[] entries = line.split(",");
				JSONObject request = new JSONObject();

				for (int i = 0; i < entries.length; i++) {
					// JSONObject doesn't support type parameters :(
					request.put("C" + (i + 1), entries[i]);
				}
				String actualLabel = entries[entries.length - 1];

				Long startTime = System.currentTimeMillis();
				ImageRecognitionResult res = restTemplate.postForObject(URL, request, ImageRecognitionResult.class);
				Long endTime = System.currentTimeMillis();
				String errorMarker = "";
				if (actualLabel.compareTo(res.getRecognizedValue()) != 0) {
					errorMarker = "<<<<<<<<<<<<";
					if (showGraphics) {
						renderer.update(actualLabel, res.getRecognizedValue(), entries);
					}
				}
				log.info("Sample=" + sampleNo + " Actual=" + actualLabel + " Predicted=" + res.getRecognizedValue()
						+ " in " + (endTime - startTime) + " ms" + errorMarker);
			}
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

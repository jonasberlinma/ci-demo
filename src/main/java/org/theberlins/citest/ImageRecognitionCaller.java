package org.theberlins.citest;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ImageRecognitionCaller extends Thread {

	private String dataFile;
	private String URL;
	private Logger log;
	private String showGraphics;
	private int delay;
	private int errorDelay;
	private ImageRecognitionRenderer renderer;

	public ImageRecognitionCaller(String URL, String dataFile, Logger log, String showGraphics, int delay,
			int errorDelay) {
		this.URL = URL;
		this.dataFile = dataFile;
		this.log = log;
		this.showGraphics = showGraphics;
		this.delay = delay;
		this.errorDelay = errorDelay;
		if (showGraphics.compareTo("none") != 0) {
			renderer = new ImageRecognitionRenderer();
		}
	}

	@Override
	public void run() {

		RestTemplate restTemplate = new RestTemplate();

		try {

			ImageCache.setFileName(dataFile);
			ImageCache imageCache = ImageCache.getInstance();

			int sampleNo = 0;
			while (true) {

				Thread.sleep(delay);

				sampleNo++;

				JSONObject request = new JSONObject();

				String[] entries = imageCache.getRandomEntry();

				for (int i = 0; i < entries.length; i++) {
					// JSONObject doesn't support type parameters :(
					request.put("C" + (i + 1), entries[i]);
				}
				String actualLabel = entries[entries.length - 1];

				Long startTime = System.currentTimeMillis();
				ImageRecognitionResult res = restTemplate.postForObject(URL, request, ImageRecognitionResult.class);
				Long endTime = System.currentTimeMillis();
				String errorMarker = "";
				if (showGraphics.compareTo("all") == 0) {
					renderer.update(actualLabel, res.getPredictedValue(), res.getProbability(), entries);
					if(actualLabel.compareTo(res.getPredictedValue()) != 0)
					{
						Thread.sleep(errorDelay);
					}
				}
				if (actualLabel.compareTo(res.getPredictedValue()) != 0) {
					errorMarker = "<<<<<<<<<<<<";
					if (showGraphics.compareTo("error") == 0) {
						renderer.update(actualLabel, res.getPredictedValue(), res.getProbability(), entries);
					}
				}
				log.info("Sample=" + sampleNo + " Actual=" + actualLabel + " Predicted=" + res.getPredictedValue()
						+ " Probability=" + res.getProbability() + " in " + (endTime - startTime) + " ms"
						+ errorMarker);
			}
		} catch ( RestClientException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}

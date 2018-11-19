package org.theberlins.citest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ImageRecognitionCaller extends Thread{

	private String dataFile;
	private String URL;
	private Logger log;
	
	public ImageRecognitionCaller (String URL, String dataFile, Logger log){
		this.URL = URL;
		this.dataFile = dataFile;
		this.log = log;
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
				log.info("Sample=" + sampleNo + " Actual=" + actualLabel + " Predicted=" + res.getRecognizedValue() + " in "
						+ (endTime - startTime) + " ms");
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

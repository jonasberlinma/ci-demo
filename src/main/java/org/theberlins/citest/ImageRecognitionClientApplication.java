package org.theberlins.citest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ImageRecognitionClientApplication {

	private static final Logger log = LoggerFactory.getLogger(ImageRecognitionClientApplication.class);
	private static String URL;
	private static String dataFile;

	public static void main(String args[]) {
		URL = args[0];
		dataFile = args[1];
		RestTemplate restTemplate = new RestTemplate();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(dataFile));

			while (reader.ready()) {
				String line = reader.readLine();

				String[] entries = line.split(",");
				JSONObject request = new JSONObject();

				for (int i = 0; i < entries.length; i++) {
					request.put("C" + (i + 1), entries[i]);
				}
				String actualLabel = entries[entries.length - 1];

				ImageRecognitionResult res = restTemplate.postForObject(URL, request, ImageRecognitionResult.class);
				log.info("Actual : " + actualLabel + "; Predicted : " + res.getRecognizedValue());
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
	};
}

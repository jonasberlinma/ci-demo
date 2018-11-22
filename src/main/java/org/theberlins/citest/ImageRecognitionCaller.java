package org.theberlins.citest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ImageRecognitionCaller extends Thread {

	private String dataFile;
	private String URL;
	private Logger log;
	private boolean showGraphics;
	private JFrame frame;
	private JTextArea jta;
	private String[] errorImage;
	private String actual;
	private String recognized;

	public ImageRecognitionCaller(String URL, String dataFile, Logger log, boolean showGraphics) {
		this.URL = URL;
		this.dataFile = dataFile;
		this.log = log;
		this.showGraphics = showGraphics;
		if (showGraphics) {
			frame = new JFrame("Image " + ImageRecognitionCaller.currentThread().getName());
			jta = new JTextArea("<no data>");
			jta.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jta.setBounds(10, 10, 100, 40);
			frame.add(jta);
			frame.setSize(300, 300);
		}
		class MyPanel extends JPanel {
			@Override
			public void paint(Graphics g) {
				if (errorImage != null) {
					jta.setText("Actual=" + actual + "\nRecognized=" + recognized);
					for (int i = 0; i < 28; i++) {
						for (int j = 0; j < 28; j++) {
							int point = j * 28 + i;
							if (new Integer(errorImage[point]).intValue() > 0) {
								int color = new Integer(errorImage[point]).intValue();
								g.setColor(new Color(color, color, color));
								g.fillRect(i * 10 + 10, j * 10 + 10, 10, 10);
							}
						}
					}
				}
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(300, 300);
			}
		}
		if (showGraphics) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(new MyPanel());
			frame.pack();
			frame.setVisible(true);
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
					errorImage = entries;
					actual = actualLabel;
					recognized = res.getRecognizedValue();
					errorMarker = "<<<<<<<<<<<<";
					if (showGraphics) {
						frame.update(frame.getGraphics());
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

package org.theberlins.citest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class ImageRecognitionRenderer {
	private JFrame frame;
	private JTextArea jta;
	private String[] errorImage;
	private String actual;
	private String predicted;
	private double probability;
	
	ImageRecognitionRenderer(){
		frame = new JFrame("Image " + ImageRecognitionCaller.currentThread().getName());
		jta = new JTextArea("<no data>");
		jta.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		jta.setBounds(10, 10, 100, 40);
		frame.add(jta);
		frame.setSize(300, 300);
		class MyPanel extends JPanel {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				if (errorImage != null) {
					jta.setText("Actual=" + actual + "\nPredicted=" + predicted + "\nProbability=" + probability);
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MyPanel());
		frame.pack();
		frame.setVisible(true);
	}
	public void update(String actual, String predicted, double probability, String[] image){
		this.actual = actual;
		this.predicted = predicted;
		this.probability = probability;
		this.errorImage = image;
		frame.update(frame.getGraphics());
	}
}

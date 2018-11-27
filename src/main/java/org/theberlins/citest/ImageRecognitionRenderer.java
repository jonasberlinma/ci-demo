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
	private static int blockSize = 5;
	private static int gridSize = 28;
	private JFrame frame;
	private JTextArea jta;
	private String[] errorImage;
	private String actual;
	private String predicted;
	private double probability;

	ImageRecognitionRenderer() {
		frame = new JFrame("Image " + ImageRecognitionCaller.currentThread().getName());
		jta = new JTextArea("<no data>");
		jta.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		jta.setBounds(10, 10, 140, 60);
		frame.add(jta);
		frame.setSize(300, 300);
		class MyPanel extends JPanel {

			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				g.setColor(new Color(0, 0, 0));
				g.fillRect(10, 80, gridSize * blockSize, gridSize * blockSize);
				if (errorImage != null) {
					jta.setText("Actual=" + actual + "\nPredicted=" + predicted + "\nProbability="
							+ Math.round(probability * 1000) / ((double) 1000));
					g.setColor(new Color(0, 0, 0));
					g.drawRect(10, 80, gridSize * blockSize, gridSize * blockSize);
					for (int i = 0; i < gridSize; i++) {
						for (int j = 0; j < gridSize; j++) {
							int point = j * gridSize + i;
							if (new Integer(errorImage[point]).intValue() > 0) {
								int color = new Integer(errorImage[point]).intValue();
								g.setColor(new Color(color, actual.compareTo(predicted) != 0 ? 0 : color,
										actual.compareTo(predicted) != 0 ? 0 : color));
								g.fillRect(i * blockSize + 10, j * blockSize + 80, blockSize, blockSize);
							}
						}
					}
				}
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(gridSize * blockSize + 20, gridSize * blockSize + 90);
			}
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MyPanel());
		frame.pack();
		frame.setVisible(true);
	}

	public void update(String actual, String predicted, double probability, String[] image) {
		this.actual = actual;
		this.predicted = predicted;
		this.probability = probability;
		this.errorImage = image;
		frame.update(frame.getGraphics());
	}
}

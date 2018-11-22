package org.theberlins.citest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageRecognitionClientApplication {

	private static final Logger log = LoggerFactory.getLogger(ImageRecognitionClientApplication.class);
	private static String URL;
	private static String dataFile;
	private static int nThreads = 1;
	private static int delay = 0;
	private static String showGraphics = "none";

	public static void main(String args[]) {
		Iterator<String> argi = new Vector<String>(Arrays.asList(args)).iterator();
		while (argi.hasNext()) {
			String theArg = argi.next();
			switch (theArg) {
			case "-url":
				URL = argi.next();
				break;
			case "-data":
				dataFile = argi.next();
				break;
			case "-delay":
				delay = new Integer(argi.next()).intValue();
				break;
			case "-threads":
				nThreads = new Integer(argi.next()).intValue();
				break;
			case "-showGraphics":
				showGraphics = argi.next();
				switch (showGraphics) {
				case "all":
					break;
				case "none":
					break;
				case "error":
					break;
				default:
					System.err.println("Only \"all\", \"none\", and \"error\" are allowed for -showGraphics");
					System.exit(1);
				}
				break;
			default:
				System.err.println("Unknown argument \"" + theArg + "\"");
				System.exit(1);
			}

		}

		ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger(Logger.ROOT_LOGGER_NAME);

		rootLogger.setLevel(ch.qos.logback.classic.Level.INFO);

		Vector<ImageRecognitionCaller> callers = new Vector<ImageRecognitionCaller>();
		for (long iThread = 0; iThread < nThreads; iThread++) {
			ImageRecognitionCaller irc = new ImageRecognitionCaller(URL, dataFile, log, showGraphics, delay);
			callers.add(irc);
			irc.start();
		}

		for (ImageRecognitionCaller irc : callers) {
			try {
				irc.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};
}

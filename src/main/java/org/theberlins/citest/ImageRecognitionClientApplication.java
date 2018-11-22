package org.theberlins.citest;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageRecognitionClientApplication {

	private static final Logger log = LoggerFactory.getLogger(ImageRecognitionClientApplication.class);
	private static String URL;
	private static String dataFile;

	public static void main(String args[]) {
		URL = args[0];
		dataFile = args[1];
		long nThreads = new Long(args[2]).longValue();

		ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger(Logger.ROOT_LOGGER_NAME);

		rootLogger.setLevel(ch.qos.logback.classic.Level.INFO);
		
		Vector<ImageRecognitionCaller> callers = new Vector<ImageRecognitionCaller>();
		for(long iThread = 0; iThread < nThreads; iThread++){
			ImageRecognitionCaller irc = new ImageRecognitionCaller(URL, dataFile, log, nThreads == 1);
			callers.add(irc);
			irc.start();
		}
		
		for(ImageRecognitionCaller irc : callers){
			try {
				irc.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};
}

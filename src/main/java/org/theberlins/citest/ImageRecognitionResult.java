package org.theberlins.citest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageRecognitionResult {
	   private String type;
	   private String recognizedValue;
	   
	   public ImageRecognitionResult(String type, String recognizedValue){
		   this.type = type;
		   this.recognizedValue = recognizedValue;
	   }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public String getRecognizedValue() {
	        return recognizedValue;
	    }

	    public void setRecognizedValue(String recognizedValue) {
	        this.recognizedValue = recognizedValue;
	    }

	    @Override
	    public String toString() {
	        return "ImageIdentity{" +
	                "type='" + type + '\'' +
	                ", recognizedValue='" + recognizedValue +
	                '}';
	    }
}

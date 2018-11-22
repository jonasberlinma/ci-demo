package org.theberlins.citest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageRecognitionResult {
	   private String type;
	   private String predictedValue;
	   
	   public ImageRecognitionResult(){
		   
	   }
	   public ImageRecognitionResult(String type, String predictedValue){
		   this.type = type;
		   this.predictedValue = predictedValue;
	   }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public String getPredictedValue() {
	        return predictedValue;
	    }

	    public void setPredictedValue(String predictedValue) {
	        this.predictedValue = predictedValue;
	    }

	    @Override
	    public String toString() {
	        return "ImageIdentity{" +
	                "type='" + type + '\'' +
	                ", predictedValue='" + predictedValue +
	                '}';
	    }
}

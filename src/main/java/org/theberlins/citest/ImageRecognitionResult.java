package org.theberlins.citest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageRecognitionResult {
	   private String type;
	   private String predictedValue;
	   private double probability;
	   
	   public ImageRecognitionResult(){
		   
	   }
	   public ImageRecognitionResult(String type, String predictedValue, double probability){
		   this.type = type;
		   this.predictedValue = predictedValue;
		   this.probability = probability;
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
	    
	    public double getProbability(){
	    	return probability;
	    }
	    
	    public void setProbability(double probability){
	    	this.probability = probability;
	    }

	    @Override
	    public String toString() {
	        return "ImageIdentity{" +
	                "type='" + type + '\'' +
	                ", predictedValue='" + predictedValue +
	                "', probability=" + probability +
	                '}';
	    }
}

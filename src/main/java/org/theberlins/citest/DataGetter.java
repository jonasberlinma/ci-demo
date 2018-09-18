package org.theberlins.citest;

public class DataGetter {

	private String message = null;
	public DataGetter(){
		message = "Initial Message";
	}
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}
}

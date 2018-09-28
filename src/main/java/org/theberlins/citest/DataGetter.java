package org.theberlins.citest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataGetter {

	private String message = null;

	public DataGetter() {
		message = "22 ";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@RequestMapping("/")
	public String index(){
		return (getMessage());
	}
}

package org.theberlins.citest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataGetter {

	private String message = null;

	public DataGetter() {
		message = "Initial Message";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@RequestMapping("/")
	public String index(){
		return ("This is Spring Boot, Version 4");
	}
}

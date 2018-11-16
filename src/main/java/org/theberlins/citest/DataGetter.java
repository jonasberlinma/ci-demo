package org.theberlins.citest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataGetter {

	private String message = null;

	public DataGetter() {
		message = "24 ";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@RequestMapping("/staticNumber")
	public String staticNumber() {
		return (getMessage());
	}

	@RequestMapping("/")
	public String index() {
		return ("Index{ type='SUCCESS' }");
	}
}

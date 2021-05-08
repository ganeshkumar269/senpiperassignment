package com.example.restservice.model;

import lombok.Getter;
import lombok.Setter;

public class CustomRequestParam {
	@Getter
	@Setter
	private String findByType;

	@Getter
	@Setter
	private String value1;

	@Getter
	@Setter
	private String value2;


	public CustomRequestParam(){}

}
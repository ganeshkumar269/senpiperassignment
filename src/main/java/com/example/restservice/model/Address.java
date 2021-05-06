package com.example.restservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;


public class Address {
	@Getter
	@Setter
	@NotNull
	private String detailedAddress;

	@Getter
	@Setter
	@NotNull
	private String city;

	@Getter
	@Setter
	@NotNull
	private String state;

	@Getter
	@Setter
	@NotNull
	@Pattern(regexp = "^\\d{6}$")
	private int pincode;

	public Address(){}
	public Address(String detailedAddress,String city,String state,int pincode){
		this.detailedAddress = detailedAddress;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

}
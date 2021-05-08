package com.example.restservice.model;
import org.springframework.data.annotation.Id;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.time.LocalTime;

// import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class TrainingCenter {

	@Id
	private String id;

	@Getter
	@Setter
	@NotNull
	@Size(min=1,max=40)
	private String centerName;

	@Getter
	@Setter
	@NotNull
	@Size(min=12,max=12)
	@Pattern(regexp = "^[A-Za-z0-9]+$")
	private String centerCode; 

	@Getter
	@Setter
	@NotNull
	private Address address; 

	@Getter
	@Setter
	@Min(0)
	private int studentCapacity; 

	@Getter
	@Setter
	private List<String> coursesOffered; 

	@Getter
	private long createdOn; 

	@Getter
	@Email
	private String contactEmail; 

	@Getter
	@NotNull
	@Pattern(regexp = "^\\d{10}$")
	private String contactPhone; 


	public TrainingCenter(){}
	public TrainingCenter(String centerName,String centerCode,Address address,int studentCapacity,List<String> coursesOffered, String contactEmail,String contactPhone){
					this.centerCode = centerCode;
					this.centerName = centerName;
					this.address = address;
					this.studentCapacity = studentCapacity;
					this.coursesOffered = coursesOffered;
					this.contactEmail = contactEmail;
					this.contactPhone = contactPhone;
					this.createdOn = System.currentTimeMillis();
				}
	public void updateCreatedOn(){this.createdOn = System.currentTimeMillis();}

}
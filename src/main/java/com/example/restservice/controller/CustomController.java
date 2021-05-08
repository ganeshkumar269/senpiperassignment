package com.example.restservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import com.example.restservice.repository.TrainingCenterRepository;
import com.example.restservice.model.TrainingCenter;
import com.example.restservice.exception.ValueNotFoundException;
import com.example.restservice.model.CustomRequestParam;


@RestController
public class CustomController {

	@Autowired
	private TrainingCenterRepository tcRepository;

	org.slf4j.Logger logger = LoggerFactory.getLogger(CustomController.class);


	//Home Page
    @CrossOrigin(origins = "*")
	@GetMapping("/")
	public Map<String,String> defaultMessage() {
		Map<String,String> respMap = new HashMap<String,String>();
		respMap.put("Hello?","World!");
		return respMap;
	}

	//Test Route
	@CrossOrigin(origins="*")
	@GetMapping("/test")
	public ResponseEntity<Object> test(CustomRequestParam params){
		logger.info(params.getFindByType());
		return 	new ResponseEntity<Object>(new Object(),HttpStatus.OK);
	}


	
	/* 
		addTrainingCenter takes a requestBody of the format defined in TrainingCenter class
		the handler updates the trainingCenter and saves it to the database
		it then returns the updated object
	*/
	@CrossOrigin(origins="*")
	@PostMapping(value = "/addTrainingCenter", consumes = "application/json", produces = "application/json")
	public ResponseEntity<TrainingCenter> addTrainingCenter(@Valid @RequestBody TrainingCenter tc){
		
		//Created On Value must be assigned in the server, not by client
		tc.updateCreatedOn();
		
		//save the trainingCenter Object in the database
		tcRepository.save(tc);

		//return the updated object with an appropriate HttpStatus
		return new ResponseEntity<TrainingCenter>(tc,HttpStatus.OK);
	}

	/*
		getTrainingCenters takes relevant parameters and sends a list of trainingCenters as response
		The paramters are of format defined in the class CustomRequestParam

		CustomRequestParam contains a findByType parameter which decides the attribute on
		which the filter must takeplace.
			Eg. if findByType is centerCode
				the handler fetches the trainingCenters with value1 as its centerCode


		the handler fetches the trainingCenters from database according the parameters given
		(more information on the find operations supported can be found in TrainingCenterRepository)
		it then returns the fetched objects as a list
	*/
	@CrossOrigin(origins="*")
	@GetMapping(value = "/getTrainingCenters",produces = "application/json")
	public ResponseEntity<List<TrainingCenter>> getTrainingCenters(@Valid CustomRequestParam params)
	{
		List<TrainingCenter> list = new ArrayList<>();
		HttpStatus status = HttpStatus.OK;
		String findByType = params.getFindByType();
		String value1 = params.getValue1();
		String value2 = params.getValue2();
		switch(findByType){
			case "all":
				{
					list = tcRepository.findAll();
				}
				break;
			case "centerCode": 
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						throw new ValueNotFoundException("no param called value1 defined");
					}
					TrainingCenter tc = tcRepository.findByCenterCode(value1);
					if(tc == null){
						status = HttpStatus.NOT_FOUND;
					}
					else{
						status = HttpStatus.OK;
						list.add(tc);
					}
				}
				break;
			case "centerName":
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						throw new ValueNotFoundException("no param called value1 defined");
					}
					list = tcRepository.findByCenterName(value1);
					if(list == null) status = HttpStatus.BAD_REQUEST;
					else if(list.size() == 0) status = HttpStatus.NOT_FOUND;
					else status = HttpStatus.OK;
				}
				break;
			case "studentCapacityRange":
				{
					if(value1 == null || value2 == null) {
						status = HttpStatus.BAD_REQUEST;
						throw new ValueNotFoundException("no param called value1 or value2 are defined");
					}
					list = tcRepository.findByStudentCapacityRange(Integer.parseInt(value1),Integer.parseInt(value2));
					status = list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
				}
				break;
			case "addressCity":
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						throw new ValueNotFoundException("no param called value1 defined");
					}
					list = tcRepository.findByAddressCity(value1);
					status = list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
				}
				break;
			case "addressState":
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						throw new ValueNotFoundException("no param called value1 defined");
					}
					list = tcRepository.findByAddressState(value1);
					status = list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
				}
				break;
			case "addressPincode":
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						throw new ValueNotFoundException("no param called value1 defined");
					}
					list = tcRepository.findByAddressPincode(Integer.parseInt(value1));
					status = list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
				}
				break;
			default:
				status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<List<TrainingCenter>>(list,status);
	}

}
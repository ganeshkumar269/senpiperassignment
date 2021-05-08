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
// import org.slf4j.Logger

import com.example.restservice.repository.TrainingCenterRepository;
import com.example.restservice.model.TrainingCenter;
import com.example.restservice.exception.ValueNotFoundException;
import com.example.restservice.model.CustomRequestParam;


@RestController
public class CustomController {

	@Autowired
	private TrainingCenterRepository tcRepository;

	org.slf4j.Logger logger = LoggerFactory.getLogger(CustomController.class);

    @CrossOrigin(origins = "*")
	@GetMapping("/")
	public Map<String,String> defaultMessage() {
		Map<String,String> respMap = new HashMap<String,String>();
		respMap.put("Hello?","World!");
		return respMap;
	}

	@CrossOrigin(origins="*")
	@GetMapping("/test")
	public ResponseEntity<Object> test(CustomRequestParam params){
		logger.info(params.getFindByType());
		return 	new ResponseEntity<Object>(new Object(),HttpStatus.OK);
	}

	@CrossOrigin(origins="*")
	@PostMapping(value = "/addTrainingCenter", consumes = "application/json", produces = "application/json")
	public ResponseEntity<TrainingCenter> addTrainingCenter(@Valid @RequestBody TrainingCenter tc){
		// System.out.println(tc.getCenterName());
		tc.updateCreatedOn();
		try{
			tcRepository.save(tc);
			return new ResponseEntity<TrainingCenter>(tc,HttpStatus.OK);
		}
		catch(IllegalArgumentException ex){
			logger.error("addTrainingCenter: Given Training Center Object has null attributes");
			ex.printStackTrace();
			return new ResponseEntity<TrainingCenter>(tc,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// System.out.println(System.getenv("testenvvar"));
	}

	@CrossOrigin(origins="*")
	@GetMapping(value = "/getTrainingCenters",produces = "application/json")
	public ResponseEntity<List<TrainingCenter>> getTrainingCenters
		(
			@RequestParam(value="findByType",required=false,defaultValue="all") String findByType,
			@RequestParam(value="value1",required=false) String value1,
			@RequestParam(value="value2",required=false) String value2
		)
	{
		List<TrainingCenter> list = new ArrayList<>();
		HttpStatus status = HttpStatus.OK;
		logger.info(value1);
		logger.info(value2);
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
						break;
					}
					TrainingCenter tc = tcRepository.findByCenterCode(value1);
					if(tc == null){
						status = HttpStatus.NOT_FOUND;
					}
					else{
						status = HttpStatus.OK;
						list.add(tc);
					}
					// return new ResponseEntity<List<TrainingCenter>>(list,status);
				}
				break;
			case "centerName":
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						throw new ValueNotFoundException("no param called value1 defined");
						// break;
					}
					list = tcRepository.findByCenterName(value1);
					if(list == null) status = HttpStatus.BAD_REQUEST;
					else if(list.size() == 0) status = HttpStatus.NOT_FOUND;
					else status = HttpStatus.OK;
					// return new ResponseEntity<List<TrainingCenter>>(list,status);
				}
				break;
			case "studentCapacityRange":
				{
					if(value1 == null || value2 == null) {
						status = HttpStatus.BAD_REQUEST;
						break;
					}
					list = tcRepository.findByStudentCapacityRange(Integer.parseInt(value1),Integer.parseInt(value2));
					status = list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
					// return new ResponseEntity<List<TrainingCenter>>(list,status);
				}
				break;
			case "addressCity":
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						break;
					}
					list = tcRepository.findByAddressCity(value1);
					status = list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
					// return new ResponseEntity<List<TrainingCenter>>(list,status);
				}
				break;
			case "addressState":
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						break;
					}
					list = tcRepository.findByAddressState(value1);
					status = list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
					// return new ResponseEntity<List<TrainingCenter>>(list,status);
				}
				break;
			case "addressPincode":
				{
					if(value1 == null) {
						status = HttpStatus.BAD_REQUEST;
						break;
					}
					list = tcRepository.findByAddressPincode(Integer.parseInt(value1));
					status = list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
					// return new ResponseEntity<List<TrainingCenter>>(list,status);
				}
				break;
			default:
				status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<List<TrainingCenter>>(list,status);
	}

}
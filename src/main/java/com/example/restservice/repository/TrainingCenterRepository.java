package com.example.restservice.repository;


import com.example.restservice.model.TrainingCenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface TrainingCenterRepository extends MongoRepository<TrainingCenter, ArrayList> {
	/*find by 
		centerCode,
		centerName,
		studentCapacity range,
		address->city,state,pincode,
	*/
	public TrainingCenter findByCenterCode(String centerCode);

	public List<TrainingCenter> findByCenterName(String centerName);

	public List<TrainingCenter> findByStudentCapacity(int cap);

	@Query(value = "{'studentCapacity' : {$gte : ?0, $lte : ?1 } }")
	public List<TrainingCenter> findByStudentCapacityRange(int min,int max);

	@Query(value = "{'address.city' : { '$eq' : '?0' } }")
	public List<TrainingCenter> findByAddressCity(String city);

	@Query(value = "{'address.state' : { '$eq' : '?0' } }")
	public List<TrainingCenter> findByAddressState(String state);

	@Query(value = "{'address.pincode' : { '$eq' : '?0' } }")
	public List<TrainingCenter> findByAddressPincode(int pincode);

}
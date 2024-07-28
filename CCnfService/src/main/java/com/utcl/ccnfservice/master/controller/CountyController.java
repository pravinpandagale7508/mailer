package com.utcl.ccnfservice.master.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.County;
import com.utcl.ccnfservice.master.service.CountyService;
import com.utcl.dto.ccnf.CountyDto;


@RestController
@RequestMapping("/api/county")
public class CountyController {
	
	private CountyService countyService;
	

	public CountyController(CountyService countyService) 
	{
		super();
		this.countyService = countyService;
	}
	
	@PostMapping("/addCounty")
	public ResponseEntity<CountyDto> addCountyDetails(@RequestBody CountyDto agencyDto) 
	{
		 return ResponseEntity.ok(countyService.addCountyDetails(agencyDto));

	}
	
	@GetMapping("/getCountyById/{id}")
	public County getCountyById(@PathVariable("id") long agentId) throws Exception
	{
		return countyService.getCountyById(agentId);
	}

	@GetMapping("/getCountyDetails")
	public List<County> getCountyDetails() 
	{
		return countyService.getCountyDetails();
	}
	
	@PutMapping("/updateCounty")
	public ResponseEntity<CountyDto> updateCountyDetails(@RequestBody CountyDto agencyDto) 
	{
		 return ResponseEntity.ok(countyService.addCountyDetails(agencyDto));

	}

	
	
	
}

package com.utcl.ccnfservice.master.controller;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.I2district;
import com.utcl.ccnfservice.master.service.I2districtService;
import com.utcl.dto.ccnf.I2districtDto;
import com.utcl.dto.ccnf.requestResponce.DepotIds;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/i2district")
@Slf4j
public class I2districtController {
	
	private I2districtService i2districtService;
	

	public I2districtController(I2districtService i2districtService) {
		super();
		this.i2districtService = i2districtService;
	}
	@PostMapping("/addI2district")
	public  ResponseEntity<I2districtDto> addI2districtDetails(@RequestBody I2districtDto i2districtDto) {
		 return ResponseEntity.ok(i2districtService.addI2districtDetails(i2districtDto));

	}
	
	@GetMapping("/getI2districtById/{distId}")
	public I2district getI2districtByDistId(@PathVariable("distId") long distId)throws Exception {
		return i2districtService.getI2districtByDistId(distId);
	}

	
	@GetMapping("/getI2districtDetails")
	public List<I2district> getGubPlantCodeDetails() {
		return i2districtService.getI2districtDetails();
	}

	@PutMapping("/updateI2district")
	public  ResponseEntity<I2districtDto> updateI2districtDetails(@RequestBody I2districtDto i2districtDto) {
		 return ResponseEntity.ok(i2districtService.addI2districtDetails(i2districtDto));

	}
	
	
	@PostMapping("/getI2districtByDepoIdDetails")
	public List<I2district> getDepotRegionDetails(@RequestBody DepotIds depotIds) {
		log.info("Inside i2district controller");
		return i2districtService.getI2districtByDepotId(depotIds.getDepotIds());
	}
	
	

	
}

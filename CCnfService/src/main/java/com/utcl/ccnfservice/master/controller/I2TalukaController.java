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
import lombok.extern.slf4j.Slf4j;

import com.utcl.ccnfservice.master.entity.I2Taluka;
import com.utcl.ccnfservice.master.service.I2TalukaService;
import com.utcl.dto.ccnf.I2TalukaDto;


@RestController
@RequestMapping("/api/i2Taluka")
@Slf4j
public class I2TalukaController {
	
	private I2TalukaService i2TalukaService;
	

	public I2TalukaController(I2TalukaService i2TalukaService) {
		super();
		this.i2TalukaService = i2TalukaService;
	}
	@PostMapping("/addI2Taluka")
	public  ResponseEntity<I2TalukaDto> addI2TalukaDetails(@RequestBody I2TalukaDto i2TalukaDto) {
		 return ResponseEntity.ok(i2TalukaService.addI2TalukaDetails(i2TalukaDto));

	}
	
	@GetMapping("/getI2TalukaById/{id}")
	public I2Taluka getI2TalukaByDistId(@PathVariable("id") long id)throws Exception {
		return i2TalukaService.getI2TalukaByDistId(id);
	}

	
	@GetMapping("/getI2TalukaDetails")
	public List<I2Taluka> getGubPlantCodeDetails() {
		return i2TalukaService.getI2TalukaDetails();
	}

	@PutMapping("/updateI2Taluka")
	public  ResponseEntity<I2TalukaDto> updateI2TalukaDetails(@RequestBody I2TalukaDto i2TalukaDto) {
		i2TalukaDto= i2TalukaService.addI2TalukaDetails(i2TalukaDto);
		 return ResponseEntity.ok(i2TalukaDto);

	}
	
	@PostMapping("/getI2TalukabyI2DistricsIds")
	public List<I2Taluka> getI2TalukabyI2DistricsIds(@RequestBody Map<String,Object> data) {
		log.info("Inside geoDistId controller");
		return i2TalukaService.getI2TalukabyI2DistricsIds((List<Long>) data.get("geoDistId"));
	}
	
	

	
}

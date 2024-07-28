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

import com.utcl.ccnfservice.master.entity.RmcPlantCode;
import com.utcl.ccnfservice.master.service.RmcPlantCodeService;
import com.utcl.dto.ccnf.RmcPlantCodeDto;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/api/rmcPlantCode")
@Slf4j
public class RmcPlantCodeController {

	private RmcPlantCodeService rmcService;

	public RmcPlantCodeController(RmcPlantCodeService rmcService) {
			super();
			this.rmcService = rmcService;
		}

	@PostMapping("/addRmcPlantCode")
	public ResponseEntity<RmcPlantCodeDto> addRmcPlantCodeDetails(@RequestBody RmcPlantCodeDto depotDto) {
		 return ResponseEntity.ok(rmcService.addRmcPlantCodeDetails(depotDto));
	}
	
	@GetMapping("/getRmcPlantCodeById/{id}")
	public RmcPlantCode getRmcPlantCodeById(@PathVariable("id") long depotId)throws Exception {
		return rmcService.getRmcPlantCodeById(depotId);
	}
	
	

	@GetMapping("/getRmcPlantCodeDetails")
	public List<RmcPlantCode> getRmcPlantCodeDetails() {
		return rmcService.getRmcPlantCodeDetails();
	}

	@PutMapping("/updateRmcPlantCode")
	public ResponseEntity<RmcPlantCodeDto> updateRmcPlantCodeDetails(@RequestBody RmcPlantCodeDto depotDto) {
		 return ResponseEntity.ok(rmcService.addRmcPlantCodeDetails(depotDto));
	}
	
	
	
	
	
}

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

import com.utcl.ccnfservice.master.entity.GubtPlantCode;
import com.utcl.ccnfservice.master.service.GubPlantCodeService;
import com.utcl.dto.ccnf.GubtPlantCodeDto;


@RestController
@RequestMapping("/api/gubPlant")
public class GubPlantCodeController {
	
	private GubPlantCodeService gubPlantCodeService;
	

	public GubPlantCodeController(GubPlantCodeService gubPlantCodeService) {
		super();
		this.gubPlantCodeService = gubPlantCodeService;
	}
	
	@PostMapping("/addGubPlantCode")
	public ResponseEntity<GubtPlantCodeDto> addGubPlantCodeDetails(@RequestBody GubtPlantCodeDto gubPlantCodeDto) {
		 return ResponseEntity.ok(gubPlantCodeService.addGubPlantCodeDetails(gubPlantCodeDto));
	}
	

	@GetMapping("/getGubPlantCodeDetailsById/{gubId}")
	public GubtPlantCode getGubPlantCodeByGubId(@PathVariable("gubId") long gubId)throws Exception {
		return gubPlantCodeService.getGubPlantCodeByGubId(gubId);
	}
	
	
	@GetMapping("/getGubPlantDetails")
	public List<GubtPlantCode> getGubPlantCodeDetails() {
		return gubPlantCodeService.getGubPlantCodeDetails();
	}

	@PutMapping("/updateGubPlantCode")
	public ResponseEntity<GubtPlantCodeDto> updateGubPlantCodeDetails(@RequestBody GubtPlantCodeDto gubPlantCodeDto) {
		 return ResponseEntity.ok(gubPlantCodeService.addGubPlantCodeDetails(gubPlantCodeDto));
	}

	
	
	
}

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

import com.utcl.ccnfservice.master.entity.Sloc;
import com.utcl.ccnfservice.master.service.SlocService;
import com.utcl.dto.ccnf.SlocDto;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/sloc")
@Slf4j
public class SlocController {
	
	private SlocService slocService;
	

	public SlocController(SlocService slocService) {
		super();
		this.slocService = slocService;
	}
	
	@PostMapping("/addSloc")
	public ResponseEntity<SlocDto> addSlocDetails(@RequestBody SlocDto slocDto) {
		 return ResponseEntity.ok(slocService.addSlocDetails(slocDto));

	}
	
	@GetMapping("/getSlocById/{slocIds}")
	public Sloc getSlocDetailsBySlocId(@PathVariable("slocIds") long slocIds)throws Exception {
		return slocService.getSlocBySlocId(slocIds);
	}
	
	@GetMapping("/getSlocDetails")
	public List<Sloc> getSlocDetails() {
		return slocService.getSlocDetails();
	}

	@PutMapping("/updateSloc")
	public ResponseEntity<SlocDto> updateSlocDetails(@RequestBody SlocDto slocDto) {
		 return ResponseEntity.ok(slocService.addSlocDetails(slocDto));

	}
	
	@GetMapping("/getSlocByDistId/{distId}")
	public Sloc getSlocDetailsByDistId(@PathVariable("distId") long distId)throws Exception {
		return slocService.getSlocDetailsByDistId(distId);
	}
	
	@PostMapping("/getSlocDetailsByI2districtId")
	public List<Sloc> getSlocDetailsByI2districtId(@RequestBody Map<String,Object> data) {
		log.info("Inside sloc controller");
		return slocService.getSlocDetailsByI2districtId((List<Long>) data.get("distId"));
	}
}

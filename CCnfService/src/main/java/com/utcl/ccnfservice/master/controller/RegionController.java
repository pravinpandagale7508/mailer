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

import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.ccnfservice.master.service.RegionService;
import com.utcl.dto.ccnf.RegionDto;
import com.utcl.dto.ccnf.requestResponce.StateIds;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/region")
@Slf4j
public class RegionController {
	
	private RegionService regionService;
	

	public RegionController(RegionService regionService) {
		super();
		this.regionService = regionService;
	}
	
	@PostMapping("/addRegion")
	public ResponseEntity<RegionDto> addRegionDetails(@RequestBody RegionDto regionDto) {
		log.info("Inside region controller"+regionDto);
		 return ResponseEntity.ok(regionService.addRegionDetails(regionDto));
	}
	@GetMapping("/getRegionById/{regionId}")
	public Region getRegionByRegionId(@PathVariable("regionId") long regionId)throws Exception {
		return regionService.getRegionByRegionId(regionId);
	}
	

	@GetMapping("/getRegionDetails")
	public List<Region> getRegionDetails() {
		return regionService.getRegionDetails();
	}

	
	
	@PutMapping("/updateRegion")
	public ResponseEntity<RegionDto> updateRegionDetails(@RequestBody RegionDto regionDto) {
		log.info("Inside region controller"+regionDto);
		 return ResponseEntity.ok(regionService.addRegionDetails(regionDto));
	}
	
	@PostMapping("/getRegionByStateIds")
	public List<Region> getRegionByStateIds(@RequestBody StateIds data) {
		log.info("Inside getRegionByStateIds controller");
		return regionService.getRegionByStateIds(data.getStateIds());
	}
	
	
}

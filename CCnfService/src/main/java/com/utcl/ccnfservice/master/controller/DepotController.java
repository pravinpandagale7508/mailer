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

import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.ccnfservice.master.service.DepotService;
import com.utcl.dto.ccnf.DepotDto;
import com.utcl.dto.ccnf.requestResponce.RegionIds;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/api/depot")
@Slf4j
public class DepotController {

	private DepotService depotService;

	public DepotController(DepotService depotService) {
			super();
			this.depotService = depotService;
		}

	@PostMapping("/addDepot")
	public ResponseEntity<DepotDto> addDepotDetails(@RequestBody DepotDto depotDto) {
		 return ResponseEntity.ok(depotService.addDepotDetails(depotDto));
	}
	
	@GetMapping("/getDepotDetailsById/{depotId}")
	public Depot getDepotByDepotId(@PathVariable("depotId") long depotId)throws Exception {
		return depotService.getDepotByDepotId(depotId);
	}
	
	@GetMapping("/getDepotByDepotRegionId/{regionId}")
	public Depot getDepotByDepotRegionId(@PathVariable("regionId") long regionId)throws Exception {
		return depotService.getDepotByDepotId(regionId);
	}

	@GetMapping("/getDepotDetails")
	public List<Depot> getDepotDetails() {
		return depotService.getDepotDetails();
	}

	@PutMapping("/updateDepot")
	public ResponseEntity<DepotDto> updateDepotDetails(@RequestBody DepotDto depotDto) {
		 return ResponseEntity.ok(depotService.addDepotDetails(depotDto));
	}
	
	
	@PostMapping("/getDepotDetailsByRegionId")
	public List<Depot> getDepotDetailsByRegionId(@RequestBody RegionIds regionIds) {
		log.info("Inside depot controller");
		return depotService.getDepotDetailsByRegionId(regionIds.getRegionIds());
	}
	
	
}

package com.utcl.ccnfservice.cement.controller.transaction;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.cement.transaction.entity.SlabwiseCommision;
import com.utcl.ccnfservice.cement.transaction.service.CcnfSlabwiseCommisionService;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/ccnfSlabWise")
@Slf4j
public class SlabWiseCommisionController{
	
	@Autowired
	CcnfSlabwiseCommisionService ccnfSlabwiseCommisionService;
	
	@PostMapping("/addCcnfSlabwiseCommission")
	public ResponseEntity<SlabwiseCommisionDto> addSlabwiseCommission(@RequestBody SlabwiseCommisionDto slabwiseCommisionDto) {
		 return ResponseEntity.ok(ccnfSlabwiseCommisionService.addSlabwiseCommission(slabwiseCommisionDto));
	}
	
	@GetMapping("/getCcnfSlabwiseCommissions")
	public ResponseEntity<List<SlabwiseCommision>> getSlabwiseCommissions() {
		return ResponseEntity.ok(ccnfSlabwiseCommisionService.getSlabwiseCommissions());
	}
	
	@GetMapping("/getCcnfSlabwiseCommissionsByid/{id}")
	public SlabwiseCommision getSlabwiseCommissionById(@PathVariable("id") long slabId) throws Exception {
		return ccnfSlabwiseCommisionService.getSlabwiseCommissionById(slabId);
	}
	
	@DeleteMapping("/deleteCcnfSlabwiseCommissionById/{id}")
	public void deleteSlabwiseCommissionById(@PathVariable("id") long slabId) {
		 ccnfSlabwiseCommisionService.deleteSlabwiseCommissionById(slabId);
	}
	
	@PostMapping("/getSlabwiseCommisionDetailsByLoiId")
	public List<SlabwiseCommision> getSlabwiseCommisionDetailsByLoiId(@RequestBody Map<String,Object> data) {
		log.info("Inside sloc controller");
		List<Long> loiIds=(List<Long>) data.get("loiId");
		return ccnfSlabwiseCommisionService.getSlabwiseCommisionDetailsByLoiId(loiIds);
	}
	
	@GetMapping("/getCcnfSlabwiseCommissionsByQuantity/{salesQuantity}/{loiId}")
	public double getSlabwiseCommissionById(@PathVariable("salesQuantity") double salesQuantity,@PathVariable ("loiId") long loiId) throws Exception {
		return ccnfSlabwiseCommisionService.getCcnfSlabwiseCommissionsByQuantity(salesQuantity,loiId);
	}
}

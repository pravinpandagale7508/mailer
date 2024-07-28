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

import com.utcl.ccnfservice.cement.transaction.entity.MinimalCommision;
import com.utcl.ccnfservice.cement.transaction.service.MinimalCommisionService;
import com.utcl.dto.ccnf.MinimalCommisionDto;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ccnfMinimalCommission")
@Slf4j
public class MinimalCommisionController {

	@Autowired
	MinimalCommisionService minimalCommisionService;

	@PostMapping("/addCcnfMinimalCommission")
	public ResponseEntity<MinimalCommisionDto> addMinimalCommisionCommission(
			@RequestBody MinimalCommisionDto minimalCommisionDto) {
		return ResponseEntity.ok(minimalCommisionService.addMinimalCommisionCommission(minimalCommisionDto));
	}

	@GetMapping("/getCcnfMinimalCommission")
	public ResponseEntity<List<MinimalCommision>> getMinimalCommisionCommission() {
		return ResponseEntity.ok(minimalCommisionService.getMinimalCommisionCommission());
	}

	@GetMapping("/getCcnfMinimalCommissionById/{id}")
	public MinimalCommision getMinimalCommisionCommissionById(@PathVariable("id") long minimalId) throws Exception {
		return minimalCommisionService.getMinimalCommisionCommissionById(minimalId);
	}

	@DeleteMapping("/deleteCcnfMinimalCommisionById/{id}")
	public void deleteMinimalCommisionCommissionById(@PathVariable("id") long minimalId) {
		minimalCommisionService.deleteMinimalCommisionCommissionById(minimalId);
	}

	@PostMapping("/getMinimalCommisionDetailsByLoiId")
	public List<MinimalCommision> getMinimalCommisionDetailsByLoiId(@RequestBody Map<String, Object> data) {
		log.info("Inside sloc controller");
		List<Long> loiIds = (List<Long>) data.get("loiId");
		return minimalCommisionService.getMinimalCommisionDetailsByLoiId(loiIds);
	}

	@GetMapping("getMinimalCommisionByQuantity/{salesQauantity}/{loiId}/{minimalCommisionFlg}")
	public double getMinimalCommisionByQuantity(@PathVariable("salesQauantity") double salesQauantity,@PathVariable("loiId") long loiId,@PathVariable("minimalCommisionFlg") boolean minimalCommisionFlg) throws Exception {
		if (minimalCommisionFlg) {
			return minimalCommisionService.getMinimalCommissionsByQuantity(salesQauantity, loiId);
		}
		return 0;
	}
}

package com.utcl.ccnfservice.rmc.controller.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSlabwiseCommision;
import com.utcl.ccnfservice.rmc.transaction.service.CcnfRMCSlabwiseCommissionServiceImpl;
import com.utcl.dto.ccnf.CcnfRMCSlabwiseCommisionDto;
import com.utcl.dto.ccnf.requestResponce.RmcSalesQuantity;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ccnfRMCCommission")
@Slf4j
public class CcnfRmcSlabwiseCommissionController {
	@Autowired
	CcnfRMCSlabwiseCommissionServiceImpl ccnfRmcExpenseService;
	
	@PostMapping("/addRMCSlabwiseCommission")
	public ResponseEntity<CcnfRMCSlabwiseCommisionDto> addRMCSlabwiseCommission(@RequestBody CcnfRMCSlabwiseCommisionDto ccnfRmcSlabwiseCommisionDto) {
		 return ResponseEntity.ok(ccnfRmcExpenseService.addRMCSlabwiseCommission(ccnfRmcSlabwiseCommisionDto));
	}
	
	@GetMapping("/getRmcSlabwiseCommissions/{salesQuantity}/{loiId}")
	public double getRMCSlabwiseCommissions(@PathVariable("salesQuantity") double salesQuantity,Long loiId){
		return ccnfRmcExpenseService.getRmcSlabwiseCommissions(salesQuantity,loiId);
	}
	
	@GetMapping("/getCcnfRMCSlabwiseCommissions")
	public ResponseEntity<List<CcnfRMCSlabwiseCommision>> getSlabwiseCommissions() {
		return ResponseEntity.ok(ccnfRmcExpenseService.getSlabwiseRMCCommissions());
	}
	
	@GetMapping("/getCcnfRMCSlabwiseCommissionsByid/{id}")
	public CcnfRMCSlabwiseCommision getSlabwiseCommissionById(@PathVariable("id") long slabId) throws Exception {
		return ccnfRmcExpenseService.getSlabwiseRMCCommissionsById(slabId);
	}
	
	@PostMapping("/getRmcTotalSalesVariableAmount")
	public double getRmcTotalSalesVariableAmount(@RequestBody RmcSalesQuantity salesQuantity, Long loiId) throws Exception {
		log.info("loiId"+loiId);
		return ccnfRmcExpenseService.performRMCSlabwiseCalculation(salesQuantity.getPlants(),salesQuantity.getWastage(), salesQuantity.getMonth(), salesQuantity.getYear(),loiId);
	}
}

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

import com.utcl.ccnfservice.cement.transaction.entity.DeductiononDirectSales;
import com.utcl.ccnfservice.cement.transaction.service.DeductionCommisionService;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/ccnfDeduction")
@Slf4j
public class DeductionDirectSalesController{
	
	@Autowired
	DeductionCommisionService deductionCommisionService;
	
	@PostMapping("/addCcnfDeductionDirectSales")
	public ResponseEntity<DeductiononDirectSalesDto> addDeductionDirectSales(@RequestBody DeductiononDirectSalesDto deductiononDirectSalesDto) {
		return ResponseEntity.ok(deductionCommisionService.addDeductionDirectSales(deductiononDirectSalesDto));
	}
	
	@GetMapping("/getCcnfDirectDeductions")
	public ResponseEntity<List<DeductiononDirectSales>> getDirectDeductions() {
		return ResponseEntity.ok(deductionCommisionService.getDirectDeductions());
	}
	
	@GetMapping("/getCcnfDirectDeductionsById/{id}")
	public DeductiononDirectSales getDirectDeductionsById(@PathVariable("id") long deductionId) throws Exception {
		return deductionCommisionService.getDirectDeductionsById(deductionId);
	}
	
	@DeleteMapping("/deleteCcnfDirectDeductionsById/{id}")
	public void deleteDirectDeductionsById(@PathVariable("id") long loiId) {
		 deductionCommisionService.deleteDirectDeductionsById(loiId);
	}
	
	@PostMapping("/getDeductiononDirectSalesDetailsByLoiId")
	public List<DeductiononDirectSales> getDeductiononDirectSalesDetailsByLoiId(@RequestBody Map<String,Object> data) {
		log.info("Inside sloc controller");
		List<Long> loiIds=(List<Long>) data.get("loiId");
		return deductionCommisionService.getDeductiononDirectSalesDetailsByLoiId(loiIds);
	}
	
	@GetMapping("/getCcnfDirectDeductionsByQuantity/{perCentQty}/{loiId}/{directSalesQty}")
	public double getCcnfDirectDeductionsByQuantity(@PathVariable("perCentQty") double perCentQty,@PathVariable("loiId") Long loiId,@PathVariable("directSalesQty") double directSalesQty) throws Exception {
		return deductionCommisionService.getDirectDeductionsByQtyByDirectSales(perCentQty,loiId, directSalesQty);
	}
}

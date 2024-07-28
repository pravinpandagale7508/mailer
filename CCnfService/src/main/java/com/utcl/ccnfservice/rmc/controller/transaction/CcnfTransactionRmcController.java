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

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCReturnSalesDetails;
import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSalesDetails;
import com.utcl.ccnfservice.rmc.transaction.service.CcnfRMCSalesDetailsServiceImpl;
import com.utcl.ccnfservice.rmc.transaction.service.CcnfRMCSalesReturnDetailsServiceImpl;
import com.utcl.dto.ccnf.requestResponce.CcnfRMCSalesDetailsDto;
import com.utcl.dto.ccnf.requestResponce.RmcSalesQuantity;
import com.utcl.dto.rmc.requestResponce.TotalSalesQuantity;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ccnfLoiRmcTransaction")
@Slf4j
public class CcnfTransactionRmcController {

	@Autowired
	CcnfRMCSalesDetailsServiceImpl ccnfRMCSalesDetailsServiceImpl;
	
	@Autowired
	CcnfRMCSalesReturnDetailsServiceImpl ccnfRMCSalesReturnDetailsServiceImpl;
	
	@PostMapping("/addCcnfRMCSalesDetails")
	public ResponseEntity<CcnfRMCSalesDetails> addCcnfRMCSalesDetails(@RequestBody CcnfRMCSalesDetailsDto cCnfLoiDto) {
		return ResponseEntity.ok(ccnfRMCSalesDetailsServiceImpl.addCcnfRMCSalesDetails(cCnfLoiDto));
	}

	@PostMapping("/getConvertedSalesQtyByPlants")
	public ResponseEntity<Double> getConvertedSalesQtyByPlants(@RequestBody RmcSalesQuantity salesQuantity) {
		return ResponseEntity.ok(ccnfRMCSalesDetailsServiceImpl.getConvertedSalesQtyByPlants(salesQuantity.getPlants(),
				 salesQuantity.getMonth(), salesQuantity.getYear()));
	}
	
	@PostMapping("/addCcnfRMCSalesReturnDetails")
	public ResponseEntity<CcnfRMCReturnSalesDetails> addCcnfRMCSalesReturnDetails(@RequestBody CcnfRMCReturnSalesDetails ccnfRMCReturnSalesDetails) {
		return ResponseEntity.ok(ccnfRMCSalesReturnDetailsServiceImpl.addCcnfRMCSalesReturnDetails(ccnfRMCReturnSalesDetails));
	}
	
	
	
	@PostMapping("/getRmcTotalSalesQuantityList")
	public double getRmcTotalSalesQuantityList(@RequestBody List<TotalSalesQuantity> totalQty) {
		log.info("getRmcTotalSalesQuantityList{}",totalQty);
		return ccnfRMCSalesDetailsServiceImpl.getRmcTotalSalesQuantityList(totalQty);
	}


}
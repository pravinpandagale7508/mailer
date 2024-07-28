package com.utcl.ccnfservice.cement.controller.transaction;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.utcl.ccnfservice.cement.controller.swagger.TransactionApi;
import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;
import com.utcl.ccnfservice.cement.transaction.service.CcnfTransactionService;
import com.utcl.ccnfservice.cement.transaction.service.CcnfCementSalesDetailsTransactionalService;
import com.utcl.ccnfservice.cement.transaction.service.MasterDataService;
import com.utcl.dto.ccnf.CCnfLoiDto;
import com.utcl.dto.ccnf.CcnfCementLoiIterator;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;
import com.utcl.dto.ccnf.GUBTCommisionDto;
import com.utcl.dto.ccnf.MinimalCommisionDto;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;
import com.utcl.dto.ccnf.requestResponce.CCnfLoiGridResponse;
import com.utcl.dto.ccnf.requestResponce.CCnfLoiRequest;
import com.utcl.dto.ccnf.requestResponce.CCnfLoiResponse;
import com.utcl.dto.ccnf.requestResponce.DecisionRequest;
import com.utcl.dto.ccnf.requestResponce.SalesQuantity;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ccnfLoiTransaction")
@Slf4j
public class CcnfTransactionLoiController implements TransactionApi {

	@Autowired
	CcnfTransactionService ccnfTransactionService;
	@Autowired
	MasterDataService apachePOI;
	@Autowired
	CcnfCementSalesDetailsTransactionalService ccnfCementSalesDetailsTransactionalService;

	@PostMapping("/addCcnfLoi")
	public ResponseEntity<CCnfLoiDto> addCcnfLoiDetails(@RequestBody CCnfLoiRequest cCnfLoiDto) {
		return ResponseEntity.ok(ccnfTransactionService.addCcnfLoiDetails(cCnfLoiDto));
	}

	@PostMapping("/getCementSalesQty")
	public ResponseEntity<Double> getCementSalesQty(@RequestBody SalesQuantity salesQuantity) {
		return ResponseEntity.ok(ccnfCementSalesDetailsTransactionalService.getTotCemSalesQntByI2Dists(salesQuantity.getI2Dists(),
				salesQuantity.getMonth(), salesQuantity.getYear()));
	}
	//makeDecisionForLoi
	@PostMapping("/makeDecisionForLoi")
	public ResponseEntity<CcnfCementLoiIterator> makeDecisionForLoi(@RequestBody DecisionRequest decisionRequest ) {
		return ResponseEntity.ok(ccnfTransactionService.makeDecisionForLoi(decisionRequest.getNumOfAgency(), decisionRequest.getNumOfState(), decisionRequest.getNumOfRate()));
	}
	@PostMapping("/makeDecisionForCalculation")
	public ResponseEntity<CcnfCementLoiIterator> makeDecisionForCalculation(@RequestBody DecisionRequest decisionRequest ) {
		return ResponseEntity.ok(ccnfTransactionService.makeDecisionForCalculation(decisionRequest.getNumOfAgency(), decisionRequest.getNumOfState(), decisionRequest.getNumOfRate(),decisionRequest.getNumberOfDepos(),decisionRequest.isAll()));
	}
	@PostMapping("/makeDecisionForInvoice")
	public ResponseEntity<CcnfCementLoiIterator> makeDecisionForInvoice(@RequestBody DecisionRequest decisionRequest ) {
		return ResponseEntity.ok(ccnfTransactionService.makeDecisionForInvoice(decisionRequest.getNumOfAgency(), decisionRequest.getNumOfState(), decisionRequest.getNumOfRate(),decisionRequest.getNumberOfDepos(),decisionRequest.isAll()));
	}
	@PostMapping("/getTotRMCSalesQntByI2Dists")
	public ResponseEntity<Double> getTotRMCSalesQntByI2Dists(@RequestBody SalesQuantity salesQuantity) {
		return ResponseEntity.ok(ccnfCementSalesDetailsTransactionalService.getTotRMCSalesQntByI2Dists(salesQuantity.getI2Dists(),
				salesQuantity.getMonth(), salesQuantity.getYear()));
	}

	@PostMapping("/getTotInterCompanySalesByi2Dists")
	public ResponseEntity<Double> getTotInterCompanySalesByi2Dists(@RequestBody SalesQuantity salesQuantity) {
		return ResponseEntity.ok(ccnfCementSalesDetailsTransactionalService.getTotInterCompanySalesByi2Dists(salesQuantity.getI2Dists(),
				salesQuantity.getMonth(), salesQuantity.getYear()));
	}

	@PostMapping("/getCementDirectSalesQty")
	public ResponseEntity<Double> getCementDirectSalesQty(@RequestBody SalesQuantity salesQuantity) {
		return ResponseEntity.ok(ccnfCementSalesDetailsTransactionalService.getTotCemDirectSalesQntByI2Dists(salesQuantity.getI2Dists(),
				salesQuantity.getPlant(), salesQuantity.getMonth(), salesQuantity.getYear()));
	}

	@PostMapping("/getSalesQuantityToOtherDepo")
	public ResponseEntity<Double> getSalesQuantityToOtherDepo(@RequestBody SalesQuantity salesQuantity) {
		return ResponseEntity.ok(ccnfCementSalesDetailsTransactionalService.getSalesQuantityToOtherDepo(salesQuantity.getI2Dists(),
				salesQuantity.getPlant(), salesQuantity.getMonth(), salesQuantity.getYear()));
	}

	@PostMapping("/getSalesQuantityFromOtherDepo")
	public ResponseEntity<Double> getSalesQuantityFromOtherDepo(@RequestBody SalesQuantity salesQuantity) {
		return ResponseEntity.ok(ccnfCementSalesDetailsTransactionalService.getSalesQuantityFromOtherDepo(salesQuantity.getI2Dists(),
				salesQuantity.getPlant(), salesQuantity.getMonth(), salesQuantity.getYear()));
	}


	@GetMapping("/processCcnfCementSalesDetails")
	public ResponseEntity<String> processCcnfCementSalesDetails() throws Exception {
		ccnfCementSalesDetailsTransactionalService.dumpCcnfCementSalesDetails(System.getProperty("user.dir") + "/cementSalesDetails.xlsx");
		return ResponseEntity.ok("Succ");
	}

	@GetMapping("/getCcnfLoiDetails")
	public ResponseEntity<List<CCnfCementLoi>> getCCnfLoiDetails() {
		return ResponseEntity.ok(ccnfTransactionService.getCCnfLoiDetails());
	}

	@GetMapping("/getCcnfLoiDetailsByid/{id}")
	public CCnfCementLoi getCCnfLoiDetailsById(@PathVariable("id") long loiId) throws Exception {
		return ccnfTransactionService.getCCnfLoiDetailsById(loiId);
	}

//	@PostMapping("/getCcnfLoiDetailsByid/{id}")
//	public CCnfCementLoi getCCnfDepotsByRegionIds(@RequestBody List<Long> regionIds) throws Exception {
//		return ccnfTransactionService.getCCnfLoiDetailsById(regionIds);
//	}

	@DeleteMapping("/deleteCCnfLoiDetailsById/{id}")
	public void deleteCCnfLoiDetailsById(@PathVariable("id") long loiId) {
		ccnfTransactionService.deleteCCnfLoiDetailsById(loiId);
	}

	@GetMapping("/getCCnfSlabWiseCommissionByLoiId/{id}")
	public List<SlabwiseCommisionDto> getCCnfSlabWiseCommissionByLoiId(@PathVariable("id") long loiId)
			throws Exception {
		return ccnfTransactionService.getCCnfSlabWiseCommissionByLoiId(loiId);
	}

	@GetMapping("/getCCnfMinimalCommisionByLoiId/{id}")
	public List<MinimalCommisionDto> getCCnfMinimalCommisionByLoiId(@PathVariable("id") long loiId) throws Exception {
		return ccnfTransactionService.getCCnfMinimalCommisionByLoiId(loiId);
	}

	@GetMapping("/getCCnfDeductionDirectSalesByLoiId/{id}")
	public List<DeductiononDirectSalesDto> getCCnfDeductionDirectSalesByLoiId(@PathVariable("id") long loiId)
			throws Exception {
		return ccnfTransactionService.getCCnfDeductionDirectSalesByLoiId(loiId);
	}

	@GetMapping("/getCCnfGUBTCommisionByLoiId/{id}")
	public List<GUBTCommisionDto> getCCnfGUBTCommisionByLoiId(@PathVariable("id") long loiId) throws Exception {
		return ccnfTransactionService.getCCnfGUBTCommisionByLoiId(loiId);
	}

	@GetMapping("/getCcnfLoiListByloiId/{loiId}")
	public CCnfLoiResponse getCcnfLoiListByloiId(@PathVariable("loiId") long loiId) throws Exception {
		return ccnfTransactionService.getCcnfLoiListByloiId(loiId);
	}

	@GetMapping("/getCcnfLoiList")
	public List<CCnfLoiGridResponse> getCcnfLoiList() throws Exception {

		try {
			return ccnfTransactionService.getCcnfLoiList();
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam String fileName,
			@RequestParam(name = "file") MultipartFile file) {
		try {
			log.info(file.getName());
			apachePOI.dumpMasterData(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("Success");
	}
}
package com.utcl.ccnfservice.master.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.VendorDetails;
import com.utcl.ccnfservice.master.service.VendorDetailsService;
import com.utcl.dto.ccnf.VendorDetailsDto;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/api/vendorDetails")
@Slf4j
public class VendorDetailsController {

	private VendorDetailsService vendorService;

	public VendorDetailsController(VendorDetailsService vendorService) {
			super();
			this.vendorService = vendorService;
		}

	@PostMapping("/addVendorDetails")
	public ResponseEntity<VendorDetailsDto> addVendorDetailsDetails(@RequestBody VendorDetailsDto vendorDto) {
		 return ResponseEntity.ok(vendorService.addVendorDetails(vendorDto));
	}
	
	@GetMapping("/getVendorDetailsById/{id}")
	public VendorDetails getVendorDetailsByVendorDetailsId(@PathVariable("id") long id)throws Exception {
		return vendorService.getVendorDetailsById(id);
	}
	
	

	@GetMapping("/getVendorDetailsDetails")
	public List<VendorDetails> getVendorDetailsDetails() {
		return vendorService.getVendorDetails();
	}

	@PutMapping("/updateVendorDetails")
	public ResponseEntity<VendorDetailsDto> updateVendorDetailsDetails(@RequestBody VendorDetailsDto vendorDto) {
		 return ResponseEntity.ok(vendorService.addVendorDetails(vendorDto));
	}
	
	  @GetMapping("/getAllVendorNameCodeForCCNFRMC")
	     public List<Object> getAllVendorNameCodeForCCNFRMC(@RequestParam String searchText) throws Exception 
	     {
	         return vendorService.getAllVendorNameCodeForCCNFRMC(searchText);
	     }	
	
	
	
	
}

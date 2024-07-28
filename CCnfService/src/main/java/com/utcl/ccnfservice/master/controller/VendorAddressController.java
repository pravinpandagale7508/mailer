package com.utcl.ccnfservice.master.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.VendorAddress;
import com.utcl.ccnfservice.master.service.VendorAddressService;
import com.utcl.dto.ccnf.VendorAddressDto;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/api/vendorAddress")
@Slf4j
public class VendorAddressController {

	private VendorAddressService vendorAddressService;

	public VendorAddressController(VendorAddressService vendorAddressService) {
			super();
			this.vendorAddressService = vendorAddressService;
		}

	@PostMapping("/addVendorAddress")
	public ResponseEntity<VendorAddressDto> addVendorAddressDetails(@RequestBody VendorAddressDto depotDto) {
		 return ResponseEntity.ok(vendorAddressService.addVendorAddressDetails(depotDto));
	}
	
	@GetMapping("/getVendorAddressDetailsById/{depotId}")
	public VendorAddress getVendorAddressByVendorAddressId(@PathVariable("depotId") long depotId)throws Exception {
		return vendorAddressService.getVendorAddressByVendorAddressId(depotId);
	}
	
	@GetMapping("/getVendorAddressByVendorAddressRegionId/{regionId}")
	public VendorAddress getVendorAddressByVendorAddressRegionId(@PathVariable("regionId") long regionId)throws Exception {
		return vendorAddressService.getVendorAddressByVendorAddressId(regionId);
	}

	@GetMapping("/getVendorAddressDetails")
	public List<VendorAddress> getVendorAddressDetails() {
		return vendorAddressService.getVendorAddressDetails();
	}

	@PutMapping("/updateVendorAddress")
	public ResponseEntity<VendorAddressDto> updateVendorAddressDetails(@RequestBody VendorAddressDto depotDto) {
		 return ResponseEntity.ok(vendorAddressService.addVendorAddressDetails(depotDto));
	}
	
	
	
	
}

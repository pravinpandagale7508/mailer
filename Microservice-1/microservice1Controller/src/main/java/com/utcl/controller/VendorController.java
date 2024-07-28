package com.utcl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.controller.swagger.VendorApi;
import com.utcl.dto.M1Dto;
import com.utcl.dto.VendorDTO;
import com.utcl.dto.mapper.VendorRecord;
import com.utcl.m1service.VendorService;

@RestController
@RequestMapping("/api/vendors")
public class VendorController implements VendorApi {
	private VendorService vendorService;

	public VendorController(VendorService vendorService) {
		super();
		this.vendorService = vendorService;
	}
	@Override
	@GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable int id) {
        VendorDTO vendorDTO = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendorDTO);
    }

	@PostMapping("/create")
	public Object create(@RequestBody VendorDTO vendorDTO) {
		return vendorService.create(vendorDTO.getVendorName(), vendorDTO.getVendorCode(),vendorDTO.getVendorCommission());
	}
	@PostMapping("/createRecord")
	public Object createRecord(VendorRecord vendorRecord) {
		return vendorService.create(vendorRecord.vendorName(), vendorRecord.vendorCode(),vendorRecord.vendorCommission());
	}
}

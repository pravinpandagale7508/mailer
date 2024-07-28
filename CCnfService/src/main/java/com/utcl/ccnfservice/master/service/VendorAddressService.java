package com.utcl.ccnfservice.master.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.VendorAddress;
import com.utcl.ccnfservice.master.repo.VendorAddressRepo;
import com.utcl.dto.ccnf.VendorAddressDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VendorAddressService {
	private VendorAddressRepo vendorAddressRepo;
	private ModelMapper modelMapper = new ModelMapper();

	public VendorAddressService(VendorAddressRepo vendorAddressRepo) {
		super();
		this.vendorAddressRepo = vendorAddressRepo;

	}

	public VendorAddressDto addVendorAddressDetails(VendorAddressDto vendorAddressDto) {
		log.info("Dto {}", vendorAddressDto);
		VendorAddress vendorAddress = null;
		if (vendorAddressRepo.findById(vendorAddressDto.getId()).isPresent()) {
			updateDetails(vendorAddressDto);
		} else {
			vendorAddress = vendorAddressRepo.save(toVendorAddress(vendorAddressDto));
			vendorAddressDto = toVendorAddressDto(vendorAddress);
		}
		return vendorAddressDto;
	}

	private VendorAddressDto updateDetails(VendorAddressDto vendorAddressDto) {
		return toVendorAddressDto(vendorAddressRepo.save(toVendorAddress(vendorAddressDto)));
	}

	public VendorAddress toVendorAddress(@Validated VendorAddressDto vendorAddressDto) {
		return modelMapper.map(vendorAddressDto, VendorAddress.class);
	}

	public VendorAddressDto toVendorAddressDto(@Validated VendorAddress vendorAddressDto) {
		return modelMapper.map(vendorAddressDto, VendorAddressDto.class);
	}

	public VendorAddress getVendorAddressByVendorAddressId(Long id) throws Exception {
		return vendorAddressRepo.findById(id).orElseThrow(() -> new Exception("VendorAddressRepo not found"));

	}

	public List<VendorAddress> getVendorAddressDetails() {
		return vendorAddressRepo.findAll();

	}

	
}

package com.utcl.ccnfservice.master.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.VendorDetails;
import com.utcl.ccnfservice.master.repo.VendorDetailsRepo;
import com.utcl.dto.ccnf.VendorDetailsDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VendorDetailsService {
	private VendorDetailsRepo vendorDetailsRepo;
	private ModelMapper modelMapper = new ModelMapper();

	public VendorDetailsService(VendorDetailsRepo vendorDetailsRepo) {
		super();
		this.vendorDetailsRepo = vendorDetailsRepo;

	}

	public VendorDetailsDto addVendorDetails(VendorDetailsDto vendorDetailsDto) {
		log.info("Dto {}", vendorDetailsDto);
		VendorDetails vendorDetails = null;
		if (vendorDetailsRepo.findById(vendorDetailsDto.getId()).isPresent()) {
	
			updateDetails(vendorDetailsDto);
		} else {
			vendorDetails = vendorDetailsRepo.save(toVendorDetails(vendorDetailsDto));
			vendorDetailsDto = toVendorDetailsDto(vendorDetails);
		}
		return vendorDetailsDto;
	}

	private VendorDetailsDto updateDetails(VendorDetailsDto vendorDetailsDto) {
		return toVendorDetailsDto(vendorDetailsRepo.save(toVendorDetails(vendorDetailsDto)));
	}

	public VendorDetails toVendorDetails(@Validated VendorDetailsDto vendorDetailsDto) {
		return modelMapper.map(vendorDetailsDto, VendorDetails.class);
	}

	public VendorDetailsDto toVendorDetailsDto(@Validated VendorDetails vendorDetailsDto) {
		return modelMapper.map(vendorDetailsDto, VendorDetailsDto.class);
	}

	public VendorDetails getVendorDetailsById(Long id) throws Exception {
		return vendorDetailsRepo.findById(id).orElseThrow(() -> new Exception("VendorDetailsRepo not found"));

	}

	public List<VendorDetails> getVendorDetails() {
		return vendorDetailsRepo.findAll();

	}
	
	
	public List<Object> getAllVendorNameCodeForCCNFRMC(String searchText) {
		List<Object> lstObj=vendorDetailsRepo.getAllVendorNameCodeForCCNFRMC(searchText);
		return lstObj;
	}

	
}

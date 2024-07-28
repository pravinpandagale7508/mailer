package com.utcl.m1service;

import org.springframework.stereotype.Service;

import com.utcl.dto.VendorDTO;
import com.utcl.dto.mapper.VendorMapperImpl;
import com.utcl.dto.mapper.VendorRecord;
import com.utcl.dto.mapper.VendorRecordMapperImpl;
import com.utcl.m1Entity.Vendor;
import com.utcl.m1repo.VendorRepo;

@Service
public class VendorServiceImpl implements VendorService {

	private VendorRepo vendorRepository;	
	private VendorMapperImpl vendorMapperImpl;
	private VendorRecordMapperImpl recordMapperImpl;
	
	
	//Constructor Injection
	public VendorServiceImpl(VendorRepo vendorRepository, VendorMapperImpl vendorMapperImpl,
			VendorRecordMapperImpl recordMapperImpl) {
		super();
		this.vendorRepository = vendorRepository;
		this.vendorMapperImpl = vendorMapperImpl;
		this.recordMapperImpl = recordMapperImpl;
	}

	@Override
	public VendorDTO getVendorById(int id) {
		Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendor not found"));

		return vendorMapperImpl.toVendorDto(vendor);
	}

	@Override
	public VendorRecord getVendorRecordById(int id) {
		Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendor not found"));

		return recordMapperImpl.toVendorRecord(vendor);
	}

	@Override
	public VendorDTO create(String vendorName, String vendorCode, double vendorCommission) {
		Vendor v =vendorRepository.save(new Vendor(0, vendorName, vendorCode, vendorCommission));
		
		return vendorMapperImpl.toVendorDto(v);
	}

}

package com.utcl.ccnf.m1service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.utcl.ccnf.dto.VendorDTO;
import com.utcl.ccnf.dto.mapper.VendorMapperImpl;
import com.utcl.ccnf.dto.mapper.VendorRecord;
import com.utcl.ccnf.dto.mapper.VendorRecordMapperImpl;
import com.utcl.ccnf.m1Entity.Vendor;
import com.utcl.ccnf.m1repo.VendorRepo;

@Service
public class VendorServiceImpl implements VendorService {

	private VendorRepo vendorRepository;	
	private VendorMapperImpl vendorMapperImpl;
	private VendorRecordMapperImpl recordMapperImpl;
	private ModelMapper modelMapper;
	
	//Constructor Injection
	public VendorServiceImpl(VendorRepo vendorRepository, VendorMapperImpl vendorMapperImpl,
			VendorRecordMapperImpl recordMapperImpl) {
		super();
		this.vendorRepository = vendorRepository;
		this.vendorMapperImpl = vendorMapperImpl;
		this.recordMapperImpl = recordMapperImpl;
		this.modelMapper = new ModelMapper();
	}

	@Override
	public VendorDTO getVendorById(int id) {
		Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendor not found"));

		return this.vendorMapperImpl.toVendorDto(vendor);
	}
	public VendorDTO getVendorById1(int id) {
		Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendor not found"));

		return toVendorDto(vendor);
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
	
	public VendorDTO toVendorDto(Vendor vendor) {
		String VendorStr = vendor.toString();
		//LOGGER.info("Mapper call: Vendor > {} ", VendorStr);
		VendorDTO orderDTO = modelMapper.map(vendor, VendorDTO.class);
		return orderDTO;
	}

}

package com.utcl.ccnf.dto.mapper;

import org.aspectj.lang.annotation.Aspect;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnf.dto.VendorDTO;
import com.utcl.ccnf.m1Entity.Vendor;


@Component
public class VendorMapperImpl implements VendorMapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorMapperImpl.class);
	//@Autowired
	private ModelMapper modelMapper;
	
	
	public VendorMapperImpl() {
		super();
		this.modelMapper = new ModelMapper();
	}

	@Override
	public Vendor toVendor(@Validated VendorDTO vendorDto) {
		LOGGER.info("Mapper call: vendorDto > {} ", vendorDto);
		return modelMapper.map(vendorDto, Vendor.class);
	}

	@Override
	public VendorDTO toVendorDto(Vendor vendor) {
		String VendorStr = vendor.toString();
		LOGGER.info("Mapper call: Vendor > {} ", VendorStr);
		VendorDTO orderDTO = modelMapper.map(vendor, VendorDTO.class);
		return orderDTO;
	}
}

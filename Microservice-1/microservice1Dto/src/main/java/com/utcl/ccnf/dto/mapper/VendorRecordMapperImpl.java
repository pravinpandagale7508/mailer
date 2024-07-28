package com.utcl.ccnf.dto.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.utcl.ccnf.m1Entity.Vendor;

@Component
public class VendorRecordMapperImpl implements VendorRecordMapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(VendorMapperImpl.class);
	
	@Override
	public Vendor toVendor(VendorRecord vendorRecord) {
		LOGGER.info("Mapper call: VendorRecord > {} ", vendorRecord);
		return new Vendor(vendorRecord.vendorId(), vendorRecord.vendorName(), vendorRecord.vendorCode(), vendorRecord.vendorCommission());
	}

	@Override
	public VendorRecord toVendorRecord(Vendor vendor) {
		LOGGER.info("Mapper call: Vendor > {} ", vendor);
		return new VendorRecord(vendor.getVendorId(), vendor.getVendorName(), vendor.getVendorCode(), vendor.getVendorCommission());
	}

}

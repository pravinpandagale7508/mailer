package com.utcl.dto.mapper;

import com.utcl.dto.VendorDTO;
import com.utcl.m1Entity.Vendor;

public interface VendorRecordMapper {
	public Vendor toVendor(VendorRecord vendorRecord);

	public VendorRecord toVendorRecord(Vendor order);
}

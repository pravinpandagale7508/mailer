package com.utcl.ccnf.dto.mapper;

import com.utcl.ccnf.dto.VendorDTO;
import com.utcl.ccnf.m1Entity.Vendor;

public interface VendorRecordMapper {
	public Vendor toVendor(VendorRecord vendorRecord);

	public VendorRecord toVendorRecord(Vendor order);
}

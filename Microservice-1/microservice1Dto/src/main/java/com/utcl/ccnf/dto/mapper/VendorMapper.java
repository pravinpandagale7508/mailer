package com.utcl.ccnf.dto.mapper;

import com.utcl.ccnf.dto.VendorDTO;
import com.utcl.ccnf.m1Entity.Vendor;

public interface VendorMapper {
	public Vendor toVendor(VendorDTO vendorDTO);

	public VendorDTO toVendorDto(Vendor vendor);
}

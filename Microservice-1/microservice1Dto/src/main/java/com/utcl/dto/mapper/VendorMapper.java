package com.utcl.dto.mapper;

import com.utcl.dto.VendorDTO;
import com.utcl.m1Entity.Vendor;

public interface VendorMapper {
	public Vendor toVendor(VendorDTO vendorDTO);

	public VendorDTO toVendorDto(Vendor vendor);
}

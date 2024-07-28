package com.utcl.m1service;

import com.utcl.dto.VendorDTO;
import com.utcl.dto.mapper.VendorRecord;

public interface VendorService {
	VendorDTO getVendorById(int id);
	VendorRecord getVendorRecordById(int id);
	VendorDTO create(String vendorName, String vendorCode, double vendorCommission);

}

package com.utcl.ccnf.m1service;

import com.utcl.ccnf.dto.VendorDTO;
import com.utcl.ccnf.dto.mapper.VendorRecord;

public interface VendorService {
	VendorDTO getVendorById(int id);
	VendorRecord getVendorRecordById(int id);
	VendorDTO create(String vendorName, String vendorCode, double vendorCommission);

}

package com.utcl.dto;

import lombok.Data;

@Data
public class VendorDTO { 
	private int vendorId;
    private String vendorName;
    private String vendorCode;
    private double vendorCommission;

}

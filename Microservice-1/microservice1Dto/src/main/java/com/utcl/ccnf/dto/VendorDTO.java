package com.utcl.ccnf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VendorDTO {
	private int vendorId;
    private String vendorName;
    private String vendorCode;
    private double vendorCommission;
	public VendorDTO(int vendorId, String vendorName, String vendorCode, double vendorCommission) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.vendorCode = vendorCode;
		this.vendorCommission = vendorCommission;
		if(this.vendorCommission<0) {
			throw new IllegalArgumentException();
		}
	}
    
    

}

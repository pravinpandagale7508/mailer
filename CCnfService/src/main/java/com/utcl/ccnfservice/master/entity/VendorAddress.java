package com.utcl.ccnfservice.master.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "vendoraddress")
@NoArgsConstructor
@AllArgsConstructor 
@Data
@Entity
public class VendorAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vendorDetailsId;
    private String city;
    private String state;
    private String pincode;
    private Date	createdDate;
    private Date	updatedDate;
    private String  createdBy;
	private String	updatedBy;
}

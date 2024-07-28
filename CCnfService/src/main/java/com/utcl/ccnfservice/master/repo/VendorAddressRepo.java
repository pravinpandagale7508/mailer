package com.utcl.ccnfservice.master.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.VendorAddress;


@Repository
public interface VendorAddressRepo extends JpaRepository<VendorAddress, Long> 
{
	
}

package com.utcl.ccnfservice.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.VendorDetails;


@Repository
public interface VendorDetailsRepo extends JpaRepository<VendorDetails, Long> 
{
    @Query(nativeQuery = true,
            value = "select ven.vendorCode,ven.firstName from \r\n"
            		+ "master_ccnf_cement.vendordetails ven \r\n"
            		+ "where ven.vendorCode LIKE CONCAT('%',:searchText, '%') \r\n"
            		+ "or ven.firstName LIKE CONCAT('%', :searchText, '%') ")
    public List<Object> getAllVendorNameCodeForCCNFRMC(String searchText);

}

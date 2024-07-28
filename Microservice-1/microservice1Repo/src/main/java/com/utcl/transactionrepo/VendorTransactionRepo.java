package com.utcl.transactionrepo;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utcl.ccnf.m1Entity.Microservice1Table;
import com.utcl.ccnf.m1Entity.Vendor;
import com.utcl.transactionEntity.VendorTrans;

@Repository
public interface VendorTransactionRepo extends JpaRepository<VendorTrans, Integer> {
	
}

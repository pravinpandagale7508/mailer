package com.utcl.ccnfservice.cement.transaction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;

@Repository
public interface CCnfLoiRepo extends JpaRepository<CCnfCementLoi, Long> {	
}

package com.utcl.ccnfservice.master.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.Sloc;



@Repository
public interface SlocRepo extends JpaRepository<Sloc, Long> {
	 @Query("SELECT sl FROM Sloc sl WHERE sl.distId IN (:distId)")
	 List<Sloc> getSlocDetailsByI2districtId(List<Long> distId);
	
}

package com.utcl.ccnfservice.master.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.County;

@Repository
public interface CountyRepo extends JpaRepository<County, Long> {

//	 @Query("SELECT reg FROM Depot reg WHERE reg.regionId IN (:regionIds)")
//	 List<Depot> getDepotDetailsByRegionId(List<Long> regionIds);
	County getCountyByCountyName(String name);

}

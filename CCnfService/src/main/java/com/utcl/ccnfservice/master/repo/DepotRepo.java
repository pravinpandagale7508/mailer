package com.utcl.ccnfservice.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.Depot;



@Repository
public interface DepotRepo extends JpaRepository<Depot, Long> {
	
	 @Query("SELECT reg FROM Depot reg WHERE reg.regionId IN (:regionIds)")
	 List<Depot> getDepotDetailsByRegionId(List<Long> regionIds);
	 Depot getDepotByDepotName(String depotName);
	 
	 @Query("SELECT dep FROM Depot dep WHERE dep.depotId IN (:depotIds)")
	 List<Depot> getDepotNameBydepotIds(List<Long> depotIds);
	
}



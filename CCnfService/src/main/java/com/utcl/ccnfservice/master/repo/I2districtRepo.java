package com.utcl.ccnfservice.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.I2district;


@Repository
public interface I2districtRepo extends JpaRepository<I2district, Long> {
	
	@Query("SELECT dis FROM I2district dis WHERE dis.depotId IN (:depotIds) ORDER BY dis.distName asc")
	 List<I2district> getI2districtByDepotId(List<Long> depotIds);
	
	I2district getI2districtByDistName(String name);
	
	 @Query("SELECT dis FROM I2district dis WHERE dis.distId IN (:distIds) ORDER BY dis.distName asc")
	 List<I2district> getI2districtNameByIds(List<Long> distIds);
}

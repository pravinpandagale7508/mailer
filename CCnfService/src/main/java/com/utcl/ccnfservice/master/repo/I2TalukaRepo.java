package com.utcl.ccnfservice.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.I2Taluka;

@Repository
public interface I2TalukaRepo extends JpaRepository<I2Taluka, Long> {

	@Query("SELECT talu FROM I2Taluka talu WHERE talu.i2DistId IN (:geoDistIds)")
	List<I2Taluka> getI2TalukabyI2DistricsIds(List<Long> geoDistIds);

	I2Taluka getI2TalukaByTalukaName(String name);

}

package com.utcl.ccnfservice.master.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.City;




@Repository
public interface CityRepo extends JpaRepository<City, Long> {
	
	 @Query("SELECT ct FROM City ct WHERE ct.i2TqId IN (:i2TqIds)")
	 List<City> getCitiesByI2TalukaIds(List<Long> i2TqIds);
	 City getCityByCityName(String name);
	
}



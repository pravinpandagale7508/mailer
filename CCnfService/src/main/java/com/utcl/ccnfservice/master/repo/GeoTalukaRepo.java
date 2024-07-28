package com.utcl.ccnfservice.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.GeoTaluka;

@Repository
public interface GeoTalukaRepo extends JpaRepository<GeoTaluka, Long> {

	@Query("SELECT geo FROM GeoTaluka geo WHERE geo.geoDistId IN (:geoDistIds)")
	List<GeoTaluka> getGeoTalukaByGeoDistrictIds(List<Long> geoDistIds);

	GeoTaluka getGeoTalukaByTalukaName(String distName);

}

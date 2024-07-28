package com.utcl.ccnfservice.master.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.Zone;


@Repository
public interface ZoneRepo extends JpaRepository<Zone, Long> {

	Zone getZoneByZoneCode(String formatCellValue);

	@Query("SELECT zn FROM Zone zn WHERE zn.zoneCode IN (:zoneCodes) ORDER BY zn.zoneCode asc")
	 List<Zone> getDPDZones(List<String> zoneCodes);
	
	@Query("SELECT zn FROM Zone zn WHERE zn.zoneCode NOT IN (:zoneCodes) ORDER BY zn.zoneCode asc")
	 List<Zone> getOtherThanDPDZones(List<String> zoneCodes);
	 
}

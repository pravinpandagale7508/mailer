package com.utcl.ccnfservice.master.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.Region;

@Repository
public interface RegionRepo extends JpaRepository<Region, Long> {
	Region getRegionByRegionId(long regionId);

	Region getRegionByRegionName(String regionId);

	@Query("SELECT rg FROM Region rg WHERE rg.stateId IN (:stateIds) ORDER BY rg.regionName asc")
	List<Region> getRegionByStateIds(List<Long> stateIds);

	@Query("SELECT rg FROM Region rg WHERE rg.regionId IN (:regionIds) ORDER BY rg.regionName asc")
	List<Region> getRegionNameByregionIds(List<Long> regionIds);
}

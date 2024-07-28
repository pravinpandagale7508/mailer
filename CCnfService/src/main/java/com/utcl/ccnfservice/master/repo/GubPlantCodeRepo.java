package com.utcl.ccnfservice.master.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.master.entity.GubtPlantCode;


@Repository
public interface GubPlantCodeRepo extends JpaRepository<GubtPlantCode, Long> {
	GubtPlantCode getGubPlantCodeByGubId(long gubId);
}

package com.utcl.ccnfservice.rmc.transaction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSlabwiseCommision;

@Transactional
@Repository
public interface CcnfRMCSlabwisecommisionRepo extends JpaRepository<CcnfRMCSlabwiseCommision, Long> {
	
	@Modifying
	@Query("update CcnfRMCSlabwiseCommision u set u.totalCommissionOnSlab = ?1 ,u.slaesQuantityCuMT=?2 ,u.remSlabAmount=?3 where u.slabId = ?4 and u.loiId=?5 ")
	void updateTotalSalesCommisionAndQuantityById(double totalSlabCommision, double slaesQuantityCuMT,double remSlabAmount, Long slabId, Long loiId);

	List<CcnfRMCSlabwiseCommision> findByLoiId(Long loiId);
}

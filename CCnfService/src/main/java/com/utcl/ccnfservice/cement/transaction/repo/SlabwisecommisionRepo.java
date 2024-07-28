package com.utcl.ccnfservice.cement.transaction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utcl.ccnfservice.cement.transaction.entity.SlabwiseCommision;

@Transactional
@Repository
public interface SlabwisecommisionRepo extends JpaRepository<SlabwiseCommision, Long> {
	 @Query("SELECT slab FROM SlabwiseCommision slab WHERE slab.loiId IN (:loiId)")
	 List<SlabwiseCommision> getSlabwiseCommisionDetailsByLoiId(List<Long> loiId);

	List<SlabwiseCommision> findByLoiId(Long loiId);

	List<SlabwiseCommision> getSlabwiseCommisionByLoiId(Long loiId);
	
	@Modifying
	@Query("update SlabwiseCommision u set u.totalCommissionOnSlab = ?1 ,u.slaesQuantityOnMT=?2 ,u.remSlabAmount=?3 where u.slabId = ?4 and u.loiId=?5 ")
	void updateTotalSalesCommisionAndQuantityById( double totalCommissionOnSlab,double slaesQuantityOnMT, double remSlabAmount,Long slabId,Long loiId);
}

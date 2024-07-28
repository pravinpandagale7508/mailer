package com.utcl.ccnfservice.cement.transaction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utcl.ccnfservice.cement.transaction.entity.MinimalCommision;

@Transactional
@Repository
public interface MinimalCommisionRepo extends JpaRepository<MinimalCommision,Long> {
	 @Query("SELECT min FROM MinimalCommision min WHERE min.loiId IN (:loiId)")
	 List<MinimalCommision> getMinimalCommisionDetailsByLoiId(List<Long> loiId);

	List<MinimalCommision> findByLoiId(long loiId);

	@Modifying
	@Query("update MinimalCommision m set m.totalSalesCommission = ?1 ,m.saleQuantityinMT=?2  where m.minimalId = ?3 and m.loiId=?4 ")
	void updateTotalMinimalCommisionAndSlaesById(double totalSalesCommission, double saleQuantityinMT, Long minimalId, Long loiId);
	
}

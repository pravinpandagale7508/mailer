package com.utcl.ccnfservice.cement.transaction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utcl.ccnfservice.cement.transaction.entity.DeductiononDirectSales;

@Transactional
@Repository
public interface DeductiononDirectSalesRepo extends JpaRepository<DeductiononDirectSales, Long> {
	 @Query("SELECT de FROM DeductiononDirectSales de WHERE de.loiId IN (:loiId)")
	 List<DeductiononDirectSales> getDeductiononDirectSalesDetailsByLoiId(List<Long> loiId);

	//@Query("SELECT d.slabAmountFrom FROM DeductiononDirectSales d where loiId = (:loiId)")
	List<DeductiononDirectSales> findByLoiId(Long loiId);

	@Modifying
	@Query("update DeductiononDirectSales m set m.totalDirectCommision = ?1 ,m.quantityPercentage=?2 , m.directSalesQty = ?3 where m.deductionId=?4 and m.loiId=?5 ")
	void updateTotalDirectCommissionById(double totalDirectCommision, double quantityPercentage, double directSalesQty, Long deductionId, Long loiId);
	
}

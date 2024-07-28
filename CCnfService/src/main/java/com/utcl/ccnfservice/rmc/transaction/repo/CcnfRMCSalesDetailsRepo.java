package com.utcl.ccnfservice.rmc.transaction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSalesDetails;

@Repository
public interface CcnfRMCSalesDetailsRepo extends JpaRepository<CcnfRMCSalesDetails, Long> {

	@Query("SELECT sum(ke.convertedQTY) FROM CcnfRMCSalesDetails ke WHERE ke.plant IN (:plants) and month(ke.transactionDate) =:month and year(ke.transactionDate) =:year")
	public Double getConvertedSalesQtyByPlants(List<String> plants,int month,int year);

}

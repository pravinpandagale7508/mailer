package com.utcl.ccnfservice.cement.transaction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcl.ccnfservice.cement.transaction.entity.CcnfCementSalesDetails;

@Repository
public interface CcnfCementSalesDetailsRepo extends JpaRepository<CcnfCementSalesDetails, Long> {

	// Step 4: Filter out the transaction data from CcnfCementSalesDetails
	@Query("SELECT sum(ke.salesQuantity) FROM CcnfCementSalesDetails ke WHERE ke.salesGroup IN (:i2Dists) and ke.customerGroup1!='N08' and ke.customerGroup2!='NG0' and month(ke.invDate) =:month and year(ke.invDate) =:year")
	public Double getTotCemSalesQntByI2Dists(List<String> i2Dists,int month,int year);

	@Query("SELECT ke.salesQuantity FROM CcnfCementSalesDetails ke WHERE ke.salesGroup IN (:i2Dists) and ke.customerGroup1!='N08' and ke.customerGroup2!='NG0' and month(ke.invDate) =:month and year(ke.invDate) =:year")
	public List<Object> getTotCemSalesQntByI2DistsList(List<String> i2Dists,int month,int year);

	// Step 8: Calculate Direct Sales Qty from CcnfCementSalesDetails
	@Query("SELECT sum(ke.salesQuantity) FROM CcnfCementSalesDetails ke WHERE ke.salesGroup IN (:i2Dists) and ke.customerGroup1!='N08' and customerGroup2!='NG0' and ke.plant!=:plant and month(ke.invDate) =:month and year(ke.invDate) =:year")
	public Double getTotCemDirectSalesQntByI2Dists(List<String> i2Dists, String plant,int month,int year);

	// Step 15 Calculate sales to other depo Commission (From Nasik to Other depo)
	@Query("SELECT sum(ke.salesQuantity) FROM CcnfCementSalesDetails ke WHERE ke.salesGroup NOT IN (:i2Dists) and ke.customerGroup1!='N08' and customerGroup2!='NG0' and ke.plant=:depo and month(ke.invDate) =:month and year(ke.invDate) =:year")
	public Double getSalesQuantityToOtherDepo(List<String> i2Dists, String depo,int month,int year);

	// Step 16 Calculate sales from other depo (From Other Depo to Nasik Depo)
	@Query("SELECT sum(ke.salesQuantity) FROM CcnfCementSalesDetails ke WHERE ke.salesGroup IN (:i2Dists) and ke.customerGroup1!='N08' and customerGroup2!='NG0' and ke.salesOffice=:depo and month(ke.invDate) =:month and year(ke.invDate) =:year")
	public Double getSalesQuantityFromOtherDepo(List<String> i2Dists, String depo,int month,int year);

	// Step 13 Calculate RMC Commission (Inter Unit Sales)
	@Query("SELECT sum(ke.salesQuantity) FROM CcnfCementSalesDetails ke WHERE ke.salesGroup IN (:i2Dists) and ke.customerGroup1='N08' and month(ke.invDate) =:month and year(ke.invDate) =:year")
	public Double getTotRMCSalesQntByI2Dists(List<String> i2Dists,int month,int year);

	// Step 14 Calculate Inter Company (Group Company) Sales Commission
	@Query("SELECT sum(ke.salesQuantity) FROM CcnfCementSalesDetails ke WHERE ke.salesGroup IN (:i2Dists) and ke.customerGroup1!='N08' and ke.customerGroup1!='N04' and customerGroup2='N05' and month(ke.invDate) =:month and year(ke.invDate) =:year")
	public Double getTotInterCompanySalesByi2Dists(List<String> i2Dists,int month,int year);

}

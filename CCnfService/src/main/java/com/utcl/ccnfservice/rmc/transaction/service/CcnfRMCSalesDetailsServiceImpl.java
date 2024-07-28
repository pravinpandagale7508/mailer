package com.utcl.ccnfservice.rmc.transaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCReturnSalesDetails;
import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSalesDetails;
import com.utcl.ccnfservice.rmc.transaction.repo.CcnfRMCSalesDetailsRepo;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCSalesDetailsService;
import com.utcl.dto.ccnf.requestResponce.CcnfRMCSalesDetailsDto;
import com.utcl.dto.rmc.requestResponce.TotalSalesQuantity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CcnfRMCSalesDetailsServiceImpl implements CcnfRMCSalesDetailsService {

	private final CcnfRMCSalesDetailsRepo ccnfRMCSalesDetailsRepo;
	
	private final CcnfRMCSalesReturnDetailsServiceImpl ccnfRMCSalesReturnDetailsServiceImpl;

	public CcnfRMCSalesDetailsServiceImpl(CcnfRMCSalesDetailsRepo ccnfRMCSalesDetailsRepo,CcnfRMCSalesReturnDetailsServiceImpl ccnfRMCSalesReturnDetailsServiceImpl) {
		super();
		this.ccnfRMCSalesDetailsRepo = ccnfRMCSalesDetailsRepo;
		this.ccnfRMCSalesReturnDetailsServiceImpl = ccnfRMCSalesReturnDetailsServiceImpl;
	}

	@Override
	public Double getConvertedSalesQtyByPlants(List<String> plants, int month, int year) {
		return  ccnfRMCSalesDetailsRepo.getConvertedSalesQtyByPlants(plants, month, year);	
	}

	@Override
	public CcnfRMCSalesDetails addCcnfRMCSalesDetails(CcnfRMCSalesDetailsDto ccnfRMCSalesDetailsDto) {
		return ccnfRMCSalesDetailsRepo.save(CcnfRMCSalesDetails.builder().plant(ccnfRMCSalesDetailsDto.getPlant())
				.convertedQTY(ccnfRMCSalesDetailsDto.getConvertedQTY())
				.transactionDate(ccnfRMCSalesDetailsDto.getTransactionDate()).build());
	}
	 public Double getRmcTotalSalesQuantity(List<String> lisOfPlants, List<Double> listOfWastages,
			int month, int year) {
		double wastage= listOfWastages.stream().mapToDouble(Double::doubleValue).sum();	
		log.info("wastage value"+wastage);
		return getConvertedSalesQtyByPlants(lisOfPlants, month, year)-wastage-ccnfRMCSalesReturnDetailsServiceImpl.getConvertedReturnQtyByPlants(lisOfPlants, month, year);
	}

	 public Double getRmcTotalSalesQuantityList(List<TotalSalesQuantity> totalQty) {
		log.info("List"+totalQty);
		double totalSlQty = 0;
		double saleQty = 0;
		double wastageQty = 0;
		double salesReturnQty = 0;
		double totalSum=0;
		for (TotalSalesQuantity totalSalesQty : totalQty) {
			saleQty=totalSalesQty.getSalesQuanity();
			wastageQty=totalSalesQty.getWastage();
			salesReturnQty=totalSalesQty.getSalesReturn();
			totalSlQty=saleQty-wastageQty-salesReturnQty;
			log.info("Print totalSlQty"+totalSlQty);
			
			totalSum=totalSlQty+totalSum;
			log.info("Print totalSum"+totalSum);

		}
		return totalSum;
	}
}

package com.utcl.ccnfservice.rmc.transaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCReturnSalesDetails;
import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSalesDetails;
import com.utcl.ccnfservice.rmc.transaction.repo.CcnfRMCReturnSalesDetailsRepo;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCSalesReturnDetailsService;
@Service
public class CcnfRMCSalesReturnDetailsServiceImpl implements CcnfRMCSalesReturnDetailsService {

	private final CcnfRMCReturnSalesDetailsRepo ccnfRMCReturnSalesDetailsRepo;

	public CcnfRMCSalesReturnDetailsServiceImpl(CcnfRMCReturnSalesDetailsRepo ccnfRMCReturnSalesDetailsRepo) {
		super();
		this.ccnfRMCReturnSalesDetailsRepo = ccnfRMCReturnSalesDetailsRepo;
	}

	@Override
	public Double getConvertedReturnQtyByPlants(List<String> plants, int month, int year) {
		return ccnfRMCReturnSalesDetailsRepo.getConvertedReturnQtyByPlants(plants, month, year);
	}

	public CcnfRMCReturnSalesDetails addCcnfRMCSalesReturnDetails(CcnfRMCReturnSalesDetails ccnfRMCReturnSalesDetails) {
		return ccnfRMCReturnSalesDetailsRepo.save(ccnfRMCReturnSalesDetails);
		
	}

}

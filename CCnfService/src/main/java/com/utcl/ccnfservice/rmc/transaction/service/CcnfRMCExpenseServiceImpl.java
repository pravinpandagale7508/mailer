package com.utcl.ccnfservice.rmc.transaction.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCExpense;
import com.utcl.ccnfservice.rmc.transaction.repo.CcnfRMCExpenseRepo;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCExpenseService;
import com.utcl.dto.ccnf.CcnfRMCExpenseDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CcnfRMCExpenseServiceImpl implements CcnfRMCExpenseService {
	private CcnfRMCExpenseRepo ccnfExpenseRepo;
	private ModelMapper modelMapper=new ModelMapper();
	
	public CcnfRMCExpenseServiceImpl( CcnfRMCExpenseRepo ccnfExpenseRepo) {
		super();
		this.ccnfExpenseRepo = ccnfExpenseRepo;
	}

	public CcnfRMCExpenseDto addCcnfExpenseDetails(CcnfRMCExpenseDto ccnfExpenseDto) {
		log.info("Agent ServiceDto {}" ,ccnfExpenseDto);
		CcnfRMCExpense ccnfExpense=null;
		if (ccnfExpenseRepo.findById(ccnfExpenseDto.getId()).isPresent()) {	
			updateDetails(ccnfExpenseDto);
		} else {
			ccnfExpense = ccnfExpenseRepo.save(toCcnfExpense(ccnfExpenseDto));
			ccnfExpenseDto=toCcnfExpenseDto(ccnfExpense);
			log.info("ccnfExpenseDto ServiceDto {}" ,ccnfExpenseDto);

		}
		return ccnfExpenseDto;
	}
	
	private CcnfRMCExpenseDto updateDetails(CcnfRMCExpenseDto ccnfExpenseDto) {
		return  toCcnfExpenseDto(ccnfExpenseRepo.save(toCcnfExpense(ccnfExpenseDto)));
	}
	public CcnfRMCExpense toCcnfExpense(@Validated CcnfRMCExpenseDto ccnfExpenseDto) {
		return modelMapper.map(ccnfExpenseDto, CcnfRMCExpense.class);
	}
	
	public CcnfRMCExpenseDto toCcnfExpenseDto(@Validated CcnfRMCExpense ccnfExpense) {
		return modelMapper.map(ccnfExpense, CcnfRMCExpenseDto.class);
	}
	
	
	
	public CcnfRMCExpense getCcnfExpenseById(Long id) throws Exception {
		log.info("expenseId"+id);
		return ccnfExpenseRepo.findById(id).
    			orElseThrow(() -> new Exception("expenseId not found"));
	 }
	    
	 public List<CcnfRMCExpense> getCcnfExpenseDetails() {
	    	return ccnfExpenseRepo.findAll();
	    }

	

	
	
	   

	    
	  
	
	


}

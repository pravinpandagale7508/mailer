package com.utcl.ccnfservice.rmc.transaction.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCPlantDetails;
import com.utcl.ccnfservice.rmc.transaction.repo.CcnfRMCPlantDetailsRepo;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCPlantDetailsService;
import com.utcl.dto.ccnf.CcnfRMCPlantDetailsDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CcnfRMCPlantDetailsServiceImpl implements CcnfRMCPlantDetailsService{
	private CcnfRMCPlantDetailsRepo ccnfRMCPlantDetailsRepo;
	private ModelMapper modelMapper=new ModelMapper();
	
	public CcnfRMCPlantDetailsServiceImpl( CcnfRMCPlantDetailsRepo ccnfRMCPlantDetailsRepo) {
		super();
		this.ccnfRMCPlantDetailsRepo = ccnfRMCPlantDetailsRepo;
	}

	public CcnfRMCPlantDetailsDto addCcnfRMCPlantDetails(CcnfRMCPlantDetailsDto ccnfRMCPlantDetailsDto) {
		log.info("Agent ServiceDto {}" ,ccnfRMCPlantDetailsDto);
		CcnfRMCPlantDetails rmcPlant=null;
		if (ccnfRMCPlantDetailsRepo.findById(ccnfRMCPlantDetailsDto.getId()).isPresent()) {	
			updateDetails(ccnfRMCPlantDetailsDto);
		} else {
			rmcPlant = ccnfRMCPlantDetailsRepo.save(toCcnfRMCPlantDetails(ccnfRMCPlantDetailsDto));
			ccnfRMCPlantDetailsDto=toCcnfRMCPlantDetailsDto(rmcPlant);
			log.info("agencyDto ServiceDto {}" ,ccnfRMCPlantDetailsDto);

		}
		return ccnfRMCPlantDetailsDto;
	}
	
	private CcnfRMCPlantDetailsDto updateDetails(CcnfRMCPlantDetailsDto ccnfRMCPlantDetailsDto) {
		return  toCcnfRMCPlantDetailsDto(ccnfRMCPlantDetailsRepo.save(toCcnfRMCPlantDetails(ccnfRMCPlantDetailsDto)));
	}
	public CcnfRMCPlantDetails toCcnfRMCPlantDetails(@Validated CcnfRMCPlantDetailsDto ccnfRMCPlantDetailsDto) {
		return modelMapper.map(ccnfRMCPlantDetailsDto, CcnfRMCPlantDetails.class);
	}
	
	public CcnfRMCPlantDetailsDto toCcnfRMCPlantDetailsDto(@Validated CcnfRMCPlantDetails agency) {
		return modelMapper.map(agency, CcnfRMCPlantDetailsDto.class);
	}
	
	
	
	public CcnfRMCPlantDetails getCcnfRMCPlantDetailsById(Long id) throws Exception {
		log.info("id"+id);
		return ccnfRMCPlantDetailsRepo.findById(id).
    			orElseThrow(() -> new Exception("id not found"));
	}
	    public List<CcnfRMCPlantDetails> getAllCcnfRMCPlantDetails() {
	    	return ccnfRMCPlantDetailsRepo.findAll();
	    }
	    
	   

	    
	  
	
	


}

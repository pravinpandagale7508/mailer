package com.utcl.ccnfservice.rmc.transaction.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCLoi;
import com.utcl.ccnfservice.rmc.transaction.repo.CcnfRMCLoiRepo;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCLoiService;
import com.utcl.dto.ccnf.CcnfRMCLoiDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CcnfRMCLoiServiceImpl  implements CcnfRMCLoiService{
	private CcnfRMCLoiRepo ccnfRMCLoiRepo;
	private ModelMapper modelMapper=new ModelMapper();
	
	public CcnfRMCLoiServiceImpl( CcnfRMCLoiRepo ccnfRMCLoiRepo) {
		super();
		this.ccnfRMCLoiRepo = ccnfRMCLoiRepo;
	}

	public CcnfRMCLoiDto addCcnfRMCLoiDetails(CcnfRMCLoiDto ccnfRMCLoiDto) {
		log.info("Agent ServiceDto {}" ,ccnfRMCLoiDto);
		CcnfRMCLoi ccnfRMCLoi=null;
		if (ccnfRMCLoiRepo.findById(ccnfRMCLoiDto.getId()).isPresent()) {	
			updateDetails(ccnfRMCLoiDto);
		} else {
			ccnfRMCLoi = ccnfRMCLoiRepo.save(toCcnfRMCLoi(ccnfRMCLoiDto));
			ccnfRMCLoiDto=toCcnfRMCLoiDto(ccnfRMCLoi);
			log.info("ccnfRMCLoiDto ServiceDto {}" ,ccnfRMCLoiDto);

		}
		return ccnfRMCLoiDto;
	}
	
	private CcnfRMCLoiDto updateDetails(CcnfRMCLoiDto ccnfRMCLoiDto) {
		return  toCcnfRMCLoiDto(ccnfRMCLoiRepo.save(toCcnfRMCLoi(ccnfRMCLoiDto)));
	}
	public CcnfRMCLoi toCcnfRMCLoi(@Validated CcnfRMCLoiDto ccnfRMCLoiDto) {
		return modelMapper.map(ccnfRMCLoiDto, CcnfRMCLoi.class);
	}
	
	public CcnfRMCLoiDto toCcnfRMCLoiDto(@Validated CcnfRMCLoi ccnfRMCLoi) {
		return modelMapper.map(ccnfRMCLoi, CcnfRMCLoiDto.class);
	}
	
	public CcnfRMCLoi getCcnfRMCLoiById(Long id) throws Exception {
		return ccnfRMCLoiRepo.findById(id).
    			orElseThrow(() -> new Exception("LOI not found"));
	}
	    public List<CcnfRMCLoi> getAllCcnfRMCLoi() {
	    	return ccnfRMCLoiRepo.findAll();
	    }
	    
	   
}

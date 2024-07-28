package com.utcl.ccnfservice.master.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.Sloc;
import com.utcl.ccnfservice.master.repo.SlocRepo;
import com.utcl.dto.ccnf.SlocDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
 public class SlocService {
	private  SlocRepo slocRepo;
	private ModelMapper modelMapper=new ModelMapper();
	public SlocService( SlocRepo slocRepo) {
		super();
		this.slocRepo = slocRepo;
		
	
	}
	public SlocDto addSlocDetails(SlocDto slocDto) {
    	log.info("Dto {}" , slocDto);
		Sloc sloc=null;
		if (slocRepo.findById(slocDto.getSlocId()).isPresent()) {	
			updateDetails(slocDto);
		} else {
			sloc = slocRepo.save(toSloc(slocDto));
			slocDto = toSlocDto(sloc);
		}
		return slocDto;
	}
	
	private SlocDto updateDetails(SlocDto slocDto) {
		return toSlocDto(slocRepo.save(toSloc(slocDto)));
	}

	public Sloc toSloc(@Validated SlocDto slocDto) {
		return modelMapper.map(slocDto, Sloc.class);
	}
	
	public SlocDto toSlocDto(@Validated Sloc slocDto) {
		return modelMapper.map(slocDto, SlocDto.class);
	}
	
	
	
	public Sloc getSlocBySlocId(Long slocId)throws Exception {
		return slocRepo.findById(slocId).
				orElseThrow(() -> new Exception("slocRepo not found"));
		
	}
	
	public Sloc getSlocDetailsByDistId(Long distId)throws Exception {
		return slocRepo.findById(distId).
				orElseThrow(() -> new Exception("slocRepo by dist not found"));
		
	}
	
	    public List<Sloc> getSlocDetails() {
	    	return slocRepo.findAll();
	     
	    }

	    
	    public List<Sloc> getSlocDetailsByI2districtId(List<Long> distId) {
			log.info("Print list distId"+distId);
			return slocRepo.getSlocDetailsByI2districtId(distId);
	    			
		}
	    
	  
	
}

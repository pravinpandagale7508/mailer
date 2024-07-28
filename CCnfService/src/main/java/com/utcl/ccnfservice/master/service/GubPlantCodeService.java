package com.utcl.ccnfservice.master.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.GubtPlantCode;
import com.utcl.ccnfservice.master.repo.GubPlantCodeRepo;
import com.utcl.dto.ccnf.GubtPlantCodeDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GubPlantCodeService {
	private GubPlantCodeRepo gubPlantCodeRepo;
	private ModelMapper modelMapper=new ModelMapper();


	public GubPlantCodeService( GubPlantCodeRepo gubPlantCodeRepo) {
		super();
		this.gubPlantCodeRepo = gubPlantCodeRepo;
	
	}

	public GubtPlantCodeDto addGubPlantCodeDetails(GubtPlantCodeDto gubPlantCodeDto) {
    	log.info("Dto {}" , gubPlantCodeDto);
		GubtPlantCode gubPlantCode=null;
		if (gubPlantCodeRepo.findById(gubPlantCodeDto.getGubId()).isPresent()) {	
			updateDetails(gubPlantCodeDto);
		} else {
			gubPlantCode = gubPlantCodeRepo.save(toGubPlantCode(gubPlantCodeDto));
			gubPlantCodeDto = toGubPlantCodeDto(gubPlantCode);

		}
		return gubPlantCodeDto;
	}
	
	private GubtPlantCodeDto updateDetails(GubtPlantCodeDto gubPlantCodeDto) {
		return  toGubPlantCodeDto(gubPlantCodeRepo.save(toGubPlantCode(gubPlantCodeDto)));
	}

	public GubtPlantCode toGubPlantCode(@Validated GubtPlantCodeDto gubPlantCodeDto) {
		return modelMapper.map(gubPlantCodeDto, GubtPlantCode.class);
	}
	public GubtPlantCodeDto toGubPlantCodeDto(@Validated GubtPlantCode gubPlantCode) {
		return modelMapper.map(gubPlantCode, GubtPlantCodeDto.class);
	}
	
	
	public GubtPlantCode getGubPlantCodeByGubId(Long gubId)throws Exception {
		return gubPlantCodeRepo.findById(gubId).
				orElseThrow(() -> new Exception("gubtPlantCode not found"));
	}
	
	    public List<GubtPlantCode> getGubPlantCodeDetails() {
	    	return gubPlantCodeRepo.findAll();
	    }

	    
	 
	

}

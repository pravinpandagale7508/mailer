package com.utcl.ccnfservice.master.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.RmcPlantCode;
import com.utcl.ccnfservice.master.repo.RmcPlantCodeRepo;
import com.utcl.dto.ccnf.RmcPlantCodeDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RmcPlantCodeService {
	private RmcPlantCodeRepo rmcPlantCodeRepo;
	private ModelMapper modelMapper=new ModelMapper();


	public RmcPlantCodeService( RmcPlantCodeRepo rmcPlantCodeRepo) {
		super();
		this.rmcPlantCodeRepo = rmcPlantCodeRepo;
	
	}

	public RmcPlantCodeDto addRmcPlantCodeDetails(RmcPlantCodeDto rmcPlantCodeDto) {
    	log.info("Dto {}" , rmcPlantCodeDto);
		RmcPlantCode rmcPlantCode=null;
		Optional<RmcPlantCode>  rmcPlantOptional=rmcPlantCodeRepo.findById(rmcPlantCodeDto.getId());
		if (rmcPlantOptional.isPresent()) {	
			//merge
			rmcPlantCode=rmcPlantOptional.get();
			rmcPlantCode.setRmcCode(rmcPlantCodeDto.getRmcCode()!=null ? rmcPlantCodeDto.getRmcCode():rmcPlantCode.getRmcCode());
			updateDetails(rmcPlantCodeDto);
		} else {
			rmcPlantCode = rmcPlantCodeRepo.save(toRmcPlantCode(rmcPlantCodeDto));
			rmcPlantCodeDto = toRmcPlantCodeDto(rmcPlantCode);

		}
		return rmcPlantCodeDto;
	}
	
	private RmcPlantCodeDto updateDetails(RmcPlantCodeDto rmcPlantCodeDto) {
		return  toRmcPlantCodeDto(rmcPlantCodeRepo.save(toRmcPlantCode(rmcPlantCodeDto)));
	}

	public RmcPlantCode toRmcPlantCode(@Validated RmcPlantCodeDto rmcPlantCodeDto) {
		return modelMapper.map(rmcPlantCodeDto, RmcPlantCode.class);
	}
	public RmcPlantCodeDto toRmcPlantCodeDto(@Validated RmcPlantCode rmcPlantCode) {
		return modelMapper.map(rmcPlantCode, RmcPlantCodeDto.class);
	}
	
	
	public RmcPlantCode getRmcPlantCodeById(Long rmcId)throws Exception {
		return rmcPlantCodeRepo.findById(rmcId).
				orElseThrow(() -> new Exception("rmctPlantCode not found"));
	}
	
	    public List<RmcPlantCode> getRmcPlantCodeDetails() {
	    	return rmcPlantCodeRepo.findAll();
	    }

	   
	 
	

}

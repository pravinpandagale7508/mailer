package com.utcl.ccnfservice.master.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.County;
import com.utcl.ccnfservice.master.repo.CountyRepo;
import com.utcl.dto.ccnf.CountyDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountyService {
	private CountyRepo countryRepo;
	private ModelMapper modelMapper=new ModelMapper();
	
	public CountyService( CountyRepo countryRepo) {
		super();
		this.countryRepo = countryRepo;
	}

	public CountyDto addCountyDetails(CountyDto countryDto) {
		log.info("Agent ServiceDto {}" ,countryDto);
		County country=null;
		if (countryRepo.findById(countryDto.getId()).isPresent()) {	
			updateDetails(countryDto);
		} else {
			country = countryRepo.save(toCounty(countryDto));
			countryDto=toCountyDto(country);
			log.info("countryDto ServiceDto {}" ,countryDto);

		}
		return countryDto;
	}
	
	private CountyDto updateDetails(CountyDto countryDto) {
		return  toCountyDto(countryRepo.save(toCounty(countryDto)));
	}
	public County toCounty(@Validated CountyDto countryDto) {
		return modelMapper.map(countryDto, County.class);
	}
	
	public CountyDto toCountyDto(@Validated County country) {
		return modelMapper.map(country, CountyDto.class);
	}
	
	
	
	public County getCountyById(Long id) throws Exception {
		log.info("id"+id);
		return countryRepo.findById(id).
    			orElseThrow(() -> new Exception("country not found"));
	}
	    public List<County> getCountyDetails() {
	    	return countryRepo.findAll();
	    }

	    
	  
	
	


}

package com.utcl.ccnfservice.master.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.City;
import com.utcl.ccnfservice.master.repo.CityRepo;
import com.utcl.dto.ccnf.CityDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CityService {
	private CityRepo cityRepo;
	private ModelMapper modelMapper=new ModelMapper();
	
	public CityService( CityRepo cityRepo) {
		super();
		this.cityRepo = cityRepo;
	}

	public CityDto addCityDetails(CityDto cityDto) {
		log.info("Agent ServiceDto {}" ,cityDto);
		City city=null;
		if (cityRepo.findById(cityDto.getId()).isPresent()) {	
			updateDetails(cityDto);
		} else {
			city = cityRepo.save(toCity(cityDto));
			cityDto=toCityDto(city);
			log.info("cityDto ServiceDto {}" ,cityDto);

		}
		return cityDto;
	}
	
	private CityDto updateDetails(CityDto cityDto) {
		return  toCityDto(cityRepo.save(toCity(cityDto)));
	}
	public City toCity(@Validated CityDto cityDto) {
		return modelMapper.map(cityDto, City.class);
	}
	
	public CityDto toCityDto(@Validated City city) {
		return modelMapper.map(city, CityDto.class);
	}
	
	
	
	public City getCityById(Long id) throws Exception {
		log.info("id"+id);
		return cityRepo.findById(id).
    			orElseThrow(() -> new Exception("city not found"));
	}
	    public List<City> getCityDetails() {
	    	return cityRepo.findAll();
	    }

	    public List<City> getCitiesByI2TalukaIds(List<Long> i2TqIds) {
			log.info("Print list"+i2TqIds);
			return cityRepo.getCitiesByI2TalukaIds(i2TqIds);
	    			
		}
		  
	  
	
	


}

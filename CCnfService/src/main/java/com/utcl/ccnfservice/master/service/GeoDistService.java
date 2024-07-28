package com.utcl.ccnfservice.master.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.GeoDist;
import com.utcl.ccnfservice.master.repo.GeoDistRepo;
import com.utcl.dto.ccnf.GeoDistDto;

import lombok.extern.slf4j.Slf4j;

	@Service
	@Slf4j
  public class GeoDistService {
		private GeoDistRepo geoDistRepo;
		private ModelMapper modelMapper=new ModelMapper();

		public GeoDistService( GeoDistRepo geoDistRepo) {
			super();
			this.geoDistRepo = geoDistRepo;
		
		}

		public GeoDistDto addGeoDistDetails(GeoDistDto geoDistDto) {
	    	log.info("Dto {}" , geoDistDto);
			GeoDist geoDist=null;
			if (geoDistRepo.findById(geoDistDto.getId()).isPresent()) {	
				updateDetails(geoDistDto);
			} else {
				geoDist = geoDistRepo.save(toGeoDist(geoDistDto));
				geoDistDto = toGeoDistDto(geoDist);

			}
			return geoDistDto;
		}
		private GeoDistDto updateDetails(GeoDistDto geoDistDto) {
			return toGeoDistDto(geoDistRepo.save(toGeoDist(geoDistDto)));

		}
		public GeoDist toGeoDist(@Validated GeoDistDto geoDistDto) {
			return modelMapper.map(geoDistDto, GeoDist.class);
		}
		
		public GeoDistDto toGeoDistDto(@Validated GeoDist geoDist) {
			return modelMapper.map(geoDist, GeoDistDto.class);
		}
		
		
		
		public GeoDist getGeoDistById(Long id) throws Exception{
			return geoDistRepo.findById(id).
	    			orElseThrow(() -> new Exception("GeoDist not found"));
		}
		
		 public List<GeoDist> getGeoDistDetails() {
		    	return geoDistRepo.findAll();
		        
		    }
		
		
		

		
		   

	}



package com.utcl.ccnfservice.master.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.GeoTaluka;
import com.utcl.ccnfservice.master.repo.GeoTalukaRepo;
import com.utcl.dto.ccnf.GeoTalukaDto;

import lombok.extern.slf4j.Slf4j;

	@Service
	@Slf4j
  public class GeoTalukaService {
		private GeoTalukaRepo geoTalukaRepo;
		private ModelMapper modelMapper=new ModelMapper();

		public GeoTalukaService( GeoTalukaRepo geoTalukaRepo) {
			super();
			this.geoTalukaRepo = geoTalukaRepo;
		
		}

		public GeoTalukaDto addGeoTalukaDetails(GeoTalukaDto geoTalukaDto) {
	    	log.info("Dto {}" , geoTalukaDto);
			GeoTaluka depot=null;
			if (geoTalukaRepo.findById(geoTalukaDto.getId()).isPresent()) {	
				updateDetails(geoTalukaDto);
			} else {
				depot = geoTalukaRepo.save(toGeoTaluka(geoTalukaDto));
				geoTalukaDto = toGeoTalukaDto(depot);

			}
			return geoTalukaDto;
		}
		private GeoTalukaDto updateDetails(GeoTalukaDto geoTalukaDto) {
			return toGeoTalukaDto(geoTalukaRepo.save(toGeoTaluka(geoTalukaDto)));

		}
		public GeoTaluka toGeoTaluka(@Validated GeoTalukaDto geoTalukaDto) {
			return modelMapper.map(geoTalukaDto, GeoTaluka.class);
		}
		
		public GeoTalukaDto toGeoTalukaDto(@Validated GeoTaluka depot) {
			return modelMapper.map(depot, GeoTalukaDto.class);
		}
		
		
		
		public GeoTaluka getGeoTalukaById(Long id) throws Exception{
			return geoTalukaRepo.findById(id).
	    			orElseThrow(() -> new Exception("GeoTaluka not found"));
		}
		
		 public List<GeoTaluka> getGeoTalukaDetails() {
		    	return geoTalukaRepo.findAll();
		        
		    }
		
		 public List<GeoTaluka> getGeoTalukaByGeoDistrictIds(List<Long> geoDistIds) {
				log.info("Print list"+geoDistIds);
				return geoTalukaRepo.getGeoTalukaByGeoDistrictIds(geoDistIds);
		    			
			}
		

		
		   

	}



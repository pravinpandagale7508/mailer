package com.utcl.ccnfservice.master.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.ccnfservice.master.repo.RegionRepo;
import com.utcl.dto.ccnf.RegionDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegionService {
   private RegionRepo regionRepo;
   private ModelMapper modelMapper=new ModelMapper();
	
   public RegionService( RegionRepo regionRepo) {
		super();
		this.regionRepo = regionRepo;
	}
    public RegionDto addRegionDetails(RegionDto regionDto) {
    	log.info("Dto {}" , regionDto);
		Region region=null;
		if (regionRepo.findById(regionDto.getRegionId()).isPresent()) {	
			updateDetails(regionDto);
		} else {
			region = regionRepo.save(toRegion(regionDto));
			regionDto =toRegionDto(region);

		}
		return regionDto;
	}
    
    private RegionDto updateDetails(RegionDto regionDto) {
		return toRegionDto(regionRepo.save(toRegion(regionDto)));
	}

	public Region toRegion(@Validated RegionDto regionDto) {
		return modelMapper.map(regionDto, Region.class);
	}
	
	public RegionDto toRegionDto(@Validated Region region) {
		return modelMapper.map(region, RegionDto.class);
	}
	


	public Region getRegionByRegionId(Long regionId)throws Exception {
		return regionRepo.findById(regionId).
				orElseThrow(() -> new Exception("region not found"));
		
	}
	
	    public List<Region> getRegionDetails() {
	    	return  regionRepo.findAll();
			
	    }

	    public List<Region> getRegionByStateIds(List<Long> stateIds) {
			log.info("Print list Region"+stateIds);
			return regionRepo.getRegionByStateIds(stateIds);
	    			
		}
	    
	    public List<String> getRegionNameByRegionId(List<Long> regionId) throws Exception {
			List<String> regionNames=new ArrayList<>();
			List<Region> regiontlist=	regionRepo.getRegionNameByregionIds(regionId);
			for(Region region: regiontlist) {
				regionNames.add(region.getRegionName());
			}
		   return regionNames;
		}
	
	
}

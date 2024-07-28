package com.utcl.ccnfservice.master.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.I2district;
import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.ccnfservice.master.repo.I2districtRepo;
import com.utcl.dto.ccnf.I2districtDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class I2districtService {
	
	private I2districtRepo i2districtRepo;
	private ModelMapper modelMapper=new ModelMapper();

	public I2districtService(I2districtRepo i2districtRepo) {
		super();
		this.i2districtRepo = i2districtRepo;
		
	
	}
	public I2districtDto addI2districtDetails(I2districtDto i2districtDto) {
    	log.info("Dto {}" , i2districtDto);
		I2district i2district=null;
		if (i2districtRepo.findById(i2districtDto.getDistId()).isPresent()) {	
			updateDetails(i2districtDto);
		} else {
			i2district = i2districtRepo.save(toI2district(i2districtDto));
			i2districtDto = toI2districtDto(i2district);

		}
		return i2districtDto;
	}
	private I2districtDto updateDetails(I2districtDto i2districtDto) {
		return toI2districtDto(i2districtRepo.save(toI2district(i2districtDto)));
	}

	public I2district toI2district(@Validated I2districtDto i2districtDto) {
		return modelMapper.map(i2districtDto, I2district.class);
	}
	
	public I2districtDto toI2districtDto(@Validated I2district i2district) {
		return modelMapper.map(i2district, I2districtDto.class);
	}
	

	public I2district getI2districtByDistId(Long gubId)throws Exception {
		return i2districtRepo.findById(gubId).
				orElseThrow(() -> new Exception("i2districtRepo not found"));
		
	}
	
	    public List<I2district> getI2districtDetails() {
	    	return i2districtRepo.findAll();
	        
	    }
	    
	    
	    public List<I2district> getI2districtByDepotId(List<Long> depotId) {
			log.info("Print list depotId"+depotId);
			return i2districtRepo.getI2districtByDepotId(depotId);
	    			
		}

	    
	    public List<String> getI2districtNameByIds(List<Long> distIds) throws Exception {
			List<String> districtNames=new ArrayList<>();
			List<I2district> distlist=	i2districtRepo.getI2districtNameByIds(distIds);
			for(I2district dist: distlist) {
				districtNames.add(dist.getDistName());
			}
		   return districtNames;
		}
	    
	 

}

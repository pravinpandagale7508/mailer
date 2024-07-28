package com.utcl.ccnfservice.master.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.I2Taluka;
import com.utcl.ccnfservice.master.repo.I2TalukaRepo;
import com.utcl.dto.ccnf.I2TalukaDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class I2TalukaService {
	
	private I2TalukaRepo i2TalukaRepo;
	private ModelMapper modelMapper=new ModelMapper();

	public I2TalukaService(I2TalukaRepo i2TalukaRepo) {
		super();
		this.i2TalukaRepo = i2TalukaRepo;
		
	
	}
	public I2TalukaDto addI2TalukaDetails(I2TalukaDto i2districtDto) {
    	log.info("Dto {}" , i2districtDto);
		I2Taluka i2district=null;
		if (i2TalukaRepo.findById(i2districtDto.getId()).isPresent()) {	
			updateDetails(i2districtDto);
		} else {
			i2district = i2TalukaRepo.save(toI2Taluka(i2districtDto));
			i2districtDto = toI2TalukaDto(i2district);

		}
		return i2districtDto;
	}
	private I2TalukaDto updateDetails(I2TalukaDto i2districtDto) {
		return toI2TalukaDto(i2TalukaRepo.save(toI2Taluka(i2districtDto)));
	}

	public I2Taluka toI2Taluka(@Validated I2TalukaDto i2districtDto) {
		return modelMapper.map(i2districtDto, I2Taluka.class);
	}
	
	public I2TalukaDto toI2TalukaDto(@Validated I2Taluka i2district) {
		return modelMapper.map(i2district, I2TalukaDto.class);
	}
	

	public I2Taluka getI2TalukaByDistId(Long id)throws Exception {
		return i2TalukaRepo.findById(id).
				orElseThrow(() -> new Exception("i2TalukaRepo not found"));
		
	}
	
	    public List<I2Taluka> getI2TalukaDetails() {
	    	return i2TalukaRepo.findAll();
	        
	    }
	    
	    
	    public List<I2Taluka> getI2TalukabyI2DistricsIds(List<Long> geoDistId) {
			log.info("Print list getI2TalukabyI2DistricsIds"+geoDistId);
			return i2TalukaRepo.getI2TalukabyI2DistricsIds(geoDistId);
	    			
		}

	    
	 

}

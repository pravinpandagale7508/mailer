package com.utcl.ccnfservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import lombok.extern.slf4j.Slf4j;

import com.utcl.ccnfservice.master.entity.City;
import com.utcl.ccnfservice.master.repo.CityRepo;
import com.utcl.ccnfservice.master.service.CityService;
import com.utcl.dto.ccnf.CityDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CityServiceTest {
	
     @Mock
     private CityRepo cityRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
    @InjectMocks
    private CityService cityService;
    
    
    private CityDto cityDto=new CityDto();
    private City city=new City();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        city.setId(1L);
        city.setCityName("ABC");
        city.setCreatedBy("Varsga1");
        city.setUpdatedBy("Varsga1");
      
        cityDto.setId(1L);
        cityDto.setCityName("ABC");
        //cityDto.setCreatedBy("Varsga1");
       // cityDto.setUpdatedBy("Varsga1");
        when(modelMapper.map(any(), any())).thenReturn(cityDto);
    }
     @Test
     @Order(1)
     public void testAddCity() throws Exception {
    	when(cityRepo.save(Mockito.any(City.class))).thenReturn(city);
	    log.info("print my values agencies: {}", cityService.addCityDetails(cityDto));
		CityDto addDto = cityService.addCityDetails(cityDto);
		log.info("log cityDetails: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetCityById() throws Exception {
        when(cityRepo.findById(city.getId())).thenReturn(java.util.Optional.of(city));
        city = cityService.getCityById(1L);
        assertEquals("ABC", city.getCityName());
        assertEquals("Varsga1", city.getCreatedBy());
        assertEquals("Varsga1", city.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllCitys() throws Exception {
    	List<City> cityList=new ArrayList<City>();
    	cityList.add(city);
        when(cityRepo.findAll()).thenReturn((cityList));
        List<City> agcList=cityService.getCityDetails();
        assertEquals(agcList, cityList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}

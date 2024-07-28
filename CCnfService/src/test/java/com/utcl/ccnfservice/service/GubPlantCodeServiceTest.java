package com.utcl.ccnfservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.utcl.ccnfservice.master.entity.GubtPlantCode;
import com.utcl.ccnfservice.master.repo.GubPlantCodeRepo;
import com.utcl.ccnfservice.master.service.GubPlantCodeService;
import com.utcl.dto.ccnf.GubtPlantCodeDto;

import lombok.extern.slf4j.Slf4j;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Slf4j
public class GubPlantCodeServiceTest {
	
     @Mock
     private GubPlantCodeRepo gubPlantCodeRepo ;
     
     @Mock
  	 private ModelMapper modelMapper = new ModelMapper();
     
     
    @InjectMocks
    private GubPlantCodeService gubPlantCodeService;
    
    
    private GubtPlantCodeDto gubPlantCodeDto=new GubtPlantCodeDto();
    private GubtPlantCode gubPlantCode=new GubtPlantCode();
    
     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gubPlantCode.setGubId(1L);
        gubPlantCode.setGubName("ABC");
        gubPlantCode.setCreatedBy("Varsga1");
        gubPlantCode.setUpdatedBy("Varsga1");
      
        gubPlantCodeDto.setGubId(1L);
        gubPlantCodeDto.setGubName("ABC");
        when(modelMapper.map(any(), any())).thenReturn(gubPlantCodeDto);
    }
     @Test
     @Order(1)
     public void testAddGubtPlantCode() throws Exception {
    	when(gubPlantCodeRepo.save(Mockito.any(GubtPlantCode.class))).thenReturn(gubPlantCode);
	    log.info("print my values gubplantcode: {}", gubPlantCodeService.addGubPlantCodeDetails(gubPlantCodeDto));
	    GubtPlantCodeDto addDto = gubPlantCodeService.addGubPlantCodeDetails(gubPlantCodeDto);
		log.info("log gubplantcode: {}", addDto);
		assertThat(addDto).isNotNull();
     	
     }

    @Test
    @Order(2)
    void testGetGubtPlantCodeById() throws Exception {
        when(gubPlantCodeRepo.findById(gubPlantCode.getGubId())).thenReturn(java.util.Optional.of(gubPlantCode));
        gubPlantCode = gubPlantCodeService.getGubPlantCodeByGubId(1L);
        assertEquals("ABC", gubPlantCode.getGubName());
        assertEquals("Varsga1", gubPlantCode.getCreatedBy());
        assertEquals("Varsga1", gubPlantCode.getUpdatedBy());

       
    }
    
    @Test
    @Order(3)
    void testGetAllGubtPlantCodes() throws Exception {
    	List<GubtPlantCode> gubPlantCodeList=new ArrayList<GubtPlantCode>();
    	gubPlantCodeList.add(gubPlantCode);
        when(gubPlantCodeRepo.findAll()).thenReturn((gubPlantCodeList));
        List<GubtPlantCode> agcList=gubPlantCodeService.getGubPlantCodeDetails();
        assertEquals(agcList, gubPlantCodeList);
        assertThat(agcList).isNotNull();
        assertThat(agcList.size()).isEqualTo(1);
    }
  

	


  
}

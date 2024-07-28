package com.utcl.ccnfservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.utcl.ccnfservice.cement.transaction.entity.MinimalCommision;
import com.utcl.ccnfservice.cement.transaction.repo.MinimalCommisionRepo;
import com.utcl.ccnfservice.cement.transaction.service.MinimalCommisionService;
import com.utcl.dto.ccnf.MinimalCommisionDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinimalCommissionServiceTest {

	@Mock
	private MinimalCommisionRepo minimalCommisiionRepo;
	@Mock
	private ModelMapper modelMapper = new ModelMapper();


	@InjectMocks
	private MinimalCommisionService minimalCommissionService;

	private MinimalCommisionDto minimalCommissionDto = new MinimalCommisionDto();
	private MinimalCommision minimalCommision = new MinimalCommision();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		minimalCommision.setMinimalId(10000L);;
		minimalCommision.setCreatedBy("MMM");

		minimalCommissionDto.setMinimalId(10000L);
		//minimalCommissionDto.setCreatedBy("MMM");

		when(modelMapper.map(any(), any())).thenReturn(minimalCommissionDto);

		// when(cCnfLoiRepo.save(cCnfCementLoi)).thenReturn(cCnfCementLoi);

	}
	
	@Test
	void testAddMinimalCommissionDetails() throws Exception {
		log.info("gggg" + (minimalCommissionDto));

		when(minimalCommisiionRepo.save(minimalCommision)).thenReturn(minimalCommision);
		// When
		MinimalCommisionDto minimalCommisionDto1 = minimalCommissionService.addMinimalCommisionCommission(minimalCommissionDto);
		log.info("++++++" + (minimalCommisionDto1));
		// Then
		assertThat(minimalCommisionDto1).isNotNull();
		verify(minimalCommisiionRepo).save(any(MinimalCommision.class));// check instance of entity

	}


	@Test
	void testGetListOfMinimalCommission() throws Exception {
		
		
		List<MinimalCommision> minimalComissionList = new ArrayList<>();
		MinimalCommision minimalCommision = new MinimalCommision();
		minimalCommision.setMinimalId(10000L);
		minimalComissionList.add(minimalCommision);
		
		when(minimalCommisiionRepo.findAll()).thenReturn(minimalComissionList);

		// When
		List<MinimalCommision> minimalComision = minimalCommissionService.getMinimalCommisionCommission();
		log.info("list of minimial"+minimalComision.size());
		// Then
		verify(minimalCommisiionRepo, times(1)).findAll();
		assertThat(minimalComision).isNotNull();
		assertEquals(minimalComissionList, minimalComision);
		// Given

	}

	@Test
	void testGetMinimalCommissionById() throws Exception {

		Long minmalId = 10000L;

		when(minimalCommisiionRepo.findById(minmalId)).thenReturn(java.util.Optional.of(minimalCommision));
		// When
		 minimalCommision = minimalCommissionService.getMinimalCommisionCommissionById(minmalId);
		// Then
		verify(minimalCommisiionRepo, times(1)).findById(minmalId);

		assertEquals("MMM", minimalCommision.getCreatedBy());

	}

	@Test
	public void updateMinimalCommission() {
		long minmalId = 10000L;

		// given - precondition or setup
		minimalCommision.setMinimalId(10000L);
		minimalCommision.setCreatedBy("MMM");
		
		
		when(minimalCommisiionRepo.findById(minmalId)).thenReturn(java.util.Optional.of(minimalCommision));
		//minimalCommissionDto.setCreatedBy("MMM");
		when(minimalCommisiionRepo.save(minimalCommision)).thenReturn(minimalCommision);
		// when - action or the behaviour that we are going test
		MinimalCommisionDto updatededuction = minimalCommissionService.updateDetails(minimalCommissionDto);

		// then - verify the output
		//assertThat(updatededuction.getCreatedBy()).isEqualTo("MMM");
	}

	@Test
	public void testMinimalCommissionById() {
		// given - precondition or setup
		long minmalId = 10000L;

		doNothing().when(minimalCommisiionRepo).deleteById(minmalId);

		// when - action or the behaviour that we are going test
		minimalCommissionService.deleteMinimalCommisionCommissionById(minmalId);

		// then - verify the output
		verify(minimalCommisiionRepo, times(1)).deleteById(minmalId);
	}

}

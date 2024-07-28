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

import com.utcl.ccnfservice.cement.transaction.entity.DeductiononDirectSales;
import com.utcl.ccnfservice.cement.transaction.repo.DeductiononDirectSalesRepo;
import com.utcl.ccnfservice.cement.transaction.service.DeductionCommisionService;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeductionDirectSalesServiceTest {

	@Mock
	private DeductiononDirectSalesRepo deductiononDirectSalesRepo;
	@Mock
	private ModelMapper modelMapper = new ModelMapper();


	@InjectMocks
	private DeductionCommisionService deductionCommisionService;

	private DeductiononDirectSalesDto deductiononDirectSalesDto = new DeductiononDirectSalesDto();
	private DeductiononDirectSales deductiononDirectSales = new DeductiononDirectSales();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		deductiononDirectSales.setDeductionId(2000000000L);;
		deductiononDirectSales.setCreatedBy("DDD");

		deductiononDirectSalesDto.setDeductionId(2000000000L);
		//deductiononDirectSalesDto.setCreatedBy("DDD");

		when(modelMapper.map(any(), any())).thenReturn(deductiononDirectSalesDto);
	}
	
	@Test
	void testAddCcnfDeductionDetails() throws Exception {
		log.info("gggg" + (deductiononDirectSalesDto));

		when(deductiononDirectSalesRepo.save(deductiononDirectSales)).thenReturn(deductiononDirectSales);
		// When
		DeductiononDirectSalesDto deductiononDirectSalesDto1 = deductionCommisionService.addDeductionDirectSales(deductiononDirectSalesDto);
		log.info("++++++" + (deductiononDirectSalesDto1));
		// Then
		assertThat(deductiononDirectSalesDto1).isNotNull();
		verify(deductiononDirectSalesRepo).save(any(DeductiononDirectSales.class));// check instance of entity

	}


	@Test
	void testGetDirectDeductions() throws Exception {
		
		
		List<DeductiononDirectSales> deductiononDirectSalesList = new ArrayList<>();
		DeductiononDirectSales deductiononDirectSales = new DeductiononDirectSales();
		deductiononDirectSales.setDeductionId(2000000000L);
		deductiononDirectSalesList.add(deductiononDirectSales);

		when(deductiononDirectSalesRepo.findAll()).thenReturn(deductiononDirectSalesList);

		// When
		List<DeductiononDirectSales> lisOfdeductions = deductionCommisionService.getDirectDeductions();
		log.info("list of deductions size"+lisOfdeductions.size());
		// Then
		verify(deductiononDirectSalesRepo, times(1)).findAll();
		assertThat(lisOfdeductions).isNotNull();
		assertEquals(deductiononDirectSalesList, lisOfdeductions);
		// Given

	}

	@Test
	void testGetDirectDeductionsById() throws Exception {

		Long deductionId = 2000000000L;

		when(deductiononDirectSalesRepo.findById(deductionId)).thenReturn(java.util.Optional.of(deductiononDirectSales));
		// When
		 deductiononDirectSales = deductionCommisionService.getDirectDeductionsById(deductionId);
		// Then
		verify(deductiononDirectSalesRepo, times(1)).findById(deductionId);

		assertEquals("DDD", deductiononDirectSales.getCreatedBy());

	}

	@Test
	public void updateLoiDetails() {
		long deductionId = 2000000000L;

		// given - precondition or setup
		deductiononDirectSales.setDeductionId(2000000000L);
		deductiononDirectSales.setCreatedBy("DDD");
		
		
		when(deductiononDirectSalesRepo.findById(deductionId)).thenReturn(java.util.Optional.of(deductiononDirectSales));
		//deductiononDirectSalesDto.setCreatedBy("DDD");
		when(deductiononDirectSalesRepo.save(deductiononDirectSales)).thenReturn(deductiononDirectSales);
		// when - action or the behaviour that we are going test
		DeductiononDirectSalesDto updatededuction = deductionCommisionService.updateDetails(deductiononDirectSalesDto);

		// then - verify the output
		//assertThat(updatededuction.getCreatedBy()).isEqualTo("DDD");
	}

	@Test
	public void testDeleteLoi() {
		// given - precondition or setup
		long deductionId = 2000000000L;

		doNothing().when(deductiononDirectSalesRepo).deleteById(deductionId);

		// when - action or the behaviour that we are going test
		deductionCommisionService.deleteDirectDeductionsById(deductionId);

		// then - verify the output
		verify(deductiononDirectSalesRepo, times(1)).deleteById(deductionId);
	}

}

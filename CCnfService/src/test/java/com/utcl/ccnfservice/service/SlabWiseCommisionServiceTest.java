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

import com.utcl.ccnfservice.cement.transaction.entity.SlabwiseCommision;
import com.utcl.ccnfservice.cement.transaction.repo.SlabwisecommisionRepo;
import com.utcl.ccnfservice.cement.transaction.service.CcnfSlabwiseCommisionService;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlabWiseCommisionServiceTest {

	@Mock
	private SlabwisecommisionRepo slabwisecommisionRepo;
	@Mock
	private ModelMapper modelMapper = new ModelMapper();


	@InjectMocks
	private CcnfSlabwiseCommisionService slabwiseCommisionService;

	private SlabwiseCommisionDto slabwiseCommisionDto = new SlabwiseCommisionDto();
	private SlabwiseCommision slabwiseCommision = new SlabwiseCommision();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		slabwiseCommision.setSlabId(2000000000L);
		;
		slabwiseCommision.setCreatedBy("SSS");

		slabwiseCommisionDto.setSlabId(2000000000L);
		//slabwiseCommisionDto.setCreatedBy("SSS");

		when(modelMapper.map(any(), any())).thenReturn(slabwiseCommisionDto);
	}

	@Test
	void testAddSlabWiseDetails() throws Exception {
		log.info("slabwiseCommisionDto" + (slabwiseCommisionDto));

		when(slabwisecommisionRepo.save(slabwiseCommision)).thenReturn(slabwiseCommision);
		// When
		SlabwiseCommisionDto slabwiseCommisionDto1 = slabwiseCommisionService
				.addSlabwiseCommission(slabwiseCommisionDto);
		log.info("++++++" + (slabwiseCommisionDto1));
		// Then
		assertThat(slabwiseCommisionDto1).isNotNull();
		verify(slabwisecommisionRepo).save(any(SlabwiseCommision.class));// check instance of entity

	}

	@Test
	void testGetListSlabWiseCommisssion() throws Exception {

		List<SlabwiseCommision> slabList = new ArrayList<>();
		SlabwiseCommision slabwiseCommision = new SlabwiseCommision();
		slabwiseCommision.setSlabId(2000000000L);
		slabList.add(slabwiseCommision);

		// when(cCnfLoiRepo.findById(loiId)).thenReturn(java.util.Optional.of(cCnfCementLoi));
		when(slabwisecommisionRepo.findAll()).thenReturn(slabList);

		// When
		List<SlabwiseCommision> lisOfslabCommission = slabwiseCommisionService.getSlabwiseCommissions();
		log.info("lisOfslabCommission"+ lisOfslabCommission.size());
		// Then
		verify(slabwisecommisionRepo, times(1)).findAll();
		assertThat(lisOfslabCommission).isNotNull();
		assertEquals(slabList, lisOfslabCommission);
		// Given

	}

	@Test
	void testgetSlabwiseCommissionById() throws Exception {

		Long slabId = 2000000000L;

		when(slabwisecommisionRepo.findById(slabId)).thenReturn(java.util.Optional.of(slabwiseCommision));
		// When
		slabwiseCommision = slabwiseCommisionService.getSlabwiseCommissionById(slabId);
		// Then
		verify(slabwisecommisionRepo, times(1)).findById(slabId);

		assertEquals("SSS", slabwiseCommision.getCreatedBy());

	}

	@Test
	public void updateSlabDetails() {
		long deductionId = 2000000000L;

		// given - precondition or setup
		slabwiseCommision.setSlabId(2000000000L);
		slabwiseCommision.setCreatedBy("SSS");

		when(slabwisecommisionRepo.findById(deductionId)).thenReturn(java.util.Optional.of(slabwiseCommision));
		//slabwiseCommisionDto.setCreatedBy("SSS");
		when(slabwisecommisionRepo.save(slabwiseCommision)).thenReturn(slabwiseCommision);
		// when - action or the behaviour that we are going test
		SlabwiseCommisionDto updatededuction = slabwiseCommisionService.updateDetails(slabwiseCommisionDto);

		// then - verify the output
		//assertThat(updatededuction.getCreatedBy()).isEqualTo("SSS");
	}

	@Test
	public void testDeleteSlab() {
		// given - precondition or setup
		long deductionId = 2000000000L;

		doNothing().when(slabwisecommisionRepo).deleteById(deductionId);

		// when - action or the behaviour that we are going test
		slabwiseCommisionService.deleteSlabwiseCommissionById(deductionId);

		// then - verify the output
		verify(slabwisecommisionRepo, times(1)).deleteById(deductionId);
	}

}

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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;
import com.utcl.ccnfservice.cement.transaction.repo.CCnfLoiRepo;
import com.utcl.ccnfservice.cement.transaction.service.CcnfTransactionService;
import com.utcl.dto.ccnf.CCnfLoiDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CcnfTransactionServiceTest {

	@Mock
	private CCnfLoiRepo cCnfLoiRepo;
	@Mock
	private ModelMapper modelMapper = new ModelMapper();

	
	@InjectMocks
	private CcnfTransactionService ccnfTransactionService;

	private CCnfLoiDto cCnfLoiDto = new CCnfLoiDto();
	private CCnfCementLoi cCnfCementLoi = new CCnfCementLoi();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		cCnfCementLoi.setLoiId(30000000000L);
		//cCnfCementLoi.setAgentId(2L);
		List<Long> slocs = new ArrayList<Long>();
		slocs.add(1234L);
		cCnfCementLoi.setI2Dists(slocs);

		cCnfLoiDto.setLoiId(30000000000L);
		//cCnfLoiDto.setAgentId(2L);
		cCnfLoiDto.setI2Dists(slocs);

		when(modelMapper.map(any(), any())).thenReturn(cCnfLoiDto);
		

	}

	@Test
	@Order(1)
	void testAddCcnfLoiDetails() throws Exception {
		log.info("cCnfLoiDto sevice" + (cCnfLoiDto));

		when(cCnfLoiRepo.save(cCnfCementLoi)).thenReturn(cCnfCementLoi);
		// When
		//CCnfLoiDto cCnfLoiDto1 = ccnfTransactionService.addCcnfLoiDetails(cCnfLoiDto);
		//log.info("++++++" + (cCnfLoiDto1));
		// Then
		//assertThat(cCnfLoiDto1).isNotNull();
		//verify(cCnfLoiRepo).save(any(CCnfCementLoi.class));// check instance of entity

	}

	@Test
	void testGetCcnfLoiById() throws Exception {
		// Given
		Long loiId = 30000000000L;
		List<Long> slocs = new ArrayList<Long>();
		slocs.add(1234L);

		when(cCnfLoiRepo.findById(loiId)).thenReturn(java.util.Optional.of(cCnfCementLoi));
		// When
		cCnfCementLoi = ccnfTransactionService.getCCnfLoiDetailsById(loiId);
		// Then
		verify(cCnfLoiRepo, times(1)).findById(loiId);

		assertEquals(loiId, cCnfCementLoi.getLoiId());
	//	assertEquals(2, cCnfCementLoi.getAgentId());
		assertEquals(slocs, cCnfCementLoi.getI2Dists());

	}

	@Test
	void testGetCcnfLoiDetails() throws Exception {

		List<CCnfCementLoi> ccnfLoiList = new ArrayList<>();
		CCnfCementLoi ccnfLoi = new CCnfCementLoi();
		ccnfLoi.setLoiId(30000000000L);
		//ccnfLoi.setAgentId(2L);
		List<Long> slocs = new ArrayList<Long>();
		slocs.add(1234L);
		ccnfLoi.setI2Dists(slocs);
		ccnfLoiList.add(ccnfLoi);

		// when(cCnfLoiRepo.findById(loiId)).thenReturn(java.util.Optional.of(cCnfCementLoi));
		when(cCnfLoiRepo.findAll()).thenReturn(ccnfLoiList);

		// When
		List<CCnfCementLoi> lisOfcCnfLoi = ccnfTransactionService.getCCnfLoiDetails();
		log.info("list of ccnf size"+lisOfcCnfLoi.size());
		// Then
		verify(cCnfLoiRepo, times(1)).findAll();
		assertThat(lisOfcCnfLoi).isNotNull();
		assertEquals(ccnfLoiList, lisOfcCnfLoi);

	}

	@Test
	public void updateLoiDetails() {
		long loiId = 30000000000L;

		// given - precondition or setup
		cCnfCementLoi.setLoiId(30000000000L);
		//cCnfCementLoi.setAgentId(2L);
		List<Long> slocs = new ArrayList<Long>();
		slocs.add(1234L);
		cCnfCementLoi.setI2Dists(slocs);
		
		when(cCnfLoiRepo.findById(loiId)).thenReturn(java.util.Optional.of(cCnfCementLoi));
		slocs = new ArrayList<Long>();
		slocs.add(5436L);
		cCnfLoiDto.setI2Dists(slocs);
		when(cCnfLoiRepo.save(cCnfCementLoi)).thenReturn(cCnfCementLoi);
		// when - action or the behaviour that we are going test
		//CCnfLoiDto updateLoi = ccnfTransactionService.updateDetails(cCnfLoiDto);

		// then - verify the output
		//assertThat(updateLoi.getSlocIds()).isEqualTo(slocs);
	}

	@Test
	public void testDeleteLoi() {
		// given - precondition or setup
		long loiId = 3000000L;
		doNothing().when(cCnfLoiRepo).deleteById(loiId);
		// when - action or the behaviour that we are going test
		ccnfTransactionService.deleteCCnfLoiDetailsById(loiId);
		// then - verify the output
		verify(cCnfLoiRepo, times(1)).deleteById(loiId);
	}

}

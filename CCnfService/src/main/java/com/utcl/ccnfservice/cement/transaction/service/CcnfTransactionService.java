package com.utcl.ccnfservice.cement.transaction.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;
import com.utcl.ccnfservice.cement.transaction.entity.DeductiononDirectSales;
import com.utcl.ccnfservice.cement.transaction.entity.MinimalCommision;
import com.utcl.ccnfservice.cement.transaction.entity.SlabwiseCommision;
import com.utcl.ccnfservice.cement.transaction.repo.CCnfLoiRepo;
import com.utcl.ccnfservice.cement.transaction.service.spec.CcnfTransLoiSpec;
import com.utcl.ccnfservice.master.service.AgencyService;
import com.utcl.ccnfservice.master.service.DepotService;
import com.utcl.ccnfservice.master.service.I2districtService;
import com.utcl.ccnfservice.master.service.RegionService;
import com.utcl.dto.ccnf.CCnfLoiDto;
import com.utcl.dto.ccnf.CcnfCementLoiIterator;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;
import com.utcl.dto.ccnf.GUBTCommisionDto;
import com.utcl.dto.ccnf.MinimalCommisionDto;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;
import com.utcl.dto.ccnf.UserInfo;
import com.utcl.dto.ccnf.data.AgencyData;
import com.utcl.dto.ccnf.data.AgentAndAggrData;
import com.utcl.dto.ccnf.data.CcnfCommitionData;
import com.utcl.dto.ccnf.data.CcnfDirectSalesData;
import com.utcl.dto.ccnf.data.CcnfGodownKeeperData;
import com.utcl.dto.ccnf.data.LoiCreationFactor;
import com.utcl.dto.ccnf.data.LoiStatus;
import com.utcl.dto.ccnf.requestResponce.CCnfLoiGridResponse;
import com.utcl.dto.ccnf.requestResponce.CCnfLoiRequest;
import com.utcl.dto.ccnf.requestResponce.CCnfLoiResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CcnfTransactionService implements CcnfTransLoiSpec {

	private CCnfLoiRepo ccnfLoiRepo;
	private CcnfSlabwiseCommisionService ccnfSlabwiseCommisionService;
	private MinimalCommisionService minimalCommisionService;
	private DeductionCommisionService deductionCommisionService;
	private ModelMapper modelMapper = new ModelMapper();
	AgencyData agencyData = new AgencyData();
	AgentAndAggrData agentAndAggrData = new AgentAndAggrData();
	CcnfCommitionData ccnfCommitionData = new CcnfCommitionData();
	CcnfDirectSalesData ccnfDirectSalesData = new CcnfDirectSalesData();
	CcnfGodownKeeperData ccnfGodownKeeperData = new CcnfGodownKeeperData();

	CCnfCementLoi cCnfCementLoi;
	AgencyService agencyService;
	DepotService depotService;
	RegionService regionService;
	I2districtService i2districtService;

	/**
	 * @param ccnfLoiMapperImpl
	 * @param ccnfLoiRepo
	 */
	public CcnfTransactionService(CCnfLoiRepo ccnfLoiRepo, CcnfSlabwiseCommisionService ccnfSlabwiseCommisionService,
			MinimalCommisionService minimalCommisionService, DeductionCommisionService deductionCommisionService,
			AgencyService agencyService, DepotService depotService, RegionService regionService,
			I2districtService i2districtService) {
		super();
		this.ccnfLoiRepo = ccnfLoiRepo;
		this.ccnfSlabwiseCommisionService = ccnfSlabwiseCommisionService;
		this.minimalCommisionService = minimalCommisionService;
		this.deductionCommisionService = deductionCommisionService;
		this.agencyService = agencyService;
		this.depotService = depotService;
		this.regionService = regionService;
		this.i2districtService = i2districtService;
	}

	public CCnfCementLoi toccnfLoi(@Validated CCnfLoiDto ccnfLoiDto) {
		return modelMapper.map(ccnfLoiDto, CCnfCementLoi.class);
	}

	public CCnfLoiDto toccnfLoiDto(CCnfCementLoi cCnfCementLoi) {
		return modelMapper.map(cCnfCementLoi, CCnfLoiDto.class);
	}

	public CCnfLoiDto updateDetails(CCnfLoiRequest ccnfLoiDto) {
		// return toccnfLoiDto(ccnfLoiRepo.save(toccnfLoi(ccnfLoiDto)));
		return null;
	}

	public List<CCnfCementLoi> getCCnfLoiDetails() {
		return ccnfLoiRepo.findAll();
	}

	public CCnfCementLoi getCCnfLoiDetailsById(Long loiId) throws Exception {
		return ccnfLoiRepo.findById(loiId).orElseThrow(() -> new Exception("LOI not found"));
	}

	public void deleteCCnfLoiDetailsById(Long loiId) {
		ccnfLoiRepo.deleteById(loiId);
	}

	// ====================== CCNF addition Logic Starts =========================
	// example for tab wise update
	public CCnfLoiDto updateCcnfLoiAgencyDataDetails(CCnfLoiRequest ccnfLoiReuest) {
		CCnfCementLoi loi = this.processCcnfLoiAgencyData(ccnfLoiReuest.getAgencyData(),
				ccnfLoiRepo.findById(ccnfLoiReuest.getLoiId()).get());
		return toccnfLoiDto(ccnfLoiRepo.save(loi));
	}

	// for transaction management while storing ccnf cement loi we need to save
	// other slabs as well if any wrong happened while saving slabs all other
	// transactions will be rolled back
	@Transactional("transactionTransactionManager")
	public CCnfLoiDto addCcnfLoiDetails(CCnfLoiRequest ccnfLoiReuest) {
		if (ccnfLoiRepo.findById(ccnfLoiReuest.getLoiId()).isPresent()) {
			CCnfCementLoi loi = this.processCcnfLoiAgencyData(ccnfLoiReuest.getAgencyData(),
					ccnfLoiRepo.findById(ccnfLoiReuest.getLoiId()).get());

			loi = this.processCcnfAgentAndAggrData(ccnfLoiReuest.getAgentAndAggrData(), loi);
			loi = this.processCcnfCommitionData(ccnfLoiReuest.getCcnfCommitionData(), loi);
			loi = this.processCcnfDirectSalesData(ccnfLoiReuest.getCcnfDirectSalesData(), loi);
			loi = this.processCcnfGodownKeeperData(ccnfLoiReuest.getCcnfGodownKeeperData(), loi);

			return toccnfLoiDto(ccnfLoiRepo.save(loi));
		} else {

			CCnfCementLoi loi = this.processCcnfLoiAgencyData(ccnfLoiReuest.getAgencyData(), null);

			loi = this.processCcnfAgentAndAggrData(ccnfLoiReuest.getAgentAndAggrData(), loi);
			loi = this.processCcnfCommitionData(ccnfLoiReuest.getCcnfCommitionData(), loi);
			loi = this.processCcnfDirectSalesData(ccnfLoiReuest.getCcnfDirectSalesData(), loi);
			loi = this.processCcnfGodownKeeperData(ccnfLoiReuest.getCcnfGodownKeeperData(), loi);

			return toccnfLoiDto(ccnfLoiRepo.save(loi));
		}
	}

	@Override
	public CCnfCementLoi processCcnfLoiAgencyData(AgencyData agencyData, CCnfCementLoi cCnfCementLoi) {

		return ccnfLoiRepo.save(CCnfCementLoi.builder().loiId(cCnfCementLoi == null ? null : cCnfCementLoi.getLoiId())
				.regionIds(agencyData.getRegionIds()).depotIds(agencyData.getDepotIds())
				.i2Dists(agencyData.getI2Districts()).createdDate(new Date()).updatedDate(new Date()).build());

	}

	@Override
	public CCnfCementLoi processCcnfAgentAndAggrData(AgentAndAggrData agentAndAggrData, CCnfCementLoi cCnfCementLoi) {

		cCnfCementLoi.setAgreementStartDate(agentAndAggrData.getAgreementStartDate());
		cCnfCementLoi.setAgreementEndDate(agentAndAggrData.getAgreementEndDate());
		cCnfCementLoi.setBillingCycleType(agentAndAggrData.getBillingCycleType());
		return cCnfCementLoi;
	}

	@Override
	public CCnfCementLoi processCcnfCommitionData(CcnfCommitionData ccnfCommitionData, CCnfCementLoi cCnfCementLoi) {

		cCnfCementLoi.setMinCommitionRange(ccnfCommitionData.getMinCommitionRange());
		cCnfCementLoi.setMaxCommitionRange(ccnfCommitionData.getMaxCommitionRange());

		boolean isMetroCommition = ccnfCommitionData.getIsMetroCommition();
		cCnfCementLoi.setIsMetroCommition(isMetroCommition);
		cCnfCementLoi.setMetroCommitionVal(isMetroCommition ? ccnfCommitionData.getMetroCommitionValue() : 0);

		List<SlabwiseCommisionDto> slabWiseCommisions = ccnfCommitionData.getSlabWiseCommisions();
		for (Iterator<SlabwiseCommisionDto> iterator = slabWiseCommisions.iterator(); iterator.hasNext();) {
			SlabwiseCommisionDto slabwiseCommisionDto = iterator.next();
			slabwiseCommisionDto.setMinAmount(ccnfCommitionData.getMinCommitionRange());
			slabwiseCommisionDto.setMaxAmount(ccnfCommitionData.getMaxCommitionRange());
			slabwiseCommisionDto.setIsMetroCommition(ccnfCommitionData.getIsMetroCommition());
			slabwiseCommisionDto.setMetroCommissionValue(cCnfCementLoi.getMetroCommitionVal());
			slabwiseCommisionDto.setLoiId(cCnfCementLoi.getLoiId());
			ccnfSlabwiseCommisionService.addSlabwiseCommission(slabwiseCommisionDto);
		}
		// int i = 10 / 0;
		boolean isMinimumCommition = ccnfCommitionData.getIsMinimumCommition();
		cCnfCementLoi.setMinimunSlabCommition(isMinimumCommition ? ccnfCommitionData.getMinimunSlabCommition() : 0);

		List<MinimalCommisionDto> minimalCommisionsSlabs = ccnfCommitionData.getMinimalCommisionsSlabs();
		if (isMinimumCommition) {
			for (Iterator<MinimalCommisionDto> iterator = minimalCommisionsSlabs.iterator(); iterator.hasNext();) {
				MinimalCommisionDto minimalCommisionDto = iterator.next();
				minimalCommisionDto.setDefaultCommision(ccnfCommitionData.getMinimunSlabCommition());
				minimalCommisionDto.setIsMinimumCommission(isMinimumCommition);
				minimalCommisionDto.setIsMetroCommition(ccnfCommitionData.getIsMetroCommition());
				minimalCommisionDto.setMetroCommitionVal(cCnfCementLoi.getMinimunSlabCommition());
				minimalCommisionDto.setLoiId(cCnfCementLoi.getLoiId());
				minimalCommisionService.addMinimalCommisionCommission(minimalCommisionDto);
			}
		}

		boolean isGubtHandledByCCnf = ccnfCommitionData.getIsGubtHandledByCCnf();
		cCnfCementLoi.setIsGubtHandledByCCnf(isGubtHandledByCCnf);
		if (isGubtHandledByCCnf) {
			cCnfCementLoi.setGubtPlantCode(ccnfCommitionData.getGubtPlantCode());
			cCnfCementLoi.setGubtPlantStanderdComm(ccnfCommitionData.getGubtPlantStanderdComm());
			cCnfCementLoi.setGubtPlantCode(ccnfCommitionData.getGubtPlantCode());
			cCnfCementLoi.setGubtPlantRate(ccnfCommitionData.getGubtPlantRate());
			List<GUBTCommisionDto> gubtCommisions = ccnfCommitionData.getGubtCommisions();
			for (Iterator<GUBTCommisionDto> iterator = gubtCommisions.iterator(); iterator.hasNext();) {
				GUBTCommisionDto gubtCommisionDto = (GUBTCommisionDto) iterator.next();
				gubtCommisionDto.setDefaultCommision(ccnfCommitionData.getGubtPlantStanderdComm());
				gubtCommisionDto.setLoiId(cCnfCementLoi.getLoiId());
				// TODO create gubt service and save GUBT slabs in db
			}
		}

		boolean isInterCompanySale = ccnfCommitionData.getIsInterCompanySale();
		if (isInterCompanySale) {
			cCnfCementLoi.setInterCompanySalesEnable(isInterCompanySale);
			cCnfCementLoi.setInterCompanySaleCommision(ccnfCommitionData.getInterCompanySaleCommition());
		}
		boolean isInterunitSale = ccnfCommitionData.getIsInterunitSale();
		if (isInterCompanySale) {
			cCnfCementLoi.setIsInterunitSale(isInterunitSale);
			cCnfCementLoi.setInterunitSaleCommition(ccnfCommitionData.getInterunitSaleCommition());
		}
		boolean isOtherDepoCommission = ccnfCommitionData.getIsOtherDepoCommission();
		if (isOtherDepoCommission) {
			cCnfCementLoi.setIsOtherDepoCommission(isOtherDepoCommission);
			cCnfCementLoi.setSalesToOtherDepoComm(ccnfCommitionData.getSalesToOtherDepoComm());
			cCnfCementLoi.setSalesFromOtherDepoComm(ccnfCommitionData.getSalesFromOtherDepoComm());
		}

		return cCnfCementLoi;
	}

	@Override
	public CCnfCementLoi processCcnfDirectSalesData(CcnfDirectSalesData ccnfDirectSalesData,
			CCnfCementLoi cCnfCementLoi) {
		boolean isDeduOnDirSales = ccnfDirectSalesData.getIsDeduOnDirSales();
		if (isDeduOnDirSales) {
			cCnfCementLoi.setSlabRangeFrom(ccnfDirectSalesData.getSlabRangeFrom());
			cCnfCementLoi.setSlabRangeTo(ccnfDirectSalesData.getSlabRangeTo());
			List<DeductiononDirectSalesDto> directSalesDtos = ccnfDirectSalesData.getDirectSalesDtos();
			for (Iterator<DeductiononDirectSalesDto> iterator = directSalesDtos.iterator(); iterator.hasNext();) {
				DeductiononDirectSalesDto deductiononDirectSalesDto = iterator.next();
				deductiononDirectSalesDto.setLoiId(cCnfCementLoi.getLoiId());
				deductiononDirectSalesDto.setMinAmount(ccnfDirectSalesData.getSlabRangeFrom());
				deductiononDirectSalesDto.setMaxAmount(ccnfDirectSalesData.getSlabRangeTo());
				deductionCommisionService.addDeductionDirectSales(deductiononDirectSalesDto);
			}
		}

		boolean isReducedCommByComp = ccnfDirectSalesData.getIsReducedCommByComp();
		cCnfCementLoi.setReducedCommission(isReducedCommByComp ? ccnfDirectSalesData.getReducedCommission() : 0);
		return cCnfCementLoi;
	}

	@Override
	public CCnfCementLoi processCcnfGodownKeeperData(CcnfGodownKeeperData ccnfGodownKeeperData,
			CCnfCementLoi cCnfCementLoi) {
		// TODO Auto-generated method stub
		return cCnfCementLoi;
	}
	// ======================CCNF addition Logic Ends =========================

	@Override
	public CCnfCementLoi processCcnfCreatedBy(UserInfo info, CCnfCementLoi cCnfCementLoi) {
		cCnfCementLoi.setCreatedBy(info.getName());
		return cCnfCementLoi;
	}

	@Override
	public CCnfCementLoi processCcnfUpdatedBy(UserInfo info, CCnfCementLoi cCnfCementLoi) {
		cCnfCementLoi.setUpdatedBy(info.getName());
		return cCnfCementLoi;
	}

	public List<SlabwiseCommisionDto> getCCnfSlabWiseCommissionByLoiId(long loiId) {
		List<SlabwiseCommision> slabwiseCommisionList = ccnfSlabwiseCommisionService.getSlabwiseCommisionByLoiId(loiId);
		List<SlabwiseCommisionDto> listOfSlabcommission = new ArrayList<>();
		for (SlabwiseCommision slabwiseCommision : slabwiseCommisionList) {
			listOfSlabcommission.add(toSlabWiseDto(slabwiseCommision));
		}
		return listOfSlabcommission;
	}

	private SlabwiseCommisionDto toSlabWiseDto(@Validated SlabwiseCommision slabwiseCommision) {
		return modelMapper.map(slabwiseCommision, SlabwiseCommisionDto.class);
	}

	public List<MinimalCommisionDto> getCCnfMinimalCommisionByLoiId(long loiId) {
		List<MinimalCommision> minimalCommisionList = minimalCommisionService.getSlabwiseCommisionByLoiId(loiId);
		List<MinimalCommisionDto> listOfMinimalCommission = new ArrayList<>();
		for (MinimalCommision minimalCommision : minimalCommisionList) {
			listOfMinimalCommission.add(toMinimalCommissionDto(minimalCommision));
		}
		return listOfMinimalCommission;
	}

	private MinimalCommisionDto toMinimalCommissionDto(MinimalCommision minimalCommision) {
		return modelMapper.map(minimalCommision, MinimalCommisionDto.class);
	}

	public List<DeductiononDirectSalesDto> getCCnfDeductionDirectSalesByLoiId(long loiId) {
		List<DeductiononDirectSales> deductionDirectSalesList = deductionCommisionService
				.getDeductiononDirectSalesByLoiId(loiId);
		List<DeductiononDirectSalesDto> listOfDeductionDirectSaleList = new ArrayList<>();
		for (DeductiononDirectSales deductiononDirectSales : deductionDirectSalesList) {
			listOfDeductionDirectSaleList.add(toDeductionDirectSalesDto(deductiononDirectSales));
		}
		return listOfDeductionDirectSaleList;
	}

	private DeductiononDirectSalesDto toDeductionDirectSalesDto(DeductiononDirectSales deductiononDirectSales) {
		return modelMapper.map(deductiononDirectSales, DeductiononDirectSalesDto.class);
	}

	public List<GUBTCommisionDto> getCCnfGUBTCommisionByLoiId(long loiId) {
		// to do
		return null;
	}

	public CCnfLoiResponse getCcnfLoiListByloiId(Long loiId) throws Exception {
		log.info("print loiid@@@" + loiId);
		CCnfLoiResponse ccnfLoiResponse = new CCnfLoiResponse();
		if (ccnfLoiRepo.findById(loiId).isPresent()) {
			CCnfCementLoi loi = ccnfLoiRepo.findById(loiId).get();

			ccnfLoiResponse.setLoiId(loiId);
			ccnfLoiResponse.setAgentId(loi.getAgentId() == null ? 0 : loi.getAgentId());
			agencyData.setAgentId(loi.getAgentId() == null ? 0 : loi.getAgentId());
			agencyData.setDepotIds(loi.getDepotIds() == null ? null : loi.getDepotIds());
			agencyData.setI2Districts(loi.getI2Dists() == null ? null : loi.getI2Dists());
			agencyData.setRegionIds(loi.getRegionIds() == null ? null : loi.getRegionIds());
			ccnfLoiResponse.setAgencyData(agencyData);
			agentAndAggrData
					.setAgreementEndDate(loi.getAgreementEndDate() == null ? new Date() : loi.getAgreementEndDate());
			agentAndAggrData.setAgreementStartDate(
					loi.getAgreementStartDate() == null ? new Date() : loi.getAgreementStartDate());
			agentAndAggrData.setBillingCycleEndDate(
					loi.getBillingCycleEndDate() == null ? new Date() : loi.getBillingCycleEndDate());
			agentAndAggrData.setBillingCycleStartDate(
					loi.getBillingCycleStartDate() == null ? new Date() : loi.getBillingCycleStartDate());
			agentAndAggrData.setBillingCycleType(loi.getBillingCycleType() == null ? null : loi.getBillingCycleType());
			ccnfLoiResponse.setAgentAndAggrData(agentAndAggrData);

			ccnfCommitionData.setMinCommitionRange(loi.getMinCommitionRange() == null ? 0 : loi.getMinCommitionRange());
			ccnfCommitionData.setMaxCommitionRange(loi.getMaxCommitionRange() == null ? 0 : loi.getMaxCommitionRange());
			ccnfCommitionData
					.setMetroCommitionValue(loi.getMetroCommitionVal() == null ? 0 : loi.getMetroCommitionVal());
			ccnfCommitionData
					.setIsMinimumCommition(loi.getIsMinimumCommition() == null ? false : loi.getIsMinimumCommition());
			ccnfCommitionData
					.setMinimunSlabCommition(loi.getMinimunSlabCommition() == null ? 0 : loi.getMinimunSlabCommition());
			ccnfCommitionData.setSlabWiseCommisions(getCCnfSlabWiseCommissionByLoiId(loiId));
			ccnfCommitionData.setMinimalCommisionsSlabs(getCCnfMinimalCommisionByLoiId(loiId));
			ccnfCommitionData.setIsGubtHandledByCCnf(
					loi.getIsGubtHandledByCCnf());
			ccnfCommitionData.setGubtPlantStanderdComm(
					loi.getGubtPlantStanderdComm() == null ? 0 : loi.getGubtPlantStanderdComm());
			ccnfCommitionData.setGubtPlantCode(loi.getGubtPlantCode());
			ccnfCommitionData.setGubtPlantRate(loi.getGubtPlantRate());
			ccnfCommitionData.setGubtCommisions(null);
			ccnfCommitionData
					.setIsInterCompanySale(loi.getIsInterCompanySale() == null ? false : loi.getIsInterCompanySale());
			ccnfCommitionData.setInterCompanySaleCommition(
					loi.getInterCompanySaleCommition() == null ? 0 : loi.getInterCompanySaleCommition());
			ccnfCommitionData.setIsInterunitSale(loi.getIsInterunitSale() == null ? false : loi.getIsInterunitSale());
			ccnfCommitionData.setInterunitSaleCommition(
					loi.getInterunitSaleCommition() == null ? 0 : loi.getInterunitSaleCommition());
			ccnfCommitionData.setIsOtherDepoCommission(
					loi.getIsOtherDepoCommission() == null ? false : loi.getIsOtherDepoCommission());
			ccnfCommitionData
					.setSalesToOtherDepoComm(loi.getSalesToOtherDepoComm() == null ? 0 : loi.getSalesToOtherDepoComm());
			ccnfCommitionData.setSalesFromOtherDepoComm(
					loi.getSalesFromOtherDepoComm() == null ? 0 : loi.getSalesFromOtherDepoComm());
			ccnfLoiResponse.setCcnfCommitionData(ccnfCommitionData);

			ccnfDirectSalesData
					.setIsDeduOnDirSales(loi.getIsDeduOnDirSales() == null ? false : loi.getIsDeduOnDirSales());
			ccnfDirectSalesData.setSlabRangeFrom(loi.getSlabRangeFrom() == null ? 0 : loi.getSlabRangeFrom());
			ccnfDirectSalesData.setSlabRangeTo(loi.getSlabRangeTo() == null ? 0 : loi.getSlabRangeTo());
			ccnfDirectSalesData.setDirectSalesDtos(getCCnfDeductionDirectSalesByLoiId(loiId));
			ccnfDirectSalesData.setIsReducedCommByComp(
					loi.getIsReducedCommByComp() == null ? false : loi.getIsReducedCommByComp());
			ccnfDirectSalesData
					.setReducedCommission(loi.getReducedCommission() == null ? 0 : loi.getReducedCommission());
			ccnfLoiResponse.setCcnfDirectSalesData(ccnfDirectSalesData);

			ccnfGodownKeeperData
					.setIsFixedRendForGodownOwn(loi.getFixedRentEnable() == null ? false : loi.getFixedRentEnable());
			ccnfLoiResponse.setCcnfGodownKeeperData(ccnfGodownKeeperData);

		}
		return ccnfLoiResponse;
	}

	public List<CCnfLoiGridResponse> getCcnfLoiList() throws Exception {
		List<CCnfLoiGridResponse> cCnfLoiGridResponse = new ArrayList<>();
		List<CCnfCementLoi> loiList = ccnfLoiRepo.findAll();
		for (CCnfCementLoi CCnfLoiDt : loiList) {
			log.info(new SimpleDateFormat("MM/dd/yyyy").format(CCnfLoiDt.getBillingCycleEndDate()));
			SimpleDateFormat simpleDateFmt = new SimpleDateFormat("dd/MM/yyyy");
			String billingFormatedEndDate = simpleDateFmt.format(CCnfLoiDt.getBillingCycleEndDate());
			String billingFormatedStartDate = simpleDateFmt.format(CCnfLoiDt.getBillingCycleStartDate());
			log.info("billingFormatedEndDate{}", billingFormatedEndDate);

			cCnfLoiGridResponse.add(CCnfLoiGridResponse.builder()
					.ccnfType(CCnfLoiDt.getCcnfType() == null ? null : CCnfLoiDt.getCcnfType())
					.billingCycleEndDate(billingFormatedEndDate).billingCycleStartDate(billingFormatedStartDate)
					.agentName(agencyService.getAgentNameByAgentId(CCnfLoiDt.getAgentId()))
					.depotName(depotService.getDepotNameByDepotId(CCnfLoiDt.getDepotIds()))
					.regionName(regionService.getRegionNameByRegionId(CCnfLoiDt.getRegionIds()))
					.distName(i2districtService.getI2districtNameByIds(CCnfLoiDt.getI2Dists()))
					.status(LoiStatus.Approved).id(CCnfLoiDt.getLoiId()).build());

//			CCnfLoiGridResponse cCnfLoiResponse=new CCnfLoiGridResponse();
//			cCnfLoiResponse.setCcnfType(CCnfLoiDt.getCcnfType()==null? null:CCnfLoiDt.getCcnfType());	
//			cCnfLoiResponse.setBillingCycleEndDate(billingFormatedEndDate);
//			cCnfLoiResponse.setBillingCycleStartDate(billingFormatedStartDate);
//			cCnfLoiResponse.setAgentName(agencyService.getAgentNameByAgentId(CCnfLoiDt.getAgentId()));
//			cCnfLoiResponse.setDepotName(depotService.getDepotNameByDepotId(CCnfLoiDt.getDepotIds()));
//			cCnfLoiResponse.setRegionName(regionService.getRegionNameByRegionId(CCnfLoiDt.getRegionIds()));
//			cCnfLoiResponse.setDistName(i2districtService.getI2districtNameByIds(CCnfLoiDt.getI2Dists()));
//			cCnfLoiResponse.setStatus(LoiStatus.Approved);
//			cCnfLoiResponse.setId(CCnfLoiDt.getLoiId());
//			cCnfLoiGridResponse.add(cCnfLoiResponse);
		}
		return cCnfLoiGridResponse;
	}

	@Override
	public CcnfCementLoiIterator makeDecisionForLoi(int numOfAgency, int numOfState, int numOfRate) {
		if (numOfAgency <= 1 && numOfState <= 1 && numOfRate <= 1) {
			return CcnfCementLoiIterator.builder().numOfOperation(1).factor(LoiCreationFactor.NORMAL).build();
		} else if (numOfAgency > 1 && numOfState <= 1 && numOfRate <= 1) {
			return CcnfCementLoiIterator.builder().numOfOperation(numOfAgency).factor(LoiCreationFactor.AGENCY)
					.build();
		} else if (numOfAgency <= 1 && numOfState > 1 && numOfRate <= 1) {
			return CcnfCementLoiIterator.builder().numOfOperation(numOfState).factor(LoiCreationFactor.STATE)
					.build();
		} else if (numOfAgency <= 1 && numOfState <= 1 && numOfRate > 1) {
			return CcnfCementLoiIterator.builder().numOfOperation(numOfRate).factor(LoiCreationFactor.RATE)
					.build();
		} else if (numOfAgency > 1 && numOfState > 1 && numOfRate <= 1) {
			return CcnfCementLoiIterator.builder().numOfOperation(numOfState).factor(LoiCreationFactor.STATE)
					.build();
		} else if (numOfAgency > 1 && numOfState > 1 && numOfRate > 1) {
			// which ever is higher
			return CcnfCementLoiIterator.builder().numOfOperation(numOfRate).factor(LoiCreationFactor.RATE)
					.build();
		} else if (numOfAgency <= 1 && numOfState > 1 && numOfRate > 1) {
			return CcnfCementLoiIterator.builder().numOfOperation(numOfRate).factor(LoiCreationFactor.RATE)
					.build();
		} else if (numOfAgency > 1 && numOfState <= 1 && numOfRate > 1) {
			return CcnfCementLoiIterator.builder().numOfOperation(numOfRate).factor(LoiCreationFactor.RATE)
					.build();
		}
		return CcnfCementLoiIterator.builder().numOfOperation(1).factor(LoiCreationFactor.NORMAL).build();
	}

	@Override
	public CcnfCementLoiIterator makeDecisionForCalculation(int numOfAgency, int numOfState, int numOfRate,
			int numberOfDepos, boolean isAll) {
		CcnfCementLoiIterator ccnfCementLoiIterator = makeDecisionForLoi(numOfAgency, numOfState, numOfRate);
		ccnfCementLoiIterator.setFactor(isAll ? LoiCreationFactor.DEPO : LoiCreationFactor.I2DIST);
		return ccnfCementLoiIterator;
	}

	@Override
	public CcnfCementLoiIterator makeDecisionForInvoice(int numOfAgency, int numOfState, int numOfRate,
			int numberOfDepos, boolean isAll) {
		CcnfCementLoiIterator ccnfCementLoiIterator = makeDecisionForCalculation(numOfAgency, numOfState, numOfRate,
				numberOfDepos, isAll);
		ccnfCementLoiIterator.setNumOfOperation(numOfAgency>numberOfDepos?numOfAgency:numberOfDepos);
		return ccnfCementLoiIterator;
	}

}

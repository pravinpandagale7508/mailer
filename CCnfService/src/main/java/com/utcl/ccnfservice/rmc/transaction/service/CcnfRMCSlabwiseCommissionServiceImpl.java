package com.utcl.ccnfservice.rmc.transaction.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCSlabwiseCommision;
import com.utcl.ccnfservice.rmc.transaction.repo.CcnfRMCSlabwisecommisionRepo;
import com.utcl.ccnfservice.rmc.transaction.service.spec.CcnfRMCSlabwiseCommissionService;
import com.utcl.dto.ccnf.CcnfRMCSlabwiseCommisionDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CcnfRMCSlabwiseCommissionServiceImpl implements CcnfRMCSlabwiseCommissionService {

	@Autowired
	private CcnfRMCSlabwisecommisionRepo rmcSlabwisecommisionRepo;

	private final CcnfRMCSalesDetailsServiceImpl ccnfRMCSalesDetailsServiceImpl;
	private ModelMapper modelMapper = new ModelMapper();

	public CcnfRMCSlabwiseCommissionServiceImpl(CcnfRMCSalesDetailsServiceImpl ccnfRMCSalesDetailsServiceImpl) {
		super();
		this.ccnfRMCSalesDetailsServiceImpl = ccnfRMCSalesDetailsServiceImpl;
	}

	private DecimalFormat df = new DecimalFormat("#.##");

	@Override
	public double performRMCSlabwiseCalculation(List<String> lisOfPlants, List<Double> listOfWastages,
			int month, int year, Long loiId) {	 
		return getRmcSlabwiseCommissions(ccnfRMCSalesDetailsServiceImpl.getRmcTotalSalesQuantity(lisOfPlants, listOfWastages, month, year),loiId);
	}
	
	public double getRmcSlabwiseCommissions(double salesQuantity, Long loiId) {
		log.info("salesQuantity"+salesQuantity+"loiId"+loiId);
		List<CcnfRMCSlabwiseCommision> slabWiseComissionList = rmcSlabwisecommisionRepo.findByLoiId(loiId);
		log.info("rmcSlabwisecommisionRepo: " + slabWiseComissionList + "quatity percent: " + salesQuantity);// {//100*20+100*30+99*40=2000+3000+3960=8960
		double calculateCommission = calculateCommision(salesQuantity, slabWiseComissionList);
		log.info("Calculated amount: " + calculateCommission);
		Stream<Double> numbers = Stream.of(calculateCommission,salesQuantity);
        double averageRate = numbers.reduce((a, b) -> a / b).orElseThrow();// Use reduce to divide the numbers
		log.info("averageRate"+df.format(averageRate));
		double variableCommission=salesQuantity*Double.valueOf(df.format(averageRate));
        return Double.valueOf(df.format(variableCommission));
	}

	private double calculateCommision(double salesQuantity, List<CcnfRMCSlabwiseCommision> slabWiseComissionList) {
		double totalSlabCommision = 0;
		boolean limitReached = false;
		double remSlabAmount = 0;
		// 100*20+100*10+99*10=2000+1000+990

		for (CcnfRMCSlabwiseCommision slabwiseCommision : slabWiseComissionList) {// Loop on list of values fetched from
																					// DB
			// condition to check salesQuantity within first slab for eg. 0-10000
			slabwiseCommision.setSlabAmountTo(
					slabwiseCommision.getSlabAmountTo() == null ? salesQuantity : slabwiseCommision.getSlabAmountTo());

			if (!limitReached && (salesQuantity > slabwiseCommision.getSlabAmountFrom()
					&& salesQuantity > slabwiseCommision.getSlabAmountTo())) {
				remSlabAmount = slabwiseCommision.getSlabAmountTo(); // storing value to calculate remaining amount
				double slabCommision = calculateCommissionForSlab(slabwiseCommision, salesQuantity);
				totalSlabCommision = totalSlabCommision + slabCommision;
			} else if (!limitReached && (salesQuantity >= slabwiseCommision.getSlabAmountFrom()// condition to check ifremaining// sales quantity// foregfor//214=100+100+14
					&& salesQuantity <= slabwiseCommision.getSlabAmountTo())) {
				totalSlabCommision = calculateCommissionForRemSlab(slabwiseCommision, salesQuantity, remSlabAmount,
						totalSlabCommision);
				limitReached = true;
			}
			rmcSlabwisecommisionRepo.updateTotalSalesCommisionAndQuantityById(
					Double.valueOf(df.format(totalSlabCommision)), salesQuantity, slabwiseCommision.getRemSlabAmount(),
					slabwiseCommision.getSlabId(), slabwiseCommision.getLoiId());
			log.info("slab" + slabwiseCommision);
			if (limitReached)
				break;
		}
		return Double.valueOf(df.format(totalSlabCommision));
	}

	private double calculateCommissionForSlab(CcnfRMCSlabwiseCommision slabwiseCommision, double salesQuantity) {
		double remSlabAmount = 0;
		double commision = 0;
		double totalSlabCommision = 0;
		slabwiseCommision.setSlabAmountTo(
				(slabwiseCommision.getSlabAmountTo() == null ? salesQuantity : slabwiseCommision.getSlabAmountTo()));
		if (slabwiseCommision.getSlabAmountFrom() == 0) { // if values in first slab
			remSlabAmount = slabwiseCommision.getSlabAmountTo();
			commision = slabwiseCommision.getCommision();
		} else {
			remSlabAmount = slabwiseCommision.getSlabAmountTo() - (slabwiseCommision.getSlabAmountFrom() - 1);
			commision = remSlabAmount * slabwiseCommision.getCommision();
		}
		log.info(" Quantity for RMC slab commision : " + remSlabAmount + "commision rates : "
				+ slabwiseCommision.getCommision());
		totalSlabCommision = totalSlabCommision + commision;
		slabwiseCommision.setRemSlabAmount(remSlabAmount);
		slabwiseCommision.setTotalCommissionOnSlab(totalSlabCommision);
		return totalSlabCommision;
	}

	// Method to calculate rem slab Amount (when it comes to last slab)
	private double calculateCommissionForRemSlab(CcnfRMCSlabwiseCommision slabwiseCommision, double salesQuantity,
			double remSlabAmtTo, double totalSlabCommision) {
		double remSlabAmount = 0;
		double commision = 0;
		if (slabwiseCommision.getSlabAmountFrom() > 0) {
			remSlabAmount = salesQuantity - remSlabAmtTo;
			commision = remSlabAmount * slabwiseCommision.getCommision();
		} else {
			remSlabAmount = salesQuantity;
			commision = slabwiseCommision.getCommision();
		}
		log.info("else if RMC Quantity for slab commision : " + remSlabAmount + "commision rates : "
				+ slabwiseCommision.getCommision());
		log.info(" else if RMC  commision : " + commision + "slabCommision" + totalSlabCommision);
		totalSlabCommision = totalSlabCommision + commision;
		rmcSlabwisecommisionRepo.updateTotalSalesCommisionAndQuantityById(totalSlabCommision, salesQuantity,
				remSlabAmount, slabwiseCommision.getSlabId(), slabwiseCommision.getLoiId());
		log.info("else if RMC Slab commision : " + totalSlabCommision);
		slabwiseCommision.setRemSlabAmount(remSlabAmount);
		slabwiseCommision.setTotalCommissionOnSlab(totalSlabCommision);
		return totalSlabCommision;
	}

	public CcnfRMCSlabwiseCommisionDto addRMCSlabwiseCommission(
			CcnfRMCSlabwiseCommisionDto ccnfRmcSlabwiseCommisionDto) {
		CcnfRMCSlabwiseCommision ccnfRMCSlabwiseCommision;
		if (rmcSlabwisecommisionRepo.findById(ccnfRmcSlabwiseCommisionDto.getSlabId()).isPresent()) {
			updateDetails(ccnfRmcSlabwiseCommisionDto);
		} else {
			ccnfRMCSlabwiseCommision = rmcSlabwisecommisionRepo
					.save(toccnfRmcSablwiseCommision(ccnfRmcSlabwiseCommisionDto));
			ccnfRmcSlabwiseCommisionDto = toccnfRmcSablwiseCommisionDto(ccnfRMCSlabwiseCommision);
		}
		return ccnfRmcSlabwiseCommisionDto;
	}

	public CcnfRMCSlabwiseCommisionDto updateDetails(CcnfRMCSlabwiseCommisionDto ccnfRmcSlabwiseCommisionDto) {
		CcnfRMCSlabwiseCommision rmcSlabwiseCommision = rmcSlabwisecommisionRepo
				.save(toccnfRmcSablwiseCommision(ccnfRmcSlabwiseCommisionDto));
		return toccnfRmcSablwiseCommisionDto(rmcSlabwiseCommision);
	}

	public CcnfRMCSlabwiseCommision toccnfRmcSablwiseCommision(
			@Validated CcnfRMCSlabwiseCommisionDto rmcSlabwiseCommisionDto) {
		return modelMapper.map(rmcSlabwiseCommisionDto, CcnfRMCSlabwiseCommision.class);
	}

	public CcnfRMCSlabwiseCommisionDto toccnfRmcSablwiseCommisionDto(CcnfRMCSlabwiseCommision rmcSlabwiseCommision) {
		return modelMapper.map(rmcSlabwiseCommision, CcnfRMCSlabwiseCommisionDto.class);
	}

	@Override
	public List<CcnfRMCSlabwiseCommision> getSlabwiseRMCCommissions() {
		return  rmcSlabwisecommisionRepo.findAll();
	}

	@Override
	public CcnfRMCSlabwiseCommision getSlabwiseRMCCommissionsById(Long slabId) throws Exception {
		return rmcSlabwisecommisionRepo.findById(slabId).orElseThrow(() -> new Exception("Slab not found"));
	}


}

package com.utcl.ccnfservice.cement.transaction.service;

import java.text.DecimalFormat;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.cement.transaction.entity.SlabwiseCommision;
import com.utcl.ccnfservice.cement.transaction.repo.SlabwisecommisionRepo;
import com.utcl.dto.ccnf.SlabwiseCommisionDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CcnfSlabwiseCommisionService {

	/**
	 * @param ccnfLoiMapperImpl
	 * @param ccnfLoiRepo
	 */
	public CcnfSlabwiseCommisionService(SlabwisecommisionRepo slabwisecommisionRepo) {
		super();
		this.slabwisecommisionRepo = slabwisecommisionRepo;
	}

	private SlabwisecommisionRepo slabwisecommisionRepo;
	private ModelMapper modelMapper = new ModelMapper();
	DecimalFormat df = new DecimalFormat("#.##");

	public SlabwiseCommisionDto addSlabwiseCommission(SlabwiseCommisionDto slabwiseCommisionDto) {
		SlabwiseCommision slabwiseCommision;
		if (slabwisecommisionRepo.findById(slabwiseCommisionDto.getSlabId()).isPresent()) {
			updateDetails(slabwiseCommisionDto);
		} else {
			slabwiseCommision = slabwisecommisionRepo.save(toccnfSablwiseCommision(slabwiseCommisionDto));
			slabwiseCommisionDto = toccnfSablwiseCommisionDto(slabwiseCommision);
		}
		return slabwiseCommisionDto;
	}

	public SlabwiseCommisionDto updateDetails(SlabwiseCommisionDto slabwiseCommisionDto) {
		return toccnfSablwiseCommisionDto(slabwisecommisionRepo.save(toccnfSablwiseCommision(slabwiseCommisionDto)));
	}

	public List<SlabwiseCommision> getSlabwiseCommissions() {
		return slabwisecommisionRepo.findAll();
	}

	public SlabwiseCommision getSlabwiseCommissionById(Long slabId) throws Exception {
		return slabwisecommisionRepo.findById(slabId).orElseThrow(() -> new Exception("Slab not found"));
	}

	public void deleteSlabwiseCommissionById(Long slabId) {
		slabwisecommisionRepo.deleteById(slabId);
	}

	public SlabwiseCommision toccnfSablwiseCommision(@Validated SlabwiseCommisionDto slabwiseCommisionDto) {
		return modelMapper.map(slabwiseCommisionDto, SlabwiseCommision.class);
	}

	public SlabwiseCommisionDto toccnfSablwiseCommisionDto(SlabwiseCommision slabwiseCommision) {
		return modelMapper.map(slabwiseCommision, SlabwiseCommisionDto.class);
	}

	public List<SlabwiseCommision> getSlabwiseCommisionDetailsByLoiId(List<Long> loiId) {
		log.info("Print list loiId" + loiId);
		return slabwisecommisionRepo.getSlabwiseCommisionDetailsByLoiId(loiId);
	}

	public List<SlabwiseCommision> getSlabwiseCommisionByLoiId(Long loiId) {
		log.info("Print list loiId" + loiId);
		return slabwisecommisionRepo.getSlabwiseCommisionByLoiId(loiId);
	}

	public double getCcnfSlabwiseCommissionsByQuantity(double totalSalesquantity, Long loiId) throws Exception {
		List<SlabwiseCommision> slabWiseComissionList = slabwisecommisionRepo.findByLoiId(loiId);
		log.info("slabwisecommisionRepo: " + slabWiseComissionList + "quatity percent: " + totalSalesquantity);
		// {//100*20+100*30+99*40=2000+3000+3960=8960
		double calculateCommission = calculateCommision(totalSalesquantity, slabWiseComissionList); // Metro//
		log.info("Calculated amount: " + calculateCommission);
		return calculateCommission;
	}

	private double calculateCommision(double totalSalesquantity, List<SlabwiseCommision> slabWiseComissionList) {
		double totalSlabCommision = 0;
		boolean limitReached = false;
		double remSlabAmount = 0;
		// 100*20+100*10+99*10=2000+1000+990
		
		for (SlabwiseCommision slabwiseCommision : slabWiseComissionList) {// Loop on list of values fetched from DB
			// condition to check totalSalesquantity within first slab for eg. 0-10000
			slabwiseCommision.setSlabAmountTo(
					slabwiseCommision.getSlabAmountTo() == null ? totalSalesquantity : slabwiseCommision.getSlabAmountTo());
			if (!limitReached && (totalSalesquantity > slabwiseCommision.getSlabAmountFrom()
					&& totalSalesquantity > slabwiseCommision.getSlabAmountTo())) {
				remSlabAmount = slabwiseCommision.getSlabAmountTo(); // storing value to calculate remaining amount
				double slabCommision = calculateCommissionForSlab(slabwiseCommision,
						totalSalesquantity);
				totalSlabCommision = totalSlabCommision + slabCommision;
			} 
			else if (!limitReached && (totalSalesquantity >= slabwiseCommision.getSlabAmountFrom()// condition to check if remaining// sales quantity// for eg for 214=100+100+14
					&& totalSalesquantity <= slabwiseCommision.getSlabAmountTo())) {
				totalSlabCommision = calculateCommissionForRemSlab(slabwiseCommision,totalSalesquantity, remSlabAmount, totalSlabCommision);
				limitReached = true;
			}			
			slabwisecommisionRepo.updateTotalSalesCommisionAndQuantityById(Double.valueOf(df.format(totalSlabCommision)),totalSalesquantity,slabwiseCommision.getRemSlabAmount(),slabwiseCommision.getSlabId(),slabwiseCommision.getLoiId());
			log.info("slab"+slabwiseCommision);
			if (limitReached)
				break;
		}
		return totalSlabCommision;
	}

	private double calculateCommissionForSlab(SlabwiseCommision slabwiseCommision, double totalSalesquantity) {
		double remSlabAmount = 0;
		double commision = 0;
		double totalSlabCommision = 0;
		slabwiseCommision.setSlabAmountTo((slabwiseCommision.getSlabAmountTo() == null ? totalSalesquantity
				: slabwiseCommision.getSlabAmountTo()));// need to change value// slabwiseCommision.getSlabAmountTo() as blank
		if (slabwiseCommision.getSlabAmountFrom() == 0) { // if values in first slab
			remSlabAmount = slabwiseCommision.getSlabAmountTo();
		} else {
			remSlabAmount = slabwiseCommision.getSlabAmountTo() - (slabwiseCommision.getSlabAmountFrom() - 1);
		}
		log.info("Metro city Quantity for slab commision : " + remSlabAmount + "commision rates : "
				+ slabwiseCommision.getCommision() + "metroCityFlg" + slabwiseCommision.getIsMetroCommition());
		if (Boolean.TRUE.equals(slabwiseCommision.getIsMetroCommition())) {
			commision = remSlabAmount * (slabwiseCommision.getCommision() + slabwiseCommision.getMetroCommissionValue()); // calculate commission// +metricity// rate
		} else {
			commision = remSlabAmount * slabwiseCommision.getCommision();
		}
		log.info("Metro city commision : " + commision);
		totalSlabCommision = totalSlabCommision + commision;
		log.info("Metro city Slab commision : " + totalSlabCommision);
		slabwiseCommision.setRemSlabAmount(remSlabAmount);
		slabwiseCommision.setTotalCommissionOnSlab(totalSlabCommision);
		return totalSlabCommision;
	}

	// Method to calculate rem slab Amount (when it comes to last slab)
	private double calculateCommissionForRemSlab(SlabwiseCommision slabwiseCommision,
			 double totalSalesquantity, double remSlabAmtTo, double totalSlabCommision) {
		double remSlabAmount = 0;
		double commision = 0;
		if (slabwiseCommision.getSlabAmountFrom() > 0) {
			remSlabAmount = totalSalesquantity - remSlabAmtTo;
		} else {
			remSlabAmount = totalSalesquantity;
		}
		log.info("else if Metro city Quantity for slab commision : " + remSlabAmount + "commision rates : "
				+ slabwiseCommision.getCommision());
		if (Boolean.TRUE.equals(slabwiseCommision.getIsMetroCommition())) {
			commision = remSlabAmount * (slabwiseCommision.getCommision() + slabwiseCommision.getMetroCommissionValue());
		} else {
			commision = remSlabAmount * slabwiseCommision.getCommision();
		}
		log.info(" else if Metro city commision : " + commision + "slabCommision" + totalSlabCommision);
		totalSlabCommision = totalSlabCommision + commision;
		slabwisecommisionRepo.updateTotalSalesCommisionAndQuantityById(totalSlabCommision,totalSalesquantity,remSlabAmount,slabwiseCommision.getSlabId(),slabwiseCommision.getLoiId());
		log.info("else if Metro city Slab commision : " + totalSlabCommision);
		slabwiseCommision.setRemSlabAmount(remSlabAmount);
		slabwiseCommision.setTotalCommissionOnSlab(totalSlabCommision);
		return totalSlabCommision;
	}
}
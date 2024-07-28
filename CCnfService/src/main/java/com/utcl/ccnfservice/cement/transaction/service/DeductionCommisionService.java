package com.utcl.ccnfservice.cement.transaction.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.cement.transaction.entity.DeductiononDirectSales;
import com.utcl.ccnfservice.cement.transaction.repo.DeductiononDirectSalesRepo;
import com.utcl.dto.ccnf.DeductiononDirectSalesDto;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class DeductionCommisionService {

	/**
	 * @param ccnfLoiMapperImpl
	 * @param ccnfLoiRepo
	 */
	public DeductionCommisionService(DeductiononDirectSalesRepo deductiononDirectSalesRepo) {
		super();
		this.deductiononDirectSalesRepo = deductiononDirectSalesRepo;

	}

	private ModelMapper modelMapper = new ModelMapper();

	private DeductiononDirectSalesRepo deductiononDirectSalesRepo;

	public DeductiononDirectSalesDto addDeductionDirectSales(DeductiononDirectSalesDto directSalesDto) {
		log.info("Dto" + directSalesDto);
		DeductiononDirectSales deductiononDirectSales;
		if (deductiononDirectSalesRepo.findById(directSalesDto.getDeductionId()).isPresent()) {
			updateDetails(directSalesDto);
		} else {
			deductiononDirectSales = deductiononDirectSalesRepo.save(toDeductiononDirectSales(directSalesDto));
			directSalesDto = toDeductiononDirectSalesDto(deductiononDirectSales);
		}
		return directSalesDto;
	}

	public DeductiononDirectSalesDto updateDetails(DeductiononDirectSalesDto directSalesDto) {
		DeductiononDirectSales deductiononDirectSales = deductiononDirectSalesRepo
				.save(toDeductiononDirectSales(directSalesDto));
		return toDeductiononDirectSalesDto(deductiononDirectSales);
	}

	public List<DeductiononDirectSales> getDirectDeductions() {
		List<DeductiononDirectSales> deductiononDirectSales = deductiononDirectSalesRepo.findAll();
		log.info("ccnfLoi" + deductiononDirectSales);
		return deductiononDirectSales;
	}

	public DeductiononDirectSales getDirectDeductionsById(Long deductionId) throws Exception {
		return deductiononDirectSalesRepo.findById(deductionId).orElseThrow(() -> new Exception("LOI not found"));

	}

	public void deleteDirectDeductionsById(Long deductionId) {
		deductiononDirectSalesRepo.deleteById(deductionId);
	}

	public DeductiononDirectSales toDeductiononDirectSales(
			@Validated DeductiononDirectSalesDto deductiononDirectSalesDto) {
		return modelMapper.map(deductiononDirectSalesDto, DeductiononDirectSales.class);
	}

	public DeductiononDirectSalesDto toDeductiononDirectSalesDto(
			@Validated DeductiononDirectSales deductiononDirectSales) {
		return modelMapper.map(deductiononDirectSales, DeductiononDirectSalesDto.class);
	}
	
	 public List<DeductiononDirectSales> getDeductiononDirectSalesDetailsByLoiId(List<Long> loiId) {
			log.info("Print list loiId"+loiId);
			return deductiononDirectSalesRepo.getDeductiononDirectSalesDetailsByLoiId(loiId);	    			
		}

	public double getDirectDeductionsByQtyByDirectSales(double quantity, Long loiId, double directSalesQty) {
		List<DeductiononDirectSales> deductionDirectSalesList = deductiononDirectSalesRepo.findByLoiId(loiId);
		log.info("deductiononDirectSalesRepo: " + deductionDirectSalesList);
		log.info("quatity percent: " + quantity);
		// {//100*20+100*30+99*40=2000+3000+3960=8960
		double calculateCommision = calculateDircetDeduction(quantity, deductionDirectSalesList, directSalesQty);
		log.info("Calculated amount: " + calculateCommision);
		return calculateCommision;
	}

	private double calculateDircetDeduction(double quantityPercentage, List<DeductiononDirectSales> deductionDirectSalesList,
			double directSalesQty) {
		double totalDirectCommision = 0;
		boolean limitReached = false;
		for (DeductiononDirectSales deductionDirectSales : deductionDirectSalesList) {
			if (deductionDirectSales.getSlabAmountTo() == 0)
				deductionDirectSales.setSlabAmountTo(quantityPercentage);
			//Condition to check which slab it is coming
			if (!limitReached && (quantityPercentage >= deductionDirectSales.getSlabAmountFrom()&& quantityPercentage <= deductionDirectSales.getSlabAmountTo())) {
				totalDirectCommision = directSalesQty * deductionDirectSales.getCommision();
				totalDirectCommision = Math.round(totalDirectCommision);
				log.info("Slab commision : " + totalDirectCommision + "commissionRates" + deductionDirectSales.getCommision()
						+ "maxQuantities" + deductionDirectSales.getMaxAmount());
				deductiononDirectSalesRepo.updateTotalDirectCommissionById(totalDirectCommision,quantityPercentage,directSalesQty,deductionDirectSales.getDeductionId(),deductionDirectSales.getLoiId());
				limitReached = true;
			}
			if (limitReached)
				break;
		}
		return totalDirectCommision;
	}

	public List<DeductiononDirectSales> getDeductiononDirectSalesByLoiId(long loiId) {	
		return deductiononDirectSalesRepo.findByLoiId(loiId);
	}
}

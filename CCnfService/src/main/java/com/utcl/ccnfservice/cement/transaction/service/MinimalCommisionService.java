package com.utcl.ccnfservice.cement.transaction.service;

import java.text.DecimalFormat;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.cement.transaction.entity.MinimalCommision;
import com.utcl.ccnfservice.cement.transaction.repo.MinimalCommisionRepo;
import com.utcl.dto.ccnf.MinimalCommisionDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MinimalCommisionService {

	/**
	 * @param ccnfLoiMapperImpl
	 * @param ccnfLoiRepo
	 */
	public MinimalCommisionService(MinimalCommisionRepo minimalCommisionRepo) {
		super();
		this.minimalCommisionRepo = minimalCommisionRepo;

	}

	private ModelMapper modelMapper = new ModelMapper();	
	DecimalFormat df = new DecimalFormat("#.##");
	private MinimalCommisionRepo minimalCommisionRepo;

	public MinimalCommisionDto addMinimalCommisionCommission(MinimalCommisionDto minimalCommisionDto) {
		System.out.println("minimalCommisionDto" + minimalCommisionDto);
		MinimalCommision minimalCommision;
		if (minimalCommisionRepo.findById(minimalCommisionDto.getMinimalId()).isPresent()) {
			updateDetails(minimalCommisionDto);
		} else {
			minimalCommision = minimalCommisionRepo.save(toccnfMinimalCommision(minimalCommisionDto));
			minimalCommisionDto = toccnfMinimalCommisionDto(minimalCommision);
		}
		return minimalCommisionDto;
	}

	public MinimalCommisionDto updateDetails(MinimalCommisionDto minimalCommisionDto) {
		MinimalCommision minimalCommision = minimalCommisionRepo.save(toccnfMinimalCommision(minimalCommisionDto));
		return minimalCommisionDto = toccnfMinimalCommisionDto(minimalCommision);
	}

	public List<MinimalCommision> getMinimalCommisionCommission() {
		List<MinimalCommision> minimalCommision = minimalCommisionRepo.findAll();
		System.out.println("ccnfLoi" + minimalCommision);
		return minimalCommision;
	}

	public MinimalCommision getMinimalCommisionCommissionById(Long minimalId) throws Exception {
		MinimalCommision minimalCommision = minimalCommisionRepo.findById(minimalId)
				.orElseThrow(() -> new Exception("LOI not found"));
		return minimalCommision;
	}

	public void deleteMinimalCommisionCommissionById(Long minimalId) {
		minimalCommisionRepo.deleteById(minimalId);
	}

	public MinimalCommision toccnfMinimalCommision(@Validated MinimalCommisionDto minimalCommisionDto) {
		return modelMapper.map(minimalCommisionDto, MinimalCommision.class);
	}

	public MinimalCommisionDto toccnfMinimalCommisionDto(MinimalCommision minimalCommision) {
		return modelMapper.map(minimalCommision, MinimalCommisionDto.class);
	}

	public List<MinimalCommision> getMinimalCommisionDetailsByLoiId(List<Long> loiId) {
		log.info("Print list loiId" + loiId);
		return minimalCommisionRepo.getMinimalCommisionDetailsByLoiId(loiId);
	}

	public double getMinimalCommissionsByQuantity(double saleQuantityinMT, long loiId) {
		List<MinimalCommision> minimalCommissionList = minimalCommisionRepo.findByLoiId(loiId);
		minimalCommissionList.forEach(System.out::println);
		log.info("quantity percent: " + saleQuantityinMT);
		// {//100*20+100*30+99*40=2000+3000+3960=8960
		double calculateMinimalCommision = calculateMinimalCommision(saleQuantityinMT,
				minimalCommissionList);
		log.info("Calculated amount: " + calculateMinimalCommision);
		return calculateMinimalCommision;
	}

	private double calculateMinimalCommision(double saleQuantityinMT,List<MinimalCommision> minimalCommissionList) {
		double totalMinimalCommission=0;
		boolean limitReached = false;
		for (MinimalCommision minimalCommision : minimalCommissionList) {
			minimalCommision.setSlabAmountTo(
					minimalCommision.getSlabAmountTo() == null ? 0 : minimalCommision.getSlabAmountTo());
			if (saleQuantityinMT > minimalCommision.getMaxQuantity())
				break;
			if ((!limitReached && (saleQuantityinMT <= minimalCommision.getSlabAmountFrom()))
					|| (!limitReached && (saleQuantityinMT >= minimalCommision.getSlabAmountFrom()
							&& saleQuantityinMT <= minimalCommision.getSlabAmountTo()))) {// Condition to check which //
																							// slab it is coming
			totalMinimalCommission=	checkSlabData(minimalCommision);
				limitReached = true;
			}
			log.info("Slab commision : " + totalMinimalCommission + "commissionRates" + minimalCommision.getCommissionRsOnMT()
					+ "maxQuantities" + minimalCommision.getSlabAmountTo());
			minimalCommisionRepo.updateTotalMinimalCommisionAndSlaesById(totalMinimalCommission,
					saleQuantityinMT, minimalCommision.getMinimalId(),
					minimalCommision.getLoiId());
			if (limitReached)
				break;
		}
		return Double.valueOf(df.format(totalMinimalCommission));
	}

	private double checkSlabData(MinimalCommision minimalCommision) {
		if (Boolean.TRUE.equals(minimalCommision.getIsMetroCommition())) {
			return  Double.valueOf(df.format(minimalCommision.getCommissiononMT()
					* (minimalCommision.getCommissionRsOnMT() + minimalCommision.getMetroCommitionVal())));
		} else {
			return Double.valueOf(df.format(minimalCommision.getCommissiononMT() * minimalCommision.getCommissionRsOnMT()));
		}
	}

	public List<MinimalCommision> getSlabwiseCommisionByLoiId(long loiId) {
		return minimalCommisionRepo.findByLoiId(loiId);
	}

}

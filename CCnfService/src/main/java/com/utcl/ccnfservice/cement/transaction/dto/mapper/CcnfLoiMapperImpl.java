package com.utcl.ccnfservice.cement.transaction.dto.mapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.cement.transaction.dto.CCnfLoiDto;
import com.utcl.ccnfservice.cement.transaction.dto.DeductiononDirectSalesDto;
import com.utcl.ccnfservice.cement.transaction.dto.MinimalCommisionDto;
import com.utcl.ccnfservice.cement.transaction.dto.SlabwiseCommisionDto;
import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;
import com.utcl.ccnfservice.cement.transaction.entity.DeductiononDirectSales;
import com.utcl.ccnfservice.cement.transaction.entity.MinimalCommision;
import com.utcl.ccnfservice.cement.transaction.entity.SlabwiseCommision;


@Component
public class CcnfLoiMapperImpl implements CcnfLoiMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CcnfLoiMapperImpl.class);
    //@Autowired
    private ModelMapper modelMapper;


    public CcnfLoiMapperImpl() {
        super();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public CCnfCementLoi toccnfLoi(@Validated CCnfLoiDto ccnfLoiDto) {
        LOGGER.info("Mapper call: CCnfLoiDto > {} ", ccnfLoiDto);
        return modelMapper.map(ccnfLoiDto, CCnfCementLoi.class);
    }

    @Override
    public CCnfLoiDto toccnfLoiDto(CCnfCementLoi cCnfCementLoi) {
        LOGGER.info("Mapper call: cCnfCementLoi > {} ", cCnfCementLoi);
        return modelMapper.map(cCnfCementLoi, CCnfLoiDto.class);
    }

    @Override
    public SlabwiseCommision toccnfSablwiseCommision(@Validated SlabwiseCommisionDto slabwiseCommisionDto) {
        LOGGER.info("Mapper call: slabwiseCommisionDto > {} ", slabwiseCommisionDto);
        return modelMapper.map(slabwiseCommisionDto, SlabwiseCommision.class);
    }

    @Override
    public SlabwiseCommisionDto toccnfSablwiseCommisionDto(SlabwiseCommision slabwiseCommision) {
        LOGGER.info("Mapper call: cCnfCementLoi > {} ", slabwiseCommision);
        return modelMapper.map(slabwiseCommision, SlabwiseCommisionDto.class);
    }

    @Override
    public DeductiononDirectSales toDeductiononDirectSales(@Validated DeductiononDirectSalesDto deductiononDirectSalesDto) {
        LOGGER.info("Mapper call: deductiononDirectSalesDto > {} ", deductiononDirectSalesDto);
        return modelMapper.map(deductiononDirectSalesDto, DeductiononDirectSales.class);
    }

    @Override
    public DeductiononDirectSalesDto toDeductiononDirectSalesDto(@Validated DeductiononDirectSales deductiononDirectSales) {
        LOGGER.info("Mapper call: deductiononDirectSales > {} ", deductiononDirectSales);
        return modelMapper.map(deductiononDirectSales, DeductiononDirectSalesDto.class);
    }

    @Override
    public MinimalCommision toccnfMinimalCommision(@Validated MinimalCommisionDto minimalCommisionDto) {
        LOGGER.info("Mapper call: minimalCommisionDto > {} ", minimalCommisionDto);
        return modelMapper.map(minimalCommisionDto, MinimalCommision.class);
    }

    @Override
    public MinimalCommisionDto toccnfMinimalCommisionDto(MinimalCommision minimalCommision) {
        LOGGER.info("Mapper call: minimalCommision > {} ", minimalCommision);
        return modelMapper.map(minimalCommision, MinimalCommisionDto.class);
    }

}

package com.utcl.ccnfservice.cement.transaction.dto.mapper;

import com.utcl.ccnfservice.cement.transaction.dto.CCnfLoiDto;
import com.utcl.ccnfservice.cement.transaction.dto.DeductiononDirectSalesDto;
import com.utcl.ccnfservice.cement.transaction.dto.MinimalCommisionDto;
import com.utcl.ccnfservice.cement.transaction.dto.SlabwiseCommisionDto;
import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;
import com.utcl.ccnfservice.cement.transaction.entity.DeductiononDirectSales;
import com.utcl.ccnfservice.cement.transaction.entity.MinimalCommision;
import com.utcl.ccnfservice.cement.transaction.entity.SlabwiseCommision;


public interface CcnfLoiMapper {
	public CCnfCementLoi toccnfLoi(CCnfLoiDto cCnfLoiDto);

	public CCnfLoiDto toccnfLoiDto(CCnfCementLoi cCnfCementLoi);

	SlabwiseCommision toccnfSablwiseCommision(SlabwiseCommisionDto slabwiseCommisionDto);

	SlabwiseCommisionDto toccnfSablwiseCommisionDto(SlabwiseCommision slabwiseCommision);

	MinimalCommision toccnfMinimalCommision(MinimalCommisionDto minimalCommisionDto);

	MinimalCommisionDto toccnfMinimalCommisionDto(MinimalCommision minimalCommision);

	DeductiononDirectSalesDto toDeductiononDirectSalesDto(DeductiononDirectSales deductiononDirectSales);

	DeductiononDirectSales toDeductiononDirectSales(DeductiononDirectSalesDto deductiononDirectSalesDto);
}

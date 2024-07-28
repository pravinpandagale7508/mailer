package com.utcl.ccnfservice.master.mapper;

import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.ccnfservice.master.entity.GubtPlantCode;
import com.utcl.ccnfservice.master.entity.I2district;
import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.ccnfservice.master.entity.Sloc;
import com.utcl.dto.ccnf.AgencyDto;
import com.utcl.dto.ccnf.DepotDto;
import com.utcl.dto.ccnf.GubtPlantCodeDto;
import com.utcl.dto.ccnf.I2districtDto;
import com.utcl.dto.ccnf.RegionDto;
import com.utcl.dto.ccnf.SlocDto;

public interface MasterMapper {  
	 Agency toAgency(AgencyDto agencyDto);
     AgencyDto toAgencyDto(Agency vendor);
    
     Depot toDepot(DepotDto depotDto);
     DepotDto toDepotDto(Depot depot);
    
    
     GubtPlantCode toGubPlantCode(GubtPlantCodeDto gubPlantCodeDto);
     GubtPlantCodeDto toGubPlantCodeDto(GubtPlantCode gubPlantCode);
    
    
     I2district toI2district(I2districtDto i2districtDtoDto);
     I2districtDto toI2districtDto(I2district i2district);
    
     Region toRegion(RegionDto regionDto);
     RegionDto toRegionDto(Region region);
    
     Sloc toSloc(SlocDto slocDto);
     SlocDto toSlocDto(Sloc sloc);
}

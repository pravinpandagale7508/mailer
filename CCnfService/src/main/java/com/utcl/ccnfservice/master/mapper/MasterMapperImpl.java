package com.utcl.ccnfservice.master.mapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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


@Component
public class MasterMapperImpl implements MasterMapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterMapperImpl.class);
	//@Autowired
	private ModelMapper modelMapper;
	
	
	public MasterMapperImpl() {
		super();
		this.modelMapper = new ModelMapper();
	}

	@Override
	public Agency toAgency(AgencyDto agencyDto) {
		LOGGER.info("Mapper call {} ", agencyDto);
		return modelMapper.map(agencyDto, Agency.class);
	}

	@Override
	public AgencyDto toAgencyDto(Agency ss) {
		String agencyStr = ss.toString();
		LOGGER.info("Mapper call:  > {} ", agencyStr);
		AgencyDto agencyDTO = modelMapper.map(ss, AgencyDto.class);
		return agencyDTO;
	}


	@Override
	public Depot toDepot(DepotDto depotDto) {
		LOGGER.info("Mapper call {} ", depotDto);
		return modelMapper.map(depotDto, Depot.class);
	}

	@Override
	public DepotDto toDepotDto(Depot depot) {
		String depotStr = depot.toString();
		LOGGER.info("Mapper call:  > {} ", depotStr);
		DepotDto depotDto = modelMapper.map(depot, DepotDto.class);
		return depotDto;
	}

	@Override
	public GubtPlantCode toGubPlantCode(GubtPlantCodeDto gubPlantCodeDto) {
		LOGGER.info("Mapper call {} ", gubPlantCodeDto);
		return modelMapper.map(gubPlantCodeDto, GubtPlantCode.class);
	}

	@Override
	public GubtPlantCodeDto toGubPlantCodeDto(GubtPlantCode gubPlantCode) {
		String gubPlantCodeStr = gubPlantCode.toString();
		LOGGER.info("Mapper call:  > {} ", gubPlantCodeStr);
		GubtPlantCodeDto gubPlantCodeDto = modelMapper.map(gubPlantCode, GubtPlantCodeDto.class);
		return gubPlantCodeDto;
	}

	@Override
	public I2district toI2district(I2districtDto i2districtDto) {
		LOGGER.info("Mapper call {} ", i2districtDto);
		return modelMapper.map(i2districtDto, I2district.class);
	}

	@Override
	public I2districtDto toI2districtDto(I2district i2district) {
		String i2districtStr = i2district.toString();
		LOGGER.info("Mapper call:  > {} ", i2districtStr);
		I2districtDto i2districtDto = modelMapper.map(i2district, I2districtDto.class);
		return i2districtDto;
	}

	@Override
	public Region toRegion(RegionDto regionDto) {
		LOGGER.info("Mapper call {} ", regionDto);
		return modelMapper.map(regionDto, Region.class);
	}

	@Override
	public RegionDto toRegionDto(Region region) {
		String regionStr = region.toString();
		LOGGER.info("Mapper call:  > {} ", regionStr);
		RegionDto regionDto = modelMapper.map(region, RegionDto.class);
		return regionDto;
	}

	@Override
	public Sloc toSloc(SlocDto slocDto) {
	LOGGER.info("Mapper call {} ", slocDto);
	return modelMapper.map(slocDto, Sloc.class);
	}

	@Override
	public SlocDto toSlocDto(Sloc sloc) {
		String slocStr = sloc.toString();
		LOGGER.info("Mapper call:  > {} ", slocStr);
		SlocDto slocDto = modelMapper.map(sloc, SlocDto.class);
		return slocDto;
	}
	
}

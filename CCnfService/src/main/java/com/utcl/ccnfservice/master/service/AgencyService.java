package com.utcl.ccnfservice.master.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.ccnfservice.master.repo.AgencyRepo;
import com.utcl.dto.ccnf.AgencyDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgencyService {
	private AgencyRepo agencyRepo;
	private ModelMapper modelMapper=new ModelMapper();
	
	public AgencyService( AgencyRepo agencyRepo) {
		super();
		this.agencyRepo = agencyRepo;
	}

	public AgencyDto addAgencyDetails(AgencyDto agencyDto) {
		log.info("Agent ServiceDto {}" ,agencyDto);
		Agency agency=null;
		if (agencyRepo.findById(agencyDto.getAgentId()).isPresent()) {	
			updateDetails(agencyDto);
		} else {
			agency = agencyRepo.save(toAgency(agencyDto));
			agencyDto=toAgencyDto(agency);
			log.info("agencyDto ServiceDto {}" ,agencyDto);

		}
		return agencyDto;
	}
	
	private AgencyDto updateDetails(AgencyDto agencyDto) {
		return  toAgencyDto(agencyRepo.save(toAgency(agencyDto)));
	}
	public Agency toAgency(@Validated AgencyDto agencyDto) {
		return modelMapper.map(agencyDto, Agency.class);
	}
	
	public AgencyDto toAgencyDto(@Validated Agency agency) {
		return modelMapper.map(agency, AgencyDto.class);
	}
	
	
	
	public Agency getAgencyByAgentId(Long agentId) throws Exception {
		log.info("agentId"+agentId);
		return agencyRepo.findById(agentId).
    			orElseThrow(() -> new Exception("Agent not found"));
	}
	    public List<Agency> getAgencyDetails() {
	    	return agencyRepo.findAll();
	    }
	    
	    public String getAgentNameByAgentId(Long agentId) throws Exception {
	    Agency agency=	agencyRepo.getAgencyByAgentId(agentId);
	    	return agency.getAgentName();
		}

	    
	    public List<Object> getAllAgencyNameCodeForCCNFRMC(String searchText) {
			List<Object> lstObj=agencyRepo.getAllAgencyNameCodeForCCNFRMC(searchText);
			return lstObj;
		}   
	  
	
	


}

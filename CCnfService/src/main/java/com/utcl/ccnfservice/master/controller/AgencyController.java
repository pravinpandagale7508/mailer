package com.utcl.ccnfservice.master.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.Agency;
import com.utcl.ccnfservice.master.service.AgencyService;
import com.utcl.dto.ccnf.AgencyDto;
import com.utcl.dto.ccnf.CustomException;

@RestController
@RequestMapping("/api/agent")
public class AgencyController {

	private AgencyService agencyService;

	public AgencyController(AgencyService agencyService) 
	{
		super();
		this.agencyService = agencyService;
	}

	@PostMapping("/addAgency")
	public ResponseEntity<AgencyDto> addAgencyDetails(@RequestBody AgencyDto agencyDto) {
		return ResponseEntity.ok(agencyService.addAgencyDetails(agencyDto));

	}

	@GetMapping("/getAgentDetailsByAgentId/{agentId}")
	public Agency getAgencyById(@PathVariable("agentId") long agentId) throws Exception {
		return agencyService.getAgencyByAgentId(agentId);
	}

	@GetMapping("/getAgentNameByAgentId/{agentId}")
	public String getAgentNameByAgentId(@PathVariable("agentId") long agentId) throws Exception {
		return agencyService.getAgentNameByAgentId(agentId);
	}

	@GetMapping("/getAgencyDetails")
	public List<Agency> getAgencyDetails() {
//		try {
//			int i = 10 / 0;
//		} catch (Exception e) {
//			throw new CustomException("testing error", "asdf session", "123 trace");
//		}
		return agencyService.getAgencyDetails();
	}

	@PutMapping("/updateAgency")
	public ResponseEntity<AgencyDto> updateAgencyDetails(@RequestBody AgencyDto agencyDto) {
		return ResponseEntity.ok(agencyService.addAgencyDetails(agencyDto));

	}
	
	@GetMapping("/getAllAgencyNameCodeForCCNFRMC")
    public List<Object> getAllAgencyNameCodeForCCNFRMC(@RequestParam String searchText) throws Exception 
    {
        return agencyService.getAllAgencyNameCodeForCCNFRMC(searchText);
    }	

}

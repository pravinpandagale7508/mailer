package com.utcl.ccnfservice.master.controller;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnfservice.master.entity.State;
import com.utcl.ccnfservice.master.service.StateService;
import com.utcl.dto.ccnf.StateDto;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/state")
@Slf4j
public class StateController {
	
	private StateService statecService;
	

	public StateController(StateService statecService) {
		super();
		this.statecService = statecService;
	}
	
	@PostMapping("/addState")
	public ResponseEntity<StateDto> addStateDetails(@RequestBody StateDto slocDto) {
		 return ResponseEntity.ok(statecService.addStateDetails(slocDto));

	}
	
	@GetMapping("/getStateById/{id}")
	public State getStateDetailsById(@PathVariable("id") long id)throws Exception {
		return statecService.getStateByStateId(id);
	}
	
	@GetMapping("/getStateDetails")
	public List<State> getStateDetails() {
		return statecService.getStateDetails();
	}

	@PutMapping("/updateState")
	public ResponseEntity<StateDto> updateStateDetails(@RequestBody StateDto slocDto) {
		 return ResponseEntity.ok(statecService.addStateDetails(slocDto));

	}
	
	@PostMapping("/getStateDetailsByzoneIds")
	public List<State> getStateDetailsByzoneIds(@RequestBody Map<String,Object> data) 
	{
		log.info("Inside state controller");
		return statecService.getStateDetailsByzoneIds((List<Long>) data.get("zoneId"));
	}
	
	
}

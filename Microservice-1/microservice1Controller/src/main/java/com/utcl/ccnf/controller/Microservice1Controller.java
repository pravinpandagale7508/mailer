package com.utcl.ccnf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnf.dto.M1Dto;
import com.utcl.ccnf.m1service.M1Service;

@RestController
public class Microservice1Controller {
	//@Autowired
	M1Service m1Service;
	
	
	public Microservice1Controller(M1Service m1Service) {
		super();
		this.m1Service = m1Service;
	}

	@GetMapping("/m1/data")
	public Object getData() {
		return m1Service.getData();
	}
	
	@PostMapping("/m1/create")
	public Object create(@RequestBody M1Dto m1Dto) {
		return m1Service.create(m1Dto.getName(), m1Dto.getDesc());
	}
}

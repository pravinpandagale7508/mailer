package com.utcl.m1service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.utcl.m1Entity.Microservice1Table;
import com.utcl.m1repo.Microservice1Repo;

@Service
public class M1Service {
	//@Autowired
	Microservice1Repo microservice1Repo;
	
	
	public M1Service(Microservice1Repo microservice1Repo) {
		super();
		this.microservice1Repo = microservice1Repo;
	}
	public Object getData() {
		return microservice1Repo.findAll();
	}
	public Object create(String name,String desc) {
		try {
			Microservice1Table t1= new Microservice1Table();
			t1.setName(name);
			t1.setDescription(desc);
			return microservice1Repo.save(t1);
		} catch (Exception e) {
			// TODO: handle exception
			return "FAIL";
		}
	}
}

package com.utcl.ccnf.m1repo;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utcl.ccnf.m1Entity.Microservice1Table;

@Repository
public interface Microservice1Repo extends JpaRepository<Microservice1Table, Long> {
	
}

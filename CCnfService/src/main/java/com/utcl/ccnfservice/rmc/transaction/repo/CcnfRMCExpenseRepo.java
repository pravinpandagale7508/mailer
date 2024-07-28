package com.utcl.ccnfservice.rmc.transaction.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utcl.ccnfservice.rmc.transaction.entity.CcnfRMCExpense;

public interface CcnfRMCExpenseRepo extends JpaRepository<CcnfRMCExpense, Long> {

}

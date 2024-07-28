package com.utcl.ccnfservice.master.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accesspermissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessPermissions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long permissionID;

	@Column(nullable = false)
	private String feature;
	@Column(nullable = false)
	private String reator;

	@Column(nullable = false)
	private Boolean viewOnly;
	@Column(nullable = false)
	private Boolean modify;
	@Column(nullable = false)
	private Boolean approve;
	
	@Column(nullable = false)
	private String l1;
	@Column(nullable = false)
	private Boolean l1ViewOnly;
	@Column(nullable = false)
	private Boolean l1Modify;
	
	@Column(nullable = false)
	private String l2;
	@Column(nullable = false)
	private Boolean l2ViewOnly;
	@Column(nullable = false)
	private Boolean l2Modify;
	
	@Column(nullable = false)
	private String l3;
	@Column(nullable = false)
	private Boolean l3ViewOnly;
	@Column(nullable = false)
	private Boolean l3Modify;
	
	@Column(nullable = false)
	private String l4;
	@Column(nullable = false)
	private Boolean l4ViewOnly;
	@Column(nullable = false)
	private Boolean l4Modify;
	
	
	@Column(nullable = false)
	private String l5;
	@Column(nullable = false)
	private Boolean l5ViewOnly;
	@Column(nullable = false)
	private Boolean l5Modify;

}

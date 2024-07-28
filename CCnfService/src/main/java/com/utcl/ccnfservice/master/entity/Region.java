package com.utcl.ccnfservice.master.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="region")
@Data
@NoArgsConstructor
@AllArgsConstructor  
@Builder
public class Region {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long    regionId;
private Long    stateId;
private String  regionName;
private String  regionDesc;
private Date	createdDate;
private Date	updatedDate;
private String  createdBy;
private String	updatedBy;
}

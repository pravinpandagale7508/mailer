package com.utcl.ccnfservice.master.entity;
import java.util.Date;
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
@Table(name="permissionregionmapping")
@Data
@NoArgsConstructor
@AllArgsConstructor  
@Builder
public class PermissionRegionMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    mappingID;
	private Long    permissionID;
	private Long    regionID;
}

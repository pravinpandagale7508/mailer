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
@Table(name="geotaluka")
@Data
@NoArgsConstructor
@AllArgsConstructor  
@Builder
public class GeoTaluka {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long    id;
	private Long    geoDistId;
	private String  talukaName;
	private String  talukaDesc;
	private Date	createdDate;
	private Date	updatedDate;
	private String  createdBy;
	private String	updatedBy;
}

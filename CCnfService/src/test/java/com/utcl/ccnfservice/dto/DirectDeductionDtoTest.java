package com.utcl.ccnfservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.utcl.dto.ccnf.DeductiononDirectSalesDto;

public class DirectDeductionDtoTest {
	@Test
	void testDeductionSaleDto() {
		DeductiononDirectSalesDto deductiononDirectSalesDto = new DeductiononDirectSalesDto(1L, 31.1, null, null, null, null, null, null, null, null, null, null, null, null);
		assertEquals(31.1,deductiononDirectSalesDto.getMinAmount());
	}

	@Test
	void testConstructor() {
		DeductiononDirectSalesDto deductiononDirectSalesDto = new DeductiononDirectSalesDto(1L, 31.1, null, null, null, null, null, null, null, null, null, null, null, null);
				assertEquals(31.1,deductiononDirectSalesDto.getMinAmount());
	}

	/*
	 * @Test void testInvalidCommission() { // Assuming vendorCommission cannot be
	 * negative assertThrows(IllegalArgumentException.class, () -> new VendorDTO(2,
	 * "Vendor B","V002", -0.1)); }
	 */
}

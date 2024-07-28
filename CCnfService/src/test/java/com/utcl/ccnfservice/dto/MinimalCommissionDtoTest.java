package com.utcl.ccnfservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.utcl.dto.ccnf.MinimalCommisionDto;

public class MinimalCommissionDtoTest {
	@Test
	void testMinmalCommissionDto() {
		MinimalCommisionDto miniamCommisionDto = new MinimalCommisionDto(1L, 40.1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
		assertEquals(40.1,miniamCommisionDto.getMinQuantity());
	}

	@Test
	void testConstructor() {
		MinimalCommisionDto miniamCommisionDto = new MinimalCommisionDto(1L, 40.1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
				assertEquals(40.1,miniamCommisionDto.getMinQuantity());}

	/*
	 * @Test void testInvalidCommission() { // Assuming vendorCommission cannot be
	 * negative assertThrows(IllegalArgumentException.class, () -> new VendorDTO(2,
	 * "Vendor B","V002", -0.1)); }
	 */
}

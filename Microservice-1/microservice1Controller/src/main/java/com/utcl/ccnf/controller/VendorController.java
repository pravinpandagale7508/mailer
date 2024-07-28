package com.utcl.ccnf.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.utcl.ccnf.controller.swagger.VendorApi;
import com.utcl.ccnf.dto.VendorDTO;
import com.utcl.ccnf.dto.mapper.VendorRecord;
import com.utcl.ccnf.m1service.PdfGenerator;
import com.utcl.ccnf.m1service.VendorService;

import lombok.var;

@RestController
@RequestMapping("/api/vendors")
public class VendorController implements VendorApi {
	private VendorService vendorService;

	public VendorController(VendorService vendorService) {
		super();
		this.vendorService = vendorService;
	}
	@Override
	@GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable int id) {
        VendorDTO vendorDTO = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendorDTO);
    }

	@PostMapping("/create")
	public Object create(@RequestBody VendorDTO vendorDTO) {
		return vendorService.create(vendorDTO.getVendorName(), vendorDTO.getVendorCode(),vendorDTO.getVendorCommission());
	}
	@PostMapping("/createRecord")
	public Object createRecord(VendorRecord vendorRecord) {
		return vendorService.create(vendorRecord.vendorName(), vendorRecord.vendorCode(),vendorRecord.vendorCommission());
	}
	
	@RequestMapping(value = "/goDownReciept/{sessionId}/{hawb}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<Object> recieptWithBarCode(@PathVariable("sessionId") String sessionId,
			@PathVariable("hawb") String hawb) {
		Map<String, Object> recBarCodeData = new HashMap<String, Object>();
		recBarCodeData.put("ORDER_NUM", 123456789);
		recBarCodeData.put("PHONE", "0123456789");
		String text = "הטקסט שלך בעברית";
		recBarCodeData.put("NAME_USER", text);
		recBarCodeData.put("USER_ID", 125);
		recBarCodeData.put("ADDRESS", " asdf " + text);
		recBarCodeData.put("COUNTRY_CODE", "IN");
		recBarCodeData.put("CITY", "Hyderabad");
		recBarCodeData.put("EMAIL", "pravin.pandagalee@onesm.com");
		recBarCodeData.put("STORAGE_LOCATION", "Hyderabad");
		recBarCodeData.put("TOKEN", "RBP00101");
		recBarCodeData.put("ZIP_CODE", 9785462);
		recBarCodeData.put("HAWB", "ABC00541");
		recBarCodeData.put("SENDER_NAME", "Pravin");
		recBarCodeData.put("QUANTITY", "3");
		recBarCodeData.put("WEIGHT", "100");
		recBarCodeData.put("DESC", "ASDFGHJIJKLYUTR");
		recBarCodeData.put("DATE", "08.11.22");
		//byte[] bis = PdfGenerator.recieptWithBarCode(recBarCodeData);
		byte[] bis = PdfGenerator.goDownReciept(recBarCodeData);
		if (bis == null) {
			return new ResponseEntity<>("Not Found",
					HttpStatus.FORBIDDEN);
		}
		var headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=CWB_" + hawb + ".pdf");
		InputStream targetStream = new ByteArrayInputStream(bis);
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(targetStream));
	}
	
}

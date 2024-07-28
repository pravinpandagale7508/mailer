package com.utcl.ccnfservice.cement.transaction.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utcl.ccnfservice.cement.transaction.entity.CcnfCementSalesDetails;
import com.utcl.ccnfservice.cement.transaction.repo.CcnfCementSalesDetailsRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CcnfCementSalesDetailsTransactionalService {
	@Autowired
	private CcnfCementSalesDetailsRepo ccnfCementSalesDetailsRepo;

	public void dumpCcnfCementSalesDetails(String fileLocation) throws IOException {
		FileInputStream file = new FileInputStream(new File(fileLocation));
		try (Workbook workbook = new XSSFWorkbook(file)) {
			Sheet sheet = workbook.getSheetAt(0);
			int totalNumRows = sheet.getPhysicalNumberOfRows();

			processSheet(sheet, 1, totalNumRows);
			//
		}
		log.info("End");
	}

	private List<String> getHeadersListFromSheet(Sheet sheet) {
		Row row = sheet.getRow(0);
		Iterator<Cell> cells = row.iterator();

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(cells, 0), false).map(Cell::getStringCellValue)
				.toList();
	}

	public void processSheet(Sheet sheet, int start, int limit) {
		DataFormatter dataFormatter = new DataFormatter();
		List<String> headerList = getHeadersListFromSheet(sheet);

		for (int n = start; n < limit; n++) {
			log.info(n + "");
			Row row = sheet.getRow(n);
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

				CcnfCementSalesDetails ccnfCementSalesDetails = CcnfCementSalesDetails.builder()
						.plant(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Plant"))))
						.salesOffice(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Sales Office"))))
						.salesQuantity(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Sales quantity"))))
						.distributionChannel(
								dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Distribution Channel"))))
						.customerGroup1(
								dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Customer Group 1"))))
						.customerGroup2(
								dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Customer Group 2"))))
						.salesGroup(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Sales Group"))))
						.salesOrder(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Sales Order"))))
						.region(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Region"))))
						.profitCenter(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Profit Center"))))
						.invoicedate(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Invoice date"))))
						
						.invDate(formatter.parse( dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Invoice date")))))
						.priceGroup(dataFormatter.formatCellValue(row.getCell(headerList.indexOf("Price Group"))))
						.build();

				ccnfCementSalesDetailsRepo.save(ccnfCementSalesDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Double getTotCemSalesQntByI2Dists(List<String> i2Dists,int month,int year) {
		return ccnfCementSalesDetailsRepo.getTotCemSalesQntByI2Dists(i2Dists,month,year);
	}

	public Double getTotRMCSalesQntByI2Dists(List<String> i2Dists, int month, int year) {
		return ccnfCementSalesDetailsRepo.getTotRMCSalesQntByI2Dists(i2Dists,month,year);
	}

	public Double getTotInterCompanySalesByi2Dists(List<String> i2Dists, int month, int year) {
		return ccnfCementSalesDetailsRepo.getTotInterCompanySalesByi2Dists(i2Dists,month,year);
	}

	public Double getTotCemDirectSalesQntByI2Dists(List<String> i2Dists, String plant, int month, int year) {
		return ccnfCementSalesDetailsRepo.getTotCemDirectSalesQntByI2Dists(i2Dists, plant,month,year);
	}

	public Double getSalesQuantityToOtherDepo(List<String> i2Dists, String depo, int month, int year) {
		return ccnfCementSalesDetailsRepo.getSalesQuantityToOtherDepo(i2Dists, depo,month,year);
	}

	public Double getSalesQuantityFromOtherDepo(List<String> i2Dists, String depo, int month, int year) {
		return ccnfCementSalesDetailsRepo.getSalesQuantityFromOtherDepo(i2Dists, depo,month,year);
	}

	public Double getDirectSalesPer(Double totalSale, Double directSale) {
		return directSale / totalSale * 100;
	}
}
package com.utcl.ccnfservice.cement.transaction.service;

import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utcl.ccnfservice.master.entity.City;
import com.utcl.ccnfservice.master.entity.County;
import com.utcl.ccnfservice.master.entity.Depot;
import com.utcl.ccnfservice.master.entity.GeoDist;
import com.utcl.ccnfservice.master.entity.GeoTaluka;
import com.utcl.ccnfservice.master.entity.I2Taluka;
import com.utcl.ccnfservice.master.entity.I2district;
import com.utcl.ccnfservice.master.entity.Region;
import com.utcl.ccnfservice.master.entity.State;
import com.utcl.ccnfservice.master.entity.Zone;
import com.utcl.ccnfservice.master.repo.CityRepo;
import com.utcl.ccnfservice.master.repo.CountyRepo;
import com.utcl.ccnfservice.master.repo.DepotRepo;
import com.utcl.ccnfservice.master.repo.GeoDistRepo;
import com.utcl.ccnfservice.master.repo.GeoTalukaRepo;
import com.utcl.ccnfservice.master.repo.I2TalukaRepo;
import com.utcl.ccnfservice.master.repo.I2districtRepo;
import com.utcl.ccnfservice.master.repo.RegionRepo;
import com.utcl.ccnfservice.master.repo.StateRepo;
import com.utcl.ccnfservice.master.repo.ZoneRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MasterDataService {
	@Autowired
	private ZoneRepo zoneRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private RegionRepo regionRepo;
	@Autowired
	private DepotRepo depotRepo;
	@Autowired
	private GeoDistRepo geoDistRepo;
	@Autowired
	private I2districtRepo i2districtRepo;
	@Autowired
	private GeoTalukaRepo geoTalukaRepo;
	@Autowired
	private I2TalukaRepo i2TalukaRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private CountyRepo countyRepo;

	public void dumpMasterData(MultipartFile file) throws IOException {

		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			int totalNumRows = sheet.getPhysicalNumberOfRows();

			new Thread(() -> {
				try {
					processSheet(sheet, 1, 15000);
				} catch (Exception e) {
					log.info("Th 1 ");
				}

			}).start();
			new Thread(() -> {
				try {
					processSheet(sheet, 15001, 30000);
				} catch (Exception e) {

					log.info("Th 2 ");
				}

			}).start();
			new Thread(() -> {
				try {
					processSheet(sheet, 30001, 45000);
				} catch (Exception e) {

					log.info("Th 5 ");
				}

			}).start();
			new Thread(() -> {
				try {
					processSheet(sheet, 45001, 60000);
				} catch (Exception e) {

					log.info("Th 3 ");
				}

			}).start();
			new Thread(() -> {
				try {
					processSheet(sheet, 60001, 75000);
				} catch (Exception e) {

					log.info("Th 4 ");
				}

			}).start();
			new Thread(() -> {
				try {
					processSheet(sheet, 75001, totalNumRows);
				} catch (Exception e) {

					log.info("Th 4 ");
				}

			}).start();
			// processSheet(sheet, 1, totalNumRows);
		}

		log.info("End");
	}

	public void processSheet(Sheet sheet, int start, int limit) {
		DataFormatter dataFormatter = new DataFormatter();
		while (start < limit) {

			Row row = sheet.getRow(start);
			try {

				int i = row.getFirstCellNum();
				Zone z = zoneRepo.getZoneByZoneCode(dataFormatter.formatCellValue(row.getCell(i)));

				Zone zone = new Zone(null, dataFormatter.formatCellValue(row.getCell(i)),
						dataFormatter.formatCellValue(row.getCell(++i)));
				if (z == null) {
					z = zoneRepo.save(zone);
				}
				State st = stateRepo.getStateByStateCode(dataFormatter.formatCellValue(row.getCell(++i)));

				State state = new State(null, z.getId(), dataFormatter.formatCellValue(row.getCell(i)),
						dataFormatter.formatCellValue(row.getCell(++i)));
				if (st == null) {
					st = stateRepo.save(state);
				}

				Region rg = regionRepo.getRegionByRegionName(dataFormatter.formatCellValue(row.getCell(++i)));
				Region region = new Region(null, st.getId(), dataFormatter.formatCellValue(row.getCell(i)),
						dataFormatter.formatCellValue(row.getCell(++i)), new Date(), new Date(), "", "");

				if (rg == null) {
					rg = regionRepo.save(region);
				}

				Depot dp = depotRepo.getDepotByDepotName(dataFormatter.formatCellValue(row.getCell(++i)));
				Depot depot = new Depot(null, rg.getRegionId(), dataFormatter.formatCellValue(row.getCell(i)),
						dataFormatter.formatCellValue(row.getCell(++i)), new Date(), new Date(), "", "");

				if (dp == null) {
					dp = depotRepo.save(depot);
				}

				GeoDist gd = geoDistRepo.getGeoDistByDistName(dataFormatter.formatCellValue(row.getCell(++i)));
				GeoDist gioDist = new GeoDist(null, dp.getDepotId(), dataFormatter.formatCellValue(row.getCell(i)),
						dataFormatter.formatCellValue(row.getCell(++i)), new Date(), new Date(), "", "");

				if (gd == null) {
					gd = geoDistRepo.save(gioDist);
				}

				I2district i2 = i2districtRepo.getI2districtByDistName(dataFormatter.formatCellValue(row.getCell(++i)));
				I2district itDistrict = new I2district(null, dp.getDepotId(),
						dataFormatter.formatCellValue(row.getCell(i)), dataFormatter.formatCellValue(row.getCell(++i)),
						new Date(), new Date(), "", "");

				if (i2 == null) {
					i2 = i2districtRepo.save(itDistrict);
				}

				GeoTaluka gt = geoTalukaRepo.getGeoTalukaByTalukaName(dataFormatter.formatCellValue(row.getCell(++i)));
				GeoTaluka geoTaluka = new GeoTaluka(null, gd.getDepotId(),
						dataFormatter.formatCellValue(row.getCell(i)), dataFormatter.formatCellValue(row.getCell(++i)),
						new Date(), new Date(), "", "");

				if (gt == null) {
					// gt =
					geoTalukaRepo.save(geoTaluka);
				}

				I2Taluka itl = i2TalukaRepo.getI2TalukaByTalukaName(dataFormatter.formatCellValue(row.getCell(++i)));
				I2Taluka itTaluka = new I2Taluka(null, i2.getDistId(), dataFormatter.formatCellValue(row.getCell(i)),
						dataFormatter.formatCellValue(row.getCell(++i)), new Date(), new Date(), "", "");

				if (itl == null) {
					itl = i2TalukaRepo.save(itTaluka);
				}

				City ct = cityRepo.getCityByCityName(dataFormatter.formatCellValue(row.getCell(++i)));
				City city = new City(null, itl.getId(), dataFormatter.formatCellValue(row.getCell(i)),
						dataFormatter.formatCellValue(row.getCell(++i)), new Date(), new Date(), "", "");

				if (ct == null) {
					ct = cityRepo.save(city);
				}

				County cnt = countyRepo.getCountyByCountyName(dataFormatter.formatCellValue(row.getCell(++i)));
				County county = new County(null, ct.getId(), dataFormatter.formatCellValue(row.getCell(i)),
						dataFormatter.formatCellValue(row.getCell(++i)), new Date(), new Date(), "", "");

				if (cnt == null) {
					// cnt =
					countyRepo.save(county);
				}
			} catch (Exception e) {
				// e.printStackTrace();;
				log.error(e.getMessage());
			}
			++start;
		}

//		if (start < limit) {
//			++start;
//			processSheet(sheet, start, limit);
//		}

	}
}
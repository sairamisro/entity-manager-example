package com.bezkoder.spring.files.excel.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.files.excel.controller.ExcelController;
import com.bezkoder.spring.files.excel.model.SampleInventory;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "code", "name", "batch", "stock", "deal", "free", "mrp", "rate", "exp", "company",
			"supplier" };
	static String SHEET = "sample_inventory";
	
	static Logger logger = LoggerFactory.getLogger(ExcelHelper.class);


	public static boolean hasExcelFormat(MultipartFile file) {

		logger.debug("hasExcelFormat-method start.");

		if (!TYPE.equals(file.getContentType())) {
			// return false;
		}

		return true;
	}

	public static ByteArrayInputStream inventoryToExcel(List<SampleInventory> sampleInventorys) {

		logger.debug("inventoryToExcel-method start.");
		
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			for (SampleInventory sampleInventory : sampleInventorys) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(sampleInventory.getCode());
				row.createCell(1).setCellValue(sampleInventory.getName());
				row.createCell(2).setCellValue(sampleInventory.getBatch());
				row.createCell(3).setCellValue(sampleInventory.getStock());
				row.createCell(4).setCellValue(sampleInventory.getDeal());
				row.createCell(5).setCellValue(sampleInventory.getFree());
				row.createCell(6).setCellValue(sampleInventory.getMrp());
				row.createCell(7).setCellValue(sampleInventory.getRate());
				row.createCell(8).setCellValue(sampleInventory.getExp());
				row.createCell(9).setCellValue(sampleInventory.getCompany());
				row.createCell(10).setCellValue(sampleInventory.getSupplier());
			}

			workbook.write(out);
			
			logger.debug("inventoryToExcel-method end.");
			
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			logger.debug("inventoryToExcel-method exception.");
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static List<SampleInventory> excelToInventory(InputStream is) {
		try {

			logger.debug("excelToInventory-method start.");
			
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<SampleInventory> sampleInventorys = new ArrayList<SampleInventory>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();

				SampleInventory sampleInventory = new SampleInventory();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					String strCell = "0";
					if ("STRING".equalsIgnoreCase(currentCell.getCellType().toString())) {
						strCell = currentCell.getStringCellValue();
					} else if ("NUMERIC".equalsIgnoreCase(currentCell.getCellType().toString())) {
						strCell = String.valueOf(currentCell.getNumericCellValue());

						if (HSSFDateUtil.isCellDateFormatted(currentCell)) {
							strCell = String.valueOf(currentCell.getNumericCellValue());
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							Date date = currentCell.getDateCellValue();
							strCell = df.format(date);
						}

					}

					switch (cellIdx) {
					case 0:
						sampleInventory.setCode(strCell);
						break;

					case 1:
						sampleInventory.setName(strCell);
						break;

					case 2:
						sampleInventory.setBatch(strCell);
						break;

					case 3:
						sampleInventory.setStock(Double.parseDouble(strCell));
						break;

					case 4:
						sampleInventory.setDeal(Double.parseDouble(strCell));
						break;

					case 5:
						sampleInventory.setFree(Double.parseDouble(strCell));
						break;

					case 6:
						sampleInventory.setMrp(Double.parseDouble(strCell));
						break;

					case 7:
						sampleInventory.setRate(Double.parseDouble(strCell));
						break;

					case 8:
						if (!strCell.startsWith("/")) {
							sampleInventory.setExp(new Date(strCell));
						}

						break;

					case 9:
						sampleInventory.setCompany(strCell);
						break;

					case 10:
						sampleInventory.setSupplier(strCell);
						break;

					default:
						break;
					}

					cellIdx++;
				}

				sampleInventorys.add(sampleInventory);
			}

			workbook.close();
			
			logger.debug("excelToInventory-method end.");

			return sampleInventorys;
		} catch (IOException e) {
			logger.debug("excelToInventory-method IOException.");

			e.printStackTrace();
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		} catch (Exception e) {
			logger.debug("excelToInventory-method Exception.");

			e.printStackTrace();
			throw new RuntimeException("fail : " + e.getMessage());
		}
	}
}

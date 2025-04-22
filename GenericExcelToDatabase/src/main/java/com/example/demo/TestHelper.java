package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TestHelper {

	@Autowired
	private static TestRepository excelDataRepository;

	public static List<Map<String, Object>> parseExcelToListOfMap(InputStream stream) throws IOException {

		List<Map<String, Object>> dataList = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(stream)) {
			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();

			if (!rowIterator.hasNext()) {
				return dataList;
			}

			// Step 1: Read header
			Row headerRow = rowIterator.next();
			List<String> headers = new ArrayList<>();
			for (Cell cell : headerRow) {
				headers.add(cell.getStringCellValue().trim());
			}

			// Step 2: Read data rows
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Map<String, Object> rowMap = new LinkedHashMap<>();

				for (int i = 0; i < headers.size(); i++) {
					Cell cell = row.getCell(i);
					Object cellValue = getCellValue(cell);
					rowMap.put(headers.get(i), cellValue);
				}

				dataList.add(rowMap);

			}
		}
		saveExcelRows(dataList);
		return dataList;

	}

	private static Object getCellValue(Cell cell) {
		if (cell == null)
			return null;

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			}
			return cell.getNumericCellValue();
		case BOOLEAN:
			return cell.getBooleanCellValue();
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
		default:
			return null;
		}
	}

	@Autowired
	private static ObjectMapper objectMapper;

	public static void saveExcelRows(List<Map<String, Object>> excelRows) {
		for (Map<String, Object> row : excelRows) {
			try {
				String json = objectMapper.writeValueAsString(row);

				ExcelData entity = new ExcelData();
				entity.setData(json);

				try {
					excelDataRepository.save(entity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JsonProcessingException e) {
				throw new RuntimeException("Failed to convert map to JSON", e);
			}
		}
	}

}

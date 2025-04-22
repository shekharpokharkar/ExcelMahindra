package com.example.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;

public class ProductUtils {

	private static XSSFWorkbook xssfWorkbook;

	public static boolean isExcelFile(MultipartFile file) {
		String contentType = file.getContentType();
		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" + "")) {
			return true;
		}
		return false;
	}

	public static List<Product> convertListOfProduct(InputStream stream) throws IOException {
		List<Product> productList = new ArrayList<>();

		try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(stream)) {
			XSSFSheet sheet = xssfWorkbook.getSheet("Data");
			Iterator<Row> iterator = sheet.iterator();

			int rowCount = 0;
			while (iterator.hasNext()) {

				Row row = iterator.next();
				if (rowCount == 0) {
					rowCount++;
					continue;
				}

				Iterator<Cell> iterator2 = row.iterator();
				int cellId = 0;
				Product p = new Product();
				while (iterator2.hasNext()) {
					Cell cell = iterator2.next();

					switch (cellId) {
					case 0:
						p.setProductId((int) cell.getNumericCellValue());
						break;
					case 1:
						p.setProductName(cell.getStringCellValue());
						break;
					case 2:
						p.setProductDescription(cell.getStringCellValue());
						break;
					case 3:
						p.setProductPrice(cell.getNumericCellValue());
						break;
					}
					cellId++;

				}
				productList.add(p);
			}
		}
		return productList;

	}

}

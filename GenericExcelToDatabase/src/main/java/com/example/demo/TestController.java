package com.example.demo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestHelper helper;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
		try {
			List<Map<String, Object>> data = helper.parseExcelToListOfMap(file.getInputStream());
			return ResponseEntity.ok(data);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to parse Excel");
		}
	}

}

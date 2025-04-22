package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.ProductUtils;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> saveProduct(MultipartFile file) throws IOException {
		List<Product> saveAll = null;
		boolean excelFile = ProductUtils.isExcelFile(file);
		if (excelFile) {
			List<Product> convertListOfProduct = ProductUtils.convertListOfProduct(file.getInputStream());

			saveAll = productRepository.saveAll(convertListOfProduct);

		}
		return saveAll;
	}

	public List<Product> getAllProduct() throws IOException {
		List<Product> saveAll = productRepository.findAll();

		return saveAll;
	}

}

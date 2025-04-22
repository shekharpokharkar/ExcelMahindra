package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("/")
	public ResponseEntity<List<Product>> saveProduct(@RequestParam("file") MultipartFile file) throws IOException {
		List<Product> saveProduct = productService.saveProduct(file);
		return new ResponseEntity<List<Product>>(saveProduct, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProduct() throws IOException {
		List<Product> saveProduct = productService.getAllProduct();
		return new ResponseEntity<List<Product>>(saveProduct, HttpStatus.OK);
	}

}

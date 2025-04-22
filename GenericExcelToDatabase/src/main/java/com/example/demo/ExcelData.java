package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "excel_data_json")
public class ExcelData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "json") // for PostgreSQL; use "json" for MySQL 5.7+
	private String data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ExcelData [id=" + id + ", data=" + data + "]";
	}

	public ExcelData(Long id, String data) {
	
		this.id = id;
		this.data = data;
	}

	public ExcelData() {

	}

	// Getters and setters

}

package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<ExcelData, Long> {

}

package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.DepartmentAndAvgSalary;
import com.example.entity.DummyData;
import com.example.service.EmployeeService;

@RestController
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("deptWiseAvgSalary")
	public List<DepartmentAndAvgSalary> getDeptAvgSalary() {
		return employeeService.getDeptAvgSalary();
	}

	@GetMapping("dummydataafterbulkinsert")
	public List<DummyData> getDummyTableDataAfterBulkInsert() {
		return employeeService.getDummyTableDataAfterBulkInsert();
	}
}

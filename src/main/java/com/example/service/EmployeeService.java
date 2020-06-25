package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.EmployeeDao;
import com.example.entity.DepartmentAndAvgSalary;
import com.example.entity.DummyData;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	public List<DepartmentAndAvgSalary> getDeptAvgSalary() {
		return employeeDao.getDeptAndAvgSalary();
	}
	
	public List<DummyData> getDummyTableDataAfterBulkInsert() {
		return employeeDao.getDummyTableDataAfterBulkInsert();
	}
}

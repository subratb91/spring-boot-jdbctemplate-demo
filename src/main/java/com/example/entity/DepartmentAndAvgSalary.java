package com.example.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentAndAvgSalary {
	
	private String departmentName;
	private Double averageSalary;
	private Date hireDate;
}

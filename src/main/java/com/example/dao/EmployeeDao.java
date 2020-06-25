package com.example.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.entity.DepartmentAndAvgSalary;
import com.example.entity.DummyData;

@Repository
public class EmployeeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String SELECT_QUERY_DEPARTMENT_WISE_AVG_SAL = "SELECT d.DEPARTMENT_NAME, AVG(SALARY) AS AVG_SALARY FROM EMPLOYEES e JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.DEPARTMENT_ID GROUP BY e.DEPARTMENT_ID";

	private final String SELECT_QUERY_DEPARTMENT_WISE_AVG_SAL_HIREDATE = "SELECT d.DEPARTMENT_NAME, AVG(SALARY) AS AVG_SALARY, e.HIRE_DATE FROM EMPLOYEES e JOIN DEPARTMENTS d ON e.DEPARTMENT_ID = d.DEPARTMENT_ID GROUP BY e.DEPARTMENT_ID,e.HIRE_DATE";

	private final String SELECT_ALL_FROM_DUMMY = "SELECT * FROM DUMMY";

	private final String INSERT_INTO_DUMMY_TABLE = "INSERT INTO DUMMY (DEPARTMENT_NAME, AVG_SALARY,HIRE_DATE) VALUES(?,?,?)";

	public List<DepartmentAndAvgSalary> getDeptAndAvgSalary() {

		List<DepartmentAndAvgSalary> resultList = jdbcTemplate.query(SELECT_QUERY_DEPARTMENT_WISE_AVG_SAL,
				this::mapDepartmentAndAvgSalary);
		return resultList;
	}

	private DepartmentAndAvgSalary mapDepartmentAndAvgSalary(ResultSet resultSet, int i) throws SQLException {

		DepartmentAndAvgSalary departmentAndAvgSalary = new DepartmentAndAvgSalary();
		departmentAndAvgSalary.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));

		// departmentAndAvgSalary.setAverageSalary(new Double(new
		// DecimalFormat("0.00").format(resultSet.getDouble("AVG_SALARY"))));
		// System.out.println(new
		// DecimalFormat("0.00").format(resultSet.getDouble("AVG_SALARY")));
		departmentAndAvgSalary
				.setAverageSalary(Double.valueOf(new DecimalFormat("0.00").format(resultSet.getDouble("AVG_SALARY"))));
		// departmentAndAvgSalary.setAverageSalary(Double.valueOf(Math.round(resultSet.getDouble("AVG_SALARY")*100)/100));
		return departmentAndAvgSalary;
	}

	private DepartmentAndAvgSalary mapDepartmentAndAvgSalaryAndHireDate(ResultSet resultSet, int i)
			throws SQLException {

		DepartmentAndAvgSalary departmentAndAvgSalary = new DepartmentAndAvgSalary();
		departmentAndAvgSalary.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
		departmentAndAvgSalary.setAverageSalary(resultSet.getDouble("AVG_SALARY"));
		departmentAndAvgSalary.setHireDate(resultSet.getDate("HIRE_DATE"));

		return departmentAndAvgSalary;
	}

	private void bulkInsertData() {
		List<DepartmentAndAvgSalary> resultList = jdbcTemplate.query(SELECT_QUERY_DEPARTMENT_WISE_AVG_SAL_HIREDATE,
				this::mapDepartmentAndAvgSalaryAndHireDate);

		BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, resultList.get(i).getDepartmentName());
				ps.setDouble(2, resultList.get(i).getAverageSalary());
				Date hireDate = resultList.get(i).getHireDate();
				if (hireDate != null) {
					ps.setDate(3, new java.sql.Date(hireDate.getTime()));
				} else {
					ps.setNull(3, java.sql.Types.DATE);
				}
			}

			public int getBatchSize() {
				return resultList.size();
			}
		};
		jdbcTemplate.batchUpdate(INSERT_INTO_DUMMY_TABLE, batchPreparedStatementSetter);
	}

	public List<DummyData> getDummyTableDataAfterBulkInsert() {
		bulkInsertData();
		List<DummyData> resultList = jdbcTemplate.query(SELECT_ALL_FROM_DUMMY,
				(rs, rowNum) -> new DummyData(rs.getString("DEPARTMENT_NAME"), rs.getDouble("AVG_SALARY"),
						rs.getDate("HIRE_DATE")));
		return resultList;
	}
}

package com.example.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.entity.UserEntity;

@Repository
public class UserDao {

	private static final String SELECT_FROM_USERS = "SELECT * FROM USERS";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<UserEntity> getUsers_query_1() {
		return jdbcTemplate.query(SELECT_FROM_USERS,
				(rs, rowNum) -> new UserEntity(rs.getLong("id"), rs.getString("name"), rs.getString("email")));
	}
}

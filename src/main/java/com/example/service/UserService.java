package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.entity.UserEntity;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public List<UserEntity> getUsers_query_1(){
		return userDao.getUsers_query_1();
	}

}

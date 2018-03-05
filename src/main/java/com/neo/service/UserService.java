package com.neo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neo.entity.UserEntity;
import com.neo.mapper.UserMapper;

@Component
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public List<UserEntity> getAll(){
		return userMapper.getAll();
	}

	public UserEntity getOne(Long id){
		return userMapper.getOne(id);
	}

	public void insert(UserEntity user){
		userMapper.insert(user);
	}

	public void update(UserEntity user){
		userMapper.update(user);
	}

	public void delete(Long id){
		userMapper.delete(id);
	}

}

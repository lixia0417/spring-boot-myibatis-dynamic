package com.neo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.entity.UserEntity;
import com.neo.route.RoutingDataSourceContext;
import com.neo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/getUsers")
	public Map<String,List<UserEntity>> getUsers() {
		Map<String,List<UserEntity>> resultMap = new HashMap<String,List<UserEntity>>();
		System.out.println("use default datasource");
		List<UserEntity> users = userService.getAll();
		resultMap.put("def", users);
		System.out.println("use slave datasource");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(RoutingDataSourceContext.DATASOURCE_KEY, "slaveDataSource");
		map.put(RoutingDataSourceContext.DATASOURCE_DRIVER, "com.mysql.jdbc.Driver");
		map.put(RoutingDataSourceContext.DATASOURCE_URL,
				"jdbc:mysql://192.168.22.43/TestDbSlave?useUnicode=true&characterEncoding=utf-8");
		map.put(RoutingDataSourceContext.DATASOURCE_USERNAME, "hyapp");
		map.put(RoutingDataSourceContext.DATASOURCE_PASSWORD, "huoji123");
		RoutingDataSourceContext.setDBType(map);
		List<UserEntity> slaveUsers = userService.getAll();
		resultMap.put("slave", slaveUsers);
		System.out.println("through aop ,this should use default datasource again");
		users = userService.getAll();
		resultMap.put("def1", users);
		return resultMap;
	}

	@RequestMapping("/getUser")
	public UserEntity getUser(Long id) {
		UserEntity user = userService.getOne(id);
		return user;
	}

	@RequestMapping("/add")
	public void save(UserEntity user) {
		userService.insert(user);
	}

	@RequestMapping(value = "update")
	public void update(UserEntity user) {
		userService.update(user);
	}

	@RequestMapping(value = "/delete/{id}")
	public void delete(@PathVariable("id") Long id) {
		userService.delete(id);
	}

}
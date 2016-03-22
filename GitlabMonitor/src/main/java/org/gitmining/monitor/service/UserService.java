package org.gitmining.monitor.service;

import java.util.List;

import org.gitmining.monitor.bean.User;
import org.gitmining.monitor.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public boolean newUser(User user) {
		userDao.insertNewUser(user);
		return true;
	}

	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	public int saveUser(User user) {
		return userDao.updateUser(user);
	}

	public int changeUserStatus(User user) {
		return userDao.updateUserStatus(user);
	}

	public List<User> getUnactivatedUsers() {
		return userDao.selectUnactivatedUsers();
	}

	public List<User> getUsers(User user) {
		return userDao.selectUsers(user);
	}

}

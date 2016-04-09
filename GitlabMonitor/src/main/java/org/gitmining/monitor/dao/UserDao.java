package org.gitmining.monitor.dao;

import java.util.List;

import org.gitmining.monitor.bean.User;
import org.springframework.stereotype.Repository;
@Repository
public class UserDao extends BaseDaoImpl{

	public int insertNewUser(User user) {
		sqlSession.insert("user.insertNewUser", user);
		return user.getId();
	}

	public User getUserByName(String name) {
		return sqlSession.selectOne("user.selectUserByName", name);
	}
	
	public User getUserByEmail(String email) {
		return sqlSession.selectOne("user.selectUserByEmail", email);
	}

	public int updateUser(User user) {
		return sqlSession.update("user.updateUser", user);
	}

	public int updateUserStatus(User user) {
		return sqlSession.update("user.updateUserStatus", user);
	}


	public List<User> selectUnactivatedUsers() {
		return sqlSession.selectList("user.selectUnactivatedUsers");
	}

	public List<User> selectUsers(User user) {
		return sqlSession.selectList("user.selectUsers", user);
	}

}

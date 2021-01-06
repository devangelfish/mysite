package com.bitacademy.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.exception.UserRepositoryException;
import com.bitacademy.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;

	public UserVo findByNo(Long userNo) {
		return sqlSession.selectOne("user.find", userNo);
	}

	public UserVo findByEmailAndPassword(UserVo vo) {
		return sqlSession.selectOne("user.findByEmailAndPassword", vo);	
	}

	public int update(UserVo vo) {
		return sqlSession.update("user.update", vo);
	}

	public int insert(UserVo vo) throws UserRepositoryException {
		return sqlSession.insert("user.insert", vo);
	}
}

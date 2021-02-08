package com.bitacademy.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(GalleryVo vo) {
		return sqlSession.insert("gallery.insert", vo);
	}
	
	public List<GalleryVo> select() {
		return sqlSession.selectList("gallery.select");
	}

	public int delete(Long no) {
		return sqlSession.delete("gallery.delete", no);
	}
}

package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int getBoardVoCount() {
		return sqlSession.selectOne("board.count");
	}
	
	public int getBoardVoCountbyKeyword(String keyword) {
		return sqlSession.selectOne("board.countBykeyword", keyword);
	}
	
	public List<BoardVo> fetch(int page) {
		return sqlSession.selectList("board.fetch", page);
	}
	
	public List<BoardVo> keywordFetch(Integer page, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("keyword", keyword);
		return sqlSession.selectList("board.fetchbyKeyword", map);
	}
	
	public int write(BoardVo boardVo) {
		return sqlSession.insert("board.write", boardVo);
	}
	
	public int reply(BoardVo boardVo, BoardVo parentBoardVo) {
		Map<String, BoardVo> map = new HashMap<String, BoardVo>();
		map.put("boardVo", boardVo);
		map.put("parentBoardVo", parentBoardVo);
		sqlSession.insert("board.reply", map);
		return sqlSession.update("board.updateOrder", parentBoardVo);
	}
	
	public int update(BoardVo boardVo) {
		return sqlSession.update("board.update", boardVo);
	}
	
	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);	
	}
	
	public int updateHitbyNo(Long no) {
		return sqlSession.update("board.updateHitbyNo", no);
	}
	
	public int delete(BoardVo boardVo) {
		return sqlSession.delete("board.delete", boardVo);
	}
}

package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public List<BoardVo> getBoardList(int page) {
		return boardRepository.fetch(page);
	}

	public int getIndex(int page) {
		return Math.round(page / 6) * 5 + 1;
	}
	
	public int getLastPage(int page) {
		return (int)Math.ceil(boardRepository.getBoardVoCount() / 5.0);
	}
	
	public void increaseHit(Long no) {
		boardRepository.updateHitbyNo(no);
	}

	public BoardVo getContents(Long no) {
		return boardRepository.findByNo(no);
	}
	
	public void editContents(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteContents(BoardVo vo) {
		boardRepository.delete(vo);
	}
	
	public void writeContents(BoardVo vo) {
		boardRepository.write(vo);
	}
	
	public void replyContents(BoardVo vo, BoardVo parentVo) {
		boardRepository.reply(vo, parentVo);
	}
}
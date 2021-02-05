package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.GuestbookRepository;
import com.bitacademy.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getMessageList() {
		return guestbookRepository.findAll();
	}
	
	public List<GuestbookVo> getMessageList(Long startNo) {
		return guestbookRepository.findAll(startNo);
	}

	public void writeMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}

	public boolean deleteMessage(GuestbookVo vo) {
		int count = guestbookRepository.delete(vo);
		return count == 1;
	}

	public boolean deleteMessage(Long no, String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
		return deleteMessage(vo);
	}
	 
}

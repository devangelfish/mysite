package com.bitacademy.mysite.aspect;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitacademy.mysite.service.BoardService;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.security.Auth;
import com.bitacademy.security.AuthUser;

@Auth
@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/{page}")
	public String list(@PathVariable(value="page") int page, Model model) {
		List<BoardVo> boardList = boardService.getBoardList(page);
		int index = boardService.getIndex(page);
		int lastPage = boardService.getLastPage(page);
		model.addAttribute("boardList", boardList);
		model.addAttribute("index", index);
		model.addAttribute("lastPage", lastPage);
		return "board/list";
	}
	
	@RequestMapping("/{page}/view/{no}/{name}")
	public String view(@PathVariable(value="page") int page, @PathVariable(value="no") Long no, @PathVariable(value="name") String name, Model model) {
		boardService.increaseHit(no);
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("newLine", "\n");
		return "board/view";
	}
	
	@RequestMapping(value="/{page}/modify/{no}/{name}", method=RequestMethod.GET)
	public String modify(@PathVariable(value="page") int page, @PathVariable(value="no") Long no, @PathVariable(value="name") String name, Model model) {
		BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("newLine", "\n");
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@RequestParam(value="page") int page, BoardVo boardVo, @AuthUser UserVo userVo, Model model) {
		boardService.editContents(boardVo);	
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("name", userVo.getName());
		model.addAttribute("newLine", "\n");
		return "board/view";
	}
	
	@RequestMapping("/{page}/delete/{no}/{name}")
	public String delete(@PathVariable(value="page") int page, @PathVariable(value="no") Long no, @PathVariable(value="name") String name, Model model) {
		BoardVo boardVo = new BoardVo();
		boardVo.setUserName(name);
		boardVo.setNo(no);
		boardService.deleteContents(boardVo);
		return "redirect:/board/" + page;
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@RequestMapping(value="/{page}/reply/{gno}/{ono}/{depth}/{no}/{name}", method=RequestMethod.GET)
	public String reply(@PathVariable(value="page") int page,
						@PathVariable(value="gno") Long groupNo,
						@PathVariable(value="ono") int orderNo,
						@PathVariable(value="depth") int depth,
						@PathVariable(value="no") Long no,
						@PathVariable(value="name") String name,
						Model model) {
		BoardVo boardVo = new BoardVo();
		boardVo.setGroupNo(groupNo);
		boardVo.setOrderNo(orderNo);
		boardVo.setDepth(depth);
		return "board/reply";
	}
	
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(BoardVo vo) {
		BoardVo parentVo = new BoardVo();
		parentVo.setGroupNo(vo.getGroupNo());
		parentVo.setOrderNo(vo.getOrderNo());
		parentVo.setDepth(vo.getDepth());
		boardService.replyContents(vo, parentVo);
		return "redirect:/board/1";
	}	
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BoardVo vo) {
		boardService.writeContents(vo);
		return "redirect:/board/1";
	}
}
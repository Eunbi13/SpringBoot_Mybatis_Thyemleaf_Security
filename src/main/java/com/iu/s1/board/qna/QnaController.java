package com.iu.s1.board.qna;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.util.Pager;
import com.iu.s1.board.BoardVO;
import com.iu.s1.board.qna.QnaVO;

@Controller
@RequestMapping("/qna/**")
public class QnaController {
	
	@Autowired
	private QnaService qnaService;
	
	
	@ModelAttribute("board")//뭐든지 얘가 제일 먼저 실행됨 //속성의 이름
	public String getBoard() {
		return "qna";//벨류값
	}
	//답글있는 경우 모델어트리뷰트를 하나 더 해서 막,, 있는 글이다 하고 보여주면 된다
	
	@GetMapping("list")
	public String getList(Pager pager, Model model)throws Exception{
		
//		if(pager.getCurPage()%2==0) {
//			throw new SQLException();
//		}
//		
		//model.addAttribute("name", "QNA"); //이렇게 넣을 필요 없이 저 위의 Model이 실행된다는 것
		List<BoardVO> ar = qnaService.getList(pager);
		model.addAttribute("list", ar);
		model.addAttribute("pager", pager);
		return "board/list";
	}
	
	
	@GetMapping("insert")
	public String setInsert(Model model)throws Exception{
		model.addAttribute("vo", new BoardVO());
		model.addAttribute("action", "insert");
		return "board/form";
	}
	
	@GetMapping("select")
	public ModelAndView getSelect(BoardVO boardVO)throws Exception{
		ModelAndView mv = new ModelAndView();
		QnaVO qnaVO = (QnaVO)qnaService.getSelect(boardVO);
		mv.addObject("vo", qnaVO);
		mv.setViewName("board/select");
		return mv;
	}
	
	@PostMapping("insert")
	public String setInsert(BoardVO boardVO, MultipartFile [] files)throws Exception{
		qnaService.setInsert(boardVO, files);
		
		for(MultipartFile f:files) {
			System.out.println(f.getOriginalFilename());
		}
		return "redirect:./list";
	}
	@GetMapping("reply")
	public String setReplyInsert(BoardVO boardVO, Model model)throws Exception{
		model.addAttribute("action", "reply");
		model.addAttribute("vo", boardVO);
		return"board/form";
	}
	@PostMapping("reply")
	public String setReplyInsert(BoardVO boardVO, MultipartFile [] files)throws Exception{
		qnaService.setReplyInsert(boardVO, files);
		
		
		return "redirect:./list";
	}
	@GetMapping("update")
	public String setUpdate(BoardVO boardVO, Model model)throws Exception{
		boardVO = qnaService.getSelect(boardVO);
		model.addAttribute("action", "update");
		model.addAttribute("vo", boardVO);
		return "board/form";
	}
	
	@PostMapping("update")
	public String setUpdate(BoardVO boardVO)throws Exception{
		qnaService.setUpdate(boardVO);
		
		return "redirect:./list";
	}
	
	@GetMapping("delete")
	public String setDelete(BoardVO boardVO)throws Exception{
		qnaService.setDelete(boardVO);
		return "redirect:./list";
	}
	
	
}

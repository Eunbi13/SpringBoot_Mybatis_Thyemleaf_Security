package com.iu.s1.board.notice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.util.Pager;
import com.iu.s1.board.BoardVO;
import com.iu.s1.member.MemberVO;


@Controller
@RequestMapping("/notice/**")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	@Value("${board.notice.filePath}")
	private String filePath;
	
	
	@ModelAttribute("board")//속성의 이름
	public String getBoard() {
		return "notice";//벨류값
	}
	
	//fileDown
	@GetMapping("fileDown")
	public ModelAndView fileDown(String fileName, String oriName) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("fileDown");
		mv.addObject("fileName", fileName);//select.html
		mv.addObject("oriName", oriName);
		mv.addObject("filePath", this.filePath/* "/upload/notice/" */);
	 // view 네임과 일치하는 (AbstractView를 상속받는) 클래스를 먼저 찾고 없으면 fileDown.html을 찾으러 감
		return mv;
	}
	
		
	@GetMapping("list")
	public String getList(Model model, Pager pager)throws Exception{
		System.out.println("filePath: "+filePath);
		
		List<BoardVO> ar = noticeService.getList(pager);
		model.addAttribute("list", ar);
		model.addAttribute("pager", pager);
		return "board/list";
	}
	
	@GetMapping("select")
	public ModelAndView getSelect(BoardVO boardVO)throws Exception{
		ModelAndView mv = new ModelAndView();
		NoticeVO noticeVO = (NoticeVO)noticeService.getSelect(boardVO);
		mv.addObject("vo", noticeVO);
		mv.setViewName("board/select");
		return mv;
	}
	
	@GetMapping("insert")
	public String setInsert(Model model, HttpSession session)throws Exception{
		model.addAttribute("vo", new BoardVO());
		model.addAttribute("action", "insert");
		
		//=========보안 코드======select뺴고 다 체크해야함 그래서 util로 만든다,,? 
		Object obj = session.getAttribute("member");
		String path = "redirect:/member/login";
		MemberVO memberVO=null;
		if(obj instanceof MemberVO) {//!=null
			memberVO = (MemberVO)obj;
			if(memberVO.getUserName().equals("admin")) {
				path = "board/form";
			}
		}
		
		return path;
	}
	
	@PostMapping("insert")
	public String setInsert(BoardVO boardVO, MultipartFile [] files)throws Exception{
		noticeService.setInsert(boardVO, files);
		System.out.println(files.length);
		for(MultipartFile f:files) {
			System.out.println(f.getOriginalFilename());
		}
		return "redirect:./list";
	}
	
	@GetMapping("update")
	public String setUpdate(BoardVO boardVO, Model model)throws Exception{
		boardVO = noticeService.getSelect(boardVO);
		model.addAttribute("action", "update");
		model.addAttribute("vo", boardVO);
		return "board/form";
	}
	
	
	
	@PostMapping("update")
	public String setUpdate(BoardVO boardVO)throws Exception{
		noticeService.setUpdate(boardVO);
		
		return "redirect:./list";
	}
	
	@GetMapping("delete")
	public String setDelete(BoardVO boardVO)throws Exception{
		noticeService.setDelete(boardVO);
		return "redirect:./list";
	}
	
	//=======error=======
	@ExceptionHandler(ArithmeticException.class)
	public String getMath(Model model) {
		//코드 진행
		model.addAttribute("msg", "수학적오류발생");
		return "error/500";
	}
	@ExceptionHandler(Throwable.class)
	public String getException(Model model) {
		model.addAttribute("msg","관리자에게 문의하세요");
		return "error/500";
	}
}

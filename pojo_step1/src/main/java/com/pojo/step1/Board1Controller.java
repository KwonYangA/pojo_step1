package com.pojo.step1;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Board1Controller implements Action1 {

	@Override
	public ActionForward1 execute(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {		
		log.info("execute호출");
		log.info("request :" + req);
		log.info("response :" + res);
		//ActionForward1 af = null; null 초기화 - NullPointerException - 화면처리 안됨
		ActionForward1 af = new ActionForward1();
		String upmu[] = (String[])req.getAttribute("upmu");		
		Board1Logic boardLogic = new Board1Logic();
		//FrontMVC11은 실제 업무를 처리하는 클래스가 X
		//실제 게시판 구현의 마지막 단계는 Board1Controller클래스 이니까
		//여기서 path정보와 리다이렉트 여부를 결정해주는 것이 맞다 - 업무 담당자의 마지막 위치이니까..
		String path = null;
		boolean isRedirect = false;
		//게시글 목록을 출력할거야?
		if("getBoardList".equals(upmu[1])) {
		List<Map<String, Object>> boardList = boardLogic.getBoardList();
		req.setAttribute("boardList", boardList);
		path = "getBoardList.jsp";
		isRedirect = false;

		} 
		else if ("jsonBoardList".equals(upmu[1])) {
			String jsonDoc = boardLogic.jsonBoardList();
			log.info(jsonDoc);
			req.setAttribute("jsonDoc", jsonDoc);
			path = "jsonBoardList.jsp";
			isRedirect = false; 
		}
		else if ("boardInsert".equals(upmu[1])) {
		//insert into board_master_t(bm_no, bm_writer, bm_title, ...) values(?,?,?)		
			int result = boardLogic.boardInsert();
		}
		else if ("boardUpdate".equals(upmu[1])) {
		//update board_master_t set bm_title =?. bm_writer=?, bm_content=?
			int result = boardLogic.boardUpdate();
		}
		else if ("boardDelete".equals(upmu[1])) {
		//delete from board_master_t where bm_no=?
			int result = boardLogic.boardDelete();
		}
		
		af.setPath(path);
		af.setRedirect(isRedirect);
		return af;
	}

	public static void main(String args[]) {
		Board1Controller bc = new Board1Controller();
		try {
			bc.execute(null, null);
		}catch (Exception e){
	 
		}
	}
}



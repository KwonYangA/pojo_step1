package com.pojo.step1;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

//나는 HttpServlet을 상속받았으므로 서블릿이다
//그래서 나는 메소드 파라미터로 request와 response를 받을 수 있다.
@Log4j2
public class FrontMVC11 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Board1Controller bc = new Board1Controller();
	//개발자 입장에서는 get방식 요청이든 post방식 요청이든 모두 처리해야 합니다.
	//그래서 창구를 하나로 통일하려고 doService 사용자 정의메소드를 추가한 것임.
	//이 메소드는 어떤 장애를 가지고 있나? - 톰캣으로부러 req, res를 주입 받을 수 없다.
	//XXX.st1으로 요청하면 doGet만 호출이 가능하고 doPost, doService 호출이X
	//POJO프레임워크는 요청객체와 응답객체에 의존적이다라고 말할 수있다
	//스프링 프레임워크는 요청객체와 응답객체 없이도 모든 서비스가 가능하다
	//없이도 가능하다는 것은 메소드의 파라미터로 주입받는다 라는 걸 의미한다
	//스프링에서는 메소드의 파라미터가 줄었다 늘었다 할 수 있다 - 메소드 오버로딩
	//테스트 URL - 프로토콜:도메인주소:포트번호/작업폴더명/요청이름(http://localhost:9000/___.st1)
	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.info("doService호출");
		// -> board/getBoardList.st1
		String uri = req.getRequestURI(); //주소창에 입력된 값중 도메인과 포트번호가 제외된 값만 담음
		log.info(uri); // /board/getBoardList.st1
		String context = req.getContextPath();// "/" -> server.xml
		log.info(context);
		String command = uri.substring(context.length()+1); 
		System.out.println(command); 
		int end = command.lastIndexOf(".");
		System.out.println(end);
		command = command.substring(0, end);// /board/getBoardList
		System.out.println(command); 
		String upmu[] = null; // upmu[0] = board폴더명 upmu[1] = getBoardList / st1은 제거
		upmu = command.split("/"); 
		
		ActionForward1 af = null;
		Board1Controller boardController = null;	
		//Board1Controller는 servlet이 아니므로 요청객체와 응답객체를 주입받지 못함
		//execute 호출 시 파라미터로 전달하기 - 구조
		//원본이 넘어가는 거니까 거기에 upmu배열을 저장한다
		req.setAttribute("upmu", upmu);
		if("board".equals(upmu[0])) {  
			boardController = new Board1Controller();
		//톰캣서버를 경유했을 때 요청객체와 응답객체를 활용가능함
		//FrontMVC11 경유 해라
			af = boardController.execute(req, res); 
		}
		
		if(af != null) { // 응답시 
			if(af.isRedirect()) {
			res.sendRedirect(af.getPath());
		}else {
			RequestDispatcher view = req.getRequestDispatcher(af.getPath());
			view.forward(req, res);
		}
	}
	}	
	/*****************************************************************************
	 Restful API - GET방식
	 브라우저에 인터셉트 당한다 - 노출(포장X)
	 헤더에 값이 담기므로 제약이 있다
	 첨부파일 처리에 사용불가
	 링크 걸 수 있다
	 단위테스트 가능	
	*****************************************************************************/
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.info("doGet 호출");
		doService(req, res);
	}
	/*****************************************************************************
 	 Restful API - POST방식
 	 링크를 걸 수 없다
 	 단독으로 호출 테스트 불가능 (테스트는 postman으로)
 	 브라우저에 쿼리스트링에 노출이 안됨 - 포장
 	 노출이 안되어서 브라우저에 인터셉트 당하지 X - 모든 요청이 서버로 전달 된다.
	 패킷에 바디부분에 값이 담김 - 용량 제한 X - 첨부파일
	*****************************************************************************/
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//post방식으로 요청이 일어나면 서블릿의 doPost가 받는다
		//이 때 톰캣컨테이너로부터 요청객체와 응답객체를 주입받는다(의존성주입)
		//개발자가 정의한 doService 호출시 파라미터로 주입받은 요청객체와 응답객체를 넘긴다
		log.info("doPost 호출");
		doService(req, res);
	}

}

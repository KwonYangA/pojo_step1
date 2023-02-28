package com.pojo.step1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;

/*
 	모델(Model => XXXLogic + XXXDao) 계층 클래스 설계 부분
 	넓은 의미로 보면 Dao(Data Access Object)도 모델 계층의 일부임
 	오라클 서버와 직접적인 연동은 XXXDao 클래스에게 전담시킴
 	왜냐하면 MyBatis와 같은 ORM솔루션과 연계하려면 독립된 클래스로 설계하는 것이 유리함
 	또 다른 이유로는 Hibernate와 같은 또 다른 솔루션으로 커스터마이징하거나 리펙토링 시에 독립적인
 	클래스 구성이 재조립시 편리하기 때문임
 	XXXLogic 클래스는 업무에 대한 판단, 결정, 선택을 하는 계층에 해당 됨
 	주로 이런 결정권을 가진 자는 팀장, 과장, 차장, 부장임
 	공통된 관심사를 분리하기 위한 목적으로도 로직 클래스 분리는 꼭 필요한 부분임
 	예를 들면 트랜잭션 처리를 코드가 아닌 시스템 레벨에서 자동으로 제어하려면 
 	서비스 계층에 해당되는 클래스는 반드시 분리할 수 있는 독립된 클래스 이어야 함
 	여기서 여러가지의 DAO클래스의 메소드가 다중으로 호출되게 됨에 따라 커밋이나 롤백의 대상이 되는 경우임
 */
@Log4j2
public class Board1Logic{ 
	Board1Dao boardDao = new Board1Dao();

	public List<Map<String, Object>> getBoardList() {
		log.info("getBoardList 호출");
		List<Map<String, Object>> boardList = new ArrayList<>();
		boardList = boardDao.getBoardList();
		return boardList;	
	}

	public String jsonBoardList() {
		log.info("jsonBoardList 호출");
		List<Map<String, Object>> boardList = new ArrayList<>();
		boardList = boardDao.getBoardList();
		String temp = null;
		Gson g = new Gson();
		temp = g.toJson(boardList);
		log.info(temp);
		return temp;
	}

	public int boardInsert() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int boardUpdate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int boardDelete() {
		// TODO Auto-generated method stub
		return 0;
	}	
}

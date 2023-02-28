package com.pojo.step1;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.util.MyBatisCommonFactory;

import lombok.extern.log4j.Log4j2;

/*
 FrontMVC11 -> Board1Controller -> Board1Logic -> Board1Dao -> MyBatis Layer
 MyBatis가 실질적인 코드를 줄여주는 부분은 어디인가요?
 1) 커넥션 연결 - 단 오라클 서버에 대한 정보는 제공해줘야 함
 : 오라클드라이버 클래스
 : 오라클 서버의 URL정보 - 멀티티어에서 유리하나 thin 드라이버 방식 
*/

@Log4j2
public class Board1Dao {
	MyBatisCommonFactory mcf = new MyBatisCommonFactory();
	public List<Map<String, Object>> getBoardList() {
		log.info("getBoardList 호출");
		//여기서 널로 하는 이유는 어차피 로직 클래스에서 인스턴스화를 해두었기 때문임
		//NPE는 발생하지 않을 것임
		SqlSessionFactory sqlSessionFactory = null;
		SqlSession sqlSession = null;
		List<Map<String, Object>> boardList = null;
		try {
		//오라클 서버에 대한 정보를 가진 MyBatisConfig.xml문서를 읽는다 ->
		//MyBatisCommonFactory가 SqlSessionFactory().build(resource)
		//MyBatisCommonFactory에서 처리된 결과를 getter메소드를 통해 주입받음
		//sqlSessionFactory = mcf.getSqlSessionFactory(); 생성된 객체가 제공하는
		//openSession()가 SqlSession객체 생성함
		//sqlSession은 쿼리문 요청이 가능한 insert(), update(), delete(), selectOne, selectMap 등을 제공함 
			sqlSessionFactory = mcf.getSqlSessionFactory();
			sqlSession = sqlSessionFactory.openSession();
			boardList = sqlSession.selectList("getBoardList", null);
			log.info(boardList);
		}catch(Exception e) {
			log.info(e.toString());
		}
		
		return boardList;
	}

}

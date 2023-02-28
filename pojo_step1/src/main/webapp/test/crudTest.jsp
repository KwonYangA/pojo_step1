<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%  
 	String menu = (String)request.getAttribute("menu");
 	String side = (String)request.getAttribute("side");
 	//서블릿에서 주입받은 request에 담겨 있는 것이지 페이지 이동으로 출력된 화면이 가진
 	//request에는 menu담지 않았다
 	//기존의 요청이 끊어지고(url의 변경으로 알수있다) 새로운 요청이 서버에 전달되어서 페이지가 출력된 것임
 	out.print("내가 선택한 메뉴는 :"+menu+", 음료는 :"+side); // null출력 됨 why?			
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>curdTest</title>
</head>
<body>
<br /> crudTest페이지 입니다.
<!-- 
기존의 요청이 끊어지고 새로운 요청이 왔다
서블릿이 호출될 때 톰캣서버로 부터 주입 받은 request객체와 response 객체가
아닌 새로운 request객체와 response객체라는 것이다.
 
 localhost:9000/haha.st1  요청
 -> web.xml ->st1찾아서 -> servlet-name-> servlet -> classname
 get 방식이니까 doGet메소드가 호출되고(누가-톰캣-시스템에서 호출하는 메소드-CallBack)
 그 안에서 doService 호출. 이때 파라미터로 톰캣서버에서 주입받은 req와 res를 넘겨준다  
 -->
</body>
</html>
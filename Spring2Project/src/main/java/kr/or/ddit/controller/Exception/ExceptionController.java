package kr.or.ddit.controller.Exception;

public class ExceptionController {

/*
 	16장 예외처리
 	
 	1. 예외처리

		일반적으로 프로그램이 처리되는 동안 특정한 문제가 일어났을 때
		처리를 중단하고 다른 처리를 하는 것을 예외처리라고 한다.
		
		웹 컨테이너는 기본적으로 예외처리를 하여 기본 에러 페이지를 표시해준다.
		하지만 페이지에 애플리케이션 서버의 내부 정보가 일반 사용자들에게 노출되어
		프레임워크의 보안 취약점을 노린 공격을 받을 수 있다.
		
		이런 점을 고려하여 최대한 사용자가 직접 예외를 처리하여 사용자가 정의한 에러 페이지를 표시하게 해야 한다.
		
		1-1) 예외 종류
		- 스프링 프레임워크 예외
		- 사용자 정의 예외
		- 의존 라이브러리에서 발생한 예외
		- 시스템 예외
		
		1-2) 가상의 예외 상황 설정
		- A. 수정 화면 생성하고 뷰 파일에서 예외 발생
		- B. 삭제 할 때 매핑 파일에서 예외 발생
		- C. 존재하지 않는 게시물을 조회할 때 사용자가 정의한 예외 발생
		- D. 존재하지 않는 페이지 URL요청시 예외 발생 
		
	2. 예외 상황
	
		2-1) 예외 발생 상황
			- 수정 화면 생성하고 뷰 파일에서 예외 발생
				> Http Status 500
				> /views/crud/register.jsp <form:input path="title2"/>로 수정
				
			- 삭제할 때 매핑 파일에서 예외 발생
				> Http Status 500
				> sqlmap/boardMapper_SQL.xml id가 delete인 삭제 기능을 가진 태그에서
				  board_no를 board_no2로 수정
			
			- 존재하지 않는 게시물을 조회할 때 사용자가 정의한 예외 발생
				> Http Status 500
				> kr.or.ddit.service.impl.BoardServiceImpl() read() 수정
					new BoardRecordNotFoundException() 예외 발생
					
			- 존재하지 않는 페이지 URL요청 시 예외 발생
				> Http Status 404
				> /crud/board/retry없는 요청 URL을 요청하여 예외 발생
	
	3. 상태코드 사용한 에러 페이지 설정
	 	- 웹 컨테이너 설정 파일(web.xml)의 <error-code>요소에 상태코드를 설정하고 
	 	  <location>요소에 이동 대상 페이지를 지정한다.
	 	  
	 	    예외 처리 방법
	 	    - 웹 컨테이너 설정(web.xml)
	 	    	> <error-page></error-page> 400코드 설정
	 	    	> <error-page></error-page> 404코드 설정
	 	    	> <error-page></error-page> 500코드 설정
	 	    	
	 	    - 처리할 수 있는 예외
	 	    	> 수정화면 생성할 때 뷰 파일에서 예외 발생
	 	    	> 삭제할 때 매핑 파일에서 예외 발생
	 	    	> 존재하지 않는 게시물을 조회할 때 사용자가 정의한 예외 발생
	 	    	> 존재하지 않는 페이지 URL요청시 예외 발생 

	 	    - 처리할 수 없는 예외
	 	    
	 4. 예외 타입 사용한 에러 페이지 설정
	 	    - 웹 컨테이너 설정 파일(web.xml)의 <exception-type>요소에 예외 타입을 설정하고
	 	      <location> 요소에 이동 대상 페이지를 지정한다.
	 	      
	 	          예외처리 방법
	 	        - 웹 컨테이너 설정(web.xml)
	 	           > <error-page></error-page>
	 	           
	 	        - 에러 종류 : Exception 타입
	 	        
	 	     	- 처리할 수 있는 예외
	 	        	> 수정화면 생성할 때 뷰 파일에서 예외 발생
	 	    		> 삭제할 때 매핑 파일에서 예외 발생
	 	    		> 존재하지 않는 게시물을 조회할 때 사용자가 정의한 예외 발생
	 	    	
	 	    	- 처리할 수 없는 예외
	 	    		> 존재하지 않는 페이지 URL요청시 예외 발생 
	
	  5. 기본 에러 페이지 설정
	  		- 웹 컨테이너 설정 파일(web.xml)의 <location>요소만 지정해 <error-page> 요소를 정의한다.
	  		
	  		예외 처리방법
	  		- 웹 컨테이너 설정(web.xml)
	  			> <error-page></error-page>
	  			> 기본 이동 대상의 설정
	  			> 에러 페이지를 jsp파일 절대 경로로 설정
	  			> Controller에서 선언한 URL매핑 정보로도 설정 가능
	  			> 서블릿 3.1이상에서 지원
	  		
	  		- 처리할 수 있는 예외
	  			> 수정화면 생성할 때 뷰 파일에서 예외 발생
	 	    	> 삭제할 때 매핑 파일에서 예외 발생
	 	    	> 존재하지 않는 게시물을 조회할 때 사용자가 정의한 예외 발생

	  		- 처리할 수 없는 예외	
	 	    	> 존재하지 않는 페이지 URL요청시 예외 발생 
	  
	  	6. 예외 처리 어노테이션
	  		- @ExceptionHandle과 @ControllerAdvice를 이용하여 처리한다.
	  		
	  		예외처리 방법
	  		- @ControllerAdvice 어노테이션은 스프링 컨트롤러에서 발생하는 예외를 처리하는 핸들러 클래스임을 명시한다.
	  		- @ExceptionHandle 어노테이션은 괄호 안에 설정한 예외 타입을 해당 메서드가 처리한다는 것을 의미한다.
	  		
	  		예외 처리 핸들러 생성
	  		- kr.or.ddit.controller.exception.CommonExceptionHandler클래스 생성

	  		- 처리할 수 있는 예외
	 	    	> 존재하지 않는 게시물을 조회할 때 사용자가 정의한 예외 발생
	 	    	> 삭제할 때 매핑 파일에서 예외 발생

	  		- 처리할 수 없는 예외	
	  			> 수정화면 생성할 때 뷰 파일에서 예외 발생
	  				> ServletException을 상속받는 JasperException 에러 발생이므로 Exception 처리 안함
	 	    	> 존재하지 않는 페이지 URL요청시 예외 발생 
	 	    	
	 	 7. 예외 정보 출력
	 	    	- 예외에 대한 내용을 Model객체를 이용해 전달하여 뷰 화면에서 출력이 가능하다.
	 	    	
	 	    	예외처리 방법
	 	    	- CommonExceptionHandler클래스에서 에러 정보를 Model을 이용하여 페이지로 던져준다.
	 	
		  8. 404 에러 페이지 처리
		  - web.xml 설정을 통해 처리할 수 있다.
		  
		  		예외 처리 방법
		  		- 웹 컨테이너 설정
		  			> 404에러를 처리할 수 있도록
		  			DispatcherServlet의 throwExceptionIfNoHandlerFound속성을 true로 설정

				처리할 수 있는 예외
	 	    		> 존재하지 않는 페이지 URL요청시 예외 발생 
				
				처리할 수 없는 예외
		  			> 수정화면 생성할 때 뷰 파일에서 예외 발생
		 	    	> 삭제할 때 매핑 파일에서 예외 발생
		 	    	> 존재하지 않는 게시물을 조회할 때 사용자가 정의한 예외 발생
		 	    	
		 9. 입력값 검증 예외 처리
		 - @Validated 어노테이션을 사용하면 Bean Validation의 유효성 검증 매커니즘을 이용할 수 있다.
		      	
		      	예외 처리 방법
		      	- 입력값 검증 기능의 활성화와 BindingResult 정의
		      		> 입력값 검증 대상인 자바 빈즈 메서드 매개변수에 @Validated어노테이션을 지정하고
		      		    바로 다음에 BindingResult를 정의한다.
		      		  BindingResult에는 요청 데이터의 바인딩 에러와 검사 에러 정보가 저장된다.  
 */
	
}











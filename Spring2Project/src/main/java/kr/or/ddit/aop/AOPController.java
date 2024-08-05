package kr.or.ddit.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// @Component는 스프링 빈으로 등록하기 위한 어노테이션
// @Aspect어노테이션을 붙여 이 클래스가 Aspect를 나타내는 클래스라는 걸 명시
// AOPController클래스 빈 등록시 aOPController로 할지 aopController로 할지가 명확하지 않을 수 있으므로
// aopController라는 이름을 명시해준다.
@Slf4j
@Component("aopController")
@Aspect
public class AOPController {
	/*
	 *  14장 AOP
	 * 
	 *  1. AOP 설명
	 *  	[ AOP란? 예시 ] 
	 *  	개발 중인 서비스 처리속도를 로그로 남겨달라는 상부의 업무 지시에 
	 *  	사원은 서비스 로직에서 처리속도를 찍어볼 메서드를 개발해 확인했다.
	 *  	이를 서비스 전체에 찍어야한다고 추가 지시에 만들어낸 메서드를 각 기능별 서비스 로직에 하나씩 작성하던 중 의문을 가질 수 있다.
	 *  	
	 *  	서비스 로직에서 본래의 기능이 중요하고, 지금 작성하고 있는 로직은 옵션(부가기능)인데,
	 *  	이걸 하나의 묶음으로는 처리가 불가능한가?
	 *  	
	 *  	시간을 측정하고 권한을 체크하는 동의 기능은 옵션과 같은 부가기능으로 일종의 인프라 로직이라고 한다.
	 *  	이 인프라 로직은 애플리케이션 전 영역에서 나타날 수 있고 중복 코드를 발생시켜 개발의 효율성을 저하시키고 유지보수를 어렵게 만든다.
	 *  
	 *  	이러한 인프라 로직은 하나의 관심사를 가지며 이 관심사들의 중복이 횡단으로 나타나는 현상을
	 *  	횡단 관심사(Cross-Cutting Concern)이라고 한다.
	 *  
	 *  	┌───────────────────────────────────────────────────────────────┐
	 *  	│ [처리속도 측정]     [처리속도 측정]     [처리속도 측정]    [처리속도 측정]  │
	 *  	└───────────────────────────────────────────────────────────────┘
	 *  	  [비즈니스 로직]     [비즈니스 로직]     [비즈니스 로직]    [비즈니스 로직]
	 *  	  [처리내용 로깅]     [처리내용 로깅]     [처리내용 로깅]    [처리내용 로깅]
	 *  	└───────────────────────────────────────────────────────────────┘
	 *  		로그인 기능		회원가입 기능		게시판 목록		게시판 등록
	 *  
	 *  	이러한 횡단 관심사를 통해 프로그래밍하는 것을 AOP라고 한다.
	 *  	
	 *  	# 간단하게 보고 넘어가기
	 *  		- Aspect
	 *  			: AOP의 단위가 되는 횡단 관심사
	 *  		- 횡단 관심사(Cross-Cutting Concern)
	 *              : 핵심(Core) 비즈니스 로직과 다소 거리가 있지만
	 *                에러 모듈에서 공통적이고 반복적인 처리를 요구하는 내용
	 *                (ex.메서드 실행 시작 시간 출력, 메서드 처리 후 시간 출력 등)
	 *  		- @Component
	 *  			: @Aspect와 짝꿍
	 *              : component-scan시 "저 여기있어요. 여기 봐주세요"의 의미 ?
	 *  		- JoinPoint
	 *  			: 어드바이스가 적용될 수 있는 위치
	 *  		- Advice
	 *  			: 어떤 부가기능을 언제 사용할지 정의
	 *  			 > Advice(부가기능)는 타겟을 감싸서 위장된 프록시가 실행되기 위한 시점에 따라 옵션이 나뉘어진다.
	 *  			 > 시점별 옵션
	 *  				- Before : 조인포인트 전에 실행
	 *  				- After : 조인포인트에서 처리가 완료된 후 실행
	 *  				- After Returning : 조인포인트가 정상적으로 종류 후 실행
	 *  				- After Throwing : 조인포인트에서 예외 발생시 실행. 예외가 발생 안되면 실행 안 함
	 *  				- Around : 조인 포인트 전후에 실행
	 *  
	 *  		# AOP(관점 지향 프로그래밍 ::: Aspect Oriented Programming)
	 *  		- 관점 지향 프로그래밍을 의미하는 약자이다.
	 *  
	 *  		1-1) 관점 지향 프로그래밍
	 *  			  소스 코드의 여기저기에 흩어져 있는 횡단 관심사를 중심으로 설계와 구현을 하는 프로그래밍 기법이다.
	 *  			  즉 관점 지향 프로그래밍은 횡단 관심사의 분리를 실행하는 방법이다. 
	 *  
	 *  			 - 횡단 관심사(Cross-Cutting Concern)
	 *  				> 핵심 비즈니스 로직과 다소 거리가 있지만 여러 모듈에서 공통적이고 반복적인 처리를 요구하는 내용
	 *  			 - 횡단 관심사의 분리(Separation of Cross-Cutting-Concern)
	 *  				> 애플리케이션을 개발할 때 횡단 관심사에 해당하는 부분을 분리해서 한곳으로 모으는 것을 으미ㅣ
	 *  
	 *  		1-2) AOP 개발 순서
	 *  			(1) 핵심 비즈니스 로직에만 근거해서 코드를 작성한다.
	 *  			(2) 주변로직에 해당하는 관심사들을 분리해서 따로 작성한다.
	 *  			(3) 핵심 비즈니스 로직 대상 객체에 어떤 관심사들을 결합할 것인지를 설정한다.
	 *  
	 *  		1-3) AOP 사용 예
	 *  			- 로깅
	 *  			- 보안 적용
	 *  			- 트랜잭션 관리
	 *  			- 예외 처리
	 *  
	 *  		1-4) AOP 관련 용어
	 *  			- Aspect
	 *  				: AOP의 단위가 되는 횡단 관심사에 해당한다.
	 *  			- 조인포인트(JoinPoint)
	 *  				: 횡단 관심사가 실행될 지점이나 시점(메서드 실행이나 예외 발생 등)을 말한다.
	 *  				: 어디에 적용할 것인지 결정, 메서드/객체생성시/필드접근시 등등
	 *  			- 어드바이스(Advice)
	 *  				: 특정 조인 포인트에서 실행되는 코드로 횡단 관심사를 실제로 구현해서 처리하는 부분이다.
	 *  				: 어떤 부가기능을 구현할 것인지 결정
	 *  				: 종류 : Before, AfterReturning, AfterThrowing, After, Around
	 *  			- 포인트컷(PointCut)
	 *  				: 수많은 조인포인트 중에서 실제로 어드바이스를 적용할 곳을 설정하기 위한 표현식을 말함.
	 *  				: 어드바이스가 적용될 지점을 작성함
	 *  			- 위빙(Weaving) 
	 *  				: 애플리케이션 코드의 적절한 지점에 Aspect를 적용하는 것을 말한다.
	 *  			- 타켓 (Target)
	 *  				: AOP처리에 의해 처리 흐름에 변화가 생긴 객체를 말한다.
	 *  				: 어떤 대상에 대해서 부가 기능을 설정할 것인지 결정
	 *  
	 *  		1-5) 스프링 지원 어드바이스 유형(부가기능)
	 *  			- Before
	 *  				: 조인 포인트 전에 실행된다.
	 *  				: 예외가 발생하는 경우만 제외하고 항상 실행된다.
	 *  			- After Returning
	 *  				: 조인 포인트가 정상적으로 종류한 후에 실행된다.
	 *  				: 예외가 발생하면 실행하지 않는다.
	 *  			- After Throwing
	 *  				: 조인 포인트에서 예외가 발생했을 때 실행된다.
	 *  				: 예외가 발생하지 않고 정상적으로 종료하면 실행되지 않는다.
	 *  			- After
	 *  				: 조인 포인트에서 처리가 완료된 후 실행된다.
	 *  				: 예외 발생이나 정상 종료 여부와 상관없이 항상 실행된다.
	 *  			- Around
	 *  				: 조인 포인트 전후에 실행된다.
	 *  
	 *  		1-6) AOP의 기능을 활용하기 위한 설정
	 *  			- 의존관계 등록(pom.xml)
	 *  				> aspectjrt (이미 등록되어 있음)
	 *  				> aspectjweaver
	 *  
	 *  			- 스프링 AOP설정
	 *  				> root-context.xml 설정
	 *  				> AOP를 활성화하기 위한 태그를 작성한다. 
	 *  
	 *  2. 포인트 컷 표현식
	 *  	- execution 지시자
	 *  
	 *  	# 포인트 컷 (PointCut)
	 *  		- Advice가 실행된 지점을 표현하는 표현식
	 *  
	 *  		2-1) execution 지시자의 표현 방법
	 *  		- execution 지시자를 활용해 포인트컷을 표현한 것이다.
	 *  		- 포인트 컷 표현 요소
	 *  		예) execution(Board kr.or.ddit.service.IBoardService.BoardService*.read*(..))
	 *  		────────────────────────────────────
				           표현요소                            │       설명
	 *  		────────────────────────────────────
	 *  		execution			   │     지시자
	 *  		Board                  │     반환값
	 *  		kr.or.ddit.service     │     패키지
	 *  		BoardService*          │     클래스(타입)
	 *  		read*                  │	  메서드
	 *  		(..)                   │     인수, 파라미터
	 *  
	 *  		2-2) 포인트컷 표현식에 사용되는 와일드카드
	 *  		───────────────────────────────────────────────
	 *			      와일드 카드       │       설명
	 *  		───────────────────────────────────────────────
	 *  			  *		   │ 임의의 패키지 1개 계층을 의미하거나 임의의 인수 1개를 의미한다.
	 *  		      ..       │ 임의의 패키지 0개 이상 계층을 의미하거나 임의의 인수 0개 이상을 의미한다.
	 *  	 	      +        │ 클래스명 뒤에 붙여 쓰며 해당 클래스와 해당 클래스의 서브 클래스, 혹은 구현 클래스 모두를 의미한다.
	 *  		
	 *  		2-3) 포인트컷 표현식을 적용한 모습
	 *  		@Before("execution(* kr.or.ddit.service.IBoardService.BoardService*.*(..))")
	 *  	←   public void startLog(JoinPoint jp){
	 *   			log.info("startLog : " + jp.getSignatrue()); 
	 *   		}
	 *   
	 *   # AOP 프록시
	 *   		클라이언트가 요청한 요청을 타겟이 받기 전 타겟인 것처럼 위장해서 받는다.
	 *   		쉽게 대리자 개념인데 타겟으로 향하는 요청을 중간에서 대리자인 프록시가 받아 선 처리 후 타겟에게 다시 요청을 던져준다.
	 *   		응답으로 나가는 부분을 잡아야하는 처리가 필요한 경우에는 응답을 잡아 처리 후 최종 응답을 내보낸다.
	 *   
	 *   		[클라이언트] >> [프록시] >> [타켓]
	 */ 		
	
	/*
	 * 3. Before 어드바이스
	 * 		- 조인포인트 전에 실행된다.
	 * 		- 예외가 발생하는 경우만 제외하고 항상 실행된다.
	 */
	@Before("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("[@Before] startLog : ");
		// getSignature() : 어떤 클래스의 어떤 메서드가 실행되었는지 보여줌. 파라미터 타입은 무엇인지 보여줌
		log.info("[@Before] startLog : " + jp.getSignature());
		// getArgs() : 전달된 파라미터 정보를 보여준다.
		// 예) [BoardVO [boardNo=127, title=개똥이]]
		log.info("[@Before] startLog : " + Arrays.toString(jp.getArgs()));
		
		// 8. 메서드 정보 획득시 사용
		// 프록시가 입혀지기 전의 원본 대상 객체를 가져온다.
		Object targetObject = jp.getTarget();
		log.info("targetObject : " + targetObject);
		
		// 프록시를 가져온다.
		Object thisObject = jp.getThis();
		log.info("thisObject :  " + thisObject);
		
		// 인수를 가져온다.
		Object[] args = jp.getArgs();
		log.info("args.length : " + args.length);
		for(int i = 0; i < args.length; i++) {
			log.info("args["+i+"] :" + args[i]);
		}
	}	
	
	/*
	 * 4. After Returning 어드바이스
	 * 		- 조인포인트가 정상적으로 종료한 후에 실행된다. 예외가 발생하면 실행되지 않는다.
	 */
	@AfterReturning("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void logReturning(JoinPoint jp) {
		log.info("[@AfterReturning] logReturning : ");
		log.info("[@AfterReturning] logReturning : " + jp.getSignature());
	}
	
	/*
	 * 5. After Throwing 어드바이스
	 * 		- 조인 포인트에서 예외가 발생했을 때 실행된다. 예외가 발생하지 않고 정상적으로 종료하면 실행되지 않는다.
	 * 
	 * 		예) crud board의 delete쿼리 중 'no = 2'를 'no 2 = '으로 의도적 에러를 내서 진행
	 */
	@AfterThrowing(pointcut = "execution(* kr.or.ddit.service.IBoardService.*(..))", 
			   throwing = "e")	
	public void logException(JoinPoint jp, Exception e) {
		log.info("[@AfterThrowing] logException : ");
		log.info("[@AfterThrowing] logException : " + jp.getSignature());
		log.info("[@AfterThrowing] logException : " + e);
	}
	
	/*
	 * 6. After 어드바이스
	 * 		- 조인 포인트에서 처리가 완료된 후 실행된다.
	 */
	@After("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("[@After] endLog : ");
		log.info("[@After] endLog : " + jp.getSignature());
		log.info("[@After] endLog : " + Arrays.toString(jp.getArgs()));
	}
	
	/*
	 * 7. Around 어드바이스
	 * 		- 조인 포인트 전후에 실행된다.
	 * 
	 *   	- ProceedingJoinPoint
	 *   	: around 어드바이스에서 사용함
	 *   
	 *   	스프링 프레임워크가 컨트롤하고 있는 비즈니스 로직 호출을 가로챈다.
	 *   	책임이 around어드바이스로 전가되는데 비즈니스 메서드에 대한 정보를 around어드바이스 메서드가 가지고 있어야 하며
	 *   	그 정보를 스프링 컨테이너가 around 어드바이스 메서드로 넘겨주면 ProceedingJoinPoint객체로 받아서
	 *   	around어드바이스가 컨트롤시 활용한다.
	 */
	@Around("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		// 메서드 실행 직전 시간 체킹
		long startTime = System.currentTimeMillis();
		log.info("[@Around] timeLog : " + Arrays.toString(pjp.getArgs()));
		
		// 메서드 실행(타켓)
		Object result = pjp.proceed();
		
		// 메서드 실행 직후 시간 체킹
		long endTime = System.currentTimeMillis();
		log.info("[@Around] timeLog : " + Arrays.toString(pjp.getArgs()));
		
		// 직후 시간 - 직전 시간 = 메서드 실행 시간
		log.info("[@Around] timeLog : " + pjp.getSignature().getName() 
					+ "(메서드 실행시간) : " + (endTime - startTime));
		return result;
	}
	
	/*
	 * 8. 메서드 정보 획득
	 * 	- @Before 어노테이션 붙은 메서드는 JoinPoint라는 매개변수를 통해 실행 중인 메서드의 정보를 구할 수 있다.
	 */
	
}

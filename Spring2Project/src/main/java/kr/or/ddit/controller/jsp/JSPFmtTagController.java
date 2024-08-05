package kr.or.ddit.controller.jsp;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/fmttag")
public class JSPFmtTagController {
	/*
	 * 		7. 숫자 및 날짜 포맷팅 처리 태그
	 * 		  - 숫자 및 날짜의 포맷팅 관련 태그
	 * 		  - 문자열을 숫자로, 문자열을 날짜로 변형하여 사용한다.
	 * 
	 * 		ex)
	 * 		Date, 숫자
	 * 		  - 2024년 5월 21일 14시 20분 10초
	 * 		  - 12,392,849,348
	 * 
	 * 	   1) <fmt:formatNumber>
	 * 			: 숫자 형식화
	 * 		
	 * 		  속성          |    타입     		   	 |    설명
	 *    ──────────────────────────────────────────────────────
	 *     value   	 |  String of Number | 서식에 맞춰 출력할 숫자 
	 *     type      |  String    	   	 | 어떤 서식으로 출력할지 정한다.
	 *     pattern   |  String 		  	 | 직접 숫자를 출력할 서식을 지정한다.
	 *     var       |  String 		     | 포맷팅한 결과를 지정할 변수 이름 
	 *    ──────────────────────────────────────────────────────
	 *    ** type 속성 : number일 경우 숫자 형식으로, percent일 경우 % 형식으로,
	 *    	 		    currency일 경우 통화 형식으로 출력한다.
	 *    	 기본값 -- number / currency는 속해있는 국가코드에 맞는 통화형식이 부여된다.
	 *    
	 *    2) <fmt:parseNumber>
	 *         : 문자열을 숫자로 변환한다.
	 *         
	 * 		  속성          |    타입     		   	 |    설명
	 *    ─────────────────────────────────────────────────────────
	 *     value   	 |  String			 | 파싱할 문자열
	 *     type      |  String    	   	 | value속성의 문자열 타입을 지정    
	 *     pattern   |  String    	   	 | 파싱할 때 직접 사용할 서식을 지정    
	 *     var       |  String    	   	 | 파싱한 결과를 저장할 변수 이름을 지정    
	 *    ─────────────────────────────────────────────────────────
	 *    ** type 속성 : number, currency, percent가 올 수 있다.
	 *    
	 *    
	 *    3) <fmt:formatDate>
	 *       : Date 객체를 문자열로 변환한다. 
	 *   	  속성          |    타입     		  	 |    설명
	 *    ─────────────────────────────────────────────────────────
	 *     value   	 |  java.util.Date	 | 포맷팅할 날짜 및 시간 값
	 *     type   	 |  String           | 날짜, 시간 또는 둘다 포맷팅할지의 여부 지정
	 *     dateStyle |  String           | 날짜에 대해 미리 정의된 포맷팅 스타일을 지정
	 *     timeStyle |  String           | 시간에 대해 미리 정의된 포맷팅 스타일을 지정
	 *     pattern   |  String           | 파싱할 때 직접 사용할 서식을 지정
	 *     var       |  String           | 파싱할 결과를 저장할 변수 이름을 지정
	 *    ─────────────────────────────────────────────────────────
	 *    ** type 속성 : time, date, both 중 한 가지 값을 가질 수 있으며 기본값은 date이다.
	 *    dateStyle속성 : default, short, medium, long, full 중 한 가지 값을 가질 수 있고
	 *    기본값은 default이다.
	 *    timeStyle속성 : default, short, medium, long, full 중 한 가지 값을 가질 수 있고
	 *    기본값은 default이다.
	 *    
	 *    
	 *    4) <fmt:parseDate>
	 *      : 문자열을 Date 객체로 변환한다.
	 *       속성          |    타입        	|    설명
	 *    ─────────────────────────────────────────────────────────
	 *     value   	 |  String      | 파싱할 문자열
	 *     type   	 |  String      | 날짜, 시간 혹은 둘다를 포맷팅할지의 여부 지정
	 *     dateStyle |  String      | 날짜에 대해 미리 정의된 포맷팅 스타일을 지정
	 *     timeStyle |  String      | 시간에 대해 미리 정의된 포맷팅 스타일을 지정
	 *     pattern   |  String      | 파싱할 때 직접 사용할 서식을 지정
	 *     var       |  String      | 파싱할 결과를 저장할 변수 이름을 지정
	 *    ─────────────────────────────────────────────────────────
	 *    ** type 속성 : time, date, both 중 한 가지 값을 가질 수 있다. (기본값: date)
	 *    ** dateStyle속성 : default, short, medium, long, full 중 한 가지 값을 가질 수 있다. (기본값 : default)
	 *    ** timeStyle속성 : default, short, medium, long, full 중 한 가지 값을 가질 수 있다. (기본값 : default)
	 *      
	 */
	// type속성을 지정하거나 pattern속성을 지정하여 숫자를 형식화한다.
	@RequestMapping(value="/home0101", method=RequestMethod.GET)
	public String home0101(Model model) {
		int coin = 100;
		model.addAttribute("coin", coin);
		return "home/fmttag/home0101";
	}
	
	// 2) type속성이 지정되지 않으면 기본값은 number이다.
	@RequestMapping(value="/home0201", method=RequestMethod.GET)
	public String home0201(Model model) {
		String coin = "1000";
		model.addAttribute("coin", coin);
		return "home/fmttag/home0201";
	}
	
	// 3) type속성이 currency이다.
	// 만약, type속성이 percent인 경우엔 넘겨받아야할 값이 '1000%'와 같이 %의 스타일 형태 값이 넘어가야 한다.
	// 그래서 type속성과 일치하여 값을 파실할 수 있다.
	@RequestMapping(value="/home0202", method=RequestMethod.GET)
	public String home0202(Model model) {
		String coin = "￦1000";
		model.addAttribute("coin", coin);
		return "home/fmttag/home0202";
	}
	
	// 4) pattern 속성을 사용하여 직접 사용할 서식을 지정한다.
	@RequestMapping(value="/home0204", method=RequestMethod.GET)
	public String home0204(Model model) {
		String coin = "1,000.25";
		model.addAttribute("coin", coin);
		return "home/fmttag/home0204";
	}
	
	// 6) type속성을 date로 지정하여 날짜 포맷팅을 한다.
	// 7) type속성을 time으로 지정하여 시간 포맷팅을 한다.
	// 8) type속성을 both로 지정하여 날짜, 시간 둘다 포맷팅을 한다.
	@RequestMapping(value="/home0301", method=RequestMethod.GET)
	public String home0301(Model model) {
		Date date = new Date();
		model.addAttribute("now", date);
		return "home/fmttag/home0301";
	}
	
	// 9) pattern 속성을 지정하여 날짜를 포맷팅한다.
	@RequestMapping(value="/home0304", method=RequestMethod.GET)
	public String home0304(Model model) {
		Date date = new Date();
		model.addAttribute("now", date);
		return "home/fmttag/home0304";
	}
	
	// 11) dateStyle속성을 각 스타일로 지정하면 문자열을 Date객체로 변환한다.
	@RequestMapping(value="/home0401", method=RequestMethod.GET)
	public String home0401(Model model) {
		String dateValueShort = "20. 2. 1";  	    // style short형태로 지정
		String dateValueMedium = "2020. 2. 1"; 		// style medium형태로 지정
		String dateValueLong = "2019년 2월 1일 (금)";  // style long 형태로 지정
		String dateValueFull = "2019년 2월 1일 금요일"; // style full 형태로 지정
		
		model.addAttribute("dateValueShort", dateValueShort);
		model.addAttribute("dateValueMedium", dateValueMedium);
		model.addAttribute("dateValueLong", dateValueLong);
		model.addAttribute("dateValueFull", dateValueFull);
		return "home/fmttag/home0401";
	}
	
	
	
	
	
}

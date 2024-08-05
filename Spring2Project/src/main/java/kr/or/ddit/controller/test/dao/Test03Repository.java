package kr.or.ddit.controller.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.controller.test.vo.StudentVO;

@Repository
public class Test03Repository {
	
	// 305호 학생 정보를 담을 리스트
	private List<StudentVO> studentList = new ArrayList<StudentVO>();
	
	private StudentVO student = new StudentVO();
	
	
	public Test03Repository() {
		// StudentVO를 활용하여 305호 학생 5명을 초기화해주세요.
		// # 첫번째 학생
		// 	아이디 : 001
		//  비밀번호 : 1234
		//  이름 : 홍길동
		//  핸드폰번호 : 010-1234-1234
		//  이메일 : a001@naver.com과 같은 데이터로 학생 총 5명의 데이터를 초기화한 후
		//  studentList에 추가해주세요.
		StudentVO student1 = new StudentVO();
		student1.setMemId("001");
		student1.setMemPw("1234");
		student1.setMemName("홍길동");
		student1.setMemPhone("010-1234-1234");
		student1.setMemEmail("a001@naver.com");

		StudentVO student2 = new StudentVO();
		student2.setMemId("002");
		student2.setMemPw("1234");
		student2.setMemName("홍이일");
		student2.setMemPhone("010-1235-1235");
		student2.setMemEmail("a002@naver.com");

		StudentVO student3 = new StudentVO();
		student3.setMemId("003");
		student3.setMemPw("1234");
		student3.setMemName("홍이이");
		student3.setMemPhone("010-1236-1236");
		student3.setMemEmail("a003@naver.com");

		StudentVO student4 = new StudentVO();
		student4.setMemId("004");
		student4.setMemPw("1234");
		student4.setMemName("홍이삼");
		student4.setMemPhone("010-1237-1237");
		student4.setMemEmail("a004@naver.com");

		StudentVO student5 = new StudentVO();
		student5.setMemId("005");
		student5.setMemPw("1234");
		student5.setMemName("홍이오");
		student5.setMemPhone("010-1237-1237");
		student5.setMemEmail("a004@naver.com");
		
		studentList.add(0, student1);
		studentList.add(1, student2);
		studentList.add(2, student3);
		studentList.add(3, student4);
		studentList.add(4, student5);
		
	}
	
	// 기능 구현
	// 1) 학생 전체 가져오기
	public List<StudentVO> getStudentAll() {
		return studentList;
	}
	
	// 2) 학생 한명 정보 가져오기
	public StudentVO getStudentOne(String memId) {

		for (StudentVO stu : studentList) {
			if(stu.getMemId() == memId) {
				student = stu;
			}
		}
		return student;
	}
	
	// 3) 이름, 이메일 정보를 활용해 학생 아이디 가져오기
	public String getStudentIdByNameNMail(String memName, String memEmail) {
		
		for (StudentVO stu : studentList) {
			if(stu.getMemName().equals(memName)&& stu.getMemEmail().equals(memEmail)) {
				student = stu;
			}
		}
		return student.getMemId();
	}
	
	// 4) 아이디, 이름, 이메일 정보를 활용해 학생 비밀번호 가져오기
	public String getPasswordBy(String memId, String memName, String memEmail) {
		
		for (StudentVO stu : studentList) {
			if(stu.getMemId().equals(memId) && stu.getMemName().equals(memName) 
					&& stu.getMemEmail().equals(memEmail)) {
				student = stu;
			}
		}
		
		return student.getMemPw();
		
	}
	
	// 등등 필요에 의한 기능을 구현해주세요.
	// 위 4가지 기능은 필수.
	
}

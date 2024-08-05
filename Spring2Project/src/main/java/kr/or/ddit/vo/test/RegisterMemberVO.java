package kr.or.ddit.vo.test;

import java.util.Date;
import java.util.List;

import kr.or.ddit.vo.Address;
import kr.or.ddit.vo.Card;
import lombok.Data;

@Data
public class RegisterMemberVO {
	
	private String userId;
	private String password;
	private String username;
	private String email;
	private Date dateOfbirth;
	private String gender;
	private String developer;
	private boolean foreigner;
	private String nationality;
	private String[] cars;
	private String[] hobbies;
	
	private Address address;
	private List<Card> cardList;
	
	private String introduction;

}

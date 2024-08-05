package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Member {
	
   @NotBlank(message = "채민아 아이디 입력해야돼")
   private String userId = "a001";
   @NotBlank(message = "채민아 이름 입력해야돼")
   @Size(max = 3)
   private String userName = "hongkd";
   @NotBlank
   private String password = "1234";
   private int coin = 100;
   @Past
   @DateTimeFormat(pattern = "yyyy-MM-dd") // input요소에 형태가 "-"으로 들어가기 때문에 pattern을 지정해줘야함 default가 /로 들어가기 때문에
   private Date dateOfBirth;
   
   @Email
   private String email;
   private String gender;
   private String hobby;
   private String[] hobbyArray;
   private List<String> hobbyList;
   private boolean foreigner;
   private String developer;
   private String nationality;
   
   @Valid
   private Address address;
   
   @Valid
   private List<Card> cardList;
   
   private String cars;
   private String[] carArray;
   private List<String> carList;

   private String introduction;
   private String birthDay;
   private MultipartFile file;
   
}
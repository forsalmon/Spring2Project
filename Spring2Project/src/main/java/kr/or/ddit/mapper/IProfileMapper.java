package kr.or.ddit.mapper;

import kr.or.ddit.vo.crud.NoticeMemberVO;

public interface IProfileMapper {

	public NoticeMemberVO profileIdCheck(String memId);
	public int profileUpdate(NoticeMemberVO memberVO);
	public NoticeMemberVO selectMember(String memId);
	public String getPicture(String memId);

}

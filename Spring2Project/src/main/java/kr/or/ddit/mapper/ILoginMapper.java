package kr.or.ddit.mapper;

import kr.or.ddit.vo.crud.NoticeMemberVO;

public interface ILoginMapper {
	public NoticeMemberVO idCheck(String memId);
	public int signup(NoticeMemberVO memberVO);
	public void signupAuth(int memNo);
	public NoticeMemberVO loginCheck(NoticeMemberVO memberVO);
	public NoticeMemberVO idForgetProcess(NoticeMemberVO memberVO);
	public NoticeMemberVO pwForgetProcess(NoticeMemberVO memberVO);

	// 시큐리티 UserDetails정보 만들기
	public NoticeMemberVO readByUserId(String username);
}

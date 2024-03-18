package edu.kh.jdbc.common;

import edu.kh.jdbc.member.model.dto.Member;

public class Session {

	//로그인 : Db에 기록된 회원정보를 가지고 오는 것
	//		-> 로그아웃 할 때까지 프로그램에서 회원정보 유지됨
	
	
	public static Member loginMember = null;

	
	//loginMember == null -> 로그아웃 상태
	//loginMember != null -> 로그인 상태
	
	
	
	
	
	
}

package edu.kh.jdbc.main.model.service;

import java.sql.Connection;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.member.model.dto.Member;

public class MainService {

	private MainDAO dao = new MainDAO();

	/** 로그인
	 * @param memberId
	 * @param memberPw
	 * @return member
	 * @throws Exception 
	 */
	public Member login(String memberId, String memberPw) throws Exception {
		//반환값은 Member ->Session.loginMember의 값으로 반환받는다고 적어놓았기 때문
		
		//1. Connection 생성
		Connection conn  = getConnection();
		
		//2. DAO호출
		Member member = dao.login(conn, memberId, memberPw);
		
		
		close(conn);
		return member;
	}

	/** 아이디 중복 검사 서비스
	 * @param memberId
	 * @return
	 * @throws Exception 
	 */
	public int idDuplicationCheck(String memberId) throws Exception {
		
		Connection conn = getConnection();
		int result = dao.idDuplicationCheck(conn, memberId);
		
		close(conn);
		return result;
	}
	
	
	

	/** 회원가입 서비스
	 * @param member
	 * @return
	 * @throws Exception 
	 */
	public int signup(Member member) throws Exception {
		
		Connection conn = getConnection();
		
		//DAO호출하기
		int result = dao.signup(conn, member); //insert 수행
		
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		
		close(conn);
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

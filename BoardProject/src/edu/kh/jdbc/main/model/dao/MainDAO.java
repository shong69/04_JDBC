package edu.kh.jdbc.main.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.model.dto.Member;

public class MainDAO {

	//JDBC객체 참조변수 선언
	private Statement stmt; // sql 수행 후 결과 반환 받기 위해
	private PreparedStatement pstmt; //placeholder(위치 홀더'?')를 포함한 sql 세팅/수행/결과반환
	private ResultSet rs; //select 수행결과를 저장함
	
	private Properties prop; 
	
	//기본 생성자 DAO 객체가 생성될 때 xml 파일 읽어와서 properties 객체에 저장하기
	public MainDAO() {
		
		try { //xml파일 읽어오며 inputstream 사용하기 때문에 IOException 고려하여 try문 사용
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-sql.xml"));
			// -> Properties객체에 K:V 형식으로 xml내용이 저장됨
			
						
		}catch (Exception e) {
			e.printStackTrace();		
		}
		
		
		
		
		
	}

	/** 로그인 DAO(아이디, 비밀번호가 일치하는 회원 조회하기)
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		
		// 1. 결과 저장용 변수 생성
		Member member = null;
		
		try {
			
			// 2. sql 작성 후 수행
			String sql =  prop.getProperty("login");
			
			//PreparedStatement 객체 생성 후 sql 담아두기
			pstmt = conn.prepareStatement(sql);
			
			//placeholder에 맞는 값 대입
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			rs = pstmt.executeQuery(); //select 수행 후 결과 반환받기
			
			// 3. 조회 결과를 한 행씩 접근해서 얻어오기
			if(rs.next()) {
				int memberNo = rs.getInt("MEMBER_NO");
				String memberName = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				String enrollDate = rs.getString("ENROLL_DT");
				
				//Member객체를 생성 후 값 세팅하기
				member = new Member();
				member.setMemberNo(memberNo);
				member.setMemberId(memberId);
				member.setMemberPw(memberPw);
				member.setMembrName(memberName);
				member.setMemebrGender(memberGender);
				member.setEnrolldate(enrollDate);
				
			}

			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return member;
	}

	/** 아이디 중복 검사 sql 수행 dao
	 * @param conn
	 * @param memberId
	 * @return
	 */
	public int idDuplicationCheck(Connection conn, String memberId) throws Exception{
		int result  = 0;
		
		try {
			
			String sql = prop.getProperty("idDuplicationCheck");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				
				result = rs.getInt(1); //컬럼 순서로도 지정 가능함
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		
		return result;
	}

	/** 회원가입 SQL 수행 DAO(INSERT)
	 * @param conn
	 * @param member
	 * @return result
	 */
	public int signup(Connection conn, Member member) throws Exception {

		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			
			// ?(placeholder)에 값 세팅
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMembrName());
			pstmt.setString(4, member.getMemebrGender());
			
			
			result = pstmt.executeUpdate();
			
			
			
			
			
			
		}finally {
			
			close(pstmt);
			
		}
		return result;
	}
	
}

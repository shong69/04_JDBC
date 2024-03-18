package edu.kh.jdbc.member.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.model.dto.Member;
import static edu.kh.jdbc.common.JDBCTemplate.*;
public class MemberDAO {

	
	//JDBC 객체 참조 변수
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	
	//기본생성자 member-sql.xml 파일 읽어오고 prop에 저장하기
	
	
	public MemberDAO() {
		
		try {

			prop = new Properties();
			prop.loadFromXML(new FileInputStream("member-sql.xml"));
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/** 회원 목록 조회
	 * @param conn
	 * @return list
	 * @throws SQLException 
	 */
	public List<Member> selectMemberList(Connection conn) throws SQLException {
		
		List<Member> list = new ArrayList<>();
		try {
			String sql = prop.getProperty("selectMemberList");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String memberId = rs.getString("MEMBER_ID");
				String memberName = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("성별");
				
				Member member = new Member();
				member.setMemberId(memberId);
				member.setMembrName(memberName);
				member.setMemebrGender(memberGender);
				
				
				list.add(member);
				
			}

			
			
		}finally {
			close(rs);
			close(stmt);
		}
		
		
		return list;
	}


	/** 회원정보 수정 sql 수행 dao
	 * @param conn
	 * @param memberName
	 * @param memberGender
	 * @param memberNo
	 * @return result
	 */
	public int updateMember(Connection conn, String memberName, String memberGender, int memberNo) throws Exception{
		
		//결과 저장용 변수 선언
		int result = 0;
		

		try {
			//2. sql 작성, 수행
			String sql = prop.getProperty("updateMember");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberName);
			pstmt.setString(2, memberGender);
			pstmt.setInt(3, memberNo);
			
			result = pstmt.executeUpdate();
			
						
		}finally {
			//3. jdbc객체 자원 반환
			close(pstmt);
			
		}
		
		
		
		return result;
	}
	
	
	
}

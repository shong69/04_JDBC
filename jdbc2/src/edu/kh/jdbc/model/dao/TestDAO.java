package edu.kh.jdbc.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.model.vo.TestVO;

public class TestDAO {
	//DAO (Data Acess Object) : 데이터가 저장된 DB에 접근하는 객체
	//					-> SQL 수행, 결과 반환 받는 기능을 수행함
	
	//JDBC객체를 참조하기 위한 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop; // SQL 수행 시 구문 작성시 XML을 사용해야 하기 때문에 필요함
	
	//기본생성자
	public TestDAO() {
		// TestDAO 객체 생성시
		// test-query.xml 파일의 내용을 읽어와
		// Properties 객체에 저장
		
		
		
		try {
			
			prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("test-query.xml"));

			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int insert(Connection conn, TestVO vo1) throws SQLException{
		
		
		// 1. 결과 저장용 변수를 선언하기
		int result = 0;
		
		try {
			
			// 2. SQL 작성하기(test-query.xml에 작성된 SQL 얻어오기)
			String sql = prop.getProperty("insert");
			//SELECT INTO TB_TEST
			//VALUES(?,?,?)
			
			
			
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			
			
			// 4. 위치 홀더(?)에 알맞은 값 세팅하기
			pstmt.setInt(1,vo1.getTestNo()); //1
			pstmt.setString(2,vo1.getTestTitle()); //제목1
			pstmt.setString(3,vo1.getTestContent()); //내용1
			
			
			
			// 5. SQL(insert) 수행 후 결과 반환받기
			result =  pstmt.executeUpdate(); // DML 수행, 반영된 행의 개수(int) 반환
			
			/////////////////////////
			//전에 배운거(no preparedStatement)
			//stmt =  conn.createSatement();
			//stmt.executeQuery();
			//---------------------------------------//
			//yes preparedStatement
			//pstmt = conn.prepareStatement(sql); 이미 버스 안에 실어놓음
			//pstmt.excuteUpdate(); 여기서 sql 또 넣으면 안된다!!!
			
			
		} finally {
			
			// 6. 사용한 JDBC 객체 자원 반환하기
			close(pstmt);  //미리 만들어둔 메서드 사용하기
			//conn은 service로 되돌아가서 할 일이 있기 때문에 반환X
			
		}
		
		// 7. SQL 수행 결과 반환
		return result;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}

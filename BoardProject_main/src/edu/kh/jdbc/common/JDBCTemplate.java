package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 */
public class JDBCTemplate {

	/* DB 연결 (Connection 생성) + 자동 커밋 off, 
	 * JDBC 객체 자원 반환(close)
	 * 트랜젝션 제어, 
	 * 
	 * JDBC 템플릿은 이러한 JDBC에서 반복 사용되는 코드를 모아둔 클래스
	 * 
	 * *모든 필드와 메서드가 static이다*
	 * -> 어디서든지 클래스명.필드명 / 클래스명.메서드명 호출 가능함
	 * -> 별도 객체 생성은 안해도 됨
	 * 
	 * 
	 * */
	
	private static Connection conn = null;
	
	/**DB 연결 정보를 담고 있는 Connection객체 생성, 반환 메서드
	 * @return conn
	 */
	public static Connection getConnection() {
		
		
		try {
			
			//현재 커넥션 객체가 없을 경우 -> 새 커넥션 객체 생성
			if(conn == null || conn.isClosed()) {
				//conn.isClosed() : 커넥션이 닫혀있는 상태면 true 반환
				
				
				Properties prop = new Properties();
				//Map<STring, String> 형태의 객체 XML입출력 특화함
				
				//driver.xml 파일 읽어고기
				
				prop.loadFromXML(new FileInputStream("driver.xml"));
				//XMl 파일에 작성된 내용이 Properties객체에 모두 저장되엉 ㅣㅆ을 것임
				
				
				//XML에서 읽어온 값을 모두 변수에 저장하기
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");
				
				//커넥션 생성하기
				Class.forName(driver); //Oracle JDBC 객체 메모리 로드
				/*
				 * Class는 실행 중인 자바 프로그램에서 클래스와 
				 * 인터페이스를 표현하는(정보를 담는) 클래스
				 * 
				 * Class의 static 메소드인 forName은 
				 * 클래스의 이름을 매개변수로 받아서 Class 객체를 리턴해줌
				 * 53번째 줄에서 불러온 driver에 JDBC를 연결해주는 구문
				 * */
				
				//DriverManager을 통해 Connection 객체 생성하기
				conn = DriverManager.getConnection(url, user, password);
				
				// + 자동 커밋 비횔성화
				conn.setAutoCommit(false);
				

				
				
			}
			
		
						
		}catch(Exception e){
			System.out.println("Connection 생성 중 예외 발생");
			e.printStackTrace();
		}
		

		return conn; //생성 및 설정된 커넥션 객체 반환

		
		
		
	}
	
	
	/** Connection객체 자원을 반환해주는 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			
			// 전달받은 conn이
			// 참조하는 Connection 객체가 있고,
			// 그 Connection객체가 close 상태가 아니라면..
			if(conn!= null&& !conn.isClosed()) conn.close();
			

			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	
	/** Statement(부모), PreparedStatement(자식) 객체 자원 반환 메서드
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		
		try {
			
			if(stmt != null && !stmt.isClosed()) stmt.close();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	
		
	/**ResultSEt 객체 자원 반환 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		
		
		try {
			
			if(rs != null && !rs.isClosed()) rs.close();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
		
		
		

	/** 트랜젝션 commit 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		
		try {
			
			if(conn != null && conn.isClosed()) conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		

		
	} 
		
		
	/**트랜젝션 rollback 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		
		try {
			
			if(conn != null && conn.isClosed()) conn.rollback();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
		
		
		
		
		
		
	}
	
	
	






























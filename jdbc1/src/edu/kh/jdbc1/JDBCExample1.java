package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {
	public static void main(String[] args) {
		// JDBC (Java Database Connectivity) : Java에서 DB에 연결할 수 있게 해주는 Java Programming API
		//(JAVA에 기본 내장된 인터페이스)
		//java.sql 패키지에서 제공함
		
		//* JDBC를 이용한 어플리케이션을 만들 때 필요한 것
		// 1. Java의 JDBC관련 인터페이스
		// 2. DBMS(Oracle)
		// 3. Oracle에서 Java와 연결할 때 사용할 JDBC를 
		//    상속받아 구현할 클래스 모음(ojdbc.jar 라이브러리 안에 있음)
		
		
		// <1단계> : JDBC 객체 참조 변수 선언 (java.sql 패키지의 인터페이스)
		Connection conn = null;
		// DB 연결 정보를 담은 객체
		// -> DBMS 타입, 이름, IP, Port, 계정명, 비밀번호 등
		// -> DBeaver의 계정 접속 방법과 유사함
		// * Java와 DB 사이를 연결해주는 통로
		
		Statement stmt = null;
		// Connection을 통해 
		// SQL문을 DB에 전달해 실행하고
		// 생성된 결과(Result Set, 성공한 행의 개수)를 반환(Java쪽으로)해주는 객체
		
		ResultSet rs = null;
		// SELECT 질의 성공 시 반환되는데,
		// 조회 결과 집합을 나타내는 객체
				
		
		
		
		try {
			// <2단계> : 참조변수에 알맞은 객체를 대입하기
			
			
			//1. DB 연결에 필요한 Oracle JDBC Driver를 메모리에 로드시키기
			//런타임 시점에 해당 경로의 클래스를 동적으로 로드함
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// -> ()안에 작성된 클래스의 객체를 반환한다
			// -> 메모리에 객체가 생성되고, JDBC가 필요할 때 알아서 참조해서 사용한다
			// --> 생략해도 자동으로 메모리 로드가 진행됨(명시적으로 작성하는 것을 권장한다)
			
			
			//2. 연결정보를 담은 Connection을 생성하기
			// -> DriverManager 객체를 이용해서 Connection 객체를 만들어 얻어오기
			
			String type = "jdbc:oracle:thin:@";  //JDBC 드라이버 종류() 저장
			String ip =  "localhost";   //DB 서버 컴퓨터의 ip 전달해줌
			// == 127.0.0.1 (루프백 ip)도 가능
			
			String port = ":1521";   //port 번호
			//오라클 포트 기본번호 1521 사용중
			
			
			String sid = ":XE";   //DB 종류 이름
			
			//url == jdbc:oracle:thin:@localhost:1521:XE
			
			String user = "kh_ksy"; //사용자 계정
			
			String pw = "kh1234"; //비밀번호
			
			
			
			//DriverManager : 
			// 메모리에 로드된 JDBC 드라이버를 이용해서
			// Connection객체를 만드는 역할 함
			conn = DriverManager.getConnection(type + ip + port + sid, user, pw); //url 형태
			
			
			//중간확인
			System.out.println(conn); //oracle.jdbc.driver.T4CConnection@72d1ad2e
			
			
			
			
			// 3. SQL 작성
			// ** JAVA에서 작성되는 SQL은 마지막에 ;를 찍으면 안된다! **
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			
			
			
			// 4. Statement 객체 생성
			// -> Connection 객체를 통해 생성됨
			stmt = conn.createStatement();
			
			
			
			
			// 5. 생성된 Statement 객체에 sql을 적재해서 실행한 후
			//    결과를 반환받아와서 rs 변수에 저장하기
			rs = stmt.executeQuery(sql);
			// executeQuery() : select문 수행 메서드, ResultSet 반환함
			
			
			
			
			
			
			
			
			
			// <3단계> : SQL을 수행해서 반환받은 결과(ResultSet)을 
			//         한 행씩 접근해서 컬럼값 얻어오기
			
			while(rs.next()) {
				//rs.next() : rs가 참조하고 있는 ResultSet 객체의 첫번째 컬럼부터
				//            순서대로 한 행씩 이동.
				//            다음 행이 있으면 true, 없으면 false 반환함
				
				
				
				
				
				//rs.get자료형("컬럼명") 
				String empId = rs.getString("EMP_ID"); //"200"
				//현재 행의 "EMP_ID" 문자열 컬럼 값을 얻어옴
				
				String empName = rs.getString("EMP_NAME"); //"선동일"
				
				int salary = rs.getInt("SALARY"); // 8,000,000
				
				//java.sql.Date
				Date hireDate = rs.getDate("HIRE_DATE"); //1990-02-06 (시,분,초는 자바로 넘어오지 않는다)
				//Date타입이 YYYY-MM-DD 형태로 오버라이딩 되어 있음
				
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s\n", 
						empId, empName, salary, hireDate);
											  //java.sql.Date의 toString()은 yyyy-mm-dd 형식으로 오버라이딩 되어 있음
			}
			

		}catch(ClassNotFoundException e ) {
			System.out.println("JDBC 드라이버 경로가 잘못 작성됨");
		}catch(SQLException e) {
			// SQLException : DB 관련 최상위 예외
			e.printStackTrace();
		}finally {
			// <4단계> : 사용한 JDBC객체 자원 반환하기 -> close()
			
			//Connection, Statement, ResultSet 순서의 역순으로 닫아야 한다
			
			
			
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
			
			
			
		}
		
		
		
		
		
	}
}

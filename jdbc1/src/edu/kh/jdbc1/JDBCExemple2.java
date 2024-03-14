package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExemple2 {
	public static void main(String[] args) {
		
		//<1단계> : JDBC 객체 참조변수 생성
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
		//<2단계> : 참조 변수에 알맞은 객체 대입하기
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@"; 
			String ip =  "localhost";   
			String port = ":1521";  
			String sid = ":XE";   
			
			String user = "kh_ksy"; //사용자 계정
			String pw = "kh1234"; //비밀번호
			
			
			conn = DriverManager.getConnection(type + ip + port + sid, user, pw);
			
			System.out.println("<입력받은 급여보다 많이 받는(초과하는) 직원만 조회하기>");
			System.out.print("급여 입력 : ");
			int input = sc.nextInt(); //2000000
			
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > "+ input;
						// SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > 2000000
			
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			// <3단계> : sql을 수행해서 반환받은 결과(ResultSet)을 한 행씩 접근해서 컬럼값 얻어오기 
			
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d\n",empId, empName, salary);
				
				
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		//<4단계> : 사용한 JDBC 객체 자원 반환하기
			try {
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}

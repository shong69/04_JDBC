package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Emp;

public class JDBCExemple3 {
	public static void main(String[] args) {
		
		//부서명을 입력받아 같은 부서에 있는 사원의
		//사원명, 부서명, 급여 조회하기
		
		
		//JDBC 객체 참조변수 선언하기
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("부서명 입력 : ");
			String input = sc.nextLine();
			
			//JDBC 참조변수에 알맞은 객체 대입하기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@"; 
			String ip =  "localhost";   
			String port = ":1521";  
			String sid = ":XE";   
			
			String user = "kh_ksy"; //사용자 계정
			String pw = "kh1234"; //비밀번호
			
			conn = DriverManager.getConnection(type + ip + port + sid, user, pw);

			//SQL 작성하기
			String sql = "SELECT EMP_NAME, NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE, SALARY "
					+ "FROM EMPLOYEE "
					+ "LEFT JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID) "
					+ "WHERE NVL(DEPT_TITLE, '부서없음') = '" + input + "'";   //sql의 문자열로 처리하기 위해 홑따옴표 처리하기
						//JAVA에서 작성되는 SQL에 문자열 변수를 추가할 경우
					    //''(DB 문자열 리터럴)이 누락되지 않도록 신경써야 한다
						//미작성 시 String 값이 컬럼명으로 인식돼서 부적합한 결과가 나오게 된다.					
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			//조회결과(rs)를 List에 옮겨담기
			List<Emp> list = new ArrayList<Emp>();
			
			while(rs.next()) {
				
				//현재 행에 존재하는 컬럼값 얻어오기
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
				//EMP객체를 생성하여 컬럼값 담기
				Emp emp = new Emp(empName, deptTitle, salary);
				
				//생성된 Emp객체를 list에 넣기
				list.add(emp);
				
			}
			
			
			//만약에 list에 추가된 Emp 객체가 없다면 "조회결과 없음"
			//있다면 순차적으로 출력하기
			
			
			if(list.isEmpty()) { //list 비어있는 경우
				System.out.println("조회 결과 없음");
			}else {
				
				for(Emp emp:list) {
					System.out.println(emp);
				}
				
			}
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)rs.close();
				if(stmt != null)stmt.close();
				if(conn != null)conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}

		}
		
		

		
		
	}
}

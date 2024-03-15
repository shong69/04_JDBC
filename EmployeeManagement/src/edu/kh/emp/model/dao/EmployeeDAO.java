package edu.kh.emp.model.dao;

import static edu.kh.emp.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop; //쿼리 결과 저장
	
	public EmployeeDAO() {
		try {
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("query.xml"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	/** 전체 사원 정보 조회 DAO
	 * @param conn
	 * @return list
	 */
	public List<Employee> selectAll(Connection conn) throws Exception{

		//결과를 만들어서 보내줘야함 -> 결과 저장용 변수 선언
		List<Employee> list = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectAll"); //selectAll이라는 key 가져오기
			
			//Statement 객체 생성
			stmt = conn.createStatement();
			
			//SQl 수행 후 결과(ResultSet) 반환받기
			rs = stmt.executeQuery(sql);
			
			//조회결과를 얻어와 한 행씩 접근해서 
			//Employee객체를 생성하고 컬럼값에 담기
			//->List에 담기
			
			while(rs.next()) {
				int empId = rs.getInt("EMP_ID");
				// EMP_ID컬럼은 문자열 컬럼이지만, 저장된 값들은 숫자형이다.
				// ->DB에서 자동으로 형변환 후 보내줄 수 있음(값이 숫자로만 적혀진 문자열인 경우에만)
				
				String empName = rs.getString("EMP_NAME");
				String empNO = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNO, email, phone, departmentTitle, jobName, salary);
				
				list.add(emp); //List에 담기
				
				
			} //list 추가 후 while문 종료
			
			
		}finally {
			//자원반환
			
			close(rs);
			close(stmt);
		}
		
		
		return list;
	}




	/** 사원 정보 추가 DAO
	 * @param conn
	 * @param emp
	 * @return result(1 / 0)
	 */
	public int insertEmployee(Connection conn, Employee emp) throws Exception {
		
		int result = 0;
		
		try {
			
			//SQL 작성
			String sql = prop.getProperty("insertEmployee");
			//INSERT INTO EMPLOYEE VALUES(?,?,?,?,?,?,?,?,?,?,?, SYSDATE, NULL, DEFAULT)
			
			//PreparedStatement 객체 생성하기
			 pstmt = conn.prepareStatement(sql);
			
			//? (위치홀더)에 알맞은 값 대입하기
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			
			result = pstmt.executeUpdate();
			
			
		}finally {
			close(pstmt);
		}
		

		
		return result;
	}




	/** 사번이 일치하는 사원 정보 조회 DAO
	 * @param conn
	 * @param empid
	 * @return
	 * @throws SQLException
	 */
	public Employee selectEmpId(Connection conn, int empid) throws SQLException {
		
		Employee emp = null;
		
		try {
			String sql = prop.getProperty("selectEmpId");
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, empid); // 첫번째 ?에 empid 집어넣기
			
			rs=pstmt.executeQuery();
			
			
			//stmt -> ?X(위치홀더 없을 때)
			//pstmt -> ?O(위치홀더 있을 때)
			//executeQuery() -> SELECT 
			//executeUpdate() -> DML(UPDATE/DELETE/INSERT)	 -> return == int형(성공한 행의 개수)	
			
			if(rs.next()) {
				//empId는 이미 조회할 때 입력 받았으므로 안가져와도 됨
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empid, empName, empNo,email, phone, 
									departmentTitle, jobName, salary);

			}
			
		}finally {
			//conn은 계속 써야해서 close안함. service에서 닫기
			close(rs);
			close(pstmt);
		}
		return emp;
	}





	/** 사번이 일치하는 사원 정보 수정 DAO
	 * @param conn
	 * @param emp
	 * @return
	 */
	public int updateEmployee(Connection conn, Employee emp) throws Exception{

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("updateEmployee");
			
			//placeholder가 있음 -> pstmt 사용하기
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4,  emp.getEmpId());
			
			
			result = pstmt.executeUpdate();  //executeUpdate() -> DML(UPDATE/DELETE/INSERT)	 -> return == int형(성공한 행의 개수)	
			
		}finally {
			close(rs);
			close(pstmt);
			
			
		}
		
		
		return result;
	}




	/** 사번이 일치하는 사원의 정보 삭제 서비스
	 * @param conn
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(Connection conn, int empId) throws Exception{

		int result = 0;
		
		
		try {
			String sql = prop.getProperty("deleteEmployee");
			
			pstmt =conn.prepareStatement(sql);
			
			pstmt.setInt(1,empId);
			
			result = pstmt.executeUpdate();
			
			
		}finally {
			
			close(pstmt);
		}
		
		
		
		return result;
	}




	/** 입력받은 부서와 일치하는 모든 사원 정보 조회
	 * @param conn
	 * @param deptTitle
	 * @return
	 * @throws SQLException 
	 */
	public List<Employee> selectDeptEmp(Connection conn, String deptTitle) throws SQLException {
		
		List<Employee> list = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectDeptEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deptTitle);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo,email, phone, 
									departmentTitle, jobName, salary);
				list.add(emp);
			}
			
			
		}finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		
		return list;
	}




	/**
	 * @param conn
	 * @param salary
	 * @return
	 * @throws SQLException
	 */
	public List<Employee> selectSalaryEmp(Connection conn, int salary) throws SQLException {
		
		List<Employee>list = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectSalaryEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, salary);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int empSalary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo,email, phone, 
									departmentTitle, jobName, empSalary);
				list.add(emp);
				
			}
		}finally {
			close(rs);
			close(pstmt);
		}

		
		
		return list;
	}





	/** 부서별 급여 합 전체 조회
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Integer> selectDeptTotalSalary(Connection conn) throws SQLException {
		
		Map<String, Integer> map = new HashMap<>();
		
		try {
			String sql = prop.getProperty("selectDeptTotalSalary");
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String deptName = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
				map.put(deptName, (Integer)salary);		
				
			}
			
			
		}finally {
			
			close(rs);
			close(pstmt);
		}
		
		
		
		return map;
	}




	/** 주민등록번호가 일치하는 사원 정보 조회
	 * @param empNo
	 * @return
	 * @throws SQLException 
	 */
	public Employee selectEmpNo(Connection conn,String empNo) throws SQLException {
		
		Employee emp=null;
		try {
			String sql  = prop.getProperty("selectEmpNo");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, empNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empId, empName, empNo,email, phone, 
						departmentTitle, jobName, salary);
			}
			
			
		}finally{
			close(rs);
			close(pstmt);
			
		}

		
		return emp;
	}




	/** 직급별 급여 평균 조회
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Double> selectJobAvgSalary(Connection conn) throws SQLException {
		Map<String, Double> map = new HashMap<>();
		try {
			String sql = prop.getProperty("selectJobAvgSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String jobName = rs.getString("JOB_NAME");
				Double salary = rs.getDouble("SALARY");
				
				map.put(jobName, salary);
			}
			
		}finally {
			close(rs);
			close(stmt);
			
		}
		
		return map;
	}





	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

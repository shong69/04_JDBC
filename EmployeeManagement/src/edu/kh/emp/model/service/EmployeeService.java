package edu.kh.emp.model.service;

import  java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

//
public class EmployeeService {

	private EmployeeDAO dao= new EmployeeDAO();
	
	
	/** 전체 사원 정보 조회 서비스
	 * @return List
	 * @throws Exception 
	 */
	public List<Employee> selectAll() throws Exception{
		
		//1. 커넥션 생성하기
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectAll(conn);
		
		//사용한 커넥션 반환하기
		close(conn);
		
		return list;
		
		
		
	}


	/** 사원 정보 추가 서비스
	 * @param emp
	 * @return result (1 / 0)
	 * @throws Exception 
	 */
	public int insertEmployee(Employee emp) throws Exception {
		Connection conn = getConnection();
		int result = dao.insertEmployee(conn, emp);
		
		if(result > 0) commit(conn); //result값이 정상이면 commit하기
		else rollback(conn);  //실패했으면 rollback
		
		close(conn);
		
		return result;
	}


	/** 사번이 일치하는 사원 조회하기
	 * @param empid
	 * @return emp<Employee>
	 * @throws SQLException 
	 */
	public Employee selectEmpId(int empid) throws SQLException {
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpId(conn, empid);
		
		close(conn);
		
		return emp;
	}


	/** 사번이 일치하는 사원의 정보 수정 서비스
	 * @param emp
	 * @return
	 * @throws Exception 
	 */
	public int updateEmployee(Employee emp) throws Exception {

		Connection conn = getConnection();

		int result = dao.updateEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}


	/** 사번이 일치하는 사원의 정보 삭제
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(int empId) throws Exception{
		
		
		Connection conn = getConnection();

		int result = dao.deleteEmployee(conn, empId);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		
		
		
		
		
		return result;
	}


	/**	입력받은 부서와 일치하는 모든 사원 정보 조회
	 * @param deptTitle
	 * @return
	 * @throws SQLException 
	 */
	public List<Employee> selectDeptEmp(String deptTitle) throws SQLException {
		
		Connection conn = getConnection();

		List<Employee> list= dao.selectDeptEmp(conn, deptTitle);
		
		close(conn);
		
		
		return list;
	}


	public List<Employee> selectSalaryEmp(int salary) throws SQLException {
		
		Connection conn = getConnection();

		List<Employee> list= dao.selectSalaryEmp(conn, salary);
		
		close(conn);
		return list;
	}


	/**부서별 급여 합 전체 조회
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Integer> selectDeptTotalSalary() throws SQLException {
		
		Connection conn = getConnection();
		
		Map<String, Integer> map = dao.selectDeptTotalSalary(conn);
		
		close(conn);
		return map;
	}


	/**주민등록번호가 일치하는 사원 정보 조회
	 * @param empNo
	 * @return
	 * @throws SQLException 
	 */
	public Employee selectEmpNo(String empNo) throws SQLException {
		
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpNo(conn, empNo);
		
		close(conn);
		
		return emp;
	}


	/**직급별 급여 평균 조회
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Double> selectJobAvgSalary() throws SQLException {
		Connection conn = getConnection();
		
		Map<String, Double> map = dao.selectJobAvgSalary(conn);
		
		close(conn);
		return map;
	}



	
	
	
	
}

package edu.kh.emp.view;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.kh.emp.model.service.EmployeeService;
import edu.kh.emp.model.vo.Employee;

//화면용 클래스
public class EmployeeView {

	private Scanner sc = new Scanner(System.in);
	
	//Service 객체 생성
	private EmployeeService service = new EmployeeService();
	
	//메인메뉴
	public void displayMenu() {
		
		int input = 0;
		
		do {
			
			try {
				
				
				
				
				System.out.println("------------------------------------------------------");
				System.out.println("------------------사원 관리 프로그램-----------------");
				System.out.println("1. 전체 사원 정보 조회");
				System.out.println("2. 새로운 사원 추가");
				System.out.println("3. 사번이 일치하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				
				//추가
				System.out.println("6. 입력받은 부서와 일치하는 모든 사원 정보 조회");
				System.out.println("7. 입력받은 급여 이상을 받는 모든 사원 정보 조회");
				System.out.println("8. 부서별 급여 합 전체 조회");
				System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				System.out.println("10. 직급별 급여 평균 조회");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 >>");
				input = sc.nextInt();
				
				switch(input) {
				case 1: selectAll(); break;
				case 2: insertEmployee();break;
				case 3: selectEmpId(); break;
				case 4: updateEmployee(); break;
				case 5: deleteEmployee(); break;
				
				//메뉴 추가
				case 6: selectDeptEmp(); break; // 부서명 입력받기
				case 7: selectSalaryEmp(); break; //급여 입력받기
				case 8: selectDeptTotalSalary(); break; 
				//D1 : 8000000원
				//D2 : 5200000원...형식으로 나오도록 - HashMap<String, Integer>
				
				case 9: selectEmpNo(); break; //주민등록번호 입력받기
				
				case 10: selectJobAvgSalary(); break; 
				//대표 : 8000000.0원
				//부사장 : 4850000.0원....형식으로 나오도록 - HashMap<String, Double(평균값이니까)>
				
				
				case 0 : System.out.println("프로그램을 종료합니다..."); break;
				
				default: System.out.println("존재하는 번호만 입력하세요");
				}
				
				
				
			}catch (InputMismatchException e) {
				//the tokenretrieved does not match the pattern for the expected type, 
				//orthat the token is out of range for the expected type.
				
				System.out.println("정수만 입력해주세요");
				input = -1; //반복문 첫번째 바퀴에서 잘못 입력하면 종료되는 상황을 방지

				sc.nextLine(); //입력버퍼에 남아있는 잘못 입력한 문자열 제거하여 무한반복 방지
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}while(input != 0);
	}
	
	
	
	/**
	 * 전체 사원 정보 조회 View
	 * @throws Exception 
	 */
	public void selectAll() throws Exception {
		System.out.println("<전체 사원 정보 조회>");
		
		List<Employee>empList = service.selectAll();
		//n개의 직원 정보가 한꺼번에 들어오게 됨 -> EmployeeVO에 한명한명 저장하기 -> 직원 리스트에 저장하기
		
		
		printAll(empList);
		
		
		
		
		
	}
	
	
	
	
	/**
	 * 사원 정보 추가
	 * @throws Exception 
	 */
	public void insertEmployee() throws Exception {
		System.out.println("<사원정보 추가?");
		
		int empId = inputEmpId();
		
		System.out.print("이름 : ");
		String empName = sc.next();
			
		System.out.print("주민등록번호('-' 미포함) : ");
		String empNo = sc.next();
		
		System.out.print("이메일 : ");
		String email = sc.next();
		
		System.out.print("전화번호 : ");
		String phone = sc.next();

		
		System.out.print("부서코드(D1~D9) : ");
		String deptCode = sc.next();
		

		System.out.print("직급 번호(J1 ~ j7) : ");
		String jobCode = sc.next();		
		

		System.out.print("급여 등급  : ");
		String salLevel = sc.next();
		

		System.out.print("급여 : ");
		int salary = sc.nextInt();

		System.out.print("보너스 : ");
		Double bonus = sc.nextDouble();
		

		System.out.print("사수 번호 : ");
		int managerId = sc.nextInt();
		
		
		Employee emp = new Employee(empId, empName, empNo, email, phone, salary, deptCode, jobCode, salLevel, bonus, managerId);
		
		int result = service.insertEmployee(emp);
		
		if(result>0) {
			System.out.println("사원정보 추가 성공");
		}else {
			System.out.println("사원정보 추가 실패");
		}
		
		
	}
	
	
	
	
	/**
	 * 사번이 일치하는 사원 정보 조회
	 * @throws SQLException 
	 */
	public void selectEmpId() throws SQLException {
		System.out.println("<사번이 일치하는 사원 정보 조회>");
		
		//사번 입력 받기(inputEmpId() 이용)
		
		int empid = inputEmpId();
		
		//서비스 호출 및 결과 반환받기
		
		Employee emp = service.selectEmpId(empid);
		
		//printOne() 메서드 이용하여 결과 출력하기
		printOne(emp);
	}
	
	
	
	/**
	 * 사번이 일치하는 사원 정보 수정
	 * @throws Exception 
	 */
	public void updateEmployee() throws Exception {
		
		System.out.println("<사번이 일치하는 사원 정보 수정>");
		
		int empId = inputEmpId();
		System.out.print("이메일 : ");
		String email = sc.next();
		
		System.out.print("전화번호(- 제외) : ");
		String phone = sc.next();
		System.out.print("급여 : ");
		int salary = sc.nextInt();
		
		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setEmail(email);
		emp.setPhone(phone);
		emp.setSalary(salary);
		
		int result = service.updateEmployee(emp);
	
		if(result >0) {
			System.out.println("사원정보가 수정되었습니다.");
		}else {
			System.out.println("사번이 일치하는 직원이 존재하지 않습니다.");
		}
	}
	
	
	
	
	/** 사번이 일치하는 사원의 정보 삭제하기
	 * @throws Exception
	 */
	public void deleteEmployee() throws Exception {
		
		System.out.println("<사번이 일치하는 사원 정보 삭제>");
		
		
		
		int empId = inputEmpId();
		System.out.print("정말 삭제하시겠습니까?(Y/N) : ");
		char ans = sc.next().toUpperCase().charAt(0);
				
		if(ans=='Y') {
			int result = service.deleteEmployee(empId);
			
			if(result>0) {
				System.out.println("삭제되었습니다.");
			}else {
				System.out.println("사번이 일치하는 사원이 존재하지 않습니다.");
			}
				
		}else {
			System.out.println("삭제가 종료되었습니다.");
		}
		
	}
	
	
	/**
	 * 입력받은 부서와 일치하는 모든 사원 정보 조회
	 * @throws SQLException 
	 */
	public void selectDeptEmp() throws SQLException {
		System.out.println("<입력받은 부서와 일치하는 모든 사원 정보 조회>");
	
		System.out.print("부서명을 입력하세요 : ");
		String deptTitle = sc.next();
		
		List<Employee> list = service.selectDeptEmp(deptTitle);
		
		
		printAll(list);
	}
	
	
	
	
	/**
	 * 입력받은 급여 이상을 받는 모든 사원 정보 조회
	 * @throws SQLException 
	 */
	public void selectSalaryEmp() throws SQLException {
		System.out.println("<입력받은 급여 이상을 받는 모든 사원 정보 조회>");
		
		System.out.print("급여를 입력하세요(숫자만 입력) : ");
		int salary = sc.nextInt();
		
		
		List<Employee> list = service.selectSalaryEmp(salary);
		
		
		printAll(list);
		
		
	}
	
	
	
	/**
	 * 부서별 급여 합 전체 조회
	 * @throws SQLException 
	 */
	public void selectDeptTotalSalary() throws SQLException {
		System.out.println("<부서별 급여 합 전체 조회>");
		
		
		Map<String, Integer> map = service.selectDeptTotalSalary();
		
		System.out.println("부서명    |    급여합");
		System.out.println("----------------------");
		
		for(String key : map.keySet()) {
			int value = map.get(key);
			System.out.printf("%s|%d\n", key, value);
		}
	}
	
	
	
	/**
	 * 주민등록번호가 일치하는 사원 정보 조회
	 * @throws SQLException 
	 */
	public void selectEmpNo() throws SQLException {
		
		System.out.println("<주민등록번호가 일치하는 사원 정보 조회>");
		
		System.out.print("주민등록번호를 입력하세요('-' 포함) : ");
		String empNo = sc.next();
		
		Employee emp = service.selectEmpNo(empNo);
		
		printOne(emp);
		
	}
	
	
	
	/**
	 * 직급별 급여 평균 조회
	 * @throws SQLException 
	 */
	public void selectJobAvgSalary() throws SQLException {
		System.out.println("<직급별 급여 평균 조회>");
		
		Map<String, Double> map = service.selectJobAvgSalary();
		System.out.println("  직급명  |  급여 평균  ");
		System.out.println("------------------------");
		for(String key : map.keySet()) {
			double value  = map.get(key);
			

			System.out.printf("%-8s | %-8.1f\n", key, value);
		}
	}
	
	
	//보조 메서드
	/** 전달받은 사원 List 모두 출력
	 *
	 */
	public void printAll(List<Employee> empList) {
		
		if(empList.isEmpty()) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			for(Employee emp : empList) {
				System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
						emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(),
						emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
			}
		
		}
		
		return;
	}

	
	
	public int inputEmpId() {
		System.out.print("사번 입력 : ");
		int empId = sc.nextInt();
		return empId;
		
		
	}
	
	


	
	/** 사원 1명 정보 출력
	 * @param emp
	 */
	public void printOne(Employee emp) {
		if(emp == null) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			
			System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
					emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(),
					emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
		}
	}

	

	
	
	
}

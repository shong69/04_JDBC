package edu.kh.emp.run;

import edu.kh.emp.view.EmployeeView;

public class EmployeeRun {
	public static void main(String[] args) {
		//Run<->View(출력/입력)<->Service(비즈니스 로직 처리 : 데이터가공, 트랜젝션 제어)<->DAO(DB연결, SQL실행, 결과 반환)
		//<->query.xml(SQL문작성)
		
		new EmployeeView().displayMenu();
		
		
		
	}
}

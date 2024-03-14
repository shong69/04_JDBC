package edu.kh.jdbc.model.service;

import java.sql.Connection;
import java.sql.SQLException;

//import static 구문
// -> static이 붙은 필드, 메서드를 호출할 때
//    클래스명을 생략할 수 있도록 해줌
import static edu.kh.jdbc.common.JDBCTemplate.*; //모든 메서드를 import해줌
import edu.kh.jdbc.model.dao.TestDAO;
import edu.kh.jdbc.model.vo.TestVO;

public class TestService {

	//Service : 비즈니스 로직(데이터 가공, 트랜젝션 제어) 처리
	// -> 실제 프로그램이 제공하는 기능을 모아놓는 클래스
	
	//하나의 Service 메서드에서 n개의 DAO 메서드를 호출하여
	//이를 하나의 트랜젝션 단위로 취급하여
	//한번에 commit rollback을 수행할 수 있다
	
	private TestDAO dao = new TestDAO();
	
		
	public int insert(TestVO vo1) throws SQLException {

		//커넥션 생성
		Connection conn = getConnection();
		
		//DAO 메서드를 호출하여 수행 후 결과 반환받기
		// -> Service에서 생성한 Connection 객체를 반드시 같이 전달해야 한다
		int result = dao.insert(conn, vo1);
		
		
		//트랜젝션 제어하기
		//성공하면 commit, 실패하면 rollback할거임
		if(result > 0) commit(conn);
		else rollback(conn);
		
		//커넥션 반환하기
		close(conn);
		
		
		//결과 반환
		return result;
		
	}
}

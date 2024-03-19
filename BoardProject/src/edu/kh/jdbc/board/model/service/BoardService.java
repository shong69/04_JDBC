package edu.kh.jdbc.board.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dto.Board;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardService {

	private BoardDAO dao = new BoardDAO();

	/** 게시글 목록 조회 서비스
	 * @return boardList
	 * @throws Exception 
	 */
	public List<Board> selectAllBoard() throws Exception {
		
		Connection conn = getConnection();
		
		List<Board> list = dao.selectAllBoard(conn);
		
		close(conn);
		return list;
	}

	/** 게시글 상세 조회 서비스
	 * @param input
	 * @param memberNo
	 * @return board
	 * @throws Exception 
	 */
	public Board selectboard(int input, int memberNo) throws Exception {
		//1. 커넥션 생성
		Connection conn = getConnection();
		
		//2. 게시글 상세 조회 DAO 메서드를 호출
		Board board = dao.selectboard(conn, input);
		
		//3. 게시글이 조회된 경우 
		if(board != null) {
			
			//4. 조회수 증가 dao 호출하기
			//단, 게시글 작성자와 현재 로그인한 회원이 다른 경우에만 증가하기
			if(board.getMemberNo()!= memberNo) {
			//조회한 게시글 작성한 회원번호 != 로그인한 회원번호
				//5. 조회수 증가 dao 메서드호출하기(UPDATE할거임)
				int result = dao.updateReadCount(conn, input);
				
				
				// 6. 트랜젝션 제어 처리 + 데이터 동기화 처리
				if(result > 0) {
					commit(conn);
					
					//조회된 board의 조회수는 0
					//DB의 조회수는 1인 상태
					// ->조회 결과인 board의 조회수도 1 증가시켜줌
					
					board.setReadCount(board.getReadCount()+1); //현재 가지고 있는 boardCount에서 +1해줌
					
				}else {
					rollback(conn);
				}
				
			}
		}
		//7. 커넥션 반환하시
		close(conn);
		
		return board;
	}
	
	
	
}

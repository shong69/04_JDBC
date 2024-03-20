package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.commit;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;
import static edu.kh.jdbc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.dto.Board;
import edu.kh.jdbc.board.model.dto.Comment;

public class BoardService {

	private BoardDAO dao = new BoardDAO();
	private CommentDAO commentDAO = new CommentDAO();

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
			
			
			//*************************************************************
			// ** 해당 게시글에 대한 댓글 목록 조회 DAO 호출
			List<Comment> commentList = commentDAO.selectCommentList(conn, input);
			
			//board에 댓글 목록 세팅하기
			board.setCommentList(commentList);
			
			//*************************************************************
			
			
			
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

	
	
	
	
	/** 게시글 수정 서비스
	 * @param boardTitle
	 * @param string
	 * @param boardNo
	 * @return result
	 */
	public int updateBoard(String boardTitle, String boardContent, int boardNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateBoard(conn, boardTitle,boardContent, boardNo);
		
		//업데이트구문이니까 transaction 처리까지 해줌
		if(result > 0)commit(conn);
		else 		  rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	
	
	
	
	

	/** 게시글 등록 서비스
	 * @param boardTitle
	 * @param string
	 * @param memberNo
	 * @return result
	 */
	public int insertBoard(String boardTitle, String boardContent, int memberNo) throws Exception {
		
		Connection conn = getConnection();
		
		//다음 게시글 번호를 생성해주는 dao 호출하기 -> insert가 동시다발적으로 일어날 때
		//글 + 제목+ 내용 / 사진이 다른 sql로 삽입되기 때문에 게시글의 순서가 다르게 들어갈 수 있다
		//-=> sequence 번호를 미리 만들어놓고, 이후에 이미지가 삽입될 때 해당 번호를 넣어준다.
		int boardNo = dao.nextBoardNo(conn);
		
		//제목, 내용, 회원번호, 다음게시글번호
		int result = dao.insertBoard(conn, boardTitle, boardContent, memberNo, boardNo);
		
		if(result > 0) {
			commit(conn);
			result = boardNo;
			//삽입 성공/실패 여부가 아니라 현재 삽입된 게시글 번호를 보내줌
		}else {
			rollback(conn);
		}
		
		close(conn);
		return result;  //삽입 성공 시 현재 삽입된 게시글 번호, 실패시 0 리턴됨
	}

	/** 게시글 삭제 서비스
	 * @param boardNo
	 * @return result
	 * @throws Exception 
	 */
	public int deleteBoard(int boardNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteBoard(conn, boardNo);
		
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	
	
}

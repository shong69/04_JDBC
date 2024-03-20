package edu.kh.jdbc.board.model.dto;

public class Comment {

	
	
	
	private int commentNO; //댓글 번호
	private String commentContent; //댓글 내용
	private int memberNo; //작성자 회원 번호
	private String memberName; //작성자 회원이름
	private String createdate; //댓글 작성일
	private int boardNo; //작성된 게시글 번호)등록, 수정, 삭제 시 이용
	
	
	public int getCommentNO() {
		return commentNO;
	}
	public void setCommentNO(int commentNO) {
		this.commentNO = commentNO;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

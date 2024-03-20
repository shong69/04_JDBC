package edu.kh.jdbc.member.model.dto;

public class Member {

	private int memberNo; //회원번호
	private String memberId; //회원 아이디
	private String memberPw; //회원 비밀번호
	private String membrName; //회원 이름
	private String memebrGender; //회원 성별
	private String enrolldate; //가입일
	private String unregisterFlag; //탈퇴 여부
	
	public Member() {}

	
	
	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMembrName() {
		return membrName;
	}

	public void setMembrName(String membrName) {
		this.membrName = membrName;
	}

	public String getMemebrGender() {
		return memebrGender;
	}

	public void setMemebrGender(String memebrGender) {
		this.memebrGender = memebrGender;
	}

	public String getEnrolldate() {
		return enrolldate;
	}

	public void setEnrolldate(String enrolldate) {
		this.enrolldate = enrolldate;
	}

	public String getUnregisterFlag() {
		return unregisterFlag;
	}

	public void setUnregisterFlag(String unregisterFlag) {
		this.unregisterFlag = unregisterFlag;
	}
	
	

	
	
	
	
	
}

package edu.kh.jdbc.member.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.member.model.dto.Member;
import edu.kh.jdbc.member.service.MemberService;


/** 회원 전용 화면
 * 
 */
public class MemberView {

	private Scanner sc = new Scanner(System.in);

	private MemberService service = new MemberService();
	
	/**
	 * 회원기능 메뉴 View
	 */
	public void memberMenu() {
		
		int input = 0;
		
		do {
			try {
				
				System.out.println("\n====회원 기능====\n");
				System.out.println("1. 내 정보 조회");
				System.out.println("2. 회원 목록 조회(아이디, 이름, 성별, (현재 로그인 한 회원번호)");
				System.out.println("3. 내 정보 수정(이름, 성별)");
				System.out.println("4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새비밀번호 확인)");
				System.out.println("5. 회원 탈퇴(보안코드, 비밀번호, UPDATE)");
				
				System.out.println("9. 메인 메뉴로 돌아가기");
				System.out.println("0. 프로그램 종료");
				
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: selectMyInfo(); break;
				case 2: selectMemberList(); break;
				case 3: updateMember(); break;
				case 4:  updatePassword(); break;
				case 5: if( unRegisterMenu() )return; break;
				case 9: System.out.println("\n====메인 메뉴로 돌아갑니다====\n"); break;
				case 0: 
					System.out.println("\n====프로그램 종료====\n");
					//JVM 강제 종료 구문
					//매개변수는 기본 0, 다른 숫자는 오류를 의미함
					System.exit(0);
				default: System.out.println("\n***메뉴 번호만 입력해 주세요***\n");
				}
				
				
			}catch (InputMismatchException e) {
				System.out.println("\n***입력 형식이 올바르지 않습니다***\n");
				
				sc.nextLine();
				input=-1;
				
			}
			
		}while(input!=9);
		
		
		
	}
	
	/**
	 * 내 정보 조회
	 */
	public void selectMyInfo() {
		System.out.println("\n====내 정보 조회====n");
		
		//회원 번호, 아이디, 이름, 성별(남, 여), 가입일
		//Session, lonignMember =이용
		System.out.println("회원번호 : " + Session.loginMember.getMemberNo());
		System.out.println("아이디 : " + Session.loginMember.getMemberId());
		System.out.println("이름 : " + Session.loginMember.getMemebrGender());
				
		if(Session.loginMember.getMemebrGender().equals("M")) {
			System.out.println("성별 : 남");
		}else {
			System.out.println("성별 : 여");
		}

		System.out.println("가입일" + Session.loginMember.getEnrolldate());
	}
	
	
	
	
	/**
	 *  회원 목록 조회(아이디, 이름, 성별, (현재 로그인 한 회원번호))
	 */
	public void selectMemberList() {
		System.out.println("\n====회원 목록 조회====\n");
		
		

		try {
			//회원 목록 조회서비스 호출 후 결과 반환받기
			List<Member> memberList = service.selectMemberList();
			
			if(memberList.isEmpty()) {
				System.out.println("\n====조회 결과가 없습니다====\n");
				return;
			}
			
			for(int i =0; i<memberList.size(); i++) {
				System.out.printf("%d\t\t %s\t\t %s\t\t %s\n", 
						i+1, 
						memberList.get(i).getMemberId(), 
						memberList.get(i).getMembrName(),
						memberList.get(i).getMemebrGender());
			}
			//1 user04 유저사 남
			//2 user03 유저삼 여
			// ...
			
			
		}catch (Exception e) {
			System.out.println("\n***회원 목록 조회 중 예외 발생***\n");
		}
		
		
	}
		
	/**
	 *  내 정보 수정(이름, 성별)
	 */
	public void updateMember() {
		
		System.out.println("\n====내 정보 수정====\n");
		
		System.out.print("수정할 이름 : ");
		String memberName = sc.next();
		
		String memberGender = null;
		while(true) {
			System.out.print("수정할 성별(F/M) : ");
			memberGender = sc.next().toUpperCase();
			
			if(memberGender.equals("M") || memberGender.equals("F")){
				break;
			}
			
			System.out.println("**M 또는 F를 입력해주세요**");
		}
		
		try {
			//회원정보 수정 서비스 호출 및 결과 반환받기
			
			int result = service.updateMember(memberName, memberGender, Session.loginMember.getMemberNo());
			//Session.loginMember.getMemberNo() : 현재 로그인한 회원의 번호
			
			if(result>0) {
				System.out.println("\n===수정되었습니다===\n");
				//Service를 호출해서 DB만 수정됨
				// -> DB와 JAVA 프로그램 간의 데이터를 동기화시켜야 한다
				Session.loginMember.setMembrName(memberName);  //java에서 데이터 동기화하기
				Session.loginMember.setMemebrGender(memberGender);
				System.out.printf("");
				
				
			}else {
				System.out.println("\n***수정 실패***\n");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	

	/**
	 * 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새비밀번호 확인)
	 */
	public void updatePassword() {
		System.out.println("\n====비밀번호 변경====\n");
		
		
		System.out.print("비밀번호를 입력하세요 : ");
		String curPw = sc.next();
		
		String newPw = null;
		//비밀번호 확인
		 //비밀번호 동일 -> 새 비밀번호 입력
		while(true) {
				
			System.out.print("새 비밀번호를 입력하세요 : ");
			String newPw1 = sc.next();
			
			System.out.print("새 비밀번호 확인 : ");
			String newPw2 = sc.next();
			
			if(newPw1.equals(newPw2)) { 
				newPw = newPw1;
				break;
			}
			
			System.out.println("새 비밀번호가 일치하지 않습니다.");
	
		}
			
		try {
			
			int result = service.updatePw(curPw, newPw, Session.loginMember.getMemberNo());					
			
			if(result >0) {
				System.out.println("\n====수정되었습니다====\n");
				Session.loginMember.setMemberPw(newPw);
				
				
			}else {
				System.out.println("\n\n***현재 비밀번호가 일치하지 않습니다***");
			}
			
			
		}catch (Exception e) {
			System.out.println("\n***비밀번호 변경 중 예외 발생***\n");
			e.printStackTrace();
		}
		

		
	}
	
	/**
	 * 회원 탈퇴(보안코드, 비밀번호, UPDATE)
	 * @return true/false
	 */
	public boolean unRegisterMenu() {
		
		System.out.println("\n====회원 탈퇴====\n");
		
		System.out.println("비밀번호를 입력하세요 : ");
		String memberPw = sc.next();
		
		String code = service.createSecurityCode();
		//보안코드 만들기
		System.out.printf("보안코드를 입력하세요[%s] : ",code);
		String input = sc.next();		
		
		
		// 보안 문자 일치여부 확인하기
		if(!input.equals(code)) { //일치하지 않으면
			System.out.println("\n***보안문자가 일치하지 않습니다***\n");
			
			return false;
			
		}
		while(true) {
			System.out.print("정말 탈퇴하시겠습니까? (Y/N)");
			char check = sc.next().toUpperCase().charAt(0);
			
			if(check=='Y') {
				break;
				
			}if(check=='N') {
				System.out.println("\n===탈퇴 취소===\n");
				return false;
			}

			//'Y', 'N'이 아닌 경우
			System.out.println("\n*** 잘못 입력하셨습니다***\n");
		
		}
		
		try {
			
			
			int result = service.unRegisterMember(memberPw, Session.loginMember.getMemberNo()); //unregister_fl을 y로 업데이트 해야함
			
			
			if(result>0) {
				System.out.println("\n=== 탈퇴 되었습니다 ===\n");
				
				//로그아웃하기
				Session.loginMember = null;
				
				return true;
			}else {
				System.out.println("\n*** 현재 비밀번호가 일치하지 않습니다. ***\n");
			}
			
		}catch (Exception e) {
			System.out.println("\n***회원 탈퇴 중 예외 발생***\n");
			e.printStackTrace();
		}
	
		
		return false;
	}
	
}

















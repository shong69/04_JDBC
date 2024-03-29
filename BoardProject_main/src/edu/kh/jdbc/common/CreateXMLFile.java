package edu.kh.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {
	public static void main(String[] args) {
		//XML(eXtensible Markup Language) : 단순화된 데이터 기술 형식
		
		//XML에 저장되는 데이터 형식은 Key : Value 형식임
		// -> Key와 Value 모두 String(문자열)형식이다.
		
		//XML 파일을 읽고, 쓰기 위한 IO 관련 클래스가 필요함
		
		// * Properties 컬렉션 객체 *
		// - Map 후손 클래스임
		// - Key, Value 모두 String(문자열)
		// - XML 파일을 읽고 쓰는데 특화된 메서드를 제공함
		try {
			Scanner sc = new Scanner(System.in);
			
			//Properties 객체 생성
			Properties prop = new Properties();
			
			System.out.print("생성할 파일 이름 : ");
			String fileName = sc.next();
			
			//FileOutputStream 생성하기
			FileOutputStream fos  = new FileOutputStream(fileName + ".XML");
			
			//Properties 객체를 이용해 XML 파일 생성하기
			prop.storeToXML(fos, fileName+".xml 파일 생성"); //XML 파일 안의 <comment> 태그 안에 두번째 파라미터가 들어감
			
			System.out.println(fileName + ".xml 파일 생성 완료");
			
			
			
			
			
			
			
			
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
}

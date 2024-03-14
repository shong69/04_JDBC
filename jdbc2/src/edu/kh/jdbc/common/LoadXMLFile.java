package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LoadXMLFile {

	public static void main(String[] args) {
		
		//XML 파일 읽어오기(Properties, FileInputStream)
		try {
			
			Properties prop  = new Properties();
			
			//driver.xml 파일을 읽어오기 위한 InputStream 객체 생성
			FileInputStream fis = new FileInputStream("driver.xml");
			
			//연결된 driver.xml 파일에 있는 내용을 모두 읽어와
			//Properties 객체에 K:V 형식으로 저장
			prop.loadFromXML(fis);
			
			System.out.println(prop);
			
			//prop.getProperty("key") : key가 일치하는 속성(데이터 == Value)을 얻어옴
			
			String driver = prop.getProperty("driver"); //oracle.jdbc.driver.OracleDriver
			String url = prop.getProperty("url"); //jdbc:oracle:thin:@localhost:1521:XE
			String user = prop.getProperty("user"); //kh_ksy
			String password = prop.getProperty("password"); //kh1234
			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user,password);
			
			
			System.out.println(conn);
			
			/* 왜 XML 파일을 이용해서 DB 연결정보를 읽어와야 할까?
			 * 
			 * 1. 코드 중복 제거
			 * 2. 별도 관리 용도
			 * 3. 재 컴파일을 진행하지 않기 위해
			 *  - 코드가 길수록 컴파일 소요 시간이 늘어나기 때문에
			 *    코드 수정으로 인한 컴파일 소요 시간을 없앤다.
			 *   (파일의 내용을 읽어오는 코드만 작성해두면, java 코드 수정 없이 파일내용만 수정해서
			 *    재컴파일 없이 가능한다)
			 *    
			 * 4. XML 파일에 작성된 문자열 형태를 그대로 읽어오기 떄문에
			 *    SQl 작성 시 좀 더 편리해진다
			 * */
			
	
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}

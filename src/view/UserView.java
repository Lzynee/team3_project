package view;

import dao.NonuserDao;
import dao.UserDao;
import model.Nonuser;
import model.User;

public class UserView implements CommonView{

	private UserDao udao = new UserDao();
	private NonuserDao nudao = new NonuserDao();

	public String Login() {
		try {
			// 로그인창 출력
			System.out.println("-----------------------------------------------------");
			System.out.println();
			System.out.println("                     [ 로 그 인 ]");
			System.out.println();
			System.out.println("-----------------------------------------------------");
			System.out.println();
			System.out.print(" I      D : ");
			String userid = scan.nextLine();
			System.out.println();
			System.out.print(" Password : ");
			String userpwd = scan.nextLine();
			System.out.println();

			User user = udao.selectById(userid);

			if (userpwd.equals(user.getUserPwd()))
				return userid;

		} catch (Exception e) {
//			e.printStackTrace();
		}
		return "fail";
	}

	public String Non_userlogin() {
		try {
			Nonuser nuser = new Nonuser();

			System.out.println("-----------------------------------------------------");
			System.out.println();
			System.out.println("                   [ 비회원 로그인 ]");
			System.out.println();
			System.out.println("-----------------------------------------------------");
			System.out.println();
			System.out.print(" 이   름\t: ");
			String username = scan.nextLine();
			System.out.println();
			System.out.print(" 전화 번호\t: ");
			String usercp = scan.nextLine();
			System.out.println();
			System.out.print(" 주   소\t: ");
			String useraddr = scan.nextLine();
			System.out.println();
			System.out.print(" 상세 주소\t: ");
			String userDaddr = scan.nextLine();
			System.out.println();
			
			nuser.setNonuserName(username);
			nuser.setNonuserCp(usercp);
			nuser.setNonuserAddr(useraddr);
			nuser.setNonuserDetailAddr(userDaddr);

			if(nudao.create(nuser) == true)	
				return usercp;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

	public String JoinUser() {

		String userid;
		String userpw;
		String username;
		String useraddr;
		String userDaddr;
		String usercp;
		

		try {
			
			System.out.println();
			System.out.println("-----------------------------------------------------");
			System.out.println();
			System.out.println("                  ○  회  원  가  입  ○");
			System.out.println();
			//아이디 입력
			while (true) {
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("ID를 입력해 주십시오 (15글자 제한)  ");
				System.out.println();
				System.out.printf("    %-10s\t: ","I      D");
				userid = scan.nextLine();
				
				User vo = udao.selectById(userid);
				
				//빈칸일시
				if(userid == "") {
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                아이디는 필수 입력 사항입니다.");
					System.out.println("                  다시 시도하여 주십시오.");
					System.out.println();
					continue;
				} else if(vo == null) {
					break;
				} else {
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                 중복된 아이디가 존재합니다.");
					System.out.println("                  다시 시도하여 주십시오.");
					System.out.println();
					continue;
				}
			}
			System.out.println();
			
			//비밀번호 입력
			while(true){
				
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("Password를 입력해 주십시오.");
				System.out.println();
				System.out.printf("    %-10s\t: ","Password");
				userpw = scan.nextLine();

				if(userpw != "") {
					break;
				} else {
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("              비밀 번호는 필수 입력 사항입니다.");
					System.out.println("                 다시 시도하여 주십시오.");
					System.out.println();
					continue;
				}
			}
			System.out.println();

			//이름 입력
			while (true) {
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("이름을 입력해 주십시오.");
				System.out.println();
				System.out.printf("    %-9s\t: ","이     름");
				username = scan.nextLine();	
				if (username != "") {
					break;
				} else {
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                이름은 필수 입력 사항입니다.");
					System.out.println("                 다시 시도하여 주십시오.");
					System.out.println();
					continue;
				}
			}
			System.out.println();

			//전화번호 입력
			while (true) {
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("전화번호를 입력해 주십시오. 숫자만 입력해 주십시오.");
				System.out.println();
				System.out.printf("    %-8s\t: ","전 화 번 호");
				usercp = scan.nextLine();
				if (usercp != "") {
					break;
				} else {
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("               전화번호는 필수 입력 사항입니다.");
					System.out.println("                  다시 시도하여 주십시오.");
					System.out.println();
					continue;
				}
			}
			System.out.println();
			
			//주소 입력
			while (true) {
				System.out.println("-----------------------------------------------------");
				System.out.println( );
				System.out.println("주소를 입력해 주십시오.");
				System.out.println();
				System.out.printf("    %-9s\t: ","주     소");
				useraddr = scan.nextLine();

				System.out.println();
				if (useraddr != "") {
					break;
				} else {
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                주소는 필수 입력 사항입니다.");
					System.out.println("                  다시 시도하여 주십시오.");
					System.out.println();
					continue;
				}
			}
			
			while (true) {
				System.out.println("-----------------------------------------------------");
				System.out.println( );
				System.out.println("주소를 입력해 주십시오.");
				System.out.println();
				System.out.printf("    %-9s\t: ","상 세  주 소");
				userDaddr = scan.nextLine();

				System.out.println();
				if (userDaddr != "") {
					break;
				} else {
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                상세 주소는 필수 입력 사항입니다.");
					System.out.println("                  다시 시도하여 주십시오.");
					System.out.println();
					continue;
				}
			}

			System.out.println();
			
			
			

			User user = new User();
			
			System.out.println(userid + " " +username + " " +useraddr + " " );
			
			user.setUserId(userid);
			user.setUserPwd(userpw);
			user.setUserName(username);
			user.setUserCp(usercp);
			user.setUserAddr(useraddr);
			user.setUserDetailAddr(userDaddr);

			if(udao.create(user) ==true)	
				return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

}

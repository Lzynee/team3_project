package view;

import java.util.Scanner;

import dao.BillDao;

public interface CommonView {

	Scanner scan = new Scanner(System.in);

	public default void exit() {
		System.out.println("** 프로그램 종료 **");
		System.exit(0);
	}


	// 작성일 : 2023-11-06
	//설명 : 요금 계산 ( 조건 변경에 따른 로직 변경 )
	// 작성자 : 이창규
	public default int cost(String flwOptName , String smlSize, int mass) {

		int fCharge = 0;
		int midCharge = 0;



		if (flwOptName.equals("기념일 화분")) {
			fCharge = 70000;
		} else if (flwOptName.equals("기념일 꽃다발")){
			fCharge = 50000;
		} else if (flwOptName.equals("경조사 화분")) {
			fCharge = 60000;
		} else if (flwOptName.equals("경조사 화환")) {
			fCharge = 80000;
		}

		if (smlSize.equals("S")) {
			midCharge = fCharge-10000;
		} else if (smlSize.equals("M")) {
			midCharge = fCharge;
		} else if (smlSize.equals("L")) {
			midCharge = fCharge+10000;
		}
			return midCharge * mass;

  }







	//설명 : 주소 값을 받아 분리하는 기능

	public default int getZipCode(String line) {

		String[] strToStrArray = line.split(" ");
		BillDao bDao = new BillDao();
		int zipcode = 0;

		// 가평군 등의 '군'의 경우
		if (strToStrArray.length == 4) {
			String[] numTobunum = strToStrArray[3].split("-"); // 지역번호,부번호 나누기
			if (numTobunum.length >= 2)  //부번호가 있을때
				zipcode = bDao.selectzipcode(strToStrArray[0], strToStrArray[1], strToStrArray[2],
						Integer.parseInt(numTobunum[0]), Integer.parseInt(numTobunum[1]));
			else //부번호가 없을때
				zipcode = bDao.selectzipcode(strToStrArray[0], strToStrArray[1], strToStrArray[2],
						Integer.parseInt(numTobunum[0]));

		} 
		else { 	// 안산시 등의 '시'의 경우
			String[] numTobunum = strToStrArray[4].split("-"); // 지역번호,부번호 나누기
			if (numTobunum.length >= 2) //부번호가 있을때
				zipcode = bDao.selectzipcode(strToStrArray[0], strToStrArray[1] + " " + strToStrArray[2],
						strToStrArray[3], Integer.parseInt(numTobunum[0]), Integer.parseInt(numTobunum[1]));
			else //부번호가 없을때
				zipcode = bDao.selectzipcode(strToStrArray[0], strToStrArray[1] + " " + strToStrArray[2],
						strToStrArray[3], Integer.parseInt(numTobunum[0]));
		}

		return zipcode;
	}

	// 수정일자 : 2023-11-08(이창규)
	public default String payView(int cost, int surcharge) {

		try {

			System.out.println("-----------------------------------------------------");
			System.out.println();
			System.out.println("                     [ 결    제 ]");
			System.out.println();
			// 수정일자 : 2023-11-08(이창규)
			System.out.println("상품 요금 : " + cost +"원과 지역별 요금 " + surcharge+"을 합쳐");
			//System.out.println(" 사이즈별 요금 " + cost +"x"+ cnt +"원과 지역별 요금 " + surcharge+"을 합쳐");
			System.out.println(" 총 요금은 " + (cost+surcharge)+"원 입니다.");
			//System.out.println(" 총 요금은 " + (cost*cnt+surcharge)+"원 입니다.");
			System.out.println();
			System.out.println(" 1. 결제   2. 취소");
			System.out.println("-----------------------------------------------------");
			System.out.print(" 메뉴 선택 : ");
			String menuNo = scan.nextLine();

			if ("1".equals(menuNo)) {
				return "success";
			} else {
				return "fail";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
	
	public default String payView(int cost, int surcharge, int discount) {

		try {

			System.out.println("-----------------------------------------------------");
			System.out.println();
			System.out.println("                      [ 결    제 ]");
			System.out.println();
			// 수정일자 : 2023-11-08(이창규)
			System.out.println(" 상품 요금 " + cost + "원과 지역별 요금 " + surcharge+"을 합쳐");
			//System.out.println(" 사이즈별 요금 " + cost +"x"+ cnt +"원과 지역별 요금 " + surcharge+"을 합쳐");
			System.out.println(" 총 요금은 " + (cost+surcharge)+"원 입니다.");
			//System.out.println(" 총 요금은 " + (cost*cnt+surcharge)+"원 입니다.");
			if(discount == 200) {
				System.out.println(" 또한 회원 님의 등급인 플래티넘 등급은 " + discount +"원 할인이 되므로");
			} else if(discount == 500) {
				System.out.println(" 또한 회원 님의 등급인 VIP 등급은 " + discount +"원 할인이 되므로");
			} else {
				System.out.println(" 또한 회원 님의 등급인 VVIP 등급은 " + discount +"원 할인이 되므로");
			}
			System.out.println(" 총 요금은 " +(cost+surcharge-discount) + "원 입니다.");
			System.out.println();
			System.out.println(" 1. 결제   2. 취소");
			System.out.println("-----------------------------------------------------");
			System.out.print(" 메뉴 선택 : ");
			String menuNo = scan.nextLine();
			System.out.println();
			
			if ("1".equals(menuNo)) {
				return "success";
			} else {
				return "fail";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

	public default String message() {
		
		String msg  ="";
		
		while (true) {

			
// 출력 메시지 일부 수정 === (Nov.06 이양진)
			System.out.println("-----------------------------------------------------");
			System.out.println("                      꽃배달 요청사항");
			System.out.println();
			System.out.println("      꽃배달 요청 사항을 선택하시면 바로 영수증이 출력됩니다.");
			System.out.println();
			System.out.println("\t      1. 선택 안함");
			System.out.println("\t      2. 배송 전 연락주세요");
			System.out.println("\t      3. 빠른 배송 부탁드립니다.");
			System.out.println("\t      4. 부재 시, 경비실에 맡겨주세요.");
			System.out.println();
			System.out.println("\t      0을 입력시 직접입력 창으로 이동합니다.");
			System.out.println("-----------------------------------------------------");
			System.out.print(" 요청사항 선택 : ");
			int menuNum = Integer.parseInt(scan.nextLine());
			System.out.println();
			
			switch (menuNum) {

			case 0:
				System.out.println("-----------------------------------------------------");
				System.out.print(" 꽃배달 요청사항(30자 제한) : ");
				msg = scan.nextLine();
				break;
			case 1:
				msg = "요청사항 없음";
				break;
			case 2:
				msg = "배송 전 연락주세요";
				break;
			case 3:
				msg = "빠른 배송 부탁드립니다.";
				break;
			case 4:
				msg = "부재 시 경비실에 맡겨주세요";
				break;
			default:
				System.out.println("다시 시도해 주십시오");
				continue;
			}
			
			break;
		}
		
		return msg;
	}
	
}

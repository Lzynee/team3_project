package view;

import dao.SuperDao;

public class OrderView implements CommonView {

	private static OrderView view = new OrderView();

	
	public void order(String userIdOrCP, Boolean isUser) {

		String productName = "";


		try {
			while (true) {

				// 화면 출력
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("               [ 상품 카테고리 선택 ]"); // 간격 조절
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("          1. 기념일               2. 경조사"); // 간격 조절
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.print("카테고리 선택(메뉴 외 값 입력시 재선택): ");
				String categoryNo = scan.nextLine();
				System.out.println();

				if ("1".equals(categoryNo)) {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                   [ 기념일 ]");
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("     1. 화분(7만원)            2. 꽃다발(5만원)"); // 가격 추가
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.print("상품 선택(메뉴 외 값 입력시 처음부터 재선택): ");
					String menuNo = scan.nextLine();
					if ("1".equals(menuNo)) {
						productName = "기념일 화분";
					} else if ("2".equals(menuNo)) {
						productName = "기념일 꽃다발";
					} else {
						System.out.println("메뉴에 없는 값을 입력하셨습니다. 처음부터 다시 선택해주세요.");
						continue;
					}

				} else if ("2".equals(categoryNo)) {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                   [ 경조사 ]");
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("     1. 화분(6만원)              2. 화환(8만원)"); // 가격 추가
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.print("상품 선택(메뉴 외 값 입력시 처음부터 재선택): ");
					String menuNo = scan.nextLine();
					if ("1".equals(menuNo)) {
						productName = "경조사 화분";
					} else if ("2".equals(menuNo)) {
						productName = "경조사 화환";
					} else {
						System.out.println("메뉴에 없는 값을 입력하셨습니다. 처음부터 다시 선택해주세요.");
						continue;
					}

				} else {
					System.out.println("메뉴에 없는 값을 입력하셨습니다. 재선택 해주세요.");
					continue;
				}

				if(isUser){
				//flwoptinfoView로 userId나 nonusercp, productName넘겨줌
					//flwoptinfoView.getinstance().info(userIdOrCP);
					FlwOptinfoView.getinstance().info(userIdOrCP, productName);
					break;
				}else{
					FlwOptinfoViewNonuser.getinstance().info(userIdOrCP, productName);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static void main(String[] args) {
		SuperDao.Load();
		view.order(null,null);
		SuperDao.close();
	}

	public static OrderView getinstance() {
		return view;
	}
}

package view;

import dao.ParcelDao;
import dao.SuperDao;
import model.Parcel;

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
				System.out.println("                   [ 상품 카테고리 선택 ]");
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.printf("\t%-20s\t%-20s\n", "1. 경조사", "2. 기념일");
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.print("카테고리 선택(메뉴 외 값 입력시 재선택): ");
				String categoryNo = scan.nextLine();
				System.out.println();

				if ("1".equals(categoryNo)) {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                   [ 경조사 ]");
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.printf("\t%-20s\t%-20s\n", "1. 화분", "2. 화환");
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

				} else if ("2".equals(categoryNo)) {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                   [ 기념일 ]");
					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.printf("\t%-20s\t%-20s\n", "1. 화분", "2. 꽃다발");
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

				} else {
					System.out.println("메뉴에 없는 값을 입력하셨습니다. 재선택 해주세요.");
					continue;
				}

				if(isUser){
				//ParcelinfoView로 userId나 nonusercp, productName넘겨줌
					ParcelinfoView.getinstance().info(userIdOrCP);
				//ParcelinfoView.getinstance().info(userIdOrCP, productName);
					break;
				}else{
					ParcelinfoViewNonuser.getinstance().info(userIdOrCP);
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

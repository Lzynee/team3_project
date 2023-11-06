package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import dao.SuperDao;
import model.User;

public class MainView {


	public static void exit() {
		System.out.println("** 프로그램 종료 **");
		System.exit(0);
	}

	public static void main(String[] args) {

		UserView userV = new UserView();
		Scanner scan = new Scanner(System.in);
		String userId;

		SuperDao.Load();

		while (true) {
			//초기 배너 FLOWER 출력
			//FLOWER 표출 문자 크기 조정 (11/6)
			int width = 130 * 2 / 3;
			int height = 20 * 2 / 3;

			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.setFont(new Font("SansSerif", Font.BOLD, 15));

			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics.drawString("FLOWER", 7, 13);

			for (int y = 0; y < height; y++) {
				StringBuilder sb = new StringBuilder();
				for (int x = 7; x < width; x++) {
					sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");
				}

				if (sb.toString().trim().isEmpty()) {
					continue;
				}

				System.out.println(sb);
			}

			System.out.println("-----------------------------------------------------------------");
			System.out.println();
			System.out.println("                           [ 메인 메뉴 ]");
			System.out.println();
			System.out.println("-----------------------------------------------------------------");
			System.out.println();
			System.out.printf("\t%-20s\t%-20s\n", " 1. 서비스 접수(회원)", "  2. 서비스 접수(비회원)");
			System.out.println();
			System.out.printf("\t%-20s\t%-60s\n", " 3. 회원 가입", "      4. 주문 접수 조회");
			System.out.println();
			System.out.println();
			System.out.println("다른 숫자 입력시 종료됩니다.");
			System.out.println("-----------------------------------------------------------------");

			System.out.print("메뉴 선택 : ");
			String menuNo = scan.nextLine();
			System.out.println();

			if ("1".equals(menuNo)) {
				userId = userV.Login();

				if (userId != "fail") {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                  로그인에 성공하셨습니다.");
					System.out.println();

					OrderView.getinstance().order(userId, true); //ParcelinfoView.getinstance().info(userId);
				} else {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                  로그인에 실패하셨습니다.");
					System.out.println();
					continue;
				}

			} else if ("2".equals(menuNo)) {
				String nonusercp = userV.Non_userlogin();

				if (nonusercp != "fail") {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("               비회원 로그인에 성공하셨습니다.");
					System.out.println();

					OrderView.getinstance().order(nonusercp, false);//ParcelinfoViewNonuser.getinstance().info(nonusercp);
				} else {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("       비회원 로그인에 실패하셨습니다. 다시 시도해 주십시오");
					continue;
				}

			} else if ("3".equals(menuNo)) {

				String sign = userV.JoinUser();
				if (sign != "fail") {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("                 회원 가입이 완료되었습니다."); //되셨습니다->되었습니다 문구 수정(차소영, 11/5)
					System.out.println(); // 엔터 추가 (11/6 수정)
					continue;
				} else {
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("         회원 가입에 실패하셨습니다. 다시 시도해 주십시오");
					continue;
				}

			} else if ("4".equals(menuNo)) {
				// 주문접수 조회 기능
				WaybillView.getinstance().wbList();
				continue;
			} else {
				SuperDao.close();
				exit();
			}

		}

	}
	

}

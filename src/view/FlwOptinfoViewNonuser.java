package view;

import dao.FlwOptDao;
import model.FlwOpt;

public class FlwOptinfoViewNonuser implements CommonView {

	private static FlwOptinfoViewNonuser view = new FlwOptinfoViewNonuser();

	public void info(String nonUserCp,String flwOptName) {

		FlwOptDao fdao = new FlwOptDao();
		// 조건이 변경되었으므로 해당 변수들은 주석 처리함.
//		int width = 0;
//		int length = 0;
//		int height = 0;
		int cost = 0;
		int mass = 0;
		int totalCharge = 0;
		// 사이즈 선택 변수 초기화 및 선언
		String smlSize = "";
		String volume = "";
		
		try {
			// 화면 출력
			while (true) {
				System.out.println("-----------------------------------------------------");
				System.out.println();
				// 무게 => 수량 , 크기 => 사이즈로 문장 변경
				System.out.println("                [ 수량 및 사이즈 입력 ]"); // 문구 수정, 간격 조절
				System.out.println();
				// 무게 => 수량 , 크기 => 사이즈로 문장 변경
				System.out.println("              수량과 사이즈를 입력해 주세요");
				System.out.println("-----------------------------------------------------");

				// 수량별 요금 계산
				// 로직 변경 ( 이창규 )
				while (true) {
					System.out.print("수량(개) : ");
					mass = Integer.parseInt(scan.nextLine());

					if (mass < 1) {
						System.out.println("수량을 확인하여 다시 입력해주세요.");
						continue;
					}
					break;
				}
				
				System.out.println();
				System.out.println("-----------------------------------------------------");
				


				// 사이즈 선택 과정
				// 출력 문구 로직 변경
				// 조건 변경에 따른 if 문으로 로직 변경
				// 수정 일시 : 2023.11.05
				// 작성자 : 이창규
				while (true) {

					System.out.println();
					System.out.println("사이즈를 골라 입력해 주세요.");
					System.out.println();
					System.out.print("S / M / L : ");
					smlSize = scan.nextLine().toUpperCase();

					if (smlSize.equals("S")) {
						System.out.println("Small 사이즈 선택");
						break;
					} else if (smlSize.equals("M")) {
						System.out.println("Medium 사이즈 선택");
						break;
					} else if (smlSize.equals("L")) {
						System.out.println("Large 사이즈 선택");
						break;
					} else {
						System.out.println("유효하지 않은 사이즈입니다. 다시 시도하세요.");
						continue;
					}

				}

				totalCharge =mass*subCharge(flwOptName,smlSize);



				// 크기 정의
				// volume = String.format("%d*%d*%d(cm)", width, length, height);
				// 입력된 값에 맞게 출력문 수정
				// 수정 일시 : 2023.11.05
				// 작성자 : 이창규
				volume = String.format("%s", smlSize);
				// 번호 설정
				int flwOptNum = fdao.selectCountId();

				// 입력값들 set
				FlwOpt flwOpt = new FlwOpt();
				flwOpt.setFlwOptNo(flwOptNum);
				flwOpt.setFlwOptName(flwOptName);
				flwOpt.setFlwOptFee(totalCharge);
				flwOpt.setFlwOptWeight(mass);
				flwOpt.setFlwOptSize(volume);

				// 마지막 확인 화면 출력
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("                   ○ 운송물 정보 확인 ○");
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				// 크기 => 사이즈
				System.out.printf(" | 내용 : %s || 사이즈 : %s || 무게 : %d |\n", flwOptName, volume, mass);
				System.out.println();
				System.out.println("고르신 상품 요금은 : "+totalCharge );
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("1. 받는 사람 정보 화면으로 2. 정보 다시 입력 3. 메인 메뉴로");
				System.out.println();
				System.out.print("메뉴 선택: ");
				String menuNo = scan.nextLine();

				if ("1".equals(menuNo)) {
					ToReceiverInfoViewNonuser.getinstance().info(nonUserCp, flwOpt, cost);
					break;
				} else if ("2".equals(menuNo)) 
					continue;
				else return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static FlwOptinfoViewNonuser getinstance() {
		return view;
	}

}

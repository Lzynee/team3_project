package view;

import dao.ParcelDao;
import dao.SuperDao;
import model.Parcel;

public class ParcelinfoViewNonuser implements CommonView {

	private static ParcelinfoViewNonuser view = new ParcelinfoViewNonuser();

	public void info(String nonUserCp,String parcelName) {

		ParcelDao pdao = new ParcelDao();
		// 조건이 변경되었으므로 해당 변수들은 주석 처리함.
//		int width = 0;
//		int length = 0;
//		int height = 0;
		int cost = 0;
		int mass = 0;

		// 사이즈 선택 변수 초기화 및 선언
		String smlSize = "";
		String volume = "";
		
		try {
			// 화면 출력
			while (true) {
				System.out.println("-----------------------------------------------------");
				System.out.println();
				// 무게 => 수량 , 크기 => 사이즈로 문장 변경
				System.out.println("                   [ 수량 및 사이즈 측정 ]");
				System.out.println();
				// 무게 => 수량 , 크기 => 사이즈로 문장 변경
				System.out.println("                 수량과 사이즈를 입력해 주세요");
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
				
				cost = costs(mass);

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

					// --------------------------------

//					System.out.println();
//					System.out.println("사이즈를 선택해 주세요.");
//					System.out.println();
//					System.out.print("가로(cm) : ");
//					width = Integer.parseInt(scan.nextLine());
//					System.out.println();
//					System.out.print("세로(cm) : ");
//					length = Integer.parseInt(scan.nextLine());
//					System.out.println();
//					System.out.print("높이(cm) : ");
//					height = Integer.parseInt(scan.nextLine());
//					System.out.println();
//
//					if (width + length + height > 160 || width > 100 || length > 100 || height > 100) {
//						System.out.println("가능한 규격이 아닙니다.");
//						continue;
//
					// --------------------------------

				}

				// 크기 정의
				// volume = String.format("%d*%d*%d(cm)", width, length, height);
				// 입력된 값에 맞게 출력문 수정
				// 수정 일시 : 2023.11.05
				// 작성자 : 이창규
				volume = String.format("%s", smlSize);
				// 번호 설정
				int parcelNum = pdao.selectCountId();

				// 입력값들 set
				Parcel parcel = new Parcel();
				parcel.setParcelNo(parcelNum);
				parcel.setParcelName(parcelName);
				parcel.setParcelFee(cost);
				parcel.setParcelWeight(mass);
				parcel.setParcelSize(volume);

				// 마지막 확인 화면 출력
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("                   ○ 운송물 정보 확인 ○");
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				// 크기 => 사이즈
				System.out.printf(" | 내용 : %s || 사이즈 : %s || 무게 : %d |\n", parcelName, volume, mass);
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("1. 받는 사람 정보 화면으로 2. 정보 다시 입력 3. 메인 메뉴로");
				System.out.println();
				System.out.print("메뉴 선택: ");
				String menuNo = scan.nextLine();

				if ("1".equals(menuNo)) {
					ToReceiverInfoViewNonuser.getinstance().info(nonUserCp, parcel, cost);
					break;
				} else if ("2".equals(menuNo)) 
					continue;
				else return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static ParcelinfoViewNonuser getinstance() {
		return view;
	}

}

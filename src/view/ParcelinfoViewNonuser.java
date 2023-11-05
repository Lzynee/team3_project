package view;

import dao.ParcelDao;
import dao.SuperDao;
import model.Parcel;

public class ParcelinfoViewNonuser implements CommonView {

	private static ParcelinfoViewNonuser view = new ParcelinfoViewNonuser();

	public void info(String nonUserCp,String parcelName) {

		ParcelDao pdao = new ParcelDao();
		int width = 0;
		int length = 0;
		int height = 0;
		int cost = 0;
		int mass = 0;


		String volume = "";
		
		try {
			// 화면 출력
			while (true) {
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("                     [무게 및 크기 측정]");
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println(" 무게와 크기를 입력해 주세요");
				System.out.println();

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

				//규격 확인


				while (true) {
					System.out.println();
					System.out.println("                  크기를 입력해 주세요.");
					System.out.println();
					System.out.print("가로(cm) : ");
					width = Integer.parseInt(scan.nextLine());
					System.out.println();
					System.out.print("세로(cm) : ");
					length = Integer.parseInt(scan.nextLine());
					System.out.println();
					System.out.print("높이(cm) : ");
					height = Integer.parseInt(scan.nextLine());
					System.out.println();
					
					if (width + length + height > 160 || width > 100 || length > 100 || height > 100) {
						System.out.println("                 가능한 규격이 아닙니다.");
						System.out.println();
						continue;
					}
					break;
				}

				// 크기 정의
				volume = String.format("%d*%d*%d(cm)", width, length, height);

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
				System.out.printf("    | 내용 : %s || 크기 : %s || 무게 : %d |\n", parcelName, volume, mass);
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

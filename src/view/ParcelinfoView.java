package view;

import dao.ParcelDao;
import dao.SuperDao;
import model.Parcel;

public class ParcelinfoView implements CommonView {

	private static ParcelinfoView view = new ParcelinfoView();

	
	public void info(String userId) {

		ParcelDao pdao = new ParcelDao();

		int width = 0, length = 0, height = 0;
		int cost = 0, mass = 0;
		String parcelName = "";
		String volume = "";

		try {
			while (true) {

				// 화면 출력
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("                   [ 상품 정보 입력창 ]");
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.print("상품명 (10 글자 제한) : ");
				parcelName = scan.nextLine();
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("                   [ 무게 및 크기 측정 ]");
				System.out.println();
				System.out.println(" 무게와 크기를 입력해 주세요");
				System.out.println("-----------------------------------------------------");

				// 무게당 요금 계산
				while (true) {
					System.out.print("무게(kg) : ");
					mass = Integer.parseInt(scan.nextLine());

					if (mass > 20) {
						System.out.println("20kg 초과는 보낼수 없습니다.");
						continue;
					}
					break;
				}
				
				System.out.println();
				System.out.println("-----------------------------------------------------");
				
				
				cost = costs(mass);

				// 규격 확인

				while (true) {
					
					System.out.println();
					System.out.println("크기를 입력해 주세요.");
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
						System.out.println("가능한 규격이 아닙니다.");
						continue;
					}
					break;
				}
			
				// 크기 정의
				volume = String.format("%d*%d*%d(cm)", width, length, height);

				int parcelNum = pdao.selectCountId() + 1;

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
				System.out.printf(" | 내용 : %s || 크기 : %s || 무게 : %d |\n", parcelName, volume, mass);
				System.out.println();
				System.out.println("1. 받는 사람 정보 화면으로 2. 정보 다시 입력 3. 메인 메뉴로");
				System.out.println("-----------------------------------------------------");
				System.out.print(" 메뉴 선택 : ");
				String menuNo = scan.nextLine();

				if ("1".equals(menuNo)) {
					ToReceiverInfoView.getinstance().info(userId, parcel, cost);
					break;
				} else if ("2".equals(menuNo)) 
					continue;
				else return;
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static void main(String[] args) {
		SuperDao.Load();
		view.info(null);
		SuperDao.close();
	}

	public static ParcelinfoView getinstance() {
		return view;
	}
}

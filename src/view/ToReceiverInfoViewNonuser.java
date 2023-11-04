package view;

import dao.ParcelDao;
import dao.SuperDao;
import dao.WaybillDao;
import model.Parcel;
import model.Waybill;

public class ToReceiverInfoViewNonuser implements CommonView{
	
	private static ToReceiverInfoViewNonuser view = new ToReceiverInfoViewNonuser();

	private int comindex = 0;
	
	private String [] companyCd = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20"} ;

	
	// 받는곳 입력
	public void info(String nonUserCp, Parcel parcel, int cost) {
		WaybillDao wbDao = new WaybillDao();
		ParcelDao pDao = new ParcelDao();

		try {
			while (true) {
				// 화면 출력
				// 받는 분 정보 입력
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("                 받는사람 정보를 입력해 주세요");
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.print("  받는 사람 이름\t: ");
				String ReceiverName = scan.nextLine();
				System.out.println();
				System.out.print("  받는 사람 전화번호 : ");
				String ReceiverCp = scan.nextLine();
				System.out.println();
				System.out.print("  받는 사람 주소\t: ");
				String ReceiverAddr = scan.nextLine();
				System.out.println();
				System.out.print("  받는 사람 상세 주소\t: ");
				String ReceiverDetailAddr = scan.nextLine();
			
				// 우편번호 찾기
				// 집에서 zipcode() 불가!!!

				int zipcode = getZipCode(ReceiverAddr);

				// 넘겨 받은 parcelNum 의 왼쪽의 공백을 0으로 채움
				String parcelNumStr = String.format("%05d", parcel.getParcelNo());

				// 도서 산간지역 요금 추가
				int surcharge = 0;

				if ((63002 <= zipcode && zipcode <= 63364) || (63500 <= zipcode && zipcode <= 63621)) { // 제주도 우편번호
					surcharge = 4000;
				}

				// 무게당 요금과 도서 산간지역을 합쳐 최종 요금 계산
				int totalFee = parcel.getParcelFee() + surcharge;


				// 우편번호와 상품 번호를 조합하여 영수증 번호 생성
				String wbNum = parcelNumStr + zipcode;

				// 운송장 기본 정보 입력
				Waybill wayBill = new Waybill();
				wayBill.setWaybillNo(wbNum);
				wayBill.setTotalFee(totalFee);
				wayBill.setRcvrName(ReceiverName);
				wayBill.setRcvrAddr(ReceiverAddr);
				wayBill.setRcvrDetailAddr(ReceiverDetailAddr);
				wayBill.setRcvrCp(ReceiverCp);
				wayBill.setCompanyCd(companyCd[comindex++]); // 코드는 나중에 수정필요
				wayBill.setNonCp(nonUserCp);
				parcel.setWaybillNo(wbNum);
				
				// 받는 사람 정보 확인
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.println("                   ○ 받는 사람 정보 확인 ○");
				System.out.println();
				System.out.println("-----------------------------------------------------");
				System.out.println();
				System.out.printf("    | 이름 : %s || 전화번호 : %s |\n", ReceiverName, ReceiverCp);
				System.out.println();
				System.out.printf("    | 주소 : %s |\n", ReceiverAddr + " " + ReceiverDetailAddr);
				System.out.println();
				System.out.println("1. 결제 화면으로  2. 받는 사람 정보 다시 입력  3. 메인 메뉴로");
				System.out.println("-----------------------------------------------------");

				System.out.print("메뉴 선택: ");
				String menuNo = scan.nextLine();
				System.out.println();
				
				if ("1".equals(menuNo)) {

					String sign = payView(cost, surcharge);

					if (sign != "fail") {
						
						System.out.println("-----------------------------------------------------");
						System.out.println();
						System.out.println("                     결 제   완 료");
						System.out.println();
						System.out.println("-----------------------------------------------------");
						
						// 결제 완료 시 요청사항 작성
						String msg = message();
						wayBill.setMsg(msg);
						
						// 결제 완료 시 영수증데이터 생성
						
						wbDao.create(wayBill);
						pDao.create(parcel);
						WaybillView.getinstance().waybillInfo(wayBill,parcel);
						break;
					} else {
						System.out.println("-----------------------------------------------------");
						System.out.println();
						System.out.println("           결제 취소 되었습니다. 다시 시도해 주십시오.");
						System.out.println();
						System.out.println("-----------------------------------------------------");
						continue;
					}

				} else if ("2".equals(menuNo))
					continue;
				else 
					return;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

		
	public static ToReceiverInfoViewNonuser getinstance()
	{
		return view;
	}

}

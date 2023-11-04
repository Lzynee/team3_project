package view;

import java.util.List;

import dao.ParcelDao;
import dao.SuperDao;
import dao.UserDao;
import dao.WaybillDao;
import model.Parcel;
import model.User;
import model.Useraddress;
import model.Waybill;

public class ToReceiverInfoView implements CommonView {

	private static ToReceiverInfoView view = new ToReceiverInfoView();

	private int comindex = 0;

	private String[] companyCd = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20" };

	// 받는곳 입력
	public void info(String userId, Parcel parcel, int cost) {
		WaybillDao wbDao = new WaybillDao();
		UserDao uDao = new UserDao();
		ParcelDao pDao = new ParcelDao();
		User user = new User();
		
		String ReceiverName = "";
		String ReceiverAddr = "";
		String ReceiverCp = "";
		String ReceiverDetailAddr = "";
		int zipcode = 0;
		int totalFee = 0;
		try {
			// 화면 출력
			while (true) {

				while (true) {

					System.out.println();
					System.out.println("-----------------------------------------------------");
					System.out.println();
					System.out.println("               [ 받는 사람 정보 입력 창 메뉴 ]");
					System.out.println();
					System.out.println("  1. 즐겨 찾기에서 선택  2. 직접 입력 ");
					System.out.println("-----------------------------------------------------");
					System.out.print(" 메뉴 선택 : ");
					int menuNo = Integer.parseInt(scan.nextLine());
					System.out.println();

					if (menuNo == 1) {

						List<Useraddress> list = uDao.selectAddrAll(userId);
						
						// 즐겨찾기 DB list가 있다면 리스트를 출력한다.
						if (list.size() != 0) {

							System.out.println("---------------------------------------------------------------");
							System.out.println();
							System.out.println(" 번호\t받는 사람 이름\t받는사람 전화번호\t      받는사람 주소"); 
							System.out.println();
							System.out.println("---------------------------------------------------------------");
							// 즐겨찾기 리스트 출력
							for (int i = 0; i < list.size(); i++) {
								System.out.println();
								System.out.println("  "+(i + 1) + "\t " + list.get(i).getRcvrName() + "\t\t"
										+ list.get(i).getRcvrCp() + "\t  " + list.get(i).getRcvrAddr() +" "+list.get(i).getRcvrDetailAddr());
							}
							System.out.println();
							System.out.println("-----------------------------------------------------");
							System.out.println();
							System.out.print("  몇번 즐겨찾기를 사용하시겠습니까? : ");
							int num = Integer.parseInt(scan.nextLine());
							System.out.println();
							System.out.println("-----------------------------------------------------");
							System.out.println();
							System.out.println("  이름 : " + list.get(num - 1).getRcvrName() + "\n  주소 : "
									+ list.get(num - 1).getRcvrAddr() + "\n  전화 번호 : " + list.get(num - 1).getRcvrCp()+" "+list.get(num-1).getRcvrDetailAddr());
							System.out.println();
							System.out.println("  이 받는 사람 정보가 맞습니까?");
							System.out.println();
							System.out.println("-----------------------------------------------------");
							System.out.println();
							System.out.println("  1. 해당 즐겨찾기 선택  2. 취소");
							System.out.println();
							System.out.println("-----------------------------------------------------");
							System.out.print(" 메뉴 선택 : ");
							String subMenuNo = scan.nextLine();
							System.out.println();
							
							if ("1".equals(subMenuNo)) {

								ReceiverName = list.get(num - 1).getRcvrName();
								ReceiverAddr = list.get(num - 1).getRcvrAddr();
								ReceiverCp = list.get(num - 1).getRcvrCp();
								ReceiverDetailAddr = list.get(num - 1).getRcvrDetailAddr();

								System.out.println("해당 즐겨찾기를 선택하셨습니다.");
								break;
							} else {
								System.out.println("해당 즐겨찾기 선택을 취소하셨습니다.");
								continue;
							}
							
						// 리스트 사이즈가 0이면 바로 보낸사람 정보입력창으로 이동	
						} else {
							System.out.println("즐겨 찾기에 등록된 정보가 없습니다.");
							System.out.println("받는 사람 정보 입력 창으로 이동합니다.");

							menuNo = 2;
						}
					}
					
					if (menuNo == 2) {

						System.out.println();
						System.out.println("-----------------------------------------------------");
						System.out.println();
						System.out.println("                 [ 받는 사람 정보 입력 ]");
						System.out.println();
						System.out.println("-----------------------------------------------------");
						System.out.println();
						System.out.print(" 받는 사람 성함 : ");
						ReceiverName = scan.nextLine();
						System.out.println();
						System.out.print(" 받는 사람 전화번호 : ");
						ReceiverCp = scan.nextLine();
						System.out.println();
						System.out.print(" 받는 사람 주소 : ");
						ReceiverAddr = scan.nextLine();
						System.out.println();
						System.out.print(" 받는 사람 상세 주소 : ");
						ReceiverDetailAddr = scan.nextLine();
						System.out.println();
						System.out.println("-----------------------------------------------------");
						System.out.println();
						System.out.println("       이 받는 사람 정보를 즐겨찾기에 저장하시겠습니까?");
						System.out.println();
						System.out.println(" 1. 저장    2. 저장하지 않고 계속");
						System.out.println("-----------------------------------------------------");
						System.out.print(" 메뉴 선택 : ");
						String subMenuNo = scan.nextLine();
						
						if ("1".equals(subMenuNo)) {
							Useraddress Uaddr = new Useraddress();
							Uaddr.setUserId(userId);
							Uaddr.setRcvrName(ReceiverName);
							Uaddr.setRcvrAddr(ReceiverAddr);
							Uaddr.setRcvrCp(ReceiverCp);
							Uaddr.setRcvrDetailAddr(ReceiverDetailAddr);
							uDao.createUserAddress(Uaddr);
							System.out.println("-----------------------------------------------------");
							System.out.println();
							System.out.println("                즐겨찾기에 저장되었습니다");
							break;
						} else {
							break;
						}
						
					} else {
						System.out.println("-----------------------------------------------------");
						System.out.println();
						System.out.println("                  잘못 입력하셨습니다");
						continue;
					}
				}
				// 우편번호 찾기
				zipcode = getZipCode(ReceiverAddr);

				
				// 넘겨 받은 parcelNum 의 왼쪽의 공백을 0으로 채움
				String parcelNumStr = String.format("%05d", parcel.getParcelNo());

				// 우편번호와 상품번호를 조합하여 운송장 번호 생성
				String wbNum = parcelNumStr + zipcode;


				// 도서 산간지역 요금 추가
				int surcharge = 0;

				if ((63002 <= zipcode && zipcode <= 63364) || (63500 <= zipcode && zipcode <= 63621)) { // 제주도 우편번호
					surcharge = 4000;
				}

				// 무게당 요금과 도서 산간지역을 합쳐 최종 요금 계산

				// 운송장 기본 정보 입력
				Waybill wayBill = new Waybill();
				wayBill.setWaybillNo(wbNum);
				wayBill.setRcvrName(ReceiverName);
				wayBill.setRcvrAddr(ReceiverAddr);
				wayBill.setRcvrDetailAddr(ReceiverDetailAddr);
				wayBill.setRcvrCp(ReceiverCp);
				wayBill.setCompanyCd(companyCd[comindex++]);
				wayBill.setUserId(userId);
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
				System.out.printf("    | 주소 : %s |\n", ReceiverAddr+" "+ReceiverDetailAddr);
				System.out.println();
				System.out.println(" 1. 결제 화면으로  2. 받는 사람 정보 다시 입력  3. 메인 메뉴로");
				System.out.println("-----------------------------------------------------");
				System.out.print(" 메뉴 선택 : ");
				String menuNo = scan.nextLine();
				System.out.println();

				if ("1".equals(menuNo)) {
					
					int grade = user.getUserGrade();
					int discount;
					
					//등급 별 할인 값 구하기
					if(grade <= 100) {
						discount = 200;
					} else if (grade <= 200) {
						discount = 500;
					} else {
						discount = 1000;
					}
								
					String sign = payView(cost, surcharge, discount);
					
					// 등급 고려하여 최종요금
					totalFee = cost + surcharge - discount;
					wayBill.setTotalFee(totalFee);
					
					if (sign != "fail") {

						System.out.println("-----------------------------------------------------");
						System.out.println();
						System.out.println("                   ○ 결 제   완 료 ○");
						System.out.println();
						System.out.println("-----------------------------------------------------");
						
						// 결제 완료 시 요청사항 작성
						String msg = message();
						wayBill.setMsg(msg);
						
						// 결제 완료 시 등급 +1
						grade++;
						user.setUserGrade(grade);
						uDao.gradeUpdate(userId, grade);
						
						// 결제 완료 시 운송장데이터 생성
						wbDao.create(wayBill);
						pDao.create(parcel);
						
						// 운송장 출력 화면으로
						WaybillView.getinstance().waybillInfo(wayBill, parcel);
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
	
	public static ToReceiverInfoView getinstance() {
		return view;
	}
}

package view;

import dao.FlwOptDao;
import dao.UserDao;
import dao.BillDao;
import model.FlwOpt;
import model.User;

import java.util.List;

public class ItemView implements CommonView {

  private static ItemView view = new ItemView();
  public void phList() {

    while (true) {

      try {
        UserView userV = new UserView();

        // 로그인 분기 : 1=회원 로그인, 2=비회원 로그인
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println();
        System.out.println("                    [ 주문 접수 조회 메뉴 ]");
        System.out.println();
        System.out.println("       해당 기능을 사용하시려면 로그인을 하셔야 합니다.");
        System.out.println();
        System.out.printf("\t%-20s\t%-20s\n", "1. 회원 로그인", "2. 비회원 로그인");
        System.out.println("-----------------------------------------------------");
        System.out.print("메뉴 선택 : ");
        String menuNo = scan.nextLine();

        // 회원 주문 내역 확인
        if ("1".equals(menuNo)) {

          String userId = userV.Login();
          // 로그인 성공
          if (userId != "fail") {
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("        해당 계정에서 주문하신 내역은 다음과 같습니다.");
            System.out.println();
            System.out.println("  개별 주문 상세 열람 시 배송현황 조회 및 주문 취소를 하실 수 있습니다.");
            System.out.println();
            System.out.println("-----------------------------------------------------");
            System.out.println();
            itemInfo(userId);
            System.out.println();
            System.out.println("-----------------------------------------------------");
            System.out.print(" 주문 상세 : ");
            String itemNo = scan.nextLine();
//
//						// 주문 상세 조회 시
//						System.out.println("-----------------------------------------------------");
//						System.out.println();
//						System.out.println("        해당 항목에 대해 처리할 작업을 선택해 주십시오.");
//						System.out.println();
//						System.out.println(" 1. 배송 현황 확인  2. 영수증 출력  3. 주문 취소");
//						System.out.println("-----------------------------------------------------");
//						System.out.print(" 메뉴 선택 : ");
            String menuNum = scan.nextLine();
//
//                        if ("1".equals(menuNum)) {
//                            System.out.println("배송 현황 확인");
//
//                        } else if ("2".equals(menuNum)) {
//                            waybillInfo(wb,pc);
//                            break;
//
//                        } else if ("3".equals(menuNum)) {
//                            System.out.println("주문 취소");
//
//                        } else {
//                            System.out.println("다른 값을 입력한 경우");
//
//                        }

            // 로그인 실패
          } else {
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("                  로그인에 실패하셨습니다.");
            System.out.println();
            continue;
          }

          // 비회원 로그인
        } else if ("2".equals(menuNo)) {
          String nonusercp = userV.Non_userlogin();

          // 로그인 성공
          if (nonusercp != "fail") {
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("               비회원 로그인에 성공하셨습니다.");
            System.out.println();
            System.out.println(" 1. 배송 현황 확인  2. 영수증 출력  3. 주문 취소");
            System.out.println("-----------------------------------------------------");
            System.out.print(" 메뉴 선택 : ");
            String menuNum = scan.nextLine();

            System.out.println("주문 내역 출력");

            // 로그인 실패
          } else {
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("       비회원 로그인에 실패하셨습니다. 다시 시도해 주십시오");
            continue;
          }

          // 1, 2 외 다른 값을 입력한 경우
        } else {
          System.out.println("잘못된 접근");

        }

      } catch (Exception e) {
        e.printStackTrace();

        System.out.println("다시 시도해 주십시오");
        break;

      }

    }  // while
  }  // phList()

  // 받는곳 입력
  public void itemInfo(String userid) {
    FlwOptDao fDao = new FlwOptDao();

    // 화면 출력
    List<FlwOpt> list = fDao.selectWaybillNoList(userid);

    // DB list가 있다면 리스트를 출력한다.
    if (list.size() != 0) {

      System.out.println("---------------------------------------------------------------");
      System.out.println();
      System.out.println("번호\t\t상품명\t\t\t무게\t\t사이즈\t\t가격");
      System.out.println();
      System.out.println("---------------------------------------------------------------");
      // 즐겨찾기 리스트 출력
      for (int i = 0; i < list.size(); i++) {
        System.out.println();
        System.out.println("  "+(i + 1) + "\t\t"
                + list.get(i).getFlwOptName() + "\t\t" + list.get(i).getFlwOptWeight() +"kg\t\t"+list.get(i).getFlwOptSize()+
                "\t\t\t"+list.get(i).getFlwOptFee()+" 원");
      }
      System.out.println();
      System.out.println("-----------------------------------------------------");
      System.out.println();
      System.out.print("  몇번을 조회하시겠습니까? : ");



      // 리스트 사이즈가 0이면 바로 보낸사람 정보입력창으로 이동
    } else {
      System.out.println("구매 정보가 없습니다.");
    }


  }

  public static ItemView getinstance() {
    return view;
  }
}

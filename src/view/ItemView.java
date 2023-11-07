package view;

import dao.FlwOptDao;
import dao.UserDao;
import dao.BillDao;
import model.Bill;
import model.FlwOpt;
import model.User;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        System.out.printf("\t%-13s\t%-13s\t%-13s\n", "1. 회원 로그인", "2. 비회원 조회", "3. 메인 메뉴로");
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
            System.out.println("                  로그인에 성공하셨습니다.");
            System.out.println();
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("        해당 계정에서 주문하신 내역은 다음과 같습니다.");
            System.out.println();
            System.out.println("  개별 주문 상세 열람 시 배송현황 조회 및 주문 취소를 하실 수 있습니다.");
            System.out.println();
            System.out.println("-----------------------------------------------------");
            System.out.println();
            // 아이템 리스트 불러오기
            itemInfo(userId, true);


            // 로그인 실패
          } else {
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("                  로그인에 실패하셨습니다.");
            continue;
          }

          // 비회원 로그인
        } else if ("2".equals(menuNo)) {
          String nonusercp = userV.Non_userlogin_check();

          // 로그인 성공
          if (nonusercp != "fail") {
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("               비회원 로그인에 성공하셨습니다.");
            System.out.println();
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("        해당 번호로 주문하신 내역은 다음과 같습니다.");
            System.out.println();
            System.out.println("  개별 주문 상세 열람 시 배송현황 조회 및 주문 취소를 하실 수 있습니다.");
            System.out.println();
            System.out.println("-----------------------------------------------------");
            // 아이템 리스트 불러오기
            itemInfo(nonusercp, false);

            // 로그인 실패
          } else {
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.println("       비회원 로그인에 실패하셨습니다. 다시 시도해 주십시오");
            continue;
          }

        } else if ("3".equals(menuNo)) {
          return;

        } else {
          System.out.println("잘못된 접근입니다. 다시 시도해 주십시오");
          break;

        }

      } catch (Exception e) {
        e.printStackTrace();

        System.out.println("다시 시도해 주십시오");
        break;

      }

    }  // while
  }  // phList()

  // 받는곳 입력
  public void itemInfo(String useridOrcp, Boolean isUser) {
    FlwOptDao fDao = new FlwOptDao();
    BillDao bDao = new BillDao();
    // 화면 출력
    List<FlwOpt> list = fDao.selectWaybillNoList(useridOrcp, isUser);

    // DB list가 있다면 리스트를 출력한다.
    if (list.size() != 0) {

      System.out.println("---------------------------------------------------------------");
      System.out.println();
      System.out.println("번호\t\t상품명\t\t\t무게\t\t사이즈\t\t가격");
      System.out.println();
      System.out.println("---------------------------------------------------------------");
      // 상품 리스트 출력
      for (int i = 0; i < list.size(); i++) {
        System.out.println();
        System.out.println("  "+(i + 1) + "\t\t"
                + list.get(i).getFlwOptName() + "\t\t" + list.get(i).getFlwOptWeight() +"kg\t\t"+list.get(i).getFlwOptSize()+
                "\t\t\t"+list.get(i).getFlwOptFee()+" 원");
      }
      System.out.println();
      System.out.println("-----------------------------------------------------");
      System.out.print(" 몇번 상품을 선택하시겠습니까? : ");
      int listNum = Integer.parseInt(scan.nextLine());

      // 주문 상세 조회 시
      System.out.println("-----------------------------------------------------");
      System.out.println();
      System.out.println("        해당 항목에 대해 처리할 작업을 선택해 주십시오.");
      System.out.println();
      System.out.println(" 1. 배송 현황 확인  2. 영수증 출력  3. 주문 취소");
      System.out.println("-----------------------------------------------------");
      System.out.print(" 메뉴 선택 : ");
      String menuNum = scan.nextLine();

      // 처리 작업 분기

      if ("1".equals(menuNum)) {  // 배송 현황 확인
        shippingStatus(list.get(listNum-1).getBillNo());

      } else if ("2".equals(menuNum)) {  // 영수증 출력
        BillView.getinstance().billInfo(bDao.selectById(list.get(listNum-1).getBillNo()), list.get(listNum-1));

        // 취소 절차를 계속 진행할지 확인 후 취소 또는 메인 화면으로 돌아간다.
      } else if ("3".equals(menuNum)) {  // 주문 취소

        System.out.println("-----------------------------------------------------");
        System.out.println();
        System.out.println("                           [ 주문 취소 ]");
        System.out.println();
        System.out.println("  1을 입력하시면 현재 열람하고 계신 주문의 진행 상황이 취소됩니다.");
        System.out.println();
        System.out.println("        계속하시겠습니까?");
        System.out.println();
        System.out.printf("\t%-20s\t%-20s\n", "1. 계속 (주문 취소)", "2. 시스템 종료");
        System.out.println("-----------------------------------------------------");
        System.out.print(" 메뉴 선택 : ");
        String menuNO = scan.nextLine();

        if ("1".equals(menuNO)) {
          bDao.delete(list.get(listNum-1).getBillNo());

          System.out.println("-----------------------------------------------------");
          System.out.println();
          System.out.println("                주문이 취소 처리되었습니다.");
          System.out.println();
          System.out.println("                  프로그램을 종료합니다.");
          System.out.println();
          System.out.println("-----------------------------------------------------");

          exit();

        } else if ("2".equals(menuNO)) exit();


      } else {
        System.out.println("다른 값을 입력한 경우");

      }


      // 리스트 사이즈가 0이면 바로 보낸사람 정보입력창으로 이동
    } else {
      System.out.println("구매 정보가 없습니다.");
    }


  }


  public void shippingStatus(String billNo){
    BillDao bDao = new BillDao();
    Bill bl = bDao.selectById(billNo);

    System.out.println("-----------------------------------------------------");
    System.out.println("                 [배송 현황 확인]");
    System.out.println("-----------------------------------------------------");
    System.out.println();
    System.out.println("시간\t\t\t\t\t\t현재위치\t\t\t\t배송상태");
    System.out.println();
    System.out.println("-----------------------------------------------------");

    //java.sql.Timestamp 클래스로 주문등록 시각 밀리초단위까지 받아옴
    Timestamp regTime = bl.getRegDate();
    //수령주소 받아옴
    String addr = bl.getRcvrAddr();
    //수령주소 파싱
    String[] strToStrArray = addr.split(" ");

    //추후 프린트를 위해 String, 초단위 포멧으로 변환
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    String startTime = sdf.format(regTime);
    //hub 배정 위한 사용자별 고정 인덱스 받아옴
    int hubNum = startTime.charAt(startTime.length()-1)-'0';

    //캘린더 객체 받아옴
    Calendar reg = Calendar.getInstance();
    Calendar now = Calendar.getInstance();
    //현재시각 받아옴
    Date dateFormatNow = Calendar.getInstance().getTime();

    //캘린더 객체에 주문등록 시각, 현재시각 주입
    reg.setTimeInMillis(regTime.getTime());
    now.setTimeInMillis(dateFormatNow.getTime());

    //현재시각 - 주문등록 시각
    long diffSec = now.getTimeInMillis() - reg.getTimeInMillis();

    // hub 배정용 배열
    String[] hub = { "동탄1HUB", "동탄1HUB", "부천HUB", "시흥HUB", "안산HUB",
            "호법HUB", "고양HUB", "용인HUB", "옥천HUB", "평택1HUB"};
    // 배송 추적표 출력
    // 조건에 사용할 밀리초 시간 환산용 변수
    int hrToMilSec = 3600000;
    String shippingTime = "";
    // 시간 , 현재위치 받아옴, 배송상태fix
    if(diffSec > 48*hrToMilSec){

      Calendar reg48 = Calendar.getInstance();
      reg48.add(Calendar.HOUR, 48);
      shippingTime = sdf.format(reg48.getTime());

      System.out.println(shippingTime +"\t\t"+ strToStrArray[1] +"\t\t"+ "배송완료");
    }
    if (diffSec > 36*hrToMilSec) {
      Calendar reg36 = Calendar.getInstance();
      reg36.add(Calendar.HOUR, 36);
      shippingTime = sdf.format(reg36.getTime());

      System.out.println(shippingTime +"\t\t"+ strToStrArray[1] +"\t\t"+ "배송출발");
    }
    if (diffSec > 24*hrToMilSec) {
      Calendar reg24 = Calendar.getInstance();
      reg24.add(Calendar.HOUR, 24);
      shippingTime = sdf.format(reg24.getTime());

      System.out.println(shippingTime +"\t\t"+ strToStrArray[1] +"\t\t\t"+ "캠프도착");
    }

    // 시간 , 허브배정, 배송상태fix
    if (diffSec > 6*hrToMilSec){
      Calendar reg1 = Calendar.getInstance();
      reg1.add(Calendar.HOUR, 1);
      shippingTime = sdf.format(reg1.getTime());

      System.out.println(shippingTime +"\t\t"+ hub[hubNum] +"\t\t\t"+ "센터상차");

      reg1.add(Calendar.HOUR, -1);
      shippingTime = sdf.format(reg1.getTime());
      System.out.println(shippingTime +"\t\t"+ hub[hubNum] +"\t\t\t"+ "집하");
    }

  }
  public static ItemView getinstance() {
    return view;
  }
}

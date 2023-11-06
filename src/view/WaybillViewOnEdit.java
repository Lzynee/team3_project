package view;

import dao.NonuserDao;
import dao.ParcelDao;
import dao.UserDao;
import dao.WaybillDao;
import model.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class WaybillViewOnEdit implements CommonView  {

    private static WaybillViewOnEdit view = new WaybillViewOnEdit();

    public void waybillInfo(Waybill wb, Parcel parcel) {
        UserDao uDao = new UserDao();
        NonuserDao nuDao = new NonuserDao();
        WaybillDao wDao = new WaybillDao();

        //	String parcelNumStr = String.valueOf(parcelNum);

        User user = uDao.selectById(wb.getUserId());
//			Unknown column 'reg_date' in 'order clause'
        Nonuser Nuser = nuDao.selectById(wb.getNonCp());

        // 들어가야 하는것
        // 받는사람 이름 번호 주소
        String Rname = wb.getRcvrName();
        String Rcp = wb.getRcvrCp();
        String Raddr = wb.getRcvrAddr();
        String RDetailAddr = wb.getRcvrDetailAddr();
        int Rzipcode = getZipCode(Raddr);

        //보내는 사람 이름 번호 주소
        String Sname;
        String Scp;
        String Saddr;
        String SDetailAddr;


        // 보내는 사람이 회원인가 비회원인가
        if (wb.getNonCp() == null && wb.getUserId() != null) {
            // 회원일시 보내는 사람 정보
            Sname = user.getUserName();
            Scp = user.getUserCp();
            Saddr = user.getUserAddr();
            SDetailAddr = user.getUserDetailAddr();

        } else if (wb.getNonCp() != null && wb.getUserId() == null) {
            // 비회원일시 보내는 사람 정보
            Sname = Nuser.getNonuserName();
            Scp = Nuser.getNonuserCp();
            Saddr = Nuser.getNonuserAddr();
            SDetailAddr = Nuser.getNonuserDetailAddr();

        } else {
            // 오류
            System.out.println("오류 입니다. 다시 시도하여 주십시오");
            return;
        }

        int Szipcode = getZipCode(Saddr);

        // 택배 중량 크기 내용물
        String pSize = parcel.getParcelSize();
        String pName = parcel.getParcelName();
//			parcel의 크기 관련 변수 주석 처리 === (Nov.05. 이양진)
//			int pWeight = parcel.getParcelWeight();
        // 택배 접수일(발송일)
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date = wb.getRegDate();
        if(date == null) {
            date = new Date();
//		        String nowTime1 = sdf1.format(date);
        }

//			택배회사 관련 변수 === (Nov.05. 이양진)
        if(wb.getCompanyName() == null)
        {
            wb.setCompanyName(wDao.selectCompanyByName(wb.getCompanyCd()));
        }

        // 영수증 정보 출력
        System.out.println();
//			'영수증 출력'이 두 줄 출력되던 문제 수정 === (Nov.05. 이양진)
        System.out.println("                               영 수 증    출 력");
        System.out.println();
        System.out.println("┌--------------------------------------------------------------------------┐");
//			택배회사 관련 변수를 사용하는 라인 === (Nov.05. 이양진)
        System.out.printf("| 주문번호 : %-52s |\n", wb.getWaybillNo() , wb.getCompanyName());
        System.out.println("|--------------------------------------------------------------------------|");
        System.out.printf("| 보내는사람 : %-20s  보내는사람 전화번호: %-16s |\n", Sname, Scp);
        System.out.printf("| 보내는사람 주소 : %-50s | \n","( " + Szipcode+" ) " + Saddr + " " + SDetailAddr);
        System.out.println("|--------------------------------------------------------------------------|");
        System.out.printf("| 받는사람 : %-20s    받는사람 전화번호: %-16s |\n", Rname, Rcp);
        System.out.printf("| 받는사람 주소 : %-50s |\n","( " + Rzipcode + " ) " + Raddr + " " + RDetailAddr);
        System.out.println("|--------------------------------------------------------------------------|");
//			상품 크기가 상품명 옆에 표시되도록 위치 이동 및 상품 무게 표시 라인 주석 처리 === (Nov. 05. 이양진)
        System.out.printf("| 상품명 : %-50s |\n", pName, "(" +  pSize + ")");
//			System.out.printf("| 상품 무게 : %-20d  \t   |\n"/*, pWeight*/, pSize);
        System.out.println("|--------------------------------------------------------------------------|");
        System.out.printf("|  %-30s| 요금 : %-10s |  %-10s |\n", wb.getMsg(), wb.getTotalFee() ,sdf1.format(date) );
        System.out.println("└--------------------------------------------------------------------------┘");
        System.out.println();
        System.out.println("                            이용해 주셔서 감사합니다.");
        System.out.println();
        System.out.println(" 1. 메인 메뉴로    2. 시스템 종료");
        System.out.println("-----------------------------------------------------");
        System.out.print(" 메뉴 선택 : ");
        String menuNO = scan.nextLine();

        if("1".equals(menuNO)) {
            System.out.println();
            System.out.println();
            System.out.println();
        } else {
            exit();
        }
    }

    // 주문 내역 출력 기능 시작 === (Nov.05. 이양진)
    public void wbList() {

        while (true) {

            try {
                WaybillDao wbDao = new WaybillDao();
                ParcelDao pDao = new ParcelDao();
                UserView userV = new UserView();

                // 로그인 분기 : 1=회원 로그인, 2=비회원 로그인
                System.out.println();
                System.out.println("-----------------------------------------------------");
                System.out.println();
                System.out.println("       해당 기능을 사용하시려면 로그인을 하셔야 합니다.");
                System.out.println();
                System.out.printf("\t%-20s\t%-20s\n", "1. 회원 로그인", "2. 비회원 로그인");
                System.out.println("-----------------------------------------------------");
                System.out.print("메뉴 선택 : ");
                String menuNo = scan.nextLine();

                // 영수증이 회원으로 접수 되었을때
                if ("1".equals(menuNo)) {
                    String userId = userV.Login();
                    PurchaseHistory ph = wbDao.selectById()

                    if (userId.equals(ph.getUserId()) ) {

                        System.out.println("-----------------------------------------------------");
                        System.out.println();
                        System.out.println("        로그인에 성공하셨습니다. 메뉴를 선택해 주십시오.");
                        System.out.println();
                        System.out.println(" 1. 주문 내역 확인  2. 삭제");
                        System.out.println("-----------------------------------------------------");
                        System.out.print(" 메뉴 선택 : ");
                        String menuNum = scan.nextLine();

                        if ("1".equals(menuNum)) {
                            waybillInfo(wb,pc);
                            break;
                        } else {
                            wbDao.delete(wbNum);

                            System.out.println("-----------------------------------------------------");
                            System.out.println();
                            System.out.println("                 접수가 취소되었습니다.");
                            System.out.println();
                            System.out.println("-----------------------------------------------------");
                            break;
                        }
//						회원 주문내역 조회 실패 시 안내 메시지 수정 === (Nov.05. 이양진)
                    } else if (userId != "fail") {
                        System.out.println("-----------------------------------------------------");
                        System.out.println();
                        System.out.println("               해당 상품을 구매한 내역을 찾을 수 없습니다.");
                        System.out.println();
                        System.out.println("-----------------------------------------------------");
                        break;
                    } else {
                        System.out.println("-----------------------------------------------------");
                        System.out.println();
                        System.out.println("               아이디 패스워드가 틀립니다.");
                        System.out.println();
                        System.out.println("-----------------------------------------------------");
                        break;
                    }

//					비회원 주문내역 조회 시 출력 문구 수정 === (Nov.05. 이양진)
//					전화번호 입력 조건('- 없이 입력하세요')을 삭제 === (Nov.05. 이양진)
                } else { // 영수증이 비회원으로 접수 되었을때
                    // 핸드폰 번호를 입력하고 해당 영수증번호와 핸드폰 번호가 같을 경우 채출력 및 접수 취소를 시킨다.
                    System.out.println("-----------------------------------------------------");
                    System.out.println();
                    System.out.println("해당 기능을 사용하시려면 해당 상품을 구매할 때 사용하신 전화번호를 입력하셔야 합니다.");
                    System.out.println();
                    System.out.println("-----------------------------------------------------");
                    System.out.print("전화 번호 입력 : ");
                    String inputNonCp = scan.nextLine();
                    if (inputNonCp.equals(wb.getNonCp())) {
                        System.out.println();
                        System.out.println("-----------------------------------------------------");
                        System.out.println();
                        System.out.println("        비회원 로그인에 성공하셨습니다. 메뉴를 선택해 주십시오.");
                        System.out.println();
                        System.out.println(" 1. 재출력  2. 삭제");
                        System.out.println("-----------------------------------------------------");
                        System.out.print(" 메뉴 선택 : ");
                        String menuNum = scan.nextLine();

                        if ("1".equals(menuNum)) {
                            waybillInfo(wb,pc);
                            break;
                        } else {
                            wbDao.delete(wbNum);
                            System.out.println("-----------------------------------------------------");
                            System.out.println();
                            System.out.println("                  접수가 취소되었습니다.");
                            System.out.println();
                            System.out.println("-----------------------------------------------------");
                            break;
                        }
                    } else {
                        System.out.println("-----------------------------------------------------");
                        System.out.println();
                        System.out.println("               전화번호가 일치하지 않습니다.");
                        System.out.println();
                        System.out.println("-----------------------------------------------------");
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("다시 시도해 주십시오");
                break;
            }

        }
    }

    public static WaybillViewOnEdit getinstance()
    {
        return view;
    }


}

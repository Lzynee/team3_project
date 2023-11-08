package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Bill;

public class BillDao {

	public BillDao() {

	}

	public List<Bill> selectAll() {

		List<Bill> list = new ArrayList<>();

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from bill";
			System.out.println(sql);

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				Bill vo = new Bill();
				vo.setBillNo(re.getString("bill_no"));
				vo.setRcvrName(re.getString("rcvr_name"));
				vo.setRcvrAddr(re.getString("rcvr_addr"));
				vo.setRcvrDetailAddr(re.getString("rcvr_Daddr"));
				vo.setRcvrCp(re.getString("rcvr_cp"));
				vo.setCompanyCd(re.getString("company_cd"));
				vo.setUserId(re.getString("user_id"));
				vo.setNonCp(re.getString("non_cp"));				
				vo.setRegDate(re.getTimestamp("reg_date"));
				vo.setMsg(re.getString("msg"));
				vo.setTotalFee(re.getInt("total_fee"));
				list.add(vo);
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public Bill selectById(String billNum) {
		
		Bill vo = null;

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "SELECT w.*,c.company_name AS company_name from bill w INNER JOIN company c ON(w.company_cd = c.company_cd) where bill_no=?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, billNum);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				vo = new Bill();
				vo.setBillNo(re.getString("bill_no"));
				vo.setRcvrName(re.getString("rcvr_name"));
				vo.setRcvrAddr(re.getString("rcvr_addr"));
				vo.setRcvrDetailAddr(re.getString("rcvr_Daddr"));
				vo.setRcvrCp(re.getString("rcvr_cp"));
				vo.setCompanyCd(re.getString("company_cd"));
				vo.setUserId(re.getString("user_id"));
				vo.setNonCp(re.getString("non_cp"));				
				vo.setCompanyName(re.getString("company_name"));
				vo.setRegDate(re.getTimestamp("reg_date"));
				vo.setMsg(re.getString("msg"));
				vo.setTotalFee(re.getInt("total_fee"));
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		

		return vo;
	}

	public void create(Bill vo) {


		try {
			Connection conn = SuperDao.getConnection();
			String sql = "insert into bill(bill_no,rcvr_name,rcvr_addr,rcvr_Daddr,rcvr_cp,company_cd,user_id,non_cp,msg,total_fee) value(?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, vo.getBillNo());
			stmt.setString(2, vo.getRcvrName());
			stmt.setString(3, vo.getRcvrAddr());
			stmt.setString(4, vo.getRcvrDetailAddr());
			stmt.setString(5, vo.getRcvrCp());
			stmt.setString(6, vo.getCompanyCd());
			stmt.setString(7, vo.getUserId());
			stmt.setString(8, vo.getNonCp());
			stmt.setString(9, vo.getMsg());
			stmt.setInt(10, vo.getTotalFee());

			stmt.executeUpdate();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void update(Bill vo) {
		try {
			Connection conn = SuperDao.getConnection();
			

			String sql = "update bill set  rcvr_name = ?,rcvr_addr = ?, rcvr_Daddr = ?, rcvr_cp = ?, company_cd= ?, user_id=?, non_cp =?, msg=?  where bill_no=? ";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, vo.getRcvrName());
			stmt.setString(2, vo.getRcvrAddr());
			stmt.setString(3, vo.getRcvrDetailAddr());
			stmt.setString(4, vo.getRcvrCp());
			stmt.setString(5, vo.getCompanyCd());
			stmt.setString(6, vo.getUserId());
			stmt.setString(7, vo.getNonCp());
			stmt.setString(8, vo.getMsg());
			stmt.setString(9, vo.getBillNo());

			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(String billNo) {

		try {
			Connection conn = SuperDao.getConnection();
			

			String sql = "delete from bill where bill_no=? ";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, billNo);
			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int selectzipcode(String sido, String gugun,String dong, int num)
	{
		try {
			Connection conn = SuperDao.getConnection();
			

			String sql = "select DISTINCT  zipcode from sigugun where dong like ? and sido=? and sigugun=? and num=? limit 1";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, dong + "%");
			stmt.setString(2, sido);
			stmt.setString(3, gugun);
			stmt.setInt(4, num);

			ResultSet re = stmt.executeQuery();
			int i = 0;
			while (re.next()) {
				i =re.getInt("zipcode");
			}
			stmt.close();

			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return -1;
	}


	public int selectzipcode(String sido, String gugun, String eupOrDong, int num, int bunum) {
		try {
			Connection conn = SuperDao.getConnection();
			String sql = "";
			//동과 동이 아닌경우 sql 분기처리
			if('동' == eupOrDong.charAt(eupOrDong.length()-1)){
				sql = "select DISTINCT  zipcode from sigugun where sido=? and sigugun = ? and dong = ? and num=? and bunum=? limit 1";
			}else{
				sql = "select DISTINCT  zipcode from sigugun where sido=? and sigugun = ? and eup = ? and num=? and bunum=? limit 1";
			}
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, sido);
			stmt.setString(2, gugun);
			stmt.setString(3, eupOrDong);
			stmt.setInt(4, num);
			stmt.setInt(5, bunum);

			ResultSet re = stmt.executeQuery();
			int i = 0;
			while (re.next()) {
				i =re.getInt("zipcode");
			}
			stmt.close();

			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return -1;
	}
	public int selectzipcode(String gugun, String dong, int num, int bunum) {
		try {
			Connection conn = SuperDao.getConnection();
			

			String sql = "select DISTINCT  zipcode from sigugun where dong like ? and sigugun like ? and num=? and bunum=? limit 1";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, dong + "%");
			stmt.setString(2, gugun + "%");
			stmt.setInt(3, num);
			stmt.setInt(4, bunum);

			ResultSet re = stmt.executeQuery();
			int i = 0;
			while (re.next()) {
				i =re.getInt("zipcode");
			}
			stmt.close();

			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return -1;
	}


//	public List<Company> selectCompanyAll() {
//
//		List<Company> list = new ArrayList<>();
//
//		try {
//			Connection conn = SuperDao.getConnection();
//			String sql = "select * from company";
//
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			ResultSet re = stmt.executeQuery();
//			while (re.next()) {
//				Company vo = new Company();
//				vo.setCompanyCd(re.getString("company_cd"));
//				vo.setCompanyCd(re.getString("company_name"));
//				list.add(vo);
//			}
//			re.close();
//			stmt.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}

	public String selectCompanyByName(String companyCd) {

		String name = null;

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from company where company_cd = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, companyCd);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				name = re.getString("company_name");
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return name;
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.FlwOpt;

public class FlwOptDao {

	public FlwOptDao() {

	}

	public List<FlwOpt> selectAll() {

		List<FlwOpt> list = new ArrayList<>();

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from flwopt";
			System.out.println(sql);

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				FlwOpt vo = new FlwOpt();
				vo.setFlwOptNo(re.getInt("flwopt_no"));
				vo.setFlwOptName(re.getString("flwopt_name"));
				vo.setFlwOptWeight(re.getInt("flwopt_weight"));
				vo.setFlwOptSize(re.getString("flwopt_size"));
				vo.setFlwOptFee(re.getInt("flwopt_fee"));
				vo.setBillNo(re.getString("bill_no"));
				list.add(vo);
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public FlwOpt selectById(String userId) {

		FlwOpt vo = null;

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from flwopt where flwopt_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				vo = new FlwOpt();
				vo.setFlwOptNo(re.getInt("flwopt_no"));
				vo.setFlwOptName(re.getString("flwopt_name"));
				vo.setFlwOptWeight(re.getInt("flwopt_weight"));
				vo.setFlwOptSize(re.getString("flwopt_size"));
				vo.setFlwOptFee(re.getInt("flwopt_fee"));
				vo.setBillNo(re.getString("bill_no"));
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	public List<FlwOpt> selectWaybillNoList(String waybillNo) {
		FlwOpt vo = null;
		List<FlwOpt> list = new ArrayList<>();

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select flwOpt_no, flwOpt_name, flwOpt_weight, flwOpt_size, flwOpt_fee, f.bill_no " +
							"from Bill as wb " +
							"join flwOpt as f on wb.bill_no= f.bill_no and wb.user_id = ?;";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, waybillNo);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				vo = new FlwOpt();
				vo.setFlwOptNo(re.getInt("flwOpt_no"));
				vo.setFlwOptName(re.getString("flwOpt_name"));
				vo.setFlwOptWeight(re.getInt("flwOpt_weight"));
				vo.setFlwOptSize(re.getString("flwOpt_size"));
				vo.setFlwOptFee(re.getInt("flwOpt_fee"));
				vo.setBillNo(re.getString("bill_no"));
				list.add(vo);
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return list;
	}
	public FlwOpt selectBillNo(String billNo) {

		FlwOpt vo = null;

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from flwopt where bill_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, billNo);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				vo = new FlwOpt();
				vo.setFlwOptNo(re.getInt("flwopt_no"));
				vo.setFlwOptName(re.getString("flwopt_name"));
				vo.setFlwOptWeight(re.getInt("flwopt_weight"));
				vo.setFlwOptSize(re.getString("flwopt_size"));
				vo.setFlwOptFee(re.getInt("flwopt_fee"));
				vo.setBillNo(re.getString("bill_no"));
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	
	public FlwOpt selectByCp(String nonCp) {

		FlwOpt vo = null;

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from flwopt where flwopt_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nonCp);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				vo = new FlwOpt();
				vo.setFlwOptNo(re.getInt("flwopt_no"));
				vo.setFlwOptName(re.getString("flwopt_name"));
				vo.setFlwOptWeight(re.getInt("flwopt_weight"));
				vo.setFlwOptSize(re.getString("flwopt_size"));
				vo.setFlwOptFee(re.getInt("flwopt_fee"));
				vo.setBillNo(re.getString("bill_no"));
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	public int selectFlwOptFee(int FlwOptNum) {
		
		int FlwOptFee = 0;
		
		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select flwopt_fee from flwopt where flwopt_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, FlwOptNum);
			ResultSet re = stmt.executeQuery();
			
			re.next();

			FlwOptFee = re.getInt("flwopt_fee");
			
			re.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return FlwOptFee;
	}

	public int selectCountId() {

		int cnt = 0;
		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select MAX(flwopt_no)+1 as cnt from flwopt";

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				cnt = re.getInt("cnt");
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cnt;
	}

	public void create(FlwOpt vo) {

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "insert into flwopt(flwopt_name,flwopt_weight,flwopt_size,flwopt_fee,bill_no) values(?,?,?,?,?)";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, vo.getFlwOptName());
			stmt.setInt(2, vo.getFlwOptWeight());
			stmt.setString(3, vo.getFlwOptSize());
			stmt.setInt(4, vo.getFlwOptFee());
			stmt.setString(5, vo.getBillNo());

			stmt.executeUpdate();

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(FlwOpt vo) {
		try {
			Connection conn = SuperDao.getConnection();

			String sql = "update flwopt set  flwopt_name = ?, flwopt_weight = ?, flwopt_size = ?, flwopt_fee = ?, bill_no = ?"
					+ "where flwopt_no=? ";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, vo.getFlwOptName());
			stmt.setInt(2, vo.getFlwOptWeight());
			stmt.setString(3, vo.getFlwOptSize());
			stmt.setInt(4, vo.getFlwOptFee());
			stmt.setString(5, vo.getBillNo());
			stmt.setInt(6, vo.getFlwOptNo());

			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(int FlwOptNo) {

		try {
			Connection conn = SuperDao.getConnection();

			String sql = "delete from flwopt where flwopt_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, FlwOptNo);
			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

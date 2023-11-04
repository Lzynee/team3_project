package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Parcel;

public class ParcelDao {

	public ParcelDao() {

	}

	public List<Parcel> selectAll() {

		List<Parcel> list = new ArrayList<>();

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from parcel";
			System.out.println(sql);

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				Parcel vo = new Parcel();
				vo.setParcelNo(re.getInt("parcel_no"));
				vo.setParcelName(re.getString("parcel_name"));
				vo.setParcelWeight(re.getInt("parcel_weight"));
				vo.setParcelSize(re.getString("parcel_size"));
				vo.setParcelFee(re.getInt("parcel_fee"));
				vo.setWaybillNo(re.getString("waybill_no"));
				list.add(vo);
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public Parcel selectById(String userId) {

		Parcel vo = null;

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from parcel where parcel_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				vo = new Parcel();
				vo.setParcelNo(re.getInt("parcel_no"));
				vo.setParcelName(re.getString("parcel_name"));
				vo.setParcelWeight(re.getInt("parcel_weight"));
				vo.setParcelSize(re.getString("parcel_size"));
				vo.setParcelFee(re.getInt("parcel_fee"));
				vo.setWaybillNo(re.getString("waybill_no"));
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	
	public Parcel selectWaybillNo(String waybillNo) {

		Parcel vo = null;

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from parcel where waybill_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, waybillNo);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				vo = new Parcel();
				vo.setParcelNo(re.getInt("parcel_no"));
				vo.setParcelName(re.getString("parcel_name"));
				vo.setParcelWeight(re.getInt("parcel_weight"));
				vo.setParcelSize(re.getString("parcel_size"));
				vo.setParcelFee(re.getInt("parcel_fee"));
				vo.setWaybillNo(re.getString("waybill_no"));
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	
	public Parcel selectByCp(String nonCp) {

		Parcel vo = null;

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select * from parcel where parcel_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nonCp);
			ResultSet re = stmt.executeQuery();
			while (re.next()) {
				vo = new Parcel();
				vo.setParcelNo(re.getInt("parcel_no"));
				vo.setParcelName(re.getString("parcel_name"));
				vo.setParcelWeight(re.getInt("parcel_weight"));
				vo.setParcelSize(re.getString("parcel_size"));
				vo.setParcelFee(re.getInt("parcel_fee"));
				vo.setWaybillNo(re.getString("waybill_no"));
			}
			re.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	public int selectParcelFee(int parcelNum) {
		
		int parcelFee = 0;
		
		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select parcel_fee from parcel where parcel_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, parcelNum);
			ResultSet re = stmt.executeQuery();
			
			re.next();
			
			parcelFee = re.getInt("parcel_fee");
			
			re.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parcelFee;
	}

	public int selectCountId() {

		int cnt = 0;
		try {
			Connection conn = SuperDao.getConnection();
			String sql = "select MAX(parcel_no)+1 as cnt from parcel";

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

	public void create(Parcel vo) {

		try {
			Connection conn = SuperDao.getConnection();
			String sql = "insert into parcel(parcel_name,parcel_weight,parcel_size,parcel_fee,waybill_no) values(?,?,?,?,?)";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, vo.getParcelName());
			stmt.setInt(2, vo.getParcelWeight());
			stmt.setString(3, vo.getParcelSize());
			stmt.setInt(4, vo.getParcelFee());
			stmt.setString(5, vo.getWaybillNo());

			stmt.executeUpdate();

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Parcel vo) {
		try {
			Connection conn = SuperDao.getConnection();

			String sql = "update parcel set  parcel_name = ?, parcel_weight = ?, parcel_size = ?, parcel_fee = ?, waybill_no = ?"
					+ "where parcel_no=? ";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, vo.getParcelName());
			stmt.setInt(2, vo.getParcelWeight());
			stmt.setString(3, vo.getParcelSize());
			stmt.setInt(4, vo.getParcelFee());
			stmt.setString(5, vo.getWaybillNo());
			stmt.setInt(6, vo.getParcelNo());

			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(int parcelNo) {

		try {
			Connection conn = SuperDao.getConnection();

			String sql = "delete from parcel where parcel_no=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, parcelNo);
			stmt.executeUpdate();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

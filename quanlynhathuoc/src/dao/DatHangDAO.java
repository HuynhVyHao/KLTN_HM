package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.JDBCConnection;
import entity.DatHang;
import entity.KhachHang;
import entity.NhanVien;

public class DatHangDAO extends InterfaceDAO<DatHang, String> {
	private final String INSERT_SQL = "INSERT INTO DatHang values (?,?,?,?,?,?)";
	private final String UPDATE_SQL = "UPDATE DatHang SET thoiGian=?, idNV=?, idKH=?, tongTien=?, trangThai=? WHERE idDH=?";
	private final String DELETE_BY_ID = "DELETE from DatHang WHERE idDH = ?";

	private final String SELECT_ALL_SQL = "SELECT DatHang.idDH, DatHang.thoiGian, DatHang.idNV, DatHang.idKH, DatHang.tongTien, DatHang.trangThai, "
			+ "NhanVien.hoTen AS tenNV, NhanVien.sdt AS sdtNV, NhanVien.gioiTinh AS gioiTinhNV, NhanVien.namSinh, NhanVien.ngayVaoLam, "
			+ "KhachHang.hoTen AS tenKH, KhachHang.sdt AS sdtKH, KhachHang.gioiTinh AS gioiTinhKH, KhachHang.ngayThamGia "
			+ "FROM DatHang " + "INNER JOIN NhanVien ON DatHang.idNV = NhanVien.idNV "
			+ "INNER JOIN KhachHang ON DatHang.idKH = KhachHang.idKH " + "ORDER BY DatHang.thoiGian";
	private final String SELECT_BY_ID = "SELECT DatHang.idDH, DatHang.thoiGian, DatHang.idNV, DatHang.idKH, DatHang.tongTien, DatHang.trangThai, "
			+ "NhanVien.hoTen AS tenNV, NhanVien.sdt AS sdtNV, NhanVien.gioiTinh AS gioiTinhNV, NhanVien.namSinh, NhanVien.ngayVaoLam, "
			+ "KhachHang.hoTen AS tenKH, KhachHang.sdt AS sdtKH, KhachHang.gioiTinh AS gioiTinhKH, KhachHang.ngayThamGia "
			+ "FROM DatHang " + "INNER JOIN NhanVien ON DatHang.idNV = NhanVien.idNV "
			+ "INNER JOIN KhachHang ON DatHang.idKH = KhachHang.idKH " + "WHERE idDH = ? "
			+ "ORDER BY DatHang.thoiGian";

	@Override
	public void create(DatHang e) {
		JDBCConnection.update(INSERT_SQL, e.getId(), e.getThoiGian(), e.getNhanVien().getId(), e.getKhachHang().getId(), e.getTongTien(), e.getTrangThai());
	}

	@Override
	public void update(DatHang e) {
		JDBCConnection.update(UPDATE_SQL, e.getThoiGian(), e.getNhanVien().getId(), e.getKhachHang().getId(),
				e.getTongTien(), e.getTrangThai(), e.getId());
	}

	@Override
	public void deleteById(String id) {
		JDBCConnection.update(DELETE_BY_ID, id);
	}

	@Override
	protected List<DatHang> selectBySql(String sql, Object... args) {
	    List<DatHang> listE = new ArrayList<>();
	    try {
	        ResultSet rs = JDBCConnection.query(sql, args);
	        while (rs.next()) {
	            DatHang datHang = new DatHang();
	            datHang.setId(rs.getString("idDH"));
	            datHang.setThoiGian(rs.getTimestamp("thoiGian"));

	            NhanVien nhanVien = new NhanVien();
	            nhanVien.setId(rs.getString("idNV"));
	            nhanVien.setHoTen(rs.getString("tenNV"));
	            nhanVien.setSdt(rs.getString("sdtNV"));
	            nhanVien.setGioiTinh(rs.getString("gioiTinhNV"));
	            nhanVien.setNamSinh(rs.getInt("namSinh"));
	            nhanVien.setNgayVaoLam(rs.getDate("ngayVaoLam"));

	            datHang.setNhanVien(nhanVien);

	            KhachHang khachHang = new KhachHang();
	            khachHang.setId(rs.getString("idKH"));
	            khachHang.setHoTen(rs.getString("tenKH"));
	            khachHang.setSdt(rs.getString("sdtKH"));
	            khachHang.setGioiTinh(rs.getString("gioiTinhKH"));
	            khachHang.setNgayThamGia(rs.getDate("ngayThamGia"));

	            datHang.setKhachHang(khachHang);

	            datHang.setTongTien(rs.getDouble("tongTien"));
	            datHang.setTrangThai(rs.getString("trangThai")); // Lấy trạng thái từ kết quả

	            listE.add(datHang);
	        }
	        rs.getStatement().getConnection().close();
	        return listE;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}


	@Override
	public List<DatHang> selectAll() {
		return this.selectBySql(SELECT_ALL_SQL);
	}

	@Override
	public DatHang selectById(String id) {
		List<DatHang> list = selectBySql(SELECT_BY_ID, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
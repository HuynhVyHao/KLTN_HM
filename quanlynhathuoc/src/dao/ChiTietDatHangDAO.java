package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.JDBCConnection;
import controller.DatHangController;
import controller.ThuocController;
import entity.ChiTietDatHang;
import entity.DatHang;
import entity.Thuoc;

public class ChiTietDatHangDAO implements ChiTietInterfaceDAO<ChiTietDatHang, String> {
	 private final String INSERT_SQL = "INSERT INTO ChiTietDatHang (idDH, idThuoc, soLuong, donGia) VALUES (?,?,?,?)";
	    private final String DELETE_BY_ID = "DELETE from ChiTietDatHang WHERE idDH = ?";
	    private final String SELECT_BY_ID = "SELECT * FROM ChiTietDatHang WHERE idDH = ?";

	    @Override
	    public void create(List<ChiTietDatHang> list) {
	        for (ChiTietDatHang e : list) {
	            JDBCConnection.update(INSERT_SQL, e.getDatHang().getId(), e.getThuoc().getId(), e.getSoLuong(), e.getDonGia());
	        }
	    }

	    @Override
	    public void update(String k, List<ChiTietDatHang> e) {
	        this.deleteById(k);
	        this.create(e);
	    }

	    @Override
	    public void deleteById(String k) {
	        JDBCConnection.update(DELETE_BY_ID, k);
	    }

	    @Override
	    public List<ChiTietDatHang> selectAllById(String k) {
	        return this.selectBySql(SELECT_BY_ID, k);
	    }

	    protected List<ChiTietDatHang> selectBySql(String sql, Object... args) {
	        List<ChiTietDatHang> listE = new ArrayList<>();
	        try {
	            ResultSet rs = JDBCConnection.query(sql, args);
	            while (rs.next()) {
	                ChiTietDatHang e = new ChiTietDatHang();

	                String idDH = rs.getString("idDH");
	                DatHang datHang = new DatHangController().selectById(idDH);
	                e.setDatHang(datHang);

	                String idThuoc = rs.getString("idThuoc");
	                Thuoc thuoc = new ThuocController().selectById(idThuoc);
	                e.setThuoc(thuoc);
	                
	                e.setSoLuong(rs.getInt("soLuong"));
	                e.setDonGia(rs.getDouble("donGia"));
	                listE.add(e);
	            }
	            rs.getStatement().getConnection().close();
	            return listE;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	}
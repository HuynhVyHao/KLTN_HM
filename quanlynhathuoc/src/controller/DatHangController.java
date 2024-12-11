package controller;

import java.util.ArrayList;
import java.util.List;

import dao.DatHangDAO;
import entity.DatHang;

public class DatHangController extends InterfaceConTroller<DatHang, String>{
	  public DatHangDAO DH_DAO = new DatHangDAO();
	  public List<DatHang> listDH= DH_DAO.selectAll();

	    public DatHangController() {
	    }

	    @Override
	    public void create(DatHang e) {
	        DH_DAO.create(e);
	    }

	    @Override
	    public void update(DatHang e) {
	        DH_DAO.update(e);
	    }

	    @Override
	    public void deleteById(String id) {
	        DH_DAO.deleteById(id);
	    }

	    @Override
	    public List<DatHang> getAllList() {
	        return DH_DAO.selectAll();
	    }

	    @Override
	    public DatHang selectById(String id) {
	        return DH_DAO.selectById(id);
	    }

	    public List<DatHang> getSearchTable(String text) {
	        List<DatHang> result = new ArrayList<>();
	        // Lặp qua danh sách đơn hàng (DatHang) đã có
	        for (DatHang datHang : listDH) {
	            // Kiểm tra nếu thông tin trong đơn hàng khớp với chuỗi tìm kiếm
	            if (datHang.getId().toLowerCase().contains(text.toLowerCase()) ||
	                (datHang.getKhachHang() != null && datHang.getKhachHang().getHoTen().toLowerCase().contains(text.toLowerCase())) ||
	                (datHang.getKhachHang() != null && datHang.getKhachHang().getSdt() != null &&
	                 datHang.getKhachHang().getSdt().toLowerCase().contains(text.toLowerCase())) ||
	                datHang.getTrangThai().toLowerCase().contains(text.toLowerCase())) {
	                result.add(datHang);
	            }
	        }
	        return result;
	    }


	    public List<DatHang> getFilterTable(String tenNV, double fromPrice, double toPrice) {
	        List<DatHang> result = new ArrayList<>();

	        for (DatHang e : this.getAllList()) {
	            boolean match = false;

	            if (e.getNhanVien().getHoTen().equals(tenNV)) {
	                match = true;
	            } else if (e.getTongTien() >= fromPrice && e.getTongTien() <= toPrice) {
	                match = true;
	            }

	            if (match) {
	                result.add(e);
	            }
	        }

	        return result;
	    }
	}

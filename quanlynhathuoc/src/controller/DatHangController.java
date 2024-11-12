package controller;

import java.util.ArrayList;
import java.util.List;

import dao.DatHangDAO;
import entity.DatHang;

public class DatHangController extends InterfaceConTroller<DatHang, String>{
	  public DatHangDAO DH_DAO = new DatHangDAO();

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

	    public List<DatHang> getSearchTable(String text, String searchType) {
	        text = text.toLowerCase();
	        List<DatHang> result = new ArrayList<>();

	        switch (searchType) {
	            case "Tất cả" -> {
	                for (DatHang e : this.getAllList()) {
	                    if (e.getId().toLowerCase().contains(text)
	                            || e.getKhachHang().getHoTen().toLowerCase().contains(text)) {
	                        result.add(e);
	                    }
	                }
	            }
	            case "Mã" -> {
	                for (DatHang e : this.getAllList()) {
	                    if (e.getId().toLowerCase().contains(text)) {
	                        result.add(e);
	                    }
	                }
	            }
	            case "Tên khách hàng" -> {
	                for (DatHang e : this.getAllList()) {
	                    if (e.getKhachHang().getHoTen().toLowerCase().contains(text)) {
	                        result.add(e);
	                    }
	                }
	            }
	            default -> {
	                throw new AssertionError();
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

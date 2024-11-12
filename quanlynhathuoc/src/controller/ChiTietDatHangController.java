package controller;

import java.util.List;

import dao.ChiTietDatHangDAO;
import entity.ChiTietDatHang;

public class ChiTietDatHangController extends ChiTietInterfaceController<ChiTietDatHang, String> {

    public ChiTietDatHangDAO CTHD_DAO = new ChiTietDatHangDAO();

    public ChiTietDatHangController() {
    }

    @Override
    public void create(List<ChiTietDatHang> e) {
        CTHD_DAO.create(e);
    }

    @Override
    public void update(String id, List<ChiTietDatHang> e) {
        CTHD_DAO.update(id, e);
    }

    @Override
    public void deleteById(String id) {
        CTHD_DAO.deleteById(id);
    }

    @Override
    public List<ChiTietDatHang> selectAllById(String id) {
        return CTHD_DAO.selectAllById(id);
    }
}
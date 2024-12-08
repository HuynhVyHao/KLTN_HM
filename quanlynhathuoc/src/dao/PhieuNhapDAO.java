package dao;

import connectDB.JDBCConnection;
import controller.NhaCungCapController;
import entity.ChiPhiThuocHetHan;
import entity.NhanVien;
import entity.PhieuNhap;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO extends InterfaceDAO<PhieuNhap, String> {

    private final String INSERT_SQL = "INSERT INTO PhieuNhap values (?,?,?,?,?)";
    private final String UPDATE_SQL = "UPDATE PhieuNhap SET thoiGian=?, idNV=?, idNCC=?, tongTien=? where idPN=?";
    private final String DELETE_BY_ID = "DELETE from PhieuNhap where idPN = ?";

    private final String SELECT_ALL_SQL = """
        SELECT PN.*, 
               NV.hoTen AS tenNV, NV.sdt AS sdtNV, NV.gioiTinh, NV.namSinh, NV.ngayVaoLam, 
               NCC.tenNCC, NCC.sdt AS sdtNCC, NCC.diaChi 
        FROM PhieuNhap PN 
        JOIN NhanVien NV ON PN.idNV = NV.idNV 
        JOIN NhaCungCap NCC ON PN.idNCC = NCC.idNCC 
        ORDER BY PN.thoiGian ; """;

    private final String SELECT_BY_ID = """
        SELECT PN.*, 
                NV.hoTen AS tenNV, NV.sdt AS sdtNV, NV.gioiTinh, NV.namSinh, NV.ngayVaoLam, 
                NCC.tenNCC, NCC.sdt AS sdtNCC, NCC.diaChi 
        FROM PhieuNhap PN 
        JOIN NhanVien NV ON PN.idNV = NV.idNV 
        JOIN NhaCungCap NCC ON PN.idNCC = NCC.idNCC 
        WHERE idPN = ? 
        ORDER BY PN.thoiGian;""";

    private final String INSERT_CHI_PHI_THUOC_HET_HAN_SQL = "INSERT INTO ChiPhiThuocHetHan (idThuoc, tongChiPhi, thoiGian) VALUES (?, ?, ?)";

    // Phương thức để tạo một bản ghi mới trong bảng ChiPhiThuocHetHan
    public void createHH(ChiPhiThuocHetHan chiPhiThuocHetHan) {
        // Cập nhật dữ liệu vào bảng ChiPhiThuocHetHan
        JDBCConnection.update(INSERT_CHI_PHI_THUOC_HET_HAN_SQL, chiPhiThuocHetHan.getThuoc().getId(), chiPhiThuocHetHan.getTongChiPhi(),chiPhiThuocHetHan.getThoiGian());
    }
    
    @Override
    public void create(PhieuNhap e) {
        JDBCConnection.update(INSERT_SQL, e.getId(), e.getThoiGian(), e.getNhanVien().getId(), e.getNcc().getId(), e.getTongTien());
    }

    @Override
    public void update(PhieuNhap e) {
        JDBCConnection.update(UPDATE_SQL, e.getThoiGian(), e.getNhanVien().getId(), e.getNcc().getId(), e.getTongTien(), e.getId());
    }

    @Override
    public void deleteById(String id) {
        JDBCConnection.update(DELETE_BY_ID, id);
    }

    @Override
    protected List<PhieuNhap> selectBySql(String sql, Object... args) {
        List<PhieuNhap> listE = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.query(sql, args);
            while (rs.next()) {
                PhieuNhap e = new PhieuNhap();
                e.setId(rs.getString("idPN"));
                e.setThoiGian(rs.getTimestamp("thoiGian"));

                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getString("idNV"));
                nhanVien.setHoTen(rs.getString("tenNV"));
                nhanVien.setSdt(rs.getString("sdtNV"));
                nhanVien.setGioiTinh(rs.getString("gioiTinh"));
                nhanVien.setNamSinh(rs.getInt("namSinh"));
                nhanVien.setNgayVaoLam(rs.getDate("ngayVaoLam"));
                e.setNhanVien(nhanVien);

                String idNCC = rs.getString("idNCC");
                e.setNcc(new NhaCungCapController().selectById(idNCC));

                e.setTongTien(rs.getDouble("tongTien"));
                listE.add(e);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PhieuNhap> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhieuNhap selectById(String id) {
        List<PhieuNhap> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}

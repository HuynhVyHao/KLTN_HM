package dao;

import connectDB.JDBCConnection;
import entity.DanhMuc;
import entity.DonViTinh;
import entity.Thuoc; 
import entity.XuatXu; 

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThuocDAO extends InterfaceDAO<Thuoc, String> {

	private final String INSERT_SQL = "INSERT INTO Thuoc (idThuoc, tenThuoc, hinhAnh, thanhPhan, idDVT, idDM, idXX, soLuongTon, giaNhap, donGia, ngaySanXuat, hanSuDung, loaiThuoc) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; // Thêm loaiThuoc vào INSERT

    private final String UPDATE_SQL = "UPDATE Thuoc SET tenThuoc = ?, hinhAnh = ?, thanhPhan = ?, idDVT = ?, idDM = ?, idXX = ?, soLuongTon = ?, giaNhap = ?, donGia = ?, ngaySanXuat = ?, hanSuDung = ?, loaiThuoc = ? "
            + "WHERE idThuoc = ?"; // Thêm loaiThuoc vào UPDATE
    
    private final String DELETE_BY_ID = "DELETE from Thuoc where idThuoc = ?";

    private final String SELECT_ALL_SQL = "SELECT Thuoc.*, "
            + "DonViTinh.idDVT, DonViTinh.ten AS tenDVT, "
            + "DanhMuc.idDM, DanhMuc.ten AS tenDM, "
            + "XuatXu.idXX, XuatXu.ten AS tenXX "
            + "FROM Thuoc "
            + "INNER JOIN DonViTinh ON Thuoc.idDVT = DonViTinh.idDVT "
            + "INNER JOIN DanhMuc ON Thuoc.idDM = DanhMuc.idDM "
            + "INNER JOIN XuatXu ON Thuoc.idXX = XuatXu.idXX"; // Đảm bảo bao gồm cột ngaySanXuat trong SQL

    private final String SELECT_BY_ID = "SELECT Thuoc.*, "
            + "DonViTinh.idDVT, DonViTinh.ten AS tenDVT, "
            + "DanhMuc.idDM, DanhMuc.ten AS tenDM, "
            + "XuatXu.idXX, XuatXu.ten AS tenXX "
            + "FROM Thuoc "
            + "INNER JOIN DonViTinh ON Thuoc.idDVT = DonViTinh.idDVT "
            + "INNER JOIN DanhMuc ON Thuoc.idDM = DanhMuc.idDM "
            + "INNER JOIN XuatXu ON Thuoc.idXX = XuatXu.idXX "
            + "WHERE Thuoc.idThuoc = ?"; // Đảm bảo bao gồm cột ngaySanXuat trong SQL

    private final String UPDATE_SO_LUONG = "UPDATE Thuoc SET soLuongTon=? WHERE idThuoc = ?";

    private final String SELECT_THONG_KE_THUOC_BAN_CHAY = """
    	    SELECT 
    	        Thuoc.idThuoc,
    	        Thuoc.tenThuoc,
    	        SUM(ChiTietHoaDon.soLuong) AS tongSoLuong,
    	        SUM(ChiTietHoaDon.soLuong * ChiTietHoaDon.donGia) AS tongDoanhThu
    	    FROM ChiTietHoaDon
    	    INNER JOIN Thuoc ON ChiTietHoaDon.idThuoc = Thuoc.idThuoc
    	    GROUP BY Thuoc.idThuoc, Thuoc.tenThuoc
    	    ORDER BY tongSoLuong DESC;
    	""";

    	// Phương thức để lấy thống kê thuốc bán chạy
    	public List<String[]> selectThongKeThuocBanChay() {
    	    List<String[]> resultList = new ArrayList<>();
    	    try {
    	        ResultSet rs = JDBCConnection.query(SELECT_THONG_KE_THUOC_BAN_CHAY);
    	        while (rs.next()) {
    	            String idThuoc = rs.getString("idThuoc");
    	            String tenThuoc = rs.getString("tenThuoc");
    	            double tongSoLuong = rs.getDouble("tongSoLuong");
    	            double tongDoanhThu = rs.getDouble("tongDoanhThu");

    	            // Thêm kết quả thống kê vào danh sách
    	            resultList.add(new String[]{idThuoc, tenThuoc, String.valueOf(tongSoLuong), String.valueOf(tongDoanhThu)});
    	        }
    	        rs.getStatement().getConnection().close();
    	    } catch (Exception e) {
    	        throw new RuntimeException(e);
    	    }
    	    return resultList;
    	}
    
 // Truy vấn SQL để lấy thống kê thuốc còn hạn sử dụng và đã hết hạn sử dụng
    private final String SELECT_THONG_KE_THUOC = """
        SELECT 
            CASE
                WHEN hanSuDung >= GETDATE() THEN N'Còn hạn sử dụng'
                ELSE N'Đã hết hạn sử dụng'
            END AS trangThai,
            COUNT(*) AS soLuongThuoc,
            SUM(soLuongTon) AS tongSoLuong
        FROM Thuoc
        GROUP BY CASE
                     WHEN hanSuDung >= GETDATE() THEN N'Còn hạn sử dụng'
                     ELSE N'Đã hết hạn sử dụng'
                 END;
    """;

    // Phương thức để lấy thống kê thuốc
    public List<String[]> selectThongKeThuoc() {
        List<String[]> resultList = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.query(SELECT_THONG_KE_THUOC);
            while (rs.next()) {
                String trangThai = rs.getString("trangThai");
                int soLuongThuoc = rs.getInt("soLuongThuoc");
                int tongSoLuong = rs.getInt("tongSoLuong");

                // Thêm kết quả thống kê vào danh sách
                resultList.add(new String[]{trangThai, String.valueOf(soLuongThuoc), String.valueOf(tongSoLuong)});
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    
    @Override
    public void create(Thuoc e) {
        JDBCConnection.update(INSERT_SQL, e.getId(), e.getTenThuoc(), e.getHinhAnh(), e.getThanhPhan(), e.getDonViTinh().getId(),
                e.getDanhMuc().getId(), e.getXuatXu().getId(), e.getSoLuongTon(), e.getGiaNhap(), e.getDonGia(), e.getNgaySanXuat(), e.getHanSuDung(), e.getLoaiThuoc()); // Thêm loaiThuoc vào create
    }

    @Override
    public void update(Thuoc e) {
        JDBCConnection.update(UPDATE_SQL, e.getTenThuoc(), e.getHinhAnh(), e.getThanhPhan(), e.getDonViTinh().getId(),
                e.getDanhMuc().getId(), e.getXuatXu().getId(), e.getSoLuongTon(), e.getGiaNhap(), e.getDonGia(), e.getNgaySanXuat(), e.getHanSuDung(), e.getLoaiThuoc(), e.getId()); // Thêm loaiThuoc vào update
    }

    public void updateSoLuongTon(Thuoc e, int soLuong) {
        JDBCConnection.update(UPDATE_SO_LUONG, soLuong, e.getId());
    }

    @Override
    public void deleteById(String id) {
        JDBCConnection.update(DELETE_BY_ID, id);
    }

    @Override
    protected List<Thuoc> selectBySql(String sql, Object... args) {
        List<Thuoc> listE = new ArrayList<>();
        try {
            ResultSet rs = JDBCConnection.query(sql, args);
            while (rs.next()) {
                Thuoc thuoc = new Thuoc();
                thuoc.setId(rs.getString("idThuoc"));
                thuoc.setTen(rs.getString("tenThuoc"));
                thuoc.setHinhAnh(rs.getBytes("hinhAnh"));
                thuoc.setThanhPhan(rs.getString("thanhPhan"));

                // Create DonViTinh object
                DonViTinh donViTinh = new DonViTinh();
                donViTinh.setId(rs.getString("idDVT"));
                donViTinh.setTen(rs.getString("tenDVT"));
                thuoc.setDonViTinh(donViTinh);

                // Create DanhMuc object
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setId(rs.getString("idDM"));
                danhMuc.setTen(rs.getString("tenDM"));
                thuoc.setDanhMuc(danhMuc);

                // Create XuatXu object
                XuatXu xuatXu = new XuatXu();
                xuatXu.setId(rs.getString("idXX"));
                xuatXu.setTen(rs.getString("tenXX"));
                thuoc.setXuatXu(xuatXu);

                thuoc.setSoLuongTon(rs.getInt("soLuongTon"));
                thuoc.setGiaNhap(rs.getDouble("giaNhap"));
                thuoc.setDonGia(rs.getDouble("donGia"));
                thuoc.setNgaySanXuat(rs.getDate("ngaySanXuat")); // Thêm ngày sản xuất vào selectBySql
                thuoc.setHanSuDung(rs.getDate("hanSuDung"));
                thuoc.setLoaiThuoc(rs.getString("loaiThuoc")); 
                
                listE.add(thuoc);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Thuoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Thuoc selectById(String id) {
        List<Thuoc> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0); 
    } 
    
    public boolean isDuplicate(String tenThuoc) {
        final String CHECK_DUPLICATE_SQL = "SELECT COUNT(*) AS count FROM Thuoc WHERE LOWER(tenThuoc) = LOWER(?)";
        try {
            ResultSet rs = JDBCConnection.query(CHECK_DUPLICATE_SQL, tenThuoc);
            if (rs.next()) {
                int count = rs.getInt("count");
                rs.getStatement().getConnection().close();
                return count > 0; // Nếu count > 0, tức là đã có thuốc trùng lặp
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}

package entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class DatHang {

    private String idDH;
    private Timestamp thoiGian;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private double tongTien;
    private String trangThai;  // Trạng thái (Chưa thanh toán, Đã thanh toán)

    public DatHang() {
    }

    public DatHang(String id, Timestamp thoiGian, NhanVien nhanVien, KhachHang khachHang, double tongTien, String trangThai) {
        this.idDH = id;
        this.thoiGian = thoiGian;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }
    public boolean isThanhToan() {
        return "Đã thanh toán".equals(this.trangThai); // Nếu trạng thái là "Đã thanh toán", trả về true
    }

    // Getter and Setter methods
    public String getId() {
        return idDH;
    }

    public void setId(String id) {
        this.idDH = id;
    }

    public Timestamp getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Timestamp thoiGian) {
        this.thoiGian = thoiGian;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.idDH);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatHang other = (DatHang) obj;
        return Objects.equals(this.idDH, other.idDH);
    }

    @Override
    public String toString() {
        return "DatHang{" + "id=" + idDH + ", thoiGian=" + thoiGian + ", nhanVien=" + nhanVien + ", khachHang=" + khachHang + ", trangThai=" + trangThai + '}';
    }
}

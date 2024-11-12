package entity;

import java.util.Objects;

public class ChiTietDatHang {

    private DatHang datHang;
    private Thuoc thuoc;
    private int soLuong;
    private double donGia;

    public ChiTietDatHang() {
    }

    public ChiTietDatHang(DatHang datHang, Thuoc thuoc, int soLuong, double donGia) {
        this.datHang = datHang;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Getter and Setter methods
    public DatHang getDatHang() {
        return datHang;
    }

    public void setDatHang(DatHang datHang) {
        this.datHang = datHang;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return this.soLuong * this.donGia;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.datHang);
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
        final ChiTietDatHang other = (ChiTietDatHang) obj;
        return Objects.equals(this.datHang, other.datHang);
    }

    @Override
    public String toString() {
        return "ChiTietDatHang{" + "datHang=" + datHang + ", thuoc=" + thuoc + ", soLuong=" + soLuong + ", donGia=" + donGia + '}';
    }
}

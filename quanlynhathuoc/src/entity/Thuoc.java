package entity;

import java.util.Date;
import java.util.Objects;

public class Thuoc {

    private String id; 
    private String ten; 
    private byte[] hinhAnh; 
    private String thanhPhan;
    private DonViTinh donViTinh;
    private DanhMuc danhMuc;
    private XuatXu xuatXu;
    private int soLuongTon;
    private double giaNhap;
    private double donGia;
    private Date hanSuDung;
    private Date ngaySanXuat; // Thêm thuộc tính ngày sản xuất
    private String loaiThuoc; // Loại thuốc (Kê đơn, Không kê đơn)


    public Thuoc() {
    }

    public Thuoc(String id, String ten, byte[] hinhAnh, String thanhPhan, DonViTinh donViTinh, DanhMuc danhMuc, XuatXu xuatXu, int soLuongTon, double giaNhap, double donGia, Date hanSuDung, Date ngaySanXuat, String loaiThuoc) {
        this.id = id;
        this.ten = ten;
        this.hinhAnh = hinhAnh;
        this.thanhPhan = thanhPhan;
        this.donViTinh = donViTinh;
        this.danhMuc = danhMuc;
        this.xuatXu = xuatXu;
        this.soLuongTon = soLuongTon;
        this.giaNhap = giaNhap;
        this.donGia = donGia;
        this.ngaySanXuat = ngaySanXuat; 
        this.hanSuDung = hanSuDung;
        this.loaiThuoc = loaiThuoc; // Initialize the new field
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenThuoc() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getThanhPhan() {
        return thanhPhan;
    }

    public void setThanhPhan(String thanhPhan) {
        this.thanhPhan = thanhPhan;
    }

    public DonViTinh getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(DonViTinh donViTinh) {
        this.donViTinh = donViTinh;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public XuatXu getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(XuatXu xuatXu) {
        this.xuatXu = xuatXu;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
    public Date getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(Date ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }
    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }
    
    

    public String getLoaiThuoc() {
		return loaiThuoc;
	}

	public void setLoaiThuoc(String loaiThuoc) {
		this.loaiThuoc = loaiThuoc;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Thuoc other = (Thuoc) obj;
        return Objects.equals(this.id, other.id);
    }

    
}

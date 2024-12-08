package entity;

import java.sql.Timestamp;

public class ChiPhiThuocHetHan {
    private int id;          // Mã chi phí
    private Thuoc thuoc;     // Đối tượng thuốc liên kết với bảng Thuoc
    private double tongChiPhi; // Tổng chi phí của thuốc hết hạn
    private Timestamp thoiGian;

    // Constructor không tham số
    public ChiPhiThuocHetHan() {}

    // Constructor đầy đủ
    public ChiPhiThuocHetHan(int id, Thuoc thuoc, double tongChiPhi, Timestamp thoiGian) {
		super();
		this.id = id;
		this.thuoc = thuoc;
		this.tongChiPhi = tongChiPhi;
		this.thoiGian = thoiGian;
	}

	// Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public double getTongChiPhi() {
        return tongChiPhi;
    }

    public void setTongChiPhi(double tongChiPhi) {
        this.tongChiPhi = tongChiPhi;
    }
    
    

    public Timestamp getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(Timestamp thoiGian) {
		this.thoiGian = thoiGian;
	}

	@Override
	public String toString() {
		return "ChiPhiThuocHetHan [id=" + id + ", thuoc=" + thuoc + ", tongChiPhi=" + tongChiPhi + ", thoiGian="
				+ thoiGian + "]";
	}

}


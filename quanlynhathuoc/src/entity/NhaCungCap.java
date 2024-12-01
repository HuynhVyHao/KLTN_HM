package entity;

public class NhaCungCap {
    private String id;
    private String ten;
    private String sdt;
    private String diaChi;
    private DanhMuc danhMuc;

    public NhaCungCap() {
    }

    public NhaCungCap(String id) {
        this.id = id;
    }

    public NhaCungCap(String id, String ten, String sdt, String diaChi, DanhMuc danhMuc) {
		super();
		this.id = id;
		this.ten = ten;
		this.sdt = sdt;
		this.diaChi = diaChi;
		this.danhMuc = danhMuc;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    

    public DanhMuc getDanhMuc() {
		return danhMuc;
	}

	public void setDanhMuc(DanhMuc danhMuc) {
		this.danhMuc = danhMuc;
	}

	@Override
	public String toString() {
		return "NhaCungCap [id=" + id + ", ten=" + ten + ", sdt=" + sdt + ", diaChi=" + diaChi + ", danhMuc=" + danhMuc
				+ "]";
	}

}

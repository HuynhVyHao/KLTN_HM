package entity;

public class XuatXu {
    private String id;
    private String ten;

    public XuatXu() {
    }

    public XuatXu(String id) {
        this.id = id;
    }

    public XuatXu(String id, String ten) {
        this.id = id;
        this.ten = ten;
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

    @Override
    public String toString() {
        return ten;
    }
    
    
}

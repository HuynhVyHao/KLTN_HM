package controller;

import dao.ThuocDAO;
import entity.DanhMuc;
import entity.DonViTinh;
import entity.Thuoc;
import entity.XuatXu;
import gui.page.BaoCaoThuocPage;
import gui.page.ChiTietThuocPage;
import gui.page.CreateHoaDonPage;
import gui.page.ThuocPage;
import gui.page.TimKiemThuocPage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.MessageDialog;
import utils.Validation;

public class ThuocController extends InterfaceConTroller<Thuoc, String> {

    public ThuocDAO THUOC_DAO = new ThuocDAO();
    public ThuocPage THUOC_GUI;
    public TimKiemThuocPage THUOC_GUITK;
    public BaoCaoThuocPage THUOC_GUIBC;
    public ChiTietThuocPage THUOC_GUICT;

    public ThuocController() {
    }

    public ThuocController(ThuocPage THUOC_GUI) {
        this.THUOC_GUI = THUOC_GUI;
    }
    
    public ThuocController(TimKiemThuocPage THUOC_GUITK) {
    	this.THUOC_GUITK = THUOC_GUITK;
    }
    
    public ThuocController(BaoCaoThuocPage THUOC_GUIBC) {
    	this.THUOC_GUIBC = THUOC_GUIBC;
    }
    
    public ThuocController(ChiTietThuocPage THUOC_GUICT) {
    	this.THUOC_GUICT = THUOC_GUICT;
    }

    @Override
    public void create(Thuoc e) {
        THUOC_DAO.create(e);
    }

    @Override
    public void update(Thuoc e) {
        THUOC_DAO.update(e);
    }

    @Override
    public void deleteById(String id) {
        THUOC_DAO.deleteById(id);
    }

    @Override
    public List<Thuoc> getAllList() {
        return THUOC_DAO.selectAll();
    }

    public int getSoLuongThuoc() {
        return this.getAllList().size();
    }

    @Override
    public Thuoc selectById(String id) {
        return THUOC_DAO.selectById(id);
    }

    public void updateSoLuongTon(Thuoc e, int soLuong) {
        THUOC_DAO.updateSoLuongTon(e, soLuong);
    }

    public List<Thuoc> getSearchTable(String text, String searchType) {
        text = text.toLowerCase();
        List result = new ArrayList<Thuoc>();

        switch (searchType) {
            case "Tất cả" -> {
                for (Thuoc e : this.getAllList()) {
                    if (e.getId().toLowerCase().contains(text)
                            || e.getTenThuoc().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Mã" -> {
                for (Thuoc e : this.getAllList()) {
                    if (e.getId().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Tên" -> {
                for (Thuoc e : this.getAllList()) {
                    if (e.getTenThuoc().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            default ->
                throw new AssertionError();
        }

        return result;
    }

    // Cập nhật phương thức để bao gồm ngày sản xuất
    public List<Thuoc> getFilterTable(String tenDM, String tenDVT, String tenXX, long ngaySanXuat, long hanSuDung) {
        List<Thuoc> result = new ArrayList<>();

        for (Thuoc e : this.getAllList()) {
            boolean match = false;
         // Tính toán ngày sản xuất
            long timeNSX = e.getNgaySanXuat().getTime() - new Date().getTime();
            long dateNSX = TimeUnit.MILLISECONDS.toDays(timeNSX);

            long timeHSD = e.getHanSuDung().getTime() - new Date().getTime();
            long dateHSD = TimeUnit.MILLISECONDS.toDays(timeHSD);
            
            
            if (e.getXuatXu().getTen().equals(tenXX)) {
                match = true;
            } else if (e.getDanhMuc().getTen().equals(tenDM)) {
                match = true;
            } else if (e.getDonViTinh().getTen().equals(tenDVT)) {
                match = true;
            } else if (dateHSD < hanSuDung || dateNSX < ngaySanXuat) {
                match = true;
            }

            if (match) {
                result.add(e);
            }
        }

        return result;
    }

    // Cập nhật quá trình nhập dữ liệu từ file Excel
    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        JFileChooser jf = new JFileChooser();
        jf.setDialogTitle("Open file");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        jf.setFileFilter(fnef);
        int result = jf.showOpenDialog(null);

        int check = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);

                    String id = excelRow.getCell(0).getStringCellValue();
                    String tenThuoc = excelRow.getCell(1).getStringCellValue();
                    String image = excelRow.getCell(2).getStringCellValue();
                    byte[] hinhAnh = image.getBytes();
                    String thanhPhan = excelRow.getCell(3).getStringCellValue();

                    String idDVT = excelRow.getCell(4).getStringCellValue();
                    DonViTinh donViTinh = new DonViTinhController().selectById(idDVT);

                    String idDM = excelRow.getCell(5).getStringCellValue();
                    DanhMuc danhMuc = new DanhMucController().selectById(idDM);

                    String idXX = excelRow.getCell(6).getStringCellValue();
                    XuatXu xuatXu = new XuatXuController().selectById(idXX);

                    String sl = excelRow.getCell(7).getStringCellValue();
                    int soLuong = Integer.parseInt(sl);
                    String gn = excelRow.getCell(8).getStringCellValue();
                    double giaNhap = Double.parseDouble(gn);
                    String dg = excelRow.getCell(9).getStringCellValue();
                    double donGia = Double.parseDouble(dg);

                    // Thêm ngày sản xuất
                    String nsx = excelRow.getCell(10).getStringCellValue();
                    Date ngaySanXuat = new Date(nsx);

                    String hsd = excelRow.getCell(11).getStringCellValue();
                    Date hanSuDung = new Date(hsd);

                    // Validate row cell
                    if (Validation.isEmpty(id) || Validation.isEmpty(tenThuoc) || Validation.isEmpty(image)
                            || Validation.isEmpty(thanhPhan) || Validation.isEmpty(idDVT) || Validation.isEmpty(idDM)
                            || Validation.isEmpty(idXX) || Validation.isEmpty(sl) || Validation.isEmpty(gn) || Validation.isEmpty(dg)) {
                        check += 1;
                    } else {
                        Thuoc e = new Thuoc(id, tenThuoc, hinhAnh, thanhPhan, donViTinh, danhMuc, xuatXu, soLuong, giaNhap, donGia, ngaySanXuat, hanSuDung); // Thêm ngày sản xuất vào constructor
                        THUOC_DAO.create(e);
                        THUOC_GUI.loadTable(this.getAllList());
                    }
                }
                MessageDialog.info(null, "Nhập dữ liệu thành công!");

            } catch (FileNotFoundException ex) {
                MessageDialog.error(null, "Lỗi đọc file");
            } catch (IOException ex) {
                MessageDialog.error(null, "Lỗi đọc file");
            }
        }
        if (check != 0) {
            MessageDialog.error(null, "Có " + check + " dòng dữ liệu không được thêm vào!");
        }
    }
}

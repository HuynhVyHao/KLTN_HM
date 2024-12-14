package controller;

import dao.NhanVienDAO;
import entity.KhachHang;
import entity.NhanVien;
import gui.page.NhanVienPage;
import gui.page.TimKiemNhanVienPage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.MessageDialog;
import utils.Validation;
import static utils.Validation.isPhoneNumber;

public class NhanVienController extends InterfaceConTroller<NhanVien, String> {

    public NhanVienDAO NV_DAO = new NhanVienDAO();
    public NhanVienPage NV_GUI;
    public TimKiemNhanVienPage NV_GUITK;

    public NhanVienController() {
    }

    public NhanVienController(NhanVienPage NV_GUI) {
        this.NV_GUI = NV_GUI;
    }
    public NhanVienController(TimKiemNhanVienPage NV_GUITK) {
    	this.NV_GUITK = NV_GUITK;
    }

    @Override
    public void create(NhanVien e) {
        NV_DAO.create(e);
    }

    @Override
    public void update(NhanVien e) {
        NV_DAO.update(e);
    }

    @Override
    public void deleteById(String id) {
        NV_DAO.deleteById(id);
    }

    @Override
    public List<NhanVien> getAllList() {
        return NV_DAO.selectAll();
    }

    public int getSoLuongNV() {
        return this.getAllList().size();
    }

    @Override
    public NhanVien selectById(String id) {
        return NV_DAO.selectById(id);
    }

    public List<NhanVien> getSearchTable(String text, String searchType) {
        text = text.toLowerCase();
        List result = new ArrayList<NhanVien>();

        switch (searchType) {
            case "Tất cả" -> {
                for (NhanVien e : this.getAllList()) {
                    if (e.getId().toLowerCase().contains(text)
                            || e.getHoTen().toLowerCase().contains(text)
                            || e.getSdt().toLowerCase().contains(text)
                            || String.valueOf(e.getNamSinh()).toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Mã" -> {
                for (NhanVien e : this.getAllList()) {
                    if (e.getId().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Tên" -> {
                for (NhanVien e : this.getAllList()) {
                    if (e.getHoTen().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Số điện thoại" -> {
                for (NhanVien e : this.getAllList()) {
                    if (e.getGioiTinh().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Năm sinh" -> {
                for (NhanVien e : this.getAllList()) {
                    if (String.valueOf(e.getNamSinh()).toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            default ->
                throw new AssertionError();
        }

        return result;
    }

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
                    String hoTen = excelRow.getCell(1).getStringCellValue();
                    
                    // Xử lý cột số điện thoại
                    XSSFCell cellSDT = excelRow.getCell(2);
                    String sdt = "";
                    if (cellSDT.getCellType() == CellType.STRING) {
                        sdt = cellSDT.getStringCellValue();
                    } else if (cellSDT.getCellType() == CellType.NUMERIC) {
                        sdt = String.valueOf((long) cellSDT.getNumericCellValue()); // Chuyển số sang chuỗi
                    }

                    String gioitinh = excelRow.getCell(3).getStringCellValue();

                    // Xử lý năm sinh (kiểu chuỗi và số)
                    XSSFCell cellNS = excelRow.getCell(4);
                    int namSinh = 0;
                    if (cellNS.getCellType() == CellType.STRING) {
                        namSinh = Integer.parseInt(cellNS.getStringCellValue());
                    } else if (cellNS.getCellType() == CellType.NUMERIC) {
                        namSinh = (int) cellNS.getNumericCellValue();
                    }

                    // Xử lý ngày (Date hoặc String)
                    XSSFCell cellNgay = excelRow.getCell(5);
                    Date ngayVaoLam = null;
                    if (cellNgay.getCellType() == CellType.STRING) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");// Định dạng ngày từ chuỗi
                        try {
							ngayVaoLam = sdf.parse(cellNgay.getStringCellValue());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    } else if (cellNgay.getCellType() == CellType.NUMERIC) {
                        ngayVaoLam = cellNgay.getDateCellValue(); // Lấy ngày trực tiếp từ cell
                    }

                    // Kiểm tra giá trị
                    if (Validation.isEmpty(id) || Validation.isEmpty(hoTen)
                            || Validation.isEmpty(sdt) || !isPhoneNumber(sdt)
                            || Validation.isEmpty(gioitinh) || namSinh <= 0
                            || ngayVaoLam == null) {
                        check += 1;
                    } else {
                        // Thêm nhân viên
                        NhanVien nv = new NhanVien(id, hoTen, sdt, gioitinh, namSinh, ngayVaoLam);
                        NV_DAO.create(nv);
                        NV_GUI.loadTable();
                    }
                }

                MessageDialog.info(NV_GUI, "Nhập dữ liệu thành công!");

            } catch (FileNotFoundException ex) {
                MessageDialog.error(NV_GUI, "Lỗi đọc file");
            } catch (IOException ex) {
                MessageDialog.error(NV_GUI, "Lỗi đọc file");
            }
        }
        if (check != 0) {
            MessageDialog.error(NV_GUI, "Có " + check + " dòng dữ liệu không được thêm vào!");
        }
    }
    
    public boolean isPhoneNumberExists(String phoneNumber) {
        NhanVien nv = NV_DAO.selectBySdt(phoneNumber);
        return nv != null;  // Nếu tìm thấy nhân viên, trả về true
    }
}

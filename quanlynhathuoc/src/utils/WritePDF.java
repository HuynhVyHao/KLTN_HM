package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Chunk;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import entity.ChiTietHoaDon;
import entity.ChiTietPhieuNhap;
import entity.HoaDon;
import entity.PhieuNhap;

import java.util.Date;
import java.util.List;

public class WritePDF {

    private DecimalFormat formatter = new DecimalFormat("###,###,###");
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    private Document document = new Document();
    private FileOutputStream file;
    private JFrame jf = new JFrame();
    private FileDialog fd = new FileDialog(jf, "Xuất pdf", FileDialog.SAVE);
    private Font fontNormal10;
    private Font fontBold15;
    private Font fontBold25;
    private Font fontBoldItalic15;

    public WritePDF() {
        try {
            fontNormal10 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, Font.NORMAL);
            fontBold25 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, Font.NORMAL);
            fontBold15 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15, Font.NORMAL);
            fontBoldItalic15 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman Bold Italic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15, Font.NORMAL);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chooseURL(String url) {
        try {
            document.close();
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy đường dẫn " + url);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi chọn file!");
        }
    }

    public void setTitle(String title) {
        try {
            Paragraph pdfTitle = new Paragraph(new Phrase(title, fontBold25));
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfTitle);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    public String getFile(String name) {
        fd.pack();
        fd.setSize(800, 600);
        fd.validate();
        Rectangle rect = jf.getContentPane().getBounds();
        double width = fd.getBounds().getWidth();
        double height = fd.getBounds().getHeight();
        double x = rect.getCenterX() - (width / 2);
        double y = rect.getCenterY() - (height / 2);
        Point leftCorner = new Point();
        leftCorner.setLocation(x, y);
        fd.setLocation(leftCorner);
        fd.setFile(name);
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("null")) {
            return null;
        }
        return url;
    }

    public void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Chunk createWhiteSpace(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        return new Chunk(builder.toString());
    }

    public void printPhieuNhap(PhieuNhap phieuNhap, List<ChiTietPhieuNhap> listCTPN) {
        String url = "";
        try {
            fd.setTitle("In hóa đơn");
            fd.setLocationRelativeTo(null);
            url = getFile(phieuNhap.getId());
            if (url.equals("nullnull")) {
                return;
            }
            url = url + ".pdf";
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            Paragraph company = new Paragraph("Hiệu thuốc tây Pharma Store", fontBold15);
            company.add(new Chunk(createWhiteSpace(20)));
            Date today = new Date(System.currentTimeMillis());
            company.add(new Chunk("Thời gian in phiếu: " + formatDate.format(today), fontNormal10));
            company.setAlignment(Element.ALIGN_LEFT);
            document.add(company);
            // Thêm tên công ty vào file PDF
            document.add(Chunk.NEWLINE);
            Paragraph header = new Paragraph("THÔNG TIN HÓA ĐƠN", fontBold25);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            // Thêm dòng Paragraph vào file PDF

            Paragraph paragraph1 = new Paragraph("Mã phiếu: " + phieuNhap.getId(), fontNormal10);

            String kh = phieuNhap.getNcc().getTen();
            Paragraph paragraph2 = new Paragraph("Nhà cung cấp: " + kh, fontNormal10);
            paragraph2.add(new Chunk(createWhiteSpace(5)));

            String nv = phieuNhap.getNhanVien().getHoTen();
            Paragraph paragraph3 = new Paragraph("Người thực hiện: " + nv, fontNormal10);
            paragraph3.add(new Chunk(createWhiteSpace(5)));

            Paragraph paragraph4 = new Paragraph("Thời gian: " + formatDate.format(phieuNhap.getThoiGian()), fontNormal10);
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(Chunk.NEWLINE);

            // Thêm table 5 cột vào file PDF
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{40f, 20f, 20f, 20f, 20f});
            PdfPCell cell;

            table.addCell(new PdfPCell(new Phrase("Tên thuốc", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Đơn vị tính", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Đơn giá", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Số lượng", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Thành tiền", fontBold15)));
            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                table.addCell(cell);
            }

            //Truyen thong tin tung chi tiet vao table
            for (ChiTietPhieuNhap ctpn : listCTPN) {
                table.addCell(new PdfPCell(new Phrase(ctpn.getThuoc().getTenThuoc(), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(ctpn.getThuoc().getDonViTinh().toString(), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(formatter.format(ctpn.getDonGia()) + "đ", fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ctpn.getSoLuong()), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(formatter.format(ctpn.getDonGia() * ctpn.getSoLuong()) + "đ", fontNormal10)));
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thành tiền: " + formatter.format(phieuNhap.getTongTien()) + "đ", fontBold15));
            paraTongThanhToan.setIndentationLeft(300);

            document.add(paraTongThanhToan);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            Paragraph paragraph = new Paragraph();
            paragraph.setIndentationLeft(22);
            paragraph.add(new Chunk("Người lập phiếu", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(30)));
            paragraph.add(new Chunk("Người giao", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(30)));
            paragraph.add(new Chunk("Khách hàng", fontBoldItalic15));

            Paragraph sign = new Paragraph();
            sign.setIndentationLeft(20);
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(25)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(23)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            

            document.add(paragraph);
            document.add(sign);
            document.close();
            writer.close();
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        }
    }

    public void printHoaDon(HoaDon hoaDon, List<ChiTietHoaDon> listCTHD) throws WriterException{
        String url = "";
        try {
            fd.setTitle("In hóa đơn");
            fd.setLocationRelativeTo(null);
            url = getFile(hoaDon.getId());
            if (url.equals("nullnull")) {
                return;
            }
            url = url + ".pdf";
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            Paragraph company = new Paragraph("Hiệu thuốc tây H&M - GÒ VẤP", fontBold15);
            company.add(new Chunk(createWhiteSpace(20)));
            Date today = new Date(System.currentTimeMillis());
            company.add(new Chunk("Thời gian in phiếu: " + formatDate.format(today), fontNormal10));
            company.setAlignment(Element.ALIGN_LEFT);
            document.add(company);
            document.add(Chunk.NEWLINE);

            Paragraph header = new Paragraph("THÔNG TIN HÓA ĐƠN", fontBold25);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph paragraph1 = new Paragraph("Mã phiếu: " + hoaDon.getId(), fontNormal10);
            String kh = hoaDon.getKhachHang().getHoTen();
            Paragraph paragraph2 = new Paragraph("Khách hàng: " + kh, fontNormal10);
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            String nv = hoaDon.getNhanVien().getHoTen();
            Paragraph paragraph3 = new Paragraph("Người thực hiện: " + nv, fontNormal10);
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            Paragraph paragraph4 = new Paragraph("Thời gian: " + formatDate.format(hoaDon.getThoiGian()), fontNormal10);
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(Chunk.NEWLINE);

            // Thêm table 5 cột vào file PDF
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{40f, 20f, 20f, 20f, 20f});
            PdfPCell cell;

         // Tăng chiều cao cho các ô tiêu đề
            cell = new PdfPCell(new Phrase("Tên thuốc", fontBold15));
            cell.setFixedHeight(25f);  // Thiết lập chiều cao cho ô
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Đơn vị tính", fontBold15));
            cell.setFixedHeight(25f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Đơn giá", fontBold15));
            cell.setFixedHeight(25f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Số lượng", fontBold15));
            cell.setFixedHeight(25f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Thành tiền", fontBold15));
            cell.setFixedHeight(25f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa
            table.addCell(cell);

            // Tăng chiều cao cho các ô trống (ở đây là hàng sau tiêu đề)
            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                table.addCell(cell);
            }

            // Truyền thông tin từng chi tiết vào table
            double tongTien = 0;
            for (ChiTietHoaDon cthd : listCTHD) {
                table.addCell(new PdfPCell(new Phrase(cthd.getThuoc().getTenThuoc(), fontNormal10)));
                table.addCell(new PdfPCell(new Phrase(cthd.getThuoc().getDonViTinh().toString(), fontNormal10)));
                // Ô Đơn giá căn phải
                PdfPCell donGiaCell = new PdfPCell(new Phrase(formatter.format(cthd.getDonGia()) + "đ", fontNormal10));
                donGiaCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Căn phải
                table.addCell(donGiaCell);

                // Ô Số lượng căn phải
                PdfPCell soLuongCell = new PdfPCell(new Phrase(String.valueOf(cthd.getSoLuong()), fontNormal10));
                soLuongCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Căn phải
                table.addCell(soLuongCell);

                // Ô Thành tiền căn phải
                PdfPCell thanhTienCell = new PdfPCell(new Phrase(formatter.format(cthd.getDonGia() * cthd.getSoLuong()) + "đ", fontNormal10));
                thanhTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Căn phải
                table.addCell(thanhTienCell);

                // Cộng dồn tổng tiền
                tongTien += cthd.getDonGia() * cthd.getSoLuong();
            }

         // Thêm hàng Tổng Tiền
            PdfPCell tongTienCell = new PdfPCell(new Phrase("Tổng Tiền", fontBold15));
            tongTienCell.setColspan(4); // Gộp 4 cột
            tongTienCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa
            tongTienCell.setFixedHeight(25f); // Thiết lập chiều cao
            table.addCell(tongTienCell);

            // Ô chứa giá tổng tiền
            PdfPCell giaTienCell = new PdfPCell(new Phrase(formatter.format(tongTien) + "đ", fontBold15));
            giaTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Căn phải
            giaTienCell.setFixedHeight(25f);
            table.addCell(giaTienCell);
            
            
            document.add(table);
            document.add(Chunk.NEWLINE);

            // Tính VAT (nếu VAT là 5%)
            double vat = tongTien * 0.05; // Tính VAT 5%
            double tongSauThue = tongTien + vat; // Tổng sau khi cộng VAT

            // Hiển thị VAT
            Paragraph paraVAT = new Paragraph(new Phrase("VAT 5%: " + formatter.format(vat) + "đ", fontBold15));
            paraVAT.setIndentationLeft(300);
            document.add(paraVAT);
            document.add(Chunk.NEWLINE);

            // Hiển thị Tổng tiền sau thuế
            Paragraph paraTongSauThue = new Paragraph(new Phrase("Tổng tiền sau thuế: " + formatter.format(tongSauThue) + "đ", fontBold15));
            paraTongSauThue.setIndentationLeft(300);
            document.add(paraTongSauThue);	
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            
            Paragraph paragraph = new Paragraph();
            paragraph.setIndentationLeft(22);
            paragraph.add(new Chunk("Người lập phiếu", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(85)));
            paragraph.add(new Chunk("Khách hàng", fontBoldItalic15));

            Paragraph sign = new Paragraph();
            sign.setIndentationLeft(21);
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(82)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            
            Paragraph name = new Paragraph();
            name.setIndentationLeft(20);
            name.add(new Chunk(nv, fontBold15));
            name.add(new Chunk(createWhiteSpace(78)));
            name.add(new Chunk(kh, fontBold15));
            
            
            document.add(paragraph);
            document.add(sign);
            for (int i = 0; i < 4; i++) {
                document.add(Chunk.NEWLINE);
            }
            document.add(name);
            document.add(Chunk.NEWLINE);
            document.add(createHorizontalLine(77));
        
         // Tạo mã QR theo chuẩn MB Bank hoặc VNPAY QR
            String qrCodeText = "STK:1770414937; NganHang:BIDV;SoTien:" +hoaDon.getTongTien();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 150, 150);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Chuyển mã QR thành image trong iText
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);

            Image qrCodeImage = Image.getInstance(baos.toByteArray());
            qrCodeImage.scaleAbsolute(150, 150); // Đặt kích thước mã QR

            // Lấy đối tượng PdfContentByte để thêm nội dung với tọa độ
            PdfContentByte canvas = writer.getDirectContent();

            // Xác định tọa độ đặt mã QR (gần đáy trang)
            float qrX = (document.getPageSize().getWidth() - qrCodeImage.getScaledWidth()) / 2; // Căn giữa theo chiều ngang
            float qrY = document.bottom() + 65; // Vị trí gần đáy (điều chỉnh giá trị nếu cần)

            // Thêm mã QR vào vị trí đã xác định
            qrCodeImage.setAbsolutePosition(qrX, qrY);
            canvas.addImage(qrCodeImage);

            // Tạo Phrase cho nội dung
            Phrase phrase1 = new Phrase("STK: 070280237 - MB BANK", fontBold15); // Dòng đầu tiên
            Phrase phrase2 = new Phrase("HUYNH VY HAO", fontBold15); // Dòng thứ hai

            // Thêm đoạn văn bản thông tin tài khoản ngân hàng ngay dưới mã QR
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                    phrase1,
                    document.getPageSize().getWidth() / 2, qrY - 25, 0); // Điều chỉnh khoảng cách giữa QR và đoạn văn

            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                    phrase2,
                    document.getPageSize().getWidth() / 2, qrY - 45, 0); // Điều chỉnh khoảng cách cho dòng thứ hai




         
          
            document.close();
            writer.close();
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private Chunk createHorizontalLine(float length) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < length; i++) {
            line.append("=");
        }
        return new Chunk(line.toString(), fontNormal10); // Sử dụng font phù hợp
    }

}

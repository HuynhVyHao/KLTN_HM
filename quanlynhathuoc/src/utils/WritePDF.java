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
import java.net.URL;
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
    private Font fontNormal15;
    private Font fontBold15;
    private Font fontBold25;
    private Font fontBoldItalic15;

    public WritePDF() {
        try {
            fontNormal10 = new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, Font.NORMAL);
            fontNormal15= new Font(BaseFont.createFont("lib/TimesNewRoman/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15, Font.NORMAL);
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
            String khSDT = hoaDon.getKhachHang().getSdt();
            Paragraph paragraph3 = new Paragraph("SĐT: " + khSDT, fontNormal10);
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            String nv = hoaDon.getNhanVien().getHoTen();
            Paragraph paragraph4 = new Paragraph("Người thực hiện: " + nv, fontNormal10);
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            Paragraph paragraph5 = new Paragraph("Thời gian: " + formatDate.format(hoaDon.getThoiGian()), fontNormal10);
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
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

         // Tính VAT (nếu VAT là 5%)
            double vat = 0;
            double discountRate = 0;
            double discountAmount = 0;

            // Tính mức giảm giá
            if (tongTien >= 200000 && tongTien < 500000) {
                discountRate = 0.05; // Giảm giá 5%
            } else if (tongTien >= 500000) {
                discountRate = 0.10; // Giảm giá 10%
            }

            // Tính số tiền giảm giá
            discountAmount = tongTien * discountRate;

            // Tính tổng tiền sau giảm giá
            double discountedTotal = tongTien - discountAmount;

            // Tính VAT trên tổng tiền đã giảm
            vat = discountedTotal * 0.05; // Tính VAT 5%
            double tongSauThue = discountedTotal + vat; // Tổng sau khi cộng VAT 

            // Hàng Tổng Tiền
            PdfPCell tongTienCell = new PdfPCell(new Phrase("Tổng Thành Tiền", fontNormal15));
            tongTienCell.setColspan(4); // Gộp 4 cột
            tongTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tongTienCell.setFixedHeight(25f);
            tongTienCell.setBorder(PdfPCell.TOP | PdfPCell.LEFT | PdfPCell.RIGHT); // Giữ viền trên, trái, và phải
            table.addCell(tongTienCell);

            // Ô chứa giá tổng tiền
            PdfPCell giaTienCell = new PdfPCell(new Phrase(formatter.format(tongTien) + "đ", fontNormal15));
            giaTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            giaTienCell.setFixedHeight(25f);
            giaTienCell.setBorder(PdfPCell.RIGHT); // Giữ viền phải, bỏ viền trái và dưới
            table.addCell(giaTienCell);

            // Hàng Giảm Giá
            PdfPCell discountCell = new PdfPCell(new Phrase("Giảm Giá (" + (discountRate * 100) + "%)", fontNormal15));
            discountCell.setColspan(4); // Gộp 4 cột
            discountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            discountCell.setFixedHeight(25f);
            discountCell.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT); // Giữ viền trái và phải
            table.addCell(discountCell);

            // Ô chứa giá giảm giá
            PdfPCell giaDiscountCell = new PdfPCell(new Phrase(formatter.format(discountedTotal) + "đ", fontNormal15));
            giaDiscountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            giaDiscountCell.setFixedHeight(25f);
            giaDiscountCell.setBorder(PdfPCell.RIGHT); // Giữ viền phải
            table.addCell(giaDiscountCell);

            // Hàng VAT
            PdfPCell vatCell = new PdfPCell(new Phrase("VAT 5%", fontNormal15));
            vatCell.setColspan(4); // Gộp 4 cột
            vatCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            vatCell.setFixedHeight(25f);
            vatCell.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT); // Giữ viền trái và phải
            table.addCell(vatCell);

            // Ô chứa giá VAT
            PdfPCell giaVatCell = new PdfPCell(new Phrase(formatter.format(vat) + "đ", fontNormal15));
            giaVatCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            giaVatCell.setFixedHeight(25f);
            giaVatCell.setBorder(PdfPCell.RIGHT); // Giữ viền phải
            table.addCell(giaVatCell);

            // Hàng Tổng Tiền Sau Thuế
            PdfPCell tongSauThueCell = new PdfPCell(new Phrase("Tổng Tiền", fontBold15));
            tongSauThueCell.setColspan(4); // Gộp 4 cột
            tongSauThueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tongSauThueCell.setFixedHeight(25f);
            table.addCell(tongSauThueCell);

            // Ô chứa giá Tổng Tiền Sau Thuế
            PdfPCell giaTongSauThueCell = new PdfPCell(new Phrase(formatter.format(tongSauThue) + "đ", fontBold15));
            giaTongSauThueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            giaTongSauThueCell.setFixedHeight(25f);
            table.addCell(giaTongSauThueCell);


            document.add(table);
            document.add(Chunk.NEWLINE);

         // Tạo bảng với 2 cột
            PdfPTable tableFooter = new PdfPTable(2);
            tableFooter.setWidthPercentage(100); // Căn chỉnh cho bảng rộng 100%
            tableFooter.setWidths(new float[] {1, 1}); // Đặt tỷ lệ các cột bằng nhau

            // Dòng 1: "Người lập phiếu" và "Khách hàng"
            PdfPCell cell1 = new PdfPCell(new Phrase("Người lập phiếu", fontBoldItalic15));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(PdfPCell.NO_BORDER);
            tableFooter.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("Khách hàng", fontBoldItalic15));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBorder(PdfPCell.NO_BORDER);
            tableFooter.addCell(cell2);

            // Dòng 2: "(Ký và ghi rõ họ tên)"
            PdfPCell cell3 = new PdfPCell(new Phrase("(Ký và ghi rõ họ tên)", fontNormal10));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setBorder(PdfPCell.NO_BORDER);
            tableFooter.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase("(Ký và ghi rõ họ tên)", fontNormal10));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBorder(PdfPCell.NO_BORDER);
            tableFooter.addCell(cell4);

            // Dòng 3: Tên nv và kh
            PdfPCell cell5 = new PdfPCell(new Phrase(nv, fontBold15));
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5.setBorder(PdfPCell.NO_BORDER);
            tableFooter.addCell(cell5);

            PdfPCell cell6 = new PdfPCell(new Phrase(kh, fontBold15));
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell6.setBorder(PdfPCell.NO_BORDER);
            tableFooter.addCell(cell6);

            // Thêm bảng vào tài liệu
            document.add(tableFooter);

            
            document.add(Chunk.NEWLINE);
            document.add(createHorizontalLine(77));
         // Tạo đoạn văn bản thông tin
            String[] infoLines = {
                "Không áp dụng đổi trả với các sản phẩm được khuyến mãi.",
                "Khách hàng đồng ý nhận thuốc và thanh toán.",
                "Vui lòng mang theo Hóa đơn để tiện việc tra cứu đơn hàng đổi trả.",
                "(Chỉ xuất Hóa đơn tài chính trong ngày)"
            };

            // Thêm từng dòng vào tài liệu
            for (String line : infoLines) {
                Paragraph infoParagraph = new Paragraph(line, fontNormal15); // fontNormal là kiểu chữ bạn đã định nghĩa
                infoParagraph.setAlignment(Element.ALIGN_CENTER); // Căn trái
                document.add(infoParagraph);
            }
        
         // Cài đặt mã QR theo chuẩn VietQR
            String bankCode = "BIDV";  // Mã ngân hàng, có thể thay đổi
            String accountNumber = "1770414937";  // Số tài khoản
            String amount = String.valueOf((int) tongSauThue);  // Số tiền
            String addInfo = "ThanhToanHoaDon" + hoaDon.getId();  // Nội dung thanh toán

            // URL QR code VietQR
            String vietQRUrl = "https://img.vietqr.io/image/" + bankCode + "-" + accountNumber +
                               "-qr_only.png?amount=" + amount + "&addInfo=" + addInfo;
            URL qrURL = new URL(vietQRUrl);
            BufferedImage qrImage = ImageIO.read(qrURL);  // Đọc hình ảnh từ URL

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            Image qrCodeImage = Image.getInstance(baos.toByteArray());
            qrCodeImage.scaleAbsolute(150, 150);  // Đặt kích thước mã QR

            // Vị trí mã QR trong PDF
            PdfContentByte canvas = writer.getDirectContent();
            float qrX = (document.getPageSize().getWidth() - qrCodeImage.getScaledWidth()) / 2;
            float qrY = document.bottom() + 50;

            qrCodeImage.setAbsolutePosition(qrX, qrY);
            canvas.addImage(qrCodeImage);

            // Hiển thị thông tin tài khoản dưới mã QR
            Phrase phrase1 = new Phrase("STK: 113366668888 - BIDV", fontBold15);
            Phrase phrase2 = new Phrase("HUYNH VY HAO", fontBold15);

            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase1,
                                       document.getPageSize().getWidth() / 2, qrY - 25, 0);

            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase2,
                                       document.getPageSize().getWidth() / 2, qrY - 45, 0);
          
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

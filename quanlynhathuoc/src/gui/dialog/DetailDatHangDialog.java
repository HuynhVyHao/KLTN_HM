package gui.dialog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.google.zxing.WriterException;

import controller.ChiTietDatHangController;
import controller.DatHangController;
import controller.ThuocController;
import entity.ChiTietDatHang;
import entity.ChiTietHoaDon;
import entity.DatHang;
import entity.HoaDon;
import entity.Thuoc;
import gui.page.DatHangPage;
import gui.page.ThuocPage;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.WritePDF;

public class DetailDatHangDialog extends javax.swing.JDialog {

	private final ChiTietDatHangController CTDH_CON = new ChiTietDatHangController();
	private final DatHangController DH_CON= new DatHangController();
	private DatHangPage DH_GUI;
	private List<ChiTietDatHang> listCTDH;
	private final ThuocController THUOC_CON = new ThuocController();
	private List<Thuoc> listThuoc = THUOC_CON.getAllList();

	private DefaultTableModel modal;

	public DetailDatHangDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public DetailDatHangDialog(java.awt.Frame parent, boolean modal, List<ChiTietDatHang> ctdh,DatHangPage DH_GUI) {
		super(parent, modal);
		initComponents();
		this.listCTDH = ctdh;
		this.DH_GUI = DH_GUI;
		fillInput();
		fillTable();
	}

	private void fillInput() {
		DatHang datHang = listCTDH.get(0).getDatHang();
		txtMaHD.setText(datHang.getId());
		txtTenKH.setText(datHang.getKhachHang().getHoTen());
		txtTenNV.setText(datHang.getNhanVien().getHoTen());
	}

	private void fillTable() {
		modal = new DefaultTableModel();
		String[] header = new String[] { "STT", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền",
				"Trạng thái" };
		modal.setColumnIdentifiers(header);
		table.setModel(modal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, centerRenderer);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);

		loadTableCTHD(listCTDH);
	}

	public void loadTableCTHD(List<ChiTietDatHang> list) {
		modal.setRowCount(0);

		listCTDH = list;
		int stt = 1;
		double sum = 0;
		for (ChiTietDatHang e : listCTDH) {
			sum += e.getThanhTien();
			modal.addRow(new Object[] { String.valueOf(stt), e.getThuoc().getTenThuoc(), e.getThuoc().getDonViTinh(),
					e.getSoLuong(), Formatter.FormatVND(e.getDonGia()), Formatter.FormatVND(e.getThanhTien()),
					e.getDatHang().getTrangThai() });
			stt++;
		}
		txtTong.setText(Formatter.FormatVND(sum));
	}

	private void initComponents() {

		jPanel15 = new javax.swing.JPanel();
		jLabel8 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		hoaDonPanel = new javax.swing.JPanel();
		jPanel7 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		txtMaHD = new javax.swing.JTextField();
		jPanel9 = new javax.swing.JPanel();
		jLabel5 = new javax.swing.JLabel();
		txtTenKH = new javax.swing.JTextField();
		jPanel11 = new javax.swing.JPanel();
		jLabel7 = new javax.swing.JLabel();
		txtTenNV = new javax.swing.JTextField();
		jPanel3 = new javax.swing.JPanel();
		imagePanel = new javax.swing.JPanel();
		txtHinhAnh = new javax.swing.JLabel();
		tableItemPanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		table = new javax.swing.JTable();
		jPanel1 = new javax.swing.JPanel();
		jPanel12 = new javax.swing.JPanel();
		jLabel9 = new javax.swing.JLabel();
		txtTong = new javax.swing.JTextField();
		jPanel16 = new javax.swing.JPanel();
		lblThuoc = new javax.swing.JLabel();
		jPanel8 = new javax.swing.JPanel();
		btnHuy = new javax.swing.JButton();
		btnPrint = new javax.swing.JButton();
		btnDoiThuoc = new javax.swing.JButton();
		btnTraThuoc = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jPanel15.setBackground(new java.awt.Color(0, 153, 153));
		jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
		jPanel15.setPreferredSize(new java.awt.Dimension(500, 50));
		jPanel15.setLayout(new java.awt.BorderLayout());

		jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 18));
		jLabel8.setForeground(new java.awt.Color(255, 255, 255));
		jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel8.setText("CHI TIẾT ĐẶT HÀNG");
		jLabel8.setPreferredSize(new java.awt.Dimension(149, 40));
		jPanel15.add(jLabel8, java.awt.BorderLayout.CENTER);

		getContentPane().add(jPanel15, java.awt.BorderLayout.NORTH);

		jPanel2.setBackground(new java.awt.Color(255, 255, 255));
		jPanel2.setLayout(new java.awt.BorderLayout());

		hoaDonPanel.setBackground(new java.awt.Color(255, 255, 255));
		hoaDonPanel.setPreferredSize(new java.awt.Dimension(1200, 80));
		hoaDonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 16));

		jPanel7.setBackground(new java.awt.Color(255, 255, 255));
		jPanel7.setPreferredSize(new java.awt.Dimension(340, 40));
		jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

		jLabel4.setFont(new java.awt.Font("Roboto", 0, 14));
		jLabel4.setText("Mã đặt hàng ");
		jLabel4.setPreferredSize(new java.awt.Dimension(120, 40));
		jPanel7.add(jLabel4);

		txtMaHD.setEditable(false);
		txtMaHD.setFont(new java.awt.Font("Roboto Mono", 1, 14));
		txtMaHD.setText("Z2NX8CN1A");
		txtMaHD.setFocusable(false);
		txtMaHD.setPreferredSize(new java.awt.Dimension(200, 40));
		jPanel7.add(txtMaHD);

		hoaDonPanel.add(jPanel7);

		jPanel9.setBackground(new java.awt.Color(255, 255, 255));
		jPanel9.setPreferredSize(new java.awt.Dimension(340, 40));
		jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

		jLabel5.setFont(new java.awt.Font("Roboto", 0, 14));
		jLabel5.setText("Tên khách hàng");
		jLabel5.setPreferredSize(new java.awt.Dimension(120, 40));
		jPanel9.add(jLabel5);

		txtTenKH.setEditable(false);
		txtTenKH.setFont(new java.awt.Font("Roboto", 0, 14));
		txtTenKH.setText("Nguyễn Văn A");
		txtTenKH.setFocusable(false);
		txtTenKH.setPreferredSize(new java.awt.Dimension(200, 40));
		jPanel9.add(txtTenKH);

		hoaDonPanel.add(jPanel9);

		jPanel11.setBackground(new java.awt.Color(255, 255, 255));
		jPanel11.setPreferredSize(new java.awt.Dimension(340, 40));
		jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

		jLabel7.setFont(new java.awt.Font("Roboto", 0, 14));
		jLabel7.setText("Tên nhân viên");
		jLabel7.setPreferredSize(new java.awt.Dimension(120, 40));
		jPanel11.add(jLabel7);

		txtTenNV.setEditable(false);
		txtTenNV.setFont(new java.awt.Font("Roboto", 0, 14));
		txtTenNV.setText("Vũ Nương");
		txtTenNV.setFocusable(false);
		txtTenNV.setPreferredSize(new java.awt.Dimension(200, 40));
		jPanel11.add(txtTenNV);

		hoaDonPanel.add(jPanel11);

		jPanel2.add(hoaDonPanel, java.awt.BorderLayout.PAGE_START);

		jPanel3.setBackground(new java.awt.Color(255, 255, 255));
		jPanel3.setPreferredSize(new java.awt.Dimension(400, 100));

		imagePanel.setBackground(new java.awt.Color(255, 255, 255));
		imagePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
		imagePanel.setPreferredSize(new java.awt.Dimension(300, 300));
		imagePanel.setLayout(new java.awt.BorderLayout());

		txtHinhAnh.setBackground(new java.awt.Color(255, 255, 255));
		txtHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		txtHinhAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
		txtHinhAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		txtHinhAnh.setPreferredSize(new java.awt.Dimension(200, 100));
		imagePanel.add(txtHinhAnh, java.awt.BorderLayout.CENTER);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
						.addContainerGap(26, Short.MAX_VALUE).addComponent(imagePanel,
								javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(24, 24, 24)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel3Layout.createSequentialGroup().addContainerGap(84, Short.MAX_VALUE)
								.addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(136, 136, 136)));

		jPanel2.add(jPanel3, java.awt.BorderLayout.WEST);

		tableItemPanel.setLayout(new java.awt.BorderLayout());

		jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));

		table.setFocusable(false);
		table.setRowHeight(40);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setShowHorizontalLines(true);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(table);

		tableItemPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setPreferredSize(new java.awt.Dimension(800, 60));
		jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

		jPanel12.setBackground(new java.awt.Color(255, 255, 255));
		jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

		jLabel9.setFont(new java.awt.Font("Roboto", 1, 14));
		jLabel9.setForeground(new java.awt.Color(255, 51, 0));
		jLabel9.setText("Tổng hóa đơn:");
		jLabel9.setPreferredSize(new java.awt.Dimension(120, 40));
		jPanel12.add(jLabel9);

		txtTong.setEditable(false);
		txtTong.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14));
		txtTong.setForeground(new java.awt.Color(255, 51, 0));
		txtTong.setText("1000000");
		txtTong.setFocusable(false);
		txtTong.setPreferredSize(new java.awt.Dimension(200, 40));
		jPanel12.add(txtTong);

		jPanel1.add(jPanel12);

		tableItemPanel.add(jPanel1, java.awt.BorderLayout.PAGE_END);

		jPanel16.setBackground(new java.awt.Color(0, 153, 153));
		jPanel16.setMinimumSize(new java.awt.Dimension(100, 60));
		jPanel16.setPreferredSize(new java.awt.Dimension(500, 30));
		jPanel16.setLayout(new java.awt.BorderLayout());

		lblThuoc.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		lblThuoc.setForeground(new java.awt.Color(255, 255, 255));
		lblThuoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblThuoc.setText("Thông tin thuốc");
		jPanel16.add(lblThuoc, java.awt.BorderLayout.CENTER);

		tableItemPanel.add(jPanel16, java.awt.BorderLayout.NORTH);

		jPanel2.add(tableItemPanel, java.awt.BorderLayout.CENTER);

		getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

		jPanel8.setBackground(new java.awt.Color(255, 255, 255));
		jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 5));

		btnHuy.setBackground(new java.awt.Color(255, 102, 102));
		btnHuy.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16));
		btnHuy.setForeground(new java.awt.Color(255, 255, 255));
		btnHuy.setText("HỦY BỎ");
		btnHuy.setBorderPainted(false);
		btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnHuy.setFocusPainted(false);
		btnHuy.setFocusable(false);
		btnHuy.setPreferredSize(new java.awt.Dimension(200, 40));
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});
		jPanel8.add(btnHuy);

		btnPrint.setBackground(new java.awt.Color(0, 153, 153));
		btnPrint.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16));
		btnPrint.setForeground(new java.awt.Color(255, 255, 255));
		btnPrint.setText("In hóa đơn");
		btnPrint.setBorderPainted(false);
		btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnPrint.setFocusPainted(false);
		btnPrint.setFocusable(false);
		btnPrint.setPreferredSize(new java.awt.Dimension(200, 40));
		btnPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					btnPrintActionPerformed(evt);
				} catch (WriterException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		jPanel8.add(btnPrint);

		// Định dạng nút Đổi thuốc
		btnDoiThuoc.setBackground(new java.awt.Color(102, 204, 255));
		btnDoiThuoc.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16));
		btnDoiThuoc.setForeground(new java.awt.Color(255, 255, 255));
		btnDoiThuoc.setText("ĐỔI THUỐC");
		btnDoiThuoc.setBorderPainted(false);
		btnDoiThuoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnDoiThuoc.setFocusPainted(false);
		btnDoiThuoc.setFocusable(false);
		btnDoiThuoc.setPreferredSize(new java.awt.Dimension(200, 40));
		btnDoiThuoc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDoiThuocActionPerformed(evt);
			}
		});
		jPanel8.add(btnDoiThuoc);

		// Định dạng nút Trả thuốc
		btnTraThuoc.setBackground(new java.awt.Color(45, 122, 247));
		btnTraThuoc.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16));
		btnTraThuoc.setForeground(new java.awt.Color(255, 255, 255));
		btnTraThuoc.setText("TRẢ THUỐC");
		btnTraThuoc.setBorderPainted(false);
		btnTraThuoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnTraThuoc.setFocusPainted(false);
		btnTraThuoc.setFocusable(false);
		btnTraThuoc.setPreferredSize(new java.awt.Dimension(200, 40));
		btnTraThuoc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnTraThuocActionPerformed(evt);
			}
		});
		jPanel8.add(btnTraThuoc);

		getContentPane().add(jPanel8, java.awt.BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
	}

	private void btnTraThuocActionPerformed(java.awt.event.ActionEvent evt) {
		  // Lấy thông tin của đơn hàng (idDH)
	    String idDH = listCTDH.get(0).getDatHang().getId(); // Giả sử tất cả các ChiTietDatHang có cùng id đơn hàng

	    // Lấy đối tượng DatHang từ cơ sở dữ liệu
	    DatHang datHang = DH_CON.selectById(idDH); // Giả sử có phương thức selectById trong DatHangController

	    if (datHang == null) {
	        JOptionPane.showMessageDialog(this, "Đơn hàng không tồn tại.");
	        return;
	    }

	    // Kiểm tra trạng thái đơn hàng
	    if ("Đã Thanh Toán".equalsIgnoreCase(datHang.getTrangThai())) { // Kiểm tra trạng thái
	        JOptionPane.showMessageDialog(this, "Đơn đã thanh toán, không được phép trả thuốc.");
	        return;
	    }
		
		// Lấy danh sách thuốc trong chi tiết đơn hàng (listCTDH)
	    String[] options = new String[listCTDH.size()];
	    for (int i = 0; i < listCTDH.size(); i++) {
	        options[i] = listCTDH.get(i).getThuoc().getTenThuoc(); // Hiển thị tên thuốc
	    }

	    // Người dùng chọn thuốc cần trả
	    String selectedMedicine = (String) JOptionPane.showInputDialog(this, "Chọn thuốc cần trả:", "Trả thuốc",
	            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

	    if (selectedMedicine != null) {
	        // Tìm thuốc đã chọn trong danh sách
	        ChiTietDatHang selectedItem = null;
	        int indexToRemove = -1;  // Biến lưu index của thuốc cần xóa
	        for (int i = 0; i < listCTDH.size(); i++) {
	            if (listCTDH.get(i).getThuoc().getTenThuoc().equals(selectedMedicine)) {
	                selectedItem = listCTDH.get(i);
	                indexToRemove = i;
	                break;
	            }
	        }

	        // Nếu tìm thấy thuốc, xóa khỏi danh sách và cập nhật lại bảng
	        if (selectedItem != null && indexToRemove != -1) {
	            listCTDH.remove(indexToRemove); // Xóa thuốc từ danh sách
	            JOptionPane.showMessageDialog(this, "Thuốc " + selectedMedicine + " đã được trả lại.");

	            // Cập nhật lại bảng và danh sách thuốc chưa thanh toán
	            loadTableCTHD(listCTDH); // Gọi lại phương thức để cập nhật bảng
	            updateDanhSachThuocChuaThanhToan(listCTDH); // Cập nhật lại danh sách thuốc chưa thanh toán

	            // Tính lại tổng tiền của đơn hàng
	            double tongTienMoi = 0;
	            for (ChiTietDatHang item : listCTDH) {
	                tongTienMoi += item.getThuoc().getDonGia(); // Tính tổng tiền mới
	            }

	            // Lấy đối tượng DatHang từ cơ sở dữ liệu để cập nhật lại tổng tiền
	            if (datHang != null) {
	                datHang.setTongTien(tongTienMoi); // Cập nhật lại tổng tiền
	                DH_CON.update(datHang); // Cập nhật đơn hàng trong cơ sở dữ liệu
	                JOptionPane.showMessageDialog(this, "Tổng tiền của đơn hàng đã được cập nhật.");
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Chưa chọn thuốc nào để trả.");
	    }
	}



	// Cập nhật danh sách thuốc chưa thanh toán trong hệ thống
	private void updateDanhSachThuocChuaThanhToan(List<ChiTietDatHang> updatedList) {
	    // Cập nhật lại danh sách thuốc chưa thanh toán trong cơ sở dữ liệu
	    String idDH = updatedList.get(0).getDatHang().getId(); // Giả sử tất cả đều có id đơn hàng giống nhau
	    CTDH_CON.update(idDH, updatedList); // Cập nhật lại danh sách thuốc chưa thanh toán mới
	}


	private void btnDoiThuocActionPerformed(java.awt.event.ActionEvent evt) {
		  // Lấy thông tin của đơn hàng (idDH)
	    String idDH = listCTDH.get(0).getDatHang().getId(); // Giả sử tất cả các ChiTietDatHang có cùng id đơn hàng

	    // Lấy đối tượng DatHang từ cơ sở dữ liệu
	    DatHang datHang = DH_CON.selectById(idDH); // Giả sử có phương thức selectById trong DatHangController

	    if (datHang == null) {
	        JOptionPane.showMessageDialog(this, "Đơn hàng không tồn tại.");
	        return;
	    }

	    // Kiểm tra trạng thái đơn hàng
	    if ("Đã Thanh Toán".equalsIgnoreCase(datHang.getTrangThai())) { // Kiểm tra trạng thái
	        JOptionPane.showMessageDialog(this, "Đơn đã thanh toán, không được phép đổi thuốc.");
	        return;
	    }
		
		// Lấy danh sách thuốc trong chi tiết đơn hàng (listCTDH)
	    String[] options = new String[listCTDH.size()];
	    for (int i = 0; i < listCTDH.size(); i++) {
	        options[i] = listCTDH.get(i).getThuoc().getTenThuoc(); // Hiển thị tên thuốc
	    }

	    // Người dùng chọn thuốc cần đổi
	    String selectedMedicine = (String) JOptionPane.showInputDialog(this,
	            "Chọn thuốc cần đổi:",
	            "Đổi thuốc",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            options,
	            options[0]);

	    if (selectedMedicine == null) {
	        JOptionPane.showMessageDialog(this, "Chưa chọn thuốc nào để đổi.");
	        return;
	    }

	    // Tìm thuốc đã chọn trong danh sách
	    ChiTietDatHang selectedItem = null;
	    for (ChiTietDatHang item : listCTDH) {
	        if (item.getThuoc().getTenThuoc().equals(selectedMedicine)) {
	            selectedItem = item;
	            break;
	        }
	    }

	    if (selectedItem == null) {
	        JOptionPane.showMessageDialog(this, "Thuốc đã chọn không hợp lệ.");
	        return;
	    }

	    // Lọc danh sách thuốc còn hạn sử dụng hợp lệ và không phải thuốc cần đổi
	    List<Thuoc> validMedicines = new ArrayList<>();
	    for (Thuoc thuoc : listThuoc) {
	        if (isMedicineValid(thuoc) && !thuoc.getTenThuoc().equals(selectedMedicine)) {
	            validMedicines.add(thuoc);
	        }
	    }

	    if (validMedicines.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Không có thuốc thay thế hợp lệ.");
	        return;
	    }

	    // Hiển thị bảng thuốc thay thế
	    String[] header = new String[]{"STT", "Tên thuốc", "Danh mục", "Xuất xứ", "Đơn vị tính", "Đơn giá"};
	    DefaultTableModel model = new DefaultTableModel();
	    model.setColumnIdentifiers(header);

	    for (int i = 0; i < validMedicines.size(); i++) {
	        Thuoc thuoc = validMedicines.get(i);
	        model.addRow(new Object[]{
	                i + 1,
	                thuoc.getTenThuoc(),
	                thuoc.getDanhMuc().getTen(),
	                thuoc.getXuatXu().getTen(),
	                thuoc.getDonViTinh().getTen(),
	                Formatter.FormatVND(thuoc.getDonGia())
	        });
	    }

	    JTable table = new JTable(model);
	    table.setRowHeight(40);
	    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

	    JScrollPane jScrollPane1 = new JScrollPane(table);
	    jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 400));

	    // Hiển thị bảng thuốc thay thế
	    int result = JOptionPane.showConfirmDialog(this, jScrollPane1,
	            "Chọn thuốc thay thế",
	            JOptionPane.OK_CANCEL_OPTION,
	            JOptionPane.INFORMATION_MESSAGE);

	    if (result != JOptionPane.OK_OPTION) {
	        JOptionPane.showMessageDialog(this, "Đổi thuốc đã bị hủy.");
	        return;
	    }

	    // Lấy thuốc mới từ bảng
	    int selectedRow = table.getSelectedRow();
	    if (selectedRow < 0) {
	        JOptionPane.showMessageDialog(this, "Chưa chọn thuốc mới.");
	        return;
	    }

	    Thuoc newSelectedMedicine = validMedicines.get(selectedRow);

	    // Nhập số lượng thuốc mới
	    String quantityInput = JOptionPane.showInputDialog(this, "Nhập số lượng thuốc mới:");
	    int quantity;
	    try {
	        quantity = Integer.parseInt(quantityInput);
	        if (quantity <= 0) throw new NumberFormatException();
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Số lượng thuốc không hợp lệ. Vui lòng nhập số lớn hơn 0.");
	        return;
	    }

	    // Cập nhật chi tiết đơn hàng với thuốc mới
	    selectedItem.setThuoc(newSelectedMedicine);
	    selectedItem.setSoLuong(quantity);
	    selectedItem.setDonGia(newSelectedMedicine.getDonGia());

	    // Tính lại tổng tiền của đơn hàng
	    double tongTienMoi = 0;
	    for (ChiTietDatHang item : listCTDH) {
	        tongTienMoi += item.getThuoc().getDonGia() * item.getSoLuong(); // Tính tổng tiền mới
	    }

	    // Lấy thông tin của đơn hàng (idDH)

	    // Lấy đối tượng DatHang từ cơ sở dữ liệu để cập nhật lại tổng tiền
	    if (datHang != null) {
	        datHang.setTongTien(tongTienMoi); // Cập nhật lại tổng tiền
	        DH_CON.update(datHang); // Cập nhật đơn hàng trong cơ sở dữ liệu
	        JOptionPane.showMessageDialog(this, "Tổng tiền của đơn hàng đã được cập nhật.");
	    }

	    // Cập nhật lại bảng chi tiết đơn hàng
	    loadTableCTHD(listCTDH);
	    updateDanhSachThuocChuaThanhToan(listCTDH);
	    JOptionPane.showMessageDialog(this, "Thuốc đã được đổi thành công!");
	}




	private boolean isMedicineValid(Thuoc thuoc) {
		// Lấy ngày hiện tại
		LocalDate today = LocalDate.now();

		// Lấy ngày hết hạn từ đối tượng Thuoc (giả sử là java.sql.Date)
		Date expiryDate = (Date) thuoc.getHanSuDung(); // Giả sử ngày hết hạn là kiểu java.sql.Date

		// Chuyển java.sql.Date thành LocalDate
		LocalDate expiryLocalDate = expiryDate.toLocalDate();

		// Kiểm tra xem hạn sử dụng có còn hơn 1 tháng
		return expiryLocalDate.isAfter(today.plusMonths(1));
	}

	private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {
		DH_GUI.loadTable(DH_CON.getAllList());
		this.dispose();
	}

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = table.getSelectedRow();
		byte[] thuocImage = listCTDH.get(row).getThuoc().getHinhAnh();
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(thuocImage).getImage()
				.getScaledInstance(txtHinhAnh.getWidth(), txtHinhAnh.getHeight(), Image.SCALE_SMOOTH));
		txtHinhAnh.setIcon(imageIcon);
	}

	private void btnPrintActionPerformed(java.awt.event.ActionEvent evt)
			throws MalformedURLException, WriterException, IOException {
//        DatHang hoaDon = listCTDH.get(0).getDatHang();
//        new WritePDF().printHoaDon(hoaDon, listCTDH);
	}

	private javax.swing.JButton btnDoiThuoc;
	private javax.swing.JButton btnTraThuoc;
	private javax.swing.JButton btnHuy;
	private javax.swing.JButton btnPrint;
	private javax.swing.JPanel hoaDonPanel;
	private javax.swing.JPanel imagePanel;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel16;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblThuoc;
	private javax.swing.JTable table;
	private javax.swing.JPanel tableItemPanel;
	private javax.swing.JLabel txtHinhAnh;
	private javax.swing.JTextField txtMaHD;
	private javax.swing.JTextField txtTenKH;
	private javax.swing.JTextField txtTenNV;
	private javax.swing.JTextField txtTong;
}

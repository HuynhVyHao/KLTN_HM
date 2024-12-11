package gui.dialog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.google.zxing.WriterException;

import controller.ChiTietDatHangController;
import controller.DatHangController;
import controller.ThuocController;
import entity.ChiTietDatHang;
import entity.DatHang;
import entity.Thuoc;
import gui.page.DatHangPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;

public class DetailDatHangDialog extends JDialog {

	private final ChiTietDatHangController CTDH_CON = new ChiTietDatHangController();
	private final DatHangController DH_CON= new DatHangController();
	private DatHangPage DH_GUI;
	private List<ChiTietDatHang> listCTDH;
	private final ThuocController THUOC_CON = new ThuocController();
	private List<Thuoc> listThuoc = THUOC_CON.getAllList();

	private DefaultTableModel modal;

	public DetailDatHangDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public DetailDatHangDialog(Frame parent, boolean modal, List<ChiTietDatHang> ctdh,DatHangPage DH_GUI) {
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

		jPanel15 = new JPanel();
		jLabel8 = new JLabel();
		jPanel2 = new JPanel();
		hoaDonPanel = new JPanel();
		jPanel7 = new JPanel();
		jLabel4 = new JLabel();
		txtMaHD = new JTextField();
		jPanel9 = new JPanel();
		jLabel5 = new JLabel();
		txtTenKH = new JTextField();
		jPanel11 = new JPanel();
		jLabel7 = new JLabel();
		txtTenNV = new JTextField();
		jPanel3 = new JPanel();
		imagePanel = new JPanel();
		txtHinhAnh = new JLabel();
		tableItemPanel = new JPanel();
		jScrollPane1 = new JScrollPane();
		table = new JTable();
		jPanel1 = new JPanel();
		jPanel12 = new JPanel();
		jLabel9 = new JLabel();
		txtTong = new JTextField();
		jPanel16 = new JPanel();
		lblThuoc = new JLabel();
		jPanel8 = new JPanel();
		btnHuy = new JButton();
		btnPrint = new JButton();
		btnDoiThuoc = new JButton();
		btnTraThuoc = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jPanel15.setBackground(new Color(0, 153, 153));
		jPanel15.setMinimumSize(new Dimension(100, 60));
		jPanel15.setPreferredSize(new Dimension(500, 50));
		jPanel15.setLayout(new BorderLayout());

		jLabel8.setFont(new Font("Roboto Medium", 0, 18));
		jLabel8.setForeground(new Color(255, 255, 255));
		jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel8.setText("CHI TIẾT ĐẶT HÀNG");
		jLabel8.setPreferredSize(new Dimension(149, 40));
		jPanel15.add(jLabel8, BorderLayout.CENTER);

		getContentPane().add(jPanel15, BorderLayout.NORTH);

		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel2.setLayout(new BorderLayout());

		hoaDonPanel.setBackground(new Color(255, 255, 255));
		hoaDonPanel.setPreferredSize(new Dimension(1200, 80));
		hoaDonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 16));

		jPanel7.setBackground(new Color(255, 255, 255));
		jPanel7.setPreferredSize(new Dimension(340, 40));
		jPanel7.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		jLabel4.setFont(new Font("Roboto", 0, 14));
		jLabel4.setText("Mã đặt hàng ");
		jLabel4.setPreferredSize(new Dimension(120, 40));
		jPanel7.add(jLabel4);

		txtMaHD.setEditable(false);
		txtMaHD.setFont(new Font("Roboto Mono", 1, 14));
		txtMaHD.setText("Z2NX8CN1A");
		txtMaHD.setFocusable(false);
		txtMaHD.setPreferredSize(new Dimension(200, 40));
		jPanel7.add(txtMaHD);

		hoaDonPanel.add(jPanel7);

		jPanel9.setBackground(new Color(255, 255, 255));
		jPanel9.setPreferredSize(new Dimension(340, 40));
		jPanel9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		jLabel5.setFont(new Font("Roboto", 0, 14));
		jLabel5.setText("Tên khách hàng");
		jLabel5.setPreferredSize(new Dimension(120, 40));
		jPanel9.add(jLabel5);

		txtTenKH.setEditable(false);
		txtTenKH.setFont(new Font("Roboto", 0, 14));
		txtTenKH.setText("Nguyễn Văn A");
		txtTenKH.setFocusable(false);
		txtTenKH.setPreferredSize(new Dimension(200, 40));
		jPanel9.add(txtTenKH);

		hoaDonPanel.add(jPanel9);

		jPanel11.setBackground(new Color(255, 255, 255));
		jPanel11.setPreferredSize(new Dimension(340, 40));
		jPanel11.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		jLabel7.setFont(new Font("Roboto", 0, 14));
		jLabel7.setText("Tên nhân viên");
		jLabel7.setPreferredSize(new Dimension(120, 40));
		jPanel11.add(jLabel7);

		txtTenNV.setEditable(false);
		txtTenNV.setFont(new Font("Roboto", 0, 14));
		txtTenNV.setText("Vũ Nương");
		txtTenNV.setFocusable(false);
		txtTenNV.setPreferredSize(new Dimension(200, 40));
		jPanel11.add(txtTenNV);

		hoaDonPanel.add(jPanel11);

		jPanel2.add(hoaDonPanel, BorderLayout.PAGE_START);

		jPanel3.setBackground(new Color(255, 255, 255));
		jPanel3.setPreferredSize(new Dimension(400, 100));

		imagePanel.setBackground(new Color(255, 255, 255));
		imagePanel.setBorder(new LineBorder(new Color(237, 237, 237), 2, true));
		imagePanel.setPreferredSize(new Dimension(300, 300));
		imagePanel.setLayout(new BorderLayout());

		txtHinhAnh.setBackground(new Color(255, 255, 255));
		txtHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
		txtHinhAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
		txtHinhAnh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		txtHinhAnh.setPreferredSize(new Dimension(200, 100));
		imagePanel.add(txtHinhAnh, BorderLayout.CENTER);

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
						.addContainerGap(26, Short.MAX_VALUE).addComponent(imagePanel,
								GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
						.addGap(24, 24, 24)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING,
						jPanel3Layout.createSequentialGroup().addContainerGap(84, Short.MAX_VALUE)
								.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(136, 136, 136)));

		jPanel2.add(jPanel3, BorderLayout.WEST);

		tableItemPanel.setLayout(new BorderLayout());

		jScrollPane1.setBorder(new LineBorder(new Color(240, 240, 240), 1, true));

		table.setFocusable(false);
		table.setRowHeight(40);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowHorizontalLines(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(table);

		tableItemPanel.add(jScrollPane1, BorderLayout.CENTER);

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setPreferredSize(new Dimension(800, 60));
		jPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT));

		jPanel12.setBackground(new Color(255, 255, 255));
		jPanel12.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		jLabel9.setFont(new Font("Roboto", 1, 14));
		jLabel9.setForeground(new Color(255, 51, 0));
		jLabel9.setText("Tổng hóa đơn:");
		jLabel9.setPreferredSize(new Dimension(120, 40));
		jPanel12.add(jLabel9);

		txtTong.setEditable(false);
		txtTong.setFont(new Font("Roboto Mono Medium", 0, 14));
		txtTong.setForeground(new Color(255, 51, 0));
		txtTong.setText("1000000");
		txtTong.setFocusable(false);
		txtTong.setPreferredSize(new Dimension(200, 40));
		jPanel12.add(txtTong);

		jPanel1.add(jPanel12);

		tableItemPanel.add(jPanel1, BorderLayout.PAGE_END);

		jPanel16.setBackground(new Color(0, 153, 153));
		jPanel16.setMinimumSize(new Dimension(100, 60));
		jPanel16.setPreferredSize(new Dimension(500, 30));
		jPanel16.setLayout(new BorderLayout());

		lblThuoc.setFont(new Font("Roboto Medium", 0, 14));
		lblThuoc.setForeground(new Color(255, 255, 255));
		lblThuoc.setHorizontalAlignment(SwingConstants.CENTER);
		lblThuoc.setText("Thông tin thuốc");
		jPanel16.add(lblThuoc, BorderLayout.CENTER);

		tableItemPanel.add(jPanel16, BorderLayout.NORTH);

		jPanel2.add(tableItemPanel, BorderLayout.CENTER);

		getContentPane().add(jPanel2, BorderLayout.CENTER);

		jPanel8.setBackground(new Color(255, 255, 255));
		jPanel8.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

		btnHuy.setBackground(new Color(255, 102, 102));
		btnHuy.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnHuy.setForeground(new Color(255, 255, 255));
		btnHuy.setText("HỦY BỎ");
		btnHuy.setBorderPainted(false);
		btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnHuy.setFocusPainted(false);
		btnHuy.setFocusable(false);
		btnHuy.setPreferredSize(new Dimension(200, 40));
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});
		jPanel8.add(btnHuy);

		btnPrint.setBackground(new Color(0, 153, 153));
		btnPrint.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnPrint.setForeground(new Color(255, 255, 255));
		btnPrint.setText("In hóa đơn");
		btnPrint.setBorderPainted(false);
		btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPrint.setFocusPainted(false);
		btnPrint.setFocusable(false);
		btnPrint.setPreferredSize(new Dimension(200, 40));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					btnPrintActionPerformed(evt);
				} catch (WriterException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		jPanel8.add(btnPrint);

		// Định dạng nút Đổi thuốc
		btnDoiThuoc.setBackground(new Color(102, 204, 255));
		btnDoiThuoc.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnDoiThuoc.setForeground(new Color(255, 255, 255));
		btnDoiThuoc.setText("ĐỔI THUỐC");
		btnDoiThuoc.setBorderPainted(false);
		btnDoiThuoc.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDoiThuoc.setFocusPainted(false);
		btnDoiThuoc.setFocusable(false);
		btnDoiThuoc.setPreferredSize(new Dimension(200, 40));
		btnDoiThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDoiThuocActionPerformed(evt);
			}
		});
		jPanel8.add(btnDoiThuoc);

		// Định dạng nút Trả thuốc
		btnTraThuoc.setBackground(new Color(45, 122, 247));
		btnTraThuoc.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnTraThuoc.setForeground(new Color(255, 255, 255));
		btnTraThuoc.setText("TRẢ THUỐC");
		btnTraThuoc.setBorderPainted(false);
		btnTraThuoc.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnTraThuoc.setFocusPainted(false);
		btnTraThuoc.setFocusable(false);
		btnTraThuoc.setPreferredSize(new Dimension(200, 40));
		btnTraThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnTraThuocActionPerformed(evt);
			}
		});
		jPanel8.add(btnTraThuoc);

		getContentPane().add(jPanel8, BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
	}

	private void btnTraThuocActionPerformed(ActionEvent evt) {
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


	private void btnDoiThuocActionPerformed(ActionEvent evt) {
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
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    JScrollPane jScrollPane1 = new JScrollPane(table);
	    jScrollPane1.setPreferredSize(new Dimension(800, 400));

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

	private void btnHuyActionPerformed(ActionEvent evt) {
		DH_GUI.loadTable(DH_CON.getAllList());
		this.dispose();
	}

	private void tableMouseClicked(MouseEvent evt) {
		int row = table.getSelectedRow();
		byte[] thuocImage = listCTDH.get(row).getThuoc().getHinhAnh();
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(thuocImage).getImage()
				.getScaledInstance(txtHinhAnh.getWidth(), txtHinhAnh.getHeight(), Image.SCALE_SMOOTH));
		txtHinhAnh.setIcon(imageIcon);
	}

	private void btnPrintActionPerformed(ActionEvent evt)
			throws MalformedURLException, WriterException, IOException {
//        DatHang hoaDon = listCTDH.get(0).getDatHang();
//        new WritePDF().printHoaDon(hoaDon, listCTDH);
	}

	private JButton btnDoiThuoc;
	private JButton btnTraThuoc;
	private JButton btnHuy;
	private JButton btnPrint;
	private JPanel hoaDonPanel;
	private JPanel imagePanel;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JPanel jPanel1;
	private JPanel jPanel11;
	private JPanel jPanel12;
	private JPanel jPanel15;
	private JPanel jPanel16;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel7;
	private JPanel jPanel8;
	private JPanel jPanel9;
	private JScrollPane jScrollPane1;
	private JLabel lblThuoc;
	private JTable table;
	private JPanel tableItemPanel;
	private JLabel txtHinhAnh;
	private JTextField txtMaHD;
	private JTextField txtTenKH;
	private JTextField txtTenNV;
	private JTextField txtTong;
}

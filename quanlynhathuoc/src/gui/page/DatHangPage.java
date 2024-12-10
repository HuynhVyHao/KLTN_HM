package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import controller.ChiTietDatHangController;
import controller.ChiTietHoaDonController;
import controller.DatHangController;
import controller.HoaDonController;
import controller.NhanVienController;
import dao.ChiTietDatHangDAO;
import dao.ChiTietHoaDonDAO;
import dao.DatHangDAO;
import dao.HoaDonDAO;
import entity.ChiTietDatHang;
import entity.ChiTietHoaDon;
import entity.DatHang;
import entity.HoaDon;
import entity.NhanVien;
import entity.TaiKhoan;
import gui.MainLayout;
import gui.dialog.DetailDatHangDialog;
import gui.dialog.DetailHoaDonDialog;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import utils.Formatter;
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;
import utils.Validation;
import utils.WritePDF;

public class DatHangPage extends javax.swing.JPanel {

	private final DatHangController DH_CON = new DatHangController();
	private List<DatHang> listDH = DH_CON.getAllList();

	private DefaultTableModel modal;
	private MainLayout main;

	public DatHangPage() {
		initComponents();
		headerLayout();
		tableLayout();
		fillCombobox();
	}

	public DatHangPage(MainLayout main) {
		this.main = main;
		initComponents();
		headerLayout();
		tableLayout();
		fillCombobox();
	}

	private void headerLayout() {
		List<JButton> listButton = new ArrayList<>();
		listButton.add(btnAdd);
		listButton.add(btnDelete);
		listButton.add(btnInfo);

		for (JButton item : listButton) {
			item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		}

	}

	private void tableLayout() {
		lblTable.setText("danh sách thông tin đặt hàng".toUpperCase());
		String[] header = new String[] { "STT", "Mã đặt hàng", "Thời gian", "Tên nhân viên", "Tên khách hàng",
				"Tổng hóa đơn", "Trạng thái", "Xác Nhận" };
		modal = new DefaultTableModel();
		modal.setColumnIdentifiers(header);
		table.setModel(modal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, centerRenderer);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);

		// Thiết lập renderer và editor cho cột "Xác Nhận" (cột thứ 7)
		table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox()));

		loadTable(listDH);
		sortTable();
	}

	private void sortTable() {
		table.setAutoCreateRowSorter(true);
		TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
	}

	public void loadTable(List<DatHang> list) {
		modal.setRowCount(0);
		listDH = list;
		int stt = 1;

		for (DatHang e : listDH) {
			modal.addRow(new Object[] { String.valueOf(stt), e.getId(), Formatter.FormatTime(e.getThoiGian()),
					e.getNhanVien().getHoTen(), e.getKhachHang().getHoTen(), Formatter.FormatVND(e.getTongTien()),
					e.getTrangThai(), "Thanh Toán" // Đây là cột "Xác Nhận" với button "Thanh Toán"
			});
			stt++;
		}
	}
	

	private void fillCombobox() {
		List<NhanVien> listNV = new NhanVienController().getAllList();
		cboxNhanVien.addItem("Tất cả");
		for (NhanVien e : listNV) {
			cboxNhanVien.addItem(e.getHoTen());
		}
	}

	private boolean isValidFilterFields() {
		if (Validation.isEmpty(txtFromPrice.getText().trim())) {
			return false;
		} else {
			try {
				double fromPrice = Double.parseDouble(txtFromPrice.getText());
				if (fromPrice < 0) {
					MessageDialog.warring(this, "Số tiền phải >= 0");
					txtFromPrice.setText("");
					txtFromPrice.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				MessageDialog.warring(this, "Số tiền phải là số!");
				txtFromPrice.setText("");
				txtFromPrice.requestFocus();
				return false;
			}
		}

		if (Validation.isEmpty(txtToPrice.getText().trim())) {
			return false;
		} else {
			try {
				double toPrice = Double.parseDouble(txtToPrice.getText());
				if (toPrice < 0) {
					MessageDialog.warring(this, "Số tiền phải >= 0");
					txtToPrice.setText("");
					txtToPrice.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				MessageDialog.warring(this, "Số tiền phải là số!");
				txtToPrice.setText("");
				txtToPrice.requestFocus();
				return false;
			}
		}

		return true;
	}

	private List<DatHang> getListFilter() {
		String tenNV = "";

		if (cboxNhanVien.getSelectedItem() != null) {
			tenNV = cboxNhanVien.getSelectedItem().toString();
		}

		double fromPrice = isValidFilterFields() ? Double.parseDouble(txtFromPrice.getText()) : 0;
		double toPrice = isValidFilterFields() ? Double.parseDouble(txtToPrice.getText()) : 0;

		return DH_CON.getFilterTable(tenNV, fromPrice, toPrice);
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		headerPanel = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		cboxSearch = new javax.swing.JComboBox<>();
		txtSearch = new javax.swing.JTextField();
		btnReload = new javax.swing.JButton();
		actionPanel = new javax.swing.JPanel();
		btnAdd = new javax.swing.JButton();
		btnDelete = new javax.swing.JButton();
		btnInfo = new javax.swing.JButton();
		btnExport = new javax.swing.JButton();
		tablePanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		table = new javax.swing.JTable();
		jPanel5 = new javax.swing.JPanel();
		lblTable = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jPanel8 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		cboxNhanVien = new javax.swing.JComboBox<>();
		jSeparator1 = new javax.swing.JSeparator();
		jPanel9 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		txtFromPrice = new javax.swing.JTextField();
		jPanel10 = new javax.swing.JPanel();
		jLabel5 = new javax.swing.JLabel();
		txtToPrice = new javax.swing.JTextField();

		setBackground(new java.awt.Color(230, 245, 245));
		setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 245, 245), 6, true));
		setMinimumSize(new java.awt.Dimension(1130, 800));
		setPreferredSize(new java.awt.Dimension(1130, 800));
		setLayout(new java.awt.BorderLayout(0, 10));

		headerPanel.setBackground(new java.awt.Color(255, 255, 255));
		headerPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(232, 232, 232), 2, true));
		headerPanel.setLayout(new java.awt.BorderLayout());

		actionPanel.setBackground(new java.awt.Color(255, 255, 255));
		actionPanel.setPreferredSize(new java.awt.Dimension(600, 100));
		actionPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 5));

		btnAdd.setFont(new java.awt.Font("Roboto", 1, 14));
		btnAdd.setIcon(new FlatSVGIcon("./icon/add.svg"));
		btnAdd.setText("THÊM");
		btnAdd.setBorder(null);
		btnAdd.setBorderPainted(false);
		btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnAdd.setFocusPainted(false);
		btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnAdd.setPreferredSize(new java.awt.Dimension(90, 90));
		btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddActionPerformed(evt);
			}
		});
		actionPanel.add(btnAdd);

		btnDelete.setFont(new java.awt.Font("Roboto", 1, 14));
		btnDelete.setIcon(new FlatSVGIcon("./icon/delete.svg"));
		btnDelete.setText("XÓA");
		btnDelete.setBorder(null);
		btnDelete.setBorderPainted(false);
		btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnDelete.setFocusPainted(false);
		btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnDelete.setPreferredSize(new java.awt.Dimension(90, 90));
		btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDeleteActionPerformed(evt);
			}
		});
		actionPanel.add(btnDelete);

		btnInfo.setFont(new java.awt.Font("Roboto", 1, 14));
		btnInfo.setIcon(new FlatSVGIcon("./icon/info.svg"));
		btnInfo.setText("INFO");
		btnInfo.setBorder(null);
		btnInfo.setBorderPainted(false);
		btnInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnInfo.setFocusPainted(false);
		btnInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnInfo.setPreferredSize(new java.awt.Dimension(90, 90));
		btnInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnInfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnInfoActionPerformed(evt);
			}
		});
		actionPanel.add(btnInfo);

		headerPanel.add(actionPanel, java.awt.BorderLayout.WEST);

		add(headerPanel, java.awt.BorderLayout.PAGE_START);

		tablePanel.setBackground(new java.awt.Color(243, 243, 243));
		tablePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 2, true));
		tablePanel.setLayout(new java.awt.BorderLayout(2, 0));

		table.setFocusable(false);
		table.setRowHeight(40);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setShowHorizontalLines(true);
		jScrollPane1.setViewportView(table);
		if (table.getColumnModel().getColumnCount() > 0) {
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
		}

		tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jPanel5.setBackground(new java.awt.Color(0, 153, 153));
		jPanel5.setMinimumSize(new java.awt.Dimension(100, 60));
		jPanel5.setPreferredSize(new java.awt.Dimension(500, 40));
		jPanel5.setLayout(new java.awt.BorderLayout());

		lblTable.setFont(new java.awt.Font("Roboto Medium", 0, 18));
		lblTable.setForeground(new java.awt.Color(255, 255, 255));
		lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblTable.setText("THÔNG TIN NHÂN VIÊN");
		jPanel5.add(lblTable, java.awt.BorderLayout.CENTER);

		tablePanel.add(jPanel5, java.awt.BorderLayout.NORTH);

		add(tablePanel, java.awt.BorderLayout.CENTER);
	}

	private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
		TaiKhoan tk = main.tk;
		CreateDatHangPage page = new CreateDatHangPage(main, tk);
		main.setPanel(page);
	}

	private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			int row = table.getSelectedRow();
			String id = table.getValueAt(row, 1).toString();

			if (MessageDialog.confirm(this, "Bạn có chắc chắn xóa dòng này?", "Xóa")) {
				new ChiTietDatHangController().deleteById(id);
				DH_CON.deleteById(id);
				MessageDialog.info(this, "Xóa thành công!");
				modal.removeRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
		}
	}

	private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
		JTableExporter.exportJTableToExcel(table);
	}

	private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
		modal.setRowCount(0);

		String search = txtSearch.getText().toLowerCase().trim();
		String searchType = cboxSearch.getSelectedItem().toString();
		List<DatHang> listsearch = DH_CON.getSearchTable(search, searchType);

		loadTable(listsearch);
	}

	private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
		txtSearch.setText("");
		txtFromPrice.setText("");
		txtToPrice.setText("");
		cboxSearch.setSelectedIndex(0);
		cboxNhanVien.setSelectedIndex(0);
		loadTable(listDH);
	}

	private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			int row = table.getSelectedRow();
			System.out.println("Selected row: " + row); // Kiểm tra xem row được chọn là gì
			if (row == -1) {
				throw new Exception("No row selected");
			}

			DatHang datHang = listDH.get(row);
			List<ChiTietDatHang> listCTDH = new ChiTietDatHangController().selectAllById(datHang.getId());
			System.out.println("Size of listCTHD: " + listCTDH.size());

			DetailDatHangDialog dialog = new DetailDatHangDialog(null, true, listCTDH, this);
			dialog.setVisible(true);
		} catch (Exception e) {
			MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
		}
	}

	private void cboxNhanVienActionPerformed(java.awt.event.ActionEvent evt) {
		modal.setRowCount(0);

		List<DatHang> listSearch = getListFilter();

		String tenDM = cboxNhanVien.getSelectedItem().toString();
		if (tenDM.equals("Tất cả")) {
			listSearch = DH_CON.getAllList();
		}

		loadTable(listSearch);
	}

	private void txtToPriceKeyReleased(java.awt.event.KeyEvent evt) {
		modal.setRowCount(0);
		List<DatHang> listSearch = getListFilter();
		loadTable(listSearch);
	}

	private void txtFromPriceKeyReleased(java.awt.event.KeyEvent evt) {
		modal.setRowCount(0);
		List<DatHang> listSearch = getListFilter();
		loadTable(listSearch);
	}

	private javax.swing.JPanel actionPanel;
	private javax.swing.JButton btnAdd;
	private javax.swing.JButton btnDelete;
	private javax.swing.JButton btnExport;
	private javax.swing.JButton btnInfo;
	private javax.swing.JButton btnReload;
	private javax.swing.JComboBox<String> cboxNhanVien;
	private javax.swing.JComboBox<String> cboxSearch;
	private javax.swing.JPanel headerPanel;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JLabel lblTable;
	private javax.swing.JTable table;
	private javax.swing.JPanel tablePanel;
	private javax.swing.JTextField txtFromPrice;
	private javax.swing.JTextField txtSearch;
	private javax.swing.JTextField txtToPrice;

	private void handleThanhToan(int row) {
		String idDH = (String) table.getValueAt(row, 1); // Cột "Mã đặt hàng" ở vị trí 1
		String trangThai = (String) table.getValueAt(row, 6); // Cột "Trạng Thái" ở vị trí 6

		if ("Chưa thanh toán".equalsIgnoreCase(trangThai)) {
			// Xử lý cập nhật trạng thái đơn hàng
			DatHangDAO datHangDAO = new DatHangDAO();
			DatHang datHang = datHangDAO.selectById(idDH);
			datHang.setTrangThai("Đã thanh toán");
			datHangDAO.update(datHang);

			// Tạo đối tượng HoaDon từ đơn đặt hàng
			HoaDon hoaDon = new HoaDon();
			hoaDon.setId(datHang.getId()); // ID hóa đơn có thể trùng với ID đơn đặt hàng
			hoaDon.setTongTien(datHang.getTongTien());
			hoaDon.setThoiGian(new Timestamp(new java.util.Date().getTime()));
			hoaDon.setKhachHang(datHang.getKhachHang());
			hoaDon.setNhanVien(datHang.getNhanVien());

			// Lấy danh sách chi tiết đơn đặt hàng
			ChiTietDatHangDAO ctdhDAO = new ChiTietDatHangDAO();
			List<ChiTietDatHang> listCTDH = ctdhDAO.selectAllById(idDH);

			// Tạo danh sách ChiTietHoaDon từ danh sách ChiTietDatHang
			List<ChiTietHoaDon> listCTHD = new ArrayList<>();
			for (ChiTietDatHang ctDH : listCTDH) {
				ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
				chiTietHoaDon.setThuoc(ctDH.getThuoc());
				chiTietHoaDon.setSoLuong(ctDH.getSoLuong());
				chiTietHoaDon.setDonGia(ctDH.getDonGia());
				chiTietHoaDon.setHoaDon(hoaDon); // Liên kết với hóa đơn
				listCTHD.add(chiTietHoaDon);
			}

			// Lưu hóa đơn và chi tiết hóa đơn vào cơ sở dữ liệu
			try {
				HoaDonDAO hoaDonDAO = new HoaDonDAO();
				hoaDonDAO.create(hoaDon); // Lưu hóa đơn
				ChiTietHoaDonDAO cthdDAO = new ChiTietHoaDonDAO();
				cthdDAO.create(listCTHD); // Lưu chi tiết hóa đơn
				JOptionPane.showMessageDialog(null, "Thanh toán thành công và hóa đơn đã được lưu!");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Lỗi khi lưu hóa đơn: " + e.getMessage());
				e.printStackTrace();
				return;
			}

			// Cập nhật trạng thái bảng hiển thị
			table.setValueAt("Đã thanh toán", row, 6);

			// Hỏi người dùng có muốn in hóa đơn không
			if (MessageDialog.confirm(this, "Bạn có muốn in hóa đơn không?", "In hóa đơn")) {
				try {
					new WritePDF().printHoaDon(hoaDon, listCTHD);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Lỗi khi in hóa đơn: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Đơn hàng này đã được thanh toán!");
		}
	}

	// ButtonRenderer hiển thị button trong cột "Xác Nhận"
	private class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setText("Thanh Toán");
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			String trangThai = (String) table.getValueAt(row, 6); // Cột "Trạng Thái"
			setEnabled(!"Đã thanh toán".equals(trangThai)); // Khóa button nếu trạng thái là "Đã thanh toán"
			return this;
		}
	}

	// ButtonEditor để xử lý sự kiện nhấn button trong cột "Xác Nhận"
	private class ButtonEditor extends DefaultCellEditor {
		private JButton button;
		private boolean isClicked;
		private int currentRow;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton("Thanh Toán");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			currentRow = row;
			isClicked = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isClicked) {
				handleThanhToan(currentRow); // Xử lý thanh toán cho hàng được chọn
			}
			isClicked = false;
			return "Thanh Toán";
		}

		@Override
		public boolean stopCellEditing() {
			isClicked = false;
			return super.stopCellEditing();
		}

		@Override
		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}
}

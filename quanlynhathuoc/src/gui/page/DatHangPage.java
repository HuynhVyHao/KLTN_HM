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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import utils.Formatter;
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;
import utils.Validation;
import utils.WritePDF;

public class DatHangPage extends JPanel {

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
		listButton.add(btnExport);

		for (JButton item : listButton) {
			item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		}
		txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
		txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("./icon/search.svg"));

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

	private void initComponents() {

		headerPanel = new JPanel();
		jPanel1 = new JPanel();
		jPanel3 = new JPanel();
		txtSearch = new JTextField();
		btnReload = new JButton();
		actionPanel = new JPanel();
		btnAdd = new JButton();
		btnDelete = new JButton();
		btnInfo = new JButton();
		btnExport = new JButton();
		tablePanel = new JPanel();
		jScrollPane1 = new JScrollPane();
		table = new JTable();
		jPanel5 = new JPanel();
		lblTable = new JLabel();
		cboxNhanVien = new JComboBox<>();

		setBackground(new Color(230, 245, 245));
		setBorder(new LineBorder(new Color(230, 245, 245), 6, true));
		setMinimumSize(new Dimension(1130, 800));
		setPreferredSize(new Dimension(1130, 800));
		setLayout(new BorderLayout(0, 10));

		headerPanel.setBackground(new Color(255, 255, 255));
		headerPanel.setBorder(new LineBorder(new Color(232, 232, 232), 2, true));
		headerPanel.setLayout(new BorderLayout());
		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setPreferredSize(new java.awt.Dimension(590, 100));
		jPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 16, 24));

		jPanel3.setBackground(new Color(255, 255, 255));
		jPanel3.setPreferredSize(new Dimension(370, 50));
		jPanel3.setLayout(new FlowLayout(FlowLayout.TRAILING));

		txtSearch.setToolTipText("Tìm kiếm");
		txtSearch.setPreferredSize(new Dimension(200, 40));
		txtSearch.setSelectionColor(new Color(230, 245, 245));
		txtSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				txtSearchKeyReleased(evt);
			}
		});
		jPanel3.add(txtSearch);

		btnReload.setIcon(new FlatSVGIcon("./icon/reload.svg"));
		btnReload.setToolTipText("Làm mới");
		btnReload.setBorder(null);
		btnReload.setBorderPainted(false);
		btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnReload.setFocusPainted(false);
		btnReload.setFocusable(false);
		btnReload.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReload.setPreferredSize(new Dimension(40, 40));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnReloadActionPerformed(evt);
			}
		});
		jPanel3.add(btnReload);

		jPanel1.add(jPanel3);

		headerPanel.add(jPanel1, BorderLayout.CENTER);

		actionPanel.setBackground(new Color(255, 255, 255));
		actionPanel.setPreferredSize(new Dimension(600, 100));
		actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 5));

		btnAdd.setFont(new Font("Roboto", 1, 14));
		btnAdd.setIcon(new FlatSVGIcon("./icon/add.svg"));
		btnAdd.setText("THÊM");
		btnAdd.setBorder(null);
		btnAdd.setBorderPainted(false);
		btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdd.setFocusPainted(false);
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setPreferredSize(new Dimension(90, 90));
		btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAddActionPerformed(evt);
			}
		});
		actionPanel.add(btnAdd);

		btnDelete.setFont(new Font("Roboto", 1, 14));
		btnDelete.setIcon(new FlatSVGIcon("./icon/delete.svg"));
		btnDelete.setText("XÓA");
		btnDelete.setBorder(null);
		btnDelete.setBorderPainted(false);
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDelete.setFocusPainted(false);
		btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDelete.setPreferredSize(new Dimension(90, 90));
		btnDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDeleteActionPerformed(evt);
			}
		});
		actionPanel.add(btnDelete);

		btnInfo.setFont(new Font("Roboto", 1, 14));
		btnInfo.setIcon(new FlatSVGIcon("./icon/info.svg"));
		btnInfo.setText("INFO");
		btnInfo.setBorder(null);
		btnInfo.setBorderPainted(false);
		btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnInfo.setFocusPainted(false);
		btnInfo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnInfo.setPreferredSize(new Dimension(90, 90));
		btnInfo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnInfoActionPerformed(evt);
			}
		});
		actionPanel.add(btnInfo);

		btnExport.setFont(new Font("Roboto", 1, 14));
		btnExport.setIcon(new FlatSVGIcon("./icon/export.svg"));
		btnExport.setText("EXPORT");
		btnExport.setBorder(null);
		btnExport.setBorderPainted(false);
		btnExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnExport.setFocusPainted(false);
		btnExport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExport.setPreferredSize(new Dimension(90, 90));
		btnExport.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnExportActionPerformed(evt);
			}
		});
		actionPanel.add(btnExport);

		headerPanel.add(actionPanel, BorderLayout.WEST);

		add(headerPanel, BorderLayout.PAGE_START);

		tablePanel.setBackground(new Color(243, 243, 243));
		tablePanel.setBorder(new LineBorder(new Color(230, 230, 230), 2, true));
		tablePanel.setLayout(new BorderLayout(2, 0));

		table.setFocusable(false);
		table.setRowHeight(40);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowHorizontalLines(true);
		jScrollPane1.setViewportView(table);
		if (table.getColumnModel().getColumnCount() > 0) {
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
		}

		tablePanel.add(jScrollPane1, BorderLayout.CENTER);

		jPanel5.setBackground(new Color(0, 153, 153));
		jPanel5.setMinimumSize(new Dimension(100, 60));
		jPanel5.setPreferredSize(new Dimension(500, 40));
		jPanel5.setLayout(new BorderLayout());

		lblTable.setFont(new Font("Roboto Medium", 0, 18));
		lblTable.setForeground(new Color(255, 255, 255));
		lblTable.setHorizontalAlignment(SwingConstants.CENTER);
		jPanel5.add(lblTable, BorderLayout.CENTER);

		tablePanel.add(jPanel5, BorderLayout.NORTH);

		add(tablePanel, BorderLayout.CENTER);
	}

	private void btnAddActionPerformed(ActionEvent evt) {
		TaiKhoan tk = main.tk;
		CreateDatHangPage page = new CreateDatHangPage(main, tk);
		main.setPanel(page);
	}

	private void btnDeleteActionPerformed(ActionEvent evt) {
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

	private void btnExportActionPerformed(ActionEvent evt) {
		JTableExporter.exportJTableToExcel(table);
	}

	private void txtSearchKeyReleased(KeyEvent evt) {
		modal.setRowCount(0);

		String search = txtSearch.getText().toLowerCase().trim();
		List<DatHang> listsearch = DH_CON.getSearchTable(search);

		loadTable(listsearch);
	}

	private void btnReloadActionPerformed(ActionEvent evt) {
		txtSearch.setText("");
		loadTable(listDH);
	}

	private void btnInfoActionPerformed(ActionEvent evt) {
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

	private JPanel actionPanel;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnInfo;
	private JButton btnReload;
	private JComboBox<String> cboxNhanVien;
	private JPanel headerPanel;
	private JButton btnExport;
	private JPanel jPanel1;
	private JPanel jPanel3;
	private JPanel jPanel5;
	private JScrollPane jScrollPane1;
	private JLabel lblTable;
	private JTable table;
	private JPanel tablePanel;
	private JTextField txtSearch;

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

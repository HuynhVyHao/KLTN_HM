package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.DanhMucController;
import controller.DonViTinhController;
import controller.ThuocController;
import controller.XuatXuController;
import entity.DanhMuc;
import entity.DonViTinh;
import entity.Thuoc;
import entity.XuatXu;
import gui.MainLayout;
import gui.dialog.CreateThuocDialog;
import gui.dialog.DetailThuocDialog;
import gui.dialog.ThuocTinhDonViTinhDialog;
import gui.dialog.ThuocTinhXuatXuDialog;
import gui.dialog.UpdateThuocDialog;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;
import utils.Validation;

public class TimKiemThuocPage extends javax.swing.JPanel {
	private final ThuocController THUOC_CON = new ThuocController(this);
	private List<Thuoc> listThuoc = THUOC_CON.getAllList();

	private final List<DonViTinh> listDVT = new DonViTinhController().getAllList();
	private final List<XuatXu> listXX = new XuatXuController().getAllList();
	private final List<DanhMuc> listDM = new DanhMucController().getAllList();

	private DefaultTableModel modal;

	private MainLayout main;

	public TimKiemThuocPage() {
		initComponents();
		headerLayout();
		tableLayout();
		fillCombobox();
	}

	public TimKiemThuocPage(MainLayout main) {
		this.main = main;
		initComponents();
		headerLayout();
		tableLayout();
		fillCombobox();
	}

	private void headerLayout() {
		List<JButton> listButton = new ArrayList<>();
		listButton.add(btnReload);
		listButton.add(btnThuocTinh);
		listButton.add(btnSubmitHSD);

		// Border radius
		for (JButton item : listButton) {
			item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		}

		txtHSD.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập số ngày...");
		txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
		txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("./icon/search.svg"));

		String[] searchType = { "Tất cả", "Mã", "Tên" };
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
		cboxSearch.setModel(model);
	}

	private void tableLayout() {
		lblTable.setText("danh sách thông tin thuốc".toUpperCase());
		String[] header = new String[] { "STT", "Mã thuốc", "Tên thuốc", "Danh mục", "Xuất xứ", "Đơn vị tính",
				"Số lượng", "Đơn giá", "Hạn sử dụng" };
		modal = new DefaultTableModel();
		modal.setColumnIdentifiers(header);
		table.setModel(modal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, centerRenderer);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);

		loadTable(listThuoc);
		sortTable();
	}

	private void sortTable() {
		table.setAutoCreateRowSorter(true);
		TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
	}

	public void loadTable(List<Thuoc> list) {
		modal.setRowCount(0);

		listThuoc = list;
		int stt = 1;
		for (Thuoc e : listThuoc) {
			modal.addRow(new Object[] { String.valueOf(stt), e.getId(), e.getTenThuoc(), e.getDanhMuc().getTen(),
					e.getXuatXu().getTen(), e.getDonViTinh().getTen(), e.getSoLuongTon(),
					Formatter.FormatVND(e.getDonGia()), Formatter.FormatDate(e.getHanSuDung()) });
			stt++;
		}
	}

	private void fillCombobox() {
		cboxDonViTinh.addItem("Tất cả");
		for (DonViTinh e : listDVT) {
			cboxDonViTinh.addItem(e.getTen());
		}

		cboxXuatXu.addItem("Tất cả");
		for (XuatXu e : listXX) {
			cboxXuatXu.addItem(e.getTen());
		}

		cboxDanhMuc.addItem("Tất cả");
		for (DanhMuc e : listDM) {
			cboxDanhMuc.addItem(e.getTen());
		}
	}

	private List<Thuoc> getListFilter() {
		String tenDM = "";
		String tenDVT = "";
		String tenXX = "";
		long hanSuDung = 0;

		// Check if selected item is not null before converting to string
		if (cboxDanhMuc.getSelectedItem() != null) {
			tenDM = cboxDanhMuc.getSelectedItem().toString();
		}
		if (cboxDonViTinh.getSelectedItem() != null) {
			tenDVT = cboxDonViTinh.getSelectedItem().toString();
		}
		if (cboxXuatXu.getSelectedItem() != null) {
			tenXX = cboxXuatXu.getSelectedItem().toString();
		}

		if (!Validation.isEmpty(txtHSD.getText()) || Validation.isNumber(txtHSD.getText())) {
			hanSuDung = Long.parseLong(txtHSD.getText());
		}

		return THUOC_CON.getFilterTable(tenDM, tenDVT, tenXX, hanSuDung);
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		headerPanel = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		cboxSearch = new javax.swing.JComboBox<>();
		txtSearch = new javax.swing.JTextField();
		btnReload = new javax.swing.JButton();
		btnThuocTinh = new javax.swing.JButton();
		tablePanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		table = new javax.swing.JTable();
		jPanel5 = new javax.swing.JPanel();
		lblTable = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jPanel8 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		cboxDanhMuc = new javax.swing.JComboBox<>();
		jPanel6 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		cboxXuatXu = new javax.swing.JComboBox<>();
		jPanel7 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		cboxDonViTinh = new javax.swing.JComboBox<>();
		jPanel9 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		txtHSD = new javax.swing.JTextField();
		btnSubmitHSD = new javax.swing.JButton();

		setBackground(new java.awt.Color(230, 245, 245));
		setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 245, 245), 6, true));
		setMinimumSize(new java.awt.Dimension(1130, 800));
		setPreferredSize(new java.awt.Dimension(1130, 800));
		setLayout(new java.awt.BorderLayout(0, 10));

		headerPanel.setBackground(new java.awt.Color(255, 255, 255));
		headerPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(232, 232, 232), 2, true));
		headerPanel.setLayout(new java.awt.BorderLayout());

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setPreferredSize(new java.awt.Dimension(590, 100));
		jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 16, 24));

		jPanel3.setBackground(new java.awt.Color(255, 255, 255));
		jPanel3.setPreferredSize(new java.awt.Dimension(370, 50));
		jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

		cboxSearch.setToolTipText("");
		cboxSearch.setPreferredSize(new java.awt.Dimension(100, 40));
		jPanel3.add(cboxSearch);

		txtSearch.setToolTipText("Tìm kiếm");
		txtSearch.setPreferredSize(new java.awt.Dimension(200, 40));
		txtSearch.setSelectionColor(new java.awt.Color(230, 245, 245));
		txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtSearchKeyReleased(evt);
			}
		});
		jPanel3.add(txtSearch);

		btnReload.setIcon(new FlatSVGIcon("./icon/reload.svg"));
		btnReload.setToolTipText("Làm mới");
		btnReload.setBorder(null);
		btnReload.setBorderPainted(false);
		btnReload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnReload.setFocusPainted(false);
		btnReload.setFocusable(false);
		btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnReload.setPreferredSize(new java.awt.Dimension(40, 40));
		btnReload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnReloadActionPerformed(evt);
			}
		});
		jPanel3.add(btnReload);

		jPanel1.add(jPanel3);

		headerPanel.add(jPanel1, java.awt.BorderLayout.CENTER);


		add(headerPanel, java.awt.BorderLayout.PAGE_START);

		tablePanel.setBackground(new java.awt.Color(243, 243, 243));
		tablePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 2, true));
		tablePanel.setLayout(new java.awt.BorderLayout(2, 0));

		table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { "123", "Anh Tuấn", "123123", null, null, null },
						{ "13124", "czczxc", "zxc", null, null, null }, { "14123", "zxczc", "zxc", null, null, null },
						{ "124123", "zxczx", "zxc", null, null, null } },
				new String[] { "Mã", "Họ tên", "Số điện thoại", "Giới tính", "Năm sinh", "Ngày vào làm" }) {
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class, java.lang.Integer.class, java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
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

		lblTable.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
		lblTable.setForeground(new java.awt.Color(255, 255, 255));
		lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblTable.setText("THÔNG TIN NHÂN VIÊN");
		jPanel5.add(lblTable, java.awt.BorderLayout.CENTER);

		tablePanel.add(jPanel5, java.awt.BorderLayout.NORTH);

		jPanel4.setBackground(new java.awt.Color(255, 255, 255));
		jPanel4.setPreferredSize(new java.awt.Dimension(200, 100));
		jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 8));

		jPanel8.setBackground(new java.awt.Color(255, 255, 255));
		jPanel8.setPreferredSize(new java.awt.Dimension(200, 80));
		jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 16, 8));

		jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
		jLabel3.setText("Danh mục thuốc");
		jLabel3.setPreferredSize(new java.awt.Dimension(140, 20));
		jPanel8.add(jLabel3);

		cboxDanhMuc.setToolTipText("");
		cboxDanhMuc.setPreferredSize(new java.awt.Dimension(170, 40));
		cboxDanhMuc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboxDanhMucActionPerformed(evt);
			}
		});
		jPanel8.add(cboxDanhMuc);

		jPanel4.add(jPanel8);

		jPanel6.setBackground(new java.awt.Color(255, 255, 255));
		jPanel6.setPreferredSize(new java.awt.Dimension(200, 80));
		jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 16, 8));

		jLabel1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
		jLabel1.setText("Xuất xứ");
		jLabel1.setPreferredSize(new java.awt.Dimension(140, 20));
		jPanel6.add(jLabel1);

		cboxXuatXu.setToolTipText("");
		cboxXuatXu.setPreferredSize(new java.awt.Dimension(170, 40));
		cboxXuatXu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboxXuatXuActionPerformed(evt);
			}
		});
		jPanel6.add(cboxXuatXu);

		jPanel4.add(jPanel6);

		jPanel7.setBackground(new java.awt.Color(255, 255, 255));
		jPanel7.setPreferredSize(new java.awt.Dimension(200, 80));
		jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 16, 8));

		jLabel2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
		jLabel2.setText("Đơn vị tính");
		jLabel2.setPreferredSize(new java.awt.Dimension(140, 20));
		jPanel7.add(jLabel2);

		cboxDonViTinh.setToolTipText("");
		cboxDonViTinh.setPreferredSize(new java.awt.Dimension(170, 40));
		cboxDonViTinh.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboxDonViTinhActionPerformed(evt);
			}
		});
		jPanel7.add(cboxDonViTinh);

		jPanel4.add(jPanel7);

		jPanel9.setBackground(new java.awt.Color(255, 255, 255));
		jPanel9.setPreferredSize(new java.awt.Dimension(200, 80));
		jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 16, 8));

		jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
		jLabel4.setText("Hạn sử dụng còn");
		jLabel4.setPreferredSize(new java.awt.Dimension(140, 20));
		jPanel9.add(jLabel4);

		jPanel2.setBackground(new java.awt.Color(255, 255, 255));
		jPanel2.setMinimumSize(new java.awt.Dimension(170, 40));
		jPanel2.setPreferredSize(new java.awt.Dimension(170, 40));
		jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

		txtHSD.setPreferredSize(new java.awt.Dimension(120, 40));
		jPanel2.add(txtHSD);

		btnSubmitHSD.setIcon(new FlatSVGIcon("./icon/submit.svg"));
		btnSubmitHSD.setBorder(null);
		btnSubmitHSD.setBorderPainted(false);
		btnSubmitHSD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnSubmitHSD.setFocusPainted(false);
		btnSubmitHSD.setOpaque(false);
		btnSubmitHSD.setPreferredSize(new java.awt.Dimension(40, 40));
		btnSubmitHSD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSubmitHSDActionPerformed(evt);
			}
		});
		jPanel2.add(btnSubmitHSD);

		jPanel9.add(jPanel2);

		jPanel4.add(jPanel9);

		tablePanel.add(jPanel4, java.awt.BorderLayout.LINE_START);

		add(tablePanel, java.awt.BorderLayout.CENTER);
	}


	private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtSearchKeyReleased
		modal.setRowCount(0);

		String search = txtSearch.getText().toLowerCase().trim();
		String searchType = cboxSearch.getSelectedItem().toString();
		List<Thuoc> listsearch = THUOC_CON.getSearchTable(search, searchType);

		loadTable(listsearch);
	}// GEN-LAST:event_txtSearchKeyReleased

	private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnReloadActionPerformed
		txtSearch.setText("");
		txtHSD.setText("");
		cboxSearch.setSelectedIndex(0);
		cboxDanhMuc.setSelectedIndex(0);
		cboxDonViTinh.setSelectedIndex(0);
		cboxXuatXu.setSelectedIndex(0);
		loadTable(listThuoc);
	}// GEN-LAST:event_btnReloadActionPerformed

	private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInfoActionPerformed
		try {
			int row = table.getSelectedRow();
			String id = table.getValueAt(row, 1).toString();
			Thuoc thuoc = THUOC_CON.selectById(id);

			DetailThuocDialog dialog = new DetailThuocDialog(null, true, thuoc);
			dialog.setVisible(true);
		} catch (Exception e) {
			MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
		}
	}// GEN-LAST:event_btnInfoActionPerformed

	private void cboxXuatXuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxXuatXuActionPerformed
		modal.setRowCount(0);

		List<Thuoc> listSearch = getListFilter();

		String tenXX = cboxXuatXu.getSelectedItem().toString();
		if (tenXX.equals("Tất cả")) {
			listSearch = THUOC_CON.getAllList();
		}

		loadTable(listSearch);
	}// GEN-LAST:event_cboxXuatXuActionPerformed

	private void cboxDonViTinhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxDonViTinhActionPerformed
		modal.setRowCount(0);

		List<Thuoc> listSearch = getListFilter();

		String tenDVT = cboxDonViTinh.getSelectedItem().toString();
		if (tenDVT.equals("Tất cả")) {
			listSearch = THUOC_CON.getAllList();
		}

		loadTable(listSearch);
	}// GEN-LAST:event_cboxDonViTinhActionPerformed

	private void cboxDanhMucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboxDanhMucActionPerformed
		modal.setRowCount(0);

		List<Thuoc> listSearch = getListFilter();

		String tenDM = cboxDanhMuc.getSelectedItem().toString();
		if (tenDM.equals("Tất cả")) {
			listSearch = THUOC_CON.getAllList();
		}

		loadTable(listSearch);
	}// GEN-LAST:event_cboxDanhMucActionPerformed

	private void btnDonViActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThuocTinhActionPerformed
		ThuocTinhDonViTinhDialog dialog = new ThuocTinhDonViTinhDialog(null, true);
		dialog.setVisible(true);
	}// GEN-LAST:event_btnThuocTinhActionPerformed

	private void btnXuatXuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThuocTinhActionPerformed
		ThuocTinhXuatXuDialog dialog = new ThuocTinhXuatXuDialog(null, true);
		dialog.setVisible(true);
	}// GEN-LAST:event_btnThuocTinhActionPerformed

	private void btnSubmitHSDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSubmitHSDActionPerformed
		modal.setRowCount(0);

		List<Thuoc> listSearch = getListFilter();

		loadTable(listSearch);
	}// GEN-LAST:event_btnSubmitHSDActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnReload;
	private javax.swing.JButton btnSubmitHSD;
	private javax.swing.JButton btnThuocTinh;
	private javax.swing.JComboBox<String> cboxDanhMuc;
	private javax.swing.JComboBox<String> cboxDonViTinh;
	private javax.swing.JComboBox<String> cboxSearch;
	private javax.swing.JComboBox<String> cboxXuatXu;
	private javax.swing.JPanel headerPanel;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblTable;
	private javax.swing.JTable table;
	private javax.swing.JPanel tablePanel;
	private javax.swing.JTextField txtHSD;
	private javax.swing.JTextField txtSearch;
}


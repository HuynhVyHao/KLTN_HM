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

public class BaoCaoThuocPage extends javax.swing.JPanel {
	private final ThuocController THUOC_CON = new ThuocController(this);
	private List<Thuoc> listThuoc = THUOC_CON.getAllList();

	private final List<DonViTinh> listDVT = new DonViTinhController().getAllList();
	private final List<XuatXu> listXX = new XuatXuController().getAllList();
	private final List<DanhMuc> listDM = new DanhMucController().getAllList();

	private DefaultTableModel modal;

	private MainLayout main;

	public BaoCaoThuocPage() {
		initComponents();
		headerLayout();
		tableLayout();
	}

	public BaoCaoThuocPage(MainLayout main) {
		this.main = main;
		initComponents();
		headerLayout();
		tableLayout();
	}

	private void headerLayout() {
		List<JButton> listButton = new ArrayList<>();
		listButton.add(btnImport);
		listButton.add(btnExport);

		// Border radius
		for (JButton item : listButton) {
			item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		}
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



	@SuppressWarnings("unchecked")
	private void initComponents() {

		headerPanel = new javax.swing.JPanel();
		actionPanel = new javax.swing.JPanel();
		btnImport = new javax.swing.JButton();
		btnExport = new javax.swing.JButton();
		tablePanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		table = new javax.swing.JTable();
		jPanel5 = new javax.swing.JPanel();
		lblTable = new javax.swing.JLabel();

		setBackground(new java.awt.Color(230, 245, 245));
		setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 245, 245), 6, true));
		setMinimumSize(new java.awt.Dimension(1130, 800));
		setPreferredSize(new java.awt.Dimension(1130, 800));
		setLayout(new java.awt.BorderLayout(0, 10));

		headerPanel.setBackground(new java.awt.Color(255, 255, 255));
		headerPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(232, 232, 232), 2, true));
		headerPanel.setLayout(new java.awt.BorderLayout());

		actionPanel.setBackground(new java.awt.Color(255, 255, 255));
		actionPanel.setPreferredSize(new java.awt.Dimension(700, 100));
		actionPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 5));

		btnImport.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
		btnImport.setIcon(new FlatSVGIcon("./icon/import.svg"));
		btnImport.setText("IMPORT");
		btnImport.setBorder(null);
		btnImport.setBorderPainted(false);
		btnImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnImport.setFocusPainted(false);
		btnImport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnImport.setPreferredSize(new java.awt.Dimension(90, 90));
		btnImport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnImport.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnImportActionPerformed(evt);
			}
		});
		actionPanel.add(btnImport);

		btnExport.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
		btnExport.setIcon(new FlatSVGIcon("./icon/export.svg"));
		btnExport.setText("EXPORT");
		btnExport.setBorder(null);
		btnExport.setBorderPainted(false);
		btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnExport.setFocusPainted(false);
		btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnExport.setPreferredSize(new java.awt.Dimension(90, 90));
		btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnExport.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnExportActionPerformed(evt);
			}
		});
		actionPanel.add(btnExport);

		headerPanel.add(actionPanel, java.awt.BorderLayout.WEST);

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

		add(tablePanel, java.awt.BorderLayout.CENTER);
	}


	private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnImportActionPerformed
		THUOC_CON.importExcel();
	}// GEN-LAST:event_btnImportActionPerformed

	private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExportActionPerformed
		JTableExporter.exportJTableToExcel(table);
	}// GEN-LAST:event_btnExportActionPerformed








	private javax.swing.JPanel actionPanel;
	private javax.swing.JButton btnExport;
	private javax.swing.JButton btnImport;
	private javax.swing.JPanel headerPanel;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblTable;
	private javax.swing.JTable table;
	private javax.swing.JPanel tablePanel;
}
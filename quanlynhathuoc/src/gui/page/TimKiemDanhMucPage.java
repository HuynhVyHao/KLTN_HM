package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.DanhMucController;
import entity.DanhMuc;
import gui.MainLayout;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.TableSorter;

public class TimKiemDanhMucPage extends javax.swing.JPanel {

	private DanhMucController DM_CON = new DanhMucController(this);
	private DefaultTableModel modal;
	private MainLayout main;

	public TimKiemDanhMucPage() {
		initComponents();
		searchLayout();
		tableLayout();
	}

	public TimKiemDanhMucPage(MainLayout main) {
		this.main = main;
		initComponents();
		searchLayout();
		tableLayout();
	}

	private void searchLayout() {
		btnReload.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");

		String[] searchType = { "Tất cả", "Mã", "Tên" };
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
		cboxSearch.setModel(model);
	}

	private void tableLayout() {
		String[] header = new String[] { "STT", "Mã danh mục thuốc", "Tên danh mục thuốc" };

		DefaultTableModel modal = new DefaultTableModel();
		modal.setColumnIdentifiers(header);
		table.setModel(modal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, centerRenderer);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);

		loadTable();
		sortTable();
	}

	private void sortTable() {
		table.setAutoCreateRowSorter(true);
		TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
	}

	public void loadTable() {
		DefaultTableModel modal = (DefaultTableModel) table.getModel();
		modal.setRowCount(0);

		List<DanhMuc> list = DM_CON.getAllList();
		int stt = 1;
		for (DanhMuc e : list) {
			modal.addRow(new Object[] { String.valueOf(stt), e.getId(), e.getTen() });
			stt++;
		}
	}

	private boolean isValidateFields() {
		if (txtTenDVT.getText().trim().equals("")) {
			MessageDialog.warring(this, "Tên danh mục không được rỗng!");
			txtTenDVT.requestFocus();
			return false;
		}

		return true;
	}

	private DanhMuc getInputFields() {
		String id = RandomGenerator.getRandomId();
		String ten = txtTenDVT.getText().trim();

		return new DanhMuc(id, ten);
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		headerPanel = new javax.swing.JPanel();
		jPanel15 = new javax.swing.JPanel();
		lblTable = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
		cboxSearch = new javax.swing.JComboBox<>();
		txtSearch = new javax.swing.JTextField();
		btnReload = new javax.swing.JButton();
		tablePanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		table = new javax.swing.JTable();
		txtTenDVT = new javax.swing.JTextField();

		// Thiết lập thuộc tính cho JPanel
		setBackground(new java.awt.Color(230, 245, 245));
		setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 245, 245), 6, true));
		setMinimumSize(new java.awt.Dimension(1130, 800));
		setPreferredSize(new java.awt.Dimension(1130, 800));
		setLayout(new java.awt.BorderLayout(0, 10));

		// Header Panel
		headerPanel.setBackground(new java.awt.Color(255, 255, 255));
		headerPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(232, 232, 232), 2, true));
		headerPanel.setLayout(new java.awt.BorderLayout());

		// Search Panel

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
		btnReload.setPreferredSize(new java.awt.Dimension(40, 40));
		btnReload.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnReloadActionPerformed(evt);
			}
		});
		jPanel3.add(btnReload);

		jPanel1.add(jPanel3);

		headerPanel.add(jPanel1, java.awt.BorderLayout.CENTER); // Đặt phần tìm kiếm vào góc phải của headerPanel


		add(headerPanel, java.awt.BorderLayout.PAGE_START); // Thêm headerPanel vào layout chính

		// Main Content Panel

		tablePanel.setBackground(new java.awt.Color(243, 243, 243));
		tablePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 2, true));
		tablePanel.setLayout(new java.awt.BorderLayout(2, 0));
		// Table Panel
		table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { "123", "Anh Tuấn", "123123", null, null, null },
						{ "13124", "czczxc", "zxc", null, null, null }, { "14123", "zxczc", "zxc", null, null, null },
						{ "124123", "zxczx", "zxc", null, null, null } },
				new String[] { "Mã", "Họ tên", "Số điện thoại", "Giới tính", "Năm sinh", "Ngày vào làm" }) {
			Class[] types = new Class[] { String.class, String.class, String.class, String.class, Integer.class,
					String.class };
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
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});

		tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

		jPanel15.setBackground(new java.awt.Color(0, 153, 153));
		jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
		jPanel15.setPreferredSize(new java.awt.Dimension(500, 40));
		jPanel15.setLayout(new java.awt.BorderLayout());

		lblTable.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
		lblTable.setForeground(new java.awt.Color(255, 255, 255));
		lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblTable.setText("DANH MỤC THUỐC");
		jPanel15.add(lblTable, java.awt.BorderLayout.CENTER);
		// Tạo panel cho tên ĐVT
		tablePanel.add(jPanel15, BorderLayout.NORTH); // Thêm panel vào phía trên tablePanel
		tablePanel.add(jScrollPane1, BorderLayout.CENTER); // Thê

		add(tablePanel, java.awt.BorderLayout.CENTER); // Thêm bảng vào giao diện chính
	}



	private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtSearchKeyReleased
		DefaultTableModel modal = (DefaultTableModel) table.getModel();
		modal.setRowCount(0);

		String search = txtSearch.getText().toLowerCase().trim();
		String searchType = cboxSearch.getSelectedItem().toString();
		List<DanhMuc> listsearch = DM_CON.getSearchTable(search, searchType);

		int stt = 1;
		for (DanhMuc e : listsearch) {
			modal.addRow(new Object[] { String.valueOf(stt), e.getId(), e.getTen() });
			stt++;
		}
	}


	private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
		txtTenDVT.setText("");
		txtSearch.setText("");
		cboxSearch.setSelectedIndex(0);
		loadTable();
	}

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = table.getSelectedRow();
		txtTenDVT.setText(table.getValueAt(row, 2).toString());
	}

	private javax.swing.JButton btnReload;
	private javax.swing.JComboBox<String> cboxSearch;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel headerPanel;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel18;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblTenDVT;
	private javax.swing.JLabel lblTable;
	private javax.swing.JPanel tablePanel;
	private javax.swing.JTable table;
	private javax.swing.JTextField txtSearch;
	private javax.swing.JTextField txtTenDVT;
}

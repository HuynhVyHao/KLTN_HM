package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.DanhMucController;
import entity.DanhMuc;
import gui.MainLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.TableSorter;

public class DanhMucPage extends JPanel {

	private DanhMucController DM_CON = new DanhMucController(this);
	private DefaultTableModel modal;
	private MainLayout main;

	public DanhMucPage() {
		initComponents();
		searchLayout();
		tableLayout();
	}

	public DanhMucPage(MainLayout main) {
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

		modal = new DefaultTableModel();
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

	private void initComponents() {
		headerPanel = new JPanel();
		jPanel15 = new JPanel();
		lblTable = new JLabel();
		jPanel1 = new JPanel();
		jPanel3 = new JPanel();
		cboxSearch = new JComboBox<>();
		txtSearch = new JTextField();
		btnReload = new JButton();
		actionPanel = new JPanel();
		btnAdd = new JButton();
		tablePanel = new JPanel();
		btnUpdate = new JButton();
		btnRemove = new JButton();
		jScrollPane1 = new JScrollPane();
		table = new JTable();
		txtTenDVT = new JTextField();

		// Thiết lập thuộc tính cho JPanel
		setBackground(new Color(230, 245, 245));
		setBorder(new LineBorder(new Color(230, 245, 245), 6, true));
		setMinimumSize(new Dimension(1130, 800));
		setPreferredSize(new Dimension(1130, 800));
		setLayout(new BorderLayout(0, 10));

		// Header Panel
		headerPanel.setBackground(new Color(255, 255, 255));
		headerPanel.setBorder(new LineBorder(new Color(232, 232, 232), 2, true));
		headerPanel.setLayout(new BorderLayout());
		
		// Search Panel
				jPanel1.setBackground(new Color(255, 255, 255));
				jPanel1.setPreferredSize(new Dimension(590, 100));
				jPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 16, 24));

				jPanel3.setBackground(new Color(255, 255, 255));
				jPanel3.setPreferredSize(new Dimension(370, 50));
				jPanel3.setLayout(new FlowLayout(FlowLayout.TRAILING));

				cboxSearch.setToolTipText("");
				cboxSearch.setPreferredSize(new Dimension(100, 40));
				jPanel3.add(cboxSearch);

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
				btnReload.setPreferredSize(new Dimension(40, 40));
				btnReload.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnReloadActionPerformed(evt);
					}
				});
				jPanel3.add(btnReload);

				jPanel1.add(jPanel3);

				headerPanel.add(jPanel1, BorderLayout.CENTER); // Đặt phần tìm kiếm vào góc phải của headerPanel


				add(headerPanel, BorderLayout.PAGE_START); // Thêm header
		// Action Panel
		actionPanel.setBackground(new Color(255, 255, 255));
		actionPanel.setPreferredSize(new Dimension(700, 100));
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

		btnUpdate.setFont(new Font("Roboto", 1, 14)); 
		btnUpdate.setIcon(new FlatSVGIcon("./icon/update.svg"));
		btnUpdate.setText("SỬA");
		btnUpdate.setBorder(null);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnUpdate.setFocusPainted(false);
		btnUpdate.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUpdate.setPreferredSize(new Dimension(90, 90));
		btnUpdate.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnUpdateActionPerformed(evt);
			}
		});
		actionPanel.add(btnUpdate);

		btnRemove.setFont(new Font("Roboto", 1, 14)); 
		btnRemove.setIcon(new FlatSVGIcon("./icon/delete.svg"));
		btnRemove.setText("XÓA");
		btnRemove.setBorder(null);
		btnRemove.setBorderPainted(false);
		btnRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRemove.setFocusPainted(false);
		btnRemove.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRemove.setPreferredSize(new Dimension(90, 90));
		btnRemove.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnRemoveActionPerformed(evt);
			}
		});
		actionPanel.add(btnRemove);

		headerPanel.add(actionPanel, BorderLayout.WEST); // Đặt actionPanel bên trái headerPanel

		add(headerPanel, BorderLayout.PAGE_START); // Thêm headerPanel vào layout chính

		// Main Content Panel
		tablePanel.setBackground(new Color(243, 243, 243));
		tablePanel.setBorder(new LineBorder(new Color(230, 230, 230), 2, true));
		tablePanel.setLayout(new BorderLayout(2, 0));
		
		table.setFocusable(false);
		table.setRowHeight(40);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowHorizontalLines(true);
		jScrollPane1.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});

		tablePanel.add(jScrollPane1, BorderLayout.CENTER);

		jPanel18 = new JPanel();
		jPanel15.setBackground(new Color(0, 153, 153));
		jPanel15.setMinimumSize(new Dimension(100, 60));
		jPanel15.setPreferredSize(new Dimension(500, 40));
		jPanel15.setLayout(new BorderLayout());

		lblTable.setFont(new Font("Roboto Medium", 0, 18)); 
		lblTable.setForeground(new Color(255, 255, 255));
		lblTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblTable.setText("DANH MỤC THUỐC");
		jPanel15.add(lblTable, BorderLayout.CENTER);
		tablePanel.add(jPanel15, BorderLayout.CENTER);
		// Tạo panel cho tên ĐVT
		jPanel18 = new JPanel();
		jPanel18.setBackground(new Color(243, 243, 243)); // Màu nền giống tablePanel
		jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		lblTenDVT = new JLabel();
		lblTenDVT.setFont(new Font("Roboto", 0, 14)); 
		lblTenDVT.setText("Tên danh mục thuốc");
		lblTenDVT.setMaximumSize(new Dimension(150, 40));
		lblTenDVT.setPreferredSize(new Dimension(150, 40));
		jPanel18.add(lblTenDVT);

		txtTenDVT = new JTextField();
		txtTenDVT.setFont(new Font("Roboto", 0, 14)); 
		txtTenDVT.setPreferredSize(new Dimension(330, 40));
		jPanel18.add(txtTenDVT);
		
	
		tablePanel.add(jPanel18, BorderLayout.NORTH); // Thêm panel vào phía trên tablePanel
		tablePanel.add(jScrollPane1, BorderLayout.CENTER); 

		add(tablePanel, BorderLayout.CENTER); // Thêm bảng vào giao diện chính
	}
	
	private void txtSearchKeyReleased(KeyEvent evt) {
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


	private void btnReloadActionPerformed(ActionEvent evt) {
		txtTenDVT.setText("");
		txtSearch.setText("");
		cboxSearch.setSelectedIndex(0);
		loadTable();
	}

	private void btnRemoveActionPerformed(ActionEvent evt) {
		try {
			DefaultTableModel modal = (DefaultTableModel) table.getModel();
			int row = table.getSelectedRow();
			String id = table.getValueAt(row, 1).toString();

			if (MessageDialog.confirm(this, "Bạn có chắc chắn xóa dòng này?", "Xóa")) {
				DM_CON.deleteById(id);
				MessageDialog.info(this, "Xóa thành công!");
				modal.removeRow(row);
			}
		} catch (Exception e) {
			MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
		}
	}

	private void btnAddActionPerformed(ActionEvent evt) {
		if (isValidateFields()) {
			DanhMuc tk = getInputFields();
			DM_CON.create(tk);
			MessageDialog.info(this, "Thêm thành công!");
			this.loadTable();
		}
	}


	private void btnUpdateActionPerformed(ActionEvent evt) {
		if (isValidateFields()) {
			try {
				int row = table.getSelectedRow();
				String id = table.getValueAt(row, 1).toString();
				String ten = txtTenDVT.getText();
				DanhMuc e = new DanhMuc(id, ten);

				DM_CON.update(e);
				MessageDialog.info(this, "Sửa thành công!");
				this.loadTable();
			} catch (Exception e) {
				MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
			}
		}
	}


	private void tableMouseClicked(MouseEvent evt) {
		int row = table.getSelectedRow();
		txtTenDVT.setText(table.getValueAt(row, 2).toString());
	}

	private JButton btnReload;
	private JComboBox<String> cboxSearch;
	private JPanel jPanel1;
	private JPanel actionPanel;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnUpdate;
	private JPanel headerPanel;
	private JPanel jPanel15;
	private JPanel jPanel18;
	private JScrollPane jScrollPane1;
	private JLabel lblTenDVT;
	private JPanel jPanel3;
	private JLabel lblTable;
	private JPanel tablePanel;
	private JTable table;
	private JTextField txtTenDVT;
	private JTextField txtSearch;
}

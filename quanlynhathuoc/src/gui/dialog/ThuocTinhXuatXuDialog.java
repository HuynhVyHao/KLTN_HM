package gui.dialog;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.XuatXuController;
import entity.XuatXu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.TableSorter;

public class ThuocTinhXuatXuDialog extends JDialog {

	private XuatXuController XX_CON = new XuatXuController(this);

	public ThuocTinhXuatXuDialog(Frame parent, boolean modal) {
		super(parent, modal);
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
		String[] header = new String[] { "STT", "Mã xuất xứ", "Tên xuất xứ" };

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

		List<XuatXu> list = XX_CON.getAllList();
		int stt = 1;
		for (XuatXu e : list) {
			modal.addRow(new Object[] { String.valueOf(stt), e.getId(), e.getTen() });
			stt++;
		}
	}

	private boolean isValidateFields() {
		if (txtTenDVT.getText().trim().equals("")) {
			MessageDialog.warring(this, "Tên đơn vị tính không được rỗng!");
			txtTenDVT.requestFocus();
			return false;
		}

		return true;
	}

	private XuatXu getInputFields() {
		String id = RandomGenerator.getRandomId();
		String ten = txtTenDVT.getText().trim();

		return new XuatXu(id, ten);
	}

	private void initComponents() {

		jPanel15 = new JPanel();
		lblDialog = new JLabel();
		jPanel1 = new JPanel();
		jPanel18 = new JPanel();
		lblTenDVT = new JLabel();
		txtTenDVT = new JTextField();
		jSeparator1 = new JSeparator();
		jPanel2 = new JPanel();
		jPanel3 = new JPanel();
		cboxSearch = new JComboBox<>();
		txtSearch = new JTextField();
		btnReload = new JButton();
		jScrollPane1 = new JScrollPane();
		table = new JTable();
		jPanel8 = new JPanel();
		btnRemove = new JButton();
		btnUpdate = new JButton();
		btnAdd = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jPanel15.setBackground(new Color(0, 153, 153));
		jPanel15.setMinimumSize(new Dimension(100, 60));
		jPanel15.setPreferredSize(new Dimension(500, 50));
		jPanel15.setLayout(new BorderLayout());

		lblDialog.setFont(new Font("Roboto Medium", 0, 18));
		lblDialog.setForeground(new Color(255, 255, 255));
		lblDialog.setHorizontalAlignment(SwingConstants.CENTER);
		lblDialog.setText("XUẤT XỨ");
		jPanel15.add(lblDialog, BorderLayout.CENTER);

		getContentPane().add(jPanel15, BorderLayout.NORTH);

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		jPanel1.setPreferredSize(new Dimension(600, 600));
		jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));

		jPanel18.setBackground(new Color(255, 255, 255));
		jPanel18.setPreferredSize(new Dimension(500, 40));
		jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblTenDVT.setFont(new Font("Roboto", 0, 14));
		lblTenDVT.setText("Tên xuất xứ");
		lblTenDVT.setMaximumSize(new Dimension(44, 40));
		lblTenDVT.setPreferredSize(new Dimension(150, 40));
		jPanel18.add(lblTenDVT);

		txtTenDVT.setFont(new Font("Roboto", 0, 14));
		txtTenDVT.setToolTipText("");
		txtTenDVT.setPreferredSize(new Dimension(330, 40));
		jPanel18.add(txtTenDVT);

		jPanel1.add(jPanel18);

		jSeparator1.setPreferredSize(new Dimension(460, 3));
		jPanel1.add(jSeparator1);

		jPanel2.setPreferredSize(new Dimension(500, 400));
		jPanel2.setLayout(new BorderLayout());

		jPanel3.setBackground(new Color(255, 255, 255));
		jPanel3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		jPanel3.setPreferredSize(new Dimension(100, 48));
		jPanel3.setLayout(new FlowLayout(FlowLayout.RIGHT));

		cboxSearch.setToolTipText("");
		cboxSearch.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		cboxSearch.setPreferredSize(new Dimension(80, 32));
		jPanel3.add(cboxSearch);

		txtSearch.setToolTipText("Tìm kiếm");
		txtSearch.setPreferredSize(new Dimension(140, 36));
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

		jPanel2.add(jPanel3, BorderLayout.PAGE_START);

		jScrollPane1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

		jPanel2.add(jScrollPane1, BorderLayout.CENTER);

		jPanel1.add(jPanel2);

		getContentPane().add(jPanel1, BorderLayout.CENTER);

		jPanel8.setBackground(new Color(255, 255, 255));
		jPanel8.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

		btnRemove.setBackground(new Color(255, 102, 102));
		btnRemove.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnRemove.setForeground(new Color(255, 255, 255));
		btnRemove.setText("XÓA");
		btnRemove.setBorderPainted(false);
		btnRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRemove.setFocusPainted(false);
		btnRemove.setFocusable(false);
		btnRemove.setPreferredSize(new Dimension(120, 40));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnRemoveActionPerformed(evt);
			}
		});
		jPanel8.add(btnRemove);

		btnUpdate.setBackground(new Color(255, 204, 51));
		btnUpdate.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnUpdate.setForeground(new Color(255, 255, 255));
		btnUpdate.setText("SỬA");
		btnUpdate.setBorderPainted(false);
		btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnUpdate.setFocusPainted(false);
		btnUpdate.setFocusable(false);
		btnUpdate.setPreferredSize(new Dimension(120, 40));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnUpdateActionPerformed(evt);
			}
		});
		jPanel8.add(btnUpdate);

		btnAdd.setBackground(new Color(0, 204, 102));
		btnAdd.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.setText("THÊM");
		btnAdd.setBorderPainted(false);
		btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdd.setFocusPainted(false);
		btnAdd.setFocusable(false);
		btnAdd.setPreferredSize(new Dimension(120, 40));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAddActionPerformed(evt);
			}
		});
		jPanel8.add(btnAdd);

		getContentPane().add(jPanel8, BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
	}

	private void btnRemoveActionPerformed(ActionEvent evt) {
		try {
			DefaultTableModel modal = (DefaultTableModel) table.getModel();
			int row = table.getSelectedRow();
			String id = table.getValueAt(row, 1).toString();

			if (MessageDialog.confirm(this, "Bạn có chắc chắn xóa dòng này?", "Xóa")) {
				XX_CON.deleteById(id);
				MessageDialog.info(this, "Xóa thành công!");
				modal.removeRow(row);
			}
		} catch (Exception e) {
			MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
		}
	}

	private void btnAddActionPerformed(ActionEvent evt) {
		if (isValidateFields()) {
			XuatXu tk = getInputFields();
			XX_CON.create(tk);
			MessageDialog.info(this, "Thêm thành công!");
			this.loadTable();
		}
	}

	private void txtSearchKeyReleased(KeyEvent evt) {
		DefaultTableModel modal = (DefaultTableModel) table.getModel();
		modal.setRowCount(0);

		String search = txtSearch.getText().toLowerCase().trim();
		String searchType = cboxSearch.getSelectedItem().toString();
		List<XuatXu> listsearch = XX_CON.getSearchTable(search, searchType);

		int stt = 1;
		for (XuatXu e : listsearch) {
			modal.addRow(new Object[] { String.valueOf(stt), e.getId(), e.getTen() });
			stt++;
		}
	}

	private void btnUpdateActionPerformed(ActionEvent evt) {
		if (isValidateFields()) {
			try {
				int row = table.getSelectedRow();
				String id = table.getValueAt(row, 1).toString();
				String ten = txtTenDVT.getText();
				XuatXu e = new XuatXu(id, ten);

				XX_CON.update(e);
				MessageDialog.info(this, "Sửa thành công!");
				this.loadTable();
			} catch (Exception e) {
				MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
			}
		}
	}

	private void btnReloadActionPerformed(ActionEvent evt) {
		txtTenDVT.setText("");
		txtSearch.setText("");
		cboxSearch.setSelectedIndex(0);
		loadTable();
	}

	private void tableMouseClicked(MouseEvent evt) {
		int row = table.getSelectedRow();
		txtTenDVT.setText(table.getValueAt(row, 2).toString());
	}

	private JButton btnAdd;
	private JButton btnReload;
	private JButton btnRemove;
	private JButton btnUpdate;
	private JComboBox<String> cboxSearch;
	private JPanel jPanel1;
	private JPanel jPanel15;
	private JPanel jPanel18;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel8;
	private JScrollPane jScrollPane1;
	private JSeparator jSeparator1;
	private JLabel lblDialog;
	private JLabel lblTenDVT;
	private JTable table;
	private JTextField txtSearch;
	private JTextField txtTenDVT;
}

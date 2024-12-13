package gui.dialog;

import controller.NhanVienController;
import controller.TaiKhoanController;
import controller.VaiTroController;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.VaiTro;
import gui.page.TaiKhoanPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.BCrypt;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.TableSorter;
import utils.Validation;

public class CreateTaiKhoanDialog extends JDialog {

	private final TaiKhoanController TK_CON = new TaiKhoanController();
	private TaiKhoanPage TK_GUI;

	private final List<VaiTro> listVT = new VaiTroController().getAllList();

	DefaultTableModel modal;

	public CreateTaiKhoanDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public CreateTaiKhoanDialog(Frame parent, boolean modal, TaiKhoanPage TK_GUI) {
		super(parent, modal);
		this.TK_GUI = TK_GUI;
		initComponents();
		fillCombobox();
		tableLayout();
	}

	private void fillCombobox() {
		for (VaiTro vt : listVT) {
			if (!vt.getId().equals("admin")) {
				cboxVaiTro.addItem(vt.getTen());
			}
		}
	}

	private void tableLayout() {
		String[] header = new String[] { "STT", "Mã nhân viên", "Họ tên", "Số điện thoại", "Năm sinh" };

		modal = new DefaultTableModel();
		modal.setColumnIdentifiers(header);
		tableNV.setModel(modal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		tableNV.setDefaultRenderer(Object.class, centerRenderer);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tableNV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tableNV.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableNV.getColumnModel().getColumn(2).setPreferredWidth(200);

		loadTable();
		sortTable();
	}

	private void sortTable() {
		tableNV.setAutoCreateRowSorter(true);
		TableSorter.configureTableColumnSorter(tableNV, 0, TableSorter.STRING_COMPARATOR);
	}

	public void loadTable() {
		modal.setRowCount(0);

		List<NhanVien> listNV = new NhanVienController().getAllList();
		List<NhanVien> listNVInTK = TK_CON.getListNV();
		int stt = 1;
		for (NhanVien e : listNV) {
			if (!listNVInTK.contains(e)) {
				modal.addRow(new Object[] { String.valueOf(stt), e.getId(), e.getHoTen(), e.getSdt(), e.getNamSinh() });
			}
			stt++;
		}
	}

	private boolean isValidateFields() {
		for (TaiKhoan tk : TK_CON.getAllList()) {
			if (tk.getUsername().equals(txtUsername.getText().trim())) {
				MessageDialog.warring(this, "Tên đăng nhập đã tồn tại!");
				return false;
			}
		}

		if (Validation.isEmpty(txtUsername.getText()) || txtUsername.getText().length() < 3) {
			MessageDialog.warring(this, "Username không được để trống và có ít nhất 3 ký tự!");
			txtUsername.requestFocus();
			return false;
		}

		if (txtPassword.getText().trim().equals("") || txtPassword.getText().length() < 6) {
			MessageDialog.warring(this, "Password không được để trống và có ít nhất 6 ký tự!");
			txtPassword.requestFocus();
			return false;
		}

		if (tableNV.getSelectedRow() < 0) {
			MessageDialog.warring(this, "Vui lòng chọn nhân viên!");
			return false;
		}

		return true;
	}

	private TaiKhoan getInputFields() {
		String id = RandomGenerator.getRandomId();
		String username = txtUsername.getText().trim();
		String password = BCrypt.hashpw(txtPassword.getText(), BCrypt.gensalt(10));
		int row = tableNV.getSelectedRow();
		String idNV = tableNV.getValueAt(row, 1).toString();
		NhanVien nhanVien = new NhanVienController().selectById(idNV);
		String idVT = listVT.get(cboxVaiTro.getSelectedIndex() + 1).getId();
		VaiTro vaiTro = new VaiTroController().selectById(idVT);

		return new TaiKhoan(id, username, password, nhanVien, vaiTro);
	}

	private void initComponents() {

		jPanel15 = new JPanel();
		jLabel8 = new JLabel();
		jPanel1 = new JPanel();
		jPanel18 = new JPanel();
		lblHoTen = new JLabel();
		txtUsername = new JTextField();
		jPanel19 = new JPanel();
		Password = new JLabel();
		txtPassword = new JPasswordField();
		jPanel22 = new JPanel();
		jLabel15 = new JLabel();
		cboxVaiTro = new JComboBox<>();
		jPanel2 = new JPanel();
		jScrollPane1 = new JScrollPane();
		tableNV = new JTable();
		jPanel8 = new JPanel();
		btnHuy = new JButton();
		btnAdd = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(700, 600));

		jPanel15.setBackground(new Color(0, 153, 153));
		jPanel15.setMinimumSize(new Dimension(100, 60));
		jPanel15.setPreferredSize(new Dimension(500, 50));
		jPanel15.setLayout(new BorderLayout());

		jLabel8.setFont(new Font("Roboto Medium", 0, 18));
		jLabel8.setForeground(new Color(255, 255, 255));
		jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel8.setText("THÊM TÀI KHOẢN");
		jPanel15.add(jLabel8, BorderLayout.CENTER);

		getContentPane().add(jPanel15, BorderLayout.NORTH);

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setPreferredSize(new Dimension(600, 600));
		jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));

		jPanel18.setBackground(new Color(255, 255, 255));
		jPanel18.setPreferredSize(new Dimension(500, 40));
		jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblHoTen.setFont(new Font("Roboto", 0, 14));
		lblHoTen.setText("Username");
		lblHoTen.setMaximumSize(new Dimension(44, 40));
		lblHoTen.setPreferredSize(new Dimension(150, 40));
		jPanel18.add(lblHoTen);

		txtUsername.setFont(new Font("Roboto", 0, 14));
		txtUsername.setToolTipText("");
		txtUsername.setPreferredSize(new Dimension(330, 40));
		jPanel18.add(txtUsername);

		jPanel1.add(jPanel18);

		jPanel19.setBackground(new Color(255, 255, 255));
		jPanel19.setPreferredSize(new Dimension(500, 40));
		jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		Password.setFont(new Font("Roboto", 0, 14));
		Password.setText("Password");
		Password.setMaximumSize(new Dimension(44, 40));
		Password.setPreferredSize(new Dimension(150, 40));
		jPanel19.add(Password);

		txtPassword.setPreferredSize(new Dimension(330, 40));
		jPanel19.add(txtPassword);

		jPanel1.add(jPanel19);

		jPanel22.setBackground(new Color(255, 255, 255));
		jPanel22.setPreferredSize(new Dimension(500, 40));
		jPanel22.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		jLabel15.setFont(new Font("Roboto", 0, 14));
		jLabel15.setText("Vai trò");
		jLabel15.setMaximumSize(new Dimension(44, 40));
		jLabel15.setPreferredSize(new Dimension(150, 40));
		jPanel22.add(jLabel15);

		cboxVaiTro.setPreferredSize(new Dimension(330, 40));
		jPanel22.add(cboxVaiTro);

		jPanel1.add(jPanel22);

		jPanel2.setPreferredSize(new Dimension(600, 230));
		jPanel2.setLayout(new BorderLayout());

		tableNV.setFocusable(false);
		tableNV.setRowHeight(40);
		tableNV.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableNV.setShowHorizontalLines(true);
		jScrollPane1.setViewportView(tableNV);

		jPanel2.add(jScrollPane1, BorderLayout.CENTER);

		jPanel1.add(jPanel2);

		getContentPane().add(jPanel1, BorderLayout.CENTER);

		jPanel8.setBackground(new Color(255, 255, 255));
		jPanel8.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

		btnHuy.setBackground(new Color(255, 102, 102));
		btnHuy.setFont(new Font("Roboto Mono Medium", 0, 16)); // NOI18N
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

		btnAdd.setBackground(new Color(0, 204, 102));
		btnAdd.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.setText("THÊM");
		btnAdd.setBorderPainted(false);
		btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdd.setFocusPainted(false);
		btnAdd.setFocusable(false);
		btnAdd.setPreferredSize(new Dimension(200, 40));
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

	private void btnHuyActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	private void btnAddActionPerformed(ActionEvent evt) {
		if (isValidateFields()) {
			TaiKhoan e = getInputFields();
			TK_CON.create(e);
			this.dispose();
			MessageDialog.info(this, "Thêm thành công!");
			TK_GUI.loadTable(TK_CON.getAllList());
		}
	}

	private JLabel Password;
	private JButton btnAdd;
	private JButton btnHuy;
	private JComboBox<String> cboxVaiTro;
	private JLabel jLabel15;
	private JLabel jLabel8;
	private JPanel jPanel1;
	private JPanel jPanel15;
	private JPanel jPanel18;
	private JPanel jPanel19;
	private JPanel jPanel2;
	private JPanel jPanel22;
	private JPanel jPanel8;
	private JScrollPane jScrollPane1;
	private JLabel lblHoTen;
	private JTable tableNV;
	private JPasswordField txtPassword;
	private JTextField txtUsername;
}
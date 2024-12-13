package gui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import controller.DanhMucController;
import controller.NhaCungCapController;
import dao.DanhMucDAO;
import entity.DanhMuc;
import entity.NhaCungCap;
import gui.page.NhaCungCapPage;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.Validation;

public class CreateNhaCungCapDialog extends JDialog {

	private NhaCungCapController NCC_CON = new NhaCungCapController();
	private NhaCungCapPage NCC_GUI;
	private final List<DanhMuc> list = new DanhMucController().getAllList();

	public CreateNhaCungCapDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		loadDanhMuc();
	}

	public CreateNhaCungCapDialog(Frame parent, boolean modal, NhaCungCapPage NCC_GUI) {
		super(parent, modal);
		initComponents();
		this.NCC_GUI = NCC_GUI;
		loadDanhMuc(); // Load danh mục vào combobox khi dialog được mở
	}

	// Load danh mục into the combobox
	private void loadDanhMuc() {
		try {
			cboDanhMuc.removeAllItems(); // Clear any previous items
			for (DanhMuc vt : list) {
				cboDanhMuc.addItem(vt.getTen()); // Add each danh mục name to the combobox
			}
		} catch (Exception e) {
			MessageDialog.error(this, "Lỗi nạp danh mục: " + e.getMessage());
		}
	}

	private boolean isValidateFields() {
		if (txtTen.getText().trim().equals("")) {
			MessageDialog.warring(this, "Tên Nhà cung cấp không được rỗng!");
			txtTen.requestFocus();
			return false;
		}

		if (txtSdt.getText().trim().equals("") || !Validation.isNumber(txtSdt.getText())
				|| txtSdt.getText().length() != 10) {
			MessageDialog.warring(this, "Số điện thoại không được rỗng và có 10 ký tự sô!");
			txtSdt.requestFocus();
			return false;
		}

		if (txtDiaChi.getText().trim().equals("")) {
			MessageDialog.warring(this, "Địa chỉ Nhà cung cấp không được rỗng!");
			txtDiaChi.requestFocus();
			return false;
		}

		return true;
	}

	// Get input data from fields
	private NhaCungCap getInputFields() {
		String id = RandomGenerator.getRandomId(); // Generate random ID
		String hoTen = txtTen.getText().trim(); // Get the name
		String sdt = txtSdt.getText().trim(); // Get the phone number
		String diaChi = txtDiaChi.getText().trim(); // Get the address
		String idDM = list.get(cboDanhMuc.getSelectedIndex()).getId(); // Get the selected danh mục ID
		DanhMuc danhMuc = new DanhMucController().selectById(idDM); // Get the danh mục object

		// Create and return the NhaCungCap object
		return new NhaCungCap(id, hoTen, sdt, diaChi, danhMuc);
	}

	private void initComponents() {

		jPanel15 = new JPanel();
		jLabel8 = new JLabel();
		jPanel1 = new JPanel();
		jPanel18 = new JPanel();
		lblHoTen = new JLabel();
		txtTen = new JTextField();
		jPanel19 = new JPanel();
		jLabel12 = new JLabel();
		txtSdt = new JTextField();
		jPanel20 = new JPanel();
		jLabel13 = new JLabel();
		txtDiaChi = new JTextField();
		jPanel8 = new JPanel();
		btnHuy = new JButton();
		btnAdd = new JButton();
		jPanelDanhMuc = new JPanel();
		jLabelDanhMuc = new JLabel();
		cboDanhMuc = new JComboBox<>();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(600, 600));

		jPanel15.setBackground(new Color(0, 153, 153));
		jPanel15.setMinimumSize(new Dimension(100, 60));
		jPanel15.setPreferredSize(new Dimension(500, 50));
		jPanel15.setLayout(new BorderLayout());

		jLabel8.setFont(new Font("Roboto Medium", 0, 18));
		jLabel8.setForeground(new Color(255, 255, 255));
		jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel8.setText("THÊM NHÀ CUNG CẤP");
		jPanel15.add(jLabel8, BorderLayout.CENTER);

		getContentPane().add(jPanel15, BorderLayout.NORTH);

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));

		jPanel18.setBackground(new Color(255, 255, 255));
		jPanel18.setPreferredSize(new Dimension(500, 40));
		jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		lblHoTen.setFont(new Font("Roboto", 0, 14));
		lblHoTen.setText("Tên nhà cung cấp");
		lblHoTen.setMaximumSize(new Dimension(44, 40));
		lblHoTen.setPreferredSize(new Dimension(150, 40));
		jPanel18.add(lblHoTen);

		txtTen.setFont(new Font("Roboto", 0, 14));
		txtTen.setToolTipText("");
		txtTen.setPreferredSize(new Dimension(330, 40));
		jPanel18.add(txtTen);

		jPanel1.add(jPanel18);

		jPanel19.setBackground(new Color(255, 255, 255));
		jPanel19.setPreferredSize(new Dimension(500, 40));
		jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel12.setFont(new Font("Roboto", 0, 14));
		jLabel12.setText("Số điện thoại");
		jLabel12.setMaximumSize(new Dimension(44, 40));
		jLabel12.setPreferredSize(new Dimension(150, 40));
		jPanel19.add(jLabel12);

		txtSdt.setFont(new Font("Roboto", 0, 14));
		txtSdt.setPreferredSize(new Dimension(330, 40));
		jPanel19.add(txtSdt);

		jPanel1.add(jPanel19);

		jPanel20.setBackground(new Color(255, 255, 255));
		jPanel20.setPreferredSize(new Dimension(500, 40));
		jPanel20.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel13.setFont(new Font("Roboto", 0, 14));
		jLabel13.setText("Địa chỉ");
		jLabel13.setMaximumSize(new Dimension(44, 40));
		jLabel13.setPreferredSize(new Dimension(150, 40));
		jPanel20.add(jLabel13);

		txtDiaChi.setFont(new Font("Roboto", 0, 14));
		txtDiaChi.setPreferredSize(new Dimension(330, 40));
		jPanel20.add(txtDiaChi);

		jPanel1.add(jPanel20);

		// Trong initComponents
		jPanelDanhMuc.setBackground(new Color(255, 255, 255));
		jPanelDanhMuc.setPreferredSize(new Dimension(500, 40));
		jPanelDanhMuc.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabelDanhMuc.setFont(new Font("Roboto", 0, 14));
		jLabelDanhMuc.setText("Danh mục");
		jLabelDanhMuc.setMaximumSize(new Dimension(44, 40));
		jLabelDanhMuc.setPreferredSize(new Dimension(150, 40));
		jPanelDanhMuc.add(jLabelDanhMuc);

//        cboDanhMuc.setFont(new Font("Roboto", 0, 14));
		cboDanhMuc.setPreferredSize(new Dimension(300, 40));
		jPanelDanhMuc.add(cboDanhMuc);

		jPanel1.add(jPanelDanhMuc);

		getContentPane().add(jPanel1, BorderLayout.CENTER);

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
			NhaCungCap e = getInputFields();
			NCC_CON.create(e);
			NCC_GUI.loadTable();
			this.dispose();
		}
	}

	private JButton btnAdd;
	private JButton btnHuy;
	private JComboBox<String> cboDanhMuc;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private JLabel jLabel8;
	private JPanel jPanel1;
	private JPanel jPanel15;
	private JPanel jPanel18;
	private JPanel jPanel19;
	private JPanel jPanel20;
	private JPanel jPanel8;
	private JPanel jPanelDanhMuc;
	private JLabel jLabelDanhMuc;
	private JLabel lblHoTen;
	private JTextField txtDiaChi;
	private JTextField txtSdt;
	private JTextField txtTen;
}

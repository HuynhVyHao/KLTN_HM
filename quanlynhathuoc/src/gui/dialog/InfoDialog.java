package gui.dialog;

import controller.NhanVienController;
import entity.NhanVien;
import entity.TaiKhoan;
import gui.MainLayout;

import java.awt.event.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import utils.MessageDialog;
import utils.Validation;

public class InfoDialog extends JDialog {

	private MainLayout main;
	private TaiKhoan tk;

	public InfoDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public InfoDialog(Frame parent, boolean modal, MainLayout main, TaiKhoan tk) {
		super(parent, modal);
		initComponents();
		this.main = main;
		this.tk = tk;
		fillInput();
	}

	private void fillInput() {
		txtHoTen.setText(tk.getNhanVien().getHoTen());
		txtSdt.setText(tk.getNhanVien().getSdt());
		txtNamSinh.setText(String.valueOf(tk.getNhanVien().getNamSinh()));
		txtUsername.setText(tk.getUsername());
	}

	private boolean isValidateFields() {
		if (txtHoTen.getText().trim().equals("")) {
			MessageDialog.warring(this, "Tên nhân viên không được rỗng!");
			txtHoTen.requestFocus();
			return false;
		}

		if (txtSdt.getText().trim().equals("") || !Validation.isNumber(txtSdt.getText())
				|| txtSdt.getText().length() != 10) {
			MessageDialog.warring(this, "Số điện thoại không được rỗng và có 10 ký tự sô!");
			txtSdt.requestFocus();
			return false;
		}

		if (txtNamSinh.getText().trim().equals("")) {
			MessageDialog.warring(this, "Năm sinh không được rỗng!");
			txtNamSinh.requestFocus();
			return false;
		} else {
			try {
				int namSinh = Integer.parseInt(txtNamSinh.getText());
				int namHienTai = Calendar.getInstance().get(Calendar.YEAR);
				if (!(namSinh >= 1900 && namSinh <= namHienTai)) {
					MessageDialog.warring(this, "Năm sinh phải >= 1900 và <= " + namHienTai);
					txtNamSinh.requestFocus();
					return false;
				} else if (namHienTai - namSinh < 18) {
					MessageDialog.warring(this, "Nhân viên phải đủ 18 tuổi");
					txtNamSinh.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				MessageDialog.warring(this, "Năm sinh phải có 4 ký tự số!");
				txtNamSinh.requestFocus();
				return false;
			}
		}

		return true;
	}

	private NhanVien getInputFields() {
		String id = tk.getNhanVien().getId();
		String hoTen = txtHoTen.getText().trim();
		String sdt = txtSdt.getText().trim();
		String gioiTinh = tk.getNhanVien().getGioiTinh();
		int namSinh = Integer.parseInt(txtNamSinh.getText().trim());
		Date ngayVaoLam = tk.getNhanVien().getNgayVaoLam();

		return new NhanVien(id, hoTen, sdt, gioiTinh, namSinh, ngayVaoLam);
	}

	private void initComponents() {

		jPanel15 = new JPanel();
		jLabel8 = new JLabel();
		jPanel1 = new JPanel();
		jPanel18 = new JPanel();
		lblHoTen = new JLabel();
		txtHoTen = new JTextField();
		jPanel19 = new JPanel();
		jLabel12 = new JLabel();
		txtSdt = new JTextField();
		jPanel20 = new JPanel();
		jLabel13 = new JLabel();
		txtNamSinh = new JTextField();
		jPanel23 = new JPanel();
		lblHoTen1 = new JLabel();
		txtUsername = new JTextField();
		jPanel24 = new JPanel();
		lblHoTen2 = new JLabel();
		btnChangePassword = new JButton();
		jPanel8 = new JPanel();
		btnHuy = new JButton();
		btnUpdate = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(600, 600));

		jPanel15.setBackground(new Color(0, 153, 153));
		jPanel15.setMinimumSize(new Dimension(100, 60));
		jPanel15.setPreferredSize(new Dimension(500, 50));
		jPanel15.setLayout(new BorderLayout());

		jLabel8.setFont(new Font("Roboto Medium", 0, 18)); 
		jLabel8.setForeground(new Color(255, 255, 255));
		jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel8.setText("THÔNG TIN CÁ NHÂN");
		jPanel15.add(jLabel8, BorderLayout.CENTER);

		getContentPane().add(jPanel15, BorderLayout.NORTH);

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));

		jPanel18.setBackground(new Color(255, 255, 255));
		jPanel18.setPreferredSize(new Dimension(500, 40));
		jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblHoTen.setFont(new Font("Roboto", 0, 14)); 
		lblHoTen.setText("Họ tên");
		lblHoTen.setMaximumSize(new Dimension(44, 40));
		lblHoTen.setPreferredSize(new Dimension(150, 40));
		jPanel18.add(lblHoTen);

		txtHoTen.setFont(new Font("Roboto", 0, 14)); 
		txtHoTen.setToolTipText("");
		txtHoTen.setPreferredSize(new Dimension(330, 40));
		jPanel18.add(txtHoTen);

		jPanel1.add(jPanel18);

		jPanel19.setBackground(new Color(255, 255, 255));
		jPanel19.setPreferredSize(new Dimension(500, 40));
		jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

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
		jPanel20.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		jLabel13.setFont(new Font("Roboto", 0, 14)); 
		jLabel13.setText("Năm sinh");
		jLabel13.setMaximumSize(new Dimension(44, 40));
		jLabel13.setPreferredSize(new Dimension(150, 40));
		jPanel20.add(jLabel13);

		txtNamSinh.setFont(new Font("Roboto", 0, 14)); 
		txtNamSinh.setPreferredSize(new Dimension(330, 40));
		jPanel20.add(txtNamSinh);

		jPanel1.add(jPanel20);

		jPanel23.setBackground(new Color(255, 255, 255));
		jPanel23.setPreferredSize(new Dimension(500, 40));
		jPanel23.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblHoTen1.setFont(new Font("Roboto", 0, 14)); 
		lblHoTen1.setText("Username");
		lblHoTen1.setMaximumSize(new Dimension(44, 40));
		lblHoTen1.setPreferredSize(new Dimension(150, 40));
		jPanel23.add(lblHoTen1);

		txtUsername.setEditable(false);
		txtUsername.setFont(new Font("Roboto", 0, 14)); 
		txtUsername.setToolTipText("");
		txtUsername.setFocusable(false);
		txtUsername.setPreferredSize(new Dimension(330, 40));
		jPanel23.add(txtUsername);

		jPanel1.add(jPanel23);

		jPanel24.setBackground(new Color(255, 255, 255));
		jPanel24.setPreferredSize(new Dimension(500, 40));
		jPanel24.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblHoTen2.setFont(new Font("Roboto", 0, 14)); 
		lblHoTen2.setText("Password");
		lblHoTen2.setMaximumSize(new Dimension(44, 40));
		lblHoTen2.setPreferredSize(new Dimension(150, 40));
		jPanel24.add(lblHoTen2);

		btnChangePassword.setBackground(new Color(0, 102, 102));
		btnChangePassword.setFont(new Font("Roboto Mono Medium", 0, 16)); 
		btnChangePassword.setForeground(new Color(255, 255, 255));
		btnChangePassword.setText("Đổi mật khẩu");
		btnChangePassword.setBorderPainted(false);
		btnChangePassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnChangePassword.setFocusPainted(false);
		btnChangePassword.setFocusable(false);
		btnChangePassword.setPreferredSize(new Dimension(200, 40));
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnChangePasswordActionPerformed(evt);
			}
		});
		jPanel24.add(btnChangePassword);

		jPanel1.add(jPanel24);

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

		btnUpdate.setBackground(new Color(0, 204, 102));
		btnUpdate.setFont(new Font("Roboto Mono Medium", 0, 16)); 
		btnUpdate.setForeground(new Color(255, 255, 255));
		btnUpdate.setText("CẬP NHẬT");
		btnUpdate.setBorderPainted(false);
		btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnUpdate.setFocusPainted(false);
		btnUpdate.setFocusable(false);
		btnUpdate.setPreferredSize(new Dimension(200, 40));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnUpdateActionPerformed(evt);
			}
		});
		jPanel8.add(btnUpdate);

		getContentPane().add(jPanel8, BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
	}

	private void btnHuyActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	private void btnUpdateActionPerformed(ActionEvent evt) {
		if (isValidateFields()) {
			NhanVien nv = getInputFields();
			new NhanVienController().update(nv);
			main.fillInfo();
			this.dispose();
		}
	}

	private void btnChangePasswordActionPerformed(ActionEvent evt) {
		InfoChangePasswordDialog dialog = new InfoChangePasswordDialog(null, true, main, tk);
		this.dispose();
		dialog.setVisible(true);
	}

	private JButton btnChangePassword;
	private JButton btnHuy;
	private JButton btnUpdate;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private JLabel jLabel8;
	private JPanel jPanel1;
	private JPanel jPanel15;
	private JPanel jPanel18;
	private JPanel jPanel19;
	private JPanel jPanel20;
	private JPanel jPanel23;
	private JPanel jPanel24;
	private JPanel jPanel8;
	private JLabel lblHoTen;
	private JLabel lblHoTen1;
	private JLabel lblHoTen2;
	private JTextField txtHoTen;
	private JTextField txtNamSinh;
	private JTextField txtSdt;
	private JTextField txtUsername;
}

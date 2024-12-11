package gui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.NhanVienController;
import controller.TaiKhoanController;
import controller.VaiTroController;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.VaiTro;
import gui.MainLayout;
import utils.BCrypt;
import utils.MessageDialog;

public class InfoChangePasswordDialog extends JDialog {

	private final TaiKhoanController TK_CON = new TaiKhoanController();
	private MainLayout main;
	private TaiKhoan tk;

	public InfoChangePasswordDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public InfoChangePasswordDialog(Frame parent, boolean modal, MainLayout main, TaiKhoan tk) {
		super(parent, modal);
		initComponents();
		this.main = main;
		this.tk = tk;
	}

	private boolean isValidateFields() {
		if (txtCurrentPassword.getText().trim().equals("") || txtCurrentPassword.getText().length() < 6) {
			MessageDialog.warring(this, "Mật khẩu hiện tại không được rỗng và có ít nhất 6 kí tự!");
			txtCurrentPassword.requestFocus();
			return false;
		} else {
			if (BCrypt.compare(txtCurrentPassword.getText(), tk.getPassword()) == false) {
				MessageDialog.warring(this, "Mật khẩu hiện tại không chính xác!");
				txtCurrentPassword.requestFocus();
				return false;
			}
		}

		if (txtNewPassword.getText().trim().equals("") || txtNewPassword.getText().length() < 6) {
			MessageDialog.warring(this, "Mật khẩu mới không được rỗng và có ít nhất 6 kí tự!");
			txtNewPassword.requestFocus();
			return false;
		}

		if (txtReNewPassword.getText().trim().equals("") || txtReNewPassword.getText().length() < 6) {
			MessageDialog.warring(this, "Nhập lại mật khẩu mới không được rỗng và có ít nhất 6 kí tự!");
			txtReNewPassword.requestFocus();
			return false;
		} else {
			if (txtNewPassword.getText().equals(txtReNewPassword.getText()) == false) {
				MessageDialog.warring(this, "Nhập lại mật khẩu mới không chính xác!");
				txtReNewPassword.requestFocus();
				return false;
			}
		}

		return true;
	}

	private TaiKhoan getInputFields() {
		String id = tk.getId();
		String username = tk.getUsername();
		String password = BCrypt.hashpw(txtReNewPassword.getText(), BCrypt.gensalt(10));
		String idNV = tk.getNhanVien().getId();
		NhanVien nhanVien = new NhanVienController().selectById(idNV);
		String idVT = tk.getVaiTro().getId();
		VaiTro vaiTro = new VaiTroController().selectById(idVT);

		return new TaiKhoan(id, username, password, nhanVien, vaiTro);
	}

	private void initComponents() {

		jPanel15 = new JPanel();
		jLabel8 = new JLabel();
		jPanel1 = new JPanel();
		jPanel18 = new JPanel();
		lblHoTen = new JLabel();
		txtCurrentPassword = new JPasswordField();
		jPanel19 = new JPanel();
		lblHoTen1 = new JLabel();
		txtNewPassword = new JPasswordField();
		jPanel20 = new JPanel();
		lblHoTen2 = new JLabel();
		txtReNewPassword = new JPasswordField();
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
		jLabel8.setText("ĐỔI MẬT KHẨU");
		jPanel15.add(jLabel8, BorderLayout.CENTER);

		getContentPane().add(jPanel15, BorderLayout.NORTH);

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));

		jPanel18.setBackground(new Color(255, 255, 255));
		jPanel18.setPreferredSize(new Dimension(500, 40));
		jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblHoTen.setFont(new Font("Roboto", 0, 14)); 
		lblHoTen.setText("Mật khẩu hiện tại");
		lblHoTen.setMaximumSize(new Dimension(44, 40));
		lblHoTen.setPreferredSize(new Dimension(150, 40));
		jPanel18.add(lblHoTen);

		txtCurrentPassword.setPreferredSize(new Dimension(330, 40));
		jPanel18.add(txtCurrentPassword);

		jPanel1.add(jPanel18);

		jPanel19.setBackground(new Color(255, 255, 255));
		jPanel19.setPreferredSize(new Dimension(500, 40));
		jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblHoTen1.setFont(new Font("Roboto", 0, 14)); 
		lblHoTen1.setText("Mật khẩu mới");
		lblHoTen1.setMaximumSize(new Dimension(44, 40));
		lblHoTen1.setPreferredSize(new Dimension(150, 40));
		jPanel19.add(lblHoTen1);

		txtNewPassword.setPreferredSize(new Dimension(330, 40));
		jPanel19.add(txtNewPassword);

		jPanel1.add(jPanel19);

		jPanel20.setBackground(new Color(255, 255, 255));
		jPanel20.setPreferredSize(new Dimension(500, 40));
		jPanel20.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblHoTen2.setFont(new Font("Roboto", 0, 14)); 
		lblHoTen2.setText("Nhập lại mật khẩu mới");
		lblHoTen2.setMaximumSize(new Dimension(44, 40));
		lblHoTen2.setPreferredSize(new Dimension(150, 40));
		jPanel20.add(lblHoTen2);

		txtReNewPassword.setPreferredSize(new Dimension(330, 40));
		jPanel20.add(txtReNewPassword);

		jPanel1.add(jPanel20);

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
		btnUpdate.setText("LƯU");
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
		InfoDialog dialog = new InfoDialog(null, true, main, tk);
		dialog.setVisible(true);
	}

	private void btnUpdateActionPerformed(ActionEvent evt) {
		if (isValidateFields()) {
			TaiKhoan e = getInputFields();
			MessageDialog.info(this, "Cập nhập thành công!");
			TK_CON.update(e);
			this.dispose();
		}
	}

	private JButton btnHuy;
	private JButton btnUpdate;
	private JLabel jLabel8;
	private JPanel jPanel1;
	private JPanel jPanel15;
	private JPanel jPanel18;
	private JPanel jPanel19;
	private JPanel jPanel20;
	private JPanel jPanel8;
	private JLabel lblHoTen;
	private JLabel lblHoTen1;
	private JLabel lblHoTen2;
	private JPasswordField txtCurrentPassword;
	private JPasswordField txtNewPassword;
	private JPasswordField txtReNewPassword;
}

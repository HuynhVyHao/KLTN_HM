package gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import controller.TaiKhoanController;
import entity.TaiKhoan;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import utils.BCrypt;
import utils.MessageDialog;
import utils.Validation;

public class Login extends JFrame {

	public Login() {
		initComponents();
		loginLayout();
	}

	public static void main(String args[]) {
		initFlatlaf();
		java.awt.EventQueue.invokeLater(() -> {
			Login login = new Login();
			login.setVisible(true);
		});
	}

	private static void initFlatlaf() {
		FlatRobotoFont.install();
		FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
		FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
		FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
		FlatLaf.registerCustomDefaultsSource("style");
		FlatIntelliJLaf.setup();
	}

	private void loginLayout() {
		txtUsername.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
				new FlatSVGIcon("./icon/username.svg"));
		txtPassword.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
				new FlatSVGIcon("./icon/password.svg"));
	}

	private boolean isValidateFields() {
		if (Validation.isEmpty(txtUsername.getText()) || Validation.isEmpty(txtPassword.getText())) {
			MessageDialog.warring(this, "Không được để trống!");
			return false;
		}

		return true;
	}

	private void authentication() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();

		if (isValidateFields()) {
			TaiKhoan tk = new TaiKhoanController().selectByUsername(username);

			if (tk == null) {
				MessageDialog.error(this, "Tài khoản không tồn tại!");
				return;
			}

			if (username.equals(tk.getUsername()) && BCrypt.compare(password, tk.getPassword())) {
				new MainLayout(tk).setVisible(true);
				this.dispose();
			} else {
				MessageDialog.error(this, "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!");
			}
		}
	}

	private void initComponents() {

		jPanel1 = new JPanel();
		jPanel2 = new JPanel();
		jLabel1 = new JLabel();
		jPanel7 = new JPanel();
		jPanel4 = new JPanel();
		logo = new JLabel();
		jPanel6 = new JPanel();
		lblUsername = new JLabel();
		txtUsername = new JTextField();
		jPanel3 = new JPanel();
		lblPassword = new JLabel();
		txtPassword = new JPasswordField();
		btnLogin = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Đăng Nhập");
		setAlwaysOnTop(true);
		setResizable(false);

		jPanel1.setLayout(new java.awt.BorderLayout());

		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel2.setPreferredSize(new Dimension(600, 600));
		jPanel2.setLayout(new BorderLayout());

		jLabel1.setBackground(new Color(255, 255, 255));
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setIcon(new FlatSVGIcon("./img/loginn.svg"));
		jPanel2.add(jLabel1, BorderLayout.CENTER);

		jPanel1.add(jPanel2, BorderLayout.WEST);

		jPanel7.setBackground(new Color(255, 255, 255));
		jPanel7.setPreferredSize(new Dimension(400, 400));

		jPanel4.setBackground(new Color(255, 255, 255));
		jPanel4.setPreferredSize(new Dimension(360, 600));
		jPanel4.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER, 0, 16));

		logo.setFont(new Font("Roboto", 1, 38));
		logo.setForeground(new Color(61, 245, 116));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setIcon(new ImageIcon(getClass().getResource("/icon/logo.png")));
		logo.setText("Nhà Thuốc H&M");
		logo.setPreferredSize(new Dimension(360, 120));
		jPanel4.add(logo);

		jPanel6.setBackground(new Color(255, 255, 255));
		jPanel6.setPreferredSize(new Dimension(360, 80));
		jPanel6.setLayout(new GridLayout(2, 0));

		jPanel6.add(lblUsername);

		txtUsername.setFont(new Font("Roboto Mono", 0, 14));
		txtUsername.setForeground(new Color(0, 0, 0));
		txtUsername.setText("admin");
		txtUsername.setPreferredSize(new Dimension(350, 27));
		txtUsername.setSelectionColor(new Color(0, 153, 153));
		txtUsername.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				txtUsernameKeyPressed(evt);
			}
		});
		jPanel6.add(txtUsername);

		jPanel4.add(jPanel6);

		jPanel3.setBackground(new Color(255, 255, 255));
		jPanel3.setMinimumSize(new Dimension(360, 80));
		jPanel3.setPreferredSize(new Dimension(360, 80));
		jPanel3.setLayout(new GridLayout(2, 0));

		jPanel3.add(lblPassword);

		txtPassword.setFont(new Font("Roboto Mono", 0, 14));
		txtPassword.setText("123123");
		txtPassword.setPreferredSize(new Dimension(90, 40));
		txtPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				txtPasswordKeyPressed(evt);
			}
		});
		jPanel3.add(txtPassword);

		jPanel4.add(jPanel3);

		btnLogin.setBackground(new Color(0, 205, 102));
		btnLogin.setFont(new Font("Roboto Mono", 1, 18));
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setText("Đăng nhập");
		btnLogin.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
		btnLogin.setFocusable(false);
		btnLogin.setPreferredSize(new Dimension(220, 40));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnLoginActionPerformed(evt);
			}
		});
		jPanel4.add(btnLogin);

		GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout
				.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanel7Layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jPanel4,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(20, Short.MAX_VALUE)));
		jPanel7Layout
				.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
								.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));

		jPanel1.add(jPanel7, BorderLayout.CENTER);

		javax.swing.GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jPanel1,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jPanel1,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
		setLocationRelativeTo(null);
	}

	private void btnLoginActionPerformed(ActionEvent evt) {
		authentication();
	}

	private void txtUsernameKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			authentication();
		}
	}

	private void txtPasswordKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			authentication();
		}
	}

	private JButton btnLogin;
	private JLabel jLabel1;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel6;
	private JPanel jPanel7;
	private JLabel lblPassword;
	private JLabel lblUsername;
	private JLabel logo;
	private JPasswordField txtPassword;
	private JTextField txtUsername;
}

package gui.dialog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.DanhMucController;
import controller.DonViTinhController;
import controller.ThuocController;
import controller.XuatXuController;
import entity.DanhMuc;
import entity.DonViTinh;
import entity.Thuoc;
import entity.XuatXu;
import gui.page.CreatePhieuNhapPage;
import gui.page.ThuocPage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

import utils.MessageDialog;
import utils.Validation;
import java.awt.*;
import java.awt.event.*;

import com.toedter.calendar.JDateChooser;

public class UpdateThuocDialog extends JDialog {

	private final ThuocController THUOC_CON = new ThuocController();
	private ThuocPage THUOC_GUI;
	private Thuoc thuoc;
	private CreatePhieuNhapPage PN_GUI;
	private byte[] thuocImage;

	private final List<DanhMuc> listDM = new DanhMucController().getAllList();
	private final List<DonViTinh> listDVT = new DonViTinhController().getAllList();
	private final List<XuatXu> listXX = new XuatXuController().getAllList();

	public UpdateThuocDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public UpdateThuocDialog(Frame parent, boolean modal, ThuocPage THUOC_GUI, Thuoc thuoc) {
		super(parent, modal);
		initComponents();
		this.THUOC_GUI = THUOC_GUI;
		this.thuoc = thuoc;
		fillCombobox();
		fillInput();
	}

	public UpdateThuocDialog(Frame parent, boolean modal, CreatePhieuNhapPage PN_GUI, Thuoc thuoc) {
		super(parent, modal);
		initComponents();
		this.PN_GUI = PN_GUI; // Lưu đối tượng CreatePhieuNhapPage vào biến này
		this.thuoc = thuoc;
		fillCombobox();
		fillInput();
	}

	private void fillCombobox() {
		for (DanhMuc vt : listDM) {
			cboxDanhMuc.addItem(vt.getTen());
		}

		for (DonViTinh vt : listDVT) {
			cboxDonViTinh.addItem(vt.getTen());
		}

		// Thêm lựa chọn loại thuốc (Kê đơn, Không kê đơn)
		cboxLoaiThuoc.addItem("Kê đơn");
		cboxLoaiThuoc.addItem("Không kê đơn");

		for (XuatXu vt : listXX) {
			cboxXuatXu.addItem(vt.getTen());
		}
	}

	private void fillInput() {
		txtTenThuoc.setText(thuoc.getTenThuoc());
		thuocImage = thuoc.getHinhAnh();
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(thuocImage).getImage()
				.getScaledInstance(txtHinhAnh.getWidth(), txtHinhAnh.getHeight(), Image.SCALE_SMOOTH));
		txtHinhAnh.setIcon(imageIcon);
		txtThanhPhan.setText(thuoc.getThanhPhan());
		cboxDanhMuc.setSelectedItem(thuoc.getDanhMuc().getTen());
		cboxDonViTinh.setSelectedItem(thuoc.getDonViTinh().getTen());
		cboxXuatXu.setSelectedItem(thuoc.getXuatXu().getTen());
		txtSoLuong.setText(String.valueOf(thuoc.getSoLuongTon()));
		txtGiaNhap.setText(String.valueOf(thuoc.getGiaNhap()));
		txtDonGia.setText(String.valueOf(thuoc.getDonGia()));
		txtNgaySanXuat_1.setDate(thuoc.getNgaySanXuat()); // Thêm dòng này để hiển thị ngày sản xuất
		txtHanSuDung.setDate(thuoc.getHanSuDung());
		cboxLoaiThuoc.setSelectedItem(thuoc.getLoaiThuoc());

	}

	private boolean isValidateFields() {
		if (Validation.isEmpty(txtTenThuoc.getText().trim())) {
			MessageDialog.warring(this, "Tên thuốc không được rỗng!");
			txtTenThuoc.requestFocus();
			return false;
		}

		if (txtHinhAnh.getIcon() == null) {
			MessageDialog.warring(this, "Hình ảnh không được rỗng!");
			txtHinhAnh.requestFocus();
			return false;
		}

		if (Validation.isEmpty(txtThanhPhan.getText().trim())) {
			MessageDialog.warring(this, "Thành phần không được để trống!");
			txtThanhPhan.requestFocus();
			return false;
		}

		if (Validation.isEmpty(txtSoLuong.getText().trim())) {
			MessageDialog.warring(this, "Số lượng không để trống!");
			txtSoLuong.requestFocus();
			return false;
		} else {
			try {
				int sl = Integer.parseInt(txtSoLuong.getText());
				if (sl < 0) {
					MessageDialog.warring(this, "Số lượng phải >= 0");
					txtSoLuong.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				MessageDialog.warring(this, "Số lượng phải là số!");
				txtSoLuong.requestFocus();
				return false;
			}
		}

		if (Validation.isEmpty(txtGiaNhap.getText().trim())) {
			MessageDialog.warring(this, "Giá nhập không được để trống!");
			txtGiaNhap.requestFocus();
			return false;
		} else {
			try {
				double gn = Double.parseDouble(txtGiaNhap.getText());
				if (gn < 0) {
					MessageDialog.warring(this, "Giá nhập phải >= 0");
					txtGiaNhap.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				MessageDialog.warring(this, "Giá nhập phải là số!");
				txtGiaNhap.requestFocus();
				return false;
			}
		}

		if (Validation.isEmpty(txtDonGia.getText().trim())) {
			MessageDialog.warring(this, "Đơn giá không được để trống!");
			txtDonGia.requestFocus();
			return false;
		} else {
			try {
				double gn = Double.parseDouble(txtGiaNhap.getText());
				double dg = Double.parseDouble(txtDonGia.getText());
				if (dg < 0) {
					MessageDialog.warring(this, "Đơn giá phải >= 0");
					txtDonGia.requestFocus();
					return false;
				} else if (dg < gn) {
					MessageDialog.warring(this, "Đon giá phải lớn hơn giá nhập!");
					txtDonGia.requestFocus();
					return false;
				}
			} catch (NumberFormatException e) {
				MessageDialog.warring(this, "Đơn giá phải là số!");
				txtDonGia.requestFocus();
				return false;
			}
		}

		if (txtNgaySanXuat_1.getDate() == null || !txtNgaySanXuat_1.getDateFormatString().equals("dd/MM/yyyy")) {
			MessageDialog.warring(this, "Ngày sản xuất không được để trống và có kiểu dd/MM/yyyy");
			return false;
		} else if (txtNgaySanXuat_1.getDate().after(new Date())) {
			MessageDialog.warring(this, "Ngày sản xuất phải trước ngày hiện tại");
			return false;
		}

		if (txtHanSuDung.getDate() == null || !txtHanSuDung.getDateFormatString().equals("dd/MM/yyyy")) {
			MessageDialog.warring(this, "Hạn sử dụng không được để trống và có kiểu dd/MM/yyyy");
			return false;
		} else if (txtHanSuDung.getDate().before(txtHanSuDung.getDate())) {
			MessageDialog.warring(this, "Hạn sử dụng phải sau ngày sản xuất");
			return false;
		}

		return true;
	}

	private Thuoc getInputFields() {
		String id = thuoc.getId();
		String tenThuoc = txtTenThuoc.getText().trim();
		byte[] hinhAnh = thuocImage;
		String thanhPhan = txtThanhPhan.getText().trim();
		String idDVT = listDVT.get(cboxDonViTinh.getSelectedIndex()).getId();
		DonViTinh donViTinh = new DonViTinhController().selectById(idDVT);
		String idDM = listDM.get(cboxDanhMuc.getSelectedIndex()).getId();
		DanhMuc danhMuc = new DanhMucController().selectById(idDM);
		String idXX = listXX.get(cboxXuatXu.getSelectedIndex()).getId();
		XuatXu xuatXu = new XuatXuController().selectById(idXX);
		int soLuongTon = Integer.parseInt(txtSoLuong.getText());
		double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
		double donGia = Double.parseDouble(txtDonGia.getText().trim());
		String loaiThuoc = cboxLoaiThuoc.getSelectedItem().toString();
		// Lấy giá trị ngày sản xuất và hạn sử dụng
		Date ngaySanXuat = txtNgaySanXuat_1.getDate();
		Date hanSuDung = txtHanSuDung.getDate();

		System.out.println("Ngày sản xuất: " + txtNgaySanXuat_1.getDate());
		System.out.println("Hạn sử dụng: " + txtHanSuDung.getDate());

		// Kiểm tra xem ngày sản xuất có được chọn không
		if (ngaySanXuat == null) {
			MessageDialog.warring(this, "Ngày sản xuất không được để trống!");
			return null;
		}

		// Kiểm tra xem hạn sử dụng có được chọn không
		if (hanSuDung == null) {
			MessageDialog.warring(this, "Hạn sử dụng không được để trống!");
			return null;
		} else if (hanSuDung.before(new Date())) {
			MessageDialog.warring(this, "Hạn sử dụng phải sau ngày hiện tại!");
			return null;
		}
		// Kiểm tra xem ngày sản xuất có trước hạn sử dụng không
		if (ngaySanXuat.after(hanSuDung)) {
			MessageDialog.warring(this, "Ngày sản xuất phải trước hạn sử dụng!");
			return null;
		}

		// Nếu tất cả các kiểm tra đều hợp lệ, trả về đối tượng Thuoc
		return new Thuoc(id, tenThuoc, hinhAnh, thanhPhan, donViTinh, danhMuc, xuatXu, soLuongTon, giaNhap, donGia,
				ngaySanXuat, hanSuDung, loaiThuoc);
	}

	private void initComponents() {

		jPanel15 = new JPanel();
		jLabel8 = new JLabel();
		jPanel3 = new JPanel();
		jPanel4 = new JPanel();
		txtHinhAnh = new JLabel();
		btnChooseImage = new JButton();
		jPanel2 = new JPanel();
		jPanel1 = new JPanel();
		jPanel18 = new JPanel();
		lblHoTen = new JLabel();
		txtTenThuoc = new JTextField();
		jPanel19 = new JPanel();
		jLabel12 = new JLabel();
		jScrollPane1 = new JScrollPane();
		txtThanhPhan = new JTextArea();
		jPanel21 = new JPanel();
		jLabel14 = new JLabel();
		cboxDanhMuc = new JComboBox<>();
		jPanel23 = new JPanel();
		jLabel16 = new JLabel();
		cboxXuatXu = new JComboBox<>();
		jPanel24 = new JPanel();
		jLabel17 = new JLabel();
		cboxDonViTinh = new JComboBox<>();
		jPanel22 = new JPanel();
		jLabel15 = new JLabel();
		txtNgaySanXuat_1 = new JDateChooser();
		txtHanSuDung = new JDateChooser();

		jPanel26 = new JPanel();
		jLabel19 = new JLabel();
		txtGiaNhap = new JTextField();
		jPanel25 = new JPanel();
		jLabel18 = new JLabel();
		txtDonGia = new JTextField();
		jPanel20 = new JPanel();
		jLabel13 = new JLabel();
		txtSoLuong = new JTextField();
		jPanel8 = new JPanel();
		btnHuy = new JButton();
		btnAdd = new JButton();
		jLabel28 = new JLabel();
		jPanel28 = new JPanel();
		cboxLoaiThuoc = new JComboBox<>();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(1200, 750));

		jPanel15.setBackground(new Color(0, 153, 153));
		jPanel15.setMinimumSize(new Dimension(100, 60));
		jPanel15.setPreferredSize(new Dimension(500, 50));
		jPanel15.setLayout(new BorderLayout());

		jLabel8.setFont(new Font("Roboto Medium", 0, 18));
		jLabel8.setForeground(new Color(255, 255, 255));
		jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel8.setText("CẬP NHẬT THUỐC");
		jLabel8.setPreferredSize(new Dimension(149, 40));
		jPanel15.add(jLabel8, BorderLayout.CENTER);

		getContentPane().add(jPanel15, BorderLayout.NORTH);

		jPanel3.setBackground(new Color(255, 255, 255));
		jPanel3.setPreferredSize(new Dimension(400, 100));

		jPanel4.setBackground(new Color(255, 255, 255));
		jPanel4.setBorder(new LineBorder(new Color(237, 237, 237), 2, true));
		jPanel4.setPreferredSize(new Dimension(300, 300));
		jPanel4.setLayout(new BorderLayout());

		txtHinhAnh.setBackground(new Color(255, 255, 255));
		txtHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
		txtHinhAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
		txtHinhAnh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		txtHinhAnh.setPreferredSize(new Dimension(200, 100));
		jPanel4.add(txtHinhAnh, BorderLayout.CENTER);

		btnChooseImage.setText("Chọn ảnh");
		btnChooseImage.setPreferredSize(new Dimension(100, 30));
		btnChooseImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnChooseImageActionPerformed(evt);
			}
		});

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(
						jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap(34, Short.MAX_VALUE)
										.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 350,
												GroupLayout.PREFERRED_SIZE)
										.addGap(16, 16, 16))
								.addGroup(jPanel3Layout.createSequentialGroup().addGap(150, 150, 150)
										.addComponent(btnChooseImage, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel3Layout.createSequentialGroup().addContainerGap(105, Short.MAX_VALUE)
						.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnChooseImage,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(103, 103, 103)));

		getContentPane().add(jPanel3, BorderLayout.WEST);

		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel2.setPreferredSize(new Dimension(750, 550));
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 16));

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setPreferredSize(new Dimension(750, 550));
		jPanel1.setLayout(new GridLayout(6, 2, 16, 8));

		jPanel18.setBackground(new Color(255, 255, 255));
		jPanel18.setPreferredSize(new Dimension(150, 40));
		jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		lblHoTen.setFont(new Font("Roboto", 0, 14));
		lblHoTen.setText("Tên thuốc");
		lblHoTen.setMaximumSize(new Dimension(44, 40));
		lblHoTen.setPreferredSize(new Dimension(150, 40));
		jPanel18.add(lblHoTen);

		txtTenThuoc.setFont(new Font("Roboto", 0, 14));
		txtTenThuoc.setToolTipText("");
		txtTenThuoc.setPreferredSize(new Dimension(300, 40));
		jPanel18.add(txtTenThuoc);

		jPanel1.add(jPanel18);

		jPanel19.setBackground(new Color(255, 255, 255));
		jPanel19.setPreferredSize(new Dimension(150, 40));
		jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel12.setFont(new Font("Roboto", 0, 14));
		jLabel12.setText("Thành phần");
		jLabel12.setMaximumSize(new Dimension(44, 40));
		jLabel12.setPreferredSize(new Dimension(150, 40));
		jPanel19.add(jLabel12);

		jScrollPane1.setMaximumSize(new Dimension(300, 70));
		jScrollPane1.setPreferredSize(new Dimension(300, 50));

		txtThanhPhan.setColumns(20);
		txtThanhPhan.setFont(new Font("Roboto", 0, 14));
		txtThanhPhan.setLineWrap(true);
		txtThanhPhan.setRows(4);
		txtThanhPhan.setPreferredSize(new Dimension(252, 50));
		jScrollPane1.setViewportView(txtThanhPhan);

		jPanel19.add(jScrollPane1);

		jPanel1.add(jPanel19);

		jPanel21.setBackground(new Color(255, 255, 255));
		jPanel21.setPreferredSize(new Dimension(150, 40));
		jPanel21.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel14.setFont(new Font("Roboto", 0, 14));
		jLabel14.setText("Danh mục");
		jLabel14.setMaximumSize(new Dimension(44, 40));
		jLabel14.setPreferredSize(new Dimension(150, 40));
		jPanel21.add(jLabel14);

		cboxDanhMuc.setPreferredSize(new Dimension(300, 40));
		jPanel21.add(cboxDanhMuc);

		jPanel1.add(jPanel21);

		jPanel28.setBackground(new Color(255, 255, 255));
		jPanel28.setPreferredSize(new Dimension(150, 40));
		jPanel28.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel28.setFont(new Font("Roboto", 0, 14));
		jLabel28.setText("Loại Thuốc");
		jLabel28.setMaximumSize(new Dimension(44, 40));
		jLabel28.setPreferredSize(new Dimension(150, 40));
		jPanel28.add(jLabel28);

		cboxLoaiThuoc.setPreferredSize(new Dimension(300, 40));
		jPanel28.add(cboxLoaiThuoc);

		jPanel1.add(jPanel28); // Thêm jPanel28 vào cuối jPanel1

		jPanel23.setBackground(new Color(255, 255, 255));
		jPanel23.setPreferredSize(new Dimension(150, 40));
		jPanel23.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel16.setFont(new Font("Roboto", 0, 14));
		jLabel16.setText("Xuất xứ");
		jLabel16.setMaximumSize(new Dimension(44, 40));
		jLabel16.setPreferredSize(new Dimension(150, 40));
		jPanel23.add(jLabel16);

		cboxXuatXu.setPreferredSize(new Dimension(300, 40));
		jPanel23.add(cboxXuatXu);

		jPanel1.add(jPanel23);

		jPanel24.setBackground(new Color(255, 255, 255));
		jPanel24.setPreferredSize(new Dimension(150, 40));
		jPanel24.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel17.setFont(new Font("Roboto", 0, 14));
		jLabel17.setText("Đơn vị tính");
		jLabel17.setMaximumSize(new Dimension(44, 40));
		jLabel17.setPreferredSize(new Dimension(150, 40));
		jPanel24.add(jLabel17);

		cboxDonViTinh.setPreferredSize(new Dimension(300, 40));
		jPanel24.add(cboxDonViTinh);

		jPanel1.add(jPanel24);

		jPanel22_1 = new JPanel();
		jPanel22_1.setPreferredSize(new Dimension(500, 40));
		jPanel22_1.setBackground(Color.WHITE);
		jPanel1.add(jPanel22_1);
		jPanel22_1.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		lblNgySnXut = new JLabel();
		lblNgySnXut.setText("Ngày sản xuất");
		lblNgySnXut.setPreferredSize(new Dimension(150, 40));
		lblNgySnXut.setMaximumSize(new Dimension(44, 40));
		lblNgySnXut.setFont(new Font("Dialog", Font.PLAIN, 14));
		jPanel22_1.add(lblNgySnXut);

		txtNgaySanXuat_1 = new JDateChooser();
		txtNgaySanXuat_1.setPreferredSize(new Dimension(300, 40));
		txtNgaySanXuat_1.setDateFormatString("dd/MM/yyyy");
		txtNgaySanXuat_1.setBackground(Color.WHITE);
		jPanel22_1.add(txtNgaySanXuat_1);

		jPanel22.setBackground(new Color(255, 255, 255));
		jPanel22.setPreferredSize(new Dimension(500, 40));
		jPanel22.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jPanel26.setBackground(new Color(255, 255, 255));
		jPanel26.setPreferredSize(new Dimension(500, 40));
		jPanel26.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel19.setFont(new Font("Roboto", 0, 14));
		jLabel19.setText("Giá nhập");
		jLabel19.setMaximumSize(new Dimension(44, 40));
		jLabel19.setPreferredSize(new Dimension(150, 40));
		jPanel26.add(jLabel19);

		txtGiaNhap.setFont(new Font("Roboto", 0, 14));
		txtGiaNhap.setPreferredSize(new Dimension(300, 40));
		jPanel26.add(txtGiaNhap);

		jPanel1.add(jPanel26);

		jLabel15.setFont(new Font("Roboto", 0, 14));
		jLabel15.setText("Hạn sử dụng");
		jLabel15.setMaximumSize(new Dimension(44, 40));
		jLabel15.setPreferredSize(new Dimension(150, 40));
		jPanel22.add(jLabel15);

		txtHanSuDung.setBackground(new Color(255, 255, 255));
		txtHanSuDung.setDateFormatString("dd/MM/yyyy");
		txtHanSuDung.setPreferredSize(new Dimension(300, 40));
		jPanel22.add(txtHanSuDung);

		jPanel1.add(jPanel22);

		jPanel25.setBackground(new Color(255, 255, 255));
		jPanel25.setPreferredSize(new Dimension(500, 40));
		jPanel25.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jPanel20.setBackground(new Color(255, 255, 255));
		jPanel20.setPreferredSize(new Dimension(500, 40));
		jPanel20.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

		jLabel13.setFont(new Font("Roboto", 0, 14));
		jLabel13.setText("Số lượng");
		jLabel13.setMaximumSize(new Dimension(44, 40));
		jLabel13.setPreferredSize(new Dimension(150, 40));
		jPanel20.add(jLabel13);

		txtSoLuong.setFont(new Font("Roboto", 0, 14));
		txtSoLuong.setPreferredSize(new Dimension(300, 40));
		txtSoLuong.setEnabled(false); // Vô hiệu hóa
		// Hoặc
		txtSoLuong.setEditable(false); // Chỉ cho phép đọc
		jPanel20.add(txtSoLuong);

		jPanel1.add(jPanel20);
		jLabel18.setFont(new Font("Roboto", 0, 14));
		jLabel18.setText("Đơn giá");
		jLabel18.setMaximumSize(new Dimension(44, 40));
		jLabel18.setPreferredSize(new Dimension(150, 40));
		jPanel25.add(jLabel18);

		txtDonGia.setFont(new Font("Roboto", 0, 14));
		txtDonGia.setPreferredSize(new Dimension(300, 40));
		jPanel25.add(txtDonGia);

		jPanel1.add(jPanel25);

		jPanel2.add(jPanel1);

		getContentPane().add(jPanel2, BorderLayout.CENTER);

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
		btnAdd.setText("CẬP NHẬT");
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
			Thuoc e = getInputFields();
			THUOC_CON.update(e); // Cập nhật thuốc trong cơ sở dữ liệu
			this.dispose(); // Đóng cửa sổ
			MessageDialog.info(this, "Cập nhật thành công!");

			// Kiểm tra đối tượng nào đang tồn tại và gọi phương thức làm mới bảng tương ứng
			if (THUOC_GUI != null) {
				THUOC_GUI.loadTable(THUOC_CON.getAllList()); // Làm mới bảng thuốc trong ThuocPage
			} else if (PN_GUI != null) {
				PN_GUI.tableThuocLayout(); // Làm mới bảng thuốc trong CreatePhieuNhapPage
			}
		}
	}

	private void btnChooseImageActionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String filename = selectedFile.getAbsolutePath();
			ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage()
					.getScaledInstance(txtHinhAnh.getWidth(), txtHinhAnh.getHeight(), Image.SCALE_SMOOTH));
			txtHinhAnh.setIcon(imageIcon);

			try {
				FileInputStream fis = new FileInputStream(new File(filename));
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				for (int readNum; (readNum = fis.read(buf)) != -1;) {
					bos.write(buf, 0, readNum);
				}
				thuocImage = bos.toByteArray();

			} catch (FileNotFoundException ex) {
				MessageDialog.error(this, "Lỗi nhập file!");
			} catch (IOException ex) {
				MessageDialog.error(this, "Lỗi nhập file!");
			}
		}
	}

	private JButton btnAdd;
	private JButton btnChooseImage;
	private JButton btnHuy;
	private JComboBox<String> cboxDanhMuc;
	private JComboBox<String> cboxDonViTinh;
	private JComboBox<String> cboxXuatXu;
	private JComboBox<String> cboxLoaiThuoc;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private JLabel jLabel14;
	private JLabel jLabel15;
	private JLabel jLabel28;
	private JLabel jLabel16;
	private JLabel jLabel17;
	private JLabel jLabel18;
	private JLabel jLabel19;
	private JLabel jLabel8;
	private JPanel jPanel1;
	private JPanel jPanel15;
	private JPanel jPanel18;
	private JPanel jPanel19;
	private JPanel jPanel2;
	private JPanel jPanel20;
	private JPanel jPanel21;
	private JPanel jPanel22;
	private JPanel jPanel23;
	private JPanel jPanel24;
	private JPanel jPanel25;
	private JPanel jPanel26;
	private JPanel jPanel28;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel8;
	private JScrollPane jScrollPane1;
	private JLabel lblHoTen;
	private JTextField txtDonGia;
	private JTextField txtGiaNhap;
	private JDateChooser txtNgaySanXuat_1;
	private JDateChooser txtHanSuDung;
	private JLabel txtHinhAnh;
	private JTextField txtSoLuong;
	private JTextField txtTenThuoc;
	private JTextArea txtThanhPhan;
	private JPanel jPanel22_1;
	private JLabel lblNgySnXut;
}

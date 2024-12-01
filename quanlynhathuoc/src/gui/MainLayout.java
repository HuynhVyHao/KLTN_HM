package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.TaiKhoanController;
import entity.TaiKhoan;
import gui.dialog.CreateKhachHangDialog;
import gui.dialog.InfoDialog;
import gui.page.BaoCaoHoaDonPage;
import gui.page.BaoCaoKhachHangPage;
import gui.page.BaoCaoNhaCungCapPage;
import gui.page.BaoCaoNhanVienPage;
import gui.page.BaoCaoPhieuNhapPage;
import gui.page.BaoCaoTaiKhoanPage;
import gui.page.BaoCaoThuocPage;
import gui.page.BaoCaoVaiTroPage;
import gui.page.ChiTietThuocPage;
import gui.page.CreateDatHangPage;
import gui.page.DanhMucPage;
import gui.page.DatHangPage;
import gui.page.HoaDonPage;
import gui.page.KhachHangPage;
import gui.page.NhaCungCapPage;
import gui.page.NhanVienPage;
import gui.page.PhieuNhapPage;
import gui.page.ThuocPage;
import gui.page.TimKiemHoaDonPage;
import gui.page.TimKiemKhachHangPage;
import gui.page.TimKiemNhaCungCapPage;
import gui.page.TimKiemNhanVienPage;
import gui.page.TimKiemPhieuNhapPage;
import gui.page.TimKiemTaiKhoanPage;
import gui.page.TimKiemThuocPage;
import gui.page.TimKiemVaiTroPage;
import gui.page.TaiKhoanPage;
import gui.page.thongke.ThongKePage;
import gui.page.VaiTroPage;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import utils.MessageDialog;

public class MainLayout extends javax.swing.JFrame {

	private ThuocPage thuoc;
	private TimKiemThuocPage timKiemThuoc;
	private BaoCaoThuocPage baoCaoThuoc;
	private ChiTietThuocPage chiTietThuoc;
	private DanhMucPage danhMuc;
	private HoaDonPage hoaDon;
	private TimKiemHoaDonPage timKiemHoaDonPage;
	private BaoCaoHoaDonPage baoCaoHoaDonPage;
	private NhaCungCapPage nhaCungCap;
	private TimKiemNhaCungCapPage timKiemNhaCungCap;
	private BaoCaoNhaCungCapPage baoCaoNhaCungCap;
	private KhachHangPage khachHang;
	private TimKiemKhachHangPage timKiemKhachHang;
	private BaoCaoKhachHangPage baoCaoKhachHang;
	private NhanVienPage nhanVien;
	private TimKiemNhanVienPage timKiemNhanVien;
	private BaoCaoNhanVienPage baoCaoNhanVien;
//	private CreateDatHangPage datHangNhanVien;
	private DatHangPage datHangNhanVien;
	private TaiKhoanPage taiKhoan;
	private TimKiemTaiKhoanPage timKiemTaiKhoan;
	private BaoCaoTaiKhoanPage baoCaoTaiKhoan;
	private VaiTroPage vaiTro;
	private TimKiemVaiTroPage timKiemVaiTro;
	private BaoCaoVaiTroPage baoCaoVaiTro;
	private PhieuNhapPage phieuNhap;
	private TimKiemPhieuNhapPage timKiemPhieuNhap;
	private BaoCaoPhieuNhapPage baoCaoPhieuNhap;
	private ThongKePage thongke;
	private boolean isMenuVisible;

	public TaiKhoan tk;

	private List<JButton> listItem;

	Color ACTIVE_BACKGROUND_COLOR = new Color(195, 240, 235);

	public MainLayout() {
		initComponents();
		fillInfo();
		sideBarLayout();
	}

	public MainLayout(TaiKhoan tk) {
		this.tk = tk;
		initComponents();
		fillInfo();
		sideBarLayout();
	}

	public void setPanel(JPanel pn) {
		mainContent.removeAll();
		mainContent.add(pn).setVisible(true);
		mainContent.repaint();
		mainContent.validate();
	}

	public final void fillInfo() {
		tk = new TaiKhoanController().selectById(tk.getId());

		if (tk.getNhanVien().getGioiTinh().equals("Nam")) {
			btnInfo.setIcon(new FlatSVGIcon("./icon/man.svg"));
		} else {
			btnInfo.setIcon(new FlatSVGIcon("./icon/woman.svg"));
		}
		txtFullName.setText(tk.getNhanVien().getHoTen());
		txtRole.setText(tk.getVaiTro().getTen());
		checkRole(tk.getVaiTro().getId());
	}

	private void sideBarLayout() {

		// Add list item Sidebar
		listItem = new ArrayList<>();

		listItem.add(hoaDonItem);
		listItem.add(thuocItem);
		listItem.add(danhMucItem);
		listItem.add(thongKeItem);
		listItem.add(phieuNhapItem);
		listItem.add(nhaCungCapItem);
		listItem.add(khachHangItem);
		listItem.add(nhanVienItem);
		listItem.add(taiKhoanItem);
//        listItem.add(phieuDatHang);
		listItem.add(vaiTroItem);

		// Default content
		mainContent.add(new HoaDonPage(this)).setVisible(true);

		// Default selected
		listItem.get(0).setSelected(true);
		listItem.get(0).setBackground(ACTIVE_BACKGROUND_COLOR);

		// Set active item
		for (JButton item : listItem) {
			item.getModel().addChangeListener((ChangeEvent e) -> {
				ButtonModel model = (ButtonModel) e.getSource();

				if (model.isSelected()) {
					item.setBackground(ACTIVE_BACKGROUND_COLOR); // Change color when selected
				}
			});
		}
	}

	private void resetActive() {
		for (JButton item : listItem) {
			item.setSelected(false);
			item.setBackground(Color.WHITE);
		}
	}

	private void checkRole(String role) {
		if (role.equals("nvbh")) {
			phieuNhapItem.setEnabled(false);
			nhaCungCapItem.setEnabled(false);
			thuocItem.setEnabled(false);
			danhMucItem.setEnabled(false);
//			nhanVienItem.setEnabled(false);
			vaiTroItem.setEnabled(false);
			taiKhoanItem.setEnabled(false);
			nguoiDungItem.setEnabled(false);
			
			 capNhatNVItem.setEnabled(false); // Vô hiệu hóa mục Cập Nhật
		        timKiemNVItem.setEnabled(false); // Vô hiệu hóa mục Tìm Kiếm
		        baoCaoNVItem.setEnabled(false);  
		}

		if (role.equals("nvsp")) {
			hoaDonItem.setEnabled(false);
			khachHangItem.setEnabled(false);
			nhanVienItem.setEnabled(false);
			vaiTroItem.setEnabled(false);
			taiKhoanItem.setEnabled(false);
		}

		if (role.equals("nvql")) {
			hoaDonItem.setEnabled(false);
			khachHangItem.setEnabled(false);
			phieuNhapItem.setEnabled(false);
			nhaCungCapItem.setEnabled(false);
			thuocItem.setEnabled(false);
			danhMucItem.setEnabled(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		leftContent = new javax.swing.JPanel();
		infoPanel = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		btnInfo = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		txtFullName = new javax.swing.JLabel();
		txtRole = new javax.swing.JLabel();
		btnLogout = new javax.swing.JButton();
		topMenuPanel = new javax.swing.JPanel();
		itemPanel = new javax.swing.JPanel();
		thongKeItem = new javax.swing.JButton();
		hoaDonItem = new javax.swing.JButton();
		khachHangItem = new javax.swing.JButton();
		jSeparator2 = new javax.swing.JSeparator();
		thuocItem = new javax.swing.JButton();
		danhMucItem = new javax.swing.JButton();
		phieuNhapItem = new javax.swing.JButton();
		nhaCungCapItem = new javax.swing.JButton();
		jSeparator3 = new javax.swing.JSeparator();
		nhanVienItem = new javax.swing.JButton();
		taiKhoanItem = new javax.swing.JButton();
		nguoiDungItem = new javax.swing.JButton();
		vaiTroItem = new javax.swing.JButton();
		mainContent = new javax.swing.JPanel();
		jpMenuNhanVien = new JPopupMenu();
		jpMenuNhanVien = new JPopupMenu();
		jpMenuThongKe = new JPopupMenu();
		jpMenuThuoc = new JPopupMenu();
		jpMenuPhieuNhap = new JPopupMenu();
		jpMenuNhanVien = new JPopupMenu();
		jpMenuTaiKhoan = new JPopupMenu();
		jpMenuVaiTo = new JPopupMenu();
		jpMenuNhanVien = new JPopupMenu();
		jpMenuDanhMuc = new JPopupMenu();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Hệ Thống Quản Lý Nhà Thuốc H&M - Gò Vấp");

		leftContent.setBackground(new java.awt.Color(218, 245, 211));
		leftContent.setPreferredSize(new java.awt.Dimension(250, 800));
		leftContent.setLayout(new java.awt.BorderLayout());

		infoPanel.setBackground(new java.awt.Color(255, 255, 255));
		infoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(242, 242, 242), 2, true));
		infoPanel.setPreferredSize(new java.awt.Dimension(250, 80));
		infoPanel.setLayout(new java.awt.BorderLayout());

		jPanel2.setBackground(new java.awt.Color(255, 255, 255));
		jPanel2.setPreferredSize(new java.awt.Dimension(64, 80));
		jPanel2.setLayout(new java.awt.BorderLayout());

		btnInfo.setFont(new java.awt.Font("Roboto", 1, 14)); 
		btnInfo.setIcon(new FlatSVGIcon("./icon/man.svg"));
		btnInfo.setBorderPainted(false);
		btnInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnInfo.setFocusPainted(false);
		btnInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnInfo.setPreferredSize(new java.awt.Dimension(90, 90));
		btnInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnInfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnInfoActionPerformed(evt);
			}
		});
		jPanel2.add(btnInfo, java.awt.BorderLayout.CENTER);

		infoPanel.add(jPanel2, java.awt.BorderLayout.WEST);

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));

		txtFullName.setFont(new java.awt.Font("Roboto", 1, 14)); 
		txtFullName.setText("Nguyễn Phan Anh Tuấn");

		txtRole.setFont(new java.awt.Font("Roboto Light", 2, 13)); 
		txtRole.setText("Nhân viên Quản lý sản phẩm");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(txtRole, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
								.addComponent(txtFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
						.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout.createSequentialGroup().addGap(18, 18, 18)
										.addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtRole, javax.swing.GroupLayout.PREFERRED_SIZE, 17,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(15, Short.MAX_VALUE)));

		infoPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

		btnLogout.setFont(new java.awt.Font("Roboto Medium", 0, 14)); 
		btnLogout.setIcon(new FlatSVGIcon("./icon/logout.svg"));
		btnLogout.setText("Đăng Xuất");
		btnLogout.setBorderPainted(false);
		btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnLogout.setFocusPainted(false);
		btnLogout.setFocusable(false);
		btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		btnLogout.setIconTextGap(16);
		btnLogout.setPreferredSize(new java.awt.Dimension(226, 46));
		btnLogout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLogoutActionPerformed(evt);
			}
		});

		leftContent.add(infoPanel, java.awt.BorderLayout.NORTH);
		leftContent.add(btnLogout, java.awt.BorderLayout.SOUTH);

		topMenuPanel.setBackground(new java.awt.Color(255, 255, 255));
		topMenuPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(242, 242, 242), 2, true));
		topMenuPanel.setPreferredSize(new java.awt.Dimension(1130, 80));
		topMenuPanel.setLayout(new java.awt.BorderLayout());

		itemPanel.setBackground(new java.awt.Color(255, 255, 255));
		itemPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 8, true));
		itemPanel.setPreferredSize(new java.awt.Dimension(1130, 80));
		itemPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

//-----------------HÓA ĐƠN--------------------------------------------
		// Tạo menu thả xuống JPopupMenu
		jpMenuHD = new JPopupMenu();
		jpMenuHD.setPreferredSize(new java.awt.Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuHD.setBorderPainted(false);

		jpMenuHD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatHDItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt
																												// kích
																												// thước
																												// biểu
		capNhatHDItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatHDItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatHDItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		capNhatHDItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				hoaDonItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemHDItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemHDItem.setIconTextGap(8);
		timKiemHDItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemHDItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		timKiemHDItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemHoaDonItemActionPerformed(evt);
			}
		});

		JMenuItem baoCaoHDItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
		baoCaoHDItem.setIconTextGap(8);
		baoCaoHDItem.setMargin(new Insets(5, 10, 5, 10));
		baoCaoHDItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		baoCaoHDItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				baoCaoHoaDonItemActionPerformed(evt);
			}
		});

		jpMenuHD.add(capNhatHDItem);
		jpMenuHD.add(new JSeparator());
		jpMenuHD.add(timKiemHDItem);
		jpMenuHD.add(new JSeparator());
		jpMenuHD.add(baoCaoHDItem);

		hoaDonItem.setFont(new java.awt.Font("Roboto Medium", 0, 12)); 
		hoaDonItem.setIcon(new FlatSVGIcon("./icon/bill.svg"));
		hoaDonItem.setText("Hóa đơn");
		hoaDonItem.setBorderPainted(false);
		hoaDonItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		hoaDonItem.setFocusPainted(false);
		hoaDonItem.setFocusable(false);
		hoaDonItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		hoaDonItem.setIconTextGap(10);
		hoaDonItem.setPreferredSize(new java.awt.Dimension(130, 60));
		isMenuVisible = false;
		hoaDonItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if (isMenuVisible) {
					// Nếu menu đang hiện, ẩn nó đi
					jpMenuHD.setVisible(false);
					isMenuVisible = false; // Cập nhật trạng thái
				} else {
					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
					int x = hoaDonItem.getLocationOnScreen().x - hoaDonItem.getParent().getLocationOnScreen().x;
					int y = hoaDonItem.getLocationOnScreen().y - hoaDonItem.getParent().getLocationOnScreen().y
							+ hoaDonItem.getHeight();

					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
					jpMenuHD.show(hoaDonItem.getParent(), x, y);
					isMenuVisible = true; // Cập nhật trạng thái
				}
			}

		});
		itemPanel.add(hoaDonItem);

//-----------------KHÁCH HÀNG--------------------------------------------

		// Tạo menu thả xuống JPopupMenu
		jpMenuKH = new JPopupMenu();
		jpMenuKH.setPreferredSize(new java.awt.Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuKH.setBorderPainted(false);

		jpMenuKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt kích
																											// thước
																											// biểu
		capNhatItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		capNhatItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				khachHangItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemItem.setIconTextGap(8);
		timKiemItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		timKiemItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemKhachHangItemActionPerformed(evt);
			}
		});

		JMenuItem baoCaoItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
		baoCaoItem.setIconTextGap(8);
		baoCaoItem.setMargin(new Insets(5, 10, 5, 10));
		baoCaoItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		baoCaoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				baoCaoKhachHangItemActionPerformed(evt);
			}
		});

		jpMenuKH.add(capNhatItem);
		jpMenuKH.add(new JSeparator());
		jpMenuKH.add(timKiemItem);
		jpMenuKH.add(new JSeparator());
		jpMenuKH.add(baoCaoItem);

		khachHangItem.setFont(new java.awt.Font("Roboto Medium", 0, 12));
		khachHangItem.setIcon(new FlatSVGIcon("./icon/customer.svg"));
		khachHangItem.setText("Khách hàng");
		khachHangItem.setBorderPainted(false);
		khachHangItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		khachHangItem.setFocusPainted(false);
		khachHangItem.setFocusable(false);
		khachHangItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		khachHangItem.setIconTextGap(10);
		khachHangItem.setPreferredSize(new java.awt.Dimension(140, 60));
		// Tạo biến để lưu trạng thái hiện/ẩn của menu
		isMenuVisible = false; // Ban đầu menu đang ẩn

		khachHangItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isMenuVisible) {
					// Nếu menu đang hiện, ẩn nó đi
					jpMenuKH.setVisible(false);
					isMenuVisible = false; // Cập nhật trạng thái
				} else {
					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
					int x = khachHangItem.getLocationOnScreen().x - khachHangItem.getParent().getLocationOnScreen().x;
					int y = khachHangItem.getLocationOnScreen().y - khachHangItem.getParent().getLocationOnScreen().y
							+ khachHangItem.getHeight();

					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
					jpMenuKH.show(khachHangItem.getParent(), x, y);
					isMenuVisible = true; // Cập nhật trạng thái
				}
			}
		});
		itemPanel.add(khachHangItem);

//-----------------THUỐC--------------------------------------------	
		// Tạo menu thả xuống JPopupMenu
		jpMenuThuoc = new JPopupMenu();
		jpMenuThuoc.setPreferredSize(new java.awt.Dimension(170, 170)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuThuoc.setBorderPainted(false);

		jpMenuThuoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatThuocItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); 
		capNhatThuocItem.setIconTextGap(8);// Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatThuocItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatThuocItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		capNhatThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				thuocItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemThuocItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemThuocItem.setIconTextGap(8);
		timKiemThuocItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemThuocItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		timKiemThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemThuocItemActionPerformed(evt);
			}
		});

		JMenuItem baoCaoThuocItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
		baoCaoThuocItem.setIconTextGap(8);
		baoCaoThuocItem.setMargin(new Insets(5, 10, 5, 10));
		baoCaoThuocItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		baoCaoThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				baoCaoThuocItemActionPerformed(evt);
			}
		});

		JMenuItem chiTietThuocItem = new JMenuItem("Chi Tiết", new FlatSVGIcon("./icon/listCT.svg", 24, 24));
		chiTietThuocItem.setIconTextGap(8);
		chiTietThuocItem.setMargin(new Insets(5, 10, 5, 10));
		chiTietThuocItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		chiTietThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				chiTietThuocItemActionPerformed(evt);
			}
		});
		
		JMenuItem danhMucThuocItem = new JMenuItem("Danh Mục", new FlatSVGIcon("./icon/categories.svg", 24, 24));
		danhMucThuocItem.setIconTextGap(8);
		danhMucThuocItem.setMargin(new Insets(5, 10, 5, 10));
		danhMucThuocItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		danhMucThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				danhMucItemActionPerformed(evt);
			}
		});

		jpMenuThuoc.add(capNhatThuocItem);
		jpMenuThuoc.add(new JSeparator());
		jpMenuThuoc.add(timKiemThuocItem);
		jpMenuThuoc.add(new JSeparator());
		jpMenuThuoc.add(baoCaoThuocItem);
		jpMenuThuoc.add(new JSeparator());
		jpMenuThuoc.add(chiTietThuocItem);
		jpMenuThuoc.add(new JSeparator());
		jpMenuThuoc.add(danhMucThuocItem);

		thuocItem.setFont(new java.awt.Font("Roboto Medium", 0, 12)); 
		thuocItem.setIcon(new FlatSVGIcon("./icon/medicine.svg"));
		thuocItem.setText("Thuốc");
		thuocItem.setBorderPainted(false);
		thuocItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		thuocItem.setFocusPainted(false);
		thuocItem.setFocusable(false);
		thuocItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		thuocItem.setIconTextGap(10);
		thuocItem.setPreferredSize(new java.awt.Dimension(120, 60));
		thuocItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isMenuVisible) {
					// Nếu menu đang hiện, ẩn nó đi
					jpMenuThuoc.setVisible(false);
					isMenuVisible = false; // Cập nhật trạng thái
				} else {
					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
					int x = thuocItem.getLocationOnScreen().x - thuocItem.getParent().getLocationOnScreen().x;
					int y = thuocItem.getLocationOnScreen().y - thuocItem.getParent().getLocationOnScreen().y
							+ thuocItem.getHeight();

					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
					jpMenuThuoc.show(thuocItem.getParent(), x, y);
					isMenuVisible = true; // Cập nhật trạng thái
				}
			}
		});
		itemPanel.add(thuocItem);

//-----------------DANH MỤC--------------------------------------------		
//		// Tạo menu thả xuống JPopupMenu
//		jpMenuDanhMuc = new JPopupMenu();
//		jpMenuDanhMuc.setPreferredSize(new java.awt.Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
//		jpMenuDanhMuc.setBorderPainted(false);
//
//		jpMenuPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//		// Tạo các item trong menu với biểu tượng và văn bản
//		JMenuItem capNhatDMItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt
//		capNhatDMItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
//		capNhatDMItem.setMargin(new Insets(5, 10, 5, 10));
//		capNhatDMItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
//		capNhatDMItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				danhMucItemActionPerformed(evt);
//			}
//		});
//
//		JMenuItem timKiemDMItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
//		timKiemDMItem.setIconTextGap(8);
//		timKiemDMItem.setMargin(new Insets(5, 10, 5, 10));
//		timKiemDMItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
//		timKiemDMItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				timKiemDanhMucItemActionPerformed(evt);
//			}
//		});
//
//		jpMenuDanhMuc.add(capNhatDMItem);
//		jpMenuDanhMuc.add(new JSeparator());
//		jpMenuDanhMuc.add(timKiemDMItem);
//
//		danhMucItem.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
//		danhMucItem.setIcon(new FlatSVGIcon("./icon/categories.svg"));
//		danhMucItem.setText("Danh Mục");
//		danhMucItem.setBorderPainted(false);
//		danhMucItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//		danhMucItem.setFocusPainted(false);
//		danhMucItem.setFocusable(false);
//		danhMucItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
//		danhMucItem.setIconTextGap(10);
//		danhMucItem.setPreferredSize(new java.awt.Dimension(130, 60));
//		danhMucItem.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				if (isMenuVisible) {
//					// Nếu menu đang hiện, ẩn nó đi
//					jpMenuDanhMuc.setVisible(false);
//					isMenuVisible = false; // Cập nhật trạng thái
//				} else {
//					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
//					int x = danhMucItem.getLocationOnScreen().x - danhMucItem.getParent().getLocationOnScreen().x;
//					int y = danhMucItem.getLocationOnScreen().y - danhMucItem.getParent().getLocationOnScreen().y
//							+ danhMucItem.getHeight();
//
//					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
//					jpMenuDanhMuc.show(danhMucItem.getParent(), x, y);
//					isMenuVisible = true; // Cập nhật trạng thái
//				}
//			}
//		});
//		itemPanel.add(danhMucItem);

//-----------------THỐNG KÊ--------------------------------------------
//
//		thongKeItem.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
//		thongKeItem.setIcon(new FlatSVGIcon("./icon/statistics.svg"));
//		thongKeItem.setText("Thống Kê");
//		thongKeItem.setBorderPainted(false);
//		thongKeItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//		thongKeItem.setFocusPainted(false);
//		thongKeItem.setFocusable(false);
//		thongKeItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
//		thongKeItem.setIconTextGap(10);
//		thongKeItem.setPreferredSize(new java.awt.Dimension(140, 60));
//		thongKeItem.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				thongKeItemActionPerformed(evt);
//			}
//		});
//		itemPanel.add(thongKeItem);

//-----------------PHIẾU NHẬP--------------------------------------------		
		// Tạo menu thả xuống JPopupMenu
		jpMenuPhieuNhap = new JPopupMenu();
		jpMenuPhieuNhap.setPreferredSize(new java.awt.Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuPhieuNhap.setBorderPainted(false);

		jpMenuPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatPNItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt
																												// kích
		capNhatPNItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatPNItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatPNItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		capNhatPNItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				phieuNhapItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemPNItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemPNItem.setIconTextGap(8);
		timKiemPNItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemPNItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		timKiemPNItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemPhieuNhapItemActionPerformed(evt);
			}
		});

		JMenuItem baoCaoPNItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
		baoCaoPNItem.setIconTextGap(8);
		baoCaoPNItem.setMargin(new Insets(5, 10, 5, 10));
		baoCaoPNItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		baoCaoPNItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				baoCaoPhieuNhapItemActionPerformed(evt);
			}
		});

		jpMenuPhieuNhap.add(capNhatPNItem);
		jpMenuPhieuNhap.add(new JSeparator());
		jpMenuPhieuNhap.add(timKiemPNItem);
		jpMenuPhieuNhap.add(new JSeparator());
		jpMenuPhieuNhap.add(baoCaoPNItem);

		phieuNhapItem.setFont(new java.awt.Font("Roboto Medium", 0, 12));
		phieuNhapItem.setIcon(new FlatSVGIcon("./icon/bill-import.svg"));
		phieuNhapItem.setText("Phiếu nhập");
		phieuNhapItem.setBorderPainted(false);
		phieuNhapItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		phieuNhapItem.setFocusPainted(false);
		phieuNhapItem.setFocusable(false);
		phieuNhapItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		phieuNhapItem.setIconTextGap(10);
		phieuNhapItem.setPreferredSize(new java.awt.Dimension(150, 60));
		phieuNhapItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isMenuVisible) {
					// Nếu menu đang hiện, ẩn nó đi
					jpMenuPhieuNhap.setVisible(false);
					isMenuVisible = false; // Cập nhật trạng thái
				} else {
					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
					int x = phieuNhapItem.getLocationOnScreen().x - phieuNhapItem.getParent().getLocationOnScreen().x;
					int y = phieuNhapItem.getLocationOnScreen().y - phieuNhapItem.getParent().getLocationOnScreen().y
							+ phieuNhapItem.getHeight();

					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
					jpMenuPhieuNhap.show(phieuNhapItem.getParent(), x, y);
					isMenuVisible = true; // Cập nhật trạng thái
				}
			}
		});
		itemPanel.add(phieuNhapItem);

//================== NHÀ CUNG CẤP ===============================
		// Tạo menu thả xuống JPopupMenu
		jpMenuNhaCC = new JPopupMenu();
		jpMenuNhaCC.setPreferredSize(new java.awt.Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuNhaCC.setBorderPainted(false);

		jpMenuNhaCC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatNCCItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); 
		capNhatNCCItem.setIconTextGap(8);// Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatNCCItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatNCCItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		capNhatNCCItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nhaCungCapItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemNCCItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemNCCItem.setIconTextGap(8);
		timKiemNCCItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemNCCItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		timKiemNCCItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemNhaCungCapItemActionPerformed(evt);
			}
		});

		JMenuItem baoCaoNCCItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
		baoCaoNCCItem.setIconTextGap(8);
		baoCaoNCCItem.setMargin(new Insets(5, 10, 5, 10));
		baoCaoNCCItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		baoCaoNCCItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				baoCaoNhaCungCapItemActionPerformed(evt);
			}
		});

		jpMenuNhaCC.add(capNhatNCCItem);
		jpMenuNhaCC.add(new JSeparator());
		jpMenuNhaCC.add(timKiemNCCItem);
		jpMenuNhaCC.add(new JSeparator());
		jpMenuNhaCC.add(baoCaoNCCItem);

		nhaCungCapItem.setFont(new java.awt.Font("Roboto Medium", 0, 12)); 
		nhaCungCapItem.setIcon(new FlatSVGIcon("./icon/trucks.svg"));
		nhaCungCapItem.setText("Nhà cung cấp");
		nhaCungCapItem.setBorderPainted(false);
		nhaCungCapItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		nhaCungCapItem.setFocusPainted(false);
		nhaCungCapItem.setFocusable(false);
		nhaCungCapItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		nhaCungCapItem.setIconTextGap(10);
		nhaCungCapItem.setPreferredSize(new java.awt.Dimension(170, 60));
		nhaCungCapItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isMenuVisible) {
					// Nếu menu đang hiện, ẩn nó đi
					jpMenuNhaCC.setVisible(false);
					isMenuVisible = false; // Cập nhật trạng thái
				} else {
					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
					int x = nhaCungCapItem.getLocationOnScreen().x - nhaCungCapItem.getParent().getLocationOnScreen().x;
					int y = nhaCungCapItem.getLocationOnScreen().y - nhaCungCapItem.getParent().getLocationOnScreen().y
							+ nhaCungCapItem.getHeight();

					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
					jpMenuNhaCC.show(nhaCungCapItem.getParent(), x, y);
					isMenuVisible = true; // Cập nhật trạng thái
				}
			}
		});
		itemPanel.add(nhaCungCapItem);

//================== NHÂN VIÊN ===============================
		jpMenuNhanVien = new JPopupMenu();
		jpMenuNhanVien.setPreferredSize(new java.awt.Dimension(170, 170)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuNhanVien.setBorderPainted(false);
		jpMenuNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		// Tạo các item trong menu với biểu tượng và văn bản
		capNhatNVItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24));
		capNhatNVItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatNVItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatNVItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		capNhatNVItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nhanVienItemActionPerformed(evt);
			}
		});

		 timKiemNVItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemNVItem.setIconTextGap(8);
		timKiemNVItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemNVItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		timKiemNVItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemNhanVienItemActionPerformed(evt);
			}
		});
		
		 baoCaoNVItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
		baoCaoNVItem.setIconTextGap(8);
		baoCaoNVItem.setMargin(new Insets(5, 10, 5, 10));
		baoCaoNVItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		baoCaoNVItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				baoCaoNhanVienItemActionPerformed(evt);
			}
		});
		
		 thongKeNVItem = new JMenuItem("Thống Kê", new FlatSVGIcon("./icon/statistics.svg", 24, 24));
		thongKeNVItem.setIconTextGap(8);
		thongKeNVItem.setMargin(new Insets(5, 10, 5, 10));
		thongKeNVItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		thongKeNVItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				thongKeItemActionPerformed(evt);
			}
		});
		
		 datHangNVItem = new JMenuItem("Đặt Hàng", new FlatSVGIcon("./icon/trolley.svg", 24, 24));
		 datHangNVItem.setIconTextGap(8);
		 datHangNVItem.setMargin(new Insets(5, 10, 5, 10));
		 datHangNVItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
		 datHangNVItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					datHangNhanVienItemActionPerformed(evt);
				}
			});

		jpMenuNhanVien.add(capNhatNVItem);
		jpMenuNhanVien.add(new JSeparator());
		jpMenuNhanVien.add(timKiemNVItem);
		jpMenuNhanVien.add(new JSeparator());
		jpMenuNhanVien.add(baoCaoNVItem);
		jpMenuNhanVien.add(new JSeparator());
		jpMenuNhanVien.add(thongKeNVItem);
		jpMenuNhanVien.add(new JSeparator());
		jpMenuNhanVien.add(datHangNVItem);

		nhanVienItem.setFont(new java.awt.Font("Roboto Medium", 0, 12)); 
		nhanVienItem.setIcon(new FlatSVGIcon("./icon/employee.svg"));
		nhanVienItem.setText("Nhân viên");
		nhanVienItem.setBorderPainted(false);
		nhanVienItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		nhanVienItem.setFocusPainted(false);
		nhanVienItem.setFocusable(false);
		nhanVienItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		nhanVienItem.setIconTextGap(10);
		nhanVienItem.setPreferredSize(new java.awt.Dimension(140, 60));
		nhanVienItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if (isMenuVisible) {
					// Nếu menu đang hiện, ẩn nó đi
					jpMenuNhanVien.setVisible(false);
					isMenuVisible = false; // Cập nhật trạng thái
				} else {
					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
					int x = nhanVienItem.getLocationOnScreen().x - nhanVienItem.getParent().getLocationOnScreen().x;
					int y = nhanVienItem.getLocationOnScreen().y - nhanVienItem.getParent().getLocationOnScreen().y
							+ nhanVienItem.getHeight();
					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
					jpMenuNhanVien.show(nhanVienItem.getParent(), x, y);
					isMenuVisible = true; // Cập nhật trạng thái
				}
			}
		});
		itemPanel.add(nhanVienItem);

//================== TÀI KHOẢN ===============================		
//
//		// Tạo menu thả xuống JPopupMenu
//		jpMenuTaiKhoan = new JPopupMenu();
//		jpMenuTaiKhoan.setPreferredSize(new java.awt.Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
//		jpMenuTaiKhoan.setBorderPainted(false);
//
//		jpMenuTaiKhoan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//		// Tạo các item trong menu với biểu tượng và văn bản
//		JMenuItem capNhatTKItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt
//		capNhatTKItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
//		capNhatTKItem.setMargin(new Insets(5, 10, 5, 10));
//		capNhatTKItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
//		capNhatTKItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				taiKhoanItemActionPerformed(evt);
//			}
//		});
//
//		JMenuItem timKiemTKItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
//		timKiemTKItem.setIconTextGap(8);
//		timKiemTKItem.setMargin(new Insets(5, 10, 5, 10));
//		timKiemTKItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
//		timKiemTKItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				timKiemTaiKhoanItemActionPerformed(evt);
//			}
//		});
//
//		JMenuItem baoCaoTKItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
//		baoCaoTKItem.setIconTextGap(8);
//		baoCaoTKItem.setMargin(new Insets(5, 10, 5, 10));
//		baoCaoTKItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
//		baoCaoTKItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				baoCaoTaiKhoanItemActionPerformed(evt);
//			}
//		});
//
//		jpMenuTaiKhoan.add(capNhatTKItem);
//		jpMenuTaiKhoan.add(new JSeparator());
//		jpMenuTaiKhoan.add(timKiemTKItem);
//		jpMenuTaiKhoan.add(new JSeparator());
//		jpMenuTaiKhoan.add(baoCaoTKItem);
//
//		taiKhoanItem.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
//		taiKhoanItem.setIcon(new FlatSVGIcon("./icon/account.svg"));
//		taiKhoanItem.setText("Tài khoản");
//		taiKhoanItem.setBorderPainted(false);
//		taiKhoanItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//		taiKhoanItem.setFocusPainted(false);
//		taiKhoanItem.setFocusable(false);
//		taiKhoanItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
//		taiKhoanItem.setIconTextGap(10);
//		taiKhoanItem.setPreferredSize(new java.awt.Dimension(140, 60));
//		taiKhoanItem.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//
//				if (isMenuVisible) {
//					// Nếu menu đang hiện, ẩn nó đi
//					jpMenuTaiKhoan.setVisible(false);
//					isMenuVisible = false; // Cập nhật trạng thái
//				} else {
//					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
//					int x = taiKhoanItem.getLocationOnScreen().x - taiKhoanItem.getParent().getLocationOnScreen().x;
//					int y = taiKhoanItem.getLocationOnScreen().y - taiKhoanItem.getParent().getLocationOnScreen().y
//							+ taiKhoanItem.getHeight();
//
//					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
//					jpMenuTaiKhoan.show(taiKhoanItem.getParent(), x, y);
//					isMenuVisible = true; // Cập nhật trạng thái
//				}
//			}
//		});
//		itemPanel.add(taiKhoanItem);

		
		//================== NGƯỜI DÙNG ===============================		    

		// Tạo menu chính "Người Dùng"
		JPopupMenu jpMenuNguoiDung = new JPopupMenu();
		jpMenuNguoiDung.setPreferredSize(new Dimension(200, 100));
		jpMenuNguoiDung.setBorderPainted(false);

		// Menu "Tài khoản"
		JMenu menuTaiKhoan = new JMenu("Tài khoản");
		menuTaiKhoan.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 14));
		menuTaiKhoan.setIcon(new FlatSVGIcon("./icon/account.svg")); 

		// Các mục trong menu "Tài khoản"
		JMenuItem capNhatTKItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24));
		capNhatTKItem.setIconTextGap(8);
		capNhatTKItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatTKItem.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 14));
		capNhatTKItem.addActionListener(evt -> taiKhoanItemActionPerformed(evt));

		JMenuItem timKiemTKItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemTKItem.setIconTextGap(8);
		timKiemTKItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemTKItem.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 14));
		timKiemTKItem.addActionListener(evt -> timKiemTaiKhoanItemActionPerformed(evt));

		JMenuItem baoCaoTKItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
		baoCaoTKItem.setIconTextGap(8);
		baoCaoTKItem.setMargin(new Insets(5, 10, 5, 10));
		baoCaoTKItem.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 14));
		baoCaoTKItem.addActionListener(evt -> baoCaoTaiKhoanItemActionPerformed(evt));

		// Thêm các mục vào menu "Tài khoản"
		menuTaiKhoan.add(capNhatTKItem);
		menuTaiKhoan.add(timKiemTKItem);
		menuTaiKhoan.add(baoCaoTKItem);

		// Menu "Vai trò"
		JMenu menuVaiTro = new JMenu("Vai trò");
		menuVaiTro.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 14));
		menuVaiTro.setIcon(new FlatSVGIcon("./icon/security.svg")); 

		// Các mục trong menu "Vai trò"
		JMenuItem capNhatVTItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24));
		capNhatVTItem.setIconTextGap(8);
		capNhatVTItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatVTItem.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 14));
		capNhatVTItem.addActionListener(evt -> vaiTroItemActionPerformed(evt));

		JMenuItem timKiemVTItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemVTItem.setIconTextGap(8);
		timKiemVTItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemVTItem.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 14));
		timKiemVTItem.addActionListener(evt -> timKiemVaiTroItemActionPerformed(evt));

		JMenuItem baoCaoVTItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
		baoCaoVTItem.setIconTextGap(8);
		baoCaoVTItem.setMargin(new Insets(5, 10, 5, 10));
		baoCaoVTItem.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 14));
		baoCaoVTItem.addActionListener(evt -> baoCaoVaiTroItemActionPerformed(evt));

		// Thêm các mục vào menu "Vai trò"
		menuVaiTro.add(capNhatVTItem);
		menuVaiTro.add(timKiemVTItem);
		menuVaiTro.add(baoCaoVTItem);

		// Thêm menu "Tài khoản" và "Vai trò" vào menu chính "Người Dùng"
		jpMenuNguoiDung.add(menuTaiKhoan);
		jpMenuNguoiDung.add(new JSeparator());
		jpMenuNguoiDung.add(menuVaiTro);

		// Tạo nút chính "Người Dùng"
		nguoiDungItem = new JButton("Người Dùng");
		nguoiDungItem.setFont(new java.awt.Font("Roboto Medium", java.awt.Font.PLAIN, 12));
		nguoiDungItem.setIcon(new FlatSVGIcon("./icon/manMenu.svg"));
		nguoiDungItem.setBorderPainted(false);
		nguoiDungItem.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
		nguoiDungItem.setFocusPainted(false);
		nguoiDungItem.setPreferredSize(new Dimension(140, 60));
		nguoiDungItem.addActionListener(evt -> {
		    if (jpMenuNguoiDung.isVisible()) {
		        jpMenuNguoiDung.setVisible(false);
		    } else {
		        int x = nguoiDungItem.getLocationOnScreen().x - nguoiDungItem.getParent().getLocationOnScreen().x;
		        int y = nguoiDungItem.getLocationOnScreen().y - nguoiDungItem.getParent().getLocationOnScreen().y + nguoiDungItem.getHeight();
		        jpMenuNguoiDung.show(nguoiDungItem.getParent(), x, y);
		    }
		});

		// Thêm nút "Người Dùng" vào panel itemPanel
		itemPanel.add(nguoiDungItem);

//================== VAI TRÒ ===============================			
//
//		// Tạo menu thả xuống JPopupMenu
//		jpMenuVaiTo = new JPopupMenu();
//		jpMenuVaiTo.setPreferredSize(new java.awt.Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
//		jpMenuVaiTo.setBorderPainted(false);
//
//		jpMenuVaiTo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//		// Tạo các item trong menu với biểu tượng và văn bản
//		JMenuItem capNhatVTItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt
//		capNhatVTItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
//		capNhatVTItem.setMargin(new Insets(5, 10, 5, 10));
//		capNhatVTItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
//		capNhatVTItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				vaiTroItemActionPerformed(evt);
//			}
//		});
//
//		JMenuItem timKiemVTItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
//		timKiemVTItem.setIconTextGap(8);
//		timKiemVTItem.setMargin(new Insets(5, 10, 5, 10));
//		timKiemVTItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
//		timKiemVTItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				timKiemVaiTroItemActionPerformed(evt);
//			}
//		});
//
//		JMenuItem baoCaoVTItem = new JMenuItem("Báo Cáo", new FlatSVGIcon("./icon/report.svg", 24, 24));
//		baoCaoVTItem.setIconTextGap(8);
//		baoCaoVTItem.setMargin(new Insets(5, 10, 5, 10));
//		baoCaoVTItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
//		baoCaoVTItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				baoCaoVaiTroItemActionPerformed(evt);
//			}
//		});
//
//		jpMenuVaiTo.add(capNhatVTItem);
//		jpMenuVaiTo.add(new JSeparator());
//		jpMenuVaiTo.add(timKiemVTItem);
//		jpMenuVaiTo.add(new JSeparator());
//		jpMenuVaiTo.add(baoCaoVTItem);
//
//		vaiTroItem.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
//		vaiTroItem.setIcon(new FlatSVGIcon("./icon/security.svg"));
//		vaiTroItem.setText("Vai trò");
//		vaiTroItem.setBorderPainted(false);
//		vaiTroItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//		vaiTroItem.setFocusPainted(false);
//		vaiTroItem.setFocusable(false);
//		vaiTroItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
//		vaiTroItem.setIconTextGap(10);
//		vaiTroItem.setPreferredSize(new java.awt.Dimension(140, 60));
//		vaiTroItem.addActionListener(new java.awt.event.ActionListener() {
//
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				if (isMenuVisible) {
//					// Nếu menu đang hiện, ẩn nó đi
//					jpMenuVaiTo.setVisible(false);
//					isMenuVisible = false; // Cập nhật trạng thái
//				} else {
//					// Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
//					int x = vaiTroItem.getLocationOnScreen().x - vaiTroItem.getParent().getLocationOnScreen().x;
//					int y = vaiTroItem.getLocationOnScreen().y - vaiTroItem.getParent().getLocationOnScreen().y
//							+ vaiTroItem.getHeight();
//
//					// Hiển thị menu thả xuống ngay dưới nút khachHangItem
//					jpMenuVaiTo.show(vaiTroItem.getParent(), x, y);
//					isMenuVisible = true; // Cập nhật trạng thái
//				}
//			}
//
//		});
//		itemPanel.add(vaiTroItem);

		topMenuPanel.add(itemPanel, java.awt.BorderLayout.CENTER);

		mainContent.setBackground(new java.awt.Color(255, 255, 255));
		mainContent.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		mainContent.setPreferredSize(new java.awt.Dimension(1130, 620));
		mainContent.setLayout(new java.awt.BorderLayout());

		getContentPane().add(leftContent, java.awt.BorderLayout.WEST);
		getContentPane().add(topMenuPanel, java.awt.BorderLayout.NORTH);
		getContentPane().add(mainContent, java.awt.BorderLayout.CENTER);

		pack();

		setLocationRelativeTo(null);
	}

	private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
		if (MessageDialog.confirm(this, "Bạn có chắc chắn muốn đăng xuất không?", "Đăng xuẩt")) {
			this.dispose();
			new Login().setVisible(true);
		}
	}

//-------------CHỨC NĂNG TÀI KHOẢN---------------------------		

	private void taiKhoanItemActionPerformed(java.awt.event.ActionEvent evt) {
		taiKhoan = new TaiKhoanPage();
		this.setPanel(taiKhoan);
		resetActive();
		taiKhoanItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemTaiKhoanItemActionPerformed(java.awt.event.ActionEvent evt) {
		timKiemTaiKhoan = new TimKiemTaiKhoanPage();
		this.setPanel(timKiemTaiKhoan);
		resetActive();
		taiKhoanItem.setSelected(true);
		isMenuVisible = false;
	}

	private void baoCaoTaiKhoanItemActionPerformed(java.awt.event.ActionEvent evt) {
		baoCaoTaiKhoan = new BaoCaoTaiKhoanPage();
		this.setPanel(baoCaoTaiKhoan);
		resetActive();
		taiKhoanItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG NHÂN VIÊN---------------------------	

	private void nhanVienItemActionPerformed(java.awt.event.ActionEvent evt) {
		nhanVien = new NhanVienPage();
		this.setPanel(nhanVien);
		resetActive();
		nhanVienItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemNhanVienItemActionPerformed(java.awt.event.ActionEvent evt) {
		timKiemNhanVien = new TimKiemNhanVienPage();
		this.setPanel(timKiemNhanVien);
		resetActive();
		nhanVienItem.setSelected(true);
		isMenuVisible = false;
	}
	
	private void datHangNhanVienItemActionPerformed(java.awt.event.ActionEvent evt) {
//		datHangNhanVien = new CreateDatHangPage();
		datHangNhanVien = new DatHangPage(this);
		this.setPanel(datHangNhanVien);
		resetActive();
		nhanVienItem.setSelected(true);
		isMenuVisible = false;
	}

	private void baoCaoNhanVienItemActionPerformed(java.awt.event.ActionEvent evt) {
		baoCaoNhanVien = new BaoCaoNhanVienPage();
		this.setPanel(baoCaoNhanVien);
		resetActive();
		nhanVienItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG KHÁCH HÀNG---------------------------

	private void khachHangItemActionPerformed(java.awt.event.ActionEvent evt) {
		khachHang = new KhachHangPage();
		this.setPanel(khachHang);
		resetActive();
		khachHangItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemKhachHangItemActionPerformed(java.awt.event.ActionEvent evt) {
		timKiemKhachHang = new TimKiemKhachHangPage();
		this.setPanel(timKiemKhachHang);
		resetActive();
		khachHangItem.setSelected(true);
		isMenuVisible = false;
	}

	private void baoCaoKhachHangItemActionPerformed(java.awt.event.ActionEvent evt) {
		baoCaoKhachHang = new BaoCaoKhachHangPage();
		this.setPanel(baoCaoKhachHang);
		resetActive();
		khachHangItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG HÓA ĐƠN---------------------------

	private void hoaDonItemActionPerformed(java.awt.event.ActionEvent evt) {
		hoaDon = new HoaDonPage(this);
		this.setPanel(hoaDon);
		resetActive();
		hoaDonItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemHoaDonItemActionPerformed(java.awt.event.ActionEvent evt) {
		timKiemHoaDonPage = new TimKiemHoaDonPage(this);
		this.setPanel(timKiemHoaDonPage);
		resetActive();
		hoaDonItem.setSelected(true);
		isMenuVisible = false;
	}

	private void baoCaoHoaDonItemActionPerformed(java.awt.event.ActionEvent evt) {
		baoCaoHoaDonPage = new BaoCaoHoaDonPage(this);
		this.setPanel(baoCaoHoaDonPage);
		resetActive();
		hoaDonItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG NHÀ CUNG CẤP---------------------------	
	private void nhaCungCapItemActionPerformed(java.awt.event.ActionEvent evt) {
		nhaCungCap = new NhaCungCapPage();
		this.setPanel(nhaCungCap);
		resetActive();
		nhaCungCapItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemNhaCungCapItemActionPerformed(java.awt.event.ActionEvent evt) {
		timKiemNhaCungCap = new TimKiemNhaCungCapPage();
		this.setPanel(timKiemNhaCungCap);
		resetActive();
		nhaCungCapItem.setSelected(true);
		isMenuVisible = false;
	}

	private void baoCaoNhaCungCapItemActionPerformed(java.awt.event.ActionEvent evt) {
		baoCaoNhaCungCap = new BaoCaoNhaCungCapPage();
		this.setPanel(baoCaoNhaCungCap);
		resetActive();
		nhaCungCapItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG THUỐC---------------------------		
	private void thuocItemActionPerformed(java.awt.event.ActionEvent evt) {
		thuoc = new ThuocPage(this);
		this.setPanel(thuoc);
		resetActive();
		thuocItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemThuocItemActionPerformed(java.awt.event.ActionEvent evt) {
		timKiemThuoc = new TimKiemThuocPage(this);
		this.setPanel(timKiemThuoc);
		resetActive();
		thuocItem.setSelected(true);
		isMenuVisible = false;
	}

	private void baoCaoThuocItemActionPerformed(java.awt.event.ActionEvent evt) {
		baoCaoThuoc = new BaoCaoThuocPage(this);
		this.setPanel(baoCaoThuoc);
		resetActive();
		thuocItem.setSelected(true);
		isMenuVisible = false;
	}

	private void chiTietThuocItemActionPerformed(java.awt.event.ActionEvent evt) {
		chiTietThuoc = new ChiTietThuocPage(this);
		this.setPanel(chiTietThuoc);
		resetActive();
		thuocItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG DANH MỤC---------------------------			

	private void danhMucItemActionPerformed(java.awt.event.ActionEvent evt) {
		danhMuc = new DanhMucPage(this);
		this.setPanel(danhMuc);
		resetActive();
		danhMucItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG VAI TRÒ---------------------------		
	private void vaiTroItemActionPerformed(java.awt.event.ActionEvent evt) {
		vaiTro = new VaiTroPage();
		this.setPanel(vaiTro);
		resetActive();
		vaiTroItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemVaiTroItemActionPerformed(java.awt.event.ActionEvent evt) {
		timKiemVaiTro = new TimKiemVaiTroPage();
		this.setPanel(timKiemVaiTro);
		resetActive();
		vaiTroItem.setSelected(true);
		isMenuVisible = false;
	}

	private void baoCaoVaiTroItemActionPerformed(java.awt.event.ActionEvent evt) {
		baoCaoVaiTro = new BaoCaoVaiTroPage();
		this.setPanel(baoCaoVaiTro);
		resetActive();
		vaiTroItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG PHIẾU NHẬP---------------------------			

	private void phieuNhapItemActionPerformed(java.awt.event.ActionEvent evt) {
		phieuNhap = new PhieuNhapPage(this);
		this.setPanel(phieuNhap);
		resetActive();
		phieuNhapItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemPhieuNhapItemActionPerformed(java.awt.event.ActionEvent evt) {
		timKiemPhieuNhap = new TimKiemPhieuNhapPage(this);
		this.setPanel(timKiemPhieuNhap);
		resetActive();
		phieuNhapItem.setSelected(true);
		isMenuVisible = false;
	}

	private void baoCaoPhieuNhapItemActionPerformed(java.awt.event.ActionEvent evt) {
		baoCaoPhieuNhap = new BaoCaoPhieuNhapPage(this);
		this.setPanel(baoCaoPhieuNhap);
		resetActive();
		phieuNhapItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG THỐNG KÊ---------------------------			

	private void thongKeItemActionPerformed(java.awt.event.ActionEvent evt) {
		thongke = new ThongKePage(tk);
		this.setPanel(thongke);
		resetActive();
		thongKeItem.setSelected(true);
	}

	private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {
		InfoDialog dialog = new InfoDialog(this, true, this, tk);
		dialog.setVisible(true);
	}

	private JButton btnInfo;
	private JButton btnLogout;
	private JButton hoaDonItem;
	private JPanel infoPanel;
	private JPanel itemPanel;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel5;
	private JScrollPane jScrollPane1;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JSeparator jSeparator3;
	private JButton khachHangItem;
	private JPanel leftContent;
	private JPanel topMenuPanel;
	private JPanel mainContent;
	private JButton nhaCungCapItem;
	private JButton nhanVienItem;
	private JButton phieuNhapItem;
	private JPanel sidebarPanel;
	private JButton taiKhoanItem;
	private JButton thongKeItem;
	private JButton thuocItem;
	private JButton danhMucItem;
	private JLabel txtFullName;
	private JLabel txtRole;
	private JButton vaiTroItem;
	private JButton nguoiDungItem;
	private JPopupMenu jpMenuNhanVien;
	private JPopupMenu jpMenuHD;
	private JPopupMenu jpMenuKH;
	private JPopupMenu jpMenuNhaCC;
	private JPopupMenu jpMenuThongKe;
	private JPopupMenu jpMenuThuoc;
	private JPopupMenu jpMenuTaiKhoan;
	private JPopupMenu jpMenuVaiTo;
	private JPopupMenu jpMenuPhieuNhap;
	private JPopupMenu jpMenuDanhMuc;
	private JMenuItem thongKeNVItem;
	private JMenuItem capNhatNVItem;
	private JMenuItem datHangNVItem;
	private JMenuItem timKiemNVItem;
	private JMenuItem baoCaoNVItem;
}

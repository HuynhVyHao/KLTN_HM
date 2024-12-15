package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.TaiKhoanController;
import controller.ThuocController;
import entity.TaiKhoan;
import entity.Thuoc;
import gui.dialog.InfoDialog;
import gui.page.ChiTietThuocPage;
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
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

import utils.Formatter;
import utils.MessageDialog;

public class MainLayout extends JFrame {

	private ThuocPage thuoc;
	private TimKiemThuocPage timKiemThuoc;
	private ChiTietThuocPage chiTietThuoc;
	private DanhMucPage danhMuc;
	private HoaDonPage hoaDon;
	private TimKiemHoaDonPage timKiemHoaDonPage;
	private NhaCungCapPage nhaCungCap;
	private TimKiemNhaCungCapPage timKiemNhaCungCap;
	private KhachHangPage khachHang;
	private TimKiemKhachHangPage timKiemKhachHang;
	private NhanVienPage nhanVien;
	private TimKiemNhanVienPage timKiemNhanVien;
	private DatHangPage datHangNhanVien;
	private TaiKhoanPage taiKhoan;
	private TimKiemTaiKhoanPage timKiemTaiKhoan;
	private VaiTroPage vaiTro;
	private TimKiemVaiTroPage timKiemVaiTro;
	private PhieuNhapPage phieuNhap;
	private TimKiemPhieuNhapPage timKiemPhieuNhap;
	private ThongKePage thongke;
	private ThuocController THUOC_CON= new ThuocController();;
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
			vaiTroItem.setEnabled(false);
			taiKhoanItem.setEnabled(false);
			nguoiDungItem.setEnabled(false);

			capNhatNVItem.setEnabled(false); // Vô hiệu hóa mục Cập Nhật
			timKiemNVItem.setEnabled(false); // Vô hiệu hóa mục Tìm Kiếm
//			baoCaoNVItem.setEnabled(false);
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

	private void initComponents() {

		leftContent = new JPanel();
		infoPanel = new JPanel();
		jPanel2 = new JPanel();
		btnInfo = new JButton();
		jPanel1 = new JPanel();
		txtFullName = new JLabel();
		txtRole = new JLabel();
		btnLogout = new JButton();
		topMenuPanel = new JPanel();
		itemPanel = new JPanel();
		thongKeItem = new JButton();
		hoaDonItem = new JButton();
		khachHangItem = new JButton();
		thuocItem = new JButton();
		danhMucItem = new JButton();
		phieuNhapItem = new JButton();
		nhaCungCapItem = new JButton();
		nhanVienItem = new JButton();
		taiKhoanItem = new JButton();
		nguoiDungItem = new JButton();
		vaiTroItem = new JButton();
		mainContent = new JPanel();
		jpMenuNhanVien = new JPopupMenu();
		jpMenuNhanVien = new JPopupMenu();
		jpMenuThuoc = new JPopupMenu();
		jpMenuPhieuNhap = new JPopupMenu();
		jpMenuNhanVien = new JPopupMenu();
		jpMenuNhanVien = new JPopupMenu();
		notificationPanel = new JPanel();
		bellIconLabel = new JLabel();
		notificationBadge = new JLabel();


		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Hệ Thống Quản Lý Nhà Thuốc H&M - Gò Vấp");

		leftContent.setBackground(new Color(218, 245, 211));
		leftContent.setPreferredSize(new Dimension(250, 800));
		leftContent.setLayout(new BorderLayout());

		infoPanel.setBackground(new Color(255, 255, 255));
		infoPanel.setBorder(new LineBorder(new Color(242, 242, 242), 2, true));
		infoPanel.setPreferredSize(new Dimension(250, 80));
		infoPanel.setLayout(new BorderLayout());

		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel2.setPreferredSize(new Dimension(64, 80));
		jPanel2.setLayout(new BorderLayout());

		btnInfo.setFont(new Font("Roboto", 1, 14));
		btnInfo.setIcon(new FlatSVGIcon("./icon/man.svg"));
		btnInfo.setBorderPainted(false);
		btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnInfo.setFocusPainted(false);
		btnInfo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnInfo.setPreferredSize(new Dimension(90, 90));
		btnInfo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnInfoActionPerformed(evt);
			}
		});
		jPanel2.add(btnInfo, BorderLayout.CENTER);

		infoPanel.add(jPanel2, BorderLayout.WEST);

		jPanel1.setBackground(new Color(255, 255, 255));

		txtFullName.setFont(new Font("Roboto", 1, 14));
//		txtFullName.setText("Nguyễn Văn A");

		txtRole.setFont(new Font("Roboto Light", 2, 13));
//		txtRole.setText("Nhân viên Quản lý sản phẩm");

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(txtRole, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
								.addComponent(txtFullName, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel1Layout.createSequentialGroup().addGap(18, 18, 18)
						.addComponent(txtFullName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(txtRole, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(15, Short.MAX_VALUE)));

		infoPanel.add(jPanel1, BorderLayout.CENTER);

		btnLogout.setFont(new Font("Roboto Medium", 0, 14));
		btnLogout.setIcon(new FlatSVGIcon("./icon/logout.svg"));
		btnLogout.setText("Đăng Xuất");
		btnLogout.setBorderPainted(false);
		btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogout.setFocusPainted(false);
		btnLogout.setFocusable(false);
		btnLogout.setHorizontalAlignment(SwingConstants.LEADING);
		btnLogout.setIconTextGap(16);
		btnLogout.setPreferredSize(new Dimension(226, 46));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnLogoutActionPerformed(evt);
			}
		});

		leftContent.add(infoPanel, BorderLayout.NORTH);
		leftContent.add(btnLogout, BorderLayout.SOUTH);

		topMenuPanel.setBackground(new Color(255, 255, 255));
		topMenuPanel.setBorder(new LineBorder(new Color(242, 242, 242), 2, true));
		topMenuPanel.setPreferredSize(new Dimension(1130, 80));
		topMenuPanel.setLayout(new BorderLayout());

		itemPanel.setBackground(new Color(255, 255, 255));
		itemPanel.setBorder(new LineBorder(new Color(255, 255, 255), 8, true));
		itemPanel.setPreferredSize(new Dimension(1130, 80));
		itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		notificationPanel.setBackground(new Color(255, 255, 255));
		notificationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10));

		notificationPanel.setPreferredSize(new Dimension(100, 100)); // Đặt kích thước vừa đủ cho chuông và badge

		List<Thuoc> expiredMedicines = THUOC_CON.selectExpiredMedicines(); // Hàm lấy danh sách thuốc hết hạn
		int expiredCount = expiredMedicines.size(); // Số lượng thuốc hết hạn

		bellIconLabel.setIcon(new FlatSVGIcon("./icon/notification.svg"));
		bellIconLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bellIconLabel.setLayout(null); // Sử dụng layout null để đặt các thành phần ở vị trí tùy chỉnh
		bellIconLabel.setPreferredSize(new Dimension(50, 50)); // Đảm bảo kích thước đủ lớn để chứa chuông và badge

		notificationBadge.setText(expiredCount > 0 ? String.valueOf(expiredCount) : ""); // Hiển thị số nếu > 0
		notificationBadge.setForeground(Color.WHITE);
		notificationBadge.setBackground(Color.RED);
		notificationBadge.setOpaque(true);
		notificationBadge.setHorizontalAlignment(SwingConstants.CENTER);
		notificationBadge.setVerticalAlignment(SwingConstants.CENTER);
		notificationBadge.setPreferredSize(new Dimension(20, 20)); // Kích thước badge
		notificationBadge.setFont(new Font("Arial", Font.BOLD, 12));
		notificationBadge.setVisible(expiredCount > 0); // Chỉ hiển thị badge nếu có thuốc hết hạn
		notificationBadge.setBounds(bellIconLabel.getWidth() + 20, 0, 20, 20); // Đặt vị trí badge ở góc trên bên phải

		bellIconLabel.add(notificationBadge);

		bellIconLabel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	 List<Thuoc> expiredMedicines = THUOC_CON.selectExpiredMedicines(); 
		        if (expiredMedicines.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Không có thuốc hết hạn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		        } else {
		            StringBuilder message = new StringBuilder("Danh sách thuốc hết hạn:\n");
		            for (Thuoc thuoc : expiredMedicines) {
		                message.append("- ").append(thuoc.getTenThuoc()).append(" (HSD: ").append( (Formatter.FormatDate(thuoc.getHanSuDung()))).append(")\n");
		            }
		            JOptionPane.showMessageDialog(null, message.toString(), "Thuốc hết hạn", JOptionPane.WARNING_MESSAGE);
		            updateNotificationBadge();
		        }
		    }
		});
		

		notificationPanel.add(bellIconLabel);

		topMenuPanel.add(notificationPanel, BorderLayout.EAST);

//-----------------HÓA ĐƠN--------------------------------------------
		// Tạo menu thả xuống JPopupMenu
		jpMenuHD = new JPopupMenu();
		jpMenuHD.setPreferredSize(new Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuHD.setBorderPainted(false);

		jpMenuHD.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatHDItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt
																												// kích
																												// thước
																												// biểu
		capNhatHDItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatHDItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatHDItem.setFont(new Font("Roboto Medium", 0, 14));
		capNhatHDItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				hoaDonItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemHDItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemHDItem.setIconTextGap(8);
		timKiemHDItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemHDItem.setFont(new Font("Roboto Medium", 0, 14));
		timKiemHDItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemHoaDonItemActionPerformed(evt);
			}
		});

		jpMenuHD.add(capNhatHDItem);
		jpMenuHD.add(new JSeparator());
		jpMenuHD.add(timKiemHDItem);

		hoaDonItem.setFont(new Font("Roboto Medium", 0, 12));
		hoaDonItem.setIcon(new FlatSVGIcon("./icon/bill.svg"));
		hoaDonItem.setText("Hóa đơn");
		hoaDonItem.setBorderPainted(false);
		hoaDonItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		hoaDonItem.setFocusPainted(false);
		hoaDonItem.setFocusable(false);
		hoaDonItem.setHorizontalAlignment(SwingConstants.LEADING);
		hoaDonItem.setIconTextGap(10);
		hoaDonItem.setPreferredSize(new Dimension(130, 60));
		isMenuVisible = false;
		hoaDonItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

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
		jpMenuKH.setPreferredSize(new Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuKH.setBorderPainted(false);

		jpMenuKH.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt kích
																											// thước
																											// biểu
		capNhatItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatItem.setFont(new Font("Roboto Medium", 0, 14));
		capNhatItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				khachHangItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemItem.setIconTextGap(8);
		timKiemItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemItem.setFont(new Font("Roboto Medium", 0, 14));
		timKiemItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemKhachHangItemActionPerformed(evt);
			}
		});

		jpMenuKH.add(capNhatItem);
		jpMenuKH.add(new JSeparator());
		jpMenuKH.add(timKiemItem);

		khachHangItem.setFont(new Font("Roboto Medium", 0, 12));
		khachHangItem.setIcon(new FlatSVGIcon("./icon/customer.svg"));
		khachHangItem.setText("Khách hàng");
		khachHangItem.setBorderPainted(false);
		khachHangItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		khachHangItem.setFocusPainted(false);
		khachHangItem.setFocusable(false);
		khachHangItem.setHorizontalAlignment(SwingConstants.LEADING);
		khachHangItem.setIconTextGap(10);
		khachHangItem.setPreferredSize(new Dimension(140, 60));
		// Tạo biến để lưu trạng thái hiện/ẩn của menu
		isMenuVisible = false; // Ban đầu menu đang ẩn

		khachHangItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
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
		jpMenuThuoc.setPreferredSize(new Dimension(170, 170)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuThuoc.setBorderPainted(false);

		jpMenuThuoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatThuocItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24));
		capNhatThuocItem.setIconTextGap(8);// Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatThuocItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatThuocItem.setFont(new Font("Roboto Medium", 0, 14));
		capNhatThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				thuocItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemThuocItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemThuocItem.setIconTextGap(8);
		timKiemThuocItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemThuocItem.setFont(new Font("Roboto Medium", 0, 14));
		timKiemThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemThuocItemActionPerformed(evt);
			}
		});

		JMenuItem chiTietThuocItem = new JMenuItem("Chi Tiết", new FlatSVGIcon("./icon/listCT.svg", 24, 24));
		chiTietThuocItem.setIconTextGap(8);
		chiTietThuocItem.setMargin(new Insets(5, 10, 5, 10));
		chiTietThuocItem.setFont(new Font("Roboto Medium", 0, 14));
		chiTietThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				chiTietThuocItemActionPerformed(evt);
			}
		});

		JMenuItem danhMucThuocItem = new JMenuItem("Danh Mục", new FlatSVGIcon("./icon/categories.svg", 24, 24));
		danhMucThuocItem.setIconTextGap(8);
		danhMucThuocItem.setMargin(new Insets(5, 10, 5, 10));
		danhMucThuocItem.setFont(new Font("Roboto Medium", 0, 14));
		danhMucThuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				danhMucItemActionPerformed(evt);
			}
		});

		jpMenuThuoc.add(capNhatThuocItem);
		jpMenuThuoc.add(new JSeparator());
		jpMenuThuoc.add(timKiemThuocItem);
		jpMenuThuoc.add(new JSeparator());
		jpMenuThuoc.add(chiTietThuocItem);
		jpMenuThuoc.add(new JSeparator());
		jpMenuThuoc.add(danhMucThuocItem);

		thuocItem.setFont(new Font("Roboto Medium", 0, 12));
		thuocItem.setIcon(new FlatSVGIcon("./icon/medicine.svg"));
		thuocItem.setText("Thuốc");
		thuocItem.setBorderPainted(false);
		thuocItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		thuocItem.setFocusPainted(false);
		thuocItem.setFocusable(false);
		thuocItem.setHorizontalAlignment(SwingConstants.LEADING);
		thuocItem.setIconTextGap(10);
		thuocItem.setPreferredSize(new Dimension(120, 60));
		thuocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
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

//-----------------PHIẾU NHẬP--------------------------------------------		
		// Tạo menu thả xuống JPopupMenu
		jpMenuPhieuNhap = new JPopupMenu();
		jpMenuPhieuNhap.setPreferredSize(new Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuPhieuNhap.setBorderPainted(false);

		jpMenuPhieuNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatPNItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24)); // Đặt
																												// kích
		capNhatPNItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatPNItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatPNItem.setFont(new Font("Roboto Medium", 0, 14));
		capNhatPNItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				phieuNhapItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemPNItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemPNItem.setIconTextGap(8);
		timKiemPNItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemPNItem.setFont(new Font("Roboto Medium", 0, 14));
		timKiemPNItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemPhieuNhapItemActionPerformed(evt);
			}
		});

		jpMenuPhieuNhap.add(capNhatPNItem);
		jpMenuPhieuNhap.add(new JSeparator());
		jpMenuPhieuNhap.add(timKiemPNItem);

		phieuNhapItem.setFont(new Font("Roboto Medium", 0, 12));
		phieuNhapItem.setIcon(new FlatSVGIcon("./icon/bill-import.svg"));
		phieuNhapItem.setText("Phiếu nhập");
		phieuNhapItem.setBorderPainted(false);
		phieuNhapItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		phieuNhapItem.setFocusPainted(false);
		phieuNhapItem.setFocusable(false);
		phieuNhapItem.setHorizontalAlignment(SwingConstants.LEADING);
		phieuNhapItem.setIconTextGap(10);
		phieuNhapItem.setPreferredSize(new Dimension(150, 60));
		phieuNhapItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
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
		jpMenuNhaCC.setPreferredSize(new Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuNhaCC.setBorderPainted(false);

		jpMenuNhaCC.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
		// Tạo các item trong menu với biểu tượng và văn bản
		JMenuItem capNhatNCCItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24));
		capNhatNCCItem.setIconTextGap(8);// Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatNCCItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatNCCItem.setFont(new Font("Roboto Medium", 0, 14));
		capNhatNCCItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nhaCungCapItemActionPerformed(evt);
			}
		});

		JMenuItem timKiemNCCItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemNCCItem.setIconTextGap(8);
		timKiemNCCItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemNCCItem.setFont(new Font("Roboto Medium", 0, 14));
		timKiemNCCItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemNhaCungCapItemActionPerformed(evt);
			}
		});

		jpMenuNhaCC.add(capNhatNCCItem);
		jpMenuNhaCC.add(new JSeparator());
		jpMenuNhaCC.add(timKiemNCCItem);

		nhaCungCapItem.setFont(new Font("Roboto Medium", 0, 12));
		nhaCungCapItem.setIcon(new FlatSVGIcon("./icon/trucks.svg"));
		nhaCungCapItem.setText("Nhà cung cấp");
		nhaCungCapItem.setBorderPainted(false);
		nhaCungCapItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		nhaCungCapItem.setFocusPainted(false);
		nhaCungCapItem.setFocusable(false);
		nhaCungCapItem.setHorizontalAlignment(SwingConstants.LEADING);
		nhaCungCapItem.setIconTextGap(10);
		nhaCungCapItem.setPreferredSize(new Dimension(170, 60));
		nhaCungCapItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
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
		jpMenuNhanVien.setPreferredSize(new Dimension(170, 170)); // Điều chỉnh kích thước tổng thể của menu
		jpMenuNhanVien.setBorderPainted(false);
		jpMenuNhanVien.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));

		// Tạo các item trong menu với biểu tượng và văn bản
		capNhatNVItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24));
		capNhatNVItem.setIconTextGap(8); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
		capNhatNVItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatNVItem.setFont(new Font("Roboto Medium", 0, 14));
		capNhatNVItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nhanVienItemActionPerformed(evt);
			}
		});

		timKiemNVItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemNVItem.setIconTextGap(8);
		timKiemNVItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemNVItem.setFont(new Font("Roboto Medium", 0, 14));
		timKiemNVItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timKiemNhanVienItemActionPerformed(evt);
			}
		});

		thongKeNVItem = new JMenuItem("Thống Kê", new FlatSVGIcon("./icon/statistics.svg", 24, 24));
		thongKeNVItem.setIconTextGap(8);
		thongKeNVItem.setMargin(new Insets(5, 10, 5, 10));
		thongKeNVItem.setFont(new Font("Roboto Medium", 0, 14));
		thongKeNVItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				thongKeItemActionPerformed(evt);
			}
		});

		datHangNVItem = new JMenuItem("Đặt Hàng", new FlatSVGIcon("./icon/trolley.svg", 24, 24));
		datHangNVItem.setIconTextGap(8);
		datHangNVItem.setMargin(new Insets(5, 10, 5, 10));
		datHangNVItem.setFont(new Font("Roboto Medium", 0, 14));
		datHangNVItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				datHangNhanVienItemActionPerformed(evt);
			}
		});

		jpMenuNhanVien.add(capNhatNVItem);
		jpMenuNhanVien.add(new JSeparator());
		jpMenuNhanVien.add(timKiemNVItem);
		jpMenuNhanVien.add(new JSeparator());
		jpMenuNhanVien.add(thongKeNVItem);
		jpMenuNhanVien.add(new JSeparator());
		jpMenuNhanVien.add(datHangNVItem);

		nhanVienItem.setFont(new java.awt.Font("Roboto Medium", 0, 12));
		nhanVienItem.setIcon(new FlatSVGIcon("./icon/employee.svg"));
		nhanVienItem.setText("Nhân viên");
		nhanVienItem.setBorderPainted(false);
		nhanVienItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		nhanVienItem.setFocusPainted(false);
		nhanVienItem.setFocusable(false);
		nhanVienItem.setHorizontalAlignment(SwingConstants.LEADING);
		nhanVienItem.setIconTextGap(10);
		nhanVienItem.setPreferredSize(new Dimension(140, 60));
		nhanVienItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

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

		// ================== NGƯỜI DÙNG ===============================

		// Tạo menu chính "Người Dùng"
		JPopupMenu jpMenuNguoiDung = new JPopupMenu();
		jpMenuNguoiDung.setPreferredSize(new Dimension(200, 100));
		jpMenuNguoiDung.setBorderPainted(false);

		// Menu "Tài khoản"
		JMenu menuTaiKhoan = new JMenu("Tài khoản");
		menuTaiKhoan.setFont(new Font("Roboto Medium", Font.PLAIN, 14));
		menuTaiKhoan.setIcon(new FlatSVGIcon("./icon/account.svg"));

		// Các mục trong menu "Tài khoản"
		JMenuItem capNhatTKItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24));
		capNhatTKItem.setIconTextGap(8);
		capNhatTKItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatTKItem.setFont(new Font("Roboto Medium", Font.PLAIN, 14));
		capNhatTKItem.addActionListener(evt -> taiKhoanItemActionPerformed(evt));

		JMenuItem timKiemTKItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemTKItem.setIconTextGap(8);
		timKiemTKItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemTKItem.setFont(new Font("Roboto Medium", Font.PLAIN, 14));
		timKiemTKItem.addActionListener(evt -> timKiemTaiKhoanItemActionPerformed(evt));

		// Thêm các mục vào menu "Tài khoản"
		menuTaiKhoan.add(capNhatTKItem);
		menuTaiKhoan.add(timKiemTKItem);

		// Menu "Vai trò"
		JMenu menuVaiTro = new JMenu("Vai trò");
		menuVaiTro.setFont(new Font("Roboto Medium", Font.PLAIN, 14));
		menuVaiTro.setIcon(new FlatSVGIcon("./icon/security.svg"));

		// Các mục trong menu "Vai trò"
		JMenuItem capNhatVTItem = new JMenuItem("Cập Nhật", new FlatSVGIcon("./icon/checklist.svg", 24, 24));
		capNhatVTItem.setIconTextGap(8);
		capNhatVTItem.setMargin(new Insets(5, 10, 5, 10));
		capNhatVTItem.setFont(new Font("Roboto Medium", Font.PLAIN, 14));
		capNhatVTItem.addActionListener(evt -> vaiTroItemActionPerformed(evt));

		JMenuItem timKiemVTItem = new JMenuItem("Tìm Kiếm", new FlatSVGIcon("./icon/search2.svg", 24, 24));
		timKiemVTItem.setIconTextGap(8);
		timKiemVTItem.setMargin(new Insets(5, 10, 5, 10));
		timKiemVTItem.setFont(new Font("Roboto Medium", Font.PLAIN, 14));
		timKiemVTItem.addActionListener(evt -> timKiemVaiTroItemActionPerformed(evt));

		// Thêm các mục vào menu "Vai trò"
		menuVaiTro.add(capNhatVTItem);
		menuVaiTro.add(timKiemVTItem);

		// Thêm menu "Tài khoản" và "Vai trò" vào menu chính "Người Dùng"
		jpMenuNguoiDung.add(menuTaiKhoan);
		jpMenuNguoiDung.add(new JSeparator());
		jpMenuNguoiDung.add(menuVaiTro);

		// Tạo nút chính "Người Dùng"
		nguoiDungItem = new JButton("Người Dùng");
		nguoiDungItem.setFont(new Font("Roboto Medium", Font.PLAIN, 12));
		nguoiDungItem.setIcon(new FlatSVGIcon("./icon/manMenu.svg"));
		nguoiDungItem.setBorderPainted(false);
		nguoiDungItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		nguoiDungItem.setFocusPainted(false);
		nguoiDungItem.setPreferredSize(new Dimension(140, 60));
		nguoiDungItem.addActionListener(evt -> {
			if (jpMenuNguoiDung.isVisible()) {
				jpMenuNguoiDung.setVisible(false);
			} else {
				int x = nguoiDungItem.getLocationOnScreen().x - nguoiDungItem.getParent().getLocationOnScreen().x;
				int y = nguoiDungItem.getLocationOnScreen().y - nguoiDungItem.getParent().getLocationOnScreen().y
						+ nguoiDungItem.getHeight();
				jpMenuNguoiDung.show(nguoiDungItem.getParent(), x, y);
			}
		});

		// Thêm nút "Người Dùng" vào panel itemPanel
		itemPanel.add(nguoiDungItem);

		topMenuPanel.add(itemPanel, BorderLayout.CENTER);

		mainContent.setBackground(new Color(255, 255, 255));
		mainContent.setBorder(BorderFactory.createEtchedBorder());
		mainContent.setPreferredSize(new Dimension(1130, 620));
		mainContent.setLayout(new BorderLayout());

		getContentPane().add(leftContent, BorderLayout.WEST);
		getContentPane().add(topMenuPanel, BorderLayout.NORTH);
		getContentPane().add(mainContent, BorderLayout.CENTER);

		pack();

		setLocationRelativeTo(null);
	}

	private void btnLogoutActionPerformed(ActionEvent evt) {
		if (MessageDialog.confirm(this, "Bạn có chắc chắn muốn đăng xuất không?", "Đăng xuẩt")) {
			this.dispose();
			new Login().setVisible(true);
		}
	}
	
	// Phương thức trong MainLayout để cập nhật badge thông báo
	public void updateNotificationBadge() {
	    // Lấy lại danh sách thuốc hết hạn mới
	    List<Thuoc> expiredMedicines = THUOC_CON.selectExpiredMedicines(); // Lấy lại danh sách thuốc hết hạn mới
	    int expiredCount = expiredMedicines.size(); // Số lượng thuốc hết hạn

	    // Cập nhật badge số
	    notificationBadge.setText(expiredCount > 0 ? String.valueOf(expiredCount) : ""); // Hiển thị số nếu > 0
	    notificationBadge.setVisible(expiredCount > 0); // Chỉ hiển thị badge nếu có thuốc hết hạn
	}


//-------------CHỨC NĂNG TÀI KHOẢN---------------------------		

	private void taiKhoanItemActionPerformed(ActionEvent evt) {
		taiKhoan = new TaiKhoanPage();
		this.setPanel(taiKhoan);
		resetActive();
		taiKhoanItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemTaiKhoanItemActionPerformed(ActionEvent evt) {
		timKiemTaiKhoan = new TimKiemTaiKhoanPage();
		this.setPanel(timKiemTaiKhoan);
		resetActive();
		taiKhoanItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG NHÂN VIÊN---------------------------	

	private void nhanVienItemActionPerformed(ActionEvent evt) {
		nhanVien = new NhanVienPage();
		this.setPanel(nhanVien);
		resetActive();
		nhanVienItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemNhanVienItemActionPerformed(ActionEvent evt) {
		timKiemNhanVien = new TimKiemNhanVienPage();
		this.setPanel(timKiemNhanVien);
		resetActive();
		nhanVienItem.setSelected(true);
		isMenuVisible = false;
	}

	private void datHangNhanVienItemActionPerformed(ActionEvent evt) {
//		datHangNhanVien = new CreateDatHangPage();
		datHangNhanVien = new DatHangPage(this);
		this.setPanel(datHangNhanVien);
		resetActive();
		nhanVienItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG KHÁCH HÀNG---------------------------

	private void khachHangItemActionPerformed(ActionEvent evt) {
		khachHang = new KhachHangPage();
		this.setPanel(khachHang);
		resetActive();
		khachHangItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemKhachHangItemActionPerformed(ActionEvent evt) {
		timKiemKhachHang = new TimKiemKhachHangPage();
		this.setPanel(timKiemKhachHang);
		resetActive();
		khachHangItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG HÓA ĐƠN---------------------------

	private void hoaDonItemActionPerformed(ActionEvent evt) {
		hoaDon = new HoaDonPage(this);
		this.setPanel(hoaDon);
		resetActive();
		hoaDonItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemHoaDonItemActionPerformed(ActionEvent evt) {
		timKiemHoaDonPage = new TimKiemHoaDonPage(this);
		this.setPanel(timKiemHoaDonPage);
		resetActive();
		hoaDonItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG NHÀ CUNG CẤP---------------------------	
	private void nhaCungCapItemActionPerformed(ActionEvent evt) {
		nhaCungCap = new NhaCungCapPage();
		this.setPanel(nhaCungCap);
		resetActive();
		nhaCungCapItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemNhaCungCapItemActionPerformed(ActionEvent evt) {
		timKiemNhaCungCap = new TimKiemNhaCungCapPage();
		this.setPanel(timKiemNhaCungCap);
		resetActive();
		nhaCungCapItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG THUỐC---------------------------		
	private void thuocItemActionPerformed(ActionEvent evt) {
		thuoc = new ThuocPage(this);
		this.setPanel(thuoc);
		resetActive();
		thuocItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemThuocItemActionPerformed(ActionEvent evt) {
		timKiemThuoc = new TimKiemThuocPage(this);
		this.setPanel(timKiemThuoc);
		resetActive();
		thuocItem.setSelected(true);
		isMenuVisible = false;
	}

	private void chiTietThuocItemActionPerformed(ActionEvent evt) {
		chiTietThuoc = new ChiTietThuocPage(this);
		this.setPanel(chiTietThuoc);
		resetActive();
		thuocItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG DANH MỤC---------------------------			

	private void danhMucItemActionPerformed(ActionEvent evt) {
		danhMuc = new DanhMucPage(this);
		this.setPanel(danhMuc);
		resetActive();
		danhMucItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG VAI TRÒ---------------------------		
	private void vaiTroItemActionPerformed(ActionEvent evt) {
		vaiTro = new VaiTroPage();
		this.setPanel(vaiTro);
		resetActive();
		vaiTroItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemVaiTroItemActionPerformed(ActionEvent evt) {
		timKiemVaiTro = new TimKiemVaiTroPage();
		this.setPanel(timKiemVaiTro);
		resetActive();
		vaiTroItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG PHIẾU NHẬP---------------------------			

	private void phieuNhapItemActionPerformed(ActionEvent evt) {
		phieuNhap = new PhieuNhapPage(this);
		this.setPanel(phieuNhap);
		resetActive();
		phieuNhapItem.setSelected(true);
		isMenuVisible = false;
	}

	private void timKiemPhieuNhapItemActionPerformed(ActionEvent evt) {
		timKiemPhieuNhap = new TimKiemPhieuNhapPage(this);
		this.setPanel(timKiemPhieuNhap);
		resetActive();
		phieuNhapItem.setSelected(true);
		isMenuVisible = false;
	}

//-------------CHỨC NĂNG THỐNG KÊ---------------------------			

	private void thongKeItemActionPerformed(ActionEvent evt) {
		thongke = new ThongKePage(tk);
		this.setPanel(thongke);
		resetActive();
		thongKeItem.setSelected(true);
	}

	private void btnInfoActionPerformed(ActionEvent evt) {
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
	private JButton khachHangItem;
	private JPanel leftContent;
	private JPanel topMenuPanel;
	private JPanel mainContent;
	private JButton nhaCungCapItem;
	private JButton nhanVienItem;
	private JButton phieuNhapItem;
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
	private JPopupMenu jpMenuThuoc;
	private JPopupMenu jpMenuPhieuNhap;
	private JMenuItem thongKeNVItem;
	private JMenuItem capNhatNVItem;
	private JMenuItem datHangNVItem;
	private JMenuItem timKiemNVItem;
	private JMenuItem baoCaoNVItem;
	private JLabel notificationBadge;
	private JLabel bellIconLabel;
	private JPanel notificationPanel;
}

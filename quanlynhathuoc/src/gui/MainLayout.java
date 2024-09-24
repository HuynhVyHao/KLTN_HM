package gui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.TaiKhoanController;
import entity.TaiKhoan;
import gui.dialog.InfoDialog;
import gui.page.HoaDonPage;
import gui.page.KhachHangPage;
import gui.page.NhaCungCapPage;
import gui.page.NhanVienPage;
import gui.page.PhieuNhapPage;
import gui.page.ThuocPage;
import gui.page.TaiKhoanPage;
import gui.page.thongke.ThongKePage;
import gui.page.VaiTroPage;
import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import utils.MessageDialog;


public class MainLayout extends javax.swing.JFrame {

    private ThuocPage thuoc;
    private HoaDonPage hoaDon;
    private NhaCungCapPage nhaCungCap;
    private KhachHangPage khachHang;
    private NhanVienPage nhanVien;
    private TaiKhoanPage taiKhoan;
    private VaiTroPage vaiTro;
    private PhieuNhapPage phieuNhap;
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

        listItem = new ArrayList<>();
        
        listItem.add(hoaDonItem);
        listItem.add(thuocItem);
        listItem.add(phieuNhapItem);
        listItem.add(thongKeItem);
        listItem.add(nhaCungCapItem);
        listItem.add(khachHangItem);
        listItem.add(nhanVienItem);
        listItem.add(taiKhoanItem);
        listItem.add(vaiTroItem);

        mainContent.add(new HoaDonPage()).setVisible(true);

        listItem.get(0).setSelected(true);
        listItem.get(0).setBackground(ACTIVE_BACKGROUND_COLOR);

        for (JButton item : listItem) {
            item.getModel().addChangeListener((ChangeEvent e) -> {
                ButtonModel model = (ButtonModel) e.getSource();

                if (model.isSelected()) {
                    item.setBackground(ACTIVE_BACKGROUND_COLOR); 
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
            nhanVienItem.setEnabled(false);
            vaiTroItem.setEnabled(false);
            taiKhoanItem.setEnabled(false);
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
        phieuNhapItem = new javax.swing.JButton();
        nhaCungCapItem = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        nhanVienItem = new javax.swing.JButton();
        taiKhoanItem = new javax.swing.JButton();
        vaiTroItem = new javax.swing.JButton();
        mainContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hệ Thống Quản Lý Nhà Thuốc H&M - Gò Vấp");
     

        leftContent.setBackground(new java.awt.Color(218, 245, 211));
        leftContent.setPreferredSize(new java.awt.Dimension(250, 800));
        leftContent.setLayout(new java.awt.BorderLayout());

        infoPanel.setBackground(new java.awt.Color(255, 255, 255));
        infoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(242, 242, 242), 2, true));
        infoPanel.setPreferredSize(new java.awt.Dimension(250, 70));
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
       
        jPanel2.add(btnInfo, java.awt.BorderLayout.CENTER);

        infoPanel.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtFullName.setFont(new java.awt.Font("Roboto", 1, 14)); 
        txtFullName.setText("Nguyễn Phan Anh Tuấn");

        txtRole.setFont(new java.awt.Font("Roboto Light", 2, 13)); 
        txtRole.setText("Nhân viên Quản lý sản phẩm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRole, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(txtFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRole, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        infoPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        btnLogout.setFont(new java.awt.Font("Roboto Medium", 0, 14)); 
        btnLogout.setIcon(new FlatSVGIcon("./icon/logout.svg"));
        btnLogout.setText("Logout");
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.setFocusPainted(false);
        btnLogout.setFocusable(false);
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnLogout.setIconTextGap(16);
        btnLogout.setPreferredSize(new java.awt.Dimension(230, 50));
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

       

        hoaDonItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        hoaDonItem.setIcon(new FlatSVGIcon("./icon/bill.svg"));
        hoaDonItem.setText("Hóa đơn");
        hoaDonItem.setBorderPainted(false);
        hoaDonItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hoaDonItem.setFocusPainted(false);
        hoaDonItem.setFocusable(false);
        hoaDonItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        hoaDonItem.setIconTextGap(16);
        hoaDonItem.setPreferredSize(new java.awt.Dimension(140, 60));
        hoaDonItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoaDonItemActionPerformed(evt);
            }
        });
        itemPanel.add(hoaDonItem);

     // Tạo menu thả xuống JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setPreferredSize(new java.awt.Dimension(170, 120)); // Điều chỉnh kích thước tổng thể của menu
        popupMenu.setBorderPainted(false);

        popupMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        // Tạo các item trong menu với biểu tượng và văn bản
        JMenuItem themItem = new JMenuItem("THÊM", new FlatSVGIcon("./icon/add.svg", 24, 24)); // Đặt kích thước biểu tượng
        JMenuItem suaItem = new JMenuItem("SỬA", new FlatSVGIcon("./icon/update.svg", 24, 24));
        JMenuItem xoaItem = new JMenuItem("XÓA", new FlatSVGIcon("./icon/delete.svg", 24, 24));

        // Chỉnh khoảng cách giữa biểu tượng và văn bản
        themItem.setIconTextGap(10); // Điều chỉnh khoảng cách giữa biểu tượng và văn bản
        suaItem.setIconTextGap(10);
        xoaItem.setIconTextGap(10);

        // Chỉnh đệm (padding) cho từng mục trong menu để có khoảng cách rõ ràng hơn
        themItem.setMargin(new Insets(5, 10, 5, 10));
        suaItem.setMargin(new Insets(5, 10, 5, 10));
        xoaItem.setMargin(new Insets(5, 10, 5, 10));

        // Chỉnh phông chữ để văn bản hiển thị rõ ràng và đồng bộ với biểu tượng
        themItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
        suaItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));
        xoaItem.setFont(new java.awt.Font("Roboto Medium", 0, 14));

        // Thêm các item vào popupMenu
        popupMenu.add(themItem);
        popupMenu.add(new JSeparator());
        popupMenu.add(suaItem);
        popupMenu.add(new JSeparator());
        popupMenu.add(xoaItem);


        
        // Gán sự kiện cho các item trong menu
//        themItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                btnAddActionPerformed(evt); // Gọi hàm thực hiện cho nút "THÊM"
//            }
//        });

//        suaItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                btnUpdateActionPerformed(evt); // Gọi hàm thực hiện cho nút "SỬA"
//            }
//        });

//        xoaItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                btnDeleteActionPerformed(evt); // Gọi hàm thực hiện cho nút "XÓA"
//            }
//        });

        // Thêm các item vào popupMenu
       
        
        khachHangItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        khachHangItem.setIcon(new FlatSVGIcon("./icon/customer.svg"));
        khachHangItem.setText("Khách hàng");
        khachHangItem.setBorderPainted(false);
        khachHangItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        khachHangItem.setFocusPainted(false);
        khachHangItem.setFocusable(false);
        khachHangItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        khachHangItem.setIconTextGap(16);
        khachHangItem.setPreferredSize(new java.awt.Dimension(170, 60));
     // Tạo biến để lưu trạng thái hiện/ẩn của menu
        isMenuVisible = false; // Ban đầu menu đang ẩn

        khachHangItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (isMenuVisible) {
                    // Nếu menu đang hiện, ẩn nó đi
                    popupMenu.setVisible(false);
                    isMenuVisible = false; // Cập nhật trạng thái
                } else {
                    // Nếu menu đang ẩn, tính toán vị trí và hiển thị popupMenu
                    int x = khachHangItem.getLocationOnScreen().x - khachHangItem.getParent().getLocationOnScreen().x;
                    int y = khachHangItem.getLocationOnScreen().y - khachHangItem.getParent().getLocationOnScreen().y + khachHangItem.getHeight();
                    
                    // Hiển thị menu thả xuống ngay dưới nút khachHangItem
                    popupMenu.show(khachHangItem.getParent(), x, y);
                    isMenuVisible = true; // Cập nhật trạng thái
                }
            }
        });


        itemPanel.add(khachHangItem);

        thuocItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        thuocItem.setIcon(new FlatSVGIcon("./icon/medicine.svg"));
        thuocItem.setText("Thuốc");
        thuocItem.setBorderPainted(false);
        thuocItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        thuocItem.setFocusPainted(false);
        thuocItem.setFocusable(false);
        thuocItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        thuocItem.setIconTextGap(16);
        thuocItem.setPreferredSize(new java.awt.Dimension(140, 60));
        thuocItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thuocItemActionPerformed(evt);
            }
        });
        itemPanel.add(thuocItem);

        phieuNhapItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        phieuNhapItem.setIcon(new FlatSVGIcon("./icon/bill-import.svg"));
        phieuNhapItem.setText("Phiếu nhập");
        phieuNhapItem.setBorderPainted(false);
        phieuNhapItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        phieuNhapItem.setFocusPainted(false);
        phieuNhapItem.setFocusable(false);
        phieuNhapItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        phieuNhapItem.setIconTextGap(16);
        phieuNhapItem.setPreferredSize(new java.awt.Dimension(170, 60));
        phieuNhapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phieuNhapItemActionPerformed(evt);
            }
        });
        itemPanel.add(phieuNhapItem);
        
        thongKeItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        thongKeItem.setIcon(new FlatSVGIcon("./icon/statistics.svg"));
        thongKeItem.setText("Thống Kê");
        thongKeItem.setBorderPainted(false);
        thongKeItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        thongKeItem.setFocusPainted(false);
        thongKeItem.setFocusable(false);
        thongKeItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        thongKeItem.setIconTextGap(16);
        thongKeItem.setPreferredSize(new java.awt.Dimension(140, 60));
        thongKeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thongKeItemActionPerformed(evt);
            }
        });
        itemPanel.add(thongKeItem);

        nhaCungCapItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        nhaCungCapItem.setIcon(new FlatSVGIcon("./icon/trucks.svg"));
        nhaCungCapItem.setText("Nhà cung cấp");
        nhaCungCapItem.setBorderPainted(false);
        nhaCungCapItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nhaCungCapItem.setFocusPainted(false);
        nhaCungCapItem.setFocusable(false);
        nhaCungCapItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        nhaCungCapItem.setIconTextGap(16);
        nhaCungCapItem.setPreferredSize(new java.awt.Dimension(170, 60));
        nhaCungCapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhaCungCapItemActionPerformed(evt);
            }
        });
        itemPanel.add(nhaCungCapItem);

        itemPanel.add(jSeparator3);

        nhanVienItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        nhanVienItem.setIcon(new FlatSVGIcon("./icon/employee.svg"));
        nhanVienItem.setText("Nhân viên");
        nhanVienItem.setBorderPainted(false);
        nhanVienItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nhanVienItem.setFocusPainted(false);
        nhanVienItem.setFocusable(false);
        nhanVienItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        nhanVienItem.setIconTextGap(16);
        nhanVienItem.setPreferredSize(new java.awt.Dimension(170, 60));
        nhanVienItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhanVienItemActionPerformed(evt);
            }
        });
        itemPanel.add(nhanVienItem);

        taiKhoanItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        taiKhoanItem.setIcon(new FlatSVGIcon("./icon/account.svg"));
        taiKhoanItem.setText("Tài khoản");
        taiKhoanItem.setBorderPainted(false);
        taiKhoanItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        taiKhoanItem.setFocusPainted(false);
        taiKhoanItem.setFocusable(false);
        taiKhoanItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        taiKhoanItem.setIconTextGap(16);
        taiKhoanItem.setPreferredSize(new java.awt.Dimension(160, 60));
      
        itemPanel.add(taiKhoanItem);

        vaiTroItem.setFont(new java.awt.Font("Roboto Medium", 0, 13)); 
        vaiTroItem.setIcon(new FlatSVGIcon("./icon/security.svg"));
        vaiTroItem.setText("Vai trò");
        vaiTroItem.setBorderPainted(false);
        vaiTroItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vaiTroItem.setFocusPainted(false);
        vaiTroItem.setFocusable(false);
        vaiTroItem.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        vaiTroItem.setIconTextGap(16);
        vaiTroItem.setPreferredSize(new java.awt.Dimension(140, 60));
      
        itemPanel.add(vaiTroItem);

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

    private void taiKhoanItemActionPerformed(java.awt.event.ActionEvent evt) {
        taiKhoan = new TaiKhoanPage();
        this.setPanel(taiKhoan);
        resetActive();
        taiKhoanItem.setSelected(true);
    }

    private void nhanVienItemActionPerformed(java.awt.event.ActionEvent evt) {
        nhanVien = new NhanVienPage();
        this.setPanel(nhanVien);
        resetActive();
        nhanVienItem.setSelected(true);
    }

    private void khachHangItemActionPerformed(java.awt.event.ActionEvent evt) {
        khachHang = new KhachHangPage();
        this.setPanel(khachHang);
        resetActive();
        khachHangItem.setSelected(true);
    }

    private void nhaCungCapItemActionPerformed(java.awt.event.ActionEvent evt) {
        nhaCungCap = new NhaCungCapPage();
        this.setPanel(nhaCungCap);
        resetActive();
        nhaCungCapItem.setSelected(true);
    }

    private void hoaDonItemActionPerformed(java.awt.event.ActionEvent evt) {
        hoaDon = new HoaDonPage(this);
        this.setPanel(hoaDon);
        resetActive();
        hoaDonItem.setSelected(true);
    }

    private void thuocItemActionPerformed(java.awt.event.ActionEvent evt) {
        thuoc = new ThuocPage(this);
        this.setPanel(thuoc);
        resetActive();
        thuocItem.setSelected(true);
    }

    private void vaiTroItemActionPerformed(java.awt.event.ActionEvent evt) {
        vaiTro = new VaiTroPage();
        this.setPanel(vaiTro);
        resetActive();
        vaiTroItem.setSelected(true);
    }

    private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {
        InfoDialog dialog = new InfoDialog(this, true, this, tk);
        dialog.setVisible(true);
    }

    private void phieuNhapItemActionPerformed(java.awt.event.ActionEvent evt) {
        phieuNhap = new PhieuNhapPage(this);
        this.setPanel(phieuNhap);
        resetActive();
        phieuNhapItem.setSelected(true);
    }

    private void thongKeItemActionPerformed(java.awt.event.ActionEvent evt) {
        thongke = new ThongKePage(tk);
        this.setPanel(thongke);
        resetActive();
        thongKeItem.setSelected(true);
    }


    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton hoaDonItem;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JPanel itemPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton khachHangItem;
    private javax.swing.JPanel leftContent;
    private javax.swing.JPanel topMenuPanel;
    private javax.swing.JPanel mainContent;
    private javax.swing.JButton nhaCungCapItem;
    private javax.swing.JButton nhanVienItem;
    private javax.swing.JButton phieuNhapItem;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JButton taiKhoanItem;
    private javax.swing.JButton thongKeItem;
    private javax.swing.JButton thuocItem;
    private javax.swing.JLabel txtFullName;
    private javax.swing.JLabel txtRole;
    private javax.swing.JButton vaiTroItem;
}

package gui.page.thongke;

import entity.TaiKhoan;


public class ThongKePage extends javax.swing.JPanel {

    private TaiKhoan tk;
    
    public ThongKePage() {
        initComponents();
        initLayout();
    }
    
    public ThongKePage(TaiKhoan tk) {
        this.tk = tk;
        initComponents();
        initLayout();
        checkRole();
    }
    
    private void checkRole() {
        String role = tk.getVaiTro().getId();
        
        if (role.equals("nvql") || role.equals("admin")) {
            tabPane.setEnabledAt(1, true);
        } else {
            tabPane.setEnabledAt(1, false);
        }
    }

    private void initLayout() {

        tabPane.addTab("Tổng quan", new ThongKeTongQuanPage());
        tabPane.addTab("Doanh thu", new ThongKeDoanhThuPage());
        
        this.add(tabPane);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        tabPane = new javax.swing.JTabbedPane();

        setBackground(new java.awt.Color(230, 245, 245));
        setMinimumSize(new java.awt.Dimension(1130, 800));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 6));

        tabPane.setPreferredSize(new java.awt.Dimension(100, 30));
        add(tabPane, java.awt.BorderLayout.PAGE_START);
    }

    private javax.swing.JTabbedPane tabPane;
}

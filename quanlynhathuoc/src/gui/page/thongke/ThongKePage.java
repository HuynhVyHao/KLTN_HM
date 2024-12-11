package gui.page.thongke;

import java.awt.*;

import javax.swing.*;

import entity.TaiKhoan;


public class ThongKePage extends JPanel {

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
        tabPane.addTab("Hạn Sử Dụng", new ThongKeHSDThuocPage());
        tabPane.addTab("Thuốc Bán Chạy", new ThongKeThuocBanChayPage());
        
        this.add(tabPane);
    }

    private void initComponents() {

        tabPane = new JTabbedPane();

        setBackground(new Color(230, 245, 245));
        setMinimumSize(new Dimension(1130, 800));
        setPreferredSize(new Dimension(1130, 800));
        setLayout(new BorderLayout(0, 6));

        tabPane.setPreferredSize(new Dimension(100, 30));
        add(tabPane, BorderLayout.PAGE_START);
    }

    private JTabbedPane tabPane;
}

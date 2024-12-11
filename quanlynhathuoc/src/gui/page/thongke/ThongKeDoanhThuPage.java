package gui.page.thongke;

import java.awt.*;

import javax.swing.*;

public class ThongKeDoanhThuPage extends JPanel {

    public ThongKeDoanhThuPage() {
        initComponents();
        initLayout();
    }

    private void initLayout() {

        tabPane.addTab("Thống kê từng ngày", new ThongKeDoanhThuTungNgayTrongThangPage());
        tabPane.addTab("Thống kê theo tháng", new ThongKeDoanhThuTheoThangPage());
        tabPane.addTab("Thống kê theo năm", new ThongKeDoanhThuTheoNamPage());

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

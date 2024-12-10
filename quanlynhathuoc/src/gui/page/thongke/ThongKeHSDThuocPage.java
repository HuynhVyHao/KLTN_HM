package gui.page.thongke;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.KhachHangController;
import controller.NhanVienController;
import controller.ThongKeController;
import controller.ThuocController;
import entity.ThongKe;
import gui.barchart.ModelChart;
import gui.curvechart.ModelChart2;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.JTableExporter;
import utils.TableSorter;


public class ThongKeHSDThuocPage extends javax.swing.JPanel {

    private DefaultTableModel modal;
    private List<String[]> listThongKeThuoc = new ThuocController().getThongKeThuoc();

    public ThongKeHSDThuocPage() {
        initComponents();
        chartLayout();
        tableLayout();
        loadDataset();
    }

    private void chartLayout() {
        chart.addLegend("Còn hạn sử dụng", new Color(135, 189, 245));
        chart.addLegend("Đã hết hạn sử dụng", new Color(245, 189, 135));
        chart.start();
    }

    private void loadChart() {
        int countConHan = 0;
        int countHetHan = 0;

        for (String[] thongKe : listThongKeThuoc) {
            if (thongKe[0].equals("Còn hạn sử dụng")) {
                countConHan += Integer.parseInt(thongKe[2]);
            } else {
                countHetHan += Integer.parseInt(thongKe[2]);
            }
        }

        chart.addData(new ModelChart("Thống kê thuốc", new double[]{countConHan, countHetHan}));
    }

    private void tableLayout() {
        String[] header = new String[]{"Trạng thái", "Thuốc đang bán", "Tổng số lượng thuốc"};
        modal = new DefaultTableModel();
        modal.setColumnIdentifiers(header);
        table.setModel(modal);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);

        sortTable();
    }

    private void sortTable() {
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
    }

    private void loadTable() {
        modal.setRowCount(0);
        for (String[] thongKe : listThongKeThuoc) {
            modal.addRow(new Object[]{thongKe[0], thongKe[1], thongKe[2]});
        }
    }

    private void loadDataset() {
        chart.clear();
        loadChart();
        loadTable();
        chart.start();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        chart = new gui.barchart.Chart();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btnStatistic = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();

        setBackground(new java.awt.Color(230, 245, 245));
        setMinimumSize(new java.awt.Dimension(1130, 800));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 6));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel1.setLayout(new java.awt.BorderLayout(4, 4));
        jPanel1.add(chart, java.awt.BorderLayout.CENTER);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(456, 300));

        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(table);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.SOUTH);

        jPanel5.setBackground(new java.awt.Color(247, 247, 247));
        jPanel5.setPreferredSize(new java.awt.Dimension(1188, 30));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 0));

        btnStatistic.setBackground(new java.awt.Color(51, 153, 255));
        btnStatistic.setForeground(new java.awt.Color(204, 255, 255));
        btnStatistic.setText("Thống kê");
        btnStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticActionPerformed(evt);
            }
        });
        jPanel5.add(btnStatistic);

        btnReload.setText("Làm mới");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jPanel5.add(btnReload);

        btnExport.setBackground(new java.awt.Color(0, 153, 102));
        btnExport.setForeground(new java.awt.Color(204, 255, 204));
        btnExport.setText("Xuất excel");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        jPanel5.add(btnExport);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    private void btnStatisticActionPerformed(java.awt.event.ActionEvent evt) {
        loadDataset();
    }

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
        JTableExporter.exportJTableToExcel(table);
    }

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
        listThongKeThuoc = new ThuocController().getThongKeThuoc();
        loadDataset();
    }

    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnStatistic;
    private gui.barchart.Chart chart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
}


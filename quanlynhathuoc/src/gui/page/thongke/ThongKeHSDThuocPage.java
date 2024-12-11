package gui.page.thongke;

import controller.ThuocController;
import gui.barchart.Chart;
import gui.barchart.ModelChart;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.JTableExporter;
import utils.TableSorter;


public class ThongKeHSDThuocPage extends JPanel {

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

    private void initComponents() {

        jPanel1 = new JPanel();
        chart = new Chart();
        jScrollPane1 = new JScrollPane();
        table = new JTable();
        jPanel5 = new JPanel();
        btnStatistic = new JButton();
        btnReload = new JButton();
        btnExport = new JButton();

        setBackground(new Color(230, 245, 245));
        setMinimumSize(new Dimension(1130, 800));
        setPreferredSize(new Dimension(1130, 800));
        setLayout(new BorderLayout(0, 6));

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel1.setLayout(new BorderLayout(4, 4));
        jPanel1.add(chart, BorderLayout.CENTER);

        jScrollPane1.setPreferredSize(new Dimension(456, 300));

        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(table);

        jPanel1.add(jScrollPane1, BorderLayout.SOUTH);

        jPanel5.setBackground(new Color(247, 247, 247));
        jPanel5.setPreferredSize(new Dimension(1188, 30));
        jPanel5.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));

        btnStatistic.setBackground(new Color(51, 153, 255));
        btnStatistic.setForeground(new Color(204, 255, 255));
        btnStatistic.setText("Thống kê");
        btnStatistic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnStatisticActionPerformed(evt);
            }
        });
        jPanel5.add(btnStatistic);

        btnReload.setText("Làm mới");
        btnReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jPanel5.add(btnReload);

        btnExport.setBackground(new Color(0, 153, 102));
        btnExport.setForeground(new Color(204, 255, 204));
        btnExport.setText("Xuất excel");
        btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        jPanel5.add(btnExport);

        jPanel1.add(jPanel5, BorderLayout.PAGE_START);

        add(jPanel1, BorderLayout.CENTER);
    }

    private void btnStatisticActionPerformed(ActionEvent evt) {
        loadDataset();
    }

    private void btnExportActionPerformed(ActionEvent evt) {
        JTableExporter.exportJTableToExcel(table);
    }

    private void btnReloadActionPerformed(ActionEvent evt) {
        listThongKeThuoc = new ThuocController().getThongKeThuoc();
        loadDataset();
    }

    private JButton btnExport;
    private JButton btnReload;
    private JButton btnStatistic;
    private Chart chart;
    private JPanel jPanel1;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JTable table;
}


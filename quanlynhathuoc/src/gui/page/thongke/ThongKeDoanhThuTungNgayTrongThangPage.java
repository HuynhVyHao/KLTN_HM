package gui.page.thongke;

import controller.ThongKeController;
import entity.ThongKe;
import gui.barchart.Chart;
import gui.barchart.ModelChart;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JMonthChooser;
import com.toedter.components.JSpinField;

import utils.Formatter;
import utils.JTableExporter;
import utils.TableSorter;


public class ThongKeDoanhThuTungNgayTrongThangPage extends JPanel {

    private final int currentMonth = LocalDate.now().getMonthValue();
    private final int currentYear = LocalDate.now().getYear();
    private List<ThongKe> listTK = new ThongKeController().getStatisticDaysByMonthYear(currentMonth, currentYear);

    private DefaultTableModel modal;

    public ThongKeDoanhThuTungNgayTrongThangPage() {
        initComponents();
        chartLayout();
        tableLayout();
        loadDataset();
    }

    private void chartLayout() {
        txtMonth.setMonth(currentMonth - 1);
        txtYear.setValue(currentYear);

        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Chi phí", new Color(245, 189, 135));
        chart.addLegend("Lợi nhuận", new Color(139, 225, 196));

        chart.start();
    }

    private void loadChart() {
        double sum_doanhthu = 0;
        double sum_chiphi = 0;
        double sum_loinhuan = 0;

        for (int day = 0; day < listTK.size(); day++) {
            sum_doanhthu += listTK.get(day).getDoanhThu();
            sum_chiphi += listTK.get(day).getChiPhi();
            sum_loinhuan += listTK.get(day).getLoiNhuan();
            if ((day + 1) % 3 == 0 || day == listTK.size() - 1) {
                int startDay = day - 2;
                if (startDay < 0) {
                    startDay = 0;
                }
                int endDay = day;
                chart.addData(new ModelChart("Ngày " + (startDay + 1) + " - " + (endDay + 1), new double[]{sum_doanhthu, sum_chiphi, sum_loinhuan}));
                sum_doanhthu = 0;
                sum_chiphi = 0;
                sum_loinhuan = 0;
            }
        }
    }

    private void tableLayout() {
        String[] header = new String[]{"Thời gian", "Doanh thu", "Chi phí", "Lợi nhuận"};
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
        for (ThongKe e : listTK) {
            modal.addRow(new Object[]{
                Formatter.FormatDate(e.getThoiGian()), Formatter.FormatVND(e.getDoanhThu()), Formatter.FormatVND(e.getChiPhi()), Formatter.FormatVND(e.getLoiNhuan())
            });
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
        lblChart = new JLabel();
        txtMonth = new JMonthChooser();
        lblChart1 = new JLabel();
        txtYear = new JSpinField();
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

        lblChart.setFont(new Font("Roboto", 0, 12)); 
        lblChart.setHorizontalAlignment(SwingConstants.RIGHT);
        lblChart.setText("Tháng");
        lblChart.setHorizontalTextPosition(SwingConstants.CENTER);
        lblChart.setPreferredSize(new Dimension(60, 30));
        jPanel5.add(lblChart);

        txtMonth.setPreferredSize(new Dimension(130, 26));
        jPanel5.add(txtMonth);

        lblChart1.setFont(new Font("Roboto", 0, 12)); 
        lblChart1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblChart1.setText("Năm");
        lblChart1.setHorizontalTextPosition(SwingConstants.CENTER);
        lblChart1.setPreferredSize(new Dimension(40, 30));
        jPanel5.add(lblChart1);

        txtYear.setPreferredSize(new Dimension(80, 26));
        jPanel5.add(txtYear);

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

    private boolean isValidFilterFields() {
        return true;
    }

    private void btnStatisticActionPerformed(ActionEvent evt) {
        if (isValidFilterFields()) {
            int mounth = txtMonth.getMonth() + 1;
            int year = txtYear.getValue();

            listTK = new ThongKeController().getStatisticDaysByMonthYear(mounth, year);
            loadDataset();
        }
    }

    private void btnExportActionPerformed(ActionEvent evt) {
        JTableExporter.exportJTableToExcel(table);
    }

    private void btnReloadActionPerformed(ActionEvent evt) {
        txtMonth.setMonth(currentMonth - 1);
        txtYear.setValue(currentYear);

        listTK = new ThongKeController().getStatisticDaysByMonthYear(currentMonth, currentYear);
        loadDataset();
    }

    private JButton btnExport;
    private JButton btnReload;
    private JButton btnStatistic;
    private Chart chart;
    private JPanel jPanel1;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JLabel lblChart;
    private JLabel lblChart1;
    private JTable table;
    private JMonthChooser txtMonth;
    private JSpinField txtYear;
}

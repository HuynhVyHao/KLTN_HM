package gui.page.thongke;

import controller.ThongKeController;
import entity.ThongKeTheoNam;
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
import utils.Formatter;
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;
import utils.Validation;


public class ThongKeDoanhThuTheoNamPage extends JPanel {

    private final int currentYear = LocalDate.now().getYear();
    private List<ThongKeTheoNam> listTK = new ThongKeController().getStatisticFromYearToYear(currentYear - 5, currentYear);

    private DefaultTableModel modal;

    public ThongKeDoanhThuTheoNamPage() {
        initComponents();
        chartLayout();
        tableLayout();
        loadDataset();
    }

    private void chartLayout() {
        chart.addLegend("Doanh thu", new Color(135, 189, 245));
        chart.addLegend("Chi phí", new Color(245, 189, 135));
        chart.addLegend("Lợi nhuận", new Color(139, 225, 196));

        chart.start();
    }

    private void loadChart() {
        for (ThongKeTheoNam e : listTK) {
            chart.addData(new ModelChart("Năm " + e.getNam(), new double[]{e.getDoanhThu(), e.getChiPhi(), e.getLoiNhuan()}));
        }
    }

    private void tableLayout() {
        String[] header = new String[]{"Năm", "Doanh thu", "Chi phí", "Lợi nhuận"};
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
        for (ThongKeTheoNam e : listTK) {
            modal.addRow(new Object[]{
                e.getNam() + "", Formatter.FormatVND(e.getDoanhThu()), Formatter.FormatVND(e.getChiPhi()), Formatter.FormatVND(e.getLoiNhuan())
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
        txtFromYear = new JTextField();
        lblChart1 = new JLabel();
        txtToYear = new JTextField();
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
        lblChart.setText("Từ năm");
        lblChart.setHorizontalTextPosition(SwingConstants.CENTER);
        lblChart.setPreferredSize(new Dimension(60, 30));
        jPanel5.add(lblChart);
        jPanel5.add(txtFromYear);

        lblChart1.setFont(new Font("Roboto", 0, 12)); 
        lblChart1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblChart1.setText("Đến năm");
        lblChart1.setHorizontalTextPosition(SwingConstants.CENTER);
        lblChart1.setPreferredSize(new Dimension(60, 30));
        jPanel5.add(lblChart1);
        jPanel5.add(txtToYear);

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
        if (Validation.isEmpty(txtFromYear.getText().trim())) {
            MessageDialog.warring(this, "Không được để trống!");
            Validation.resetTextfield(txtFromYear);
            return false;
        }
        if (Validation.isEmpty(txtToYear.getText().trim())) {
            MessageDialog.warring(this, "Không được để trống!");
            Validation.resetTextfield(txtToYear);
            return false;
        }

        int fromYear = Integer.parseInt(txtFromYear.getText());
        int toYear = Integer.parseInt(txtToYear.getText());

        try {
            if (fromYear <= 1900 || fromYear > currentYear
                    && toYear <= 1900 || toYear > currentYear) {
                MessageDialog.warring(this, "Số năm phải từ 1900 đến " + currentYear);
                return false;
            }
            if (toYear < fromYear) {
                MessageDialog.warring(this, "Số năm kết thúc phải >= năm bắt đầu!");
                Validation.selectAllTextfield(txtToYear);
                return false;
            }
            if (toYear - fromYear >= 10) {
                MessageDialog.warring(this, "Hai năm không cách nhau quá 10 năm");
                Validation.selectAllTextfield(txtFromYear);
                return false;
            }
        } catch (NumberFormatException e) {
            MessageDialog.warring(this, "Số không hợp lệ!");
            Validation.resetTextfield(txtFromYear);
            return false;
        }

        return true;
    }

    private void btnStatisticActionPerformed(ActionEvent evt) {
        if (isValidFilterFields()) {
            int fromYear = Integer.parseInt(txtFromYear.getText());
            int toYear = Integer.parseInt(txtToYear.getText());

            listTK = new ThongKeController().getStatisticFromYearToYear(fromYear, toYear);
            loadDataset();
        }
    }

    private void btnExportActionPerformed(ActionEvent evt) {
        JTableExporter.exportJTableToExcel(table);
    }

    private void btnReloadActionPerformed(ActionEvent evt) {
        txtFromYear.setText("");
        txtToYear.setText("");

        listTK = new ThongKeController().getStatisticFromYearToYear(currentYear - 5, currentYear);
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
    private JTextField txtFromYear;
    private JTextField txtToYear;
}

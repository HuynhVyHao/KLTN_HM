package gui.page.thongke;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.KhachHangController;
import controller.NhanVienController;
import controller.ThongKeController;
import controller.ThuocController;
import entity.ThongKe;
import gui.curvechart.CurveChart;
import gui.curvechart.ModelChart2;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import utils.Formatter;
import utils.TableSorter;


public class ThongKeTongQuanPage extends JPanel {

    private final List<ThongKe> listTK = new ThongKeController().getStatistic7DaysAgo();

    private DefaultTableModel modal;

    public ThongKeTongQuanPage() {
        initComponents();
        initChart();
        initHeader();
        tableLayout();
    }

    private void initHeader() {
        int tongThuoc = new ThuocController().getSoLuongThuoc();
        txtTongThuoc.setText(String.valueOf(tongThuoc));

        int tongKH = new KhachHangController().getSoLuongKH();
        txtTongKhachHang.setText(String.valueOf(tongKH));

        int tongNV = new NhanVienController().getSoLuongNV();
        txtTongNhanVien.setText(String.valueOf(tongNV));
    }

    private void initChart() {
        lblChart.setText("thống kê doanh thu 7 ngày gần nhất".toUpperCase());

        curveChart.addLegend("Doanh thu", new Color(0, 102, 204), new Color(102, 178, 255)); // Xanh dương đậm -> Xanh nhạt
        curveChart.addLegend("Chi phí", new Color(204, 0, 0), new Color(255, 102, 102));     // Đỏ đậm -> Đỏ nhạt
        curveChart.addLegend("Lợi nhuận", new Color(0, 153, 76), new Color(102, 204, 153));  // Xanh lá đậm -> Xanh lá nhạt

        loadDataChart();

        curveChart.start();
    }

    public void loadDataChart() {
        for (ThongKe e : listTK) {
            curveChart.addData(new ModelChart2(Formatter.FormatDate(e.getThoiGian()), new double[]{e.getDoanhThu(), e.getChiPhi(), e.getLoiNhuan()}));
        }
    }
    
    private void tableLayout() {
        String[] header = new String[]{"STT", "Thời gian", "Doanh thu", "Chi phí", "Lợi nhuận"};
        modal = new DefaultTableModel();
        modal.setColumnIdentifiers(header);
        table.setModel(modal);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);

        loadTable(listTK);
        sortTable();
    }

    private void sortTable() {
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
    }


    public void loadTable(List<ThongKe> list) {
        modal.setRowCount(0);
        int stt = 1;
        for (ThongKe e : list) {
            modal.addRow(new Object[]{
                stt, Formatter.FormatDate(e.getThoiGian()), Formatter.FormatVND(e.getDoanhThu()), Formatter.FormatVND(e.getChiPhi()), Formatter.FormatVND(e.getLoiNhuan())
            });
            stt++;
        }
    }

    private void initComponents() {
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jPanel7 = new JPanel();
        jLabel1 = new JLabel();
        jPanel6 = new JPanel();
        txtTongThuoc = new JLabel();
        jLabel3 = new JLabel();
        jPanel8 = new JPanel();
        jPanel9 = new JPanel();
        jLabel2 = new JLabel();
        jPanel10 = new JPanel();
        txtTongKhachHang = new JLabel();
        jLabel4 = new JLabel();
        jPanel11 = new JPanel();
        jPanel12 = new JPanel();
        jLabel5 = new JLabel();
        jPanel13 = new JPanel();
        txtTongNhanVien = new JLabel();
        jLabel6 = new JLabel();
        jPanel1 = new JPanel();
        curveChart = new CurveChart();
        jPanel5 = new JPanel();
        lblChart = new JLabel();
        jPanel4 = new JPanel();
        jScrollPane1 = new JScrollPane();
        table = new JTable();

        setBackground(new Color(230, 245, 245));
        setBorder(new LineBorder(new Color(230, 245, 245), 6, true));
        setMinimumSize(new Dimension(1130, 800));
        setPreferredSize(new Dimension(1130, 800));
        setLayout(new BorderLayout(0, 6));

        jPanel2.setBackground(new Color(230, 245, 245));
        jPanel2.setPreferredSize(new Dimension(100, 110));
        jPanel2.setLayout(new GridLayout(1, 3, 16, 8));

        jPanel3.setBackground(new Color(255, 255, 255));
        jPanel3.setPreferredSize(new Dimension(370, 100));
        jPanel3.setLayout(new BorderLayout());

        jPanel7.setBackground(new Color(255, 255, 255));
        jPanel7.setPreferredSize(new Dimension(120, 100));
        jPanel7.setLayout(new BorderLayout());

        jLabel1.setBackground(new Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(new FlatSVGIcon("./icon/medicine_52.svg"));
        jLabel1.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanel7.add(jLabel1, BorderLayout.CENTER);

        jPanel3.add(jPanel7, BorderLayout.WEST);

        jPanel6.setBackground(new Color(255, 255, 255));
        jPanel6.setPreferredSize(new Dimension(120, 100));

        txtTongThuoc.setFont(new Font("Roboto Mono", 1, 36)); 
        txtTongThuoc.setForeground(new Color(51, 51, 51));
        txtTongThuoc.setText("50");
        txtTongThuoc.setPreferredSize(new Dimension(100, 16));

        jLabel3.setFont(new Font("Roboto", 2, 14)); 
        jLabel3.setForeground(new Color(51, 51, 51));
        jLabel3.setText("Thuốc hiện có đang bán");

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongThuoc, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txtTongThuoc, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel6, BorderLayout.CENTER);

        jPanel2.add(jPanel3);

        jPanel8.setBackground(new Color(255, 255, 255));
        jPanel8.setPreferredSize(new Dimension(370, 100));
        jPanel8.setLayout(new BorderLayout());

        jPanel9.setBackground(new Color(255, 255, 255));
        jPanel9.setPreferredSize(new Dimension(120, 100));
        jPanel9.setLayout(new BorderLayout());

        jLabel2.setBackground(new Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setIcon(new FlatSVGIcon("./icon/customer_52.svg"));
        jLabel2.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanel9.add(jLabel2, BorderLayout.CENTER);

        jPanel8.add(jPanel9, BorderLayout.WEST);

        jPanel10.setBackground(new Color(255, 255, 255));
        jPanel10.setPreferredSize(new Dimension(120, 100));

        txtTongKhachHang.setFont(new Font("Roboto Mono", 1, 36)); 
        txtTongKhachHang.setForeground(new Color(51, 51, 51));
        txtTongKhachHang.setText("50");
        txtTongKhachHang.setPreferredSize(new Dimension(100, 16));

        jLabel4.setFont(new Font("Roboto", 2, 14)); 
        jLabel4.setForeground(new Color(51, 51, 51));
        jLabel4.setText("Khách hàng từ trước đến nay");

        GroupLayout jPanel10Layout = new GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongKhachHang, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txtTongKhachHang, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel10, BorderLayout.CENTER);

        jPanel2.add(jPanel8);

        jPanel11.setBackground(new Color(255, 255, 255));
        jPanel11.setPreferredSize(new Dimension(370, 100));
        jPanel11.setLayout(new BorderLayout());

        jPanel12.setBackground(new Color(255, 255, 255));
        jPanel12.setPreferredSize(new Dimension(120, 100));
        jPanel12.setLayout(new BorderLayout());

        jLabel5.setBackground(new Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setIcon(new FlatSVGIcon("./icon/man_52.svg"));
        jLabel5.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        jLabel5.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanel12.add(jLabel5, BorderLayout.CENTER);

        jPanel11.add(jPanel12, BorderLayout.WEST);

        jPanel13.setBackground(new Color(255, 255, 255));
        jPanel13.setPreferredSize(new Dimension(120, 100));

        txtTongNhanVien.setFont(new Font("Roboto Mono", 1, 36)); 
        txtTongNhanVien.setForeground(new Color(51, 51, 51));
        txtTongNhanVien.setText("50");
        txtTongNhanVien.setPreferredSize(new Dimension(100, 16));

        jLabel6.setFont(new Font("Roboto", 2, 14)); 
        jLabel6.setForeground(new Color(51, 51, 51));
        jLabel6.setText("Nhân viên đang hoạt động");

        GroupLayout jPanel13Layout = new GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongNhanVien, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txtTongNhanVien, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel13, BorderLayout.CENTER);

        jPanel2.add(jPanel11);

        add(jPanel2, BorderLayout.PAGE_START);

        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(curveChart, BorderLayout.CENTER);

        jPanel5.setBackground(new Color(255, 255, 255));
        jPanel5.setPreferredSize(new Dimension(1188, 30));
        jPanel5.setLayout(new BorderLayout());

        lblChart.setFont(new Font("Roboto", 1, 16)); 
        lblChart.setHorizontalAlignment(SwingConstants.CENTER);
        lblChart.setText("Thống kê");
        lblChart.setPreferredSize(new Dimension(37, 30));
        jPanel5.add(lblChart, BorderLayout.PAGE_START);

        jPanel1.add(jPanel5, BorderLayout.PAGE_START);

        add(jPanel1, BorderLayout.CENTER);

        jPanel4.setBackground(new Color(230, 245, 245));
        jPanel4.setPreferredSize(new Dimension(100, 280));
        jPanel4.setLayout(new BorderLayout());

        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(table);

        jPanel4.add(jScrollPane1, BorderLayout.CENTER);

        add(jPanel4, BorderLayout.PAGE_END);
    }

    private CurveChart curveChart;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel13;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JScrollPane jScrollPane1;
    private JLabel lblChart;
    private JTable table;
    private JLabel txtTongKhachHang;
    private JLabel txtTongNhanVien;
    private JLabel txtTongThuoc;
}

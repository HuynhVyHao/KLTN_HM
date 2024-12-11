package gui.page.thongke;

import controller.ThuocController;
import gui.barchart.Chart;
import gui.barchart.ModelChart;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import utils.Formatter;
import utils.JTableExporter;
import utils.TableSorter;

public class ThongKeThuocBanChayPage extends JPanel {

	private DefaultTableModel modal;
	private List<String[]> listThongKeThuocBanChay = new ThuocController().selectThongKeThuocBanChay();

	public ThongKeThuocBanChayPage() {
		initComponents();
		chartLayout();
		tableLayout();
		loadDataset();
	}

	private void chartLayout() {
		chart.addLegend("Thuốc bán chạy", new Color(135, 189, 245));
		chart.start();
	}

	private void loadChart() {
	    chart.clear();
	    for (int i = 0; i < listThongKeThuocBanChay.size(); i++) {
	        String[] thongKe = listThongKeThuocBanChay.get(i);
	        String tenThuoc = thongKe[1]; // Tên thuốc
	        double tongSoLuong = Double.parseDouble(thongKe[2]); // Số lượng bán

	        // Tạo ModelChart với chỉ số lượng bán
	        ModelChart modelChart = new ModelChart(tenThuoc, new double[]{tongSoLuong});
	        chart.addData(modelChart);

	        // Gán màu sắc cho cột tại vị trí `i` sau khi thêm dữ liệu vào biểu đồ
	    }
	}

	private void tableLayout() {
		String[] header = new String[] { "Tên Thuốc", "Số Lượng Bán", "Tổng Doanh Thu" };

		modal = new DefaultTableModel();
		modal.setColumnIdentifiers(header);
		table.setModel(modal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, centerRenderer);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);

		sortTable();
	}

	private void sortTable() {
	    table.setAutoCreateRowSorter(true);
	    TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
	    
	    // Sau khi bảng đã được sắp xếp, cập nhật lại biểu đồ
	    loadChart();
	}


	private void loadTable() {
		modal.setRowCount(0);
		DecimalFormat decimalFormat = new DecimalFormat("#.###");
		for (String[] thongKe : listThongKeThuocBanChay) {
			modal.addRow(new Object[] { thongKe[1], decimalFormat.format(Double.parseDouble(thongKe[2])),Formatter.FormatVND(Double.parseDouble(thongKe[3])) });

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
	    chart = new Chart();// Biểu đồ tròn
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
		listThongKeThuocBanChay = new ThuocController().selectThongKeThuocBanChay();
		loadDataset();
	}

	private JButton btnExport;
	private JButton btnReload;
	private JButton btnStatistic;
	 private Chart chart; // Sử dụng biểu đồ tròn
	private JPanel jPanel1;
	private JPanel jPanel5;
	private JScrollPane jScrollPane1;
	private JTable table;
}

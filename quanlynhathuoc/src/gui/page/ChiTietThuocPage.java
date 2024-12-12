package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.DanhMucController;
import controller.DonViTinhController;
import controller.ThuocController;
import controller.XuatXuController;
import entity.DanhMuc;
import entity.DonViTinh;
import entity.Thuoc;
import entity.XuatXu;
import gui.MainLayout;
import gui.dialog.DetailThuocDialog;
import gui.dialog.ThuocTinhDonViTinhDialog;
import gui.dialog.ThuocTinhXuatXuDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.MessageDialog;
import utils.TableSorter;

public class ChiTietThuocPage extends JPanel {
	private final ThuocController THUOC_CON = new ThuocController(this);
	private List<Thuoc> listThuoc = THUOC_CON.getAllList();

	private DefaultTableModel modal;

	private MainLayout main;

	public ChiTietThuocPage() {
		initComponents();
		headerLayout();
		tableLayout();
	}

	public ChiTietThuocPage(MainLayout main) {
		this.main = main;
		initComponents();
		headerLayout();
		tableLayout();
	}

	private void headerLayout() {
		List<JButton> listButton = new ArrayList<>();
		listButton.add(btnInfo);
		listButton.add(btnDonVi);
		listButton.add(btnXuatXu);

		for (JButton item : listButton) {
			item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		}
	}

	private void tableLayout() {
		lblTable.setText("danh sách thông tin thuốc".toUpperCase());
		String[] header = new String[] { "STT", "Mã thuốc", "Tên thuốc", "Danh mục", "Xuất xứ", "Đơn vị tính",
				"Số lượng", "Đơn giá", "Hạn sử dụng" };
		modal = new DefaultTableModel();
		modal.setColumnIdentifiers(header);
		table.setModel(modal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, centerRenderer);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);

		loadTable(listThuoc);
		sortTable();
	}

	private void sortTable() {
		table.setAutoCreateRowSorter(true);
		TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
	}

	public void loadTable(List<Thuoc> list) {
		modal.setRowCount(0);

		listThuoc = list;
		int stt = 1;
		for (Thuoc e : listThuoc) {
			modal.addRow(new Object[] { String.valueOf(stt), e.getId(), e.getTenThuoc(), e.getDanhMuc().getTen(),
					e.getXuatXu().getTen(), e.getDonViTinh().getTen(), e.getSoLuongTon(),
					Formatter.FormatVND(e.getDonGia()), Formatter.FormatDate(e.getHanSuDung()) });
			stt++;
		}
	}



	private void initComponents() {

		headerPanel = new JPanel();
		actionPanel = new JPanel();
		btnInfo = new JButton();
		btnDonVi = new JButton();
		btnXuatXu = new JButton();
		tablePanel = new JPanel();
		jScrollPane1 = new JScrollPane();
		table = new JTable();
		jPanel5 = new JPanel();
		lblTable = new JLabel();

		setBackground(new Color(230, 245, 245));
		setBorder(new LineBorder(new Color(230, 245, 245), 6, true));
		setMinimumSize(new Dimension(1130, 800));
		setPreferredSize(new Dimension(1130, 800));
		setLayout(new BorderLayout(0, 10));

		headerPanel.setBackground(new Color(255, 255, 255));
		headerPanel.setBorder(new LineBorder(new Color(232, 232, 232), 2, true));
		headerPanel.setLayout(new BorderLayout());

		actionPanel.setBackground(new Color(255, 255, 255));
		actionPanel.setPreferredSize(new Dimension(700, 100));
		actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 5));

		btnInfo.setFont(new Font("Roboto", 1, 14)); 
		btnInfo.setIcon(new FlatSVGIcon("./icon/info.svg"));
		btnInfo.setText("INFO");
		btnInfo.setBorder(null);
		btnInfo.setBorderPainted(false);
		btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnInfo.setFocusPainted(false);
		btnInfo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnInfo.setPreferredSize(new Dimension(90, 90));
		btnInfo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnInfoActionPerformed(evt);
			}
		});
		actionPanel.add(btnInfo);

		btnDonVi.setFont(new Font("Roboto", 1, 10)); 
		btnDonVi.setIcon(new FlatSVGIcon("./icon/study.svg"));
		btnDonVi.setText("ĐƠN VỊ");
		btnDonVi.setBorder(null);
		btnDonVi.setBorderPainted(false);
		btnDonVi.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDonVi.setFocusPainted(false);
		btnDonVi.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDonVi.setPreferredSize(new Dimension(100, 90));
		btnDonVi.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDonVi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDonViActionPerformed(evt);
			}
		});
		actionPanel.add(btnDonVi);

		btnXuatXu.setFont(new Font("Roboto", 1, 10)); 
		btnXuatXu.setIcon(new FlatSVGIcon("./icon/countries.svg"));
		btnXuatXu.setText("XUẤT XỨ");
		btnXuatXu.setBorder(null);
		btnXuatXu.setBorderPainted(false);
		btnXuatXu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnXuatXu.setFocusPainted(false);
		btnXuatXu.setHorizontalTextPosition(SwingConstants.CENTER);
		btnXuatXu.setPreferredSize(new Dimension(100, 90));
		btnXuatXu.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnXuatXu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnXuatXuActionPerformed(evt);
			}
		});
		actionPanel.add(btnXuatXu);

		headerPanel.add(actionPanel, BorderLayout.WEST);

		add(headerPanel, BorderLayout.PAGE_START);

		tablePanel.setBackground(new Color(243, 243, 243));
		tablePanel.setBorder(new LineBorder(new Color(230, 230, 230), 2, true));
		tablePanel.setLayout(new BorderLayout(2, 0));

		table.setFocusable(false);
		table.setRowHeight(40);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowHorizontalLines(true);
		jScrollPane1.setViewportView(table);
		if (table.getColumnModel().getColumnCount() > 0) {
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
		}

		tablePanel.add(jScrollPane1, BorderLayout.CENTER);

		jPanel5.setBackground(new Color(0, 153, 153));
		jPanel5.setMinimumSize(new Dimension(100, 60));
		jPanel5.setPreferredSize(new Dimension(500, 40));
		jPanel5.setLayout(new BorderLayout());

		lblTable.setFont(new Font("Roboto Medium", 0, 18)); 
		lblTable.setForeground(new Color(255, 255, 255));
		lblTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblTable.setText("THÔNG TIN NHÂN VIÊN");
		jPanel5.add(lblTable, BorderLayout.CENTER);

		tablePanel.add(jPanel5, BorderLayout.NORTH);

		add(tablePanel, BorderLayout.CENTER);
	}




	private void btnInfoActionPerformed(ActionEvent evt) {
		try {
			int row = table.getSelectedRow();
			String id = table.getValueAt(row, 1).toString();
			Thuoc thuoc = THUOC_CON.selectById(id);

			DetailThuocDialog dialog = new DetailThuocDialog(null, true, thuoc);
			dialog.setVisible(true);
		} catch (Exception e) {
			MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
		}
	}

	private void btnDonViActionPerformed(ActionEvent evt) {
		ThuocTinhDonViTinhDialog dialog = new ThuocTinhDonViTinhDialog(null, true);
		dialog.setVisible(true);
	}

	private void btnXuatXuActionPerformed(ActionEvent evt) {
		ThuocTinhXuatXuDialog dialog = new ThuocTinhXuatXuDialog(null, true);
		dialog.setVisible(true);
	}

	private JPanel actionPanel;
	private JButton btnInfo;
	private JButton btnDonVi;
	private JButton btnXuatXu;
	private JPanel headerPanel;
	private JPanel jPanel5;
	private JScrollPane jScrollPane1;
	private JLabel lblTable;
	private JTable table;
	private JPanel tablePanel;
}

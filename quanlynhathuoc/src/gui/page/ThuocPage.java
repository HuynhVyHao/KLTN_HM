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
import gui.dialog.CreateThuocDialog;
import gui.dialog.DetailThuocDialog;
import gui.dialog.ThuocTinhDonViTinhDialog;
import gui.dialog.ThuocTinhXuatXuDialog;
import gui.dialog.UpdateThuocDialog;

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
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;
import utils.Validation;

public class ThuocPage extends JPanel {

	private final ThuocController THUOC_CON = new ThuocController(this);
	private List<Thuoc> listThuoc = THUOC_CON.getAllList();

	private final List<DonViTinh> listDVT = new DonViTinhController().getAllList();
	private final List<XuatXu> listXX = new XuatXuController().getAllList();
	private final List<DanhMuc> listDM = new DanhMucController().getAllList();

	private DefaultTableModel modal;

	private MainLayout main;

	public ThuocPage() {
		initComponents();
		headerLayout();
		tableLayout();
	}

	public ThuocPage(MainLayout main) {
		this.main = main;
		initComponents();
		headerLayout();
		tableLayout();
	}

	private void headerLayout() {
		List<JButton> listButton = new ArrayList<>();
		listButton.add(btnAdd);
		listButton.add(btnUpdate);
		listButton.add(btnDelete);
		listButton.add(btnImport);
		listButton.add(btnExport);

		for (JButton item : listButton) {
			item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		}
	}

	private void tableLayout() {
	    lblTable.setText("danh sách thông tin thuốc".toUpperCase());
	    String[] header = new String[] { "STT", "Mã thuốc", "Tên thuốc", "Danh mục","Loại Thuốc", "Xuất xứ", "Đơn vị tính",
	            "Số lượng", "Đơn giá", "Ngày sản xuất", "Hạn sử dụng" }; // Thêm "Ngày sản xuất"
	    modal = new DefaultTableModel();
	    modal.setColumnIdentifiers(header);
	    table.setModel(modal);

	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    table.setDefaultRenderer(Object.class, centerRenderer);
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    table.getColumnModel().getColumn(0).setPreferredWidth(30);
	    table.getColumnModel().getColumn(2).setPreferredWidth(200);
	    table.getColumnModel().getColumn(3).setPreferredWidth(150);
	    table.getColumnModel().getColumn(4).setPreferredWidth(100);

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
	        modal.addRow(new Object[]{
	            String.valueOf(stt), 
	            e.getId(), 
	            e.getTenThuoc(), 
	            e.getDanhMuc().getTen(), 
	            e.getLoaiThuoc(),
	            e.getXuatXu().getTen(), 
	            e.getDonViTinh().getTen(),
	            e.getSoLuongTon(), 
	            Formatter.FormatVND(e.getDonGia()), 
	        	Formatter.FormatDate(e.getNgaySanXuat()), // Đảm bảo rằng đây là ngày sản xuất
	            Formatter.FormatDate(e.getHanSuDung())    // Và đây là hạn sử dụng
	        });
	        stt++;
	    }
	}

	private void initComponents() {

		headerPanel = new JPanel();
		actionPanel = new JPanel();
		btnAdd = new JButton();
		btnUpdate = new JButton();
		btnDelete = new JButton();
		btnImport = new JButton();
		btnExport = new JButton();
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

		btnAdd.setFont(new Font("Roboto", 1, 14)); 
		btnAdd.setIcon(new FlatSVGIcon("./icon/add.svg"));
		btnAdd.setText("THÊM");
		btnAdd.setBorder(null);
		btnAdd.setBorderPainted(false);
		btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdd.setFocusPainted(false);
		btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdd.setPreferredSize(new Dimension(90, 90));
		btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAddActionPerformed(evt);
			}
		});
		actionPanel.add(btnAdd);

		btnUpdate.setFont(new Font("Roboto", 1, 14)); 
		btnUpdate.setIcon(new FlatSVGIcon("./icon/update.svg"));
		btnUpdate.setText("SỬA");
		btnUpdate.setBorder(null);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnUpdate.setFocusPainted(false);
		btnUpdate.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUpdate.setPreferredSize(new Dimension(90, 90));
		btnUpdate.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnUpdateActionPerformed(evt);
			}
		});
		actionPanel.add(btnUpdate);

		btnDelete.setFont(new Font("Roboto", 1, 14)); 
		btnDelete.setIcon(new FlatSVGIcon("./icon/delete.svg"));
		btnDelete.setText("XÓA");
		btnDelete.setBorder(null);
		btnDelete.setBorderPainted(false);
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDelete.setFocusPainted(false);
		btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDelete.setPreferredSize(new Dimension(90, 90));
		btnDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDeleteActionPerformed(evt);
			}
		});
		actionPanel.add(btnDelete);
		
		btnImport.setFont(new Font("Roboto", 1, 14)); 
		btnImport.setIcon(new FlatSVGIcon("./icon/import.svg"));
		btnImport.setText("IMPORT");
		btnImport.setBorder(null);
		btnImport.setBorderPainted(false);
		btnImport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnImport.setFocusPainted(false);
		btnImport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImport.setPreferredSize(new Dimension(90, 90));
		btnImport.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnImportActionPerformed(evt);
			}
		});
		actionPanel.add(btnImport);

		btnExport.setFont(new Font("Roboto", 1, 14)); 
		btnExport.setIcon(new FlatSVGIcon("./icon/export.svg"));
		btnExport.setText("EXPORT");
		btnExport.setBorder(null);
		btnExport.setBorderPainted(false);
		btnExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnExport.setFocusPainted(false);
		btnExport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExport.setPreferredSize(new Dimension(90, 90));
		btnExport.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnExportActionPerformed(evt);
			}
		});
		actionPanel.add(btnExport);

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
//		lblTable.setText("THÔNG TIN NHÂN VIÊN");
		jPanel5.add(lblTable, BorderLayout.CENTER);

		tablePanel.add(jPanel5, BorderLayout.NORTH);

		add(tablePanel, BorderLayout.CENTER);
	}

	private void btnAddActionPerformed(ActionEvent evt) {
		CreateThuocDialog dialog = new CreateThuocDialog(null, true, this);
		dialog.setVisible(true);
	}

	private void btnUpdateActionPerformed(ActionEvent evt) {
		try {
			int row = table.getSelectedRow();
			String id = table.getValueAt(row, 1).toString();
			Thuoc thuoc = THUOC_CON.selectById(id);

			UpdateThuocDialog dialog = new UpdateThuocDialog(null, true, this, thuoc);
			dialog.setVisible(true);
		} catch (IndexOutOfBoundsException e) {
			MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
		}
	}

	private void btnDeleteActionPerformed(ActionEvent evt) {
		try {
			int row = table.getSelectedRow();
			String id = table.getValueAt(row, 1).toString();

			if (MessageDialog.confirm(this, "Bạn có chắc chắn xóa dòng này?", "Xóa")) {
				THUOC_CON.deleteById(id);
				MessageDialog.info(this, "Xóa thành công!");
				modal.removeRow(row);
			}
			
			 // Gọi phương thức updateNotificationBadge để cập nhật badge
            if (main != null) {
                main.updateNotificationBadge();  // Cập nhật lại badge thông báo
            }
		} catch (Exception e) {
			MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
		}
	}

	private void btnImportActionPerformed(ActionEvent evt) {
		THUOC_CON.importExcel();
	}

	private void btnExportActionPerformed(ActionEvent evt) {
		JTableExporter.exportJTableToExcel(table);
	}
	private JPanel actionPanel;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JButton btnExport;
	private JButton btnImport;
	private JPanel headerPanel;
	private JPanel jPanel5;
	private JScrollPane jScrollPane1;
	private JLabel lblTable;
	private JTable table;
	private JPanel tablePanel;
}
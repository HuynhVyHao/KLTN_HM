package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;

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
import gui.dialog.UpdateThuocDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;
import utils.Validation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class TimKiemThuocPage extends JPanel {
	private final ThuocController THUOC_CON = new ThuocController(this);
	private List<Thuoc> listThuoc = THUOC_CON.getAllList();

	private final List<DonViTinh> listDVT = new DonViTinhController().getAllList();
	private final List<XuatXu> listXX = new XuatXuController().getAllList();
	private final List<DanhMuc> listDM = new DanhMucController().getAllList();

	private DefaultTableModel modal;

	private MainLayout main;

	public TimKiemThuocPage() {
		initComponents();
		headerLayout();
		tableLayout();
		fillCombobox();
	}

	public TimKiemThuocPage(MainLayout main) {
		this.main = main;
		initComponents();
		headerLayout();
		tableLayout();
		fillCombobox();
	}

	private void headerLayout() {
		List<JButton> listButton = new ArrayList<>();
		listButton.add(btnReload);
		listButton.add(btnThuocTinh);
		listButton.add(btnSubmitHSD);

		// Border radius
		for (JButton item : listButton) {
			item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		}

		txtHSD.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập số ngày...");
		txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
		txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("./icon/search.svg"));

		String[] searchType = { "Tất cả", "Mã", "Tên" };
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
		cboxSearch.setModel(model);
	}

	private void tableLayout() {
	    lblTable.setText("danh sách thông tin thuốc".toUpperCase());
	    String[] header = new String[] { "STT", "Mã thuốc", "Tên thuốc", "Danh mục", "Xuất xứ", "Đơn vị tính",
	            "Số lượng", "Đơn giá", "Ngày sản xuất", "Hạn sử dụng" }; // Thêm cột Ngày sản xuất
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
	        modal.addRow(new Object[] { 
	            String.valueOf(stt), 
	            e.getId(), 
	            e.getTenThuoc(), 
	            e.getDanhMuc().getTen(),
	            e.getXuatXu().getTen(), 
	            e.getDonViTinh().getTen(), 
	            e.getSoLuongTon(), 
	            Formatter.FormatVND(e.getDonGia()), 
	            Formatter.FormatDate(e.getNgaySanXuat()), // Thêm ngày sản xuất
	            Formatter.FormatDate(e.getHanSuDung())    // Hiển thị hạn sử dụng
	        });
	        stt++;
	    }
	}


	private void fillCombobox() {
		cboxDonViTinh.addItem("Tất cả");
		for (DonViTinh e : listDVT) {
			cboxDonViTinh.addItem(e.getTen());
		}

		cboxXuatXu.addItem("Tất cả");
		for (XuatXu e : listXX) {
			cboxXuatXu.addItem(e.getTen());
		}

		cboxDanhMuc.addItem("Tất cả");
		for (DanhMuc e : listDM) {
			cboxDanhMuc.addItem(e.getTen());
		}
	}

	private List<Thuoc> getListFilter() {
	    String tenDM = "";
	    String tenDVT = "";
	    String tenXX = "";
	    Date ngaySanXuat = null;
	    long hanSuDung = 0;

	    // Lấy các giá trị từ combobox
	    if (cboxDanhMuc.getSelectedItem() != null) {
	        tenDM = cboxDanhMuc.getSelectedItem().toString();
	    }
	    if (cboxDonViTinh.getSelectedItem() != null) {
	        tenDVT = cboxDonViTinh.getSelectedItem().toString();
	    }
	    if (cboxXuatXu.getSelectedItem() != null) {
	        tenXX = cboxXuatXu.getSelectedItem().toString();
	    }

	    // Kiểm tra nếu txtNSX khác null và không rỗng
	    if (txtNSX != null && !txtNSX.getText().isEmpty()) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        try {
	            ngaySanXuat = dateFormat.parse(txtNSX.getText());
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }

	    // Kiểm tra và lấy giá trị hạn sử dụng
	    if (!Validation.isEmpty(txtHSD.getText()) || Validation.isNumber(txtHSD.getText())) {
	        hanSuDung = Long.parseLong(txtHSD.getText());
	    }

	    long ngaySanXuatLong = (ngaySanXuat != null) ? ngaySanXuat.getTime() : 0;
	    return THUOC_CON.getFilterTable(tenDM, tenDVT, tenXX, ngaySanXuatLong, hanSuDung);
	}

	private void initComponents() {

		headerPanel = new JPanel();
		jPanel1 = new JPanel();
		jPanel3 = new JPanel();
		cboxSearch = new JComboBox<>();
		txtSearch = new JTextField();
		btnReload = new JButton();
		btnThuocTinh = new JButton();
		tablePanel = new JPanel();
		jScrollPane1 = new JScrollPane();
		table = new JTable();
		jPanel5 = new JPanel();
		lblTable = new JLabel();
		jPanel4 = new JPanel();
		jPanel8 = new JPanel();
		jLabel3 = new JLabel();
		cboxDanhMuc = new JComboBox<>();
		jPanel6 = new JPanel();
		jLabel1 = new JLabel();
		cboxXuatXu = new JComboBox<>();
		jPanel7 = new JPanel();
		jLabel2 = new JLabel();
		cboxDonViTinh = new JComboBox<>();
		jPanel9 = new JPanel();
		jLabel4 = new JLabel();
		jPanel2 = new JPanel();
		txtHSD = new JTextField();
		btnSubmitHSD = new JButton();

		setBackground(new Color(230, 245, 245));
		setBorder(new LineBorder(new Color(230, 245, 245), 6, true));
		setMinimumSize(new Dimension(1130, 800));
		setPreferredSize(new Dimension(1130, 800));
		setLayout(new BorderLayout(0, 10));

		headerPanel.setBackground(new Color(255, 255, 255));
		headerPanel.setBorder(new LineBorder(new Color(232, 232, 232), 2, true));
		headerPanel.setLayout(new BorderLayout());

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setPreferredSize(new Dimension(590, 100));
		jPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 16, 24));

		jPanel3.setBackground(new Color(255, 255, 255));
		jPanel3.setPreferredSize(new Dimension(370, 50));
		jPanel3.setLayout(new FlowLayout(FlowLayout.TRAILING));

		cboxSearch.setToolTipText("");
		cboxSearch.setPreferredSize(new Dimension(100, 40));
		jPanel3.add(cboxSearch);

		txtSearch.setToolTipText("Tìm kiếm");
		txtSearch.setPreferredSize(new Dimension(200, 40));
		txtSearch.setSelectionColor(new Color(230, 245, 245));
		txtSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				txtSearchKeyReleased(evt);
			}
		});
		jPanel3.add(txtSearch);

		btnReload.setIcon(new FlatSVGIcon("./icon/reload.svg"));
		btnReload.setToolTipText("Làm mới");
		btnReload.setBorder(null);
		btnReload.setBorderPainted(false);
		btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnReload.setFocusPainted(false);
		btnReload.setFocusable(false);
		btnReload.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReload.setPreferredSize(new Dimension(40, 40));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnReloadActionPerformed(evt);
			}
		});
		jPanel3.add(btnReload);

		jPanel1.add(jPanel3);

		headerPanel.add(jPanel1, BorderLayout.CENTER);


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

		jPanel4.setBackground(new Color(255, 255, 255));
		jPanel4.setPreferredSize(new Dimension(200, 100));
		jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));

		jPanel8.setBackground(new Color(255, 255, 255));
		jPanel8.setPreferredSize(new Dimension(200, 80));
		jPanel8.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));

		jLabel3.setFont(new Font("Roboto", 0, 14)); 
		jLabel3.setText("Danh mục thuốc");
		jLabel3.setPreferredSize(new Dimension(140, 20));
		jPanel8.add(jLabel3);

		cboxDanhMuc.setToolTipText("");
		cboxDanhMuc.setPreferredSize(new Dimension(170, 40));
		cboxDanhMuc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cboxDanhMucActionPerformed(evt);
			}
		});
		jPanel8.add(cboxDanhMuc);

		jPanel4.add(jPanel8);

		jPanel6.setBackground(new Color(255, 255, 255));
		jPanel6.setPreferredSize(new Dimension(200, 80));
		jPanel6.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));

		jLabel1.setFont(new Font("Roboto", 0, 14)); 
		jLabel1.setText("Xuất xứ");
		jLabel1.setPreferredSize(new Dimension(140, 20));
		jPanel6.add(jLabel1);

		cboxXuatXu.setToolTipText("");
		cboxXuatXu.setPreferredSize(new Dimension(170, 40));
		cboxXuatXu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cboxXuatXuActionPerformed(evt);
			}
		});
		jPanel6.add(cboxXuatXu);

		jPanel4.add(jPanel6);

		jPanel7.setBackground(new Color(255, 255, 255));
		jPanel7.setPreferredSize(new Dimension(200, 80));
		jPanel7.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));

		jLabel2.setFont(new Font("Roboto", 0, 14)); 
		jLabel2.setText("Đơn vị tính");
		jLabel2.setPreferredSize(new Dimension(140, 20));
		jPanel7.add(jLabel2);

		cboxDonViTinh.setToolTipText("");
		cboxDonViTinh.setPreferredSize(new Dimension(170, 40));
		cboxDonViTinh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cboxDonViTinhActionPerformed(evt);
			}
		});
		jPanel7.add(cboxDonViTinh);

		jPanel4.add(jPanel7);

		jPanel9.setBackground(new Color(255, 255, 255));
		jPanel9.setPreferredSize(new Dimension(200, 80));
		jPanel9.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));

		jLabel4.setFont(new Font("Roboto", 0, 14)); 
		jLabel4.setText("Hạn sử dụng còn");
		jLabel4.setPreferredSize(new Dimension(140, 20));
		jPanel9.add(jLabel4);

		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel2.setMinimumSize(new java.awt.Dimension(170, 40));
		jPanel2.setPreferredSize(new Dimension(170, 40));
		jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		txtHSD.setPreferredSize(new Dimension(120, 40));
		jPanel2.add(txtHSD);

		btnSubmitHSD.setIcon(new FlatSVGIcon("./icon/submit.svg"));
		btnSubmitHSD.setBorder(null);
		btnSubmitHSD.setBorderPainted(false);
		btnSubmitHSD.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSubmitHSD.setFocusPainted(false);
		btnSubmitHSD.setOpaque(false);
		btnSubmitHSD.setPreferredSize(new Dimension(40, 40));
		btnSubmitHSD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSubmitHSDActionPerformed(evt);
			}
		});
		
		jPanel9_1 = new JPanel();
		jPanel9_1.setPreferredSize(new Dimension(200, 80));
		jPanel9_1.setBackground(Color.WHITE);
		jPanel4.add(jPanel9_1);
		jPanel9_1.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));
		
		lblNgySnXut = new JLabel();
		lblNgySnXut.setText("Ngày sản xuất");
		lblNgySnXut.setPreferredSize(new Dimension(140, 20));
		lblNgySnXut.setFont(new Font("Dialog", Font.PLAIN, 14));
		jPanel9_1.add(lblNgySnXut);

		jPanel2_1 = new JPanel();
		jPanel2_1.setPreferredSize(new Dimension(170, 40));
		jPanel2_1.setMinimumSize(new Dimension(170, 40));
		jPanel2_1.setBackground(Color.WHITE);
		jPanel9_1.add(jPanel2_1);
		jPanel2_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		// Tạo JDateChooser để chọn ngày
		txtNgaySanXuat = new JDateChooser();
		txtNgaySanXuat.setDateFormatString("dd/MM/yyyy"); // Định dạng ngày
		txtNgaySanXuat.setPreferredSize(new Dimension(160, 40)); // Kích thước cho JDateChooser
		// Thêm sự kiện vào JDateChooser
		txtNgaySanXuat.addPropertyChangeListener("date", new PropertyChangeListener() {
		    public void propertyChange(PropertyChangeEvent evt) {
		        if ("date".equals(evt.getPropertyName())) {
		            Date selectedDate = (Date) evt.getNewValue(); // Lấy ngày mới được chọn
		            if (selectedDate != null) {
		                // Gọi phương thức lọc theo ngày sản xuất
		                filterByProductionDate(selectedDate);
		            }
		        }
		    }
		});



		jPanel2_1.add(txtNgaySanXuat); // Thêm vào panel
		jPanel2.add(btnSubmitHSD);
		jPanel9.add(jPanel2);
		jPanel4.add(jPanel9);

		tablePanel.add(jPanel4, BorderLayout.LINE_START);
		add(tablePanel, BorderLayout.CENTER);
	}
	
	private void filterByProductionDate(Date selectedDate) {
	    // Lấy danh sách thuốc từ cơ sở dữ liệu hoặc danh sách hiện tại
	    List<Thuoc> allMedicines = THUOC_CON.getAllList(); // Giả định có phương thức này để lấy tất cả thuốc
	    List<Thuoc> filteredMedicines = new ArrayList<>();

	    // Lọc thuốc theo ngày sản xuất
	    for (Thuoc thuoc : allMedicines) {
	        // Giả định có phương thức getProductionDate() trả về ngày sản xuất dưới dạng Date
	        Date productionDate = thuoc.getNgaySanXuat(); // Thay đổi nếu cần
	        
	        // So sánh ngày sản xuất
	        if (productionDate != null && isSameDay(productionDate, selectedDate)) {
	            filteredMedicines.add(thuoc);
	        }
	    }

	    // Gọi loadTable để hiển thị danh sách thuốc đã lọc
	    loadTable(filteredMedicines);
	}
	private boolean isSameDay(Date date1, Date date2) {
	    Calendar cal1 = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
	    
	    cal1.setTime(date1);
	    cal2.setTime(date2);
	    
	    return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
	            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));
	}




	private void txtSearchKeyReleased(KeyEvent evt) {
		modal.setRowCount(0);

		String search = txtSearch.getText().toLowerCase().trim();
		String searchType = cboxSearch.getSelectedItem().toString();
		List<Thuoc> listsearch = THUOC_CON.getSearchTable(search, searchType);

		loadTable(listsearch);
	}

	private void btnReloadActionPerformed(ActionEvent evt) {
		txtSearch.setText("");
		txtHSD.setText("");
		cboxSearch.setSelectedIndex(0);
		cboxDanhMuc.setSelectedIndex(0);
		cboxDonViTinh.setSelectedIndex(0);
		cboxXuatXu.setSelectedIndex(0);
		loadTable(listThuoc);
	}

	private void cboxXuatXuActionPerformed(ActionEvent evt) {
		modal.setRowCount(0);

		List<Thuoc> listSearch = getListFilter();

		String tenXX = cboxXuatXu.getSelectedItem().toString();
		if (tenXX.equals("Tất cả")) {
			listSearch = THUOC_CON.getAllList();
		}

		loadTable(listSearch);
	}

	private void cboxDonViTinhActionPerformed(ActionEvent evt) {
		modal.setRowCount(0);

		List<Thuoc> listSearch = getListFilter();

		String tenDVT = cboxDonViTinh.getSelectedItem().toString();
		if (tenDVT.equals("Tất cả")) {
			listSearch = THUOC_CON.getAllList();
		}

		loadTable(listSearch);
	}

	private void cboxDanhMucActionPerformed(ActionEvent evt) {
		modal.setRowCount(0);

		List<Thuoc> listSearch = getListFilter();

		String tenDM = cboxDanhMuc.getSelectedItem().toString();
		if (tenDM.equals("Tất cả")) {
			listSearch = THUOC_CON.getAllList();
		}

		loadTable(listSearch);
	}

	private void btnSubmitHSDActionPerformed(ActionEvent evt) {
		modal.setRowCount(0);

		List<Thuoc> listSearch = getListFilter();

		loadTable(listSearch);
	}

	private JButton btnReload;
	private JButton btnSubmitHSD;
	private JButton btnThuocTinh;
	private JComboBox<String> cboxDanhMuc;
	private JComboBox<String> cboxDonViTinh;
	private JComboBox<String> cboxSearch;
	private JComboBox<String> cboxXuatXu;
	private JPanel headerPanel;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JPanel jPanel6;
	private JPanel jPanel7;
	private JPanel jPanel8;
	private JPanel jPanel9;
	private JScrollPane jScrollPane1;
	private JLabel lblTable;
	private JTable table;
	private JPanel tablePanel;
	private JTextField txtHSD;
	private JTextField txtSearch;
	private JPanel jPanel9_1;
	private JLabel lblNgySnXut;
	private JPanel jPanel2_1;
	private JTextField txtNSX;
	private JDateChooser txtNgaySanXuat;
}


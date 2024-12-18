package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.google.zxing.WriterException;

import controller.ChiTietDatHangController;
import controller.ChiTietHoaDonController;
import controller.DatHangController;
import controller.HoaDonController;
import controller.KhachHangController;
import controller.ThuocController;
import dao.HoaDonDAO;
import entity.ChiTietDatHang;
import entity.ChiTietHoaDon;
import entity.DatHang;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.Thuoc;
import gui.MainLayout;
import gui.dialog.CreateKhachHangDialog;
import mail.EmailSender;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.TableSorter;
import utils.Validation;
import utils.WritePDF;

public class CreateDatHangPage extends JPanel {

    private final ThuocController THUOC_CON = new ThuocController();
    private final DatHangController DH_CON = new DatHangController();
    private final ChiTietDatHangController CTDH_CON = new ChiTietDatHangController();
    private final HoaDonController HD_CON = new HoaDonController();
    private final ChiTietHoaDonController CTHD_CON = new ChiTietHoaDonController();
    private List<ChiTietHoaDon> listCTHD = new ArrayList<>();
    private List<Thuoc> listThuoc = THUOC_CON.getAllList();
    private List<ChiTietDatHang> listCTDH = new ArrayList<>();

    private MainLayout main;
    private TaiKhoan tk;

    private DefaultTableModel modal;
    private DefaultTableModel modalCart;

    public CreateDatHangPage() {
        initComponents();
        pruductLayout();
        billLayout();
        tableThuocLayout();
        tableCartLayout();
    }

    public CreateDatHangPage(MainLayout main, TaiKhoan tk) {
        this.main = main;
        this.tk = tk;
        initComponents();
        pruductLayout();
        billLayout();
        tableThuocLayout();
        tableCartLayout();
    }

    private void pruductLayout() {
        txtSoLuong.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Số lượng...");
        btnReload.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("./icon/search.svg"));

        String[] searchType = {"Tất cả", "Mã", "Tên"};
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
        cboxSearch.setModel(model);
    }

    private void tableThuocLayout() {
        lblThuoc.setText(" thông tin thuốc".toUpperCase());
        String[] header = new String[]{"STT", "Mã thuốc", "Tên thuốc", "Danh mục", "Xuất xứ", "Đơn vị tính", "Số lượng tồn", "Đơn giá"};
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

    public void loadTable(List<Thuoc> list) {
	    modal.setRowCount(0);
	    listThuoc = list;
	    int stt = 1;

	    // Lấy ngày hiện tại
	    Date currentDate = new Date();
	    
	    for (Thuoc e : listThuoc) {
	        // Lấy hạn sử dụng của thuốc
	        Date expiryDate = e.getHanSuDung();
	        
	        // Kiểm tra thuốc chưa hết hạn hoặc gần hết hạn trong vòng 1 tháng
	        if (expiryDate.after(currentDate) || isExpiringSoon(currentDate, expiryDate)) {
	            modal.addRow(new Object[]{
	                String.valueOf(stt), 
	                e.getId(), 
	                e.getTenThuoc(), 
	                e.getDanhMuc().getTen(), 
	                e.getXuatXu().getTen(), 
	                e.getDonViTinh().getTen(),
	                e.getSoLuongTon(), 
	                Formatter.FormatVND(e.getDonGia()), 
	            });
	            stt++;
	        }
	    }
	}

	// Kiểm tra hạn sử dụng gần hết trong vòng 1 tháng
	private boolean isExpiringSoon(Date currentDate, Date expiryDate) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(currentDate);
	    calendar.add(Calendar.MONTH, 1); // Thêm 1 tháng vào ngày hiện tại

	    Date oneMonthLater = calendar.getTime();
	    
	    return expiryDate.before(oneMonthLater) && expiryDate.after(currentDate);
	}

    private void sortTable() {
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
    }

    private void tableCartLayout() {
        modalCart = new DefaultTableModel();
        String[] header = new String[]{"STT", "Tên thuốc", "Số lượng", "Đơn giá"};
        modalCart.setColumnIdentifiers(header);
        tableCart.setModel(modalCart);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        tableCart.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableCart.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableCart.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableCart.getColumnModel().getColumn(1).setPreferredWidth(200);

        // Cho phép chỉnh sửa cột "Số lượng"
        tableCart.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));

        // Lắng nghe sự thay đổi trong bảng khi người dùng sửa số lượng
        tableCart.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 2) {  // Cột "Số lượng"
                    String newQuantityStr = tableCart.getValueAt(row, column).toString();
                    try {
                        int newQuantity = Integer.parseInt(newQuantityStr);

                        // Cập nhật lại số lượng thuốc trong listCTHD
                        ChiTietDatHang chiTiet = listCTDH.get(row);
                        chiTiet.setSoLuong(newQuantity);

                        // Cập nhật lại tổng tiền sau khi thay đổi số lượng
                        loadTableCTHD(listCTDH);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ.");
                    }
                }
            }
        });

        loadTableCTHD(listCTDH);
        sortTable();
    }

    public void loadTableCTHD(List<ChiTietDatHang> list) {
        modalCart.setRowCount(0);

        listCTDH = list;
        int stt = 1;
        double sum = 0;
        for (ChiTietDatHang e : listCTDH) {
            sum += e.getThanhTien();
            modalCart.addRow(new Object[]{String.valueOf(stt), e.getThuoc().getTenThuoc(), e.getSoLuong(), Formatter.FormatVND(e.getDonGia())});
            stt++;
        }
        txtTong.setText(Formatter.FormatVND(sum));
    }

    private void billLayout() {
        btnAddCustomer.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        formatNumberFields();

        txtMaHoaDon.setText(RandomGenerator.getRandomId());
    }

    private void formatNumberFields() {
        String txtDonGiaFormat = Formatter.FormatVND(Double.parseDouble(txtDonGia.getText()));
        txtDonGia.setText(txtDonGiaFormat);
        String txtTongFormat = Formatter.FormatVND(Double.parseDouble(txtTong.getText()));
        txtTong.setText(txtTongFormat);
    }

    private boolean isValidHoaDon() {
        if (listCTDH.isEmpty()) {
            MessageDialog.warring(this, "Vui lòng chọn sản phẩm!");
            return false;
        }

        if (Validation.isEmpty(txtSdtKH.getText())) {
            MessageDialog.warring(this, "Vui lòng chọn khách hàng!");
            txtSdtKH.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isValidChiTietHoaDon() {
        if (Validation.isEmpty(txtSoLuong.getText().trim())) {
            MessageDialog.warring(this, "Số lượng không được để trống!");
            txtSoLuong.requestFocus();
            return false;
        } else {
            try {
                Thuoc selectedThuoc = THUOC_CON.selectById(txtMaThuoc.getText());

                if (selectedThuoc == null) {
                    MessageDialog.warring(this, "Vui lòng chọn sản phẩm");
                    return false;
                }

                int soLuongTon = selectedThuoc.getSoLuongTon();
                int sl = Integer.parseInt(txtSoLuong.getText());
                if (sl < 0) {
                    MessageDialog.warring(this, "Số lượng đưa phải >= 0");
                    txtSoLuong.requestFocus();
                    return false;
                } else if (soLuongTon < sl) {
                    MessageDialog.warring(this, "Không đủ số lượng!");
                    txtSoLuong.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                MessageDialog.warring(this, "Số lượng đưa phải là số!");
                txtSoLuong.requestFocus();
                return false;
            }
        }

        Thuoc selectedThuoc = THUOC_CON.selectById(txtMaThuoc.getText());
        for (ChiTietDatHang cthd : listCTDH) {
            if (cthd.getThuoc().equals(selectedThuoc)) {
                MessageDialog.warring(this, "Thuốc đã tồn tại trong giỏ hàng!");
                return false;
            }
        }

        return true;
    }

    private DatHang getInputDatHang() {
        String idHD = txtMaHoaDon.getText();
        Timestamp thoiGian = new Timestamp(System.currentTimeMillis());
        NhanVien nhanVien = tk.getNhanVien();
        KhachHang khachHang = new KhachHangController().selectBySdt(txtSdtKH.getText());
        double tongTien = Formatter.unformatVND(txtTong.getText());

        // Lấy trạng thái thanh toán từ JComboBox (Chưa thanh toán hoặc Đã thanh toán)
        String trangThaiThanhToan = (String) cboxThanhToan.getSelectedItem();

        // Trả về đối tượng DatHang với tất cả các thông tin bao gồm trạng thái thanh toán
        return new DatHang(idHD, thoiGian, nhanVien, khachHang, tongTien, trangThaiThanhToan);
    }


    private ChiTietDatHang getInputChiTietHoaDon() {
        DatHang datHang = getInputDatHang();
        Thuoc thuoc = THUOC_CON.selectById(txtMaThuoc.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        double donGia = thuoc.getDonGia();

        return new ChiTietDatHang(datHang, thuoc, soLuong, donGia);
    }

    private void initComponents() {

        mainPanel = new JPanel();
        sanPhamPanel = new JPanel();
        jPanel15 = new JPanel();
        lblThuoc = new JLabel();
        jPanel16 = new JPanel();
        jPanel22 = new JPanel();
        txtHinhAnh = new JLabel();
        jPanel24 = new JPanel();
        jPanel17 = new JPanel();
        jLabel10 = new JLabel();
        txtMaThuoc = new JTextField();
        jPanel18 = new JPanel();
        jLabel11 = new JLabel();
        txtTenThuoc = new JTextField();
        jPanel19 = new JPanel();
        jLabel12 = new JLabel();
        jScrollPane3 = new JScrollPane();
        txtThanhPhan = new JTextArea();
        jPanel21 = new JPanel();
        jLabel14 = new JLabel();
        txtDonGia = new JTextField();
        jPanel4 = new JPanel();
        actionPanel = new JPanel();
        jPanel12 = new JPanel();
        jPanel14 = new JPanel();
        cboxSearch = new JComboBox<>();
        txtSearch = new JTextField();
        btnReload = new JButton();
        jPanel13 = new JPanel();
        txtSoLuong = new JTextField();
        btnAddCart = new JButton();
        tablePanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        table = new JTable();
        billPanel = new JPanel();
        cardPanel = new JPanel();
        jScrollPane2 = new JScrollPane();
        tableCart = new JTable();
        jPanel3 = new JPanel();
        jLabel1 = new JLabel();
        jPanel20 = new JPanel();
        btnDeleteCartItem = new JButton();
        billInfoPanel = new JPanel();
        jPanel5 = new JPanel();
        jLabel2 = new JLabel();
        jPanel6 = new JPanel();
        jPanel23 = new JPanel();
        jPanel7 = new JPanel();
        jLabel4 = new JLabel();
        txtMaHoaDon = new JTextField();
        jPanel25 = new JPanel();
        jLabel8 = new JLabel();
        txtSdtKH = new JTextField();
        btnSearchKH = new JButton();
        btnAddCustomer = new JButton();
        jPanel2 = new JPanel();
        jLabel3 = new JLabel();
        txtHoTenKH = new JTextField();
        cboxGioiTinhKH = new JComboBox<>();
        jSeparator1 = new JSeparator();
        jPanel26 = new JPanel();
        jPanel11 = new JPanel();
        jLabel7 = new JLabel();
        txtTong = new JTextField();
        jPanel10 = new JPanel();
        jPanel8 = new JPanel();
        btnHuy = new JButton();
        btnThanhToan = new JButton();

        setBackground(new Color(230, 245, 245));
        setBorder(new LineBorder(new Color(230, 245, 245), 6, true));
        setLayout(new BorderLayout(5, 0));

        mainPanel.setBackground(new Color(230, 245, 245));
        mainPanel.setLayout(new BorderLayout(5, 5));

        sanPhamPanel.setBackground(new Color(255, 255, 255));
        sanPhamPanel.setBorder(new LineBorder(new Color(237, 237, 237), 2, true));
        sanPhamPanel.setPreferredSize(new Dimension(832, 300));
        sanPhamPanel.setLayout(new BorderLayout());

        jPanel15.setBackground(new Color(0, 153, 153));
        jPanel15.setMinimumSize(new Dimension(100, 60));
        jPanel15.setPreferredSize(new Dimension(500, 30));
        jPanel15.setLayout(new BorderLayout());

        lblThuoc.setFont(new Font("Roboto Medium", 0, 14)); 
        lblThuoc.setForeground(new Color(255, 255, 255));
        lblThuoc.setHorizontalAlignment(SwingConstants.CENTER);
        lblThuoc.setText("Thông tin thuốc");
        jPanel15.add(lblThuoc, BorderLayout.CENTER);

        sanPhamPanel.add(jPanel15, BorderLayout.NORTH);

        jPanel16.setBackground(new Color(255, 255, 255));
        jPanel16.setLayout(new BorderLayout(16, 16));

        jPanel22.setBackground(new Color(255, 255, 255));
        jPanel22.setPreferredSize(new Dimension(300, 200));
        jPanel22.setLayout(new BorderLayout(20, 20));

        txtHinhAnh.setBorder(new LineBorder(new Color(230, 230, 230), 4, true));
        txtHinhAnh.setPreferredSize(new Dimension(300, 200));
        jPanel22.add(txtHinhAnh, BorderLayout.CENTER);

        jPanel16.add(jPanel22, BorderLayout.WEST);

        jPanel24.setBackground(new Color(255, 255, 255));

        jPanel17.setBackground(new Color(255, 255, 255));
        jPanel17.setPreferredSize(new Dimension(215, 40));
        jPanel17.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));

        jLabel10.setFont(new Font("Roboto", 0, 14)); 
        jLabel10.setText("Mã thuốc:");
        jLabel10.setMaximumSize(new Dimension(44, 40));
        jLabel10.setPreferredSize(new Dimension(90, 40));
        jPanel17.add(jLabel10);

        txtMaThuoc.setEditable(false);
        txtMaThuoc.setText("ASZX21Z1X");
        txtMaThuoc.setFocusable(false);
        txtMaThuoc.setPreferredSize(new Dimension(120, 40));
        jPanel17.add(txtMaThuoc);

        jPanel18.setBackground(new Color(255, 255, 255));
        jPanel18.setPreferredSize(new Dimension(340, 40));
        jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));

        jLabel11.setFont(new Font("Roboto", 0, 14)); 
        jLabel11.setText("Tên thuốc:");
        jLabel11.setMaximumSize(new Dimension(44, 40));
        jLabel11.setPreferredSize(new Dimension(90, 40));
        jPanel18.add(jLabel11);

        txtTenThuoc.setEditable(false);
        txtTenThuoc.setFocusable(false);
        txtTenThuoc.setPreferredSize(new Dimension(350, 40));
        jPanel18.add(txtTenThuoc);

        jPanel19.setBackground(new Color(255, 255, 255));
        jPanel19.setPreferredSize(new Dimension(215, 40));
        jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));

        jLabel12.setFont(new Font("Roboto", 0, 14)); 
        jLabel12.setText("Thành phần:");
        jLabel12.setMaximumSize(new Dimension(44, 40));
        jLabel12.setPreferredSize(new Dimension(90, 40));
        jPanel19.add(jLabel12);

        jScrollPane3.setPreferredSize(new Dimension(350, 100));

        txtThanhPhan.setEditable(false);
        txtThanhPhan.setColumns(20);
        txtThanhPhan.setLineWrap(true);
        txtThanhPhan.setRows(5);
        txtThanhPhan.setFocusable(false);
        txtThanhPhan.setPreferredSize(new Dimension(320, 40));
        jScrollPane3.setViewportView(txtThanhPhan);

        jPanel19.add(jScrollPane3);

        jPanel21.setBackground(new Color(255, 255, 255));
        jPanel21.setPreferredSize(new Dimension(215, 40));
        jPanel21.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));

        jLabel14.setFont(new Font("Roboto", 0, 14)); 
        jLabel14.setText("Đơn giá:");
        jLabel14.setMaximumSize(new Dimension(44, 40));
        jLabel14.setPreferredSize(new Dimension(90, 40));
        jPanel21.add(jLabel14);

        txtDonGia.setEditable(false);
        txtDonGia.setFont(new Font("Roboto Mono Medium", 0, 14)); 
        txtDonGia.setText("123123");
        txtDonGia.setFocusable(false);
        txtDonGia.setPreferredSize(new Dimension(120, 40));
        jPanel21.add(txtDonGia);

        GroupLayout jPanel24Layout = new GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(612, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel18, GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
                        .addComponent(jPanel17, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel19, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(205, Short.MAX_VALUE)
                .addComponent(jPanel21, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel19, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(66, Short.MAX_VALUE)))
        );

        jPanel16.add(jPanel24, BorderLayout.CENTER);

        sanPhamPanel.add(jPanel16, BorderLayout.CENTER);

        mainPanel.add(sanPhamPanel, BorderLayout.PAGE_START);

        jPanel4.setBackground(new Color(230, 245, 245));
        jPanel4.setPreferredSize(new Dimension(832, 400));
        jPanel4.setLayout(new BorderLayout(0, 5));

        actionPanel.setBackground(new Color(255, 255, 255));
        actionPanel.setBorder(new LineBorder(new Color(237, 237, 237), 2, true));
        actionPanel.setPreferredSize(new Dimension(605, 60));
        actionPanel.setLayout(new BorderLayout());

        jPanel12.setBackground(new Color(255, 255, 255));
        jPanel12.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 8));

        jPanel14.setBackground(new Color(255, 255, 255));
        jPanel14.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        jPanel12.add(jPanel14);

        cboxSearch.setToolTipText("");
        cboxSearch.setPreferredSize(new Dimension(100, 40));
        jPanel12.add(cboxSearch);

        txtSearch.setToolTipText("Tìm kiếm");
        txtSearch.setPreferredSize(new Dimension(200, 40));
        txtSearch.setSelectionColor(new Color(230, 245, 245));
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel12.add(txtSearch);

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
        jPanel12.add(btnReload);

        actionPanel.add(jPanel12, BorderLayout.CENTER);

        jPanel13.setBackground(new Color(255, 255, 255));
        jPanel13.setPreferredSize(new Dimension(260, 60));
        jPanel13.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 8));

        txtSoLuong.setFont(new Font("Roboto", 0, 12)); 
        txtSoLuong.setPreferredSize(new Dimension(120, 40));
        jPanel13.add(txtSoLuong);

        btnAddCart.setBackground(new Color(0, 179, 246));
        btnAddCart.setFont(new Font("Roboto Black", 0, 16)); 
        btnAddCart.setForeground(new Color(255, 220, 0));
        btnAddCart.setIcon(new FlatSVGIcon("./icon/add-to-cart.svg"));
        btnAddCart.setText("THÊM");
        btnAddCart.setBorderPainted(false);
        btnAddCart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddCart.setFocusPainted(false);
        btnAddCart.setFocusable(false);
        btnAddCart.setPreferredSize(new Dimension(120, 40));
        btnAddCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAddCartActionPerformed(evt);
            }
        });
        jPanel13.add(btnAddCart);

        actionPanel.add(jPanel13, BorderLayout.EAST);

        jPanel4.add(actionPanel, BorderLayout.PAGE_START);

        tablePanel.setBackground(new Color(255, 255, 255));
        tablePanel.setBorder(new LineBorder(new Color(237, 237, 237), 2, true));
        tablePanel.setLayout(new BorderLayout());

        jScrollPane1.setBorder(null);

        table.setFocusable(false);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        tablePanel.add(jScrollPane1, BorderLayout.CENTER);

        jPanel4.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(jPanel4, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        billPanel.setBackground(new Color(230, 245, 245));
        billPanel.setPreferredSize(new Dimension(460, 800));
        billPanel.setLayout(new BorderLayout(0, 5));

        cardPanel.setBackground(new Color(255, 255, 255));
        cardPanel.setBorder(new LineBorder(new Color(238, 238, 238), 2, true));
        cardPanel.setPreferredSize(new Dimension(600, 500));
        cardPanel.setLayout(new BorderLayout());

        jScrollPane2.setBorder(new LineBorder(new Color(230, 230, 230), 1, true));

        tableCart.setFocusable(false);
        jScrollPane2.setViewportView(tableCart);

        cardPanel.add(jScrollPane2, BorderLayout.CENTER);

        jPanel3.setBackground(new Color(0, 153, 153));
        jPanel3.setMinimumSize(new Dimension(100, 60));
        jPanel3.setPreferredSize(new Dimension(500, 30));
        jPanel3.setLayout(new BorderLayout());

        jLabel1.setFont(new Font("Roboto Medium", 0, 14)); 
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Giỏ hàng");
        jPanel3.add(jLabel1, BorderLayout.CENTER);

        cardPanel.add(jPanel3, BorderLayout.NORTH);

        jPanel20.setBackground(new Color(255, 255, 255));
        jPanel20.setForeground(new Color(255, 255, 255));
        jPanel20.setPreferredSize(new Dimension(456, 42));
        jPanel20.setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 2));

        btnDeleteCartItem.setBackground(new Color(255, 102, 102));
        btnDeleteCartItem.setFont(new Font("Roboto Mono", 1, 14)); 
        btnDeleteCartItem.setForeground(new Color(255, 255, 255));
        btnDeleteCartItem.setIcon(new FlatSVGIcon("./icon/trash-cart.svg"));
        btnDeleteCartItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeleteCartItem.setPreferredSize(new Dimension(50, 38));
        btnDeleteCartItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteCartItemActionPerformed(evt);
            }
        });
        jPanel20.add(btnDeleteCartItem);

        cardPanel.add(jPanel20, BorderLayout.PAGE_END);

        billPanel.add(cardPanel, BorderLayout.CENTER);

        billInfoPanel.setBackground(new Color(255, 255, 255));
        billInfoPanel.setBorder(new LineBorder(new Color(238, 238, 238), 2, true));
        billInfoPanel.setPreferredSize(new Dimension(500, 400));
        billInfoPanel.setLayout(new BorderLayout());

        jPanel5.setBackground(new Color(0, 153, 153));
        jPanel5.setMinimumSize(new Dimension(100, 60));
        jPanel5.setPreferredSize(new Dimension(500, 30));
        jPanel5.setLayout(new BorderLayout());

        jLabel2.setFont(new Font("Roboto Medium", 0, 14)); 
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Chi tiết");
        jPanel5.add(jLabel2, BorderLayout.CENTER);

        billInfoPanel.add(jPanel5, BorderLayout.NORTH);

        jPanel6.setBackground(new Color(255, 255, 255));
        jPanel6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 8));

        jPanel23.setBackground(new Color(255, 255, 255));
        jPanel23.setPreferredSize(new Dimension(440, 190));
        jPanel23.setLayout(new FlowLayout(FlowLayout.LEFT));

        jPanel7.setBackground(new Color(255, 255, 255));
        jPanel7.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        jLabel4.setFont(new Font("Roboto", 0, 14)); 
        jLabel4.setText("Mã đặt hàng: ");
        jLabel4.setPreferredSize(new Dimension(120, 40));
        jPanel7.add(jLabel4);

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(new Font("Roboto Mono", 1, 14)); 
        txtMaHoaDon.setText("Z2NX8CN1A");
        txtMaHoaDon.setFocusable(false);
        txtMaHoaDon.setPreferredSize(new Dimension(200, 40));
        jPanel7.add(txtMaHoaDon);

        jPanel23.add(jPanel7);

        jPanel25.setBackground(new Color(255, 255, 255));
        jPanel25.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        jLabel8.setFont(new Font("Roboto", 0, 14)); 
        jLabel8.setText("Số điện thoại:");
        jLabel8.setPreferredSize(new Dimension(120, 40));
        jPanel25.add(jLabel8);

        txtSdtKH.setPreferredSize(new Dimension(200, 40));
        jPanel25.add(txtSdtKH);

        btnSearchKH.setIcon(new FlatSVGIcon("./icon/search.svg"));
        btnSearchKH.setBorderPainted(false);
        btnSearchKH.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearchKH.setFocusPainted(false);
        btnSearchKH.setFocusable(false);
        btnSearchKH.setPreferredSize(new Dimension(40, 40));
        btnSearchKH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSearchKHActionPerformed(evt);
            }
        });
        jPanel25.add(btnSearchKH);

        btnAddCustomer.setIcon(new FlatSVGIcon("./icon/add-customer.svg"));
        btnAddCustomer.setBorderPainted(false);
        btnAddCustomer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddCustomer.setFocusPainted(false);
        btnAddCustomer.setFocusable(false);
        btnAddCustomer.setPreferredSize(new Dimension(40, 40));
        btnAddCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });
        jPanel25.add(btnAddCustomer);

        jPanel23.add(jPanel25);

        jPanel2.setBackground(new Color(255, 255, 255));
        jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        jLabel3.setFont(new Font("Roboto", 0, 14)); 
        jLabel3.setText("Tên khách hàng");
        jLabel3.setMaximumSize(new Dimension(44, 40));
        jLabel3.setPreferredSize(new Dimension(120, 40));
        jPanel2.add(jLabel3);

        txtHoTenKH.setPreferredSize(new Dimension(200, 40));
        jPanel2.add(txtHoTenKH);

        jPanel23.add(jPanel2);

        cboxGioiTinhKH.setModel(new DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cboxGioiTinhKH.setPreferredSize(new Dimension(90, 40));
        jPanel23.add(cboxGioiTinhKH);

        jPanel6.add(jPanel23);
        
        jPanelEmail = new JPanel();
        jPanelEmail.setBackground(new Color(255, 255, 255));
        jPanelEmail.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        // Tạo JLabel cho Email
        jLabelEmail = new JLabel("Email:");
        jLabelEmail.setFont(new Font("Roboto", 0, 14));
        jLabelEmail.setPreferredSize(new Dimension(120, 40));
        jPanelEmail.add(jLabelEmail);

        // Tạo JTextField cho Email
        txtEmailKH = new JTextField();
        txtEmailKH.setPreferredSize(new Dimension(200, 40));
        jPanelEmail.add(txtEmailKH);

        // Thêm JPanel Email vào jPanel23
        jPanel23.add(jPanelEmail);

        // Tiếp tục các thao tác với jPanel23, ví dụ như cboxGioiTinhKH
        cboxGioiTinhKH.setModel(new DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cboxGioiTinhKH.setPreferredSize(new Dimension(90, 40));
        jPanel23.add(cboxGioiTinhKH);

        // Cuối cùng, thêm jPanel23 vào jPanel6
        jPanel6.add(jPanel23);

        jSeparator1.setPreferredSize(new Dimension(400, 3));
        jPanel6.add(jSeparator1);

        jPanel26.setBackground(new Color(255, 255, 255));
        jPanel26.setPreferredSize(new Dimension(440, 150));
        jPanel26.setLayout(new FlowLayout(FlowLayout.LEFT));

        jPanel11.setBackground(new Color(255, 255, 255));
        jPanel11.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        jLabel7.setFont(new Font("Roboto", 1, 14)); 
        jLabel7.setForeground(new Color(255, 51, 0));
        jLabel7.setText("Tổng đặt hàng:");
        jLabel7.setPreferredSize(new Dimension(120, 40));
        jPanel11.add(jLabel7);

        txtTong.setEditable(false);
        txtTong.setFont(new Font("Roboto Mono Medium", 0, 14)); 
        txtTong.setForeground(new Color(255, 51, 0));
        txtTong.setText("1000000");
        txtTong.setFocusable(false);
        txtTong.setPreferredSize(new Dimension(200, 40));
        jPanel11.add(txtTong);

        jPanel26.add(jPanel11);

        jPanel10.setBackground(new Color(255, 255, 255));
        jPanel10.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        // Tạo JComboBox với các lựa chọn
        cboxThanhToan = new JComboBox<>(new String[] { "Chưa thanh toán", "Đã thanh toán" });
        cboxThanhToan.setPreferredSize(new Dimension(150, 40));

        // Tạo một panel để chứa JComboBox
        JPanel paymentStatusPanel = new JPanel();
        paymentStatusPanel.setBackground(new Color(255, 255, 255));
        paymentStatusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        JLabel labelThanhToan = new JLabel("Trạng thái thanh toán:");
        labelThanhToan.setFont(new Font("Roboto", 0, 14)); 
        labelThanhToan.setPreferredSize(new Dimension(150, 40));
        paymentStatusPanel.add(labelThanhToan);

        paymentStatusPanel.add(cboxThanhToan);

        // Thêm paymentStatusPanel vào jPanel6 ngay sau jPanel26
        jPanel10.add(paymentStatusPanel);
        jPanel26.add(jPanel10);

        jPanel6.add(jPanel26);

        billInfoPanel.add(jPanel6, BorderLayout.CENTER);

        jPanel8.setBackground(new Color(255, 255, 255));

        btnHuy.setBackground(new Color(255, 102, 102));
        btnHuy.setFont(new Font("Roboto Mono Medium", 0, 16)); 
        btnHuy.setForeground(new Color(255, 255, 255));
        btnHuy.setText("HỦY BỎ");
        btnHuy.setBorderPainted(false);
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHuy.setFocusPainted(false);
        btnHuy.setFocusable(false);
        btnHuy.setPreferredSize(new Dimension(200, 40));
        btnHuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel8.add(btnHuy);

        btnThanhToan.setBackground(new Color(0, 204, 51));
        btnThanhToan.setFont(new Font("Roboto Mono Medium", 0, 16)); 
        btnThanhToan.setForeground(new Color(255, 255, 255));
        btnThanhToan.setText("ĐẶT HÀNG");
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setFocusable(false);
        btnThanhToan.setPreferredSize(new Dimension(200, 40));
        btnThanhToan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
					btnDatHangActionPerformed(evt);
				} catch (WriterException e) {
					e.printStackTrace();
				}
            }
        });
        jPanel8.add(btnThanhToan);

        billInfoPanel.add(jPanel8, BorderLayout.PAGE_END);

        billPanel.add(billInfoPanel, BorderLayout.SOUTH);

        add(billPanel, BorderLayout.EAST);
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
        cboxSearch.setSelectedIndex(0);
        loadTable(THUOC_CON.getAllList());
    }

    private void tableMouseClicked(MouseEvent evt) {
        int row = table.getSelectedRow();
        String idThuoc = modal.getValueAt(row, 1).toString();
        Thuoc e = THUOC_CON.selectById(idThuoc);
        ImageIcon imageIcon = new ImageIcon(
                new ImageIcon(e.getHinhAnh()).getImage().getScaledInstance(txtHinhAnh.getWidth(), txtHinhAnh.getHeight(), Image.SCALE_SMOOTH));
        txtHinhAnh.setIcon(imageIcon);
        txtHinhAnh.setIcon(imageIcon);
        txtMaThuoc.setText(e.getId());
        txtTenThuoc.setText(e.getTenThuoc());
        txtThanhPhan.setText(e.getThanhPhan());
        txtDonGia.setText(Formatter.FormatVND(e.getDonGia()));
    }

    private void btnAddCartActionPerformed(ActionEvent evt) {
        if (isValidChiTietHoaDon()) {
            ChiTietDatHang cthd = getInputChiTietHoaDon();
            listCTDH.add(cthd);
            loadTableCTHD(listCTDH);

            // Update số lượng tồn
            Thuoc thuoc = THUOC_CON.selectById(txtMaThuoc.getText());
            int updatedSoLuongTon = thuoc.getSoLuongTon() - cthd.getSoLuong();
            THUOC_CON.updateSoLuongTon(thuoc, updatedSoLuongTon);
            loadTable(THUOC_CON.getAllList());

            txtSoLuong.setText("");
        }
    }

    private void btnDeleteCartItemActionPerformed(ActionEvent evt) {
        if (MessageDialog.confirm(this, "Bạn có chắc muốc xóa khỏi giỏ hàng?", "Xóa thuốc khỏi giỏ hàng")) {
            if (listCTDH.isEmpty()) {
                MessageDialog.error(this, "Không có sản phẩm trong giỏ hàng!");
                return;
            }

            ChiTietDatHang cthd = listCTDH.get(tableCart.getSelectedRow());
            listCTDH.remove(tableCart.getSelectedRow());
            loadTableCTHD(listCTDH);

            // Update số lượng tồn
            Thuoc thuocCTHD = cthd.getThuoc();
            Thuoc thuoc = listThuoc.get(listThuoc.indexOf(thuocCTHD));
            int updatedSoLuongTon = thuoc.getSoLuongTon() + cthd.getSoLuong();
            THUOC_CON.updateSoLuongTon(thuoc, updatedSoLuongTon);
            loadTable(THUOC_CON.getAllList());
        }
    }

    private void btnSearchKHActionPerformed(ActionEvent evt) {
        KhachHang kh = new KhachHangController().selectBySdt(txtSdtKH.getText());

        if (kh == null) {
            MessageDialog.error(this, "Không tìm thấy khách hàng!");
            txtHoTenKH.setText("");
            cboxGioiTinhKH.setSelectedIndex(0);
            txtHoTenKH.setEnabled(true);
            cboxGioiTinhKH.setEnabled(true);
        } else {
            txtHoTenKH.setText(kh.getHoTen());
            cboxGioiTinhKH.setSelectedItem(kh.getGioiTinh());
            txtHoTenKH.setEnabled(false);
            cboxGioiTinhKH.setEnabled(false);
        }
    }

    private void btnAddCustomerActionPerformed(ActionEvent evt) {
        CreateKhachHangDialog dialog = new CreateKhachHangDialog(null, true, new KhachHangPage());
        dialog.setVisible(true);
    }

    private void btnHuyActionPerformed(ActionEvent evt) {
        if (MessageDialog.confirm(this, "Xác nhận hủy hóa đơn?", "Hủy hóa đơn")) {
            for (ChiTietDatHang cthd : listCTDH) {
                Thuoc thuocCTHD = cthd.getThuoc();
                Thuoc thuoc = listThuoc.get(listThuoc.indexOf(thuocCTHD));
                int updatedSoLuongTon = thuoc.getSoLuongTon() + cthd.getSoLuong();
                THUOC_CON.updateSoLuongTon(thuoc, updatedSoLuongTon);
            }

            main.setPanel(new HoaDonPage(main));
        }
    }
    
    private void btnDatHangActionPerformed(ActionEvent evt) throws WriterException { 
        if (isValidHoaDon()) { // Kiểm tra tính hợp lệ của hóa đơn
            if (MessageDialog.confirm(this, "Xác nhận đặt hàng?", "Đặt hàng")) {
                // Lấy thông tin đơn đặt hàng từ giao diện
                DatHang dh = getInputDatHang();

                // Lưu đơn đặt hàng vào cơ sở dữ liệu
                DH_CON.create(dh);  // Lưu đơn đặt hàng
                CTDH_CON.create(listCTDH);  // Lưu chi tiết đơn đặt hàng

                // Kiểm tra trạng thái thanh toán của đơn đặt hàng
                if (dh.getTrangThai().equalsIgnoreCase("Đã thanh toán")) {
                    // Tạo đối tượng HoaDon từ đơn đặt hàng
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setId(dh.getId());  // Có thể tạo mã hóa đơn riêng nếu cần
                    hoaDon.setTongTien(dh.getTongTien()); 
                    hoaDon.setThoiGian(dh.getThoiGian());
                    hoaDon.setKhachHang(dh.getKhachHang());
                    hoaDon.setNhanVien(dh.getNhanVien());

                    // Tạo ChiTietHoaDon từ các chi tiết đơn hàng và liên kết với HoaDon
                    List<ChiTietHoaDon> listCTHD = new ArrayList<>();
                    for (ChiTietDatHang ctDH : listCTDH) {
                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                        chiTietHoaDon.setThuoc(ctDH.getThuoc());
                        chiTietHoaDon.setSoLuong(ctDH.getSoLuong());
                        chiTietHoaDon.setDonGia(ctDH.getDonGia());
                        chiTietHoaDon.setHoaDon(hoaDon); // Liên kết với hóa đơn
                        listCTHD.add(chiTietHoaDon); // Thêm vào list chi tiết hóa đơn
                    }

                    // Lưu hóa đơn vào cơ sở dữ liệu
                    try {
                        HD_CON.create(hoaDon); // Lưu hóa đơn vào cơ sở dữ liệu
                        CTHD_CON.create(listCTHD); // Lưu chi tiết hóa đơn vào cơ sở dữ liệu
                        System.out.println("HoaDon đã lưu: " + hoaDon.toString());
                    } catch (Exception e) {
                        MessageDialog.error(this, "Lưu hóa đơn thất bại: " + e.getMessage());
                        e.printStackTrace(); // In chi tiết lỗi để dễ dàng debug
                        return;
                    }

                    // Gửi email thông báo đặt hàng thành công
                    String recipientEmail = txtEmailKH.getText(); // Lấy email từ ô nhập liệu
                    try {
                        EmailSender.sendEmail(recipientEmail, dh.getId(), "Đã thanh toán");
                        System.out.println("Email sent successfully.");
                    } catch (Exception e) {
                        MessageDialog.error(this, "Không thể gửi email: " + e.getMessage());
                        e.printStackTrace(); // In ra lỗi chi tiết để dễ dàng debug
                    }

                    // Hỏi người dùng có muốn in hóa đơn không
                    if (MessageDialog.confirm(this, "Bạn có muốn in hóa đơn không?", "In hóa đơn")) {
                        // Thực hiện in hóa đơn
                        try {
                            new WritePDF().printHoaDon(hoaDon, listCTHD);
                        } catch (Exception e) {
                            MessageDialog.error(this, "In hóa đơn thất bại: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                } else if (dh.getTrangThai().equalsIgnoreCase("Chưa thanh toán")) {
                    // Gửi email thông báo đơn hàng đã được đặt
                    String recipientEmail = txtEmailKH.getText(); // Lấy email từ ô nhập liệu
                    try {
                        EmailSender.sendEmail(recipientEmail, dh.getId(), "Chưa thanh toán");
                        System.out.println("Email sent successfully.");
                    } catch (Exception e) {
                        MessageDialog.error(this, "Không thể gửi email: " + e.getMessage());
                        e.printStackTrace(); // In ra lỗi chi tiết để dễ dàng debug
                    }
                }

                // Hiển thị thông báo thành công
                MessageDialog.info(this, "Đặt hàng thành công!");

                // Trở về trang hóa đơn
                main.setPanel(new DatHangPage(main));
            }
        }
    }





    private JPanel actionPanel;
    private JPanel billInfoPanel;
    private JPanel billPanel;
    private JButton btnAddCart;
    private JButton btnAddCustomer;
    private JButton btnDeleteCartItem;
    private JButton btnHuy;
    private JButton btnReload;
    private JTextField txtEmailKH;
    private JButton btnSearchKH;
    private JButton btnThanhToan;
    private JPanel cardPanel;
    private JComboBox<String> cboxGioiTinhKH;
    private JComboBox<String> cboxSearch;
    private JComboBox<String> cboxThanhToan;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel14;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel13;
    private JPanel jPanel14;
    private JPanel jPanel15;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel2;
    private JPanel jPanel20;
    private JPanel jPanel21;
    private JPanel jPanel22;
    private JPanel jPanel23;
    private JPanel jPanel24;
    private JPanel jPanel25;
    private JPanel jPanel26;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JSeparator jSeparator1;
    private JLabel lblThuoc;
    private JLabel jLabelEmail;
    private JPanel mainPanel;
    private JPanel sanPhamPanel;
    private JTable table;
    private JTable tableCart;
    private JPanel tablePanel;
    private JPanel jPanelEmail;
    private JTextField txtDonGia;
    private JLabel txtHinhAnh;
    private JTextField txtHoTenKH;
    private JTextField txtMaHoaDon;
    private JTextField txtMaThuoc;
    private JTextField txtSdtKH;
    private JTextField txtSearch;
    private JTextField txtSoLuong;
    private JTextField txtTenThuoc;
    private JTextArea txtThanhPhan;
    private JTextField txtTong;
}

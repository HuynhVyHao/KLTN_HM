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
import java.awt.Image;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.TableSorter;
import utils.Validation;
import utils.WritePDF;

public class CreateDatHangPage extends javax.swing.JPanel {

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
        for (Thuoc e : listThuoc) {
            modal.addRow(new Object[]{String.valueOf(stt), e.getId(), e.getTenThuoc(), e.getDanhMuc().getTen(),
                e.getXuatXu().getTen(), e.getDonViTinh().getTen(), e.getSoLuongTon(), Formatter.FormatVND(e.getDonGia())});
            stt++;
        }
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

    @SuppressWarnings("unchecked")
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sanPhamPanel = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        lblThuoc = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        txtHinhAnh = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtMaThuoc = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtTenThuoc = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtThanhPhan = new javax.swing.JTextArea();
        jPanel21 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        actionPanel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        cboxSearch = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnReload = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        txtSoLuong = new javax.swing.JTextField();
        btnAddCart = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        billPanel = new javax.swing.JPanel();
        cardPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCart = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        btnDeleteCartItem = new javax.swing.JButton();
        billInfoPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSdtKH = new javax.swing.JTextField();
        btnSearchKH = new javax.swing.JButton();
        btnAddCustomer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtHoTenKH = new javax.swing.JTextField();
        cboxGioiTinhKH = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel26 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTong = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();

        setBackground(new java.awt.Color(230, 245, 245));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 245, 245), 6, true));
        setLayout(new java.awt.BorderLayout(5, 0));

        mainPanel.setBackground(new java.awt.Color(230, 245, 245));
        mainPanel.setLayout(new java.awt.BorderLayout(5, 5));

        sanPhamPanel.setBackground(new java.awt.Color(255, 255, 255));
        sanPhamPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
        sanPhamPanel.setPreferredSize(new java.awt.Dimension(832, 300));
        sanPhamPanel.setLayout(new java.awt.BorderLayout());

        jPanel15.setBackground(new java.awt.Color(0, 153, 153));
        jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel15.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel15.setLayout(new java.awt.BorderLayout());

        lblThuoc.setFont(new java.awt.Font("Roboto Medium", 0, 14)); 
        lblThuoc.setForeground(new java.awt.Color(255, 255, 255));
        lblThuoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThuoc.setText("Thông tin thuốc");
        jPanel15.add(lblThuoc, java.awt.BorderLayout.CENTER);

        sanPhamPanel.add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.BorderLayout(16, 16));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setPreferredSize(new java.awt.Dimension(300, 200));
        jPanel22.setLayout(new java.awt.BorderLayout(20, 20));

        txtHinhAnh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 4, true));
        txtHinhAnh.setPreferredSize(new java.awt.Dimension(300, 200));
        jPanel22.add(txtHinhAnh, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel22, java.awt.BorderLayout.WEST);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(215, 40));
        jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel10.setText("Mã thuốc:");
        jLabel10.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel10.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel17.add(jLabel10);

        txtMaThuoc.setEditable(false);
        txtMaThuoc.setText("ASZX21Z1X");
        txtMaThuoc.setFocusable(false);
        txtMaThuoc.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel17.add(txtMaThuoc);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(340, 40));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel11.setText("Tên thuốc:");
        jLabel11.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel11.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel18.add(jLabel11);

        txtTenThuoc.setEditable(false);
        txtTenThuoc.setFocusable(false);
        txtTenThuoc.setPreferredSize(new java.awt.Dimension(350, 40));
        jPanel18.add(txtTenThuoc);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(215, 40));
        jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel12.setText("Thành phần:");
        jLabel12.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel12.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel19.add(jLabel12);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(350, 100));

        txtThanhPhan.setEditable(false);
        txtThanhPhan.setColumns(20);
        txtThanhPhan.setLineWrap(true);
        txtThanhPhan.setRows(5);
        txtThanhPhan.setFocusable(false);
        txtThanhPhan.setPreferredSize(new java.awt.Dimension(320, 40));
        jScrollPane3.setViewportView(txtThanhPhan);

        jPanel19.add(jScrollPane3);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setPreferredSize(new java.awt.Dimension(215, 40));
        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

        jLabel14.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel14.setText("Đơn giá:");
        jLabel14.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel14.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel21.add(jLabel14);

        txtDonGia.setEditable(false);
        txtDonGia.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14)); 
        txtDonGia.setText("123123");
        txtDonGia.setFocusable(false);
        txtDonGia.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel21.add(txtDonGia);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(612, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(205, Short.MAX_VALUE)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(66, Short.MAX_VALUE)))
        );

        jPanel16.add(jPanel24, java.awt.BorderLayout.CENTER);

        sanPhamPanel.add(jPanel16, java.awt.BorderLayout.CENTER);

        mainPanel.add(sanPhamPanel, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(230, 245, 245));
        jPanel4.setPreferredSize(new java.awt.Dimension(832, 400));
        jPanel4.setLayout(new java.awt.BorderLayout(0, 5));

        actionPanel.setBackground(new java.awt.Color(255, 255, 255));
        actionPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
        actionPanel.setPreferredSize(new java.awt.Dimension(605, 60));
        actionPanel.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 8));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        jPanel12.add(jPanel14);

        cboxSearch.setToolTipText("");
        cboxSearch.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel12.add(cboxSearch);

        txtSearch.setToolTipText("Tìm kiếm");
        txtSearch.setPreferredSize(new java.awt.Dimension(200, 40));
        txtSearch.setSelectionColor(new java.awt.Color(230, 245, 245));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel12.add(txtSearch);

        btnReload.setIcon(new FlatSVGIcon("./icon/reload.svg"));
        btnReload.setToolTipText("Làm mới");
        btnReload.setBorder(null);
        btnReload.setBorderPainted(false);
        btnReload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReload.setFocusPainted(false);
        btnReload.setFocusable(false);
        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReload.setPreferredSize(new java.awt.Dimension(40, 40));
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jPanel12.add(btnReload);

        actionPanel.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setPreferredSize(new java.awt.Dimension(260, 60));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 8));

        txtSoLuong.setFont(new java.awt.Font("Roboto", 0, 12)); 
        txtSoLuong.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel13.add(txtSoLuong);

        btnAddCart.setBackground(new java.awt.Color(0, 179, 246));
        btnAddCart.setFont(new java.awt.Font("Roboto Black", 0, 16)); 
        btnAddCart.setForeground(new java.awt.Color(255, 220, 0));
        btnAddCart.setIcon(new FlatSVGIcon("./icon/add-to-cart.svg"));
        btnAddCart.setText("THÊM");
        btnAddCart.setBorderPainted(false);
        btnAddCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddCart.setFocusPainted(false);
        btnAddCart.setFocusable(false);
        btnAddCart.setPreferredSize(new java.awt.Dimension(120, 40));
        btnAddCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCartActionPerformed(evt);
            }
        });
        jPanel13.add(btnAddCart);

        actionPanel.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel4.add(actionPanel, java.awt.BorderLayout.PAGE_START);

        tablePanel.setBackground(new java.awt.Color(255, 255, 255));
        tablePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
        tablePanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setFocusable(false);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.add(tablePanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

        add(mainPanel, java.awt.BorderLayout.CENTER);

        billPanel.setBackground(new java.awt.Color(230, 245, 245));
        billPanel.setPreferredSize(new java.awt.Dimension(460, 800));
        billPanel.setLayout(new java.awt.BorderLayout(0, 5));

        cardPanel.setBackground(new java.awt.Color(255, 255, 255));
        cardPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 238, 238), 2, true));
        cardPanel.setPreferredSize(new java.awt.Dimension(600, 500));
        cardPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 1, true));

        tableCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableCart.setFocusable(false);
        jScrollPane2.setViewportView(tableCart);

        cardPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 14)); 
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Giỏ hàng");
        jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);

        cardPanel.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setForeground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(456, 42));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 2));

        btnDeleteCartItem.setBackground(new java.awt.Color(255, 102, 102));
        btnDeleteCartItem.setFont(new java.awt.Font("Roboto Mono", 1, 14)); 
        btnDeleteCartItem.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteCartItem.setIcon(new FlatSVGIcon("./icon/trash-cart.svg"));
        btnDeleteCartItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteCartItem.setPreferredSize(new java.awt.Dimension(50, 38));
        btnDeleteCartItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCartItemActionPerformed(evt);
            }
        });
        jPanel20.add(btnDeleteCartItem);

        cardPanel.add(jPanel20, java.awt.BorderLayout.PAGE_END);

        billPanel.add(cardPanel, java.awt.BorderLayout.CENTER);

        billInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        billInfoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 238, 238), 2, true));
        billInfoPanel.setPreferredSize(new java.awt.Dimension(500, 400));
        billInfoPanel.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));
        jPanel5.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel5.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 14)); 
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Chi tiết");
        jPanel5.add(jLabel2, java.awt.BorderLayout.CENTER);

        billInfoPanel.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 8));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setPreferredSize(new java.awt.Dimension(440, 140));
        jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel4.setText("Mã đặt hàng: ");
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel7.add(jLabel4);

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(new java.awt.Font("Roboto Mono", 1, 14)); 
        txtMaHoaDon.setText("Z2NX8CN1A");
        txtMaHoaDon.setFocusable(false);
        txtMaHoaDon.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel7.add(txtMaHoaDon);

        jPanel23.add(jPanel7);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel8.setText("Số điện thoại:");
        jLabel8.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel25.add(jLabel8);

        txtSdtKH.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel25.add(txtSdtKH);

        btnSearchKH.setIcon(new FlatSVGIcon("./icon/search.svg"));
        btnSearchKH.setBorderPainted(false);
        btnSearchKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchKH.setFocusPainted(false);
        btnSearchKH.setFocusable(false);
        btnSearchKH.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSearchKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchKHActionPerformed(evt);
            }
        });
        jPanel25.add(btnSearchKH);

        btnAddCustomer.setIcon(new FlatSVGIcon("./icon/add-customer.svg"));
        btnAddCustomer.setBorderPainted(false);
        btnAddCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddCustomer.setFocusPainted(false);
        btnAddCustomer.setFocusable(false);
        btnAddCustomer.setPreferredSize(new java.awt.Dimension(40, 40));
        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });
        jPanel25.add(btnAddCustomer);

        jPanel23.add(jPanel25);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel3.setText("Tên khách hàng");
        jLabel3.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel2.add(jLabel3);

        txtHoTenKH.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel2.add(txtHoTenKH);

        jPanel23.add(jPanel2);

        cboxGioiTinhKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cboxGioiTinhKH.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel23.add(cboxGioiTinhKH);

        jPanel6.add(jPanel23);

        jSeparator1.setPreferredSize(new java.awt.Dimension(400, 3));
        jPanel6.add(jSeparator1);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setPreferredSize(new java.awt.Dimension(440, 150));
        jPanel26.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); 
        jLabel7.setForeground(new java.awt.Color(255, 51, 0));
        jLabel7.setText("Tổng đặt hàng:");
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel11.add(jLabel7);

        txtTong.setEditable(false);
        txtTong.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14)); 
        txtTong.setForeground(new java.awt.Color(255, 51, 0));
        txtTong.setText("1000000");
        txtTong.setFocusable(false);
        txtTong.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel11.add(txtTong);

        jPanel26.add(jPanel11);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        // Tạo JComboBox với các lựa chọn
        cboxThanhToan = new JComboBox<>(new String[] { "Chưa thanh toán", "Đã thanh toán" });
        cboxThanhToan.setPreferredSize(new java.awt.Dimension(150, 40));

        // Tạo một panel để chứa JComboBox
        JPanel paymentStatusPanel = new JPanel();
        paymentStatusPanel.setBackground(new java.awt.Color(255, 255, 255));
        paymentStatusPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        JLabel labelThanhToan = new JLabel("Trạng thái thanh toán:");
        labelThanhToan.setFont(new java.awt.Font("Roboto", 0, 14)); 
        labelThanhToan.setPreferredSize(new java.awt.Dimension(150, 40));
        paymentStatusPanel.add(labelThanhToan);

        paymentStatusPanel.add(cboxThanhToan);

        // Thêm paymentStatusPanel vào jPanel6 ngay sau jPanel26
        jPanel10.add(paymentStatusPanel);
        jPanel26.add(jPanel10);

        jPanel6.add(jPanel26);

        billInfoPanel.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnHuy.setBackground(new java.awt.Color(255, 102, 102));
        btnHuy.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); 
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("HỦY BỎ");
        btnHuy.setBorderPainted(false);
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuy.setFocusPainted(false);
        btnHuy.setFocusable(false);
        btnHuy.setPreferredSize(new java.awt.Dimension(200, 40));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel8.add(btnHuy);

        btnThanhToan.setBackground(new java.awt.Color(0, 204, 51));
        btnThanhToan.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); 
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("ĐẶT HÀNG");
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setFocusable(false);
        btnThanhToan.setPreferredSize(new java.awt.Dimension(200, 40));
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					btnDatHangActionPerformed(evt);
				} catch (WriterException e) {
					e.printStackTrace();
				}
            }
        });
        jPanel8.add(btnThanhToan);

        billInfoPanel.add(jPanel8, java.awt.BorderLayout.PAGE_END);

        billPanel.add(billInfoPanel, java.awt.BorderLayout.SOUTH);

        add(billPanel, java.awt.BorderLayout.EAST);
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        modal.setRowCount(0);

        String search = txtSearch.getText().toLowerCase().trim();
        String searchType = cboxSearch.getSelectedItem().toString();
        List<Thuoc> listsearch = THUOC_CON.getSearchTable(search, searchType);

        loadTable(listsearch);
    }

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
        txtSearch.setText("");
        cboxSearch.setSelectedIndex(0);
        loadTable(THUOC_CON.getAllList());
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
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

    private void btnAddCartActionPerformed(java.awt.event.ActionEvent evt) {
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

    private void btnDeleteCartItemActionPerformed(java.awt.event.ActionEvent evt) {
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

    private void btnSearchKHActionPerformed(java.awt.event.ActionEvent evt) {
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

    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {
        CreateKhachHangDialog dialog = new CreateKhachHangDialog(null, true, new KhachHangPage());
        dialog.setVisible(true);
    }

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {
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
    
    private void btnDatHangActionPerformed(java.awt.event.ActionEvent evt) throws WriterException { 
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
                }
                
                // Hiển thị thông báo thành công
                MessageDialog.info(this, "Đặt hàng thành công!");
                
                // Trở về trang hóa đơn
                main.setPanel(new DatHangPage(main));
            }
        }
    }


    private javax.swing.JPanel actionPanel;
    private javax.swing.JPanel billInfoPanel;
    private javax.swing.JPanel billPanel;
    private javax.swing.JButton btnAddCart;
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JButton btnDeleteCartItem;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSearchKH;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JComboBox<String> cboxGioiTinhKH;
    private javax.swing.JComboBox<String> cboxSearch;
    private javax.swing.JComboBox<String> cboxThanhToan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblThuoc;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel sanPhamPanel;
    private javax.swing.JTable table;
    private javax.swing.JTable tableCart;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JLabel txtHinhAnh;
    private javax.swing.JTextField txtHoTenKH;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaThuoc;
    private javax.swing.JTextField txtSdtKH;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenThuoc;
    private javax.swing.JTextArea txtThanhPhan;
    private javax.swing.JTextField txtTong;
}

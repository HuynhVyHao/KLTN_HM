package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.ChiTietPhieuNhapController;
import controller.NhanVienController;
import controller.PhieuNhapController;
import entity.ChiTietPhieuNhap;
import entity.NhanVien;
import entity.PhieuNhap;
import entity.TaiKhoan;
import gui.MainLayout;
import gui.dialog.DetailPhieuNhapDialog;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;
import utils.Validation;


public class PhieuNhapPage extends javax.swing.JPanel {

    private final PhieuNhapController PN_CON = new PhieuNhapController();
    private List<PhieuNhap> listHD = PN_CON.getAllList();

    private DefaultTableModel modal;
    private MainLayout main;

    public PhieuNhapPage() {
        initComponents();
        headerLayout();
        tableLayout();
        fillCombobox();
    }

    public PhieuNhapPage(MainLayout main) {
        this.main = main;
        initComponents();
        headerLayout();
        tableLayout();
        fillCombobox();
    }

    private void headerLayout() {
        List<JButton> listButton = new ArrayList<>();
        listButton.add(btnAdd);
        listButton.add(btnDelete);
        listButton.add(btnInfo);
        listButton.add(btnExport);
        listButton.add(btnReload);

        for (JButton item : listButton) {
            item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        }

    }

    private void tableLayout() {
        lblTable.setText("danh sách thông tin phiếu nhập".toUpperCase());
        String[] header = new String[]{"STT", "Mã phiếu nhập", "Thời gian", "Tên nhân viên", "Tên nhà cung cấp", "Tổng tiền"};
        modal = new DefaultTableModel();
        modal.setColumnIdentifiers(header);
        table.setModel(modal);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);

        loadTable(listHD);
        sortTable();
    }

    private void sortTable() {
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
    }

    public void loadTable(List<PhieuNhap> list) {
        modal.setRowCount(0);

        listHD = list;
        int stt = 1;

        for (PhieuNhap e : listHD) {
            modal.addRow(new Object[]{String.valueOf(stt), e.getId(), Formatter.FormatTime(e.getThoiGian()),
                e.getNhanVien().getHoTen(), e.getNcc().getTen(), Formatter.FormatVND(e.getTongTien())});
            stt++;
        }
    }

    private void fillCombobox() {
        List<NhanVien> listNV = new NhanVienController().getAllList();
        cboxNhanVien.addItem("Tất cả");
        for (NhanVien e : listNV) {
            cboxNhanVien.addItem(e.getHoTen());
        }
    }

    private boolean isValidFilterFields() {
        if (Validation.isEmpty(txtFromPrice.getText().trim())) {
            return false;
        } else {
            try {
                double fromPrice = Double.parseDouble(txtFromPrice.getText());
                if (fromPrice < 0) {
                    MessageDialog.warring(this, "Số tiền phải >= 0");
                    txtFromPrice.setText("");
                    txtFromPrice.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                MessageDialog.warring(this, "Số tiền phải là số!");
                txtFromPrice.setText("");
                txtFromPrice.requestFocus();
                return false;
            }
        }

        if (Validation.isEmpty(txtToPrice.getText().trim())) {
            return false;
        } else {
            try {
                double toPrice = Double.parseDouble(txtToPrice.getText());
                if (toPrice < 0) {
                    MessageDialog.warring(this, "Số tiền phải >= 0");
                    txtToPrice.setText("");
                    txtToPrice.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                MessageDialog.warring(this, "Số tiền phải là số!");
                txtToPrice.setText("");
                txtToPrice.requestFocus();
                return false;
            }
        }

        return true;
    }

    private List<PhieuNhap> getListFilter() {
        String tenNV = "";

        if (cboxNhanVien.getSelectedItem() != null) {
            tenNV = cboxNhanVien.getSelectedItem().toString();
        }

        double fromPrice = isValidFilterFields() ? Double.parseDouble(txtFromPrice.getText()) : 0;
        double toPrice = isValidFilterFields() ? Double.parseDouble(txtToPrice.getText()) : 0;
        
        return PN_CON.getFilterTable(tenNV, fromPrice, toPrice);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        cboxSearch = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnReload = new javax.swing.JButton();
        actionPanel = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnInfo = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        lblTable = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboxNhanVien = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtFromPrice = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtToPrice = new javax.swing.JTextField();

        setBackground(new java.awt.Color(227, 242, 223));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(227, 242, 223), 6, true));
        setMinimumSize(new java.awt.Dimension(1130, 800));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 10));

        headerPanel.setBackground(new java.awt.Color(255, 255, 255));
        headerPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(232, 232, 232), 2, true));
        headerPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(590, 100));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 16, 24));

        headerPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        actionPanel.setBackground(new java.awt.Color(255, 255, 255));
        actionPanel.setPreferredSize(new java.awt.Dimension(600, 100));
        actionPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 5));

        btnAdd.setFont(new java.awt.Font("Roboto", 1, 14)); 
        btnAdd.setIcon(new FlatSVGIcon("./icon/add.svg"));
        btnAdd.setText("THÊM");
        btnAdd.setBorder(null);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setPreferredSize(new java.awt.Dimension(90, 90));
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
       
        actionPanel.add(btnAdd);

        btnDelete.setFont(new java.awt.Font("Roboto", 1, 14)); 
        btnDelete.setIcon(new FlatSVGIcon("./icon/delete.svg"));
        btnDelete.setText("XÓA");
        btnDelete.setBorder(null);
        btnDelete.setBorderPainted(false);
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setFocusPainted(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setPreferredSize(new java.awt.Dimension(90, 90));
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        actionPanel.add(btnDelete);
      
        headerPanel.add(actionPanel, java.awt.BorderLayout.WEST);

        add(headerPanel, java.awt.BorderLayout.PAGE_START);

        tablePanel.setBackground(new java.awt.Color(243, 243, 243));
        tablePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 2, true));
        tablePanel.setLayout(new java.awt.BorderLayout(2, 0));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"123", "Anh Tuấn", "123123", null, null, null},
                {"13124", "czczxc", "zxc", null, null, null},
                {"14123", "zxczc", "zxc", null, null, null},
                {"124123", "zxczx", "zxc", null, null, null}
            },
            new String [] {
                "Mã", "Họ tên", "Số điện thoại", "Giới tính", "Năm sinh", "Ngày vào làm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));
        jPanel5.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel5.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel5.setLayout(new java.awt.BorderLayout());

        lblTable.setFont(new java.awt.Font("Roboto Medium", 0, 18)); 
        lblTable.setForeground(new java.awt.Color(255, 255, 255));
        lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTable.setText("THÔNG TIN NHÂN VIÊN");
        jPanel5.add(lblTable, java.awt.BorderLayout.CENTER);

        tablePanel.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 8));

     

        tablePanel.add(jPanel4, java.awt.BorderLayout.LINE_START);

        add(tablePanel, java.awt.BorderLayout.CENTER);
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        TaiKhoan tk = main.tk;
        CreatePhieuNhapPage page = new CreatePhieuNhapPage(main, tk);
        main.setPanel(page);
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int row = table.getSelectedRow();
            String id = table.getValueAt(row, 1).toString();

            if (MessageDialog.confirm(this, "Bạn có chắc chắn xóa dòng này?", "Xóa")) {
                new ChiTietPhieuNhapController().deleteById(id);
                PN_CON.deleteById(id);
                MessageDialog.info(this, "Xóa thành công!");
                modal.removeRow(row);
            }
        } catch (Exception e) {
            MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
        }
    }

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
        JTableExporter.exportJTableToExcel(table);
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        modal.setRowCount(0);

        String search = txtSearch.getText().toLowerCase().trim();
        String searchType = cboxSearch.getSelectedItem().toString();
        List<PhieuNhap> listsearch = PN_CON.getSearchTable(search, searchType);

        loadTable(listsearch);
    }

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
        txtSearch.setText("");
        txtFromPrice.setText("");
        txtToPrice.setText("");
        cboxSearch.setSelectedIndex(0);
        cboxNhanVien.setSelectedIndex(0);
        loadTable(listHD);
    }

    private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int row = table.getSelectedRow();
            PhieuNhap hoaDon = listHD.get(row);
            List<ChiTietPhieuNhap> listCTPN = new ChiTietPhieuNhapController().selectAllById(hoaDon.getId());

            DetailPhieuNhapDialog dialog = new DetailPhieuNhapDialog(null, true, listCTPN);
            dialog.setVisible(true);
        } catch (Exception e) {
            MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
        }
    }

    private void cboxNhanVienActionPerformed(java.awt.event.ActionEvent evt) {
        modal.setRowCount(0);

        List<PhieuNhap> listSearch = getListFilter();

        String tenDM = cboxNhanVien.getSelectedItem().toString();
        if (tenDM.equals("Tất cả")) {
            listSearch = PN_CON.getAllList();
        }

        loadTable(listSearch);
    }

    private void txtToPriceKeyReleased(java.awt.event.KeyEvent evt) {
        modal.setRowCount(0);
        List<PhieuNhap> listSearch = getListFilter();
        loadTable(listSearch);
    }

    private void txtFromPriceKeyReleased(java.awt.event.KeyEvent evt) {
        modal.setRowCount(0);
        List<PhieuNhap> listSearch = getListFilter();
        loadTable(listSearch);
    }


    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnReload;
    private javax.swing.JComboBox<String> cboxNhanVien;
    private javax.swing.JComboBox<String> cboxSearch;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTable;
    private javax.swing.JTable table;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTextField txtFromPrice;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtToPrice;
}

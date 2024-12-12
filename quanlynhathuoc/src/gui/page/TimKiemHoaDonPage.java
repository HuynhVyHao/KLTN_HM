package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.ChiTietHoaDonController;
import controller.HoaDonController;
import controller.NhanVienController;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.NhanVien;
import entity.TaiKhoan;
import gui.MainLayout;
import gui.dialog.DetailHoaDonDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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


public class TimKiemHoaDonPage extends JPanel {

    private final HoaDonController HD_CON = new HoaDonController();
    private List<HoaDon> listHD = HD_CON.getAllList();

    private DefaultTableModel modal;
    private MainLayout main;

    public TimKiemHoaDonPage() {
        initComponents();
        headerLayout();
        tableLayout();
        fillCombobox();
    }

    public TimKiemHoaDonPage(MainLayout main) {
        this.main = main;
        initComponents();
        headerLayout();
        tableLayout();
        fillCombobox();
    }

    private void headerLayout() {
        List<JButton> listButton = new ArrayList<>();
        listButton.add(btnReload);

        for (JButton item : listButton) {
            item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        }

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("./icon/search.svg"));

        String[] searchType = {"Tất cả", "Mã", "Tên khách hàng"};
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
        cboxSearch.setModel(model);
    }

    private void tableLayout() {
        lblTable.setText("danh sách thông tin hóa đơn".toUpperCase());
        String[] header = new String[]{"STT", "Mã hóa đơn", "Thời gian", "Tên nhân viên", "Tên khách hàng", "Tổng hóa đơn"};
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

    public void loadTable(List<HoaDon> list) {
        modal.setRowCount(0);

        listHD = list;
        int stt = 1;

        for (HoaDon e : listHD) {
            modal.addRow(new Object[]{String.valueOf(stt), e.getId(), Formatter.FormatTime(e.getThoiGian()),
                e.getNhanVien().getHoTen(), e.getKhachHang().getHoTen(), Formatter.FormatVND(e.getTongTien())});
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

    private List<HoaDon> getListFilter() {
        String tenNV = "";

        if (cboxNhanVien.getSelectedItem() != null) {
            tenNV = cboxNhanVien.getSelectedItem().toString();
        }

        double fromPrice = isValidFilterFields() ? Double.parseDouble(txtFromPrice.getText()) : 0;
        double toPrice = isValidFilterFields() ? Double.parseDouble(txtToPrice.getText()) : 0;
        
        return HD_CON.getFilterTable(tenNV, fromPrice, toPrice);
    }

    private void initComponents() {

        headerPanel = new JPanel();
        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        cboxSearch = new JComboBox<>();
        txtSearch = new JTextField();
        btnReload = new JButton();
        tablePanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        table = new JTable();
        jPanel5 = new JPanel();
        lblTable = new JLabel();
        jPanel4 = new JPanel();
        jPanel8 = new JPanel();
        jLabel3 = new JLabel();
        cboxNhanVien = new JComboBox<>();
        jSeparator1 = new JSeparator();
        jPanel9 = new JPanel();
        jLabel4 = new JLabel();
        txtFromPrice = new JTextField();
        jPanel10 = new JPanel();
        jLabel5 = new JLabel();
        txtToPrice = new JTextField();

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
        jLabel3.setText("Tên nhân viên");
        jLabel3.setPreferredSize(new Dimension(140, 20));
        jPanel8.add(jLabel3);

        cboxNhanVien.setToolTipText("");
        cboxNhanVien.setPreferredSize(new Dimension(170, 40));
        cboxNhanVien.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cboxNhanVienActionPerformed(evt);
            }
        });
        jPanel8.add(cboxNhanVien);

        jPanel4.add(jPanel8);

        jSeparator1.setPreferredSize(new Dimension(140, 3));
        jPanel4.add(jSeparator1);

        jPanel9.setBackground(new Color(255, 255, 255));
        jPanel9.setPreferredSize(new Dimension(200, 80));
        jPanel9.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));

        jLabel4.setFont(new Font("Roboto", 0, 14)); 
        jLabel4.setText("Từ số tiền");
        jLabel4.setPreferredSize(new Dimension(140, 20));
        jPanel9.add(jLabel4);

        txtFromPrice.setPreferredSize(new Dimension(170, 40));
        txtFromPrice.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                txtFromPriceKeyReleased(evt);
            }
        });
        jPanel9.add(txtFromPrice);

        jPanel4.add(jPanel9);

        jPanel10.setBackground(new Color(255, 255, 255));
        jPanel10.setPreferredSize(new Dimension(200, 80));
        jPanel10.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));

        jLabel5.setFont(new Font("Roboto", 0, 14)); 
        jLabel5.setText("Đến số tiền:");
        jLabel5.setPreferredSize(new Dimension(140, 20));
        jPanel10.add(jLabel5);

        txtToPrice.setPreferredSize(new Dimension(170, 40));
        txtToPrice.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                txtToPriceKeyReleased(evt);
            }
        });
        jPanel10.add(txtToPrice);

        jPanel4.add(jPanel10);

        tablePanel.add(jPanel4, BorderLayout.LINE_START);

        add(tablePanel, BorderLayout.CENTER);
    }

  

    private void txtSearchKeyReleased(KeyEvent evt) {
        modal.setRowCount(0);

        String search = txtSearch.getText().toLowerCase().trim();
        String searchType = cboxSearch.getSelectedItem().toString();
        List<HoaDon> listsearch = HD_CON.getSearchTable(search, searchType);

        loadTable(listsearch);
    }

    private void btnReloadActionPerformed(ActionEvent evt) {
        txtSearch.setText("");
        txtFromPrice.setText("");
        txtToPrice.setText("");
        cboxSearch.setSelectedIndex(0);
        cboxNhanVien.setSelectedIndex(0);
        loadTable(listHD);
    }

 

    private void cboxNhanVienActionPerformed(ActionEvent evt) {
        modal.setRowCount(0);

        List<HoaDon> listSearch = getListFilter();

        String tenDM = cboxNhanVien.getSelectedItem().toString();
        if (tenDM.equals("Tất cả")) {
            listSearch = HD_CON.getAllList();
        }

        loadTable(listSearch);
    }

    private void txtToPriceKeyReleased(KeyEvent evt) {
        modal.setRowCount(0);
        List<HoaDon> listSearch = getListFilter();
        loadTable(listSearch);
    }

    private void txtFromPriceKeyReleased(KeyEvent evt) {
        modal.setRowCount(0);
        List<HoaDon> listSearch = getListFilter();
        loadTable(listSearch);
    }

    private JButton btnReload;
    private JComboBox<String> cboxNhanVien;
    private JComboBox<String> cboxSearch;
    private JPanel headerPanel;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JLabel lblTable;
    private JTable table;
    private JPanel tablePanel;
    private JTextField txtFromPrice;
    private JTextField txtSearch;
    private JTextField txtToPrice;
}

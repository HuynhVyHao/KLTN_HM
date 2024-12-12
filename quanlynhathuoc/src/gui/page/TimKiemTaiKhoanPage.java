package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.TaiKhoanController;
import controller.VaiTroController;
import entity.TaiKhoan;
import entity.VaiTro;
import gui.dialog.CreateTaiKhoanDialog;
import gui.dialog.UpdateTaiKhoanDialog;

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
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;

public class TimKiemTaiKhoanPage extends JPanel {

    private final TaiKhoanController TK_CON = new TaiKhoanController(this);
    private List<TaiKhoan> listTK = TK_CON.getAllList();
    
    private final List<VaiTro> listVT = new VaiTroController().getAllList();

    DefaultTableModel modal;

    public TimKiemTaiKhoanPage() {
        initComponents();
        headerLayout();
        tableLayout();
        fillCombobox();
    }

    private void headerLayout() {
        btnReload.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("./icon/search.svg"));

        String[] searchType = {"Tất cả", "Username", "Tên nhân viên"};
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
        cboxSearch.setModel(model);
    }

    private void tableLayout() {
        lblTable.setText("danh sách thông tin tài khoản".toUpperCase());
        String[] header = new String[]{"STT", "Mã tài khoản", "Username", "Password", "Tên nhân viên", "Vai Trò"};

        modal = new DefaultTableModel();
        modal.setColumnIdentifiers(header);
        table.setModel(modal);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);

        loadTable(listTK);
        sortTable();
    }

    private void sortTable() {
        table.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
    }

    public void loadTable(List<TaiKhoan> list) {
        modal.setRowCount(0);

        listTK = list;
        int stt = 1;
        for (TaiKhoan e : listTK) {
            modal.addRow(new Object[]{String.valueOf(stt), e.getId(), e.getUsername(), e.getPassword(), e.getNhanVien().getHoTen(), e.getVaiTro().getTen()});
            stt++;
        }
    }
    
    
    private void fillCombobox() {
        cboxVaiTro.addItem("Tất cả");
        for (VaiTro e : listVT) {
            cboxVaiTro.addItem(e.getTen());
        }
    }

    private List<TaiKhoan> getListFilter() {
        String tenVT = "";

        if (cboxVaiTro.getSelectedItem() != null) {
            tenVT = cboxVaiTro.getSelectedItem().toString();
        }

        return TK_CON.getFilterTable(tenVT);
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
        cboxVaiTro = new JComboBox<>();

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
        jPanel3.setPreferredSize(new Dimension(584, 50));
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
        lblTable.setText("THÔNG TIN TÀI KHOẢN");
        jPanel5.add(lblTable, BorderLayout.CENTER);

        tablePanel.add(jPanel5, BorderLayout.NORTH);

        jPanel4.setBackground(new Color(255, 255, 255));
        jPanel4.setPreferredSize(new Dimension(200, 100));
        jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 16));

        jPanel8.setBackground(new Color(255, 255, 255));
        jPanel8.setPreferredSize(new Dimension(200, 80));
        jPanel8.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 8));

        jLabel3.setFont(new Font("Roboto", 0, 14)); 
        jLabel3.setText("Vai Trò");
        jLabel3.setPreferredSize(new Dimension(140, 20));
        jPanel8.add(jLabel3);

        cboxVaiTro.setToolTipText("");
        cboxVaiTro.setPreferredSize(new Dimension(170, 40));
        cboxVaiTro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cboxVaiTroActionPerformed(evt);
            }
        });
        jPanel8.add(cboxVaiTro);

        jPanel4.add(jPanel8);

        tablePanel.add(jPanel4, BorderLayout.LINE_START);

        add(tablePanel, BorderLayout.CENTER);
    }


    private void txtSearchKeyReleased(KeyEvent evt) {
        modal.setRowCount(0);

        String search = txtSearch.getText().toLowerCase().trim();
        String searchType = cboxSearch.getSelectedItem().toString();
        List<TaiKhoan> listSearch = TK_CON.getSearchTable(search, searchType);

        loadTable(listSearch);
    }

    private void btnReloadActionPerformed(ActionEvent evt) {
        txtSearch.setText("");
        cboxSearch.setSelectedIndex(0);
        loadTable(listTK);
    }

    private void cboxVaiTroActionPerformed(ActionEvent evt) {
        modal.setRowCount(0);

        List<TaiKhoan> listSearch = getListFilter();

        String tenVT = cboxVaiTro.getSelectedItem().toString();
        if (tenVT.equals("Tất cả")) {
            listSearch = TK_CON.getAllList();
        }

        loadTable(listSearch);
    }

    private JButton btnReload;
    private JComboBox<String> cboxSearch;
    private JComboBox<String> cboxVaiTro;
    private JPanel headerPanel;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel8;
    private JScrollPane jScrollPane1;
    private JLabel lblTable;
    private JTable table;
    private JPanel tablePanel;
    private JTextField txtSearch;
}

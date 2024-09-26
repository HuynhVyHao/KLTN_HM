package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.KhachHangController;
import entity.KhachHang;
import gui.dialog.CreateKhachHangDialog;
import gui.dialog.UpdateKhachHangDialog;
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

public class TimKiemKhachHangPage extends javax.swing.JPanel {
	  private KhachHangController KH_CON = new KhachHangController(this);

	    public TimKiemKhachHangPage() {
	        initComponents();
	        headerLayout();
	        tableLayout();
	    }

	    private void headerLayout() {
	        

	        // Border radius
	        btnReload.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

	        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
	        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("./icon/search.svg"));

	        String[] searchType = {"Tất cả", "Mã", "Tên", "Số điện thoại", "Năm sinh"};
	        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
	        cboxSearch.setModel(model);
	    }

	    private void tableLayout() {
	        lblTable.setText("danh sách thông tin khách hàng".toUpperCase());
	        String[] header = new String[]{"STT", "Mã khách hàng", "Họ tên", "Số điện thoại", "Giới tính", "Ngày tham gia"};
	        DefaultTableModel modal = new DefaultTableModel();
	        modal.setColumnIdentifiers(header);
	        table.setModel(modal);

	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        table.setDefaultRenderer(Object.class, centerRenderer);
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	        table.getColumnModel().getColumn(0).setPreferredWidth(30);
	        table.getColumnModel().getColumn(2).setPreferredWidth(200);

	        loadTable();
	        sortTable();
	    }

	    private void sortTable() {
	        table.setAutoCreateRowSorter(true);
	        TableSorter.configureTableColumnSorter(table, 0, TableSorter.STRING_COMPARATOR);
	    }

	    public void loadTable() {
	        DefaultTableModel modal = (DefaultTableModel) table.getModel();
	        modal.setRowCount(0);

	        List<KhachHang> list = KH_CON.getAllList();
	        int stt = 1;
	        for (KhachHang e : list) {
	            modal.addRow(new Object[]{String.valueOf(stt), e.getId(), e.getHoTen(), e.getSdt(), e.getGioiTinh(), Formatter.FormatDate(e.getNgayThamGia())});
	            stt++;
	        }
	    }

	    @SuppressWarnings("unchecked")
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	    private void initComponents() {

	        headerPanel = new javax.swing.JPanel();
	        jPanel1 = new javax.swing.JPanel();
	        jPanel3 = new javax.swing.JPanel();
	        cboxSearch = new javax.swing.JComboBox<>();
	        txtSearch = new javax.swing.JTextField();
	        btnReload = new javax.swing.JButton();
	        tablePanel = new javax.swing.JPanel();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        table = new javax.swing.JTable();
	        jPanel5 = new javax.swing.JPanel();
	        lblTable = new javax.swing.JLabel();

	        setBackground(new java.awt.Color(230, 245, 245));
	        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 245, 245), 6, true));
	        setMinimumSize(new java.awt.Dimension(1130, 800));
	        setPreferredSize(new java.awt.Dimension(1130, 800));
	        setLayout(new java.awt.BorderLayout(0, 10));

	        headerPanel.setBackground(new java.awt.Color(255, 255, 255));
	        headerPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(232, 232, 232), 2, true));
	        headerPanel.setLayout(new java.awt.BorderLayout());

	        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel1.setPreferredSize(new java.awt.Dimension(590, 100));
	        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 16, 24));

	        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel3.setPreferredSize(new java.awt.Dimension(584, 50));
	        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

	        cboxSearch.setToolTipText("");
	        cboxSearch.setPreferredSize(new java.awt.Dimension(100, 40));
	        jPanel3.add(cboxSearch);

	        txtSearch.setToolTipText("Tìm kiếm");
	        txtSearch.setPreferredSize(new java.awt.Dimension(200, 40));
	        txtSearch.setSelectionColor(new java.awt.Color(230, 245, 245));
	        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
	            public void keyReleased(java.awt.event.KeyEvent evt) {
	                txtSearchKeyReleased(evt);
	            }
	        });
	        jPanel3.add(txtSearch);

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
	        jPanel3.add(btnReload);

	        jPanel1.add(jPanel3);

	        headerPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

	        add(headerPanel, java.awt.BorderLayout.PAGE_START);

	        tablePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 2, true));
	        tablePanel.setLayout(new java.awt.BorderLayout());

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

	        lblTable.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
	        lblTable.setForeground(new java.awt.Color(255, 255, 255));
	        lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        lblTable.setText("THÔNG TIN NHÂN VIÊN");
	        jPanel5.add(lblTable, java.awt.BorderLayout.CENTER);

	        tablePanel.add(jPanel5, java.awt.BorderLayout.NORTH);

	        add(tablePanel, java.awt.BorderLayout.CENTER);
	    }// </editor-fold>//GEN-END:initComponents



	    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
	        DefaultTableModel modal = (DefaultTableModel) table.getModel();
	        modal.setRowCount(0);

	        String search = txtSearch.getText().toLowerCase().trim();
	        String searchType = cboxSearch.getSelectedItem().toString();
	        List<KhachHang> listsearch = KH_CON.getSearchTable(search, searchType);

	        int stt = 1;
	        for (KhachHang e : listsearch) {
	            modal.addRow(new Object[]{String.valueOf(stt), e.getId(), e.getHoTen(), e.getSdt(), e.getGioiTinh(), Formatter.FormatDate(e.getNgayThamGia())});
	            stt++;
	        }
	    }//GEN-LAST:event_txtSearchKeyReleased

	    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
	        txtSearch.setText("");
	        cboxSearch.setSelectedIndex(0);
	        loadTable();
	    }//GEN-LAST:event_btnReloadActionPerformed


	    // Variables declaration - do not modify//GEN-BEGIN:variables
	    private javax.swing.JPanel actionPanel;
	    private javax.swing.JButton btnAdd;
	    private javax.swing.JButton btnDelete;
	    private javax.swing.JButton btnExport;
	    private javax.swing.JButton btnImport;
	    private javax.swing.JButton btnInfo;
	    private javax.swing.JButton btnReload;
	    private javax.swing.JButton btnUpdate;
	    private javax.swing.JComboBox<String> cboxSearch;
	    private javax.swing.JPanel headerPanel;
	    private javax.swing.JPanel jPanel1;
	    private javax.swing.JPanel jPanel3;
	    private javax.swing.JPanel jPanel5;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JLabel lblTable;
	    private javax.swing.JTable table;
	    private javax.swing.JPanel tablePanel;
	    private javax.swing.JTextField txtSearch;
	    // End of variables declaration//GEN-END:variables
}

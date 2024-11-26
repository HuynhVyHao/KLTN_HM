package gui.page;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import controller.KhachHangController;
import entity.KhachHang;
import gui.dialog.CreateKhachHangDialog;
import gui.dialog.UpdateKhachHangDialog;
import utils.Formatter;
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;

public class BaoCaoKhachHangPage extends JPanel{
	 private KhachHangController KH_CON = new KhachHangController(this);

	    public BaoCaoKhachHangPage() {
	        initComponents();
	        headerLayout();
	        tableLayout();
	    }

	    private void headerLayout() {
	        List<JButton> listButton = new ArrayList<>();	     
	        listButton.add(btnImport);
	        listButton.add(btnExport);

	        for (JButton item : listButton) {
	            item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
	        }
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
	    private void initComponents() {

	        headerPanel = new javax.swing.JPanel();
	        actionPanel = new javax.swing.JPanel();
	        btnImport = new javax.swing.JButton();
	        btnExport = new javax.swing.JButton();
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

	        actionPanel.setBackground(new java.awt.Color(255, 255, 255));
	        actionPanel.setPreferredSize(new java.awt.Dimension(600, 100));
	        actionPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 5));

	   

	        btnImport.setFont(new java.awt.Font("Roboto", 1, 14)); 
	        btnImport.setIcon(new FlatSVGIcon("./icon/import.svg"));
	        btnImport.setText("IMPORT");
	        btnImport.setBorder(null);
	        btnImport.setBorderPainted(false);
	        btnImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnImport.setFocusPainted(false);
	        btnImport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	        btnImport.setPreferredSize(new java.awt.Dimension(90, 90));
	        btnImport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	        btnImport.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnImportActionPerformed(evt);
	            }
	        });
	        actionPanel.add(btnImport);

	        btnExport.setFont(new java.awt.Font("Roboto", 1, 14)); 
	        btnExport.setIcon(new FlatSVGIcon("./icon/export.svg"));
	        btnExport.setText("EXPORT");
	        btnExport.setBorder(null);
	        btnExport.setBorderPainted(false);
	        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnExport.setFocusPainted(false);
	        btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	        btnExport.setPreferredSize(new java.awt.Dimension(90, 90));
	        btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
	        btnExport.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnExportActionPerformed(evt);
	            }
	        });
	        actionPanel.add(btnExport);

	        headerPanel.add(actionPanel, java.awt.BorderLayout.WEST);

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

	        lblTable.setFont(new java.awt.Font("Roboto Medium", 0, 18)); 
	        lblTable.setForeground(new java.awt.Color(255, 255, 255));
	        lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        lblTable.setText("THÔNG TIN NHÂN VIÊN");
	        jPanel5.add(lblTable, java.awt.BorderLayout.CENTER);

	        tablePanel.add(jPanel5, java.awt.BorderLayout.NORTH);

	        add(tablePanel, java.awt.BorderLayout.CENTER);
	    }


	    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {
	        KH_CON.importExcel();
	    }

	    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
	        JTableExporter.exportJTableToExcel(table);
	    }

	    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
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
	    }

	    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
	        txtSearch.setText("");
	        cboxSearch.setSelectedIndex(0);
	        loadTable();
	    }

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
}

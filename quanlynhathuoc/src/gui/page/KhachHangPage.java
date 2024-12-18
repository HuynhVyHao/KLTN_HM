package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.KhachHangController;
import entity.KhachHang;
import gui.dialog.CreateKhachHangDialog;
import gui.dialog.UpdateKhachHangDialog;
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


public class KhachHangPage extends JPanel {

    private KhachHangController KH_CON = new KhachHangController(this);

    public KhachHangPage() {
        initComponents();
        headerLayout();
        tableLayout();
    }

    private void headerLayout() {
        List<JButton> listButton = new ArrayList<>();
        listButton.add(btnAdd);
        listButton.add(btnUpdate);
//        listButton.add(btnDelete);
        listButton.add(btnInfo);
//        listButton.add(btnImport);
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

    private void initComponents() {

        headerPanel = new JPanel();
        actionPanel = new JPanel();
        btnAdd = new JButton();
        btnUpdate = new JButton();
//        btnDelete = new JButton();
        btnInfo = new JButton();
//        btnImport = new JButton();
        btnExport = new JButton();
        tablePanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        table = new JTable();
        jPanel5 = new JPanel();
        lblTable = new JLabel();

        setBackground(new java.awt.Color(230, 245, 245));
        setBorder(new LineBorder(new java.awt.Color(230, 245, 245), 6, true));
        setMinimumSize(new java.awt.Dimension(1130, 800));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 10));

        headerPanel.setBackground(new java.awt.Color(255, 255, 255));
        headerPanel.setBorder(new LineBorder(new java.awt.Color(232, 232, 232), 2, true));
        headerPanel.setLayout(new java.awt.BorderLayout());


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
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setPreferredSize(new java.awt.Dimension(90, 90));
        btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        actionPanel.add(btnAdd);

        btnUpdate.setFont(new java.awt.Font("Roboto", 1, 14)); 
        btnUpdate.setIcon(new FlatSVGIcon("./icon/update.svg"));
        btnUpdate.setText("SỬA");
        btnUpdate.setBorder(null);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUpdate.setPreferredSize(new java.awt.Dimension(90, 90));
        btnUpdate.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        actionPanel.add(btnUpdate);

//        btnDelete.setFont(new java.awt.Font("Roboto", 1, 14)); 
//        btnDelete.setIcon(new FlatSVGIcon("./icon/delete.svg"));
//        btnDelete.setText("XÓA");
//        btnDelete.setBorder(null);
//        btnDelete.setBorderPainted(false);
//        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//        btnDelete.setFocusPainted(false);
//        btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
//        btnDelete.setPreferredSize(new java.awt.Dimension(90, 90));
//        btnDelete.setVerticalTextPosition(SwingConstants.BOTTOM);
//        btnDelete.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnDeleteActionPerformed(evt);
//            }
//        });
//        actionPanel.add(btnDelete);

//        btnInfo.setFont(new java.awt.Font("Roboto", 1, 14)); 
//        btnInfo.setIcon(new FlatSVGIcon("./icon/info.svg"));
//        btnInfo.setText("INFO");
//        btnInfo.setBorder(null);
//        btnInfo.setBorderPainted(false);
//        btnInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//        btnInfo.setFocusPainted(false);
//        btnInfo.setHorizontalTextPosition(SwingConstants.CENTER);
//        btnInfo.setPreferredSize(new java.awt.Dimension(90, 90));
//        btnInfo.setVerticalTextPosition(SwingConstants.BOTTOM);
//        actionPanel.add(btnInfo);

//        btnImport.setFont(new java.awt.Font("Roboto", 1, 14)); 
//        btnImport.setIcon(new FlatSVGIcon("./icon/import.svg"));
//        btnImport.setText("IMPORT");
//        btnImport.setBorder(null);
//        btnImport.setBorderPainted(false);
//        btnImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//        btnImport.setFocusPainted(false);
//        btnImport.setHorizontalTextPosition(SwingConstants.CENTER);
//        btnImport.setPreferredSize(new java.awt.Dimension(90, 90));
//        btnImport.setVerticalTextPosition(SwingConstants.BOTTOM);
//        btnImport.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnImportActionPerformed(evt);
//            }
//        });
//        actionPanel.add(btnImport);

        btnExport.setFont(new java.awt.Font("Roboto", 1, 14)); 
        btnExport.setIcon(new FlatSVGIcon("./icon/export.svg"));
        btnExport.setText("EXPORT");
        btnExport.setBorder(null);
        btnExport.setBorderPainted(false);
        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExport.setFocusPainted(false);
        btnExport.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExport.setPreferredSize(new java.awt.Dimension(90, 90));
        btnExport.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        actionPanel.add(btnExport);

        headerPanel.add(actionPanel, java.awt.BorderLayout.WEST);

        add(headerPanel, java.awt.BorderLayout.PAGE_START);

        tablePanel.setBorder(new LineBorder(new java.awt.Color(230, 230, 230), 2, true));
        tablePanel.setLayout(new java.awt.BorderLayout());

        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        lblTable.setHorizontalAlignment(SwingConstants.CENTER);
        lblTable.setText("THÔNG TIN NHÂN VIÊN");
        jPanel5.add(lblTable, java.awt.BorderLayout.CENTER);

        tablePanel.add(jPanel5, java.awt.BorderLayout.NORTH);

        add(tablePanel, java.awt.BorderLayout.CENTER);
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        CreateKhachHangDialog dialog = new CreateKhachHangDialog(null, true, this);
        dialog.setVisible(true);
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int row = table.getSelectedRow();
            String id = table.getValueAt(row, 1).toString();
            KhachHang nv = KH_CON.selectById(id);

            UpdateKhachHangDialog dialog = new UpdateKhachHangDialog(null, true, this, nv);
            dialog.setVisible(true);
        } catch (Exception e) {
            MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            DefaultTableModel modal = (DefaultTableModel) table.getModel();
            int row = table.getSelectedRow();
            String id = table.getValueAt(row, 1).toString();

            if (MessageDialog.confirm(this, "Bạn có chắc chắn xóa dòng này?", "Xóa")) {
                KH_CON.deleteById(id);
                modal.removeRow(row);
            }
        } catch (Exception e) {
            MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
        }
    }

//    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {
//        KH_CON.importExcel();
//    }

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
        JTableExporter.exportJTableToExcel(table);
    }



    private JPanel actionPanel;
    private JButton btnAdd;
//    private JButton btnDelete;
    private JButton btnInfo;
    private JButton btnUpdate;
    private JButton btnExport;
//    private JButton btnImport;
    private JPanel headerPanel;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JLabel lblTable;
    private JTable table;
    private JPanel tablePanel;
}

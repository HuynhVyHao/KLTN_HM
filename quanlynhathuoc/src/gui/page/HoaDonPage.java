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


public class HoaDonPage extends JPanel {

    private final HoaDonController HD_CON = new HoaDonController();
    private List<HoaDon> listHD = HD_CON.getAllList();

    private DefaultTableModel modal;
    private MainLayout main;

    public HoaDonPage() {
        initComponents();
        headerLayout();
        tableLayout();
    }

    public HoaDonPage(MainLayout main) {
        this.main = main;
        initComponents();
        headerLayout();
        tableLayout();
    }

    private void headerLayout() {
        List<JButton> listButton = new ArrayList<>();
        listButton.add(btnAdd);
        listButton.add(btnDelete);
        listButton.add(btnInfo);
//        listButton.add(btnImport);
        listButton.add(btnExport);

        for (JButton item : listButton) {
            item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        }
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

    private void initComponents() {

        headerPanel = new JPanel();
        actionPanel = new JPanel();
        btnAdd = new JButton();
        btnDelete = new JButton();
        btnInfo = new JButton();
//        btnImport = new JButton();
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
        actionPanel.setPreferredSize(new Dimension(600, 100));
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

        btnInfo.setFont(new Font("Roboto", 1, 14)); 
        btnInfo.setIcon(new FlatSVGIcon("./icon/info.svg"));
        btnInfo.setText("INFO");
        btnInfo.setBorder(null);
        btnInfo.setBorderPainted(false);
        btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnInfo.setFocusPainted(false);
        btnInfo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnInfo.setPreferredSize(new Dimension(90, 90));
        btnInfo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnInfoActionPerformed(evt);
            }
        });
        actionPanel.add(btnInfo);

//        btnImport.setFont(new Font("Roboto", 1, 14)); 
//        btnImport.setIcon(new FlatSVGIcon("./icon/import.svg"));
//        btnImport.setText("IMPORT");
//        btnImport.setBorder(null);
//        btnImport.setBorderPainted(false);
//        btnImport.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnImport.setFocusPainted(false);
//        btnImport.setHorizontalTextPosition(SwingConstants.CENTER);
//        btnImport.setPreferredSize(new Dimension(90, 90));
//        btnImport.setVerticalTextPosition(SwingConstants.BOTTOM);
//        btnImport.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                btnImportActionPerformed(evt);
//            }
//        });
//        actionPanel.add(btnImport);

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
        jPanel5.add(lblTable, BorderLayout.CENTER);

        tablePanel.add(jPanel5, BorderLayout.NORTH);

       

        add(tablePanel, BorderLayout.CENTER);
    }

    private void btnAddActionPerformed(ActionEvent evt) {
        TaiKhoan tk = main.tk;
        CreateHoaDonPage page = new CreateHoaDonPage(main, tk);
        main.setPanel(page);
    }

    private void btnDeleteActionPerformed(ActionEvent evt) {
        try {
            int row = table.getSelectedRow();
            String id = table.getValueAt(row, 1).toString();

            if (MessageDialog.confirm(this, "Bạn có chắc chắn xóa dòng này?", "Xóa")) {
                new ChiTietHoaDonController().deleteById(id);
                HD_CON.deleteById(id);
                MessageDialog.info(this, "Xóa thành công!");
                modal.removeRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
        }
    }

    private void btnExportActionPerformed(ActionEvent evt) {
        JTableExporter.exportJTableToExcel(table);
    }
    
//    private void btnImportActionPerformed(ActionEvent evt) {
//        
//    }



    private void btnInfoActionPerformed(ActionEvent evt) {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                throw new Exception("No row selected");
            }

            HoaDon hoaDon = listHD.get(row);
            List<ChiTietHoaDon> listCTHD = new ChiTietHoaDonController().selectAllById(hoaDon.getId());

            DetailHoaDonDialog dialog = new DetailHoaDonDialog(null, true, listCTHD);
            dialog.setVisible(true);
        } catch (Exception e) {
            MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
        }
    }



    private JPanel actionPanel;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnExport;
//    private JButton btnImport;
    private JButton btnInfo;
    private JPanel headerPanel;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JLabel lblTable;
    private JTable table;
    private JPanel tablePanel;
}

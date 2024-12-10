package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.NhaCungCapController;
import entity.NhaCungCap;
import gui.dialog.CreateNhaCungCapDialog;
import gui.dialog.UpdateNhaCungCapDialog;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.JTableExporter;
import utils.MessageDialog;
import utils.TableSorter;


public class NhaCungCapPage extends javax.swing.JPanel {

    private NhaCungCapController NCC_CON = new NhaCungCapController(this);

    public NhaCungCapPage() {
        initComponents();
        headerLayout();
        tableLayout();
    }

    private void headerLayout() {List<JButton> listButton = new ArrayList<>();
        listButton.add(btnAdd);
        listButton.add(btnUpdate);
        listButton.add(btnDelete);
        listButton.add(btnInfo);
        listButton.add(btnImport);
		listButton.add(btnExport);

        for (JButton item : listButton) {
            item.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        }
    }

    private void tableLayout() {
        lblTable.setText("danh sách thông tin nhà cung cấp".toUpperCase());
        String[] header = new String[]{"STT", "Mã nhà cung cấp", "Tên nhà cung cấp", "Số điện thoại", "Địa chỉ","Cung Cấp"};
        DefaultTableModel modal = new DefaultTableModel();
        modal.setColumnIdentifiers(header);
        table.setModel(modal);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        // Đặt chiều rộng cho cột "Địa chỉ"
        table.getColumnModel().getColumn(4).setPreferredWidth(300);

        // Tạo renderer cho việc xuống dòng trong cột "Địa chỉ"
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value != null) {
                    setText("<html>" + value.toString().replaceAll("\n", "<br/>") + "</html>");
                }
                super.setValue(value);
            }
        };
        table.getColumnModel().getColumn(4).setCellRenderer(renderer);

        // Cho phép tự động điều chỉnh chiều cao của các dòng
        table.setRowHeight(50);


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

        List<NhaCungCap> list = NCC_CON.getAllList();
        int stt = 1;
        for (NhaCungCap e : list) {
            modal.addRow(new Object[]{String.valueOf(stt), e.getId(), e.getTen(), e.getSdt(), e.getDiaChi(),e.getDanhMuc().getTen()});
            stt++;
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        actionPanel = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnInfo = new javax.swing.JButton();
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
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUpdate.setPreferredSize(new java.awt.Dimension(90, 90));
        btnUpdate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        actionPanel.add(btnUpdate);

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

        btnInfo.setFont(new java.awt.Font("Roboto", 1, 14)); 
        btnInfo.setIcon(new FlatSVGIcon("./icon/info.svg"));
        btnInfo.setText("INFO");
        btnInfo.setBorder(null);
        btnInfo.setBorderPainted(false);
        btnInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInfo.setFocusPainted(false);
        btnInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInfo.setPreferredSize(new java.awt.Dimension(90, 90));
        btnInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        actionPanel.add(btnInfo);

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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        CreateNhaCungCapDialog dialog = new CreateNhaCungCapDialog(null, true, this);
        dialog.setVisible(true);
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int row = table.getSelectedRow();
            String id = table.getValueAt(row, 1).toString();
            NhaCungCap e = NCC_CON.selectById(id);

            UpdateNhaCungCapDialog dialog = new UpdateNhaCungCapDialog(null, true, this, e);
            dialog.setVisible(true);
        } catch (Exception e) {
            MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            DefaultTableModel modal = (DefaultTableModel) table.getModel();
            int row = table.getSelectedRow();

            // Kiểm tra xem có dòng nào được chọn không
            if (row == -1) {
                MessageDialog.error(this, "Vui lòng chọn dòng cần thực hiện!");
                return;
            }

            String id = table.getValueAt(row, 1).toString();

            // Xác nhận xóa
            if (MessageDialog.confirm(this, "Bạn có chắc chắn xóa dòng này?", "Xóa")) {
                try {
                    // Gọi phương thức xóa dữ liệu từ DAO
                    NCC_CON.deleteById(id);  // Gọi phương thức xóa trong DAO

                    // Nếu xóa thành công, cập nhật bảng
                    modal.removeRow(row);
                } catch (Exception e) {
                    // Kiểm tra lỗi ràng buộc khóa ngoại
                    if (e.getMessage().contains("REFERENCE constraint")) {
                        MessageDialog.error(this, "Không thể xóa vì có dữ liệu liên quan trong các bảng khác.");
                    } else {
                        // Các lỗi khác (nếu có)
                        MessageDialog.error(this, "Lỗi xảy ra: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            // Các lỗi khác ngoài SQLException
            MessageDialog.error(this, "Lỗi xảy ra: " + e.getMessage());
        }
    }
    
	private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {
		NCC_CON.importExcel();
	}

	private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {
		JTableExporter.exportJTableToExcel(table);
	}


    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnExport;
	private javax.swing.JButton btnImport;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTable;
    private javax.swing.JTable table;
    private javax.swing.JPanel tablePanel;
}

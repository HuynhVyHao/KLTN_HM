package gui.page;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.NhaCungCapController;
import entity.NhaCungCap;
import gui.dialog.CreateNhaCungCapDialog;
import gui.dialog.UpdateNhaCungCapDialog;

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


public class NhaCungCapPage extends JPanel {

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

    private void initComponents() {

        headerPanel = new JPanel();
        actionPanel = new JPanel();
        btnAdd = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();
        btnImport = new JButton();
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

        btnUpdate.setFont(new Font("Roboto", 1, 14)); 
        btnUpdate.setIcon(new FlatSVGIcon("./icon/update.svg"));
        btnUpdate.setText("SỬA");
        btnUpdate.setBorder(null);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUpdate.setPreferredSize(new Dimension(90, 90));
        btnUpdate.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        actionPanel.add(btnUpdate);

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

        btnImport.setFont(new Font("Roboto", 1, 14));
		btnImport.setIcon(new FlatSVGIcon("./icon/import.svg"));
		btnImport.setText("IMPORT");
		btnImport.setBorder(null);
		btnImport.setBorderPainted(false);
		btnImport.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnImport.setFocusPainted(false);
		btnImport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImport.setPreferredSize(new Dimension(90, 90));
		btnImport.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnImportActionPerformed(evt);
			}
		});
		actionPanel.add(btnImport);

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

        tablePanel.setBorder(new LineBorder(new Color(230, 230, 230), 2, true));
        tablePanel.setLayout(new BorderLayout());

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
        CreateNhaCungCapDialog dialog = new CreateNhaCungCapDialog(null, true, this);
        dialog.setVisible(true);
    }

    private void btnUpdateActionPerformed(ActionEvent evt) {
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

    private void btnDeleteActionPerformed(ActionEvent evt) {
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
    
	private void btnImportActionPerformed(ActionEvent evt) {
		NCC_CON.importExcel();
	}

	private void btnExportActionPerformed(ActionEvent evt) {
		JTableExporter.exportJTableToExcel(table);
	}


    private JPanel actionPanel;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnExport;
	private JButton btnImport;
    private JPanel headerPanel;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JLabel lblTable;
    private JTable table;
    private JPanel tablePanel;
}

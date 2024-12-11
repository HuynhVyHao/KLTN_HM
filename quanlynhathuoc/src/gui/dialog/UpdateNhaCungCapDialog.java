package gui.dialog;

import java.util.List;

import controller.DanhMucController;
import controller.NhaCungCapController;
import entity.DanhMuc;
import entity.NhaCungCap;
import gui.page.NhaCungCapPage;
import utils.MessageDialog;
import utils.RandomGenerator;
import utils.Validation;


public class UpdateNhaCungCapDialog extends javax.swing.JDialog {

    private NhaCungCapController NCC_CON = new NhaCungCapController();
    private NhaCungCapPage NCC_GUI;
    private NhaCungCap ncc;
    private final List<DanhMuc> list = new DanhMucController().getAllList();

    public UpdateNhaCungCapDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadDanhMuc();
    }

    public UpdateNhaCungCapDialog(java.awt.Frame parent, boolean modal, NhaCungCapPage NCC_GUI, NhaCungCap ncc) {
        super(parent, modal);
        initComponents();
        this.NCC_GUI = NCC_GUI;
        this.ncc = ncc;
        loadDanhMuc(); // Gọi loadDanhMuc() trước
        fillInput();   // Sau đó mới điền dữ liệu vào các trường
    }
    private void loadDanhMuc() {
        try {
            cboDanhMuc.removeAllItems();  // Clear any previous items
            for (DanhMuc vt : list) {
                cboDanhMuc.addItem(vt.getTen());  // Add each danh mục name to the combobox
            }
        } catch (Exception e) {
            MessageDialog.error(this, "Lỗi nạp danh mục: " + e.getMessage());
        }
    }

    private void fillInput() {
        if (ncc != null) { 
            txtTen.setText(ncc.getTen());
            txtSdt.setText(ncc.getSdt());
            txtDiaChi.setText(ncc.getDiaChi());

            if (cboDanhMuc.getItemCount() > 0) { // Chỉ thực hiện nếu combobox đã có dữ liệu
                for (int i = 0; i < cboDanhMuc.getItemCount(); i++) {
                    if (cboDanhMuc.getItemAt(i).toString().equals(ncc.getDanhMuc().getTen())) {
                        cboDanhMuc.setSelectedIndex(i);
                        break;
                    }
                }
            } else {
                System.out.println("Combobox chưa được nạp danh sách danh mục.");
            }
        } else {
            System.out.println("Không thể điền dữ liệu, nhà cung cấp (ncc) là null.");
        }
    }



    private boolean isValidateFields() {
        if (txtTen.getText().trim().equals("")) {
            MessageDialog.warring(this, "Tên Nhà cung cấp không được rỗng!");
            txtTen.requestFocus();
            return false;
        }

        if (txtSdt.getText().trim().equals("") || !Validation.isNumber(txtSdt.getText()) || txtSdt.getText().length() != 10) {
            MessageDialog.warring(this, "Số điện thoại không được rỗng và có 10 ký tự sô!");
            txtSdt.requestFocus();
            return false;
        }

        if (txtDiaChi.getText().trim().equals("")) {
            MessageDialog.warring(this, "Địa chỉ Nhà cung cấp không được rỗng!");
            txtDiaChi.requestFocus();
            return false;
        }

        return true;
    }

    private NhaCungCap getInputFields() {
        String id = ncc.getId();
        String ten = txtTen.getText().trim();
        String sdt = txtSdt.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String idDM = list.get(cboDanhMuc.getSelectedIndex()).getId();  // Get the selected danh mục ID
        DanhMuc danhMuc = new DanhMucController().selectById(idDM);  // Get the danh mục object

        return new NhaCungCap(id, ten, sdt, diaChi, danhMuc);
    }

    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lblHoTen = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jPanelDanhMuc = new javax.swing.JPanel();
        jLabelDanhMuc = new javax.swing.JLabel();
        cboDanhMuc = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 600));

        jPanel15.setBackground(new java.awt.Color(0, 153, 153));
        jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel15.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 18)); 
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CẬP NHẬT NHÀ CUNG CẤP");
        jPanel15.add(jLabel8, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 16));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        lblHoTen.setFont(new java.awt.Font("Roboto", 0, 14)); 
        lblHoTen.setText("Tên nhà cung cấp");
        lblHoTen.setMaximumSize(new java.awt.Dimension(44, 40));
        lblHoTen.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel18.add(lblHoTen);

        txtTen.setFont(new java.awt.Font("Roboto", 0, 14)); 
        txtTen.setToolTipText("");
        txtTen.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel18.add(txtTen);

        jPanel1.add(jPanel18);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel12.setText("Số điện thoại");
        jLabel12.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel12.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel19.add(jLabel12);

        txtSdt.setFont(new java.awt.Font("Roboto", 0, 14)); 
        txtSdt.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel19.add(txtSdt);

        jPanel1.add(jPanel19);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 14)); 
        jLabel13.setText("Địa chỉ");
        jLabel13.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabel13.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel20.add(jLabel13);

        txtDiaChi.setFont(new java.awt.Font("Roboto", 0, 14)); 
        txtDiaChi.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel20.add(txtDiaChi);

        jPanel1.add(jPanel20);
        
        // Trong initComponents
        jPanelDanhMuc.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDanhMuc.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanelDanhMuc.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));

        jLabelDanhMuc.setFont(new java.awt.Font("Roboto", 0, 14));
        jLabelDanhMuc.setText("Danh mục");
        jLabelDanhMuc.setMaximumSize(new java.awt.Dimension(44, 40));
        jLabelDanhMuc.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanelDanhMuc.add(jLabelDanhMuc);

//        cboDanhMuc.setFont(new java.awt.Font("Roboto", 0, 14));
        cboDanhMuc.setPreferredSize(new java.awt.Dimension(300, 40));
        jPanelDanhMuc.add(cboDanhMuc);

        jPanel1.add(jPanelDanhMuc);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 5));

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

        btnUpdate.setBackground(new java.awt.Color(0, 204, 102));
        btnUpdate.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); 
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("CẬP NHẬT");
        btnUpdate.setBorderPainted(false);
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setFocusable(false);
        btnUpdate.setPreferredSize(new java.awt.Dimension(200, 40));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel8.add(btnUpdate);

        getContentPane().add(jPanel8, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        if (isValidateFields()) {
            NhaCungCap e = getInputFields();
            NCC_CON.update(e);
            NCC_GUI.loadTable();
            this.dispose();
        }
    }

    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JPanel jPanelDanhMuc;
    private javax.swing.JLabel jLabelDanhMuc;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTen;
}

package gui.dialog;

import controller.KhachHangController;
import entity.KhachHang;
import gui.page.KhachHangPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import utils.MessageDialog;
import utils.RandomGenerator;
import utils.Validation;


public class CreateKhachHangDialog extends JDialog {

    private KhachHangController KH_CON = new KhachHangController();
    private KhachHangPage KH_GUI;

    public CreateKhachHangDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public CreateKhachHangDialog(Frame parent, boolean modal, KhachHangPage NV_GUI) {
        super(parent, modal);
        initComponents();
        this.KH_GUI = NV_GUI;
        fillInput();
    }

    private void fillInput() {
        txtNgayThamGia.setDate(new Date());
    }

    private boolean isValidateFields() {
        if (txtHoTen.getText().trim().equals("")) {
            MessageDialog.warring(this, "Họ tên không được rỗng!");
            txtHoTen.requestFocus();
            return false;
        }
        // Kiểm tra Số điện thoại
        String phoneNumber = txtSdt.getText().trim();
        if (phoneNumber.equals("") || !Validation.isNumber(phoneNumber) || phoneNumber.length() != 10) {
            MessageDialog.warring(this, "Số điện thoại không được rỗng và có 10 ký tự số!");
            txtSdt.requestFocus();
            return false;
        }

        // Kiểm tra nếu số điện thoại đã tồn tại
        if (KH_CON.isPhoneNumberExists(phoneNumber)) {
            MessageDialog.warring(this, "Số điện thoại đã tồn tại!");
            txtSdt.requestFocus();
            return false;
        }

        if (txtNgayThamGia.getDate() == null || !txtNgayThamGia.getDateFormatString().equals("dd/MM/yyyy")) {
            MessageDialog.warring(this, "Ngày tham gia không được rỗng và có kiểu dd/MM/yyyy");
            return false;
        } else if (txtNgayThamGia.getDate().after(new Date())) {
            MessageDialog.warring(this, "Ngày tham gia phải trước ngày hiện tại");
            return false;
        }

        return true;
    }

    private KhachHang getInputFields() {
        String id = RandomGenerator.getRandomId();
        String hoTen = txtHoTen.getText().trim();
        String sdt = txtSdt.getText().trim();
        String gioiTinh = cboxGioiTinh.getSelectedItem().toString();
        Date ngayThamGia = txtNgayThamGia.getDate();

        return new KhachHang(id, hoTen, sdt, gioiTinh, ngayThamGia);
    }

    private void initComponents() {

        jPanel15 = new JPanel();
        lblDialog = new JLabel();
        jPanel1 = new JPanel();
        jPanel18 = new JPanel();
        lblHoTen = new JLabel();
        txtHoTen = new JTextField();
        jPanel19 = new JPanel();
        jLabel12 = new JLabel();
        txtSdt = new JTextField();
        jPanel21 = new JPanel();
        jLabel14 = new JLabel();
        cboxGioiTinh = new JComboBox<>();
        jPanel22 = new JPanel();
        jLabel15 = new JLabel();
        txtNgayThamGia = new JDateChooser();
        jPanel8 = new JPanel();
        btnHuy = new JButton();
        btnAdd = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));

        jPanel15.setBackground(new Color(0, 153, 153));
        jPanel15.setMinimumSize(new Dimension(100, 60));
        jPanel15.setPreferredSize(new Dimension(500, 50));
        jPanel15.setLayout(new BorderLayout());

        lblDialog.setFont(new java.awt.Font("Roboto Medium", 0, 18)); 
        lblDialog.setForeground(new Color(255, 255, 255));
        lblDialog.setHorizontalAlignment(SwingConstants.CENTER);
        lblDialog.setText("THÊM KHÁCH HÀNG");
        jPanel15.add(lblDialog, BorderLayout.CENTER);

        getContentPane().add(jPanel15, BorderLayout.NORTH);

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));

        jPanel18.setBackground(new Color(255, 255, 255));
        jPanel18.setPreferredSize(new Dimension(500, 40));
        jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        lblHoTen.setFont(new Font("Roboto", 0, 14)); 
        lblHoTen.setText("Họ tên");
        lblHoTen.setMaximumSize(new Dimension(44, 40));
        lblHoTen.setPreferredSize(new Dimension(150, 40));
        jPanel18.add(lblHoTen);

        txtHoTen.setFont(new Font("Roboto", 0, 14));
        txtHoTen.setToolTipText("");
        txtHoTen.setPreferredSize(new Dimension(330, 40));
        jPanel18.add(txtHoTen);

        jPanel1.add(jPanel18);

        jPanel19.setBackground(new Color(255, 255, 255));
        jPanel19.setPreferredSize(new Dimension(500, 40));
        jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel12.setFont(new Font("Roboto", 0, 14)); 
        jLabel12.setText("Số điện thoại");
        jLabel12.setMaximumSize(new Dimension(44, 40));
        jLabel12.setPreferredSize(new Dimension(150, 40));
        jPanel19.add(jLabel12);

        txtSdt.setFont(new Font("Roboto", 0, 14)); 
        txtSdt.setPreferredSize(new Dimension(330, 40));
        jPanel19.add(txtSdt);

        jPanel1.add(jPanel19);

        jPanel21.setBackground(new Color(255, 255, 255));
        jPanel21.setPreferredSize(new Dimension(500, 40));
        jPanel21.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel14.setFont(new Font("Roboto", 0, 14)); 
        jLabel14.setText("Giới tính");
        jLabel14.setMaximumSize(new Dimension(44, 40));
        jLabel14.setPreferredSize(new Dimension(150, 40));
        jPanel21.add(jLabel14);

        cboxGioiTinh.setModel(new DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cboxGioiTinh.setPreferredSize(new Dimension(200, 40));
        jPanel21.add(cboxGioiTinh);

        jPanel1.add(jPanel21);

        jPanel22.setBackground(new Color(255, 255, 255));
        jPanel22.setPreferredSize(new Dimension(500, 40));
        jPanel22.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel15.setFont(new Font("Roboto", 0, 14)); 
        jLabel15.setText("Ngày tham gia");
        jLabel15.setMaximumSize(new Dimension(44, 40));
        jLabel15.setPreferredSize(new Dimension(150, 40));
        jPanel22.add(jLabel15);

        txtNgayThamGia.setBackground(new Color(255, 255, 255));
        txtNgayThamGia.setDateFormatString("dd/MM/yyyy");
        txtNgayThamGia.setPreferredSize(new Dimension(200, 40));
        jPanel22.add(txtNgayThamGia);

        jPanel1.add(jPanel22);

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        jPanel8.setBackground(new Color(255, 255, 255));
        jPanel8.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

        btnHuy.setBackground(new Color(255, 102, 102));
        btnHuy.setFont(new Font("Roboto Mono Medium", 0, 16)); 
        btnHuy.setForeground(new Color(255, 255, 255));
        btnHuy.setText("HỦY BỎ");
        btnHuy.setBorderPainted(false);
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHuy.setFocusPainted(false);
        btnHuy.setFocusable(false);
        btnHuy.setPreferredSize(new Dimension(200, 40));
        btnHuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel8.add(btnHuy);

        btnAdd.setBackground(new Color(0, 204, 102));
        btnAdd.setFont(new Font("Roboto Mono Medium", 0, 16)); 
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setText("THÊM");
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setFocusPainted(false);
        btnAdd.setFocusable(false);
        btnAdd.setPreferredSize(new Dimension(200, 40));
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel8.add(btnAdd);

        getContentPane().add(jPanel8, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }

    private void btnHuyActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void btnAddActionPerformed(ActionEvent evt) {
        if (isValidateFields()) {
            KhachHang nv = getInputFields();
            KH_CON.create(nv);
            MessageDialog.info(null, "Thêm thành công!");
            KH_GUI.loadTable();
            this.dispose();
        }
    }

    private JButton btnAdd;
    private JButton btnHuy;
    private JComboBox<String> cboxGioiTinh;
    private JLabel jLabel12;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JPanel jPanel1;
    private JPanel jPanel15;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel21;
    private JPanel jPanel22;
    private JPanel jPanel8;
    private JLabel lblDialog;
    private JLabel lblHoTen;
    private JTextField txtHoTen;
    private JDateChooser txtNgayThamGia;
    private JTextField txtSdt;
}

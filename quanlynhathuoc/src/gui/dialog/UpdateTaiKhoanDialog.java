package gui.dialog;

import controller.NhanVienController;
import controller.TaiKhoanController;
import controller.VaiTroController;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.VaiTro;
import gui.page.TaiKhoanPage;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import utils.MessageDialog;

public class UpdateTaiKhoanDialog extends JDialog {

    private TaiKhoanController TK_CON = new TaiKhoanController();
    private TaiKhoanPage TK_GUI;
    private TaiKhoan tk;

    private final List<NhanVien> listNV = new NhanVienController().getAllList();
    private final List<VaiTro> listVT = new VaiTroController().getAllList();

    public UpdateTaiKhoanDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public UpdateTaiKhoanDialog(Frame parent, boolean modal, TaiKhoanPage TK_GUI, TaiKhoan tk) {
        super(parent, modal);
        initComponents();
        this.TK_GUI = TK_GUI;
        this.tk = tk;
        fillCombobox();
        fillInput();
    }

    private void fillCombobox() {
        for (NhanVien nv : listNV) {
            cboxNhanVien.addItem(nv.getHoTen());
        }

        for (VaiTro vt : listVT) {
            if (!vt.getId().equals("admin")) {
                cboxVaiTro.addItem(vt.getTen());
            }
        }
    }

    private void fillInput() {
        txtUsername.setText(tk.getUsername());
        cboxNhanVien.setSelectedItem(tk.getNhanVien().getHoTen());
        cboxVaiTro.setSelectedItem(tk.getVaiTro().getTen());
    }

    private boolean isValidateFields() {
        if (txtUsername.getText().trim().equals("") || txtUsername.getText().length() < 3) {
            MessageDialog.warring(this, "Username không được để trống và có ít nhất 3 ký tự!");
            txtUsername.requestFocus();
            return false;
        }

        return true;
    }

    private TaiKhoan getInputFields() {
        String id = tk.getId();
        String username = tk.getUsername();
        String password = tk.getPassword();
        String idNV = tk.getNhanVien().getId();
        NhanVien nhanVien = new NhanVienController().selectById(idNV);
        String idVT = listVT.get(cboxVaiTro.getSelectedIndex() + 1).getId();
        VaiTro vaiTro = new VaiTroController().selectById(idVT);

        return new TaiKhoan(id, username, password, nhanVien, vaiTro);
    }

    private void initComponents() {

        jPanel15 = new JPanel();
        jLabel8 = new JLabel();
        jPanel1 = new JPanel();
        jPanel18 = new JPanel();
        lblHoTen = new JLabel();
        txtUsername = new JTextField();
        jPanel21 = new JPanel();
        jLabel14 = new JLabel();
        cboxNhanVien = new JComboBox<>();
        jPanel22 = new JPanel();
        jLabel15 = new JLabel();
        cboxVaiTro = new JComboBox<>();
        jPanel8 = new JPanel();
        btnHuy = new JButton();
        btnUpdate = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));

        jPanel15.setBackground(new Color(0, 153, 153));
        jPanel15.setMinimumSize(new Dimension(100, 60));
        jPanel15.setPreferredSize(new Dimension(500, 50));
        jPanel15.setLayout(new BorderLayout());

        jLabel8.setFont(new Font("Roboto Medium", 0, 18)); 
        jLabel8.setForeground(new Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel8.setText("CẬP NHẬP TÀI KHOẢN");
        jPanel15.add(jLabel8, BorderLayout.CENTER);

        getContentPane().add(jPanel15, BorderLayout.NORTH);

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));

        jPanel18.setBackground(new Color(255, 255, 255));
        jPanel18.setPreferredSize(new Dimension(500, 40));
        jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

        lblHoTen.setFont(new Font("Roboto", 0, 14)); 
        lblHoTen.setText("Username");
        lblHoTen.setMaximumSize(new Dimension(44, 40));
        lblHoTen.setPreferredSize(new Dimension(150, 40));
        jPanel18.add(lblHoTen);

        txtUsername.setEditable(false);
        txtUsername.setFont(new Font("Roboto", 0, 14)); 
        txtUsername.setToolTipText("");
        txtUsername.setFocusable(false);
        txtUsername.setPreferredSize(new Dimension(330, 40));
        jPanel18.add(txtUsername);

        jPanel1.add(jPanel18);

        jPanel21.setBackground(new Color(255, 255, 255));
        jPanel21.setPreferredSize(new Dimension(500, 40));
        jPanel21.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

        jLabel14.setFont(new Font("Roboto", 0, 14)); 
        jLabel14.setText("Nhân viên");
        jLabel14.setMaximumSize(new Dimension(44, 40));
        jLabel14.setPreferredSize(new Dimension(150, 40));
        jPanel21.add(jLabel14);

        cboxNhanVien.setEnabled(false);
        cboxNhanVien.setFocusable(false);
        cboxNhanVien.setPreferredSize(new Dimension(330, 40));
        jPanel21.add(cboxNhanVien);

        jPanel1.add(jPanel21);

        jPanel22.setBackground(new Color(255, 255, 255));
        jPanel22.setPreferredSize(new Dimension(500, 40));
        jPanel22.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

        jLabel15.setFont(new Font("Roboto", 0, 14)); 
        jLabel15.setText("Vai trò");
        jLabel15.setMaximumSize(new Dimension(44, 40));
        jLabel15.setPreferredSize(new Dimension(150, 40));
        jPanel22.add(jLabel15);

        cboxVaiTro.setPreferredSize(new Dimension(330, 40));
        jPanel22.add(cboxVaiTro);

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

        btnUpdate.setBackground(new Color(0, 204, 102));
        btnUpdate.setFont(new Font("Roboto Mono Medium", 0, 16)); 
        btnUpdate.setForeground(new Color(255, 255, 255));
        btnUpdate.setText("Cập nhập");
        btnUpdate.setBorderPainted(false);
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setFocusable(false);
        btnUpdate.setPreferredSize(new Dimension(200, 40));
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel8.add(btnUpdate);

        getContentPane().add(jPanel8, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }

    private void btnHuyActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void btnUpdateActionPerformed(ActionEvent evt) {
        if (isValidateFields()) {
            TaiKhoan e = getInputFields();
            TK_CON.update(e);
            MessageDialog.info(this, "Cập nhập thành công!");
            TK_GUI.loadTable(TK_CON.getAllList());
            this.dispose();
        }
    }

    private JButton btnHuy;
    private JButton btnUpdate;
    private JComboBox<String> cboxNhanVien;
    private JComboBox<String> cboxVaiTro;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel15;
    private JPanel jPanel18;
    private JPanel jPanel21;
    private JPanel jPanel22;
    private JPanel jPanel8;
    private JLabel lblHoTen;
    private JTextField txtUsername;
}
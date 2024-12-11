package gui.dialog;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import controller.VaiTroController;
import entity.VaiTro;
import gui.page.VaiTroPage;
import utils.MessageDialog;

public class UpdateVaiTroDialog extends JDialog {

    private VaiTroController VT_CON = new VaiTroController();
    private VaiTroPage VT_GUI;
    private VaiTro vt;

    public UpdateVaiTroDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public UpdateVaiTroDialog(Frame parent, boolean modal, VaiTroPage NV_GUI, VaiTro vt) {
        super(parent, modal);
        initComponents();
        this.VT_GUI = NV_GUI;
        this.vt = vt;
        fillInput();
    }

    private void fillInput() {
        txtId.setText(vt.getId());
        txtId.setEditable(false);
        txtTen.setText(vt.getTen());
    }

    private boolean isValidateFields() {
        if (txtTen.getText().trim().equals("")) {
            MessageDialog.warring(this, "Tên vai trò không được rỗng!");
            txtTen.requestFocus();
            return false;
        }

        return true;
    }

    private VaiTro getInputFields() {
        String id = vt.getId();
        String ten = txtTen.getText().trim();

        return new VaiTro(id, ten);
    }

    private void initComponents() {

        jPanel15 = new JPanel();
        jLabel8 = new JLabel();
        jPanel1 = new JPanel();
        jPanel19 = new JPanel();
        lblmaVaiTro = new JLabel();
        txtId = new JTextField();
        jPanel18 = new JPanel();
        lblHoTen = new JLabel();
        txtTen = new JTextField();
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
        jLabel8.setText("CẬP NHẬP VAI TRÒ");
        jPanel15.add(jLabel8, BorderLayout.CENTER);

        getContentPane().add(jPanel15, BorderLayout.NORTH);

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 16));

        jPanel19.setBackground(new Color(255, 255, 255));
        jPanel19.setPreferredSize(new Dimension(500, 40));
        jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

        lblmaVaiTro.setFont(new Font("Roboto", 0, 14)); 
        lblmaVaiTro.setText("Mã vai trò");
        lblmaVaiTro.setMaximumSize(new Dimension(44, 40));
        lblmaVaiTro.setPreferredSize(new Dimension(150, 40));
        jPanel19.add(lblmaVaiTro);

        txtId.setFont(new Font("Roboto", 0, 14)); 
        txtId.setToolTipText("");
        txtId.setFocusable(false);
        txtId.setPreferredSize(new Dimension(330, 40));
        jPanel19.add(txtId);

        jPanel1.add(jPanel19);

        jPanel18.setBackground(new Color(255, 255, 255));
        jPanel18.setPreferredSize(new Dimension(500, 40));
        jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

        lblHoTen.setFont(new Font("Roboto", 0, 14)); 
        lblHoTen.setText("Tên vai trò");
        lblHoTen.setMaximumSize(new Dimension(44, 40));
        lblHoTen.setPreferredSize(new Dimension(150, 40));
        jPanel18.add(lblHoTen);

        txtTen.setFont(new Font("Roboto", 0, 14)); // NOI18N
        txtTen.setToolTipText("");
        txtTen.setPreferredSize(new Dimension(330, 40));
        jPanel18.add(txtTen);

        jPanel1.add(jPanel18);

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
        btnUpdate.setText("CẬP NHẬP");
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
            VaiTro e = getInputFields();
            VT_CON.update(e);
            VT_GUI.loadTable();
            this.dispose();
        }
    }

    private JButton btnHuy;
    private JButton btnUpdate;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel15;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel8;
    private JLabel lblHoTen;
    private JLabel lblmaVaiTro;
    private JTextField txtId;
    private JTextField txtTen;
}
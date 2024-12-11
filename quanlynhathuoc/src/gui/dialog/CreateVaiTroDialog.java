package gui.dialog;

import controller.VaiTroController;
import entity.VaiTro;
import gui.page.VaiTroPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import utils.MessageDialog;

public class CreateVaiTroDialog extends JDialog {

    private VaiTroController VT_CON = new VaiTroController();
    private VaiTroPage VT_GUI;

    public CreateVaiTroDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public CreateVaiTroDialog(Frame parent, boolean modal, VaiTroPage VT_GUI) {
        super(parent, modal);
        initComponents();
        this.VT_GUI = VT_GUI;
    }

    private boolean isValidateFields() {
        if (txtId.getText().trim().equals("")) {
            MessageDialog.warring(this, "Mã vai trò không được rỗng!");
            txtId.requestFocus();
            return false;
        } else {
            List<VaiTro> listVT = VT_CON.getAllList();
            for (VaiTro vt : listVT) {
                if (vt.getId().equals(txtId.getText())) {
                    MessageDialog.warring(this, "Trùng mã!");
                    txtId.requestFocus();
                    return false;
                }
            }
        }

        if (txtTen.getText().trim().equals("")) {
            MessageDialog.warring(this, "Tên vai trò không được rỗng!");
            txtTen.requestFocus();
            return false;
        }

        return true;
    }

    private VaiTro getInputFields() {
        String id = txtId.getText().trim();
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
        btnAdd = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));

        jPanel15.setBackground(new Color(0, 153, 153));
        jPanel15.setMinimumSize(new Dimension(100, 60));
        jPanel15.setPreferredSize(new Dimension(500, 50));
        jPanel15.setLayout(new BorderLayout());

        jLabel8.setFont(new Font("Roboto Medium", 0, 18)); 
        jLabel8.setForeground(new Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel8.setText("THÊM VAI TRÒ");
        jPanel15.add(jLabel8, BorderLayout.CENTER);

        getContentPane().add(jPanel15, BorderLayout.NORTH);

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(600, 600));
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

        txtTen.setFont(new Font("Roboto", 0, 14));
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
            VaiTro e = getInputFields();
            VT_CON.create(e);
            VT_GUI.loadTable();
            this.dispose();
        }
    }

    private JButton btnAdd;
    private JButton btnHuy;
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
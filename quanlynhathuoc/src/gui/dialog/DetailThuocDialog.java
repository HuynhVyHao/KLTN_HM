package gui.dialog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;

import controller.DanhMucController;
import controller.DonViTinhController;
import controller.XuatXuController;
import entity.DanhMuc;
import entity.DonViTinh;
import entity.Thuoc;
import entity.XuatXu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class DetailThuocDialog extends JDialog {

    private byte[] thuocImage;
    private Thuoc thuoc;

    public DetailThuocDialog(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public DetailThuocDialog(Frame parent, boolean modal, Thuoc thuoc) {
        super(parent, modal);
        initComponents();
        this.thuoc = thuoc;
        fillInput();
        fillCombobox();
    }

    private void fillCombobox() {
        List<DanhMuc> listDM = new DanhMucController().getAllList();
        for (DanhMuc vt : listDM) {
            cboxDanhMuc.addItem(vt.getTen());
        }

        List<DonViTinh> listDVT = new DonViTinhController().getAllList();
        for (DonViTinh vt : listDVT) {
            cboxDonViTinh.addItem(vt.getTen());
        }

        List<XuatXu> listXX = new XuatXuController().getAllList();
        for (XuatXu vt : listXX) {
            cboxXuatXu.addItem(vt.getTen());
        }
    }

    private void fillInput() {
        txtTenThuoc.setText(thuoc.getTenThuoc());
        thuocImage = thuoc.getHinhAnh();
        ImageIcon imageIcon = new ImageIcon(
                new ImageIcon(thuocImage).getImage().getScaledInstance(txtHinhAnh.getWidth(), txtHinhAnh.getHeight(), Image.SCALE_SMOOTH));
        txtHinhAnh.setIcon(imageIcon);
        txtThanhPhan.setText(thuoc.getThanhPhan());
        cboxDanhMuc.setSelectedItem(thuoc.getDanhMuc().getTen());
        cboxDonViTinh.setSelectedItem(thuoc.getDonViTinh().getTen());
        cboxXuatXu.setSelectedItem(thuoc.getXuatXu().getTen());
        txtSoLuong.setText(String.valueOf(thuoc.getSoLuongTon()));
        txtGiaNhap.setText(String.valueOf(thuoc.getGiaNhap()));
        txtDonGia.setText(String.valueOf(thuoc.getDonGia()));
        txtHanSuDung.setDate(thuoc.getHanSuDung());
    }

    private void initComponents() {

        jPanel15 = new JPanel();
        jLabel8 = new JLabel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        txtHinhAnh = new JLabel();
        jPanel2 = new JPanel();
        jPanel1 = new JPanel();
        jPanel18 = new JPanel();
        lblHoTen = new JLabel();
        txtTenThuoc = new JTextField();
        jPanel19 = new JPanel();
        jLabel12 = new JLabel();
        jScrollPane1 = new JScrollPane();
        txtThanhPhan = new JTextArea();
        jPanel21 = new JPanel();
        jLabel14 = new JLabel();
        cboxDanhMuc = new JComboBox<>();
        jPanel23 = new JPanel();
        jLabel16 = new JLabel();
        cboxXuatXu = new JComboBox<>();
        jPanel24 = new JPanel();
        jLabel17 = new JLabel();
        cboxDonViTinh = new JComboBox<>();
        jPanel22 = new JPanel();
        jLabel15 = new JLabel();
        txtHanSuDung = new JDateChooser();
        jPanel26 = new JPanel();
        jLabel19 = new JLabel();
        txtGiaNhap = new JTextField();
        jPanel25 = new JPanel();
        jLabel18 = new JLabel();
        txtDonGia = new JTextField();
        jPanel20 = new JPanel();
        jLabel13 = new JLabel();
        txtSoLuong = new JTextField();
        jPanel8 = new JPanel();
        btnHuy = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jPanel15.setBackground(new Color(0, 153, 153));
        jPanel15.setMinimumSize(new Dimension(100, 60));
        jPanel15.setPreferredSize(new Dimension(500, 50));
        jPanel15.setLayout(new BorderLayout());

        jLabel8.setFont(new Font("Roboto Medium", 0, 18)); 
        jLabel8.setForeground(new Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel8.setText("CHI TIẾT THUỐC");
        jLabel8.setPreferredSize(new Dimension(149, 40));
        jPanel15.add(jLabel8, BorderLayout.CENTER);

        getContentPane().add(jPanel15, BorderLayout.NORTH);

        jPanel3.setBackground(new Color(255, 255, 255));
        jPanel3.setPreferredSize(new Dimension(400, 100));

        jPanel4.setBackground(new Color(255, 255, 255));
        jPanel4.setBorder(new LineBorder(new Color(237, 237, 237), 2, true));
        jPanel4.setPreferredSize(new Dimension(300, 300));
        jPanel4.setLayout(new BorderLayout());

        txtHinhAnh.setBackground(new Color(255, 255, 255));
        txtHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
        txtHinhAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
        txtHinhAnh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtHinhAnh.setPreferredSize(new Dimension(200, 100));
        jPanel4.add(txtHinhAnh, BorderLayout.CENTER);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );

        getContentPane().add(jPanel3, BorderLayout.WEST);

        jPanel2.setBackground(new Color(255, 255, 255));
        jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 16));

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(650, 470));
        jPanel1.setLayout(new GridLayout(5, 2, 16, 8));

        jPanel18.setBackground(new Color(255, 255, 255));
        jPanel18.setPreferredSize(new Dimension(150, 40));
        jPanel18.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        lblHoTen.setFont(new Font("Roboto", 0, 14)); 
        lblHoTen.setText("Tên thuốc");
        lblHoTen.setMaximumSize(new Dimension(44, 40));
        lblHoTen.setPreferredSize(new Dimension(150, 40));
        jPanel18.add(lblHoTen);

        txtTenThuoc.setEditable(false);
        txtTenThuoc.setFont(new Font("Roboto", 0, 14)); 
        txtTenThuoc.setToolTipText("");
        txtTenThuoc.setEnabled(false);
        txtTenThuoc.setPreferredSize(new Dimension(300, 40));
        jPanel18.add(txtTenThuoc);

        jPanel1.add(jPanel18);

        jPanel19.setBackground(new Color(255, 255, 255));
        jPanel19.setPreferredSize(new Dimension(150, 40));
        jPanel19.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel12.setFont(new Font("Roboto", 0, 14)); 
        jLabel12.setText("Thành phần");
        jLabel12.setMaximumSize(new Dimension(44, 40));
        jLabel12.setPreferredSize(new Dimension(150, 40));
        jPanel19.add(jLabel12);

        jScrollPane1.setMaximumSize(new Dimension(300, 70));
        jScrollPane1.setPreferredSize(new Dimension(300, 70));

        txtThanhPhan.setEditable(false);
        txtThanhPhan.setColumns(20);
        txtThanhPhan.setFont(new Font("Roboto", 0, 14)); 
        txtThanhPhan.setLineWrap(true);
        txtThanhPhan.setRows(4);
        txtThanhPhan.setEnabled(false);
        jScrollPane1.setViewportView(txtThanhPhan);

        jPanel19.add(jScrollPane1);

        jPanel1.add(jPanel19);

        jPanel21.setBackground(new Color(255, 255, 255));
        jPanel21.setPreferredSize(new Dimension(150, 40));
        jPanel21.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel14.setFont(new Font("Roboto", 0, 14)); 
        jLabel14.setText("Danh mục");
        jLabel14.setMaximumSize(new Dimension(44, 40));
        jLabel14.setPreferredSize(new Dimension(150, 40));
        jPanel21.add(jLabel14);

        cboxDanhMuc.setEnabled(false);
        cboxDanhMuc.setPreferredSize(new Dimension(300, 40));
        jPanel21.add(cboxDanhMuc);

        jPanel1.add(jPanel21);

        jPanel23.setBackground(new Color(255, 255, 255));
        jPanel23.setPreferredSize(new Dimension(150, 40));
        jPanel23.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel16.setFont(new Font("Roboto", 0, 14)); 
        jLabel16.setText("Xuất xứ");
        jLabel16.setMaximumSize(new Dimension(44, 40));
        jLabel16.setPreferredSize(new Dimension(150, 40));
        jPanel23.add(jLabel16);

        cboxXuatXu.setEnabled(false);
        cboxXuatXu.setPreferredSize(new Dimension(300, 40));
        jPanel23.add(cboxXuatXu);

        jPanel1.add(jPanel23);

        jPanel24.setBackground(new Color(255, 255, 255));
        jPanel24.setPreferredSize(new Dimension(150, 40));
        jPanel24.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel17.setFont(new Font("Roboto", 0, 14)); 
        jLabel17.setText("Đơn vị tính");
        jLabel17.setMaximumSize(new Dimension(44, 40));
        jLabel17.setPreferredSize(new Dimension(150, 40));
        jPanel24.add(jLabel17);

        cboxDonViTinh.setEnabled(false);
        cboxDonViTinh.setPreferredSize(new Dimension(300, 40));
        jPanel24.add(cboxDonViTinh);

        jPanel1.add(jPanel24);

        jPanel22.setBackground(new Color(255, 255, 255));
        jPanel22.setPreferredSize(new Dimension(500, 40));
        jPanel22.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel15.setFont(new Font("Roboto", 0, 14)); 
        jLabel15.setText("Hạn sử dụng");
        jLabel15.setMaximumSize(new Dimension(44, 40));
        jLabel15.setPreferredSize(new Dimension(150, 40));
        jPanel22.add(jLabel15);

        txtHanSuDung.setBackground(new Color(255, 255, 255));
        txtHanSuDung.setDateFormatString("dd/MM/yyyy");
        txtHanSuDung.setEnabled(false);
        txtHanSuDung.setPreferredSize(new Dimension(300, 40));
        jPanel22.add(txtHanSuDung);

        jPanel1.add(jPanel22);

        jPanel26.setBackground(new Color(255, 255, 255));
        jPanel26.setPreferredSize(new Dimension(500, 40));
        jPanel26.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel19.setFont(new Font("Roboto", 0, 14)); 
        jLabel19.setText("Giá nhập");
        jLabel19.setMaximumSize(new Dimension(44, 40));
        jLabel19.setPreferredSize(new Dimension(150, 40));
        jPanel26.add(jLabel19);

        txtGiaNhap.setFont(new Font("Roboto", 0, 14)); 
        txtGiaNhap.setEnabled(false);
        txtGiaNhap.setPreferredSize(new Dimension(300, 40));
        jPanel26.add(txtGiaNhap);

        jPanel1.add(jPanel26);

        jPanel25.setBackground(new Color(255, 255, 255));
        jPanel25.setPreferredSize(new Dimension(500, 40));
        jPanel25.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel18.setFont(new Font("Roboto", 0, 14)); 
        jLabel18.setText("Đơn giá");
        jLabel18.setMaximumSize(new Dimension(44, 40));
        jLabel18.setPreferredSize(new Dimension(150, 40));
        jPanel25.add(jLabel18);

        txtDonGia.setFont(new Font("Roboto", 0, 14)); 
        txtDonGia.setEnabled(false);
        txtDonGia.setPreferredSize(new Dimension(300, 40));
        jPanel25.add(txtDonGia);

        jPanel1.add(jPanel25);

        jPanel20.setBackground(new Color(255, 255, 255));
        jPanel20.setPreferredSize(new Dimension(500, 40));
        jPanel20.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 0));

        jLabel13.setFont(new Font("Roboto", 0, 14)); 
        jLabel13.setText("Số lượng");
        jLabel13.setMaximumSize(new Dimension(44, 40));
        jLabel13.setPreferredSize(new Dimension(150, 40));
        jPanel20.add(jLabel13);

        txtSoLuong.setFont(new Font("Roboto", 0, 14)); 
        txtSoLuong.setEnabled(false);
        txtSoLuong.setPreferredSize(new Dimension(300, 40));
        jPanel20.add(txtSoLuong);

        jPanel1.add(jPanel20);

        jPanel2.add(jPanel1);

        getContentPane().add(jPanel2, BorderLayout.CENTER);

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

        getContentPane().add(jPanel8, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }

    private void btnHuyActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private JButton btnHuy;
    private JComboBox<String> cboxDanhMuc;
    private JComboBox<String> cboxDonViTinh;
    private JComboBox<String> cboxXuatXu;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel15;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel2;
    private JPanel jPanel20;
    private JPanel jPanel21;
    private JPanel jPanel22;
    private JPanel jPanel23;
    private JPanel jPanel24;
    private JPanel jPanel25;
    private JPanel jPanel26;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel8;
    private JScrollPane jScrollPane1;
    private JLabel lblHoTen;
    private JTextField txtDonGia;
    private JTextField txtGiaNhap;
    private JDateChooser txtHanSuDung;
    private JLabel txtHinhAnh;
    private JTextField txtSoLuong;
    private JTextField txtTenThuoc;
    private JTextArea txtThanhPhan;
}

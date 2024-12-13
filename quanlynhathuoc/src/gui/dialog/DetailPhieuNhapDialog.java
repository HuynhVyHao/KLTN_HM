package gui.dialog;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import entity.ChiTietPhieuNhap;
import entity.PhieuNhap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utils.Formatter;
import utils.WritePDF;

public class DetailPhieuNhapDialog extends JDialog {

	private List<ChiTietPhieuNhap> listCTPN;
	private DefaultTableModel modal;

	public DetailPhieuNhapDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	public DetailPhieuNhapDialog(Frame parent, boolean modal, List<ChiTietPhieuNhap> ctpn) {
		super(parent, modal);
		this.listCTPN = ctpn;
		initComponents();
		fillInput();
		fillTable();
	}

	private void fillInput() {
		PhieuNhap hoaDon = listCTPN.get(0).getPhieuNhap();
		txtMaHD.setText(hoaDon.getId());
		txtTenKH.setText(hoaDon.getNcc().getTen());
		txtTenNV.setText(hoaDon.getNhanVien().getHoTen());
	}

	private void fillTable() {
		modal = new DefaultTableModel();
		String[] header = new String[] { "STT", "Tên thuốc", "Đơn vị tính", "Số lượng", "Đơn giá", "Thành tiền" };
		modal.setColumnIdentifiers(header);
		table.setModel(modal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		table.setDefaultRenderer(Object.class, centerRenderer);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);

		loadTableCTHD(listCTPN);
	}

	public void loadTableCTHD(List<ChiTietPhieuNhap> list) {
		modal.setRowCount(0);

		listCTPN = list;
		int stt = 1;
		double sum = 0;
		for (ChiTietPhieuNhap e : listCTPN) {
			sum += e.getThanhTien();
			modal.addRow(new Object[] { String.valueOf(stt), e.getThuoc().getTenThuoc(), e.getThuoc().getDonViTinh(),
					e.getSoLuong(), Formatter.FormatVND(e.getDonGia()), Formatter.FormatVND(e.getThanhTien()) });
			stt++;
		}
		txtTong.setText(Formatter.FormatVND(sum));
	}

	private void initComponents() {

		jPanel15 = new JPanel();
		jLabel8 = new JLabel();
		jPanel2 = new JPanel();
		hoaDonPanel = new JPanel();
		jPanel7 = new JPanel();
		jLabel4 = new JLabel();
		txtMaHD = new JTextField();
		jPanel9 = new JPanel();
		jLabel5 = new JLabel();
		txtTenKH = new JTextField();
		jPanel11 = new JPanel();
		jLabel7 = new JLabel();
		txtTenNV = new JTextField();
		jPanel3 = new JPanel();
		imagePanel = new JPanel();
		txtHinhAnh = new JLabel();
		tableItemPanel = new JPanel();
		jScrollPane1 = new JScrollPane();
		table = new JTable();
		jPanel1 = new JPanel();
		jPanel12 = new JPanel();
		jLabel9 = new JLabel();
		txtTong = new JTextField();
		jPanel16 = new JPanel();
		lblThuoc = new JLabel();
		jPanel8 = new JPanel();
		btnHuy = new JButton();
		btnPrint = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		jPanel15.setBackground(new Color(0, 153, 153));
		jPanel15.setMinimumSize(new Dimension(100, 60));
		jPanel15.setPreferredSize(new Dimension(500, 50));
		jPanel15.setLayout(new BorderLayout());

		jLabel8.setFont(new Font("Roboto Medium", 0, 18));
		jLabel8.setForeground(new Color(255, 255, 255));
		jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel8.setText("CHI TIẾT PHIẾU NHẬP");
		jLabel8.setPreferredSize(new Dimension(149, 40));
		jPanel15.add(jLabel8, BorderLayout.CENTER);

		getContentPane().add(jPanel15, BorderLayout.NORTH);

		jPanel2.setBackground(new Color(255, 255, 255));
		jPanel2.setLayout(new BorderLayout());

		hoaDonPanel.setBackground(new Color(255, 255, 255));
		hoaDonPanel.setPreferredSize(new Dimension(1200, 80));
		hoaDonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 16));

		jPanel7.setBackground(new Color(255, 255, 255));
		jPanel7.setPreferredSize(new Dimension(340, 40));
		jPanel7.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		jLabel4.setFont(new Font("Roboto", 0, 14));
		jLabel4.setText("Mã hóa đơn ");
		jLabel4.setPreferredSize(new Dimension(120, 40));
		jPanel7.add(jLabel4);

		txtMaHD.setEditable(false);
		txtMaHD.setFont(new Font("Roboto Mono", 1, 14));
		txtMaHD.setText("Z2NX8CN1A");
		txtMaHD.setFocusable(false);
		txtMaHD.setPreferredSize(new Dimension(200, 40));
		jPanel7.add(txtMaHD);

		hoaDonPanel.add(jPanel7);

		jPanel9.setBackground(new Color(255, 255, 255));
		jPanel9.setPreferredSize(new Dimension(340, 40));
		jPanel9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		jLabel5.setFont(new Font("Roboto", 0, 14));
		jLabel5.setText("Tên khách hàng");
		jLabel5.setPreferredSize(new Dimension(120, 40));
		jPanel9.add(jLabel5);

		txtTenKH.setEditable(false);
		txtTenKH.setFont(new Font("Roboto", 0, 14));
		txtTenKH.setText("Nguyễn Văn A");
		txtTenKH.setFocusable(false);
		txtTenKH.setPreferredSize(new Dimension(200, 40));
		jPanel9.add(txtTenKH);

		hoaDonPanel.add(jPanel9);

		jPanel11.setBackground(new Color(255, 255, 255));
		jPanel11.setPreferredSize(new Dimension(340, 40));
		jPanel11.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		jLabel7.setFont(new Font("Roboto", 0, 14));
		jLabel7.setText("Tên nhân viên");
		jLabel7.setPreferredSize(new Dimension(120, 40));
		jPanel11.add(jLabel7);

		txtTenNV.setEditable(false);
		txtTenNV.setFont(new Font("Roboto", 0, 14));
		txtTenNV.setText("Văn A");
		txtTenNV.setFocusable(false);
		txtTenNV.setPreferredSize(new Dimension(200, 40));
		jPanel11.add(txtTenNV);

		hoaDonPanel.add(jPanel11);

		jPanel2.add(hoaDonPanel, BorderLayout.PAGE_START);

		jPanel3.setBackground(new Color(255, 255, 255));
		jPanel3.setPreferredSize(new Dimension(400, 100));

		imagePanel.setBackground(new Color(255, 255, 255));
		imagePanel.setBorder(new LineBorder(new Color(237, 237, 237), 2, true));
		imagePanel.setPreferredSize(new Dimension(300, 300));
		imagePanel.setLayout(new BorderLayout());

		txtHinhAnh.setBackground(new Color(255, 255, 255));
		txtHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
		txtHinhAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
		txtHinhAnh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		txtHinhAnh.setPreferredSize(new Dimension(200, 100));
		imagePanel.add(txtHinhAnh, BorderLayout.CENTER);

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel3Layout.createSequentialGroup().addContainerGap(26, Short.MAX_VALUE)
						.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
						.addGap(24, 24, 24)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout
						.createSequentialGroup().addContainerGap(84, Short.MAX_VALUE).addComponent(imagePanel,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(136, 136, 136)));

		jPanel2.add(jPanel3, BorderLayout.WEST);

		tableItemPanel.setLayout(new BorderLayout());

		jScrollPane1.setBorder(new LineBorder(new Color(240, 240, 240), 1, true));

		table.setFocusable(false);
		table.setRowHeight(40);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowHorizontalLines(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(table);

		tableItemPanel.add(jScrollPane1, BorderLayout.CENTER);

		jPanel1.setBackground(new Color(255, 255, 255));
		jPanel1.setPreferredSize(new Dimension(800, 60));
		jPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT));

		jPanel12.setBackground(new Color(255, 255, 255));
		jPanel12.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		jLabel9.setFont(new Font("Roboto", 1, 14));
		jLabel9.setForeground(new Color(255, 51, 0));
		jLabel9.setText("Tổng hóa đơn:");
		jLabel9.setPreferredSize(new Dimension(120, 40));
		jPanel12.add(jLabel9);

		txtTong.setEditable(false);
		txtTong.setFont(new Font("Roboto Mono Medium", 0, 14));
		txtTong.setForeground(new Color(255, 51, 0));
		txtTong.setText("1000000");
		txtTong.setFocusable(false);
		txtTong.setPreferredSize(new Dimension(200, 40));
		jPanel12.add(txtTong);

		jPanel1.add(jPanel12);

		tableItemPanel.add(jPanel1, BorderLayout.PAGE_END);

		jPanel16.setBackground(new Color(0, 153, 153));
		jPanel16.setMinimumSize(new Dimension(100, 60));
		jPanel16.setPreferredSize(new Dimension(500, 30));
		jPanel16.setLayout(new BorderLayout());

		lblThuoc.setFont(new Font("Roboto Medium", 0, 14));
		lblThuoc.setForeground(new Color(255, 255, 255));
		lblThuoc.setHorizontalAlignment(SwingConstants.CENTER);
		lblThuoc.setText("Thông tin thuốc");
		jPanel16.add(lblThuoc, BorderLayout.CENTER);

		tableItemPanel.add(jPanel16, BorderLayout.NORTH);

		jPanel2.add(tableItemPanel, BorderLayout.CENTER);

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

		btnPrint.setBackground(new Color(0, 153, 153));
		btnPrint.setFont(new Font("Roboto Mono Medium", 0, 16));
		btnPrint.setForeground(new Color(255, 255, 255));
		btnPrint.setText("In hóa đơn");
		btnPrint.setBorderPainted(false);
		btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPrint.setFocusPainted(false);
		btnPrint.setFocusable(false);
		btnPrint.setPreferredSize(new Dimension(200, 40));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnPrintActionPerformed(evt);
			}
		});
		jPanel8.add(btnPrint);

		getContentPane().add(jPanel8, BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
	}

	private void btnHuyActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	private void tableMouseClicked(MouseEvent evt) {
		int row = table.getSelectedRow();
		byte[] thuocImage = listCTPN.get(row).getThuoc().getHinhAnh();
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(thuocImage).getImage()
				.getScaledInstance(txtHinhAnh.getWidth(), txtHinhAnh.getHeight(), Image.SCALE_SMOOTH));
		txtHinhAnh.setIcon(imageIcon);
	}

	private void btnPrintActionPerformed(ActionEvent evt) {
		PhieuNhap phieuNhap = listCTPN.get(0).getPhieuNhap();
		new WritePDF().printPhieuNhap(phieuNhap, listCTPN);
	}

	private JButton btnHuy;
	private JButton btnPrint;
	private JPanel hoaDonPanel;
	private JPanel imagePanel;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JPanel jPanel1;
	private JPanel jPanel11;
	private JPanel jPanel12;
	private JPanel jPanel15;
	private JPanel jPanel16;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel7;
	private JPanel jPanel8;
	private JPanel jPanel9;
	private JScrollPane jScrollPane1;
	private JLabel lblThuoc;
	private JTable table;
	private JPanel tableItemPanel;
	private JLabel txtHinhAnh;
	private JTextField txtMaHD;
	private JTextField txtTenKH;
	private JTextField txtTenNV;
	private JTextField txtTong;
}

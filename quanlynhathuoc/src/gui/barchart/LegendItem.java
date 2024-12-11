package gui.barchart;
import javax.swing.*;
import java.awt.*;

public class LegendItem extends JPanel {

    private LabelColor lbColor;
    private JLabel lbName;

    public LegendItem(ModelLegend data) {
        initComponents();
        setOpaque(false);  // Đảm bảo panel không che khuất nền
        lbColor.setBackground(data.getColor());
        lbName.setText(data.getName());
    }

    private void initComponents() {
        // Khởi tạo các thành phần của panel
        lbColor = new LabelColor();
        lbName = new JLabel();

        // Thiết lập cho lbColor
        lbColor.setText("labelColor1");

        // Thiết lập cho lbName
        lbName.setForeground(new Color(100, 100, 100));  // Màu sắc của tên
        lbName.setText("Name");  // Tên mặc định

        // Thiết lập layout
        javax.swing.GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        
        // Định dạng chiều ngang của các thành phần
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbColor, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)  // Kích thước màu
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbName)  // Tên
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // Định dạng chiều dọc của các thành phần
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)  // Vị trí lbColor
                    .addComponent(lbName))  // Vị trí lbName
                .addContainerGap())
        );
    }
}

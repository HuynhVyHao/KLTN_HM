package gui.curvechart;

import java.awt.*;

import javax.swing.*;

public class LegendItem2 extends JPanel {

    public LegendItem2(ModelLegend2 data) {
        initComponents();
        setOpaque(false);
        lbColor.setBackground(data.getColor());
        lbColor.setForeground(data.getColorLight());
        lbName.setText(data.getName());
    }

    private void initComponents() {

        lbColor = new LabelColor2();
        lbName = new JLabel();

        lbColor.setText("labelColor1");

        lbName.setForeground(new Color(180, 180, 180));
        lbName.setText("Name");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbColor, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbName)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbName)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lbColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }             

    private LabelColor2 lbColor;
    private JLabel lbName;
}

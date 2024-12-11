package gui.barchart;

import gui.barchart.blankchart.BlankPlotChart;
import gui.barchart.blankchart.BlankPlotChatRender;
import gui.barchart.blankchart.SeriesSize;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Chart extends JPanel {

    private List<ModelLegend> legends = new ArrayList<>();
    private List<ModelChart> model = new ArrayList<>();
    private final int seriesSize = 12;
    private final int seriesSpace = 6;
    private final Animator animator;
    private float animate;

    public Chart() {
        initComponents();
        animator = createAnimator();
        configureBlankPlotChart();
    }

    // Tạo Animator với các cấu hình cần thiết
    private Animator createAnimator() {
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animate = fraction;
                repaint();
            }
        };
        Animator animator = new Animator(800, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        return animator;
    }

    // Cấu hình BlankPlotChart
    private void configureBlankPlotChart() {
        blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
            @Override
            public String getLabelText(int index) {
                return model.get(index).getLabel();
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
                double totalSeriesWidth = calculateTotalSeriesWidth();
                double x = (size.getWidth() - totalSeriesWidth) / 2;
                for (int i = 0; i < legends.size(); i++) {
                    renderLegendSeries(g2, chart, size, index, x, i);
                    x += seriesSpace + seriesSize;
                }
            }
        });
    }

    // Tính tổng chiều rộng của các series
    private double calculateTotalSeriesWidth() {
        return (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
    }

    // Render từng series trong biểu đồ
    private void renderLegendSeries(Graphics2D g2, BlankPlotChart chart, SeriesSize size, int index, double x, int legendIndex) {
        ModelLegend legend = legends.get(legendIndex);
        g2.setColor(legend.getColor());
        double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[legendIndex], size.getHeight()) * animate;
        g2.fillRect((int) (size.getX() + x), (int) (size.getY() + size.getHeight() - seriesValues), seriesSize, (int) seriesValues);
    }

    // Thêm biểu tượng cho một legend
    public void addLegend(String name, Color color) {
        ModelLegend data = new ModelLegend(name, color);
        legends.add(data);
        updateLegendPanel(data);
    }

    // Cập nhật panelLegend khi thêm legend mới
    private void updateLegendPanel(ModelLegend data) {
        SwingUtilities.invokeLater(() -> {
            panelLegend.add(new LegendItem(data));
            panelLegend.repaint();
            panelLegend.revalidate();
        });
    }

    // Thêm dữ liệu biểu đồ
    public void addData(ModelChart data) {
        model.add(data);
        updateChartLabelCount();
        updateMaxChartValues(data);
    }

    // Cập nhật số lượng nhãn trên biểu đồ
    private void updateChartLabelCount() {
        blankPlotChart.setLabelCount(model.size());
    }

    // Cập nhật giá trị tối đa của biểu đồ
    private void updateMaxChartValues(ModelChart data) {
        double max = data.getMaxValues();
        if (max > blankPlotChart.getMaxValues()) {
            blankPlotChart.setMaxValues(max);
        }
    }

    // Xóa tất cả dữ liệu và làm mới biểu đồ
    public void clear() {
        animate = 0;
        blankPlotChart.setLabelCount(0);
        model.clear();
        repaint();
    }

    // Bắt đầu hoạt ảnh (animation)
    public void start() {
        if (!animator.isRunning()) {
            animator.start();
        }
    }

    private void initComponents() {
        blankPlotChart = new BlankPlotChart();
        panelLegend = new JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        panelLegend.setOpaque(false);
        panelLegend.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panelLegend, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                    .addComponent(blankPlotChart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(blankPlotChart, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panelLegend, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }

    private BlankPlotChart blankPlotChart;
    private JPanel panelLegend;
}


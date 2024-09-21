package gui.page.thongke;

import entity.TaiKhoan;


public class ThongKePage extends javax.swing.JPanel {

    private TaiKhoan tk;
    
    public ThongKePage() {
        initComponents();

    }
    
    public ThongKePage(TaiKhoan tk) {
        this.tk = tk;
        initComponents();
    }
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPane = new javax.swing.JTabbedPane();

        setBackground(new java.awt.Color(227, 242, 223));
        setMinimumSize(new java.awt.Dimension(1130, 800));
        setPreferredSize(new java.awt.Dimension(1130, 800));
        setLayout(new java.awt.BorderLayout(0, 6));

        tabPane.setPreferredSize(new java.awt.Dimension(100, 30));
        add(tabPane, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabPane;
    // End of variables declaration//GEN-END:variables
}

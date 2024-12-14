package utils;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {
	 private int radius;

	    public RoundedBorder(int radius) {
	        this.radius = radius;
	    }

	    @Override
	    public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
	        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
	        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setColor(c.getBackground());
	        g2d.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
	        g2d.setColor(c.getForeground());
	        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
	    }

	    @Override
	    public java.awt.Insets getBorderInsets(java.awt.Component c) {
	        return new java.awt.Insets(1, 1, 1, 1);
	    }

	    @Override
	    public boolean isBorderOpaque() {
	        return true;
	    }
}

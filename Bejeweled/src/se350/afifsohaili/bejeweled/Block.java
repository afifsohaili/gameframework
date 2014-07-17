package se350.afifsohaili.bejeweled;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * @author Afif Sohaili
 */
class Block extends JLabel {
	private static final long serialVersionUID = -1499183356301196360L;
	private int width, height, borderWidth;
	private Icon icon;
	
	/**
	 * Constructor for Block class
	 * @param width the width of the block
	 * @param height the height of the block
	 * @param borderWidth the width of the block's border
	 * @param color the Color of the block
	 */
	Block(final int width, final int height, final Color color, final int borderWidth) throws IllegalArgumentException {
		init(width, height, color, borderWidth);
	}
	
	/**
	 * Constructor for Block class
	 * @param width the width of the block
	 * @param height the height of the block
	 * @param borderWidth the width of the block's border
	 * @param color the RGB value of the color of the block
	 */
	Block(final int width, final int height, final int color, final int borderWidth) throws IllegalArgumentException {
		init(width, height, new Color(color), borderWidth);
	}
	
	private void init(int width, int height, Color color, int borderWidth) {
		if (width < 0 || height < 0 || borderWidth < 0) {
			throw new IllegalArgumentException();
		}
		this.width = width;
		this.height = height;
		this.borderWidth = borderWidth;
		icon = new BlockIcon(color);
		setIcon(icon);
	}
	
	public void recolor(Color color) {
		if (((BlockIcon)icon).getColor().getRGB() != color.getRGB()) {
			setIcon(new BlockIcon(color));
//			try {
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	/**
	 * @author Afif Sohaili
	 */
	private class BlockIcon implements Icon {
		private Color color;
		
		public BlockIcon (Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return new Color(color.getRGB());
		}
		
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			RoundRectangle2D.Double block = new RoundRectangle2D.Double(borderWidth / 2, borderWidth / 2, width - (borderWidth), height - (borderWidth), width / 8, width / 8);
			BasicStroke circleBorder = new BasicStroke(borderWidth);
			Graphics2D graphics2D = (Graphics2D) g;
			graphics2D.setColor(color.darker());
			graphics2D.setStroke(circleBorder);
			graphics2D.draw(block);
			graphics2D.setColor(color);
			graphics2D.fill(block);
		}
		
		@Override
		public int getIconWidth() {
			return new Integer(width);
		}
		
		@Override
		public int getIconHeight() {
			return new Integer(height);
		}
	}
}
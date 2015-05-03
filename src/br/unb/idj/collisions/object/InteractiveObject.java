package br.unb.idj.collisions.object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import br.unb.idj.collisions.collider.Collider;
import br.unb.idj.collisions.drawing.Colors;
import br.unb.idj.collisions.util.Common;
import br.unb.idj.collisions.util.Vector2;

public class InteractiveObject extends CollidingObject {
	private static Font baseFont = null;
	static {
		try {
			baseFont = Common.loadFont("times.ttf");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private boolean highlighted = false;
	private boolean simulate = true;
	private boolean showArrow = true;

	@Override
	public void paint(Graphics2D g2d) {
		if (!isVisible())
			return;

		super.paint(g2d);

		AffineTransform oldTransform = g2d.getTransform();
		Stroke oldStroke = g2d.getStroke();
		Color oldColor = g2d.getColor();
		Font oldFont = g2d.getFont();

		Vector2 pos = getPosition();
		g2d.translate(pos.x, pos.y);

		if (isShowArrow()) {
			float v = getSpeed();
			g2d.rotate(-getRotation());
			int vx = (int) v;
			g2d.setColor(Colors.VELOCITY_ARROW);
			g2d.setStroke(new BasicStroke(2f));
			g2d.drawLine(0, 0, vx, 0);
			int xPoints[] = new int[] { vx - 9, vx - 9, vx };
			int yPoints[] = new int[] { -4, 4, 0 };
			g2d.fillPolygon(xPoints, yPoints, 3);
			Font f = baseFont.deriveFont(14.0f).deriveFont(Font.ITALIC).deriveFont(Font.BOLD);
			g2d.setFont(f);
			g2d.setColor(Colors.VELOCITY_TXT);
			g2d.drawChars(new char[] { 'v' }, 0, 1, vx - 9, -6);
			g2d.setFont(f.deriveFont(12.0f));
			g2d.drawChars(new char[] { '\u2192' }, 0, 1, vx - 11, -13);
			g2d.rotate(getRotation());
		}

		g2d.setColor(Colors.ANCHOR_FILL);
		g2d.fillArc(-3, -3, 6, 6, 0, 360);
		g2d.setColor(Colors.ANCHOR_BORDER);
		g2d.setStroke(new BasicStroke(1.5f));
		g2d.drawArc(-3, -3, 6, 6, 0, 360);

		g2d.setFont(oldFont);
		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);
		g2d.setTransform(oldTransform);
	}

	public boolean hits(Point p) {
		Collider c = getCollider();
		if (c == null)
			return false;

		return c.hitsPoint(new Vector2(p.x, p.y));
	}

	public boolean isDrawShadow() {
		return (getCollider() != null) && getCollider().isDrawShadow();
	}

	public void setDrawShadow(boolean v) {
		if (getCollider() != null) {
			getCollider().setDrawShadow(v);
		}
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		if (this.highlighted != highlighted) {
			this.highlighted = highlighted;
			Collider collider = getCollider();
			if (collider != null)
				collider.setBorderColor(highlighted ? Colors.COLLIDER_BORDER_HL : Colors.COLLIDER_BORDER);
			fireChangeEvent();
		}
	}

	public boolean isSimulate() {
		return simulate;
	}

	public void setSimulate(boolean simulate) {
		this.simulate = simulate;
	}

	public boolean isShowArrow() {
		return showArrow;
	}

	public void setShowArrow(boolean showArrow) {
		this.showArrow = showArrow;
	}

	public InteractiveObject clone() {
		InteractiveObject obj = new InteractiveObject();
		obj.setName(getName());
		obj.setPosition(getPosition());
		obj.setAnchor(getAnchor());
		obj.setRotation(getRotation());
		obj.setSpeed(getSpeed());
		obj.setTexture(getTexture());
		obj.setHighlighted(isHighlighted());
		obj.setDrawShadow(isDrawShadow());
		obj.setSimulate(isSimulate());
		obj.setVisible(isVisible());
		Collider c = getCollider() != null ? getCollider().clone() : null;
		obj.setCollider(c);
		return obj;
	}
}

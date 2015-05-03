package br.unb.idj.collisions.collider;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import br.unb.idj.collisions.object.CollidingObject;
import br.unb.idj.collisions.util.Vector2;

public class AABBCollider extends Collider {
	public AABBCollider(CollidingObject owner) {
		if (owner != null)
			owner.setCollider(this);
	}

	public AABBCollider() {
		this(null);
	}

	@Override
	public void paint(Graphics2D g2d) {
		CollidingObject owner = getOwner();
		if (owner == null)
			return;

		AffineTransform oldTransform = g2d.getTransform();
		Stroke oldStroke = g2d.getStroke();
		Color oldColor = g2d.getColor();

		Rectangle bounds = getBounds();

		// if (isDrawShadow()) {
		// g2d.translate(owner.getPosition().x - owner.getAnchor().x,
		// owner.getPosition().y - owner.getAnchor().y);
		// g2d.rotate(-owner.getRotation(), owner.getAnchor().x,
		// owner.getAnchor().y);
		// g2d.setColor(Colors.SHADOW_FILL);
		// int dy = (int) new Vector2(bounds.width, bounds.height).magnitude();
		// int dx = (int) owner.getSpeed();
		// g2d.fillRect(0, -dy / 2, dx + 1, dy);
		// g2d.setColor(Colors.SHADOW_BORDER);
		// g2d.drawRect(dx - bounds.width / 2, bounds.y, bounds.width,
		// bounds.height - 1);
		// g2d.setTransform(oldTransform);
		// }

		// if (isDrawShadow()) {
		// g2d.setColor(Colors.SHADOW_FILL);
		// int dx = (int) owner.getSpeed();
		// g2d.fillRect((int) bounds.getCenterX(), bounds.y, dx + 1,
		// bounds.height);
		// g2d.setColor(Colors.SHADOW_BORDER);
		// g2d.drawRect(dx - bounds.width / 2, bounds.y, bounds.width,
		// bounds.height - 1);
		// }

		g2d.setColor(getFillColor());
		g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		g2d.setStroke(new BasicStroke(1.5f));
		g2d.setColor(getBorderColor());
		g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);
		g2d.setTransform(oldTransform);
	}

	private Rectangle getTextureBounds() {
		CollidingObject owner = getOwner();
		if (owner != null) {
			Rectangle r = new Rectangle();
			r.width = owner.getTexture().getWidth(null);
			r.height = owner.getTexture().getHeight(null);
			return r;
		}
		return null;
	}

	private AffineTransform getTransform() {
		CollidingObject owner = getOwner();
		Vector2 p = owner.getPosition();
		Vector2 a = owner.getAnchor();
		AffineTransform t = new AffineTransform();
		t.translate(p.x - a.x, p.y - a.y);
		t.rotate(-owner.getRotation(), a.x, a.y);
		return t;
	}

	@Override
	public boolean hitsPoint(Vector2 point) {
		if (getOwner() != null) {
			Rectangle bounds = getBounds();
			return bounds.contains(new Point2D.Float(point.x, point.y));
		}
		return false;
	}

	@Override
	public Rectangle getBounds() {
		Rectangle r = new Rectangle();
		Rectangle b = getTextureBounds();
		if (b != null) {
			AffineTransform t = getTransform();
			Point2D points[] = new Point2D[] {
					new Point2D.Double(b.getMinX(), b.getMinY()),
					new Point2D.Double(b.getMaxX(), b.getMinY()),
					new Point2D.Double(b.getMaxX(), b.getMaxY()),
					new Point2D.Double(b.getMinX(), b.getMaxY()),
			};
			double minx = Double.MAX_VALUE, maxx = Double.MIN_VALUE, miny = Double.MAX_VALUE, maxy = Double.MIN_VALUE;
			for (Point2D p2d : points) {
				Point2D p = t.transform(p2d, null);
				minx = Math.min(minx, p.getX());
				maxx = Math.max(maxx, p.getX());
				miny = Math.min(miny, p.getY());
				maxy = Math.max(maxy, p.getY());
			}
			r.x = (int) minx;
			r.y = (int) miny;
			r.width = (int) (maxx - minx);
			r.height = (int) (maxy - miny);
		}
		return r;
	}

	@Override
	public Collider clone() {
		return new AABBCollider();
	}
}

package br.unb.idj.collisions.collider;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import br.unb.idj.collisions.drawing.Colors;
import br.unb.idj.collisions.object.CollidingObject;
import br.unb.idj.collisions.util.Vector2;

public class SphereCollider extends Collider {
	private float radius;
	private float sqrRadius;

	public SphereCollider(CollidingObject owner) {
		Rectangle bounds = owner.getBounds();
		float w = bounds.width / 2.0f;
		float h = bounds.height / 2.0f;
		setRadius((float) Math.sqrt(w * w + h * h));

		owner.setCollider(this);
	}

	public SphereCollider() {
		this(0);
	}

	public SphereCollider(float radius) {
		setRadius(radius);
	}

	public float getRadius() {
		return radius;
	}

	public float getSqrRadius() {
		return sqrRadius;
	}

	public void setRadius(float radius) {
		if (Float.compare(this.radius, radius) != 0) {
			this.radius = radius;
			this.sqrRadius = radius * radius;
			fireChangeEvent();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		CollidingObject owner = getOwner();
		if (owner == null)
			return;

		AffineTransform oldTransform = g2d.getTransform();
		Stroke oldStroke = g2d.getStroke();
		Color oldColor = g2d.getColor();

		Vector2 pos = owner.getPosition();
		g2d.translate(pos.x, pos.y);

		int r = (int) getRadius();
		int d = (int) (2 * r);

		if (isDrawShadow()) {
			g2d.rotate(-owner.getRotation());
			g2d.setColor(Colors.SHADOW_FILL);
			int dx = (int) owner.getSpeed();
			g2d.fillRect(0, -r, dx + 1, d);
			g2d.fillArc(dx - r, -r, d, d, -90, 180);
			g2d.setColor(Colors.SHADOW_BORDER);
			g2d.drawArc(dx - r, -r, d, d, 0, 360);
			g2d.rotate(owner.getRotation());
		}

		g2d.setColor(getFillColor());
		g2d.fillArc(-r, -r, d, d, 0, 360);
		g2d.setStroke(new BasicStroke(1.5f));
		g2d.setColor(getBorderColor());
		g2d.drawArc(-r, -r, d, d, 0, 360);

		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);
		g2d.setTransform(oldTransform);
	}

	@Override
	public boolean hitsPoint(Vector2 p) {
		CollidingObject owner = getOwner();
		if (owner == null)
			return false;

		Vector2 c = owner.getPosition();
		float dx = p.x - c.x;
		float dy = p.y - c.y;

		return (dx * dx + dy * dy) < getSqrRadius();
	}

	@Override
	public Rectangle getBounds() {
		CollidingObject owner = getOwner();
		if (owner == null)
			return null;

		Vector2 c = owner.getAnchor();
		float r = getRadius();
		int d = (int) (2 * r);
		return new Rectangle((int) (c.x - r), (int) (c.y - r), d, d);
	}

	@Override
	public Collider clone() {
		return new SphereCollider(radius);
	}
}

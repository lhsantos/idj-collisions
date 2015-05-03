package br.unb.idj.collisions.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.unb.idj.collisions.collider.Collider;

/**
 * An object that can collide.
 * 
 * @author Luciano Santos
 */
public class CollidingObject extends BasicObject implements ChangeListener {
	private Collider collider;

	public CollidingObject() {
		super();
	}

	public CollidingObject(String name) {
		super(name);
	}

	public Collider getCollider() {
		return collider;
	}

	public void setCollider(Collider collider) {
		if (this.collider != collider) {
			if (this.collider != null)
				this.collider.removeChangeListener(this);

			this.collider = collider;
			if (collider != null) {
				CollidingObject oldOwner = collider.getOwner();
				if (oldOwner != this) {
					if (oldOwner != null)
						oldOwner.setCollider(null);
					collider.setOwner(this);
				}
				collider.addChangeListener(this);
			}
			fireChangeEvent();
		}
	}

	@Override
	public Rectangle getBounds() {
		Rectangle imgBounds = super.getBounds();
		if (collider != null) {
			Rectangle collBounds = collider.getBounds();
			double minx = Math.min(imgBounds.getMinX(), collBounds.getMinX());
			double miny = Math.min(imgBounds.getMinY(), collBounds.getMinY());
			double maxx = Math.max(imgBounds.getMaxX(), collBounds.getMaxX());
			double maxy = Math.max(imgBounds.getMaxY(), collBounds.getMaxY());

			return new Rectangle((int) minx, (int) miny, (int) (maxx - minx), (int) (maxy - miny));
		}
		return imgBounds;
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (collider != null)
			collider.paint(g2d);
		super.paint(g2d);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == getCollider())
			fireChangeEvent();
	}
}

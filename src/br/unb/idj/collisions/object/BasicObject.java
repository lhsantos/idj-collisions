package br.unb.idj.collisions.object;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import br.unb.idj.collisions.util.Entity;
import br.unb.idj.collisions.util.Vector2;

public class BasicObject extends Entity {
	private static long _name_counter = 1;

	private String name;
	private Image texture = null;
	private Vector2 position = Vector2.zero();
	private Vector2 anchor = Vector2.zero();
	private float rotation = 0;
	private float speed = 0;
	private boolean visible = true;

	public BasicObject() {
		this("GameObject" + _name_counter);
		++_name_counter;
	}

	public BasicObject(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (((this.name == null) && (name != null)) || (!this.name.equals(name))) {
			this.name = name;
			fireChangeEvent();
		}
	}

	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		setTexture(texture, false);
	}

	public void setTexture(Image texture, boolean keepAnchor) {
		if (this.texture != texture) {
			this.texture = texture;
			fireChangeEvent();
			if (!keepAnchor)
				setAnchor(null);
		}
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		if (position == null)
			position = Vector2.zero();
		if (!this.position.equals(position)) {
			this.position = position;
			fireChangeEvent();
		}
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		if (Float.compare(this.rotation, rotation) != 0) {
			this.rotation = rotation;
			fireChangeEvent();
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		if (Float.compare(this.speed, speed) != 0) {
			this.speed = speed;
			fireChangeEvent();
		}
	}

	public Vector2 getAnchor() {
		return anchor;
	}

	public void setAnchor(Vector2 anchor) {
		if (anchor == null) {
			Rectangle bounds = getBounds();
			anchor = new Vector2(bounds.width / 2, bounds.height / 2);
		}
		if (!this.anchor.equals(anchor)) {
			this.anchor = anchor;
			fireChangeEvent();
		}
	}

	public Rectangle getBounds() {
		Vector2 pos = getPosition();
		Rectangle bounds = new Rectangle((int) pos.x, (int) pos.y);
		if (texture != null) {
			bounds.width = texture.getWidth(null);
			bounds.height = texture.getHeight(null);
		}
		return bounds;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		if (this.visible != visible) {
			this.visible = visible;
			fireChangeEvent();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (!isVisible())
			return;

		Image texture = getTexture();
		if (texture != null) {
			AffineTransform oldTransform = g2d.getTransform();
			Composite oldComposite = g2d.getComposite();

			Vector2 pos = getPosition();
			Vector2 a = getAnchor();
			g2d.translate(pos.x - a.x, pos.y - a.y);
			g2d.rotate(-getRotation(), a.x, a.y);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			g2d.drawImage(texture, 0, 0, texture.getWidth(null), texture.getHeight(null), null);

			g2d.setComposite(oldComposite);
			g2d.setTransform(oldTransform);
		}
	}
}

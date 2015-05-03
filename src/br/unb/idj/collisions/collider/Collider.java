package br.unb.idj.collisions.collider;

import java.awt.Color;
import java.awt.Rectangle;

import br.unb.idj.collisions.drawing.Colors;
import br.unb.idj.collisions.object.CollidingObject;
import br.unb.idj.collisions.util.Entity;
import br.unb.idj.collisions.util.Vector2;

/**
 * Base class for all colliders.
 * 
 * @author Luciano Santos
 */
public abstract class Collider extends Entity {
	private CollidingObject owner = null;
	private Color fillColor = Colors.COLLIDER_FILL;
	private Color borderColor = Colors.COLLIDER_BORDER;
	private boolean drawShadow = false;

	public abstract boolean hitsPoint(Vector2 p);

	public abstract Rectangle getBounds();

	public abstract Collider clone();

	public CollidingObject getOwner() {
		return owner;
	}

	public void setOwner(CollidingObject owner) {
		if (((this.owner == null) && (owner != null)) || (!this.owner.equals(owner))) {
			this.owner = owner;
			fireChangeEvent();
		}
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		if (!fillColor.equals(this.fillColor)) {
			this.fillColor = fillColor;
			fireChangeEvent();
		}
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		if (!borderColor.equals(this.borderColor)) {
			this.borderColor = borderColor;
			fireChangeEvent();
		}
	}

	public boolean isDrawShadow() {
		return drawShadow;
	}

	public void setDrawShadow(boolean drawShadow) {
		if (this.drawShadow != drawShadow) {
			this.drawShadow = drawShadow;
			fireChangeEvent();
		}
	}
}

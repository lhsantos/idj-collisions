package br.unb.idj.collisions.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.unb.idj.collisions.drawing.Colors;
import br.unb.idj.collisions.drawing.Renderable;
import br.unb.idj.collisions.object.InteractiveObject;
import br.unb.idj.collisions.util.Vector2;

@SuppressWarnings("serial")
public class ObjectViewPanel extends JPanel implements ChangeListener, MouseListener, MouseMotionListener, Renderable {
	public static final String SEL_OBJ_PROPERTY = "selectedObject";

	private static final Color COLOR_BG = Color.white;

	private List<InteractiveObject> objects = new ArrayList<>();
	private Map<InteractiveObject, InteractiveObject> backMap = new HashMap<>();
	InteractiveObject hovered = null;
	InteractiveObject selected = null;
	Point startMousePos = null;
	Vector2 startObjPos = null;

	public ObjectViewPanel() {
		initialize();
	}

	private void initialize() {
		this.setBackground(COLOR_BG);
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void add(InteractiveObject obj) {
		if (!objects.contains(obj)) {
			objects.add(obj);
			obj.addChangeListener(this);
			repaint();
		}
	}

	public void remove(InteractiveObject obj) {
		if (objects.remove(obj)) {
			obj.removeChangeListener(this);
			repaint();
		}
	}

	public InteractiveObject getSelectedObject() {
		return selected;
	}

	public void setSelectedObject(InteractiveObject obj) {
		InteractiveObject old = selected;
		if (obj != null) {
			selected = obj;
			selected.setHighlighted(true);
		}
		else if (selected != null) {
			selected.setHighlighted(false);
			selected = null;
		}

		if (selected != old) {
			firePropertyChange(SEL_OBJ_PROPERTY, old, selected);
		}
	}

	public void stepForward() {
		backMap.clear();
		for (InteractiveObject obj : objects) {
			if (obj.isSimulate()) {
				InteractiveObject clone = obj.clone();
				clone.getCollider().setFillColor(Colors.SHADOW_FILL);
				clone.getCollider().setBorderColor(Colors.SHADOW_BORDER);
				clone.setShowArrow(false);
				backMap.put(obj, clone);
				Vector2 p = new Vector2(obj.getPosition().x, obj.getPosition().y);
				p.x += obj.getSpeed() * Math.cos(obj.getRotation());
				p.y -= obj.getSpeed() * Math.sin(obj.getRotation());
				obj.setPosition(p);
			}
		}
		repaint();
	}

	public void stepBack() {
		for (Entry<InteractiveObject, InteractiveObject> entry : backMap.entrySet()) {
			entry.getKey().setPosition(entry.getValue().getPosition());
		}
		backMap.clear();
		repaint();
	}

	@Override
	public void paint(java.awt.Graphics g) {
		super.paint(g);
		paint((Graphics2D) g);
	}

	@Override
	public void paint(Graphics2D g2d) {
		RenderingHints oldHints = g2d.getRenderingHints();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Stroke oldStroke = g2d.getStroke();
		Color oldColor = g2d.getColor();
		g2d.setStroke(new BasicStroke(1.5f));
		g2d.setColor(Colors.SHADOW_FILL);
		for (Entry<InteractiveObject, InteractiveObject> entry : backMap.entrySet()) {
			g2d.drawLine(
					(int) entry.getValue().getPosition().x, (int) entry.getValue().getPosition().y,
					(int) entry.getKey().getPosition().x, (int) entry.getKey().getPosition().y);
			entry.getValue().paint(g2d);
		}
		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);

		for (Renderable obj : objects) {
			obj.paint(g2d);
		}

		g2d.setRenderingHints(oldHints);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (hovered != null) {
			startMousePos = e.getPoint();
			startObjPos = hovered.getPosition();
		}
		setSelectedObject(hovered);
		backMap.clear();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selected != null) {
			Vector2 ds = new Vector2(e.getPoint().x - startMousePos.x, e.getPoint().y - startMousePos.y);
			selected.setPosition(startObjPos.add(ds));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		hovered = null;
		for (InteractiveObject obj : objects)
			if (obj.isVisible()) {
				if (obj.hits(e.getPoint())) {
					obj.setHighlighted(true);
					hovered = obj;
				}
				else if (obj != selected)
					obj.setHighlighted(false);
			}
	}
}

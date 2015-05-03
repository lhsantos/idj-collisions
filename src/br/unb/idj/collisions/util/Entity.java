package br.unb.idj.collisions.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.unb.idj.collisions.drawing.Renderable;
import br.unb.idj.collisions.object.Changeable;

public abstract class Entity implements Changeable, Renderable {
	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

	public void addChangeListener(ChangeListener l) {
		if (l != null)
			listeners.add(l);
	}

	public void removeChangeListener(ChangeListener l) {
		if (l != null)
			listeners.remove(l);
	}

	protected void fireChangeEvent() {
		ChangeEvent e = new ChangeEvent(this);
		for (ChangeListener l : listeners) {
			l.stateChanged(e);
		}
	}
}

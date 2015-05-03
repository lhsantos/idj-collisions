package br.unb.idj.collisions.object;

import javax.swing.event.ChangeListener;

public interface Changeable {
	void addChangeListener(ChangeListener l);

	void removeChangeListener(ChangeListener l);
}

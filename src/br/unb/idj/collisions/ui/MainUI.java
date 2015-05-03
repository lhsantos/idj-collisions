package br.unb.idj.collisions.ui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import br.unb.idj.collisions.object.InteractiveObject;

@SuppressWarnings("serial")
public class MainUI extends JPanel implements PropertyChangeListener {

	public MainUI() {
		initialize();
	}

	private void initialize() {
		this.controlPanel = new ObjectControlPanel(this);

		this.viewPanel = new ObjectViewPanel();
		this.viewPanel.addPropertyChangeListener(this);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		splitPane.setLeftComponent(this.controlPanel);
		splitPane.setRightComponent(this.viewPanel);
		splitPane.setDividerLocation(200);
		this.setLayout(new BorderLayout());
		this.add(splitPane, BorderLayout.CENTER);
	}

	public void newObject(InteractiveObject obj) {
		viewPanel.add(obj);
		viewPanel.setSelectedObject(obj);
		controlPanel.setObject(obj);
	}

	public ObjectViewPanel viewPanel = null;
	public ObjectControlPanel controlPanel = null;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (viewPanel == evt.getSource()) {
			if (evt.getPropertyName().equals(ObjectViewPanel.SEL_OBJ_PROPERTY))
				controlPanel.setObject(viewPanel.getSelectedObject());
		}
	}
}

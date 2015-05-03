package br.unb.idj.collisions.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.unb.idj.collisions.collider.AABBCollider;
import br.unb.idj.collisions.collider.OBBCollider;
import br.unb.idj.collisions.collider.SphereCollider;
import br.unb.idj.collisions.object.InteractiveObject;
import br.unb.idj.collisions.util.Common;

@SuppressWarnings("serial")
public class ObjectControlPanel extends JPanel implements ActionListener, ChangeListener {
	private enum ColliderTypes {
		Sphere, OBB, AABB
	};

	private static final double RAD2DEG = 180.0 / Math.PI;
	private static final double DEG2RAD = Math.PI / 180.0;

	private MainUI mainUI;
	private InteractiveObject obj;

	public ObjectControlPanel(MainUI mainUI) {
		this.mainUI = mainUI;

		initialize();
	}

	public void setObject(InteractiveObject obj) {
		this.obj = obj;
		update();
	}

	private void update() {
		boolean en = obj != null;
		for (Component c : pnProps.getComponents()) {
			c.setEnabled(en);
		}

		String name = (en && (obj.getName() != null)) ? obj.getName() : "";
		brdName.setTitle(name);
		if (en) {
			spnRotation.setValue(RAD2DEG * obj.getRotation());
			spnSpeed.setValue(obj.getSpeed());
			chkProject.setSelected(obj.isDrawShadow());
			chkSimulate.setSelected(obj.isSimulate());
		}

		revalidate();
		repaint();
	}

	private void initialize() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;

		this.cboType = new JComboBox<>(new ColliderTypes[] { ColliderTypes.Sphere, ColliderTypes.OBB,
				ColliderTypes.AABB });
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		this.add(this.cboType, gbc);

		this.btnCreate = new JButton("Create");
		this.btnCreate.addActionListener(this);
		gbc.gridy++;
		this.add(this.btnCreate, gbc);

		this.pnProps = new JPanel();
		this.pnProps.setLayout(new GridBagLayout());
		this.brdName = new TitledBorder(new LineBorder(Color.black), "");
		this.pnProps.setBorder(brdName);

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.WEST;
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.insets = new Insets(0, 5, 5, 0);

		this.lblRotation = new JLabel("Rotation");
		gbc2.fill = GridBagConstraints.NONE;
		gbc2.weightx = 0;
		gbc2.weighty = 0;
		gbc2.gridy++;
		this.pnProps.add(this.lblRotation, gbc2);

		this.spnRotation = new JSpinner(new SpinnerNumberModel(0, 0, 360, 5));
		this.spnRotation.addChangeListener(this);
		gbc2.anchor = GridBagConstraints.EAST;
		gbc2.gridx = 1;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.weightx = 1;
		gbc2.weighty = 0;
		this.pnProps.add(this.spnRotation, gbc2);

		this.lblSpeed = new JLabel("Speed");
		gbc2.gridx = 0;
		gbc2.anchor = GridBagConstraints.WEST;
		gbc2.fill = GridBagConstraints.NONE;
		gbc2.weightx = 0;
		gbc2.weighty = 0;
		gbc2.gridy++;
		this.pnProps.add(this.lblSpeed, gbc2);

		this.spnSpeed = new JSpinner(new SpinnerNumberModel(0, 0, Float.MAX_VALUE, 5));
		this.spnSpeed.addChangeListener(this);
		gbc2.anchor = GridBagConstraints.EAST;
		gbc2.gridx = 1;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.weightx = 1;
		gbc2.weighty = 0;
		this.pnProps.add(this.spnSpeed, gbc2);

		this.chkProject = new JCheckBox("Project");
		this.chkProject.addActionListener(this);
		gbc2.gridx = 0;
		gbc2.anchor = GridBagConstraints.CENTER;
		gbc2.fill = GridBagConstraints.NONE;
		gbc2.weightx = 0;
		gbc2.weighty = 0;
		gbc2.gridy++;
		this.pnProps.add(this.chkProject, gbc2);

		this.chkSimulate = new JCheckBox("Simulate");
		this.chkSimulate.addActionListener(this);
		gbc2.gridx = 1;
		this.pnProps.add(this.chkSimulate, gbc2);

		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.gridy++;
		this.add(this.pnProps, gbc);

		this.btnBack = new JButton("Step Back");
		this.btnBack.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridy++;
		this.add(this.btnBack, gbc);

		this.btnForward = new JButton("Step Forward");
		this.btnForward.addActionListener(this);
		gbc.gridx = 1;
		this.add(this.btnForward, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridy++;
		this.add(new JPanel(), gbc);

		update();
	}

	private JComboBox<ColliderTypes> cboType = null;
	private JButton btnCreate = null;
	private JPanel pnProps = null;
	private TitledBorder brdName = null;
	private JLabel lblRotation = null;
	private JSpinner spnRotation = null;
	private JLabel lblSpeed = null;
	private JSpinner spnSpeed = null;
	private JCheckBox chkProject = null;
	private JCheckBox chkSimulate = null;
	private JButton btnBack = null;
	private JButton btnForward = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCreate) {
			try {
				Image img = Common.loadTexture("rocket.png");
				InteractiveObject obj = new InteractiveObject();
				obj.setSpeed(200);
				obj.setTexture(img);
				switch ((ColliderTypes) cboType.getSelectedItem()) {
				case Sphere:
					new SphereCollider(obj);
					break;

				case OBB:
					new OBBCollider(obj);
					break;

				case AABB:
					new AABBCollider(obj);
					break;
				}
				mainUI.newObject(obj);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		else if (e.getSource() == chkProject) {
			obj.setDrawShadow(chkProject.isSelected());
		}
		else if (e.getSource() == chkSimulate) {
			obj.setSimulate(chkSimulate.isSelected());
		}
		else if (e.getSource() == btnForward) {
			mainUI.viewPanel.stepForward();
		}
		else if (e.getSource() == btnBack) {
			mainUI.viewPanel.stepBack();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == spnRotation) {
			double angle = DEG2RAD * ((Number) spnRotation.getValue()).doubleValue();
			obj.setRotation((float) angle);
		}
		else if (e.getSource() == spnSpeed) {
			obj.setSpeed(((Number) spnSpeed.getValue()).floatValue());
		}
	}
}

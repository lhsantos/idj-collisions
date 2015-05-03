package br.unb.idj.collisions.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import br.unb.idj.collisions.ui.MainUI;

@SuppressWarnings("serial")
public class Application extends JFrame {
	private static final Dimension INITIAL_SIZE = new Dimension(1024, 768);

	public Application() {
		super("idj-collisions-frame");

		initialize();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void initialize() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(INITIAL_SIZE);
		this.setLocation(screen.width / 2 - INITIAL_SIZE.width / 2, screen.height / 2 - INITIAL_SIZE.height / 2);
		this.setResizable(false);

		this.setTitle("IDJ - Collisions");

		this.setContentPane(new MainUI());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Application();
			}
		});
	}
}

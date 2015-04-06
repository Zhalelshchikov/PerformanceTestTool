package com.harris.netboss.dxtPerformanceTestingTool;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.harris.netboss.dxtPerformanceTestingTool.About;

import java.net.URL;

public class About extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new form About
	 */
	public About() {
		initComponents();
		setIconImage(getImage());
	}

	protected static Image getImage() {

		URL imgURL = About.class.getResource(ICON);
		if (imgURL != null) {
			return new ImageIcon(imgURL).getImage();
		} else {
			return null;
		}
	}

	private void initComponents() {

		about = new javax.swing.JLabel();

		setTitle("About");
		setAlwaysOnTop(true);
		setBounds(new java.awt.Rectangle(300, 200, 0, 0));
		setResizable(false);

		about.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		about.setText("About");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(about,
								javax.swing.GroupLayout.PREFERRED_SIZE, 169,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(about,
								javax.swing.GroupLayout.PREFERRED_SIZE, 114,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(About.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(About.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(About.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(About.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new About().setVisible(true);
			}
		});
	}

	static final String ICON = "res\\AirbusDSDXT.gif";
	private javax.swing.JLabel about;
}

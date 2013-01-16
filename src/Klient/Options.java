package Klient;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Options extends JFrame {
	//This was planned, but is unfinished. An empty but fully functional Options menu.

  private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About frame = new About();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void windowCloser(){
		this.setVisible(false);
	}

	public Options() {
		setMinimumSize(new Dimension(400, 400));
		setMaximumSize(new Dimension(400, 400));
		setSize(new Dimension(400, 400));
		setPreferredSize(new Dimension(400, 400));
		setTitle("Options");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnNothing = new JButton("Finns inget h\u00E4r...");
		GridBagConstraints gbc_btnNothing = new GridBagConstraints();
		gbc_btnNothing.insets = new Insets(0, 0, 5, 5);
		gbc_btnNothing.gridx = 1;
		gbc_btnNothing.gridy = 1;
		panel.add(btnNothing, gbc_btnNothing);
		
		btnNothing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowCloser();
			}
		});
	}
}


package Klient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GUIConnectMenu extends JFrame {

	private JPanel contentPane;
	private JTextField connectEnterIP;
	private JTextField connectEnterPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIConnectMenu frame = new GUIConnectMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIConnectMenu() {
		setTitle("Connect");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 225, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {19, 0, 0, 27, 30, 45, 26, 0, 5};
		gbl_contentPane.rowHeights = new int[] {40, 40, 35, 7};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblEnterServerIp = new JLabel("Server IP:");
		GridBagConstraints gbc_lblEnterServerIp = new GridBagConstraints();
		gbc_lblEnterServerIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterServerIp.gridx = 1;
		gbc_lblEnterServerIp.gridy = 0;
		contentPane.add(lblEnterServerIp, gbc_lblEnterServerIp);
		
		connectEnterIP = new JTextField();
		GridBagConstraints gbc_connectEnterIP = new GridBagConstraints();
		gbc_connectEnterIP.gridwidth = 4;
		gbc_connectEnterIP.insets = new Insets(0, 0, 5, 5);
		gbc_connectEnterIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectEnterIP.gridx = 2;
		gbc_connectEnterIP.gridy = 0;
		contentPane.add(connectEnterIP, gbc_connectEnterIP);
		connectEnterIP.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.gridx = 1;
		gbc_lblPort.gridy = 1;
		contentPane.add(lblPort, gbc_lblPort);
		
		connectEnterPort = new JTextField();
		connectEnterPort.setText("54602");
		GridBagConstraints gbc_connectEnterPort = new GridBagConstraints();
		gbc_connectEnterPort.insets = new Insets(0, 0, 5, 5);
		gbc_connectEnterPort.gridwidth = 3;
		gbc_connectEnterPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectEnterPort.gridx = 2;
		gbc_connectEnterPort.gridy = 1;
		contentPane.add(connectEnterPort, gbc_connectEnterPort);
		connectEnterPort.setColumns(10);
		
		JButton connectMenuConnectBtn = new JButton("Connect");
		GridBagConstraints gbc_connectMenuConnectBtn = new GridBagConstraints();
		gbc_connectMenuConnectBtn.gridwidth = 8;
		gbc_connectMenuConnectBtn.gridx = 0;
		gbc_connectMenuConnectBtn.gridy = 2;
		contentPane.add(connectMenuConnectBtn, gbc_connectMenuConnectBtn);
		
		connectMenuConnectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int tempPort = Integer.parseInt(connectEnterPort.getText());
				Main.klient.connect(connectEnterIP.getText(), tempPort);
			}
		});
	}

}

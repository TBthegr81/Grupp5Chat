package Klient;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class GUIConnectMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField connectEnterIP;
	private JTextField connectEnterPort;
	private JCheckBox bookmarkCheckbox;
	private JComboBox comboBoxBookmarkSelect;
	private JLabel lblBookmark;
	private String tempIP;
	private int tempPort;
	BookmarkInput bookmarkMenu = new BookmarkInput();


	/**
	 * Launch the application.
	 */
	public void windowCloser(){
		this.setVisible(false);
	}

	public void BookmarkSetter(){
		tempIP = connectEnterIP.getText();
		tempPort = Integer.parseInt(connectEnterPort.getText());
	}
	public String BookmarkGetIp(){
		return tempIP;
	}
	public int BookmarkGetPort(){
		return tempPort;
	}


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
		setBounds(100, 100, 275, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {5, 15, 50, 50, 20};
		gbl_contentPane.rowHeights = new int[] {20, 20, 20, 20, 20, 20 ,20};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		lblBookmark = new JLabel("Bookmark:");
		GridBagConstraints gbc_lblBookmark = new GridBagConstraints();
		gbc_lblBookmark.insets = new Insets(0, 0, 5, 5);
		gbc_lblBookmark.anchor = GridBagConstraints.EAST;
		gbc_lblBookmark.gridx = 2;
		gbc_lblBookmark.gridy = 1;
		contentPane.add(lblBookmark, gbc_lblBookmark);

		comboBoxBookmarkSelect = new JComboBox<>();
		
		//Denna uppdaterar listan
		comboBoxBookmarkSelect.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				
					if (Main.mainData.nickname.isEmpty() != true){
						
						//T�mmer f�r att undvika dubletter
						comboBoxBookmarkSelect.removeAllItems();
						
						for (int i = 0; i < Main.mainData.nickname.size(); i++){
							comboBoxBookmarkSelect.addItem(Main.mainData.nickname.get(i));
						}

						bookmarkCheckbox.setSelected(false);
					}
				}
			});
		//Denna uppdaterar �vriga f�lt med korrekt data.
		comboBoxBookmarkSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxBookmarkSelect.getSelectedIndex() != -1){
					int sel = comboBoxBookmarkSelect.getSelectedIndex();
					
					connectEnterIP.setText(Main.mainData.ipAdress.get(sel));
					connectEnterPort.setText(Main.mainData.port.get(sel).toString());
					
					
					
				}	
			}
		});
		
			
		comboBoxBookmarkSelect.setToolTipText("Select a server:");
		GridBagConstraints gbc_comboBoxBookmarkSelect = new GridBagConstraints();
		gbc_comboBoxBookmarkSelect.gridwidth = 3;
		gbc_comboBoxBookmarkSelect.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxBookmarkSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxBookmarkSelect.gridx = 3;
		gbc_comboBoxBookmarkSelect.gridy = 1;
		contentPane.add(comboBoxBookmarkSelect, gbc_comboBoxBookmarkSelect);


		

		JLabel lblEnterServerIp = new JLabel("Server IP:");
		GridBagConstraints gbc_lblEnterServerIp = new GridBagConstraints();
		gbc_lblEnterServerIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterServerIp.gridx = 2;
		gbc_lblEnterServerIp.gridy = 3;
		contentPane.add(lblEnterServerIp, gbc_lblEnterServerIp);

		connectEnterIP = new JTextField();
		GridBagConstraints gbc_connectEnterIP = new GridBagConstraints();
		gbc_connectEnterIP.gridwidth = 2;
		gbc_connectEnterIP.insets = new Insets(0, 0, 5, 5);
		gbc_connectEnterIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectEnterIP.gridx = 3;
		gbc_connectEnterIP.gridy = 3;
		contentPane.add(connectEnterIP, gbc_connectEnterIP);
		connectEnterIP.setColumns(10);

		JLabel lblPort = new JLabel("Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.gridx = 2;
		gbc_lblPort.gridy = 4;
		contentPane.add(lblPort, gbc_lblPort);

		connectEnterPort = new JTextField();
		connectEnterPort.setText("54602");
		GridBagConstraints gbc_connectEnterPort = new GridBagConstraints();
		gbc_connectEnterPort.insets = new Insets(0, 0, 5, 5);
		gbc_connectEnterPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectEnterPort.gridx = 3;
		gbc_connectEnterPort.gridy = 4;
		contentPane.add(connectEnterPort, gbc_connectEnterPort);
		connectEnterPort.setColumns(10);

		bookmarkCheckbox = new JCheckBox("Save this server");
		bookmarkCheckbox.setSelected(true);
		GridBagConstraints gbc_bookmarkCheckbox = new GridBagConstraints();
		gbc_bookmarkCheckbox.gridwidth = 2;
		gbc_bookmarkCheckbox.insets = new Insets(0, 0, 5, 5);
		gbc_bookmarkCheckbox.gridx = 3;
		gbc_bookmarkCheckbox.gridy = 5;
		contentPane.add(bookmarkCheckbox, gbc_bookmarkCheckbox);

		JButton connectMenuConnectBtn = new JButton("Connect");
		GridBagConstraints gbc_connectMenuConnectBtn = new GridBagConstraints();
		gbc_connectMenuConnectBtn.gridwidth = 9;
		gbc_connectMenuConnectBtn.gridx = 0;
		gbc_connectMenuConnectBtn.gridy = 6;
		contentPane.add(connectMenuConnectBtn, gbc_connectMenuConnectBtn);

		connectMenuConnectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				BookmarkSetter();
				//Main.klient.startRunning(tempIP, tempPort);
				KlientThread tre = new KlientThread();
				Thread t = new Thread(tre);
				t.start();
				
				if (bookmarkCheckbox.isSelected() == true){

					bookmarkMenu.setVisible(true);
				}

				//Main.klient.connect(tempIP, tempPort, Main.mainData.userName);
				windowCloser();

			}
		});

		this.revalidate();
	}

}

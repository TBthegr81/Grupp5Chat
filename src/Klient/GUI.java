package Klient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class GUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	public static String newline = System.getProperty("line.separator");
	public static String myUserName = "Debugger";
	
	public static JTextArea conversationWindow;
	public static JTextField messageInputField;
	static JTextArea memberListTextArea;
	GUIConnectMenu connectMenu = new GUIConnectMenu();
	

	/**
	 * @param args
	 */
	public GUI(){
		// TODO Auto-generated method stub

		
		final JFrame clientWindow = new JFrame ("Group 5 chat client");
		clientWindow.setTitle("iClient");
		clientWindow.getContentPane().setSize(new Dimension(700, 350));
		clientWindow.setMinimumSize(new Dimension(400, 350));
		clientWindow.setSize(new Dimension(700, 550));
		clientWindow.getContentPane().setMinimumSize(new Dimension(400, 350));
		
		clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientWindow.setSize(700, 550);
		clientWindow.setLocationRelativeTo(null);
		clientWindow.setVisible(true);

		Container contentClientWindow = clientWindow.getContentPane();
		contentClientWindow.setBackground(Color.LIGHT_GRAY);
		
		
		//Menu for chat
		JMenu chatMenu = new JMenu("iClient");
		chatMenu.setMnemonic(KeyEvent.VK_M);
		
		//Menu bar 
		JMenuBar chatMenuBar = new JMenuBar();
		chatMenuBar.add(chatMenu);

		//Set chatMenuBar to the frame
		clientWindow.setJMenuBar(chatMenuBar);
		clientWindow.revalidate();
 
		//Menu
		JMenuItem connectMenuItem = new JMenuItem("Connect", KeyEvent.VK_C);
		chatMenu.add(connectMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_S);
		chatMenu.add(exitMenuItem);
		
		//Meny två
		JMenu menuTools = new JMenu("Tools");
		chatMenuBar.add(menuTools);
		
		
		JMenuItem optionsMenuItem = new JMenuItem("Options");
		menuTools.add(optionsMenuItem);
		optionsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Options optionsWindow = new Options();
				optionsWindow.setVisible(true);
			}
		});
		
		JMenuItem aboutMenuItem = new JMenuItem("About");
		menuTools.add(aboutMenuItem);
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About aboutWindow = new About();
				aboutWindow.setVisible(true);
			}
		});
		
		//Actions for the menu items
		connectMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectMenu.setVisible(true);
			}
		});
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{155, 398, 101, 0};
		gridBagLayout.rowHeights = new int[]{40, 399, 83, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		clientWindow.getContentPane().setLayout(gridBagLayout);
		
	

		//Text field for message
		messageInputField = new JTextField();
		messageInputField.setFont(new Font("Monospaced", Font.PLAIN, 11));
		messageInputField.setMinimumSize(new Dimension(300, 25));
		messageInputField.setPreferredSize(new Dimension(300, 25));
		messageInputField.setSize(new Dimension(300, 25));
		messageInputField.setColumns(10);
		
		messageInputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.klient.checkMessage(messageInputField.getText());
				messageInputField.setText("");
			}
		});
		
		//HÃ¤r visas rummets namn.
		JFormattedTextField roomNameTextArea = new JFormattedTextField();
		roomNameTextArea.setBackground(Color.LIGHT_GRAY);
		roomNameTextArea.setHorizontalAlignment(SwingConstants.CENTER);
		roomNameTextArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		roomNameTextArea.setEditable(false);
		roomNameTextArea.setText("<no connection>");
		GridBagConstraints gbc_roomNameTextArea = new GridBagConstraints();
		gbc_roomNameTextArea.insets = new Insets(0, 0, 5, 5);
		gbc_roomNameTextArea.fill = GridBagConstraints.BOTH;
		gbc_roomNameTextArea.gridx = 0;
		gbc_roomNameTextArea.gridy = 0;
		clientWindow.getContentPane().add(roomNameTextArea, gbc_roomNameTextArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(300, 200));
		scrollPane.setMinimumSize(new Dimension(300, 200));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
				//Conversation field where conversation will show
				conversationWindow = new JTextArea();
				conversationWindow.setEditable(false);
				conversationWindow.setMaximumSize(new Dimension(300, 2147483647));
				scrollPane.setViewportView(conversationWindow);
				conversationWindow.setMinimumSize(new Dimension(300, 0));
				conversationWindow.setColumns(10);
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.gridheight = 2;
				gbc_scrollPane.gridwidth = 2;
				gbc_scrollPane.anchor = GridBagConstraints.EAST;
				gbc_scrollPane.fill = GridBagConstraints.BOTH;
				gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPane.gridx = 1;
				gbc_scrollPane.gridy = 0;
				clientWindow.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JTextArea memberListTextArea = new JTextArea();
		memberListTextArea.setBackground(Color.LIGHT_GRAY);
		memberListTextArea.setEditable(false);
		GridBagConstraints gbc_memberListTextArea = new GridBagConstraints();
		gbc_memberListTextArea.insets = new Insets(0, 0, 5, 5);
		gbc_memberListTextArea.fill = GridBagConstraints.BOTH;
		gbc_memberListTextArea.gridx = 0;
		gbc_memberListTextArea.gridy = 1;
		clientWindow.getContentPane().add(memberListTextArea, gbc_memberListTextArea);
		messageInputField.setEditable(false);
		GridBagConstraints gbc_messageInputField = new GridBagConstraints();
		gbc_messageInputField.fill = GridBagConstraints.BOTH;
		gbc_messageInputField.insets = new Insets(0, 0, 0, 5);
		gbc_messageInputField.gridx = 1;
		gbc_messageInputField.gridy = 2;
		clientWindow.getContentPane().add(messageInputField, gbc_messageInputField);
		
				
				//submit button to send text
				JButton btnSendMessage = new JButton("Send message");
				btnSendMessage.setSize(new Dimension(80, 20));
				btnSendMessage.setBackground(Color.CYAN);
				
				btnSendMessage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Main.klient.checkMessage(messageInputField.getText());
						//Clears input field.
						messageInputField.setText("");
					}
				});
				GridBagConstraints gbc_btnSendMessage = new GridBagConstraints();
				gbc_btnSendMessage.anchor = GridBagConstraints.NORTHWEST;
				gbc_btnSendMessage.gridx = 2;
				gbc_btnSendMessage.gridy = 2;
				gbc_btnSendMessage.fill = GridBagConstraints.BOTH;
				clientWindow.getContentPane().add(btnSendMessage, gbc_btnSendMessage);

		
		clientWindow.revalidate();
		
	}
	public void showReceivedMessage(String message, String user){
		conversationWindow.append(newline + user + ": " + message);
	}
	public void serverMessage(String message){
		conversationWindow.append(newline + message);
	}
	public void setMemberList(ArrayList<String> message){
		for (int i = 0;i<message.size();i++){
			memberListTextArea.append(message.get(i) + newline);
		}		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

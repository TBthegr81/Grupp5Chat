package Klient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;


public class GUI extends JFrame implements ActionListener{
	//This is the main window. Contains 
	
	private static final long serialVersionUID = 1L;
	public static String newline = System.getProperty("line.separator");
	public static JTextArea conversationWindow;
	public static JTextField messageInputField;
	public static JTextArea memberField;
	static ArrayList<String> userList = new ArrayList<String>();;
	GUIConnectMenu connectMenu = new GUIConnectMenu();
	

	/**
	 * @param args
	 */
	public GUI(){
		
		//Main window
		final JFrame clientWindow = new JFrame();
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
		
		//Menubar 
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
		
		//Menu two
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
		
		//Main frame layouts
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
		messageInputField.setEditable(false);
		
		GridBagConstraints gbc_messageInputField = new GridBagConstraints();
		gbc_messageInputField.fill = GridBagConstraints.BOTH;
		gbc_messageInputField.insets = new Insets(0, 0, 0, 5);
		gbc_messageInputField.gridx = 1;
		gbc_messageInputField.gridy = 2;
		clientWindow.getContentPane().add(messageInputField, gbc_messageInputField);
		
		//Sends message when pressing Enter when input field is focused, for checking by Klient.java, then clears input field
		messageInputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.klient.checkMessage(messageInputField.getText());
				messageInputField.setText("");
			}
		});
		
		//Här visas rummets namn.
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
		
				//Här visas användare
		memberField = new JTextArea();
		memberField.setText("Users online:\r\n");
		memberField.setBackground(Color.LIGHT_GRAY);
		memberField.setEditable(false);
		GridBagConstraints gbc_memberListTextArea = new GridBagConstraints();
		gbc_memberListTextArea.insets = new Insets(0, 0, 5, 5);
		gbc_memberListTextArea.fill = GridBagConstraints.BOTH;
		gbc_memberListTextArea.gridx = 0;
		gbc_memberListTextArea.gridy = 1;
		clientWindow.getContentPane().add(memberField, gbc_memberListTextArea);
		
				//This button sends a message. Identical to the action at line 125, but on a button.
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

		//Rechecks positions.
		clientWindow.revalidate();
		
	}
	//Shows messages sent by others and you, after server has sent them to every client connected.
	public void showReceivedMessage(String message, String user){
		conversationWindow.append(newline + user + ": " + message);
		//After printing message, checks memberlist so that no user gets left out in the memberlist. 
		for (String s : userList){
			if (s == user){
				userList.add(user);
				setMemberList();
			}
		}
	}
	//System messages from server
	public void serverMessage(String message){
		conversationWindow.append(newline + message);
	}
	//Function for updating the list of connected users.
	public static void setMemberList(){
			//Only do if there are any users online. (Needed for debug purposes)
			if (userList.isEmpty() == false){
			System.out.println("There are " + userList.size() + " users online.");
			//For safety, resets the text before listing users each time function is called.
			memberField.setText("Users online:" + newline);
			
			for (int i = 0; i<userList.size();i++){
				System.out.println(userList.get(i));
				memberField.append(userList.get(i) + newline);
			}
			}
			else {
				//Debug message. :)
				System.out.println("There are no users online. Including you. This shouldn't happen, check server connections.");
			}
		}	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

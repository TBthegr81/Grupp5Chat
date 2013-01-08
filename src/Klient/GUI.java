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
	GUIConnectMenu connectMenu = new GUIConnectMenu();

	/**
	 * @param args
	 */
	public GUI(){
		// TODO Auto-generated method stub

		
		final JFrame clientWindow = new JFrame ("Group 5 chat client");
		clientWindow.getContentPane().setSize(new Dimension(400, 350));
		clientWindow.setMinimumSize(new Dimension(400, 350));
		clientWindow.setSize(new Dimension(400, 350));
		clientWindow.getContentPane().setMinimumSize(new Dimension(400, 350));
		
		clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientWindow.setSize(700, 750);
		clientWindow.setLocationRelativeTo(null);
		clientWindow.setVisible(true);

		Container contentClientWindow = clientWindow.getContentPane();
		contentClientWindow.setBackground(Color.LIGHT_GRAY);
		
	

		//Text field for message
		messageInputField = new JTextField();
		messageInputField.setMinimumSize(new Dimension(300, 25));
		messageInputField.setPreferredSize(new Dimension(300, 25));
		messageInputField.setSize(new Dimension(300, 25));
		messageInputField.setColumns(10);
		
		messageInputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				conversationWindow.setText(conversationWindow.getText() + newline + myUserName + ": " + messageInputField.getText());
				Main.klient.checkMessage(messageInputField.getText());
				messageInputField.setText("");
			}
		});
		messageInputField.setEditable(false);

		
		//submit button to send text
		JButton btnSendMessage = new JButton("Send message");
		btnSendMessage.setSize(new Dimension(80, 20));
		btnSendMessage.setBackground(Color.CYAN);
		
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Main.klient.send(messageInputField.getText());
				conversationWindow.setText(conversationWindow.getText() + newline + myUserName + ": " + messageInputField.getText());

			
				Main.klient.checkMessage(messageInputField.getText());

				//Clears input field.
				messageInputField.setText("");
			}
		});
		
		
		//Menu for chat
		JMenu chatMenu = new JMenu("Menu");
		chatMenu.setMnemonic(KeyEvent.VK_M);
		
		//Menu bar 
		JMenuBar chatMenuBar = new JMenuBar();
		chatMenuBar.add(chatMenu);

		//Set chatMenuBar to the frame
		clientWindow.setJMenuBar(chatMenuBar);
		clientWindow.revalidate();
 
		//TODO Add functions to all items on menu bar
		
		// 3 items on the menu. New necessary items can be added or these three can be deleted.
		JMenuItem connectMenuItem = new JMenuItem("Connect", KeyEvent.VK_C);
		chatMenu.add(connectMenuItem);
		JMenuItem bookmarkMenuItem = new JMenuItem("Bookmark", KeyEvent.VK_B);
		chatMenu.add(bookmarkMenuItem);
		JMenuItem saveConversationMenuItem = new JMenuItem("Save conversation", KeyEvent.VK_S);
		chatMenu.add(saveConversationMenuItem);
		
		//Actions for the menu items
		connectMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				connectMenu.setVisible(true);
				
			}
		});
		
		//New label. 
		//TODO Make it able to change?!?
		
		JLabel lblChatroom = new JLabel("Chatroom # 1");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(300, 200));
		scrollPane.setMinimumSize(new Dimension(300, 200));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Layout for clientWindow with all the components added
		GroupLayout groupLayout = new GroupLayout(clientWindow.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(23)
					.addComponent(lblChatroom)
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(messageInputField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnSendMessage))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 552, GroupLayout.PREFERRED_SIZE))
					.addGap(15))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblChatroom)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 609, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(messageInputField, 0, 0, Short.MAX_VALUE)
						.addComponent(btnSendMessage, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
				//Conversation field where conversation will show
				conversationWindow = new JTextArea();
				conversationWindow.setMaximumSize(new Dimension(300, 2147483647));
				scrollPane.setViewportView(conversationWindow);
				conversationWindow.setMinimumSize(new Dimension(300, 0));
				conversationWindow.setColumns(10);
		clientWindow.getContentPane().setLayout(groupLayout);

		
		clientWindow.revalidate();
		
	}
	public void showReceivedMessage(String message, String user){
				
		conversationWindow.setText(conversationWindow.getText() + newline + user + ": " + message);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

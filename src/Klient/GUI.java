package Klient;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class GUI {
	private static JTextField conversationBoard;
	private static JTextField messageBoard;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Headframe, temporary name for JFrame, change later
		final JFrame clientWindow = new JFrame ("Group 5 chat client");
		clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientWindow.setSize(700, 750);
		clientWindow.setLocationRelativeTo(null);
		clientWindow.setVisible(true);

		Container contentClientWindow = clientWindow.getContentPane();
		contentClientWindow.setBackground(Color.DARK_GRAY);

		//Conversation field where conversation will show
		conversationBoard = new JTextField();
		conversationBoard.setColumns(10);

		//Text field for message
		messageBoard = new JTextField();
		messageBoard.setColumns(10);

		
		//TODO Add listener to btnSendMessage
		
		//submit button to send text
		JButton btnSendMessage = new JButton("Send message");
		btnSendMessage.setBackground(Color.CYAN);
		
		
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
		
		//New label. 
		//TODO Make it able to change?!?
		
		JLabel lblChatroom = new JLabel("Chatroom # 1");
		
		JScrollBar ConversationScrollBar = new JScrollBar();
		
		// Layout for clientWindow with all the components added
		GroupLayout groupLayout = new GroupLayout(clientWindow.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
				.addGap(204)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(btnSendMessage)
				.addComponent(messageBoard, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
									
						.addComponent(lblChatroom)
						.addGap(398))
						.addComponent(conversationBoard, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
							
					.addComponent(ConversationScrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
			.addGap(13)
					
					//Adding label for chat room
					.addComponent(lblChatroom)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							
							
							
							 //TODO Make scroll bar functional with conversation board
							
							//Adding scroll bar for conversation board
						.addComponent(ConversationScrollBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(conversationBoard, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
					.addGap(23)
		 
					
					//Adding message button
					.addComponent(btnSendMessage)
					.addPreferredGap(ComponentPlacement.RELATED)
					
					//Adding message board
					.addComponent(messageBoard, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		clientWindow.getContentPane().setLayout(groupLayout);

		

		
	}
}
package Klient;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class UserNameFailWindow extends JFrame {
	//This class creates a window for input of a new username, if the previous selection was not accepted by server.
	
	private static final long serialVersionUID = 1L;
	private JTextField userNewNameInput;

	private void windowCloser(){
		this.setVisible(false);
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookmarkInput frame = new BookmarkInput();
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
	public UserNameFailWindow() {
		setSize(new Dimension(300, 150));
		setResizable(false);
		setTitle("Choose a new User name");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {10, 260, 10};
		gridBagLayout.rowHeights = new int[]{10, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblEnterANickname = new JLabel("Your name was not accepted. Enter another name:");
		lblEnterANickname.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblEnterANickname = new GridBagConstraints();
		gbc_lblEnterANickname.insets = new Insets(0, 0, 5, 0);
		gbc_lblEnterANickname.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEnterANickname.gridx = 1;
		gbc_lblEnterANickname.gridy = 1;
		getContentPane().add(lblEnterANickname, gbc_lblEnterANickname);
		
		userNewNameInput = new JTextField();
		GridBagConstraints gbc_userNewNameInput = new GridBagConstraints();
		gbc_userNewNameInput.insets = new Insets(0, 0, 5, 0);
		gbc_userNewNameInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNewNameInput.gridx = 1;
		gbc_userNewNameInput.gridy = 2;
		getContentPane().add(userNewNameInput, gbc_userNewNameInput);
		userNewNameInput.setColumns(10);
		
		JButton newUserNameButton = new JButton("Ok");
		GridBagConstraints gbc_newUserNameButton = new GridBagConstraints();
		gbc_newUserNameButton.insets = new Insets(0, 0, 5, 0);
		gbc_newUserNameButton.gridx = 1;
		gbc_newUserNameButton.gridy = 3;
		getContentPane().add(newUserNameButton, gbc_newUserNameButton);
		
		newUserNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Main.klient.send(Main.klient.idGetter(), userNewNameInput.getText(), null, "OK");
				windowCloser();
				
			}
		});
	}

}

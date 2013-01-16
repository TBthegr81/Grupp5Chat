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

public class BookmarkInput extends JFrame {
	//New window that handles the naming and saveíng of bookmarks and the userName.
  
	private static final long serialVersionUID = 1L;
	private JTextField bookmarkNameInput;

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
	public BookmarkInput() {
		setSize(new Dimension(300, 150));
		setResizable(false);
		setTitle("Enter a name for this server");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {10, 260, 10};
		gridBagLayout.rowHeights = new int[]{10, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblEnterANickname = new JLabel("Enter a nickname for your server:");
		lblEnterANickname.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblEnterANickname = new GridBagConstraints();
		gbc_lblEnterANickname.insets = new Insets(0, 0, 5, 0);
		gbc_lblEnterANickname.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEnterANickname.gridx = 1;
		gbc_lblEnterANickname.gridy = 1;
		getContentPane().add(lblEnterANickname, gbc_lblEnterANickname);
		
		bookmarkNameInput = new JTextField();
		GridBagConstraints gbc_bookmarkNameInput = new GridBagConstraints();
		gbc_bookmarkNameInput.insets = new Insets(0, 0, 5, 0);
		gbc_bookmarkNameInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_bookmarkNameInput.gridx = 1;
		gbc_bookmarkNameInput.gridy = 2;
		getContentPane().add(bookmarkNameInput, gbc_bookmarkNameInput);
		bookmarkNameInput.setColumns(10);
		
		JButton saveButton = new JButton("Save");
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.insets = new Insets(0, 0, 5, 0);
		gbc_saveButton.gridx = 1;
		gbc_saveButton.gridy = 3;
		getContentPane().add(saveButton, gbc_saveButton);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Attempting to save bookmark with name " + bookmarkNameInput.getText() + Main.gui.connectMenu.BookmarkGetIp() + Main.gui.connectMenu.BookmarkGetPort());
				
				Main.mainData.addBookmark(bookmarkNameInput.getText(), Main.gui.connectMenu.BookmarkGetIp(), Main.gui.connectMenu.BookmarkGetPort());
				
				System.out.println("Will now attempt save to file...");
				
				Main.saveData(Main.mainData);
				
				System.out.println("Saved!");
				Main.mainData.listAllBookmarks();
				windowCloser();
				
			}
		});
	}

}

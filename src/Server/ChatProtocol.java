package Server;
import java.util.ArrayList;

import Delat.Message;

public class ChatProtocol {
	private static ArrayList<String> states = new ArrayList<String>(); 
	public static User user;
	private static String message;
	 public int thisUser;
	 private int state;
	 public ChatProtocol()
	 {
		 states.add("WAITING");
		 states.add("SENTWELCOME");
		 states.add("SENTUSERNAMEANSWER");
		 states.add("SENTMOT");
		 states.add("SENTUSERS");
		 states.add("SENTROOMS");
		 states.add("CHATTING");
		 
		 state = states.indexOf("WAITING");
	 }
	 
	 public Message read(Message input)
	 {
		 message = "State = " + states.get(state);
		 System.out.println(message);
		 Lib.log(message);
		 
		 if(input == null || input.getMessage().equals("\\s+") || input.getMessage().equalsIgnoreCase("") || input.getMessage().equalsIgnoreCase(" "))
		 {
		 input = new Message(1, "Klient", "Null");
		 }
		 //String Input[] = input.split("\\s+",3);
		 //input = Input[2];
		 //String output = "";
		 Message output = null;
		 switch(state)
		 {
		 		// State = Waiting for Users to connect.
		 	case 0:
		 		// A user connected! Send them a welcome-message with the servername.
		 	//output = "1" + " " + "Server" + " " + "Welcome to " + Lib.settings.get(0);
		 	output = new Message(1, "Server", "Welcome to" + Lib.settings.get(0));
			break;
			
				// State = Client is connected and have sent the nickname they wanna use, check if its good.
		 	case 1:
		 		//if(!input.equalsIgnoreCase(Main.users.get(i).getNickname())) // Check if the nickname isn't Ted. Cuse that is always taken
		 		if(ServerThread.userOK(input.getMessage()) == false)
		 		{
		 			output = new Message(2,"Server","Nickname available");
		 			thisUser = ServerThread.createUser();

		 			Main.users.get(thisUser).setNickname(input.getMessage());
		 			System.out.println(Main.users.get(thisUser).getNickname());
		 			state = states.indexOf("SENTUSERNAMEANSWER");
		 		}
		 		else
		 		{
		 			output = new Message(2,"Server","Nickname is already taken, Choose another one");
		 		}
		 		break;
		 	
		 		// Sate = User have gotten their nickname accepted by the Server.
		 	case 2:
		 		// Now send the MOT (Message Of the Day) that is loaded from Settings-file
		 		output = new Message(4,"Server",Lib.settings.get(2));
		 		state = states.indexOf("SENTMOT");
		 		break;
		 		
		 		// State = MOT is sent, time for the users on the server
		 	case 3:
		 		output = new Message(5,"Server",ServerThread.getUsers());
		 		state = states.indexOf("SENTUSERS");
		 		break;
		 		
		 		// State = Users-list is sent. Time to send list of public chatrooms.
		 	case 4:
		 		// This will be loaded dynamicly later... ofc
		 		output = new Message(6,"Server",ServerThread.getRooms());
		 		state = states.indexOf("SENTROOMS");
		 		break;
		 		
		 		// State = Room-list is sent, User will reply with what room they wanna chat in.
		 	case 5:
		 		// Yeah, not sure how this case will be, yet. Just some random stuff atm.
		 		int room1 = ServerThread.getRoomIndex(input.getMessage());
		 		Main.rooms.get(room1).addUser(Main.users.get(thisUser));
		 		Main.rooms.get(room1).setUserLevel(Main.users.get(thisUser).getNickname(), 1);
		 		System.out.println("User nr: " + thisUser + " with username: " + Main.users.get(thisUser).getNickname() + " created in room " + Main.rooms.get(room1).roomName);
		 		output = new Message(7,"Server","Welcome to this room. Write something!");
		 		state = states.indexOf("CHATTING");
		 		break;
		 		
		 		/*
		 		 *  State = The user have joined a room. Now this will listen to users input,
		 		 *  send it to the room and also sent what the room says to the user
		 		 */
		 	case 6:
		 		output = new Message(8,"<Ted>","Indeed dear sir!");
		 		
		 		// Send "input" to the room...
		 		break;
		 		
		 }
		 message = "Input: " + input.getId() + " " + input.getUsername() + " " +  input.getMessage();
		 System.out.println(message);
		 Lib.log(message);
		 
		 message = "Output: " + output.getId() + " " + output.getUsername() + " " + output.getMessage();
		 System.out.println(message);
		 Lib.log(message);
		 
		 message = "State = " + states.get(state);
		 System.out.println(message);
		 Lib.log(message);
		 
		 System.out.println("\n");
		 return output;
	 }
}
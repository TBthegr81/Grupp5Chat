package Server;
import java.net.InetAddress;
import java.util.ArrayList;

import Delat.Message;
import Delat.Spell;

public class ChatProtocol {
	private static ArrayList<String> states = new ArrayList<String>(); 
	private User user;
	private static String message;
	 public int thisUser;
	 private int state;
	 public ChatProtocol(User user)
	 {
		 states.add("WAITING");
		 states.add("SENTWELCOME");
		 states.add("SENTUSERNAMEANSWER");
		 states.add("SENTMOT");
		 states.add("SENTROOMS");
		 states.add("JOINEDROOM");
		 states.add("SENTUSERS");
		 
		 state = states.indexOf("WAITING");
		 this.user = user;
	 }
	 
	 public Message read(Message input, InetAddress ip)
	 {
		 message = "State = " + states.get(state);
		 System.out.println(message);
		 Lib.log(message);

		 if(input == null || input.getMessage().equals("\\s+") || input.getMessage().equalsIgnoreCase("") || input.getMessage().equalsIgnoreCase(" "))
		 {
			 input = new Message(1, "Klient", "null", "Null");
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
			 output = new Message(1, "Server", "null","Welcome to" + Lib.settings.get(0));
			 state = states.indexOf("SENTWELCOME");
			 break;

			 // State = Client is connected and have sent the nickname they wanna use, check if its good.
		 case 1:
			 //if(!input.equalsIgnoreCase(Main.users.get(i).getNickname())) // Check if the nickname isn't Ted. Cuse that is always taken
			 if(ServerThread.userOK(input.getMessage()) == false)
			 {
				 user.setNickname(input.getMessage());
				 String text = user.getNickname();
				 Lib.print(text);
				 Lib.log(text);
				 output = new Message(2,"Server","null",text);
				 //thisUser = ServerThread.createUser();
				
				 //ServerThread.username = Main.users.get(thisUser).getNickname();
				 state = states.indexOf("SENTUSERNAMEANSWER");
			 }
			 else
			 {
				 output = new Message(3,"Server","null",input.getMessage());
			 }
			 break;

			 // State = User have gotten their nickname accepted by the Server.
		 case 2:
			 // Now send the MOT (Message Of the Day) that is loaded from Settings-file
			 output = new Message(4,"Server","null",Lib.settings.get(2));
			 state = states.indexOf("SENTMOT");
			 break;

			 // State = MOT is sent, time for the users on the server
		 case 3:
			// This will be loaded dynamicly later... ofc
			 output = new Message(5,"Server","null", ServerThread.getRooms());
			 state = states.indexOf("SENTROOMS");
			 break;
			 // State = Room-list is sent, User will reply with what room they wanna chat in
			 
		 case 4:
			 // Yeah, not sure how this case will be, yet. Just some random stuff atm.
			 /*int room1 = 0;
			 room1 = ServerThread.getRoomIndex(input.getMessage());
			 if(room1 == 0)
			 {
				 room1 = ServerThread.createRoom(input.getMessage());
			 }
			 Main.rooms.get(room1).addUser(Main.users.get(thisUser));
			 Main.rooms.get(room1).setUserLevel(Main.users.get(thisUser).getNickname(), 1);
			 */
			 Room room = Main.rooms.get(0);
			 room.addUser(user);
			 message = ("User nr: " + Main.users.indexOf(user) + " with username: " + user.getNickname() + " created in room " + room.getRoomName());
			 Lib.print(message);
			 Lib.log(message);
			 output = new Message(6,"Server", room.getRoomName(), "Welcome to " + room.getRoomName());
			 state = states.indexOf("JOINEDROOM");
			 break;
			 /*
			  *  State = The user have joined a room. Now this will listen to users input,
			  *  send it to the room and also sent what the room says to the user
			  */
			 
		 case 5:
			 
			 output = new Message(7,"Server","null",Main.rooms.get(0).getUsersString());
			 state = states.indexOf("SENTUSERS");

			 break;
			 // State = Users-list is sent. Time to send list of public chatrooms.
		 case 6:
			 //output = new Message(8,"<Ted>","Indeed dear sir!");
			 output = new Message(0, "Server" , Main.rooms.get(0).getRoomName(), "Message Sent");
			 //output = new Message(7,"Server",Main.rooms.get(0).roomName, Main.users.get(Main.users.indexOf(user)).cast(new Spell("FireBolt",5,1,10), Main.users.get(0)));
			 Main.rooms.get(0).say(user,input.getMessage());
			 
			 // Send "input" to the room...
			 break;

		 }
		 message = "Input: " + input.getId() + " " + input.getUsername() + " " + input.getRoom() + " "  +  input.getMessage();
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

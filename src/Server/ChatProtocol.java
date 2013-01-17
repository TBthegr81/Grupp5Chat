package Server;
import java.net.InetAddress;
import java.util.ArrayList;

import Delat.Message;

public class ChatProtocol {
	/*
	 * Chatprotocol är den andra delen av de två som gör upp köttet i Server-programmet.
	 * Här tas det klienten skirver in och behandlas.
	 * Den följer en lista på saker som ska utföras innan klientern får chatta.
	 * När en uppgift har gjorts klart sätts state till nästa sak,
	 * så när usern skickar något nästa gång går den vidare och gör nästa sak.
	 */
	private static ArrayList<String> states = new ArrayList<String>(); 
	private User user;
	private static String message;
	 public int thisUser;
	 private int state;
	 public ChatProtocol(User user)
	 {
		 states.add("WAITING"); // Här börjar den, väntandes på någon som ska connecta
		 states.add("SENTWELCOME"); // Här har den skickat välkomstmeddelandet
		 states.add("SENTUSERNAMEANSWER"); // Här svarar den på om det angivna username är ok
		 states.add("SENTMOT"); // Här har den skickat MOT (Message Of the Day) typ regler och stuff om servern
		 states.add("SENTROOMS"); // Här har den skickat listan över de chatrum som finns på servern
		 states.add("JOINEDROOM"); // Här har usern valt vilket rum den vill joina
		 states.add("SENTUSERS"); // Här får usern listan på dem som finns i rummet.
		 // Efter detta så är det chat som gäller.
		 
		 state = states.indexOf("WAITING");
		 this.user = user;
	 }
	 
	 public Message read(Message input, InetAddress ip)
	 {
		 // Börja logga inputen från user
		 message = "State = " + states.get(state);
		 Lib.print(message);
		 Lib.log(message);
		 
		 // Errorchecka inputen för konstiga saker, typ som att det är tomt.
		 if(input == null || input.getMessage().equals("\\s+") || input.getMessage().equalsIgnoreCase("") || input.getMessage().equalsIgnoreCase(" "))
		 {
			 input = new Message(1, "Klient", "null", "Null");
		 }
		 Message output = null;
		 switch(state)
		 {
		 // State = Waiting for Users to connect.
		 case 0:
			 // A user connected! Send them a welcome-message with the servername.
			 output = new Message(1, "Server", "null","Welcome to" + Lib.settings.get(0));
			 state = states.indexOf("SENTWELCOME");
			 break;

			 // State = Client is connected and have sent the nickname they wanna use, check if its good.
		 case 1:
			 if(ServerThread.userOK(input.getMessage()) == false)
			 {
				 user.setNickname(input.getMessage());
				 String text = user.getNickname();
				 Lib.print(text);
				 Lib.log(text);
				 output = new Message(2,"Server","null",text);
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
			 // Sending the list of rooms
			 output = new Message(5,"Server","null", Lib.getRooms());
			 state = states.indexOf("SENTROOMS");
			 break;
			 // State = Room-list is sent, User will reply with what room they wanna chat in
			 
		 case 4:
			 // Here the user chooses witch room s/he wanna join
			 // (Right now the user gets thrown into main-room whatever they say)
			 Room room = Main.rooms.get(0);
			 room.addUser(user);
			 message = ("User nr: " + Main.users.indexOf(user) + " with username: " + user.getNickname() + " created in room " + room.getRoomName());
			 Lib.print(message);
			 Lib.log(message);
			 output = new Message(6,"Server", room.getRoomName(), "Welcome to " + room.getRoomName());
			 Main.rooms.get(0).say(null,user.getNickname() + " has joined the room.");
			 state = states.indexOf("JOINEDROOM");
			 break;
			 /*
			  *  State = The user have joined a room. Now this will listen to users input,
			  *  send it to the room and also sent what the room says to the user
			  */
			 
		 case 5:
			 // Sending the list of users in that room
			 output = new Message(7,"Server","null",Main.rooms.get(0).getUsersString());
			 state = states.indexOf("SENTUSERS");

			 break;
			 // State = Users-list is sent. Time to send list of public chatrooms.
		 case 6:
			 // Everything the user sends now get sent to the "send to all users" function in that room.
			 // The server also sends back a confirmation that the message have been received and sent
			 output = new Message(0, "Server" , Main.rooms.get(0).getRoomName(), "Message Sent");
			 Main.rooms.get(0).say(user,input.getMessage());
			 
			 break;

		 }
		 // And in the end, print the state, input and output to the console and log.
		 // Mostly for bugtesting purposes.
		 message = "Input: " + input.getId() + " " + input.getUsername() + " " + input.getRoom() + " "  +  input.getMessage();
		 Lib.print(message);
		 Lib.log(message);

		 message = "Output: " + output.getId() + " " + output.getUsername() + " " + output.getMessage();
		 Lib.print(message);
		 Lib.log(message);

		 message = "State = " + states.get(state);
		 Lib.print(message);
		 Lib.log(message);

		 Lib.print("\n");
		 
		 // Send the answer back to the ServerThread that sends it to the user
		 return output;
	 }
	
}
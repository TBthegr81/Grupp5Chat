package Server;
import java.net.*;
import java.io.*;

public class ChatProtocol {
	 private static final int WAITING = 0;
	 private static final int SENTWELCOME = 1;
	 private static final int SENTUSERNAMEANSWER = 2;
	 private static final int SENTMOT = 3;
	 private static final int SENTUSERS = 4;
	 private static final int SENTROOMS = 5;
	 private static final int CHATTING = 6;

	 private int state = WAITING;
	 
	 public String read(String input)
	 {
		 String output = "";
		 switch(state)
		 {
		 		// State = Waiting for Users to connect.
		 	case 0:
		 		// A user connected! Send them a welcome-message with the servername.
		 	output = "1" + " " + "Server" + " " + "Welcome to " + Lib.settings.get(0);
		 	state = SENTWELCOME;
			break;
			
				// State = Client is connected and have sent the nickname they wanna use, check if its good.
		 	case 1:
			 	if(!input.equals("Ted")) // Check if the nickname isn't Ted. Cuse that is always taken
			 	{
			 		output = "2" + " " + "Server" + " " + "Nickname available";
			 		User user = new User();
			 		
			 		user.setNickname(input);
			 		state = SENTUSERNAMEANSWER;
			 	}
			 	else
			 	{
			 		output = "3" + " " + "Server" + " " + "Nickname is already taken, Choose another one";
			 	}
		 		break;
		 	
		 		// Sate = User have gotten their nickname accepted by the Server.
		 	case 2:
		 		// Now send the MOT (Message Of the Day) that is loaded from Settings-file
		 		output = "4" + " " + "Server" + " " + Lib.settings.get(2);
		 		state = SENTMOT;
		 		break;
		 		
		 		// State = MOT is sent, time for the users on the server
		 	case 3:
		 		// This will be loaded dynamicly later... ofc
		 		output = "5" + " " + "Server" + " " + "@TBthegr81, $SomeDude, $AnotherOne";
		 		state = SENTUSERS;
		 		break;
		 		
		 		// State = Users-list is sent. Time to send list of public chatrooms.
		 	case 4:
		 		// This will be loaded dynamicly later... ofc
		 		output = "6" + " " + "Server" + " " + "Main, Animetalk, Gametalking, Awesome";
		 		state = SENTROOMS;
		 		break;
		 		
		 		// State = Room-list is sent, User will reply with what room they wanna chat in.
		 	case 5:
		 		// Yeah, not sure how this case will be, yet. Just some random stuff atm.
		 		output = "7" + " " + "Server" + " " + "Welcome to this room. Write something!";
		 		state = CHATTING;
		 		break;
		 		
		 		/*
		 		 *  State = The user have joined a room. Now this will listen to users input,
		 		 *  send it to the room and also sent what the room says to the user
		 		 */
		 	case 6:
		 		output = "8" + " " + "Some user" + " " + "Stuff other people wrote!";
		 		// Send "input" to the room...
		 		break;
		 		
		 }
		 String message = "State = " + state;
		 System.out.println(message);
		 Lib.log(message);
		 
		 message = "Input: " + input;
		 System.out.println(message);
		 Lib.log(message);
		 
		 message = "Sever: " + output;
		 System.out.println(message);
		 Lib.log(message);
		 return output;
	 }
}

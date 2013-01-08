package Server;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
private Socket connectionToClient;
private ServerSocket openForConnections;
private PrintWriter outStream;
private BufferedReader inStream;
String message;
String servername;
ArrayList<String> settings;
int port;
public void start()
{
message = "Starting up server...";
System.out.println(message);
Lib.log(message);
settings = Lib.settings;
servername = settings.get(0);
port = Integer.parseInt(settings.get(1));
try {
openForConnections = new ServerSocket(port);
} catch (IOException e) {
message = "Could not listen on port: " + port;
System.err.println(message);
Lib.log(message);
System.exit(1);
}
message = "Server started on port " + port;
System.out.println(message);
Lib.log(message);
}
public void listenForClients()
{
// Start listening to things
message = "Starts listening to things...";
System.out.println(message);
Lib.log(message);
try {
connectionToClient = openForConnections.accept();
} catch (IOException e) {
message = "Error: " + e.getMessage();
System.err.println(message);
Lib.log(message);
}
message = "Client connected";
System.out.println(message);
Lib.log(message);
// Opens stream top out socket for sending and receiving
try {
outStream = new PrintWriter(connectionToClient.getOutputStream());
} catch (IOException e) {
message = "Error: " + e.getMessage();
System.err.println(message);
Lib.log(message);
}
try {
inStream = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream()));
} catch (IOException e) {
message = "Error: " + e.getMessage();
System.err.println(message);
Lib.log(message);
}
String inputLine = null;
String outputLine = null;
ChatProtocol cp = new ChatProtocol();
try {
do{
if (inputLine != null && inputLine.equals("END"))
{
close();
break;
}
outputLine = cp.read(inputLine);
outStream.println(outputLine);
outStream.flush();
}
while ((inputLine = inStream.readLine()) != null);
} catch (IOException e) {
message = "Error: " + e.getMessage();
System.err.println(message);
Lib.log(message);
}
}

public void close()
{
try{
inStream.close();
outStream.close();
connectionToClient.close();
} catch(IOException e) {
System.err.println("Error on close.");
}
message = "Server shutting down!";
System.out.println(message);
Lib.log(message);
}
public static int createUser()
{
User thisUser = new User();
Main.users.add(thisUser);
return Main.users.indexOf(thisUser);
}
public static int createRoom()
{
Room thisRoom = new Room();
Main.rooms.add(thisRoom);
return Main.rooms.indexOf(thisRoom);
}
public static String getUsers()
{
String text = "";
for(int i = 0; i < Main.users.size(); i++)
{
text = text + "<" + Main.users.get(i).getNickname() + "> ";
}
return text;
}
public static String getRooms()
{
String text = "";
for(int i = 0; i < Main.rooms.size(); i++)
{
text = text + Main.rooms.get(i).roomName + " ";
}
return text;
}
public static int getRoomIndex(String input)
{
int roomid = 0;
for(int i = 0; i < Main.rooms.size(); i++)
{
if(Main.rooms.get(i).getRoomName().equalsIgnoreCase(input))
{
roomid = i;
}
}
return roomid;
}
public static boolean userExist(String input)
{
boolean svar = false;
for(int i = 0; i < Main.users.size(); i++)
{
if(input.equalsIgnoreCase(Main.users.get(i).getNickname()))
{
svar = true;
}
}
return svar;
}
}*/
package Server;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * Innehåller randomfunktioner som kan användas lite wherever
 */
public class Lib {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	public static ArrayList<String> settings;
	
	public static void loadSettings()
	{
		/*
		 * Laddar settings ifrån userfile
		 */
			Path path = Paths.get("./bin/Server/Grupp5Chat.cfg");
			settings = new ArrayList<String>();
		    List<String> list;
		    String message;
		    message = "Trying to load settings...";
		    Lib.log(message);
		    System.out.println(message);
			try {
				list = Files.readAllLines(path, ENCODING);
				for(int i = 0; i < list.size(); i++)
			    {
					Lib.log(list.get(i));
					settings.add(list.get(i).split("\\s+")[1]);
			    }
				for(String line : settings)
				{
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
				message = "Cant load settings check file";
				Lib.log(message);
				System.out.println(message);
			}
			message = "Settings loaded\n";
			Lib.log(message);
			System.out.println(message);
	}
	
	public static void print(String text)
	{
		/*
		 * Printar till konsol
		 */
		System.out.println(text);
	}
	
	public static void log(String text)
	{
		/*
		 * Ska logga allt som händer på servern
		 */
		FileWriter logging = null;
		try {
			logging = new FileWriter("./bin/Server/Grupp5Chat.log", true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		try {
			logging.write(text + System.lineSeparator());
		} catch (IOException e) {
			System.out.println("Error-logging failed, check error-log for details");
		}
		
		// Close FileOutputStream
			try
			{
				logging.close();
			} catch (IOException e){
				System.out.println("Error on closing file");
			}
	}
}

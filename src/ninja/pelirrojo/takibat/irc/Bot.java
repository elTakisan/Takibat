package ninja.pelirrojo.takibat.irc;

import java.io.*;
import java.net.*;
import java.util.*;

public class Bot extends Thread{
	public static Properties runningConfig;
	private final BufferedReader rdr;
	private final PrintWriter out;
	public Bot(Socket s,Properties props,Bot b) throws IOException{
		runningConfig = props;
		rdr = new BufferedReader(new InputStreamReader(s.getInputStream(),props.getProperty("bot.charset")));
		out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(),props.getProperty("bot.charset")));
	}
	
	private void IRCPLine(String s){
		String a[] = s.split(":",2);
		String b[] = a[1].split(" ");
		String m   = a[2];
		if(b[1].equals("PRIVMSG")){
			if(m.startsWith("\001ACTION") && m.endsWith("\001")){
				// Slash Me
			}
			else{
				// Standard Message
			}
		}
		if(b[2].equals("QUIT")){
			// Somebody left, get them out of the user index
		}
		if(b[2].equals("JOIN")){
			// Somebody joined the channel
		}
	}
	
	public void run(){
		while(true){
			try{
				if(rdr.ready()){
					IRCPLine(rdr.readLine());
				}
			}
			catch(IOException e){
				
			}
		}
	}
}


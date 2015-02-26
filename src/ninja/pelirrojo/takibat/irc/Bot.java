package ninja.pelirrojo.takibat.irc;

import java.io.*;
import java.net.*;
import java.util.*;

public class Bot extends Thread{
	public static Properties runningConfig;
	private final BufferedReader rdr;
	private final PrintWriter out;
	public Bot(Socket s,Properties props) throws IOException{
		runningConfig = props;
		rdr = new BufferedReader(new InputStreamReader(s.getInputStream(),props.getProperty("bot.charset")));
		out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(),props.getProperty("bot.charset")));
	}
	
	private String IRCPLine(String s){
		return null;
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

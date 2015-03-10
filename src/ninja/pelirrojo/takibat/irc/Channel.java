package ninja.pelirrojo.takibat.irc;

import java.io.IOException;
import java.io.OutputStream;

public class Channel{
	private final String chanName;
	private final OutputStream out;
	public Channel(String chanName){
		this.chanName = chanName;
		this.out = new PrefixedOutputStream(IRCConnection.instance.out,String.format("PRIVMSG %s :",chanName).getBytes());
	}
	public String toString(){
		return chanName;
	}
	public OutputStream getOutputStream(){
		return out;
	}
	public void msg(String s){
		try{
			IRCConnection.instance.out.write(String.format("PRIVMSG %s :%s\r\n",chanName,s).getBytes());
		}
		catch(IOException e){}
	}
}

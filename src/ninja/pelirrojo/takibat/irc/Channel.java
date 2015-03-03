package ninja.pelirrojo.takibat.irc;

import java.io.OutputStream;

import ninja.pelirrojo.util.PrefixedOutputStream;

public class Channel{
	private final String chanName;
	private final OutputStream out;
	protected Channel(String chanName){
		this.chanName = chanName;
		this.out = new PrefixedOutputStream(IRCConnection.instance.out,String.format("PRIVMSG %s :",chanName).getBytes());
	}
	public String toString(){
		return chanName;
	}
	public OutputStream getOutputStream(){
		return out;
	}
}

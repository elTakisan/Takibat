package ninja.pelirrojo.takibat.irc;

import java.io.IOException;
import java.io.OutputStream;
import ninja.pelirrojo.util.PrefixedOutputStream;
import java.util.Formattable;
import java.util.Formatter;

/**
 * Representation of a User.
 * 
 * @author takisan <takisan@pelirrojo.ninja>
 * @since INDEV-0
 * @version INDEV-0
 */
public class User implements Formattable{
	/** Nickname. */
	private final String nick;
	/** Username. */
	private final String user;
	/** Hostname. */
	private final String host;
	/**
	 * Creates a new User.
	 * 
	 * @param nick Nickname
	 * @param user Username
	 * @param host Hostname
	 */
	protected User(String nick,String user,String host){
		this.nick = nick;
		this.user = user;
		this.host = host;
	}
	/**
	 * Parses a User.
	 * 
	 * @param s A string of a user
	 * @return User object, or Server Object if it's the Server
	 */
	public static final User parse(String s){
		if(s.indexOf("@") > 1 && s.indexOf("!") > 0){
			String[] sz = s.split("!");
			String[] szz = s.split("@");
			return new User(sz[0],szz[0],szz[1]);
		}
		return null;
	}
	/* === Getters === */
	/**
	 * Gets the Nickname.
	 * 
	 * @return Nickname
	 */
	public String getNick(){
		return nick;
	}
	/**
	 * Gets the Username.
	 * 
	 * @return Username
	 */
	public String getUser(){
		return user;
	}
	/**
	 * Gets the Hostname.
	 * 
	 * @return Hostname
	 */
	public String getHost(){
		return host;
	}
	/**
	 * Runs a Whois on the User.
	 * 
	 * @return Whois object
	 */
	public UserWhois whois(){
		return null; // TODO
	}
	/**
	 * Gets an Output Stream that is sent to the client.
	 * 
	 * @return OutputStream
	 */
	public OutputStream getOutputStream(){
		return new PrefixedOutputStream(IRCConnection.instance.out,String.format("PRIVMSG %s :",nick).getBytes());
	}
	public void msg(String s){ // TODO Document
		try{
			IRCConnection.instance.out.write(String.format("PRIVMSG %s :%s\r\n",nick,s).getBytes());
		}
		catch(IOException e){}
	}
	public void notice(String s){
		try{
			IRCConnection.instance.out.write(String.format("NOTICE %s :%s\r\n",nick,s).getBytes());
		}
		catch(IOException e){}
	}
	/**
	 * Formats the User in the format of {@code <nick>!<user>@<host>}.
	 */
	public String toString(){
		return String.format("%s!%s@%s",nick,user,host);
	}
	public void formatTo(Formatter f,int l,int w,int p){
		// TODO Auto-generated method stub
		
	}
}

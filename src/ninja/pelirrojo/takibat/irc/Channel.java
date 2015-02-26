package ninja.pelirrojo.takibat.irc;

public interface Channel{
	public boolean amIOp();
	public void say(String s);
	public void topic(String s) throws IRCException;
	public User[] getUsers();
}

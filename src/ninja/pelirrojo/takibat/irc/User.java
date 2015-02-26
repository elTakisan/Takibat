package ninja.pelirrojo.takibat.irc;

public interface User{
	public String nick();
	public String user();
	public String host();
	public String real();
	public boolean isOp();
	public void say(String line);
	public void notice(String line);
}

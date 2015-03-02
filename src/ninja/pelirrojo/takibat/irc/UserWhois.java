package ninja.pelirrojo.takibat.irc;

public class UserWhois extends User{
	protected UserWhois(String nick,String user,String host){
		super(nick,user,host);
		// TODO Auto-generated constructor stub
	}
	@Override
	public UserWhois whois(){
		return this;
	}
}

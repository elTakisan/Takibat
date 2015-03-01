package ninja.pelirrojo.takibat.irc;

import java.util.Formattable;
import java.util.Formatter;

import org.python.core.PyObject;

public class User implements Formattable{
	private final String nick;
	private final String user;
	private final String host;
	private final String real;
	protected User(String nick,String user,String host,String real){
		this.nick = nick;
		this.user = user;
		this.host = host;
		this.real = real;
	}
	
	public static User parse(String s){
		if(s.indexOf("@") > 1 && s.indexOf("!") > 0){
			String[] sz = s.split("[@!]");
			return new User(sz[0],sz[1],sz[2],null);
		}
		return null;
	}
	
	@Override
	public String toString(){
		return String.format("%s!%s@%s",nick,user,host);
	}

	public void formatTo(Formatter f,int l,int w,int p){
		// TODO Auto-generated method stub
		
	}
	
	public PyObject toPython(){
		
		return null;
	}
}

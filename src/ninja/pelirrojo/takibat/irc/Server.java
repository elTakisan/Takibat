package ninja.pelirrojo.takibat.irc;

import java.util.Formattable;
import java.util.Formatter;

public class Server extends User implements Formattable{
	private final String name;
	public Server(String a){
		super(a,a,a);
		name = a;
	}
	public String toString(){
		return name;
	}
	public void formatTo(Formatter f, int l, int w, int p) {
		
	}
}

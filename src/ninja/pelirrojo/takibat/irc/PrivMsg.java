package ninja.pelirrojo.takibat.irc;

public class PrivMsg extends ParsedLine{
	private User u;
	private String line;
	public PrivMsg(String s){
		super(s);
		String[] sp = s.split(":",2);
		String[] pr = sp[1].split(" ");
		u = User.parse(pr[0]);
		line = sp[2];
	}
	public String toString(){
		return String.format("<%s> %s",u.getNick(),line.trim());
	}
}

package ninja.pelirrojo.takibat.irc;

public class ParsedLine{

	protected final String raw;
	
	protected ParsedLine(String raw){
		this.raw = raw;
	}

	protected static ParsedLine parse(String s){
		if(s.indexOf(" PRIVMSG ") > 1){
			return new PrivMsg(s);
		}
		return null;
	}
	
	public String toString(){
		return raw;
	}
}

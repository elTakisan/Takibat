package ninja.pelirrojo.takibat.irc;

public class ParsedLine{

	protected final String raw;
	
	protected ParsedLine(String raw){
		this.raw = raw;
	}

	protected static ParsedLine parse(String s){
		try{
			if(s.indexOf(" PRIVMSG ") > 1 || s.indexOf(" NOTICE ") > 1){
				String[] sp = s.split(":",3);
				String[] pr = sp[1].split(" ");
				User u = User.parse(pr[0]);
				String dest = pr[2];
				if(dest.equals(IRCConnection.instance.myNick) ||
				   dest.equals("*"))
					return new PrivMsg(s,u,sp[2]); // TODO Check this
				else
					return new ChanMsg(s,u,pr[2],sp[2]);
			}
			if(s.indexOf(" MODE ") > 1){
				return new ModeLine(s);
			}
			if(s.indexOf(" 372 ") > 1){
				// TODO MOTD
			}
			if(s.indexOf(" 376 ") > 1){
				// TODO End of MOTD
			}
			if(s.indexOf(" 332 ") > 1){
				// TODO Set Topic on the Channel
			}
			if(s.indexOf(" 333 ") > 1){
				// TODO Created?
			}
			if(s.indexOf(" 353 ") > 1){
				// TODO Members
			}
		}
		catch(Exception e){
			return new ParsedLine(s);
		}
		return new ParsedLine(s);
	}
	
	public String toString(){
		return raw;
	}
}

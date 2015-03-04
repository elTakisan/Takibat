package ninja.pelirrojo.takibat.irc;

public class ChanMsg extends PrivMsg{
	private User u;
	private String d;
	private String l;
	protected ChanMsg(String s,User u,String d,String l){
		super(s,u,l);
		this.u = u;
		this.d = d;
		this.l = l;
	}
	
	public Channel getChan(){
		return new Channel(d);
	}
	
	public String toString(){
		return String.format("{%s} <%s> %s",d,u.getNick(),l.trim());
	}
}

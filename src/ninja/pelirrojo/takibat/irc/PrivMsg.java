package ninja.pelirrojo.takibat.irc;

public class PrivMsg extends ParsedLine{
	private User u;
	private String line;
	protected PrivMsg(String s,User u,String line){
		super(s);
		this.u = u;
		this.line = line;
	}
	public User getUser(){
		return u;
	}
	public String getLine(){
		return line;
	}
	public String toString(){
		try{
			return String.format("<%s> %s",u.getNick(),line.trim());
		}
		catch(Exception e){
			return super.toString();
		}
	}
}

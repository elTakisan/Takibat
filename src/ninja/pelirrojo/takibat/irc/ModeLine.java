package ninja.pelirrojo.takibat.irc;

public class ModeLine extends ParsedLine{
	private final User sender;
	private final Channel destChan;
	private final String destUser;
	private final String opts;
	protected ModeLine(String line){
		super(line);
		String[] chars = line.substring(1).split(" ");
		sender = User.parse(chars[0]);
		if(chars[2].indexOf("#") > -1){
			destChan = new Channel(chars[2]);
			opts = chars[3];
			destUser = chars[4];
		}
		else{
			destChan = null;
			destUser = chars[2];
			opts = chars[3].substring(1);
		}
	}
	public User getSender(){
		return sender;
	}
	public Channel getDestChan(){
		return destChan;
	}
	public String getDestUser(){
		return destUser;
	}
	public String getOpts(){
		return opts;
	}
}

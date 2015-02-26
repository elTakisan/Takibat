package ninja.pelirrojo.takibat.bot;

import ninja.pelirrojo.takibat.irc.Channel;
import ninja.pelirrojo.takibat.irc.User;

public abstract class BotPlugin{
	public void onLine(User u,Channel c,String l){}
	public void onSlashMe(User u,Channel c,String l){}
	public void onJoin(User u,Channel c){}
	public void onPart(User u,Channel c,String l){}
	public void onPriv(User u,String l){}
	public void onUnknown(String l){}
}

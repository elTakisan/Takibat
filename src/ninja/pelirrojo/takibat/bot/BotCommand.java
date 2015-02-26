package ninja.pelirrojo.takibat.bot;

import ninja.pelirrojo.takibat.irc.*;

public abstract class BotCommand{
	public abstract void onCommand(User u,Channel c,String cmd,String[] args,String raw);
}

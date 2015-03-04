package ninja.pelirrojo.takibat.bot;

import ninja.pelirrojo.takibat.irc.*;
import java.io.PrintStream;

/**
 * Abstract Class of a Command for the Bot.
 * 
 * @author takisan <takisan@pelirrojo.ninja>
 * @since INDEV-0
 * @version INDEV-0
 */
public abstract class BotCommand{
	public abstract void onCommand(User u,Channel c,String cmd,String[] args,String raw,PrintStream out,PrintStream err);
}

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
	/**
	 * Run on a Command for the Bot.
	 * 
	 * @param u User who sent the command
	 * @param c Channel the command was sent to
	 * @param cmd Command String
	 * @param args Argument String
	 * @param raw Raw String Line
	 * @param out Standard Output
	 * @param err Standard Error
	 */
	public abstract void onCommand(User u,Channel c,String cmd,String[] args,String raw,PrintStream out,PrintStream err);
}

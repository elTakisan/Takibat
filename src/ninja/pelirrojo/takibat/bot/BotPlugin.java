package ninja.pelirrojo.takibat.bot;

import ninja.pelirrojo.takibat.irc.Channel;
import ninja.pelirrojo.takibat.irc.User;

import java.io.PrintStream;

/**
 * Root class for a bot plugin.
 * 
 * @author takisan <takisan@pelirrojo.ninja>
 * @since INDEV-0
 * @version INDEV-0
 */
public abstract class BotPlugin{
	/**
	 * Run on a standard IRC channel message.
	 * 
	 * @param u User who sent
	 * @param c Channel sent to
	 * @param l Line of text
	 * @param out Standard Output
	 * @param err Standard Error
	 */
	public void onLine(User u,Channel c,String l,PrintStream out,PrintStream err){}
	/**
	 * Run on a Slash-Me Action.
	 * 
	 * @param u User who sent
	 * @param c Channel sent to
	 * @param l Line of Text
	 * @param out Standard Output
	 * @param err Standard Error
	 */
	public void onSlashMe(User u,Channel c,String l,PrintStream out,PrintStream err){}
	/**
	 * Run on a user joining the Channel.
	 * 
	 * @param u User who joined
	 * @param c Channel joined to
	 * @param out Standard Output
	 * @param err Standard Error
	 */
	public void onJoin(User u,Channel c,PrintStream out,PrintStream err){}
	/**
	 * Run on a user leaving the Channel.
	 * 
	 * @param u User who left
	 * @param c Channel they left
	 * @param l Exit Message
	 * @param out Standard Output
	 * @param err Standard Error
	 */
	public void onPart(User u,Channel c,String l,PrintStream out,PrintStream err){}
	/**
	 * Run on a private message to the channel.
	 * 
	 * @param u User who privmsgd
	 * @param l Message
	 * @param out Standard Output
	 * @param err Standard Error
	 */
	public void onPriv(User u,String l,PrintStream out,PrintStream err){}
	/**
	 * Run on an unknown line from the server.
	 * 
	 * @param l Line of Text
	 * @param out Standard Output
	 * @param err Standard Error
	 */
	public void onUnknown(String l,PrintStream out,PrintStream err){}
	/**
	 * Periodically run on a thread.
	 * 
	 * @param c Channel
	 * @param out Standard Output
	 * @param err Standard Error
	 */
	public void periodic(Channel c,PrintStream out,PrintStream err){}
}

package ninja.pelirrojo.takibat.bot;

import java.io.*;
import java.net.*;
import java.util.*;

import org.python.core.PyList;
import org.python.util.PythonInterpreter;

import ninja.pelirrojo.takibat.irc.ChanMsg;
import ninja.pelirrojo.takibat.irc.Channel;
import ninja.pelirrojo.takibat.irc.IRCConnection;
import ninja.pelirrojo.takibat.irc.ParsedLine;
import ninja.pelirrojo.takibat.irc.PrivMsg;
import ninja.pelirrojo.takibat.irc.User;

/**
 * Root class of the Bot.
 * 
 * @author takisan <takisan@pelirrojo.ninja>
 * @since INDEV-0
 * @version INDEV-0
 */
public final class Takibat{
	/** Configuration. */
	public static Properties runningConf = new Properties();
	/** Set of the plugins on the Bot. */
	public static Set<BotPlugin> plugins = new HashSet<BotPlugin>();
	/** Map of the commands. */
	public static Map<String,BotCommand> commands = new HashMap<String,BotCommand>();
	/** Plugin to interpret lines as commands. */
	private static final BotPlugin cmdIntPlg = new BotPlugin(){
		public void onLine(User u,Channel c,String l,PrintStream out,PrintStream err){
			if(l.startsWith(runningConf.getProperty("bot.commandPrefix"))){
				List<String> spax = Arrays.asList(l.substring(1).split(" "));
				String cmd = spax.remove(0);
				if(!commands.containsKey(cmd))
					return;
				commands.get(cmd).onCommand(
						u,
						c,
						cmd,
						spax.toArray(new String[spax.size()]),
						l,
						new PrintStream(c.getOutputStream()){
							public void println(){
								super.print("\r\n");
							}
						},
						new PrintStream(u.getOutputStream()){
							public void println(){
								super.print("\r\n");
							}
						});
			}
		}
		public void onPriv(User u,String l,PrintStream out,PrintStream err){
			if(l.startsWith(runningConf.getProperty("bot.commandPrefix"))){
				List<String> spax = Arrays.asList(l.substring(1).split(" "));
				String cmd = spax.remove(0);
				if(!commands.containsKey(cmd))
					return;
				commands.get(cmd).onCommand(
						u,
						null,
						cmd,
						spax.toArray(new String[spax.size()]),
						l,
						out,
						err);
			}
		}
	};
	/** Reload Command. */
	private static final BotCommand reload = new BotCommand(){
		public void onCommand(User u,Channel c,String cmd,String[] args,String raw,PrintStream out,PrintStream err){
			plugins.clear();
			commands.clear();
			plugins.add(cmdIntPlg);
			commands.put("reload",this);
			try{
				load(err);
			}
			catch(IOException e){
				err.println(e.getMessage());
			}
			out.println("Reloaded");
		}
	};
	/** Program Initalization. */
	public static void main(String[] args) throws Exception{
		Socket sock = new Socket("morgan.freenode.net",6667);
		plugins.add(cmdIntPlg);
		commands.put("reload",reload);
		runningConf.setProperty("bot.commandprefix","!");
		IRCConnection conn = new IRCConnection(sock.getOutputStream(),sock.getInputStream(),"takibot",null,0){
			public void onLine(ParsedLine line){
				if(line instanceof PrivMsg){
					PrivMsg msg = (PrivMsg) line;
					for(BotPlugin plug:plugins){
						if(msg instanceof ChanMsg){
							ChanMsg m = (ChanMsg) msg;
							plug.onLine(m.getUser(),m.getChan(),m.getLine(),null,null); // TODO Get Out and Err
						}
						else
							plug.onPriv(msg.getUser(),msg.getLine(),null,null); // TODO
					}
				}
			}
		};
		conn.start();
		conn.join("##takisan");
		conn.join();
		sock.close();
	}
	/**
	 * Loads all plugins.
	 * 
	 * @param err Error Stream
	 * @throws IOException
	 */
	private static void load(PrintStream err) throws IOException{
		File d = new File("plugins/");
		if(!d.exists())
			d.mkdir();
		for(File f:d.listFiles()){
			try{
				if(f.toString().endsWith(".py"))
					loadPy(f);
			}
			catch(BotException e){
				err.println(e.getMessage());
			}
		}
	}
	/**
	 * Loads a Python File into an Command.
	 * 
	 * @param f Python File to load
	 * @throws IOException IO Problem
	 * @throws BotException Bot Problem
	 */
	private static void loadPy(File f) throws IOException,BotException{
		PythonInterpreter i = new PythonInterpreter();
		i.execfile(new FileInputStream(f));
		i.exec("__d = dir()");
		PyList dir = (PyList) i.get("__d");
		for(Object o:dir){
			String s = o.toString();
			if(s.startsWith("__") || s.equals("BotPlugin") || s.equals("BotCommand"))
				continue; // We don't want the base classes included
			i.exec("__ec = issubclass("+s+",BotCommmand)");
			i.exec("__ep = issubclass("+s+",BotPlugin)");
			if(i.get("__ec").__nonzero__()){
				// Command Processing
				i.exec("__do = dir("+s+")");
				PyList object = (PyList) i.get("__do");
				if(!object.contains("provides"))
					throw new BotException(String.format("Class %s in %s doesn't have `provides` as a class variable",s,f));
				i.exec("__i = "+s+"()");
				BotCommand instance = (BotCommand) i.get("__i").__tojava__(BotCommand.class);
				PyList provides = (PyList) i.get(s+".provides");
				for(Object lst:provides){
					commands.put(lst.toString(),instance);
				}
			}
			else if(i.get("__ep").__nonzero__()){
				i.exec("__i = "+s+"()");
				BotPlugin instance = (BotPlugin) i.get("__i").__tojava__(BotPlugin.class);
				plugins.add(instance);
			}
		}
	}
}

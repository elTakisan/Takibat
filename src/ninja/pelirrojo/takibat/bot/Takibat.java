package ninja.pelirrojo.takibat.bot;

import java.io.*;
import java.net.*;
import java.util.*;

import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyClass;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import ninja.pelirrojo.takibat.irc.ChanMsg;
import ninja.pelirrojo.takibat.irc.Channel;
import ninja.pelirrojo.takibat.irc.IRCConnection;
import ninja.pelirrojo.takibat.irc.IRCException;
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
			if(l.startsWith("!")){
				System.err.println("Line Starts with Command Prefix");
				System.err.println(commands.containsKey(l.substring(1).trim()));
				List<String> spax = Arrays.asList(l.substring(1).split(" "));
				String cmd = spax.get(0);
				commands.get(l.substring(1).trim().split(" ")[0]).onCommand(
						u,
						c,
						cmd,
						spax.subList(1,spax.size()).toArray(new String[spax.size()-1]),
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
				String cmd = spax.get(0);
				commands.get(l.substring(1).trim().split(" ")[0]).onCommand(
						u,
						null,
						cmd,
						spax.subList(1,spax.size()).toArray(new String[spax.size()]),
						l,
						out,
						err);
			}
		}
	};
	private static final BotCommand jingCommand = new BotCommand(){
		public void onCommand(User u,Channel c,String cmd,String[] args,String raw,PrintStream out,PrintStream err){
			c.msg("Jong");
		}
	};
	/** Reload Command. */
	private static final BotCommand reload = new BotCommand(){
		public void onCommand(User u,Channel c,String cmd,String[] args,String raw,PrintStream out,PrintStream err){
			plugins.clear();
			commands.clear();
			plugins.add(cmdIntPlg);
			commands.put("reload",this);
			commands.put("jing",jingCommand);
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
		commands.put("jing",jingCommand);
		load(System.err);
		System.err.println(plugins);
		System.err.println(commands);
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
			err.println(f);
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
		System.err.printf("Loading %s%n",f);
		PythonInterpreter i = new PythonInterpreter();
		i.execfile(new FileInputStream(f));
		i.exec("__d = dir()");
		PyList dir = (PyList) i.get("__d");
		for(Object o:dir){
			String s = o.toString();
			if(s.startsWith("__") || s.equals("BotPlugin") || s.equals("BotCommand"))
				continue; // We don't want the base classes included
			i.exec(	"__ic = 0\n" +
					"__ip = 0\n" +
					"try:\n" +
					"    __ic = issubclass("+s+",BotCommand)\n" +
					"except:\n" +
					"    pass\n" +
					"try:\n" +
					"    __ip = issubclass("+s+",BotPlugin)\n" +
					"except:\n" +
					"    pass\n");
			if(i.get("__ic").__nonzero__()){
				PyObject oj = i.get(s);
				PyList odir = (PyList) oj.__dir__();
				if(!odir.contains("provides"))
					throw new BotException(String.format("%s in %s does not have Provides as a class variable.",s,f));
				
			}
			if(i.get("__ip").__nonzero__()){
				
			}
		}
	}
	private static class PyCmdFct{
		private PyObject cfo;
		private PyCmdFct(PyObject cfo){
			this.cfo = cfo;
		}
		private BotCommand create(){
			return (BotCommand) cfo.__call__().__tojava__(BotCommand.class);
		}
	}
	
	private static class PyPlgFct{
		private PyObject cfo;
		private PyPlgFct(PyObject cfo){
			this.cfo = cfo;
		}
		private BotPlugin create(){
			return (BotPlugin) cfo.__call__().__tojava__(BotPlugin.class);
		}
	}
}

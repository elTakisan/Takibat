package ninja.pelirrojo.takibat.bot;

import java.io.*;
import java.net.*;
import java.util.*;

import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import ninja.pelirrojo.takibat.irc.ChanMsg;
import ninja.pelirrojo.takibat.irc.Channel;
import ninja.pelirrojo.takibat.irc.IRCConnection;
import ninja.pelirrojo.takibat.irc.ParsedLine;
import ninja.pelirrojo.takibat.irc.PrivMsg;
import ninja.pelirrojo.takibat.irc.User;

public final class Takibat{
	public static Properties runningConf = new Properties();
	public static Set<BotPlugin> plugins = new HashSet<BotPlugin>();
	public static Map<String,BotCommand> commands = new HashMap<String,BotCommand>();
	private static final BotPlugin cmdIntPlg = new BotPlugin(){
		public void onLine(User u,Channel c,String l){
			if(l.startsWith(runningConf.getProperty("bot.commandPrefix"))){
				List<String> spax = Arrays.asList(l.substring(1).split(" "));
				
			}
		}
		public void onPriv(User u,String l){
			if(l.startsWith(runningConf.getProperty("bot.commandPrefix"))){
				List<String> spax = Arrays.asList(l.substring(1).split(" "));
			}
		};
	};
	private static final BotCommand reload = new BotCommand(){
		public void onCommand(User u,Channel c,String cmd,String[] args,String raw,PrintStream out,PrintStream err){
			plugins.clear();
			commands.clear();
			plugins.add(cmdIntPlg);
			commands.put("reload",this);
			try{
				load();
			}
			catch(IOException e){
				err.println(e.getMessage());
			}
			out.println("Reloaded");
		}
	};
	public static void main(String[] args) throws Exception{
		Socket sock = new Socket("morgan.freenode.net",6667);
		plugins.add(cmdIntPlg);
		commands.put("reload",reload);
		IRCConnection conn = new IRCConnection(sock.getOutputStream(),sock.getInputStream(),"takibot",null,0){
			public void onLine(ParsedLine line){
				if(line instanceof PrivMsg){
					PrivMsg msg = (PrivMsg) line;
					for(BotPlugin plug:plugins){
						if(msg instanceof ChanMsg){
							ChanMsg m = (ChanMsg) msg;
							plug.onLine(m.getUser(),m.getChan(),m.getLine());
						}
						else
							plug.onPriv(msg.getUser(),msg.getLine());
					}
				}
			}
		};
		conn.start();
		conn.join("##takisan");
		conn.join();
		sock.close();
	}
	private static void load() throws IOException{
		File d = new File("plugins/");
		if(!d.exists())
			d.mkdir();
		for(File f:d.listFiles()){
			if(f.toString().endsWith(".py"))
				loadPy(f);
			if(f.toString().endsWith(".jar"))
				loadJar(f);
		}
	}
	private static void loadPy(File f) throws IOException{
		PythonInterpreter i = new PythonInterpreter();
		i.execfile(new FileInputStream(f));
		i.exec("__d = dir()");
		PyList dir = (PyList) i.get("__d");
		for(Object o:dir){
			String s = o.toString();
			if(s.startsWith("__") || s.equals("BotPlugin") || s.equals("BotCommand"))
				continue;
			i.exec("__do = dir("+s+")");
		}
	}
	private static void loadJar(File f) throws IOException{
		// TODO Load .Jar
	}
}

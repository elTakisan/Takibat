package ninja.pelirrojo.takibat.irc;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

/**
 * Class of an IRC Connection.
 * 
 * @author takisan <takisan@pelirrojo.ninja>
 * @since INDEV-0
 * @version INDEV-0
 */
public final class IRCConnection extends Thread implements Runnable{
	/**
	 * Thread to parse an IRC Line.
	 * 
	 * @author takisan <takisan@pelirrojo.ninja>
	 * @since INDEV-0
	 * @version INDEV-0
	 */
	private class LineParseThread extends Thread implements Runnable{
		private final String line;
		private LineParseThread(String line){
			this.line = line;
		}
		public void run(){
			if(this.line.startsWith("PING ")){
				try{
					System.out.println("[I] Sent Pong");
					out.write("PONG\r\n".getBytes() );
				}
				catch(IOException e){
					
				}
			}
			else{
				ParsedLine line = ParsedLine.parse(this.line);
				recievedStack.add(line);
				onLine(line);
			}
		}
	}
	/** InputStream from the Connection. */
	protected final InputStream in;
	/** OutputStream to the Connection. */
	protected final OutputStream out;
	
	protected static IRCConnection instance;
	
	protected final String myNick;
	/**
	 * Creates a new IRC Connection
	 * 
	 * @param out OutputStream
	 * @param in InputStream
	 * @param nick Nickname to use
	 * @param pass Password to Connect (may be null)
	 * @param pushpings Push the Pings up to the Server
	 * @throws IOException
	 */
	public IRCConnection(final OutputStream out,final InputStream in,final String nick,final String pass,final int pushpings) throws IOException{
		this.out = out;
		this.in  = in;
		this.myNick = nick;
		if(pass != null)
			this.out.write(String.format("PASS %s\r\n",pass).getBytes());
		this.out.write(String.format("NICK %s\r\n",nick).getBytes());
		this.out.write(String.format("USER %s %s %s :%s\r\n",nick,nick,"localhost",nick).getBytes());
		
		instance = this;
	}
	/** Stack of incoming IRC Messages. */
	private List<ParsedLine> recievedStack = new ArrayList<ParsedLine>();
	/**
	 * Pulls out the top thing on the Stack.
	 * 
	 * @return Top of the Stack
	 */
	public ParsedLine popStack(){
		if(recievedStack.size() > 0)
			return recievedStack.remove(0);
		else
			return null;
	}
	/**
	 * Peeks at the top of the stack.
	 * 
	 * @return Top of the Stack
	 */
	public ParsedLine peekStack(){
		if(recievedStack.size() > 0)
			return recievedStack.get(0);
		else
			return null;
	}
	/**
	 * Override this method to run something whenever a line is recieved.
	 * 
	 * @param line Line
	 */
	public void onLine(ParsedLine line){}
	/* == Thread Invocation == */
	/**
	 * This thread just reads lines from the socket and passes them on
	 * to an {@link LineParseThread}.
	 */
	public void run(){
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		while(true){
			try{
				if(isInterrupted())
					break;
				if(in.available() > 0){
					buf.write(in.read());
					if(buf.toString().endsWith("\r\n")){
						new LineParseThread(buf.toString().substring(0,buf.toString().length()-1)).start();
						buf.reset();
					}
				}
			}
			catch(IOException e){
				System.err.printf("[E] %s%n",e.getLocalizedMessage());
			}
		}
		try{
			out.close();
			in.close();
		}
		catch(IOException e){
			System.err.printf("[E] %s%n",e.getLocalizedMessage());
		}
	}
	/**
	 * Joins an IRC Channel.
	 * 
	 * @param channel Channel to join
	 */
	public void join(String channel){
		try{
			out.write(String.format("JOIN %s\r\n",channel).getBytes());
		}
		catch(IOException e){
			System.err.printf("[E] %s%n",e.getLocalizedMessage());
		}
	}
	
	public static void main(String[] args) throws Exception{
		// Testing Program XXX
		System.out.println("[I] Init");
		Socket sock = new Socket("morgan.freenode.net",6667);
		final IRCConnection conn = new IRCConnection(sock.getOutputStream(),sock.getInputStream(),"takibot",null,0);
		conn.start();
		conn.join("##takisan");
		new Thread(){
			public void run(){
				while(true){
					ParsedLine line = conn.popStack();
					if(line != null) System.out.println(line);
				}
			}
		}.start();
		conn.join();
		sock.close();
	}
}

package ninja.pelirrojo.takibat.irc;

import java.io.*;
import java.net.*;

/**
 * Class of an IRC Connection.
 * 
 * @author takisan <takisan@pelirrojo.ninja>
 * @since INDEV-0
 * @version INDEV-0
 */
public abstract class IRCConnection extends Thread implements Runnable{
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
				// It's a Ping line, send a Pong Back
				try{
					out.write("PONG\r\n");
				}
				catch(IOException e){
					
				}
				ParsedLine.parse(this.line);
			}
		}
	}
	/** Socket Connection. */
	@SuppressWarnings("unused")
	private final Socket sock;
	/** Reader in from the Connection. */
	private final Reader rdr;
	/** Writer out to the Connection. */
	private final Writer out;
	public IRCConnection(Socket sock,String nick,String pass,String charset) throws IOException{
		this.sock = sock;
		this.rdr = new BufferedReader(new InputStreamReader(sock.getInputStream(),charset));
		this.out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream(),charset)){
			public void println(){
				super.print("\r\n");
			}
		};
		if(pass != null)
			out.write(String.format("PASS %s\r\n",pass));
		out.write(String.format("NICK %s\r\n",nick));
		out.write(String.format("USER %s %s %s :%s\r\n",nick));
	}
	
	public abstract void onLine(ParsedLine line);
	
	public void run(){
		StringBuilder buf = new StringBuilder();
		while(true){
			try{
				if(isInterrupted())
					break;
				if(rdr.ready()){
					buf.append(rdr.read());
					if(buf.toString().endsWith("\r\n"))
						new LineParseThread(buf.toString()).start();
				}
			}
			catch(IOException e){
				System.err.printf("[E] %s%n",e.getMessage());
			}
		}
	}
}

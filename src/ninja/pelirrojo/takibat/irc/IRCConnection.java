package ninja.pelirrojo.takibat.irc;

import java.io.*;
import java.net.Socket;

/**
 * Class of an IRC Connection.
 * 
 * @author takisan <takisan@pelirrojo.ninja>
 * @since INDEV-0
 * @version INDEV-0
 */
public abstract class IRCConnection extends Thread implements Runnable{
	public class MultiOutputStream extends OutputStream{
		private final OutputStream[] outs;
		public void write(int b) throws IOException{
			for(OutputStream o:outs){
				o.write(b);
			}
		}
		public MultiOutputStream(OutputStream[] outs){
			this.outs = outs;
		}
	}
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
			System.out.printf("[C] %s%n",line);
			if(this.line.startsWith("PING ")){
				// It's a Ping line, send a Pong Back
				try{
					System.out.println("[I] Sent Pong");
					out.write("PONG\r\n".getBytes() );
				}
				catch(IOException e){
					
				}
				ParsedLine.parse(this.line);
			}
		}
	}
	/** Reader in from the Connection. */
	private final InputStream in;
	/** Writer out to the Connection. */
	private final OutputStream out;
	
	public IRCConnection(final OutputStream out,final InputStream in,final String nick,final String pass,final int pushpings) throws IOException{
		this.out = new MultiOutputStream(new OutputStream[]{out,System.out});
		this.in  = in;
		if(pass != null)
			this.out.write(String.format("PASS %s\r\n",pass).getBytes());
		this.out.write(String.format("NICK %s\r\n",nick).getBytes());
		this.out.write(String.format("USER %s %s %s :%s\r\n",nick,nick,"localhost",nick).getBytes());
	}
	
	public abstract void onLine(ParsedLine line);
	
	public void run(){
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		while(true){
			try{
				if(isInterrupted())
					break;
				if(in.available() > 0){
					buf.write(in.read());
					if(buf.toString().endsWith("\r\n")){
						new LineParseThread(buf.toString()).start();
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
	
	public static void main(String[] args) throws Exception{
		System.out.println("[I] Init");
		Socket sock = new Socket("morgan.freenode.net",6667);
		IRCConnection conn = new IRCConnection(sock.getOutputStream(),sock.getInputStream(),"takibot",null,0){
			public void onLine(ParsedLine line){
				
			}
		};
		conn.start();
		conn.join();
		sock.close();
	}
}

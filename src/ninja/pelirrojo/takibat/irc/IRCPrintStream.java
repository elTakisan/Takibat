package ninja.pelirrojo.takibat.irc;

import java.io.*;

public class IRCPrintStream extends PrintStream{
	public IRCPrintStream(OutputStream out){
		super(out);
	}
	@Override
	public void println(){
		super.print("\r\n");
	}
	public void slashMe(String s){
		printf("\001ACTION %s\001%n",s);
	}
}

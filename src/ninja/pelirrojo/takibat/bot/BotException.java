package ninja.pelirrojo.takibat.bot;

public class BotException extends Exception{
	private static final long serialVersionUID = 1L;
	public BotException(){}
	public BotException(String message){
		super(message);
	}
	public BotException(Throwable cause){
		super(cause);
	}
	public BotException(String message,Throwable cause){
		super(message,cause);
	}
}

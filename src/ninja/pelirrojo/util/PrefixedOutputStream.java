package ninja.pelirrojo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * On a newline ({@code <CR><LF>}), write the prefix first and then the buffer.
 *  
 * @author takisan <takisan@pelirrojo.ninja>
 * @since Takibat INDEV-0
 * @version Takibat INDEV-0
 */
public class PrefixedOutputStream extends OutputStream{
	private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private final OutputStream out;
	private final byte[] prefix;
	public PrefixedOutputStream(OutputStream out,byte[] prefix){
		this.out = out;
		this.prefix = prefix;
	}
	public void write(int b) throws IOException{
		buffer.write(b);
		if(buffer.toString().endsWith("\r\n")){
			out.write(prefix);
			out.write(buffer.toByteArray());
		}
	}
}

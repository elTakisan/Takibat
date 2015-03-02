package ninja.pelirrojo.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class MultiOutputStream extends OutputStream implements Set<OutputStream>{
	private final List<OutputStream> outs;
	public void write(int b) throws IOException{
		for(OutputStream o:outs){
			o.write(b);
		}
	}
	public MultiOutputStream(OutputStream[] outs){
		this.outs = Arrays.asList(outs);
	}
	public MultiOutputStream(List<OutputStream> outs){
		this.outs = outs;
	}
	public boolean add(OutputStream e){
		synchronized(outs){
			return outs.add(e);
		}
	}
	public void add(int index,OutputStream element){
		synchronized(outs){
			outs.add(index,element);
		}
	}
	public boolean addAll(Collection<? extends OutputStream> c){
		synchronized(outs){
			return outs.addAll(c);
		}
	}
	public boolean addAll(int index,Collection<? extends OutputStream> c){
		synchronized(outs){
			return outs.addAll(index,c);
		}
	}
	public void clear(){
		synchronized(outs){
			outs.clear();
		}
	}
	public boolean contains(Object o){
		synchronized(outs){
			return outs.contains(o);
		}
	}
	public boolean containsAll(Collection<?> c){
		synchronized(outs){
			return outs.containsAll(c);
		}
	}
	public boolean isEmpty(){
		synchronized(outs){
			return outs.isEmpty();
		}
	}
	public Iterator<OutputStream> iterator(){
		synchronized(outs){
			return outs.iterator();
		}
	}
	public boolean remove(Object o){
		synchronized(outs){
			return outs.remove(o);
		}
	}
	public boolean removeAll(Collection<?> c){
		synchronized(outs){
			return outs.removeAll(c);
		}
	}
	public boolean retainAll(Collection<?> c){
		synchronized(outs){
			return outs.retainAll(c);
		}
	}
	public int size(){
		synchronized(outs){
			return outs.size();
		}
	}
	public Object[] toArray(){
		synchronized(outs){
			return outs.toArray();
		}
	}
	public <T> T[] toArray(T[] a){
		synchronized(outs){
			return outs.toArray(a);
		}
	}
}

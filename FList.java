package flist;

public abstract class FList<T> {
	public abstract T head();
	public abstract FList<T> tail();
	public abstract boolean isEmpty();
	
	public static <E> FList<E> empty(){
		
	}
	
	public static <E> int size(FList<E> xs) {
		if(xs.isEmpty()) {
			return 0;
		}else {
			return 1 + size(xs.tail());
		}
	}
	
}

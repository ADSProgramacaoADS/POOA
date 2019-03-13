package flist;

public abstract class FList<T> {
	public abstract T head();
	public abstract FList<T> tail();
	public abstract boolean isEmpty();
	
	public static <E> FList<E> empty(){
		return new FList<E>(){
			public E head() {
				throw new IllegalStateException();
			}
			public FList<E> tail() {
				throw new IllegalStateException();
			}
			public boolean isEmpty() {
				return true;
			}
		};
	}
	
	public static <E> FList<E> cons(E head, FList<E> tail){
		return new FList<E>() {
			public E head() {
				return head;
			}
			public FList<E> tail() {
				return tail;
			}
			public boolean isEmpty() {
				return false;
			}
		};
	}
	
	public static <E> int size(FList<E> xs) {
		if(xs.isEmpty()) {
			return 0;
		}
		return 1 + size(xs.tail());
	}
	
}

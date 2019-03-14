package flist;

public abstract class FList<T> {
	public final int size;
	public abstract T head();
	public abstract FList<T> tail();
	public abstract boolean isEmpty();
	public abstract boolean contains(T elmt);
	public abstract FList<T> concat(FList<T> ys);
	
	public FList(int size){
		this.size = size;
	}
	
	public static <E> FList<E> empty(){
		return new FList<E>(0){
			public E head() {
				throw new IllegalStateException();
			}
			public FList<E> tail() {
				throw new IllegalStateException();
			}
			public boolean isEmpty() {
				return true;
			}
			public boolean contains(E elmt) {
				return false;
			}
			public FList<E> concat(FList<E> ys){
				return ys;
			}
			public String toString() {
				return "";
			}
		};
	}
	
	public static <E> FList<E> cons(E head, FList<E> tail){
		return new FList<E>(1 + tail.size) {
			public E head() {
				return head;
			}
			public FList<E> tail() {
				return tail;
			}
			public boolean isEmpty() {
				return false;
			}
			public boolean contains(E elmt) {
				if(head.equals(elmt)) {
					return true;
				}
				return tail.contains(elmt);
			}
			public FList<E> concat(FList<E> ys){
				return cons(head, tail.concat(ys));
			}
			public String toString() {
				return head.toString() + " " + tail.toString();
			}
		};
	}
	
	public static <E> boolean contains(E elmt, FList<E> xs) {
		if(xs.isEmpty()) {
			return false;
		}
		if(xs.head().equals(elmt)) {
			return true;
		}
		return contains(elmt, xs.tail());
	}
	
	public static <E> FList<E> concat(FList<E> xs, FList<E> ys){
		if(xs.isEmpty()) {
			return ys;
		}
		return cons(xs.head(), concat(xs.tail(), ys));
	}
	
	public static FList<Integer> build(int start, int finish){
		if(start == finish) {
			return empty();
		}
		return cons(start, build(start+1, finish));
	}
	
	public static void main(String[] args) {
		FList<Integer> xs = build(10, 20);
		FList<Integer> ys = build(30, 40);
		FList<Integer> zs = concat(xs, ys);
		System.out.println(xs);
		System.out.println(ys);
		System.out.println(zs);
	}
	
}

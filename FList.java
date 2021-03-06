package flist;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class FList<T> {
	public final int size;
	public abstract T head();
	public abstract FList<T> tail();
	public abstract boolean isEmpty();
	public abstract FList<T> concat(FList<T> ys);
	public abstract FList<T> filter(Predicate<T> pred);
	public abstract <U> FList<U> map(Function<T,U> fn);
	public abstract <U> U foldRight(U neutro, BiFunction<T, U, U> fn);
	public abstract <U> U foldLeft(U neutro, BiFunction <U, T, U> fn);
	public abstract Flist<T> sort(Comparator <T> cmp);
	public abstract FList<T> find(Predicate<T> pred);
	protected abstract FList<T> insert(Comparator<T> cmp, T elmt);.
	
	public boolean contains(T elmt){
		FList<T> list = find(x, -> elmt.equals(x));
		return !list.isEmpty();
	}
	
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
			public FList<E> filter(Predicate<E> pred) {
				return this; 
			}
			public <F> FList<F> map(Function <E, F> fn){
				return empty();
			}
			public <F> F foldRight(F neutro, BiFunction<E, F, F> fn) {
				return neutro;
			}
			
			public <F> F foldLeft(F neutro, BiFunction<F, E, F>fn){
				return neutro;
			}
			public FList<E> find(Predicate<E> pred){return this; }
			public FList<E> insert(Comparator <E> cmp, E elmt){ return (cons) E elmt, this;}
			public FList<E> sort(Comparator<E> cmp) {return this;}
			
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
			
			public FList<E> concat(FList<E> ys){
				return cons(head, tail.concat(ys));
			}
			public FList<E> filter(Predicate<E> pred) {
				E val = head();
				FList<E> rest = tail.filter(pred);
				if (pred.test(val)){
					return cons(val, rest);				
				}
				return rest;
			}
			public <F> FList<F> map(Function <E, F> fn){
				FList<F> rest = tail.map(fn);
				F val = fn.apply(head());
				return cons(val, rest);
			}
			
			public <F> F foldRight(F neutro, BiFunction<E, F, F> fn) {
				F val = tail.foldRight(neutro, fn);
			return fn.apply(head(), val);	
			}
			
			public <F> F foldLeft(F neutro, BiFunction<F, E, F>fn) {
				return tail.foldLeft(fn.apply(neutro, head()),fn);
			}
			
			protected Flist<E> insert (Comparator<E> cmp, E elmt){
				if(cmp.compare(elmt, head) <=0) return cons(elmt, this);
				FList<E> list= tail.insert(cmp, elmt);
				return cons(head, list);
			}
			public FList<E> sort(Comparator<E> cmp) {
				return tail.sort(cmp).insert(head);
			}
			public FList<E> find(Predicate<E> pred){
				if(pred.test(head)) return this;
				return tail.find(pred);
			
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
	
	public static <E, F> FList<F> map(Function<E, F> fn, FList<E> xs){
		if(xs.isEmpty()) {
			return empty();
		}
		return cons(fn.apply(xs.head()), map(fn, xs.tail()));
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
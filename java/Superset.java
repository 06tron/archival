package mpc.structs;

import java.util.ArrayList;
import java.util.List;

public class Superset<E> {
	
	private E[] superContents;
	
	public Superset(E[] contents) {
		superContents = contents;
	}
	
	public static void main(String[] args) {
		String[] words = {"apple", "orange", "banana", "grapes", "kiwi", "mango", "peach", "melon", "pinapple", "lychee", "rambutan", "strawberry", "avocado"};
		Superset<String> fruits = new Superset<>(words);
		Superset<String>.Subset test = fruits.new Subset();
		test.add("apple");
		test.add("kiwi");
		test.add("orange");
		System.out.println(test.getContents());
	}
	
	public class Subset {
		
		private long subContents;
		
		public Subset() {
			subContents = 0L;
		}
		
		public void add(E item) {
			for (int i = 0; i < superContents.length; i++) {
				if (superContents[i].equals(item)) {
					subContents |= (long) Math.pow(2, i);
				}
			}
		}
		
		public List<E> getContents() {
			List<E> contents = new ArrayList<>();
			for (int i = 0; i < superContents.length; i++) {
				long powerOf2 = (long) Math.pow(2, i);
				if ((subContents & powerOf2) == powerOf2) {
					contents.add(superContents[i]);
				}
			}
			return contents;
		}
		
	}

}

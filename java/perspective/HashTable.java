package spect.math;

import java.util.LinkedList;
import java.util.List;

public class HashTable<E> {

	private List<E>[] table;
	
	@SuppressWarnings("unchecked")
	public HashTable(int length) {
		table = new List[length];
		for (int i = 0; i < length; i++) {
			table[i] = new LinkedList<>();
		}
	}
	
	public void add(E item) {
		table[item.hashCode() % table.length].add(item);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (List<E> list : table) {
			sb.append(list  + ", " + count++ + "\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		//HashTable<Math3Ints> test = new HashTable<>(100);
		/*for (int i = 0; i < 100; i++) {
			int max = 10;
			int a = (int) (Math.random() * max);
			int b = (int) (Math.random() * max);
			int c = (int) (Math.random() * max);
			test.add(new Math3Ints(a, b, c));
		}
		
		int digitCap = 16;
		for (int a = 0; a < digitCap; a++) {
			for (int b = 0; b < digitCap; b++) {
				for (int c = 0; c < digitCap; c++) {
					if (true) {
						if (a + b + c < digitCap) {
							test.add(new Math3Ints(a, b, c));
						}
					}
				}
			}
		}*/
		
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				System.out.print(String.format("%5d", h1(x,y)));
			}
			System.out.println();
		}
	}
	
//	private static class Math3Ints {
//		
//		private int a, b, c;
//		
//		public Math3Ints(int a, int b, int c) {
//			this.a = a;
//			this.b = b;
//			this.c = c;
//		}
//		
//		@Override
//		public int hashCode() {
//			int aSqSum = a * (a + 1) * (2 * a + 1);
//			int bSqSum = b * (b + 1) * (2 * b + 1);
//			int cSqSum = c * (c + 1) * (2 * c + 1);
//			int aSum = (a * (a + 1)) * (2 * b + 2 * c + 1);
//			int bSum = (b * (b + 1)) * (2 * a + 2 * c + 1);
//			int cSum = (c + 1) * (2 * a * (c + 2) + (3 + 2 * b) * c);
//			return ((aSqSum + bSqSum + cSqSum + 3 * (aSum + bSum + cSum)) / 12) + c * (b * (a + 1) + 1);
//		}
//
//		@Override
//		public String toString() {
//			return a + " "+ b + " " + c;
//		}
//		
//	}
	
	public static int h1(int x, int y) {
		if (x >= y) {
			return (x * x) + y;
		} else {
			return (y * y) + (2 * y) - x;
		}
	}
	
	public static int h2(int x, int y) {
		return ((x * (x + 1) + y * (y + 1)) / 2) + (x * (y + 1));
	}
	
}

package spect.math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph<E> {
	
	public static class IndexPair {

		private int i, j;
		
		public IndexPair(int i, int j) {
			if (i >= 0 && j >= 0) {
				this.i = Math.min(i, j);
				this.j = Math.max(i, j);
			} else {
				throw new IllegalArgumentException();
			}
		}
		
		public int i() {
			return i;
		}
		
		public int j() {
			return j;
		}
		
		@Override
		public int hashCode() {
			return ((j * (j + 1)) / 2) + i;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj instanceof IndexPair) {
				IndexPair iP = (IndexPair) obj;
				return i == iP.i && j == iP.j;
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return i + "-" + j;
		}
		
	}
	
	public List<E> vrts;
	
	private Set<IndexPair> knex;
	
	public Graph() {
		vrts = new ArrayList<>();
		knex = new HashSet<>();
	}
	
	public void connect(int i, int j) {
		knex.add(new IndexPair(i, j));
	}
	
	public void connect(E a, E b) {
		connect(vrts.indexOf(a), vrts.indexOf(b));
	}
	
	public Set<IndexPair> getIPs() {
		return new HashSet<IndexPair>(knex);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < vrts.size(); i++) {
			sb.append(", " + i + "=" + vrts.get(i));
		}
		return "vrts: [" + sb.substring(2) + "]\nknex: " + knex;
	}

}

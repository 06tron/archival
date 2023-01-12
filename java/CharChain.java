package mpc.chain;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CharChain implements java.util.List<Character> {

	private java.util.List<CharChain> chain;
	
	private int size;

	protected CharChain() { // used by CharLink
		chain = null;
		size = 1;
	}

	public CharChain(String initial) {
		chain = new java.util.ArrayList<>();
		size = initial.length();
		for (int i = 0; i < size; i++) {
			chain.add(new CharLink(initial.charAt(i)));
		}
	}
	
	@Override
	public int size() {
		return size;
	}

	protected void buildString(StringBuilder sb) {
		for (CharChain c : chain) {
			c.buildString(sb);
		}
	}
	
	public void add(CharChain next) {
		chain.add(next);
		size += next.size;
	}

	@Override
	public Character get(int target) {
		if (target < 0 || target >= size) {
			throw new IllegalArgumentException();
		}
		int index, count = 0;
		for (index = 0; count < target; index++) {
			
			count += chain.get(index).size;
		}
		if (count > target) { // overshoot
			index--;
			count -= chain.get(index).size;
		}
		return chain.get(index).get(target - count);
	}
	
	public void add(int target, CharChain insert) {
		if (target < 0 || target > size) {
			throw new IllegalArgumentException();
		} else if (target == size) {
			add(insert);
		} else {
			int index, count = 0;
			for (index = 0; count < target; index++) {
				count += chain.get(index).size;
			}
			if (count == target) {
				chain.add(index, insert);
			} else { // overshoot
				count -= chain.get(index - 1).size;
				chain.get(index - 1).add(target - count, insert);
			}
			size += insert.size;
		}
	}

	@Override
	public Character remove(int target) {
		if (target < 0 || target >= size) {
			throw new IllegalArgumentException();
		}
		int index, count = 0;
		for (index = 0; count < target; index++) {
			count += chain.get(index).size;
		}
		if (count > target) { // overshoot
			index--;
			count -= chain.get(index).size;
		} else if (chain.get(index) instanceof CharLink) {
			chain.remove(index);
		}
		size--;
		return chain.get(index).remove(target - count);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		buildString(sb);
		return sb.toString();
	}

}

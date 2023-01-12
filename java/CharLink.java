package mpc.chain;

public class CharLink extends CharChain {

	private char link;

	public CharLink(char initial) {
		super();
		link = initial;
	}

	@Override
	public void add(CharChain next) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, CharChain insert) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Character get(int target) {
		if (target != 0) {
			throw new IllegalArgumentException();
		}
		return link;
	}

	@Override
	public Character remove(int target) {
		return get(target);
	}

	@Override
	protected void buildString(StringBuilder sb) {
		sb.append(link);
	}
	
	@Override
	public String toString() {
		return Character.toString(link);
	}

}

package spect;

public class IndexConnection {

	private int i;
	
	private int j;
	
	public IndexConnection(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public int i() {
		return i;
	}
	
	public int j() {
		return j;
	}

	@Override
	public String toString() {
		return i + "-" + j;
	}
	
}

package mpc;

import java.util.ArrayList;
import java.util.Collections;

/**
 * CLASS, represents a square grid, where each site is either open or closed.
 * Sites are opened randomly, and at some point, the grid will percolate. This
 * means that there will be a path through open sites from the top of the grid
 * to the bottom of the grid
 * 
 * @author mrichardson
 */
public class Percolation {

	/**
	 * CLASS, used to represent one space in a percolation grid (which can only be
	 * open or closed)
	 */
	public static class Site {

		/**
		 * VARIABLE, true if this site is open, false if this site is closed
		 */
		private boolean isOpen;

		/**
		 * CONSTRUCTOR, creates a percolation grid site that is open or closed
		 * 
		 * @param isOpen this site will be open if true, closed otherwise
		 */
		public Site(boolean isOpen) {
			this.isOpen = isOpen;
		}

		/**
		 * ACCESSOR, returns the isOpen boolean variable
		 * 
		 * @return True if this site is open, false if this site is closed
		 */
		public boolean isOpen() {
			return isOpen;
		}

		/**
		 * MUTATOR, opens this site by setting isOpen to true
		 */
		public void open() {
			isOpen = true;
		}

		/**
		 * MUTATOR, closes this site by setting isOpen to false
		 */
		public void close() {
			isOpen = false;
		}

	}

	/**
	 * FINAL VARIABLE, stores the side length of this square percolation grid
	 */
	private final int GSL;

	/**
	 * CONSTANTS, represent the top and bottom of this percolation grid
	 */
	private static final Site TOP = new Site(true), BOTTOM = new Site(true);

	/**
	 * VARIABLE, a list of all the sites in this percolation grid
	 */
	private ArrayList<Site> sites;

	/**
	 * VARIABLE, a list of all the indices of the sites in this percolation grid.
	 * The list is shuffled so the sites can be iterated over in a random order
	 */
	private ArrayList<Integer> openingOrder;

	/**
	 * VARIABLE, the openingOrder index. Used to move through the opening order one
	 * index at a time
	 */
	private int ooI;

	/**
	 * VARIABLE, the union find object to represent the connections between open
	 * sites in this percolation gird.
	 */
	private UnionFind<Site> unionFind;

	/**
	 * CONSTRUCTOR, sets up this percolation grid. Shuffles the site's opening
	 * order, and initializes all of the sites to be closed. The top and bottom,
	 * which are always open, are added to the sites list before a union-find object
	 * is created using all the sites
	 * 
	 * @param gridSideLength The side length of this square percolation grid
	 */
	public Percolation(int gridSideLength) {
		GSL = gridSideLength;
		sites = new ArrayList<>();
		openingOrder = new ArrayList<>();
		ooI = 0;
		int listLength = GSL * GSL;
		for (int i = 0; i < listLength; i++) {
			sites.add(new Site(false));
			openingOrder.add(i);
		}
		Collections.shuffle(openingOrder);
		sites.add(TOP);
		sites.add(BOTTOM);
		unionFind = new UnionFind<>(sites);
	}

	/**
	 * MUTATOR, opens the next site in the opening order, and connects the newly
	 * opened site to any open sites adjacent to it
	 */
	public void openRandomSite() {
		int siteI = openingOrder.get(ooI++);
		Site site = sites.get(siteI);
		site.open();
		if (rowOf(siteI) != 0 && sites.get(siteI - GSL).isOpen()) {
			unionFind.union(site, sites.get(siteI - GSL));
		} // site above is open
		if (rowOf(siteI) != GSL - 1 && sites.get(siteI + GSL).isOpen()) {
			unionFind.union(site, sites.get(siteI + GSL));
		} // site below is open
		if (columnOf(siteI) != 0 && sites.get(siteI - 1).isOpen()) {
			unionFind.union(site, sites.get(siteI - 1));
		} // site to the left is open
		if (columnOf(siteI) != GSL - 1 && sites.get(siteI + 1).isOpen()) {
			unionFind.union(site, sites.get(siteI + 1));
		} // site to the right is open
		if (rowOf(siteI) == 0) {
			unionFind.union(site, TOP);
		} // this site is on top
		if (rowOf(siteI) == GSL - 1) {
			unionFind.union(site, BOTTOM);
		} // this site is on bottom
	}

	/**
	 * ACCESSOR, tells whether or not this percolation grid can percolate. This
	 * means that there is a path through open sites from the top to the bottom
	 * 
	 * @return True if this grid does percolate, false otherwise
	 */
	public boolean canPercolate() {
		return unionFind.connected(TOP, BOTTOM);
	}

	/**
	 * MUTATOR, resets this percolation grid. The opening order is reshuffled, all
	 * the grid sites are closed, and the union find is reset as well
	 */
	public void resetSites() {
		unionFind.eraseConnections();
		int arrayLength = GSL * GSL;
		for (int i = 0; i < arrayLength; i++) {
			sites.get(i).close();
		}
		Collections.shuffle(openingOrder);
		ooI = 0;
	}

	/**
	 * CALCULATION, finds the average number of times a random site in this
	 * percolation grid has to be opened until there is a path from the top to the
	 * bottom
	 * 
	 * @param numTests The number of times to count the number of openings it takes
	 *                 for this grid to percolate
	 * @return The average number of openings for this percolation grid to percolate
	 */
	public double averageOpeningsTillPercolation(int numTests) {
		int sum = 0;
		for (int i = 0; i < numTests; i++) {
			resetSites();
			int countOpenings = 0;
			while (!canPercolate()) {
				openRandomSite();
				countOpenings++;
			}
			sum += countOpenings;
		}
		return sum / (double) numTests;
	}

	/**
	 * ACCESSOR, given a row and column of this percolation grid, finds the index of
	 * the indicated site
	 * 
	 * @param r A row in this percolation grid
	 * @param c A column in this percolation grid
	 * @return The index of the site indicated by the given row and column
	 */
	private int indexOf(int r, int c) {
		return (r * GSL) + c;
	}

	/**
	 * ACCESSOR, given an index of this percolation grid, finds the row that the
	 * indicated site is in
	 * 
	 * @param i An index of this percolation grid
	 * @return The row of the site indicated by the given index
	 */
	private int rowOf(int i) {
		return i / GSL;
	}

	/**
	 * ACCESSOR, given an index of this percolation grid, finds the column that the
	 * indicated site is in
	 * 
	 * @param i An index of this percolation grid
	 * @return The column of the site indicated by the given index
	 */
	private int columnOf(int i) {
		return i % GSL;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean foundLastOpened = false;
		for (int r = 0; r < GSL; r++) {
			for (int c = 0; c < GSL; c++) {
				if (!foundLastOpened && indexOf(r, c) == openingOrder.get(ooI - 1)) {
					sb.append("-X-");
					foundLastOpened = true;
				} else if (sites.get(indexOf(r, c)).isOpen()) {
					sb.append("-|-");
				} else {
					sb.append("   ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}

package cartograph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents a gridded collection of points in space
 * TODO: Should use the builder pattern
 */
public class Cartograph {
	
	private int rows; // the number of rows in the map grid
	private int cols; // the number of columns in the map grid
	private Datum[][] map; // the map grid
	private Set<Point> known; // the known points on the map
	//TODO: make a Neighborhood k-d tree that does the known Set and nearest neighbor functionality
	
	private int peaks; // the number of local maxima present
	private int troughs; // the number of local minima present
	private double maxHeight; // the height of the peaks
	private double minHeight; // the depth of the troughs
	
	private int k; // the number of nearest neighbors to use in elevation assignment

	private final int ROWS = 20;
	private final int COLS = 20;

	private final int PEAKS = 5;
	private final int TROUGHS = 5;

	private final double MAX_HEIGHT = 100.0;
	private final double MIN_HEIGHT = 0.0;

	private final int K = 4; // nearest neighbors
	
	public Cartograph() {
		this.setRows(ROWS);
		this.setCols(COLS);
		this.map = new Datum[rows][cols];
		this.known = new TreeSet<Point>();
		
		this.setPeaks(PEAKS);
		this.setTroughs(TROUGHS);
		this.setMaxHeight(MAX_HEIGHT);
		this.setMinHeight(MIN_HEIGHT);
		
		this.generateMap();

	}
	
	public void setRows(int r) {
		this.rows = r;
	}
	
	public void setCols(int c) {
		this.cols = c;
	}
	
	/**
	 * Creates an empty map grid to populate
	 */
	public void createMapBase() {
		this.map = new Datum[rows][cols];
	}
	
	/**
	 * Creates an empty known set
	 */
	public void createKnownSet() {
		this.known = new TreeSet<Point>();
	}
	
	public void setPeaks(int p) {
		this.peaks = p;
	}
	
	public void setTroughs(int t) {
		this.troughs = t;
	}
	
	public void setMaxHeight(double max) {
		this.maxHeight = max;
	}
	
	public void setMinHeight(double min) {
		this.minHeight = min;
	}
	
	public void setK(int k) {
		this.k = k;
	}
	
	/**
	 * TODO: needs a better name
	 * @return A random ordering of all points in the cartograph
	 * Each point is visited exactly once
	 */
	private List<Point> generateRandomQueue() {
		List<Point> queue = new ArrayList<Point>(this.rows * this.cols);
		for (int r = 0; r < this.rows; r += 1) {
			for (int c = 0; c < this.cols; c += 1) {
				queue.add(new Point(c, r)); // points are done x, y
			}
		}
		FisherYates.shuffle(queue);
		return queue;
	}

	/**
	 * Generates elevation data for every point in the map
	 */
	public void generateMap() {
		List<Point> randomQueue = generateRandomQueue();
		this.generatePeaks(randomQueue);
		this.generateTroughs(randomQueue);

		Point p;
		while (!randomQueue.isEmpty()) { // while we still have points to get
			p = randomQueue.remove(0);
			double elev = this.calcElev(p);
			Datum d = new Datum(p, elev);
			this.plot(d);
		}
	}

	private void generatePeaks(List<Point> randomQueue) {
		this.generatePoints(randomQueue, this.PEAKS, this.MAX_HEIGHT);
	}

	private void generateTroughs(List<Point> randomQueue) {
		this.generatePoints(randomQueue, this.TROUGHS, this.MIN_HEIGHT);
	}

	private void generatePoints(List<Point> randomQueue, int count, double elev) {
		for (int i = 0; i < count; i += 1) {
			Point p = randomQueue.remove(0);
			Datum d = new Datum(p, elev);;
			this.plot(d);
		}
	}

	/**
	 * Returns true is a Datum exists for the given Point
	 * false otherwise
	 */
	private boolean isKnown(Point p) {
		return this.known.contains(p);
	}

	/**
	 * Records unknown Point p on the map
	 */
	private void plot(Datum d) {
		if (this.isKnown(d.getPoint())) {
			throw new IllegalArgumentException("The datum d is already known");
		}
		this.map[d.getY()][d.getX()] = d;
		this.known.add(d.getPoint());
	}

	/**
	 * Fetches the Datum at the given coordinates
	 * Returns null if no such Datum exists
	 */
	private Datum getDatum(int x, int y) {
		return this.map[y][x];
	}

	/**
	 * Fetches the Datum at the given point
	 * Returns null if no such Datum exists
	 */
	private Datum getDatum(Point p) {
		return this.getDatum(p.getX(), p.getY());
	}

	/**
	 * Calculates the proper elevation for the given point
	 * Based on some algorithm on the existing cartograph
	 */
	public double calcElev(Point p) {
		Map<Datum, Double> neighbors = this.kNearestNeighbors(p, this.K);
		return this.meanDistance(p, neighbors);
	}

	// TODO: make nearestneighbormaps a self-contained class
	// TODO: use kd-trees as a backing for the cartograph to speed this up
	/**
	 * Finds the k nearest neighbors to the given Point
	 * @param focus
	 * @param k
	 * @return A map of the Datums to the distances from said Datum to the given Point
	 */
	private Map<Datum, Double> kNearestNeighbors(Point focus, int k) {
		Map<Datum, Double> nearest = new TreeMap<Datum, Double>();
		for (Point p : this.known) {
			double distance = focus.distanceTo(p);
			nearest.put(this.getDatum(p), distance);
			if (nearest.keySet().size() > k) {
				this.removeFarthest(nearest);
			}
		}
		return nearest;
	}

	private void removeFarthest(Map<Datum, Double> nearest) {
		double max = -1.0;
		Datum maxKey = null;
		for (Datum key : nearest.keySet()) {
			double distance = nearest.get(key);
			if (distance > max) {
				max = distance;
				maxKey = key;
			}
		}
		nearest.remove(maxKey);
	}

	/**
	 * Calculates the distance-weighted mean elevation in a neighbormap
	 */
	public double meanDistance(Point p, Map<Datum, Double> neighbors) {
		double weightedElev = 0.0;
		double totalWeight = 0.0;
		for (Datum key : neighbors.keySet()) {
			double weight = 1.0 / neighbors.get(key);;
			weightedElev += key.getElev() * weight;
			totalWeight += weight;
		}
		return weightedElev / totalWeight;
	}

	/**
	 * Returns a string representation of the cartograph
	 * Tab-delimited matrix of elevation values
	 */
	public String toString() {
		String rep = "";
		for (Datum[] row : map) { // for each row
			rep = rep + stringElev(row[0]);
			for (int i = 1; i < row.length; i += 1) { // for each column in each row
				rep = rep + "\t" + stringElev(row[i]);
			}
			rep = rep + "\n";
		}
		return rep;
	}

	/**
	 * Returns a string representation of the elevation of the given Datum
	 * Maximum length of 5 characters
	 */
	private static String stringElev(Datum d) {
		if (d == null) {
			return "---";
		} else {
			String rep = "" + d.getElev();
			if (rep.length() > 5) { // max length of 5
				rep = rep.substring(0, 5);
			}
			return rep;
		}
	}
}


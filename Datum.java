package cartograph;

/*
 * Represents metadata about an immutable point in space
 */
public class Datum implements Comparable<Datum> {

	private final Point p; // the point in space in question
	private final double elev; // the elevation of the point in m

	public Datum(Point p, double elev) {
		this.p = new Point(p.getX(), p.getY()); // copy-in
		this.elev = elev;
	} 

	public Datum(int x, int y, double elev) {
		this(new Point(x, y), elev);
	}

	/*
	 * Returns the Point backing this Datum
	 */
	public Point getPoint() {
		return new Point(this.p.getX(), this.p.getY()); // copy-out
	}

	/*
	 * Returns the x-coordinate of this Datum
	 */
	public int getX() {
		return this.getPoint().getX();
	}

	/*
	 * Returns the y-coordinate of this Datum
	 */
	public int getY() {
		return this.getPoint().getY();
	}

	/*
	 * Returns the elevation of this Datum
	 */
	public double getElev() {
		return this.elev;
	}

	/*
	 * Equality for Datum objects is defined solely by their Points
	 */
	public boolean isEqual(Object o) {
		if (!(o instanceof Datum)) {
			return false;
		}
		Datum other = (Datum) o;
		return this.getPoint().isEqual(other.getPoint());
	}

	/*
	 * Comparison for Datum objects is defined solely by their Points
	 */
	public int compareTo(Datum other) {
		return this.getPoint().compareTo(other.getPoint());
	}
}



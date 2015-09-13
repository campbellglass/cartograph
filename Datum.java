/*
 * Represents metadata about an immutable point in space
 */

public class Datum implements Comparable<Datum> {

private final Point p;
private final double elev;

public Datum(Point p, double elev) {
this.p = new Point(p.getX(), p.getY()); // copy-in
this.elev = elev;
} 

public Datum(int x, int y, double elev) {
this(new Point(x, y), elev);
}

public Point getPoint() {
return new Point(this.p.getX(), this.p.getY()); // copy-out
}

public int getX() {
return this.getPoint().getX();
}

public int getY() {
return this.getPoint().getY();
}

public double getElev() {
return this.elev;
}

public boolean isEqual(Object o) {
if (!(o instanceof Datum)) {
return false;
}
Datum other = (Datum) o;
return this.getPoint().isEqual(other.getPoint());
}

public int compareTo(Datum other) {
return this.getPoint().compareTo(other.getPoint());
}

}



/*
 * Represents an immutable point in space
 */

public class Point implements Comparable<Point> {

private final int x;
private final int y;

public Point(int x, int y) {
this.x = x;
this.y = y;
}

public int getX() {
return this.x;
}

public int getY() {
return this.y;
}

public boolean isEqual(Object o) {
if (!(o instanceof Point)) {
return false;
}
Point other = (Point) o;
return this.x == other.x && this.y == other.y;
}

public int compareTo(Point other) {
if (this.x != other.x) {
return other.x - this.x;
}
return other.y - this.y;
}

public double distanceTo(Point other) {
double dX = Math.pow(this.getX() - other.getX(), 2.0);
double dY = Math.pow(this.getY() - other.getY(), 2.0);
return Math.pow(dX + dY, 0.5);
}

}



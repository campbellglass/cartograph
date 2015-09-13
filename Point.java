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

}



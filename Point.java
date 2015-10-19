package cartograph;

/**
 * Represents an immutable point in 2-dimensional space
 */
public class Point implements Comparable<Point> {
  
  // 2-d coordinates of this point
  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-coordinate of this point
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y-coordinate of this point
   */
  public int getY() {
    return this.y;
  }

  /**
   * Returns true if this point is equal to the other point.
   * Equality for points is defined as occupying the same point in space.
   */
  public boolean isEqual(Object o) {
    if (!(o instanceof Point)) {
      return false;
    }
    Point other = (Point) o;
    return this.x == other.x && this.y == other.y;
  }

  /**
   * Returns negative if this point is to the right of other,
   * or if this point is equally right but below other
   * Returns positive if this point is to the left of other,
   * or if this point is equally right but above other.
   * Returns 0 if this point is equal to other
   */
  public int compareTo(Point other) {
    if (this.x != other.x) {
      return other.x - this.x;
    }
    return other.y - this.y;
  }

  /**
   * Returns the pythagorean distance from this point to the other point
   */
  public double distanceTo(Point other) {
    double dX = Math.pow(this.getX() - other.getX(), 2.0);
    double dY = Math.pow(this.getY() - other.getY(), 2.0);
    return Math.pow(dX + dY, 0.5);
  }
}



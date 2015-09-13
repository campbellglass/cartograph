/*
 * Represents a gridded collection of points in space
 */

import java.util.*;

public class Cartograph {

private int rows;
private int cols;
private Datum[][] map;
private Set<Point> known;

private final int ROWS = 10;
private final int COLS = 10;

private final int PEAKS = 5;
private final int TROUGHS = 5;

private final double MAX_HEIGHT = 100.0;
private final double MIN_HEIGHT = 0.0;

private final Random R = new Random();

public Cartograph() {
this.rows = this.ROWS;
this.cols = this.COLS;
this.map = new Datum[rows][cols];
this.known = new TreeSet<Point>();

this.generatePeaks();
this.generateTroughs();

this.generateMap();

}

private void generatePeaks() {
this.generatePoints(this.PEAKS, this.MAX_HEIGHT);
}

private void generateTroughs() {
this.generatePoints(this.TROUGHS, this.MIN_HEIGHT);
}

private void generatePoints(int count, double elev) {
for (int i = 0; i < count; i += 1) {
Point p = this.unknownLocation();
Datum d = new Datum(p, elev);;
this.plot(d);
}
}

/*
 * Returns a pair of random x/y coordinates
 */
private Point randomLocation() {
int x = this.R.nextInt(this.cols);
int y = this.R.nextInt(this.rows);
return new Point(x, y);
}

/*
 * Gets an unknown location on the map
 */
private Point unknownLocation() {
if (this.known.size() >= this.rows * this.cols) {
throw new IllegalStateException("No unknown points exist");
}
Point p = this.randomLocation();
// THIS IS HIGHLY INEFFICIENT AND NEEDS TO BE REPLACED
while (this.isKnown(p)) {
p = this.randomLocation();
}
return p;
}

private boolean isKnown(Point p) {
return this.known.contains(p);
}

/*
 * Records unknown point p on the map
 */
private void plot(Datum d) {
if (this.isKnown(d.getPoint())) {
throw new IllegalArgumentException("The datum d is already known");
}
this.map[d.getY()][d.getX()] = d;
this.known.add(d.getPoint());
}

/*
 * Fetches the Datum at the given coordinates
 */
private Datum getDatum(int x, int y) {
return this.map[y][x];
}

private Datum getDatum(Point p) {
return this.getDatum(p.getX(), p.getY());
}

/*
 * Fills in the map using nearest neighbor
 */
public void generateMap() {
Point p;

while (known.size() < this.rows * this.cols) { // while we still have points to get
p = this.randomLocation();;
while (known.contains(p)) { // get new points until we find a new one
// THIS IS A HIGHLY INEFFICIENT PLACEHOLDER
p = randomLocation();;
}

double elev = this.assignElev(p.getX(), p.getY());
Datum d = new Datum(p, elev);
this.plot(d);

}

}

public double assignElev(int x, int y) {
return 0.5;
}

/*
 * Prints a string representation of the cartograph
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

private static String stringElev(Datum d) {
if (d == null) {
return "---";
} else {
return "" + d.getElev();
}
}


}

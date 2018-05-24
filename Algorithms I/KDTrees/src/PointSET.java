import java.util.ArrayList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	private TreeSet<Point2D> set;
	
	// construct an empty set of points
	public PointSET() {
		set = new TreeSet<Point2D>();
	}

	// is the set empty?
	public boolean isEmpty() {
		return set.isEmpty();
	}

	// number of points in the set
	public int size() {
		return set.size();
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) { throw new IllegalArgumentException(); }
		set.add(p);
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null) { throw new IllegalArgumentException(); }
		return set.contains(p);
	}

	// draw all points to standard draw
	public void draw() {
		for(Point2D p : set) {
			p.draw();
		}
		
	}

	// all points that are inside the rectangle (or on the boundary)
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) { throw new IllegalArgumentException(); }
		ArrayList<Point2D> range = new ArrayList<Point2D>();
		for (Point2D p : set) {
			if (rect.contains(p)) {
				range.add(p);
			}
		}
		return range;
	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (p == null) { throw new IllegalArgumentException(); }
		double minDist = 2.0d, dist;
		Point2D nearest = null;
		
		for (Point2D p2 : set) {
			dist = p.distanceTo(p2);
			if (dist < minDist) {
				minDist = dist;
				nearest = p2;
			}
		}
		return nearest;
	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {

	}
}

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
	private LineSegment[] lineSegments;

	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < points.length; i++) {
			if ( points[i] == null ) { throw new IllegalArgumentException(); } 
		}
		LinkedList<LineSegment> linesList = new LinkedList<LineSegment>();
		Point[] pointsCopy;
		for (int i = 0; i < points.length - 1; i++) {
			Point p = points[i];
			pointsCopy = Arrays.copyOf(points, points.length);
			
			//only sort from the current i onwards - avoids having to check for duplicated line segments and performs better.
			Arrays.sort(pointsCopy, i + 1, points.length, p.slopeOrder());

			LinkedList<Point> ls;
			
			for (int j = i + 1; j < points.length - 1; j++) {
				//checks for null or duplicates points - they should be adjacent after sorting
				if (p.compareTo(pointsCopy[j]) == 0
						|| p.compareTo(pointsCopy[j + 1]) == 0 || pointsCopy[j].compareTo(pointsCopy[j + 1]) == 0) {
					throw new IllegalArgumentException();
				}
				ls = new LinkedList<Point>();
				ls.add(p);

				while (j + 1 < points.length && p.slopeTo(pointsCopy[j]) == p.slopeTo(pointsCopy[j + 1])) {
					ls.add(pointsCopy[j]);
					j++;
				}
				if (ls.size() >= 3) {
					ls.add(pointsCopy[j]);
					Point[] ps = ls.toArray(new Point[0]);
					Arrays.sort(ps);
					linesList.add(new LineSegment(ps[0], ps[ps.length - 1]));

				}
			}
			
			

		}
		lineSegments = linesList.toArray(new LineSegment[0]);
	}

	// the number of line segments
	public int numberOfSegments() {
		return lineSegments.length;
	}

	// the line segments
	public LineSegment[] segments() {
		return Arrays.copyOf(lineSegments, lineSegments.length);
	}
}
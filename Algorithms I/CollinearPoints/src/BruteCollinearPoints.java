import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {
	private LineSegment[] lineSegments;
	
	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		if (points == null) { throw new IllegalArgumentException(); }
		//immutable type - copy the input
		Point[] pointSet = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			if ( points[i] == null ) { throw new IllegalArgumentException(); } 
			pointSet[i] = points[i];
		}
		int n = pointSet.length;
		LinkedList<Point[]> list = new LinkedList<Point[]>();
		LinkedList<LineSegment> segList = new LinkedList<LineSegment>();
		double slope1, slope2, slope3;
		//sort to ensure deterministic behaviour
		Arrays.sort(pointSet);
		for (int i = 0; i < n; i++) {	
			Point p = pointSet[i];
			if (p == null) { throw new IllegalArgumentException(); }
			
			for (int j = i + 1; j < n; j++) {
				Point q = pointSet[j];
				if (q == null || p.compareTo(q) == 0) { throw new IllegalArgumentException(); }
				slope1 = p.slopeTo(q);
				
				for (int k = j + 1; k < n; k++) {
					Point r = pointSet[k];
					if (r == null || p.compareTo(r) == 0) { throw new IllegalArgumentException(); }
					slope2 = p.slopeTo(r);
					if (slope1 != slope2) continue;
					
					for (int l = k + 1; l < n; l++) {
						Point s = pointSet[l];
						if (s == null || p.compareTo(s) == 0) { throw new IllegalArgumentException(); }
						slope3 = p.slopeTo(s);
						
						if (slope1 == slope3) {
							Point[] thisPair = new Point[2];
							thisPair[0] = p;
							thisPair[1] = s;
							boolean dup = false;
							for (Point[] pair : list) {
								if (pair[0].compareTo(thisPair[0]) == 0 && pair[1].compareTo(thisPair[1]) == 0) {
									dup = true;
								}
							}
							if (!dup) {
								list.add(thisPair);
								segList.add(new LineSegment(p, s));
							}
							
						}
					}
				}
			}
		}

		lineSegments = segList.toArray(new LineSegment[0]);
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
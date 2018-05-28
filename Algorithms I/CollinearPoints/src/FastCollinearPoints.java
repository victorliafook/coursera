import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
	private LineSegment[] lineSegments;

	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		//immutable type - copy the input
		Point[] pointSet = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			if ( points[i] == null ) { throw new IllegalArgumentException(); } 
			pointSet[i] = points[i];
		}
		LinkedList<LineSegment> segmentsList = new LinkedList<LineSegment>();
		LinkedList<SeenLine> linesList = new LinkedList<SeenLine>();
		Point[] pointSetCopy;
		for (int i = 0; i < pointSet.length - 1; i++) {
			Point p = pointSet[i];
			pointSetCopy = Arrays.copyOf(pointSet, pointSet.length);
			
			//only sort from the current i onwards - avoids having to check for duplicated line segments and performs better.
			Arrays.sort(pointSetCopy, i + 1, pointSet.length, p.slopeOrder());
			//checks for duplicates points - they should be adjacent after sorting
			if (p.compareTo(pointSetCopy[i+1]) == 0) {
				throw new IllegalArgumentException();
			}
			LinkedList<Point> ls;
			
			for (int j = i + 1; j < pointSet.length; j++) {
				
				ls = new LinkedList<Point>();
				Double lastSlope = null, currentSlope = null;
				//ls.add(p);

				while (j < pointSet.length) {
					currentSlope = p.slopeTo(pointSetCopy[j]);
					if (lastSlope == null || !lastSlope.equals(currentSlope)) {
						if (ls.size() > 3) {
							//ls.add(pointSetCopy[j]);
							boolean contains = false;
							for (SeenLine s : linesList) {
								if(lastSlope.equals(p.slopeTo(s.point))) {	
									contains = true;
								}
							}
							if (!contains) {
								Point[] ps = ls.toArray(new Point[0]);
								Arrays.sort(ps);
								linesList.add(new SeenLine(lastSlope, p));
								segmentsList.add(new LineSegment(ps[0], ps[ps.length - 1]));
							}
						}
						ls = new LinkedList<Point>();
						ls.add(p);
						//ls.add(pointSetCopy[j]);
					}
					if (j != pointSet.length - 1)
						lastSlope = currentSlope;
					ls.add(pointSetCopy[j]);	
					j++;
				}
				if (ls.size() > 3 ) {
					boolean contains = false;
					for (SeenLine s : linesList) {
						if(lastSlope.equals(p.slopeTo(s.point))) {	
							contains = true;
						}
					}
					if (!contains) {
						//ls.add(pointSetCopy[j]);
						Point[] ps = ls.toArray(new Point[0]);
						Arrays.sort(ps);
						linesList.add(new SeenLine(lastSlope, p));
						segmentsList.add(new LineSegment(ps[0], ps[ps.length - 1]));
					}
				}
				
			}
			
		}
		linesList = null;
		lineSegments = segmentsList.toArray(new LineSegment[0]);
	}

	// the number of line segments
	public int numberOfSegments() {
		return lineSegments.length;
	}

	// the line segments
	public LineSegment[] segments() {
		return Arrays.copyOf(lineSegments, lineSegments.length);
	}
	
	private class SeenLine{
		public Double slope;
		public Point point;
		
		public SeenLine(Double slope, Point point) {
			this.slope = slope;
			this.point = point;
		}
		
		
	}
}
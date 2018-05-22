import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private Node root;
	private int n;

	// construct an empty set of points
	public KdTree() {
		n = 0;
	}

	// is the set empty?
	public boolean isEmpty() {
		return root == null;
	}

	// number of points in the set
	public int size() {
		return n;
	}

	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}

		root = insert(null, root, p, true);
	}

	private Node insert(Node parent, Node node, Point2D p, boolean orientation) {
		if (node == null) {
			RectHV rect;
			Node newNode = new Node(p);
			if (parent == null) {
				rect = new RectHV(0, 0, 1, 1);
			} else {
				if (parent.p.equals(p))
					return parent;
				int cmp;
				if (orientation) {
					cmp = Point2D.Y_ORDER.compare(p, parent.p);
					if (cmp < 0) {
						rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
					} else {
						rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
					}
				} else {
					cmp =  Point2D.X_ORDER.compare(p, parent.p);
					if (cmp < 0) {
						rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax());
					} else {
						rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
					}
				}
	
			}
			newNode.rect = rect;
			return newNode;
		}
		int cmp = orientation ? Point2D.X_ORDER.compare(p, node.p) : Point2D.Y_ORDER.compare(p, node.p);
		if (cmp < 0)
			node.lb = insert(node, node.lb, p, !orientation);
		else
			node.rt = insert(node, node.rt, p, !orientation);
		return node;
	}

	// does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}

		return contains(root, p, true);
	}

	private boolean contains(Node node, Point2D p, boolean orientation) {
		if (node == null)
			return false;
		if (node.p.equals(p))
			return true;
		int cmp = orientation ? Point2D.X_ORDER.compare(p, node.p) : Point2D.Y_ORDER.compare(p, node.p);
		if (cmp < 0)
			return contains(node.lb, p, !orientation);
		else 
			return contains(node.rt, p, !orientation);

	}

	// draw all points to standard draw
	public void draw() {
		drawInOrder(root, true);
	}

	private void drawInOrder(Node n, boolean orientation) {
		if (n == null) {
			return;
		}
		
		StdDraw.setPenRadius();
		if (orientation) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(n.p.x(), n.p.y());
		drawInOrder(n.lb, !orientation);
		drawInOrder(n.rt, !orientation);
	}

	// all points that are inside the rectangle (or on the boundary)
	/*public Iterable<Point2D> range(RectHV rect) {

	}

	// a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {

	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {

	}*/

	private static class Node {
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree

		public Node(Point2D p) {
			this.p = p;
		}

	}
}

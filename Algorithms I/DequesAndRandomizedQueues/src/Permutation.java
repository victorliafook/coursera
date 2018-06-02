import edu.princeton.cs.algs4.StdIn;

public class Permutation {

	public static void main(String[] args) {
		int i = 0;
		int n = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			rq.enqueue(item);
			
		}

		for (String s : rq) {
			if (i >= n) {
				break;
			}
			System.out.println(s);
			i++;
		}
	}
}

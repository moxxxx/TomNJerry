public class Main {

    public static void main(String[] args) {
	Node node = new Node(null, 2,2,0);
	System.out.println(node);
	BFS bfs = new BFS();
	bfs.production(node);
    }
}

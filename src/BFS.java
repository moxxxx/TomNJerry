import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class BFS {
    public List<Node> toVisit;
    ArrayList<ArrayList<Node> > visited;
    int size;

     public BFS(){
         size = 8;
         toVisit = new ArrayList<Node>();
         visited = new ArrayList<ArrayList<Node> >(size);
         for (int i = 0; i < size; i ++){
             visited.add(new ArrayList<Node>(size));
         }
     }
    public BFS(int size){
        this.size = size;
        toVisit = new ArrayList<Node>();
        visited = new ArrayList<ArrayList<Node> >(size);
        for (int i = 0; i < size; i ++){
            visited.add(new ArrayList<Node>(size));
        }
    }
    public boolean isVisited(Node node){
             return (visited.get(node.x).get(node.y) != null);
    }

    public boolean termination (Node node){


            return (positionsOfJerry[depth].x == x && positionsOfJerry[depth].y == y);
        }
    }
     public void production(Node node){
         List<Point> estimatedPosition = new ArrayList<Point>();
         estimatedPosition.add( new Point(node.x + 2, node.y + 1));
         estimatedPosition.add( new Point(node.x - 2, node.y - 1));
         estimatedPosition.add( new Point(node.x + 1, node.y + 2));
         estimatedPosition.add( new Point(node.x + 2, node.y - 1));
         estimatedPosition.add( new Point(node.x - 2, node.y + 1));
         estimatedPosition.add( new Point(node.x - 1, node.y - 2));
         estimatedPosition.add( new Point(node.x + 1, node.y - 2));
         estimatedPosition.add( new Point(node.x - 1, node.y + 2));

         for (Point point : estimatedPosition){
             if (point.x >= 0 && point.x < size && point.y >= 0 && point.y < size) {
                 Node child = new Node(node, point.x, point.y, node.depth+1);
                 node.addChild(child);
              }
         }
     }
    public static void main(String[] args) {
        Node node = new Node(null, 0,0,0);
        BFS bfs = new BFS();
        bfs.production(node);
        node.printChild();

    }
}

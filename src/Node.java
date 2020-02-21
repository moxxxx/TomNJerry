import java.util.ArrayList;
import java.util.List;

public class Node {
    int x;
    int y;
    int depth;
    Node parent;
    // ArrayList children;
    public List<Node> children;

    public Node(Node p, int x, int y, int depth){
        parent = p;
        this.x = x;
        this.y = y;
        this.depth = depth;
        children = new ArrayList<Node>();
    }
    public String toString() {
        return String.format("Node at x: " + x +  ", y : " + y + ", at depth of " + depth);
    }
    public void  printChild(){
        System.out.println("I have my children: ");
         for (Node n : children){
             System.out.println(n);
         }

    }
    public void addChild(Node child){
        children.add(child);
    }

}

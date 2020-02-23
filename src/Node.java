import java.util.*;

public class Node implements Comparable<Node> {
    int x;
    int y;
    int h;
    int f;
    int depth; // not only the depth but also the cost!
    Node parent;
    // ArrayList children;
    public List<Node> children;

    public int compareTo(Node anthorNode) {
        return this.f - anthorNode.f;
    }

    public Node(Node p, int x, int y, int depth){
        parent = p;
        this.x = x;
        this.y = y;
        this.depth = depth;
        h = 0;
        f = 0;
        children = new ArrayList<>();
    }
    public String toString() {
        return String.format("Node at x: " + x +  ", y : " + y + ", at depth of " + depth + " with f: " + f );
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

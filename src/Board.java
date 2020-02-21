import java.util.*;
import java.awt.Point;

public class Board {
    // public Queue<Node> toVisit;
    ArrayList<ArrayList<Boolean>> visited;
    int boardSize;
    Point tom;
    int depthOfJerry = 10;
    ArrayList<Point> positionOfJerry = new ArrayList<Point>();

    public Board() {
        boardSize = 8;
        tom = new Point(0, 0);
        // toVisit = new LinkedList<>();
        visited = new ArrayList<ArrayList<Boolean>>(boardSize);
        for (int i = 0; i < boardSize; i++) {
            ArrayList<Boolean> array = new ArrayList<>(boardSize);
            for (int j = 0; j < boardSize; j++) {
                array.add(false);
            }
            visited.add(array);
        }
        this.getJerryPosition(1,1);
    }

    public Board(int size, int tomX, int tomY, int jerX, int jerY) {
        this.boardSize = size;
        this.tom = new Point(tomX, tomY);

        // toVisit = new LinkedList<>();
        visited = new ArrayList<ArrayList<Boolean>>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<Boolean> array = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                array.add(false);
            }
            visited.add(array);
        }
        this.getJerryPosition(jerX,jerY);
    }

    public boolean isVisited(Node node) {
        return visited.get(node.x).get(node.y);
    }

    public boolean termination(Node node) {
        Point p = positionOfJerry.get(node.depth);
        return (node.x == p.x && node.y == p.y);
    }

    public void production(Node node) {
        List<Point> estimatedPosition = new ArrayList<Point>();
        estimatedPosition.add(new Point(node.x + 2, node.y + 1));
        estimatedPosition.add(new Point(node.x - 2, node.y - 1));
        estimatedPosition.add(new Point(node.x + 1, node.y + 2));
        estimatedPosition.add(new Point(node.x + 2, node.y - 1));
        estimatedPosition.add(new Point(node.x - 2, node.y + 1));
        estimatedPosition.add(new Point(node.x - 1, node.y - 2));
        estimatedPosition.add(new Point(node.x + 1, node.y - 2));
        estimatedPosition.add(new Point(node.x - 1, node.y + 2));

        for (Point point : estimatedPosition) {
            if (point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize) {
                Node child = new Node(node, point.x, point.y, node.depth + 1);
                node.addChild(child);
            }
        }
    }

    public boolean setToVisit(Node node) {
        return visited.get(node.x).set(node.y, true);
    }
    public void printPath(Stack<Node> path){
        while (!path.isEmpty()){
            Node current = path.pop();
            System.out.println(current);
            printBoard(current);
        }
    }
    public void printBoard(Node current){

        for (int i = 0 ; i < boardSize; i++){
            if (i > 9) {
                System.out.print(i + ":");
            }else{
                System.out.print(i + " :");
            }
            for (int j = 0; j < boardSize; j ++){
                if (current.x == j && current.y ==i){
                    System.out.print("T |");
                }else if (positionOfJerry.get(current.depth).x == j &&  positionOfJerry.get(current.depth).y == i ){
                    System.out.print("J |");
                }else {
                    System.out.print("__|");
                }
            }
            System.out.print('\n');
        }
    }

    public void bfs()
    {
        Queue<Node> toVisit = new LinkedList<Node>();
        Node root = new Node(null, tom.x, tom.y, 0);
        Node current = root;
        toVisit.add(root);
        while (!toVisit.isEmpty()) {
            current = toVisit.poll();
            // System.out.println(toVisit);
            if (current.depth >= depthOfJerry) { // jerry eats chesse
                // setToVisit(current);
            } else {
                if (termination(current)) {
                    // if catch!
                    break;
                } else {// not catch, expand
                    // setToVisit(current);
                    production(current);// set its child
                    for (Node child : current.children) {// append to its to Visited
                        if (!isVisited(child)) {
                            toVisit.add(child);
                        }
                    }
                }
            }
        }
        if (termination(current)) {
            Stack<Node> path = new Stack<Node>();
            while (current.parent != null) {
                path.add(current);
                current = current.parent;
            }
            path.add(current);
            System.out.println("Tom catch Jerry");
            printPath(path);
        } else {
            System.out.println("Jerry won");
        }
    }

    public void dfs(){
        Stack<Node> toVisit = new Stack<Node>();
        Node root = new Node(null, tom.x, tom.y, 0);
        Node current = root;
        toVisit.add(root);
        while (!toVisit.isEmpty()) {
            current = toVisit.pop();
            // System.out.println(toVisit);
            if (current.depth >= depthOfJerry) { // jerry eats chesse
                // setToVisit(current);
            } else {
                if (termination(current)) {
                    // if catch!
                    break;
                } else {// not catch, expand
                    //setToVisit(current);
                    production(current);// set its child
                    for (Node child : current.children) {// append to its to Visited
                        if (!isVisited(child)) {
                            toVisit.add(child);
                        }
                    }
                }
            }
        }
        if (termination(current)) {
            Stack<Node> path = new Stack<Node>();
            while (current.parent != null) {
                path.add(current);
                current = current.parent;
            }
            path.add(current);
            System.out.println("Tom catch Jerry");
            printPath(path);
        } else {
            System.out.println("Jerry won");
        }
    }

    public void getJerryPosition(int x, int y) {
        // d = left , w = up, a = right, s = down

        ArrayList<Character> moveOfJerry = new ArrayList<Character>();
        moveOfJerry.add('d');
        moveOfJerry.add('d');
        moveOfJerry.add('s');
        moveOfJerry.add('s');
        moveOfJerry.add('s');
        moveOfJerry.add('s');
        moveOfJerry.add('s');
        moveOfJerry.add('z');
        moveOfJerry.add('z');
        moveOfJerry.add('s');

        // Character[] moveOfJerry = {'d','d','d','s','s','s','s','z','z','z','s'};

        for (char i : moveOfJerry) {
            switch (i) {
                case 'd':
                    x++;
                    break;
                case 'w':
                    y--;
                    break;
                case 'a':
                    x--;
                    break;
                case 's':
                    y++;
                    break;
                case 'z':
                    x--;
                    y++;
                    break;
                case 'q':
                    x--;
                    y--;
                    break;
                case 'c':
                    x++;
                    y++;
                    break;
                case 'e':
                    x++;
                    y--;
                    break;
            }
            positionOfJerry.add(new Point(x, y));
        }
    }

    public static void main(String[] args) {
        // Board board = new Board(12,2,6,7,1);
        Board board = new Board(12,2,6,7,1);
        board.dfs();
    }

}

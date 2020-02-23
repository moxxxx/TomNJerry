import java.util.*;
import java.awt.Point;

public class Board {
    ArrayList<ArrayList<Boolean>> visited;
    int boardSize;
    Point tom;
    int depthOfJerry = 9;
    ArrayList<Point> positionOfJerry = new ArrayList<Point>();

    public Board() {
        boardSize = 8;
        tom = new Point(0, 0);
        visited = new ArrayList<ArrayList<Boolean>>(boardSize);
        for (int i = 0; i < boardSize; i++) {
            ArrayList<Boolean> array = new ArrayList<>(boardSize);
            for (int j = 0; j < boardSize; j++) {
                array.add(false);
            }
            visited.add(array);
        }
        this.getJerryPosition(1, 1);
    }

    public Board(int size, int tomX, int tomY, int jerX, int jerY) {
        this.boardSize = size;
        this.tom = new Point(tomX, tomY);
        visited = new ArrayList<ArrayList<Boolean>>(size);
        for (int i = 0; i < size; i++) {
            ArrayList<Boolean> array = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                array.add(false);
            }
            visited.add(array);
        }
        this.getJerryPosition(jerX, jerY);
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
            if (point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize && node.depth < depthOfJerry) {
                // not out of boundary && depth does not greater than depth of jerry
                Node child = new Node(node, point.x, point.y, node.depth + 1);
                node.addChild(child);
            }
        }
    }

    public void hn1(Node node) {
        int depth = node.depth;
        int x1 = positionOfJerry.get(depth).x;
        int y1 = positionOfJerry.get(depth).y;
        node.h = (int) (Math.sqrt((node.y - y1) * (node.y - y1) + (node.x - x1) * (node.x - x1)) / Math.sqrt(5));
        node.f = node.h + node.depth;
    }

    /*
    public void hn2(Node node){
        int
    }


     */
    public boolean setToVisit(Node node) {
        return visited.get(node.x).set(node.y, true);
    }

    public void printPath(Stack<Node> path) {
        while (!path.isEmpty()) {
            Node current = path.pop();
            System.out.println(current);
            printBoard(current);
        }
    }

    public void printBoard(Node current) {
        // current.printChild();
        for (int i = 0; i < boardSize; i++) {
            if (i > 9) {
                System.out.print(i + ":");
            } else {
                System.out.print(i + " :");
            }
            for (int j = 0; j < boardSize; j++) {
                if (current.x == j && current.y == i) {
                    System.out.print("T |");
                } else if (positionOfJerry.get(current.depth).x == j && positionOfJerry.get(current.depth).y == i) {
                    System.out.print("J |");
                } else {
                    System.out.print("__|");
                }
            }
            System.out.print('\n');
        }
    }

    public void bfs() {
        Queue<Node> toVisit = new LinkedList<Node>();
        Node root = new Node(null, tom.x, tom.y, 0);
        Node current = root;
        toVisit.add(root);
        int i = 0;
        while (!toVisit.isEmpty()) {
            current = toVisit.poll();
            if (current.depth >= depthOfJerry) { // jerry eats cheese
            } else {
                if (termination(current)) {
                    // if catch!
                    break;
                } else {// not catch, expand
                    production(current);// set its child
                    for (Node child : current.children) {// append to its to Visited
                        if (inToVisit(toVisit, child)){
                            // already in to Visit, skip
                        }else {
                            toVisit.add(child);
                            i ++;
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
            System.out.println("Tom catch Jerry" + i);
            printPath(path);
        } else {
            System.out.println("Jerry won");
        }
    }

    public void dfs() {
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

    private void initClose(List close) {
        for (int i = 0; i < boardSize; i++) {
            List<List<Boolean>> listOfList = new ArrayList<>(boardSize);
            // close.add(new ArrayList<>(boardSize));
            for (int j = 0; j < boardSize; j++) {
                List<Boolean> booleanList = new ArrayList<>(depthOfJerry);
                for (int k = 0; k < depthOfJerry; k++) {
                    booleanList.add(false);
                }
                listOfList.add(booleanList);
            }
            close.add(listOfList);
        }
    }

    private void closeIt(List<List<List<Boolean>>> close, Node nodeToClose) {
        close.get(nodeToClose.x).get(nodeToClose.y).set(nodeToClose.depth, true);
    }

    private boolean inToVisit(Queue<Node> open, Node node){
        for (Node n : open){
            if (n.x == node.x && n.y == node.y && n.depth == node.depth){
                return true;
            }
        }
        return false;
    }


    private boolean inOpen(List<Node> open, Node node){
        for (Node n: open){

            if (n.x== node.x && n.y == node.y && n.depth == node.depth) {
                return true;
            }
        }
        return false;
    }

    private boolean inCLose(List<List<List<Boolean>>> closed, Node node){
        return closed.get(node.x).get(node.y).get(node.depth);
    }
    private boolean foundAtLast(Node n, int i){
        return (n.x == positionOfJerry.get(i).x && n.y == positionOfJerry.get(i).y && n.depth ==i);
    }
    public void aStar() {
        List<Node> open = new ArrayList<Node>();
        List<List<List<Boolean>>> close = new ArrayList(boardSize);
        initClose(close);
        Node root = new Node(null, tom.x, tom.y, 0);
        open.add(root);
        boolean found = false;
        Node q = null;
        int i = 0;
        while (!open.isEmpty()) {
            q = open.remove(0);
            if (q.depth < depthOfJerry) {
                closeIt(close, q);
                production(q); // generate child
                for (Node child : q.children) {
                    if (termination(child)){
                        System.out.println("Tom Win! " + i);
                        Stack<Node> path = new Stack<Node>();
                        while (child != null) {
                            path.add(child);
                            child = child.parent;
                        }
                        System.out.println("Tom catch Jerry");
                        printPath(path);
                        return;
                    }
                    hn1(child);// updated the the h and f
                    if (inOpen(open,child)){
                        //skip
                    }else if (inCLose(close, child)){
                        //skip
                    }else{
                        open.add(child);
                        i++;
                    }
                }
                Collections.sort(open);
            }
        }
            System.out.println("jerry Won");
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
        Board board = new Board(10, 0, 0, 7, 1);
        //Board board = new Board(12,2,6,7,1);
        board.aStar();
        /*
        List open = new ArrayList<Node>();
        open.add(new Node(null, 1,1,2));
        open.add(new Node(null, 1,2,4));
        System.out.println(board.openContains(open, new Node(null,2,1,2)));
         */
    }

}

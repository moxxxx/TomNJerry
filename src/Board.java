import java.util.*;
import java.awt.Point;

public class Board {
    ArrayList<ArrayList<Boolean>> visited;
    int boardSize;
    Point tom;
    Point jerry;
    List<Cheese> cheeseBag;
    int depthOfJerry;
    ArrayList<Point> positionOfJerry = new ArrayList<Point>();

    public Board() {
        boardSize = 10;
        cheeseBag = new ArrayList<Cheese>(3);
        visited = new ArrayList<ArrayList<Boolean>>(boardSize);
        for (int i = 0; i < boardSize; i++) {
            ArrayList<Boolean> array = new ArrayList<>(boardSize);
            for (int j = 0; j < boardSize; j++) {
                array.add(false);
            }
            visited.add(array);
        }
        initPosition();
        runJerry();
        printBoard();
        depthOfJerry = positionOfJerry.size()-1;
    }

    public Board(int size) {
        boardSize = size;
        cheeseBag = new ArrayList<Cheese>(3);
        visited = new ArrayList<ArrayList<Boolean>>(boardSize);
        for (int i = 0; i < boardSize; i++) {
            ArrayList<Boolean> array = new ArrayList<>(boardSize);
            for (int j = 0; j < boardSize; j++) {
                array.add(false);
            }
            visited.add(array);
        }
        initPosition();
        runJerry();
        printBoard();
        depthOfJerry = positionOfJerry.size()-1;
    }

    private int cost(Point rat, int x, int y){
        int absX = Math.abs(rat.x - x);
        int absY = Math.abs(rat.y-y);
        if (absX > absY) return absX;
        return absY;
    }// If Jerry is smart, he should use this to calculate a cost of step to eat cheese instead of calculating the distance of cheese

    private void printBoard(){
        for (int i = 0; i < boardSize; i++){
            if (i > 9) {
                System.out.print(i + ":");
            } else {
                System.out.print(i + " :");
            }
            for (int j =0; j < boardSize; j++){
                if (tom.x == j && tom.y == i) {
                    System.out.print("T |");
                } else if (jerry.x == j && jerry.y == i) {
                    System.out.print("J |");
                }
                else {
                    boolean f = false;
                    for (Cheese ch : cheeseBag){
                        if (ch.x == j && ch.y == i){
                            System.out.print("C_|");
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        for (Point path : positionOfJerry) {// if in path
                            if (path.x == j && path.y == i) {
                                System.out.print("P |");
                                f = true;
                                break;
                            }
                        }
                    }
                    if (!f)
                        System.out.print("__|");
                }
            }
            System.out.print('\n');
        }
    }

    private void initPosition(){
        int randomNum;
        int numberOfCheese = 3;
        TreeSet<Integer> set = new TreeSet<>(); // first two are cheese, 3rd is rat, 4th is cat
        while (set.size() < 2 + numberOfCheese){
            randomNum = new Random().nextInt(boardSize*boardSize);
            set.add(randomNum);
        }
        int x;
        int y;
        int i;
        i = set.pollFirst();
        x = i / boardSize;// x
        y = i % boardSize; // y
        tom = new Point(x,y);

        i = set.pollFirst();
        x = i / boardSize;// x
        y = i % boardSize; // y
        jerry = new Point(x,y);

        while (!set.isEmpty()){
            i = set.pollFirst();
            x = i / boardSize;// x
            y = i % boardSize; // y
            Point p = new Point (x, y);
            cheeseBag.add(new Cheese(x, y, distance(p,jerry)));
        }
        Collections.sort(cheeseBag);
        positionOfJerry.add(jerry);
    }

    private void runJerry(){
        Point jerry = new Point(this.jerry.x, this.jerry.y);
        List<Cheese> tempCheese = new ArrayList<Cheese>();
        tempCheese.addAll(cheeseBag);
        while (!tempCheese.isEmpty()){
            Cheese cheese = tempCheese.remove(0);
            while (jerry.x != cheese.x || jerry.y != cheese.y ){// not eaten
                if (jerry.x > cheese.x){
                    jerry.x--;
                }
                if (jerry.x < cheese.x){
                    jerry.x++;
                }
                if (jerry.y > cheese.y){
                    jerry.y--;
                }
                if (jerry.y < cheese.y){
                    jerry.y++;
                }
                Point c = new Point(jerry.x, jerry.y);
                positionOfJerry.add(c);
            }
            for (Cheese remainCheese : tempCheese){// update new distance
                remainCheese.distance = distance(new Point(remainCheese.x, remainCheese.y), jerry);
            }
            Collections.sort(tempCheese);
        }
    }

    private double distance(Point a, Point b){
        return Math.sqrt((a.y - b.y) * (a.y - b.y) + (a.x - b.x) * (a.x - b.x));
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


    public void hn2(Node node){
        int depth = node.depth;
        int x1 = positionOfJerry.get(depth).x;
        int y1 = positionOfJerry.get(depth).y;
        // node.h = (int) (Math.sqrt((node.y - y1) * (node.y - y1) + (node.x - x1) * (node.x - x1)) / Math.sqrt(5));
        node.h = (int) distance(new Point(node.x, node.y), (new Point(x1, y1)));
        node.f = node.h + node.depth;
    }

    public void hn3(Node node){
        int depth = depthOfJerry;
        int x1 = positionOfJerry.get(depth).x;
        int y1 = positionOfJerry.get(depth).y;
        double h1 = (Math.sqrt((node.y - y1) * (node.y - y1) + (node.x - x1) * (node.x - x1)) / Math.sqrt(5));
        depth = node.depth;
        x1 = positionOfJerry.get(depth).x;
        y1 = positionOfJerry.get(depth).y;
        double h2 =(Math.sqrt((node.y - y1) * (node.y - y1) + (node.x - x1) * (node.x - x1)) / Math.sqrt(5));
        node.h = (int) (h1 + h2)/2;
        node.f = node.h + node.depth;
    }


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
                    for (Cheese cheese : cheeseBag){
                        if (cheese.x == j && cheese.y == i){
                            cheeseBag.remove(cheese);
                            break;
                        }
                    }
                } else {
                    boolean f = false;
                    for (Cheese cheese : cheeseBag){
                        if (cheese.x == j && cheese.y == i){
                            System.out.print("C |");
                            f = true;
                            break;
                        }
                    }
                    if (!f)
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
            System.out.println("Tom catch Jerry, span: " + i);
            printPath(path);
        } else {
            System.out.println("Jerry won, span : " + i);
        }
    }
    public void superDfs() {
        Stack<Node> toVisit = new Stack<Node>();
        Node root = new Node(null, tom.x, tom.y, 0);
        Node current = root;
        toVisit.add(root);
        int i = 0;
        while (!toVisit.isEmpty()) {
            current = toVisit.pop();
            if (current.depth >= depthOfJerry) { // jerry eats cheese
            } else {
                if (termination(current)) {
                    // if catch!
                    break;
                } else {// not catch, expand
                    production(current);// set its child
                    for (Node child : current.children) {// append to its to Visited
                        if (!isVisited(child)){
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
            System.out.println("Tom catch Jerry, span: " + i);
            printPath(path);
        } else {
            System.out.println("Jerry won + span: " + i);
        }
    }


    public void dfs() {
        Stack<Node> toVisit = new Stack<Node>();
        Node root = new Node(null, tom.x, tom.y, 0);
        Node current = root;
        toVisit.add(root);
        int count =0;
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
                            count++;
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
            System.out.println("Tom catch Jerry and span" + count);
            printPath(path);
        } else {
            System.out.println("Jerry won, span: " + count);
        }
    } // modify this

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
        if (node.depth >= depthOfJerry){// error handling
            return false;
        }
        return closed.get(node.x).get(node.y).get(node.depth);
    }
    private boolean foundAtLast(Node n, int i){
        return (n.x == positionOfJerry.get(i).x && n.y == positionOfJerry.get(i).y && n.depth ==i);
    }
    public void aStar(int mode) {
        List<Node> open = new ArrayList<Node>();
        List<List<List<Boolean>>> close = new ArrayList();
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
                    switch (mode){
                        case 0: hn1(child);
                        case 1: hn2(child);
                        case 2: hn3(child);
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
            System.out.println("Jerry Won");
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
        Board board = new Board(30);
        System.out.println("------------Hn1-------------");
        System.out.println();
        board.aStar(1);
        System.out.println("------------Hn2-------------");
        System.out.println();
        board.aStar(0);
        System.out.println("------------BFS-------------");
        System.out.println();
        board.bfs();
        //System.out.println("------------DFS1-------------");
        System.out.println();
        //board.dfs();

    }

}

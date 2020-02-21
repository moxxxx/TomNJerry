import java.awt.*;
import java.util.ArrayList;

public class Board {
    ArrayList<Point> positionOfJerry;
    public Board(){
        positionOfJerry = new ArrayList<Point>();
    }
    public void getJerryPostion(int x, int y) {
        // d = left , w = up, a = right, s = down
        ArrayList<Character> moveOfJerry = new ArrayList<Character>();
        moveOfJerry.add('d');
        moveOfJerry.add('d');
        moveOfJerry.add('d');
        moveOfJerry.add('s');
        moveOfJerry.add('s');
        moveOfJerry.add('s');
        moveOfJerry.add('s');
        moveOfJerry.add('z');
        moveOfJerry.add('z');
        moveOfJerry.add('z');
        moveOfJerry.add('s');

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
        Board game = new Board();
        game.getJerryPostion(1,2);
        System.out.println(game.positionOfJerry);
    }
}



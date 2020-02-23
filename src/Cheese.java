public class Cheese implements Comparable<Cheese>{
    int x;
    int y;
    double distance;
    public Cheese(){
        x = 0;
        y = 0;
        distance = 0;
    }
    public Cheese(int x, int y , double d){
        this.x = x;
        this. y = y;
        this.distance = d;
    }

    public int compareTo(Cheese anthorNode) {
        return this.distance > anthorNode.distance ? 1 : this.distance < anthorNode.distance ? -1 : 0;
    }

    @Override
    public String toString() {
        return String.format("Cheese at x: " + x +  ", y : " + y + ", at distance of " + distance);
    }
}

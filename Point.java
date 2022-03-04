package closestPoints;

public class Point /**implements Comparable<Point>**/{
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Pythagoras sats
    public double distanceTo(Point point) {
        double xDistance = Math.pow(this.getX() - point.getX(), 2.0);
        double yDistance = Math.pow(this.getY() - point.getY(), 2.0);
        return Math.sqrt(xDistance + yDistance);
    }

//    @Override
//    public int compareTo(Point o) {
//        return Integer.compare(y, o.getY());
//    }
}

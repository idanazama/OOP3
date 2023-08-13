package Project.TilePackage;

public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x,int y)
    {
        this.x = x;
        this.y = y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    @Override
    public int compareTo(Position o) {
        if(x==o.x&&y==o.y)
        {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return ("x: " + x + " y: " + y);
    }

    public double calculateRange(Position p)
    {
        return Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y));
    }
}

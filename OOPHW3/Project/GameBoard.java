package Project;

import Project.TilePackage.*;
import Project.TilePackage.EnemyPackage.Enemy;

public class GameBoard {
    private final Tile[][] board;


    public GameBoard(Tile[][] board) {
        this.board = board;
    }

    public Tile get(int x, int y) {
        return board[x][y];
    }

    public void remove(Enemy e) {
        Position p = e.getPosition();
        board[p.getX()][p.getY()] = new Empty(p);
    }
    public void switchPosition(Position p1, Position p2)
    {
        Tile t1 = board[p1.getX()][p1.getY()];
        Tile t2 = board[p2.getX()][p2.getY()];
        board[p1.getX()][p1.getY()] = t2;
        board[p2.getX()][p2.getY()] = t1;
    }
    @Override
    public String toString() {
        String s = "";
        for(int y = 0;y<board[0].length;y++)
        {
            for(int x = 0;x<board.length;x++)
            {
                s += board[x][y].toString();
            }
            s+= '\n';
        }
        return s;
    }
}

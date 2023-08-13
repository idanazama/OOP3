package Project;

import Project.CallbackPackage.ChangedPositionCallback;
import Project.CallbackPackage.EnemyDeathCallback;
import Project.CallbackPackage.GetTileCallback;
import Project.CallbackPackage.MessageCallback;
import Project.TilePackage.EnemyPackage.Enemy;
import Project.TilePackage.PlayerPackage.Player;
import Project.TilePackage.Position;
import Project.TilePackage.Tile;
import Project.TilePackage.TileFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class LevelLoader {
    private final String PATH;
    private final String BASE_FILE_NAME;
    private final int END_COUNT;
    private int currentCount;
    private Player player;
    private TileFactory tileFactory;
    private MessageCallback messageCallback;
    private EnemyDeathCallback enemyDeathCallback;
    private ChangedPositionCallback changedPositionCallback;
    private GetTileCallback getTileCallback;
    public LevelLoader(String path, String baseFileName, int startCount, int endCount, Player player, MessageCallback messageCallback,
                       EnemyDeathCallback enemyDeathCallback, ChangedPositionCallback changedPositionCallback, GetTileCallback getTileCallback)
    {
        tileFactory = new TileFactory();
        PATH = path;
        BASE_FILE_NAME = baseFileName;
        currentCount = startCount;
        END_COUNT = endCount;
        this.player = player;
        this.messageCallback = messageCallback;
        this.enemyDeathCallback = enemyDeathCallback;
        this.changedPositionCallback = changedPositionCallback;
        this.getTileCallback = getTileCallback;
    }
    public boolean hasNextLevel()
    {
        return currentCount<=END_COUNT;
    }
    public Pair<LinkedList<Enemy>,GameBoard> loadNextLevel(){
        char[][] boardChars;
        LinkedList<Enemy> enemies = new LinkedList<>();
        GameBoard gameBoard = null;
        Path filePath = Path.of(PATH + "/" + BASE_FILE_NAME + currentCount + ".txt");
        try
        {
            String content = Files.readString(filePath);
            String[] substrings = content.split("\n");
            for(int i =0;i<substrings.length;i++)
            {
                substrings[i] = substrings[i].replace("\r" , "");
            }
            Tile[][] tiles = new Tile[substrings[0].length()][substrings.length];
            for(int y =0;y<substrings.length;y++)
            {
                for(int x = 0;x<substrings[y].length();x++)
                {
                    char c = substrings[y].charAt(x);
                    toTileArray(x,y,c,tiles,enemies);
                }
            }
            gameBoard = new GameBoard(tiles);
            for (Enemy e: enemies) {
                e.initPlayer(player);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        currentCount++;
        return new Pair(enemies,gameBoard);
    }
    private void toTileArray(int x,int y,char c ,Tile[][] tiles,LinkedList<Enemy> enemies)
    {
        if(c == '#')
        {
            tiles[x][y] = tileFactory.produceWall(new Position(x,y));
        }
        else if(c == '.')
        {
            tiles[x][y] = tileFactory.produceEmpty(new Position(x,y));
        }
        else if(c == '@')
        {
            player.setPosition(new Position(x,y));
            tiles[x][y] = player;
        }
        else
        {
            Enemy e = tileFactory.produceEnemy(c,new Position(x,y),messageCallback,enemyDeathCallback,changedPositionCallback, getTileCallback);
            enemies.add(e);
            tiles[x][y] = e;
        }

    }
}

package Project;

import Project.CallbackPackage.*;
import Project.TilePackage.EnemyPackage.Enemy;
import Project.TilePackage.PlayerPackage.Player;
import Project.TilePackage.Position;
import Project.TilePackage.Tile;
import Project.TilePackage.TileFactory;
import Project.TilePackage.Unit;

import java.util.LinkedList;
import java.util.List;

public class GamePlayer {
    private String path;
    private final String BASE_FILE_NAME = "level";
    private final int START_LEVEL = 1;
    private final int END_LEVEL = 4;
    private GameBoard gameBoard;
    private Player player;
    private List<Enemy> enemies;
    private TileFactory tileFactory;
    private MessageCallback messageCallback;
    private GetEnemiesCallback getEnemiesCallback;
    private EnemyDeathCallback enemyDeathCallback;
    private ChangedPositionCallback changedPositionCallback;
    private GetTileCallback getTileCallback;
    private LevelLoader levelLoader;
    private InputReader inputReader;
    private CLI cli;
    public GamePlayer(String path)
    {
        this.path = path;
    }
    public void playGame()
    {
        initGame();
        while(levelLoader.hasNextLevel() && !player.isDead())
        {
            playLevel();
        }
        if(player.isDead())
        {
            messageCallback.call("Player died! Game Over.");
        }
        else
        {
            messageCallback.call("Player Won!");
        }
    }
    private void playLevel()
    {
        Pair<LinkedList<Enemy>,GameBoard> temp = levelLoader.loadNextLevel();
        enemies = temp.getFirst();
        gameBoard = temp.getSecond();
        while(!enemies.isEmpty() && !player.isDead())
        {
            messageCallback.call(gameBoard.toString());
            playTurn(inputReader.read());
        }
    }
    private void initGame()
    {
        cli = CLI.getInstance();
        messageCallback = cli.getMessageCallback();
        inputReader = cli.getInputReader();
        enemyDeathCallback = (e) -> enemyDied(e);
        changedPositionCallback = (u,t) -> unitChangedPosition(u,t);
        getTileCallback = (p) -> getTile(p);
        getEnemiesCallback = () -> getEnemies();
        player = cli.getPlayer();
        levelLoader = new LevelLoader(path,BASE_FILE_NAME,START_LEVEL,END_LEVEL,player,messageCallback,enemyDeathCallback,changedPositionCallback,getTileCallback);
        player.initialize(null,messageCallback,changedPositionCallback,getEnemiesCallback);
    }
    private void enemyDied(Enemy e)
    {
        gameBoard.remove(e);
        enemies.remove(e);
        player.increaseExperience(e.getExperienceValue());
    }
    private List<Enemy> getEnemies()
    {
        return enemies;
    }
    private Tile getTile(Position p)
    {
        return gameBoard.get(p.getX(),p.getY());
    }
    private void unitChangedPosition(Unit u, Position to){
        gameBoard.switchPosition(u.getPosition(),to);
    }
    private void playTurn(String input)
    {
        Position p = player.getPosition();
        switch (input)
        {
            case "a":
                player.playTurn(gameBoard.get(p.getX()-1, p.getY()));
                break;
            case "s":
                player.playTurn(gameBoard.get(p.getX(),p.getY()+1));
                break;
            case "d":
                player.playTurn(gameBoard.get(p.getX()+1,p.getY()));
                break;
            case "w":
                player.playTurn(gameBoard.get(p.getX(),p.getY()-1));
                break;
            case "e":
                player.castSpecialAbility(enemies);
                break;
            case "q":
                messageCallback.call("Chose to do nothing.");
                player.playTurn(gameBoard.get(p.getX(),p.getY()));
                break;
            default:
                messageCallback.call("Invalid input!");
                return;
        }
        for(Enemy e : enemies)
        {
            e.playTurn();
        }
        player.onTick();
    }
}

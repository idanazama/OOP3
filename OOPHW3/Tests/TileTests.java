package Tests;
import Project.CallbackPackage.ChangedPositionCallback;
import Project.CallbackPackage.EnemyDeathCallback;
import Project.CallbackPackage.MessageCallback;
import Project.TilePackage.Empty;
import Project.TilePackage.EnemyPackage.Enemy;
import Project.TilePackage.PlayerPackage.Player;
import Project.TilePackage.PlayerPackage.Rogue;
import Project.TilePackage.Position;
import Project.TilePackage.TileFactory;
import Project.TilePackage.Wall;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TileTests {
    TileFactory TF;
    Position p1;
    Position p2;
    private MessageCallback messageCallback;
    private EnemyDeathCallback enemyDeathCallback;
    private ChangedPositionCallback changedPositionCallback;
    @Before
    public void initTest(){
        TF = new TileFactory();
        p1 = new Position(1,1);
        p2 = new Position(2,1);
        messageCallback = (m) -> System.out.println(m);
        enemyDeathCallback = (e) -> System.out.println("Enemy" + e.getName() + " has died");
        changedPositionCallback = (u,to) -> System.out.println(u.getName() + " changed position to " + to.toString());
    }
    @Test
    public void Move_Wall_NoChange(){
        Wall wall =TF.produceWall(p1);
        Player player = TF.producePlayer(1, p2, messageCallback,changedPositionCallback);
        player.playTurn(wall);
        int[] expected = {p2.getX(),p2.getY()};
        int[] actual = {player.getPosition().getX(), player.getPosition().getY()};
        Assert.assertArrayEquals(expected, actual);
    }
    @Test
    public void Move_Empty_ChangePos(){
        Empty e =TF.produceEmpty(p1);
        Player player = TF.producePlayer(1, p2, messageCallback,changedPositionCallback);
        player.playTurn(e);
        int[] expected = {p1.getX(),p1.getY()};
        int[] actual = {player.getPosition().getX(), player.getPosition().getY()};
        Assert.assertArrayEquals(expected, actual);
    }
    @Test
    public void Move_Enemy_NoChange(){
        Enemy e = TF.produceEnemy('s',new Position(1,1),messageCallback,enemyDeathCallback,changedPositionCallback,null);
        Player player = TF.producePlayer(1, p2, messageCallback,changedPositionCallback);
        e.initPlayer(player);
        int[] expected = {player.getPosition().getX(), player.getPosition().getY()};
        player.playTurn(e);
        int[] actual = {player.getPosition().getX(), player.getPosition().getY()};
        Assert.assertArrayEquals(expected, actual);
    }


}

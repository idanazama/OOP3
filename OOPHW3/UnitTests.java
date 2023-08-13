//package Tests;
//import Project.CallbackPackage.ChangedPositionCallback;
//import Project.CallbackPackage.EnemyDeathCallback;
//import Project.CallbackPackage.MessageCallback;
//import Project.TilePackage.EnemyPackage.Enemy;
//import Project.TilePackage.PlayerPackage.Player;
//import Project.TilePackage.Position;
//import Project.TilePackage.TileFactory;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//
//public class UnitTests {
//    private TileFactory factory;
//    private MessageCallback messageCallback;
//    private EnemyDeathCallback enemyDeathCallback;
//    private ChangedPositionCallback changedPositionCallback;
//
//    @Before
//    public void initTest() {
//        factory = new TileFactory();
//        messageCallback = (m) -> System.out.println(m);
//        enemyDeathCallback = (e) -> System.out.println("Enemy " + e.getName() + " has died");
//        changedPositionCallback = (u,to) -> System.out.println(u.getName() + " changed position to " + to.toString());
//    }
//    @Test
//    public void Battle_EnemyVsEnemy_NoBattle()
//    {
//        Enemy e1 = factory.produceEnemy('s',new Position(1,1),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//        Enemy e2 = factory.produceEnemy('s',new Position(1,2),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//        e1.accept(e2);
//        e2.accept(e1);
//        //success = no print.
//    }
//    @Test
//    public void Battle_EnemyVsPlayer_Battle()
//    {
//        Enemy e = factory.produceEnemy('s',new Position(1,1),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//        Player p = factory.producePlayer(1,new Position(1,2),messageCallback,changedPositionCallback);
//        e.accept(p);
//        p.accept(e);
//        //success = prints, once enemy hit player, once player hit enemy.
//    }
//    @Test
//    public void Battle_PlayerVsPlayer_NoBattle()
//    {
//        Player p1 = factory.producePlayer(1,new Position(1,1),messageCallback,changedPositionCallback);
//        Player p2 = factory.producePlayer(2,new Position(1,2),messageCallback,changedPositionCallback);
//        //success = no print.
//    }
//    @Test
//    public void CastAbility_Cooldown_CooldownMsg()
//    {
//        for(int i =1;i<=2;i++)
//        {
//            Player p = factory.producePlayer(i,new Position(1,1),messageCallback,changedPositionCallback);
//            List<Enemy> enemies = new LinkedList<>();
//            Enemy e1 = factory.produceEnemy('s',new Position(1,2),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//            Enemy e2 = factory.produceEnemy('s',new Position(2,1),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//            e1.initPlayer(p);
//            e2.initPlayer(p);
//            enemies.add(e1);
//            enemies.add(e2);
//            p.castSpecialAbility(enemies);
//            p.castSpecialAbility(enemies);
//            System.out.println("");
//        }
//        //success = for each character, 1 message of cast ability, 1 message of cooldown.
//    }
//    @Test
//    public void CastAbility_NoMana_NoManaMsg()
//    {
//        for(int i =3;i<=4;i++)
//        {
//            Player p = factory.producePlayer(i,new Position(1,1),messageCallback,changedPositionCallback);
//            List<Enemy> enemies = new LinkedList<>();
//            Enemy e1 = factory.produceEnemy('s',new Position(1,2),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//            Enemy e2 = factory.produceEnemy('s',new Position(2,1),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//            e1.initPlayer(p);
//            e2.initPlayer(p);
//            enemies.add(e1);
//            enemies.add(e2);
//            p.castSpecialAbility(enemies);
//            p.castSpecialAbility(enemies);
//            p.castSpecialAbility(enemies);
//            System.out.println("");
//        }
//        //success = for each character, 1-2 messages of cast ability, 1-2 messages of no mana.
//    }
//    @Test
//    public void CastAbility_NoEnergy_NoEnergyMsg()
//    {
//        for(int i =5;i<=6;i++)
//        {
//            Player p = factory.producePlayer(i,new Position(1,1),messageCallback,changedPositionCallback);
//            List<Enemy> enemies = new LinkedList<>();
//            Enemy e1 = factory.produceEnemy('s',new Position(1,2),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//            Enemy e2 = factory.produceEnemy('s',new Position(2,1),messageCallback,enemyDeathCallback,changedPositionCallback,null);
//            e1.initPlayer(p);
//            e2.initPlayer(p);
//            enemies.add(e1);
//            enemies.add(e2);
//            p.castSpecialAbility(enemies);
//            p.castSpecialAbility(enemies);
//            p.castSpecialAbility(enemies);
//            p.castSpecialAbility(enemies);
//            p.castSpecialAbility(enemies);
//            p.castSpecialAbility(enemies);
//            System.out.println("");
//        }
//        //success = first gets 1 not enough mana, second gets 4 not enough mana.
//    }
//}

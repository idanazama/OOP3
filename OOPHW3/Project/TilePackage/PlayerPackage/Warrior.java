package Project.TilePackage.PlayerPackage;

import Project.TilePackage.EnemyPackage.Enemy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player{
    private int abilityCoolDown;
    private int remainingCoolDown;
    private final int Heath_Capacity_Multiplier = 5;
    private final int Attack_Multiplier = 2;
    private final int Defense_Multiplier = 2;




    public Warrior(String name, int healthCapacity, int attack, int defense,int abilityCoolDown) {
        super(name, healthCapacity, attack, defense);
        this.abilityCoolDown = abilityCoolDown;
        remainingCoolDown = 0;
    }

    @Override
    protected void classLevelUp() {
        remainingCoolDown = 0;
        healthCapacity += (Heath_Capacity_Multiplier*level);
        attack += (Attack_Multiplier*level);
        defense += level;
    }


    public void castAbility(){
        castSpecialAbility(enemiesCallback.call());
    }
    @Override
    public void castSpecialAbility(List<Enemy> enemies) {
        if(remainingCoolDown>0)
        {
            messageCallback.call("There is still cooldown! try waiting a few turns!");
        }
        else
        {
            List<Enemy> closeEnemies = getSurrounding(enemies);
            if(closeEnemies.size() == 0){
                messageCallback.call("No enemies close! try getting closer!");
            }
            else {
                remainingCoolDown = abilityCoolDown;
                currentHealth = Math.min(currentHealth + (Defense_Multiplier*defense),healthCapacity);
                Random rand = new Random();
                int i = rand.nextInt(0,closeEnemies.size());
                closeEnemies.get(i).hitBySpecialAbility((int) (0.1 * healthCapacity));
                messageCallback.call("Used Avenger's Shield!");
            }

        }
    }

    private List<Enemy> getSurrounding(List<Enemy> enemies){
        LinkedList<Enemy> closeEnemies = new LinkedList<>();
        double dist;
        for (Enemy e: enemies) {
            dist = e.getPosition().calculateRange(this.position);
            if(dist<3){
                closeEnemies.add(e);
            }
        }
        return closeEnemies;
    }

    @Override
    public void onTick() {
        if (remainingCoolDown >0)
            remainingCoolDown -= 1;
        messageCallback.call(printStats());

    }

    @Override
    public String printStats() {
        return super.printStats() + "\n"
                + "remaining cooldown: " + remainingCoolDown + " ability cooldown: " + abilityCoolDown;
    }


}
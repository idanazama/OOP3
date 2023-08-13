package Project.TilePackage.PlayerPackage;

import Project.TilePackage.EnemyPackage.Enemy;

import java.util.List;

public class Hunter extends Player{
    private int range;
    private int ticksCount;
    private int arrowsCount;

    private final int MAX_TICKS = 10;
    private final int ARROWS_ADD = 10;
    private final int ATTACK_ADD = 2;
    private final int DEFENSE_ADD = 1;

    public Hunter(String name, int healthCapacity, int attack, int defense, int range) {
        super(name, healthCapacity, attack, defense);
        this.range = range;
        this.ticksCount = 0;
        this.arrowsCount = 10;
    }

    @Override
    protected void classLevelUp() {
        arrowsCount += ARROWS_ADD*level;
        attack += ATTACK_ADD*level;
        defense += DEFENSE_ADD*level;
    }


    public void castAbility(){
        castSpecialAbility(enemiesCallback.call());
    }



    @Override
    public void castSpecialAbility(List<Enemy> enemies) {
        if(arrowsCount == 0){
            messageCallback.call("Out of arrows! try to replenish first");
        }
        else{
            arrowsCount -= 1;
            Enemy enemy = getClosest(enemies);
            enemy.hitBySpecialAbility(this.attack);
            messageCallback.call("Used Shoot!");

        }
    }

    private Enemy getClosest(List<Enemy> enemies){
        double dist,minDist = Double.MAX_VALUE;
        Enemy closestEnemy = null;
        for (Enemy e: enemies) {
            dist = e.getPosition().calculateRange(this.position);
            if(dist< minDist){
                minDist = dist;
                closestEnemy = e;
            }

        }
        // is it possible that we return null ?
        // null ==>> list is empty
        return closestEnemy;
    }

    @Override
    public void onTick() {
        if(ticksCount == MAX_TICKS){

            arrowsCount += ARROWS_ADD*level;

            ticksCount = 0;
        }
        else{
            ticksCount++;
        }
        messageCallback.call(printStats());
    }

    @Override
    public String printStats() {
        return super.printStats() + "\n"
                + "arrows count: " +  arrowsCount;
    }
}

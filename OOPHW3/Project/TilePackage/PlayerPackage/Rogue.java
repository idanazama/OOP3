package Project.TilePackage.PlayerPackage;

import Project.TilePackage.EnemyPackage.Enemy;

import java.util.LinkedList;
import java.util.List;

public class Rogue extends Player{
    private int cost;
    private int currentEnergy;
    private final int MAX_ENERGY = 100;
    private final int ATTACK_ADD = 3;
    private final int ENERGY_ADD = 10;
    public Rogue(String name, int healthCapacity, int attack, int defense, int cost) {
        super(name, healthCapacity, attack, defense);
        this.cost = cost;
        currentEnergy = MAX_ENERGY;
    }

    @Override
    protected void classLevelUp() {
        currentEnergy = MAX_ENERGY;
        attack += ATTACK_ADD*level;
    }


    public void castAbility(){
        castSpecialAbility(enemiesCallback.call());

    }

    @Override
    public void castSpecialAbility(List<Enemy> enemies) {
        if(currentEnergy<cost)
        {
            messageCallback.call("Not enough energy! try replenishing first");
        }
        else
        {
            List<Enemy> closeEnemies = getSurrounding(enemies);
            if(closeEnemies.size() == 0){
                messageCallback.call("No enemies close! try getting closer!");
            }
            else {
                currentEnergy -= cost;
                for (Enemy e: closeEnemies) {
                    e.hitBySpecialAbility(attack);
                }
                messageCallback.call("Used Fan of Knives!");
            }
        }
    }

    private List<Enemy> getSurrounding(List<Enemy> enemies){
        LinkedList<Enemy> closeEnemies = new LinkedList<>();
        double dist;
        for (Enemy e: enemies) {
            dist = e.getPosition().calculateRange(this.position);
            if(dist<2){
                closeEnemies.add(e);
            }
        }
        return closeEnemies;
    }

    @Override
    public void onTick() {
        currentEnergy = Math.min(currentEnergy + ENERGY_ADD, MAX_ENERGY);
        messageCallback.call(printStats());

    }

    @Override
    public String printStats() {
        return super.printStats() + "\n"
                + "energy: " + currentEnergy+"/"+MAX_ENERGY;
    }
}

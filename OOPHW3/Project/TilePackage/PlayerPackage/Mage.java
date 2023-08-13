package Project.TilePackage.PlayerPackage;

import Project.TilePackage.EnemyPackage.Enemy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Mage extends Player{
    private int manaPool;
    private int manaCost;
    private int currentMana;
    private int spellpower;
    private int hitCount;
    private int range;

    private final int MAX_MANA_ADD = 10;
    private final int SPELLPOWER_ADD = 2;
    private final int CURRENT_MANA_ADD = 4;
    public Mage(String name, int healthCapacity, int attack, int defense,int manaPool, int manaCost,int spellPower,int hitCount,int range) {
        super(name, healthCapacity, attack, defense);
        this.manaPool = manaPool;
        this.manaCost = manaCost;
        this.currentMana = manaPool/4;
        this.spellpower = spellPower;
        this.hitCount = hitCount;
        this.range = range;
    }

    @Override
    protected void classLevelUp() {
        manaPool += MAX_MANA_ADD*level;
        currentMana = Math.min(currentMana+manaPool/CURRENT_MANA_ADD,manaPool);
        spellpower += SPELLPOWER_ADD*level;
    }

    public void castAbility(){
        castSpecialAbility(enemiesCallback.call());
    }

    @Override
    public void castSpecialAbility(List<Enemy> enemies) {
        if(currentMana<manaCost)
        {
            messageCallback.call("Not enough mana!, try replenishing first!");
        }
        else
        {
            List<Enemy> closeEnemies = getSurrounding(enemies);
            if(closeEnemies.size() == 0){
                messageCallback.call("No enemies inside range! try getting closer!");
            }
            else{
                currentMana -= manaCost;
                double hits = 0;
                Random rand = new Random();
                while(hits< hitCount && closeEnemies.size() > 0){
                    int i = rand.nextInt(0,closeEnemies.size());
                    Enemy e = closeEnemies.get(i);
                    e.hitBySpecialAbility(this.spellpower);
                    hits++;
                    closeEnemies = getSurrounding(enemies); // need to refresh because an enemy might die
                }
                messageCallback.call("Used Blizzard!");
            }
        }
    }
    private List<Enemy> getSurrounding(List<Enemy> enemies){
        LinkedList<Enemy> closeEnemies = new LinkedList<>();
        double dist;
        for (Enemy e: enemies) {
            dist = e.getPosition().calculateRange(this.position);
            if(dist<range){
                closeEnemies.add(e);
            }
        }
        return closeEnemies;
    }

    @Override
    public void onTick() {
        currentMana = Math.min(manaPool, currentMana + level);
        messageCallback.call(printStats());
    }

    @Override
    public String printStats() {
        return super.printStats() + "\n"
                + "mana: " + currentMana + "/" + manaPool + " spell power: " + spellpower + "\n";
    }
}

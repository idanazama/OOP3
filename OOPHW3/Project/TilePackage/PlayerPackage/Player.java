package Project.TilePackage.PlayerPackage;

import Project.CallbackPackage.ChangedPositionCallback;
import Project.CallbackPackage.GetEnemiesCallback;
import Project.CallbackPackage.MessageCallback;
import Project.TilePackage.*;
import Project.TilePackage.EnemyPackage.Enemy;

import java.util.List;

public abstract class Player extends Unit implements HeroicUnit {
    protected int experience;
    protected int level;
    protected GetEnemiesCallback enemiesCallback;
    private final int EXP_CONST = 50;
    protected Player(String name, int healthCapacity, int attack, int defense) {
        super('@', name, healthCapacity, attack, defense);
        experience = 0;
        level = 1;
    }
    protected abstract void classLevelUp();

    public void initialize(Position position, MessageCallback messageCallback, ChangedPositionCallback changedPositionCallback, GetEnemiesCallback enemiesCallback) {
        super.initialize(position, messageCallback, changedPositionCallback);
        this.enemiesCallback = enemiesCallback;
    }

    public void increaseExperience(int amount)
    {
        experience += amount;
        while(checkLevelUp())
        {
        }
    }
    private boolean checkLevelUp()
    {
        if(experience>= EXP_CONST*level)
        {
            experience -= EXP_CONST*level;
            level++;
            healthCapacity += 10*level;
            currentHealth = healthCapacity;
            attack += 4*level;
            defense += level;
            classLevelUp();
            messageCallback.call("Player leveled up");
            messageCallback.call(describe());
            return true;
        }
        return false;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    @Override
    public void processStep() {

    }
    public abstract void castSpecialAbility(List<Enemy> enemies);

    public void playTurn(Tile tileTo)
    {
        tileTo.accept(this);
    }
    public abstract void onTick();
    @Override
    public void onDeath() {
        tile = 'X';
    }

    @Override
    public void visit(Enemy e) {
        battle(e);
    }
    @Override
    public void visit(Player p) {

    }
    public String printStats(){
        return "\n"+ "name: " + name + " health: " + currentHealth+"/"+healthCapacity + " attackDamage: " + attack + " defense: " + defense + "\n"
                + "level: " + level + " experience: " + experience;
    }
}

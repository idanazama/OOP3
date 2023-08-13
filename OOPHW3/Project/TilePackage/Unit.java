
package Project.TilePackage;

import Project.CallbackPackage.ChangedPositionCallback;
import Project.CallbackPackage.MessageCallback;
import Project.TilePackage.EnemyPackage.Enemy;
import Project.TilePackage.PlayerPackage.Player;

import java.util.Random;

public abstract class Unit extends Tile {
    protected String name;
    protected int healthCapacity;
    protected int currentHealth;
    protected int attack;
    protected int defense;
    protected MessageCallback messageCallback;
    protected ChangedPositionCallback changedPositionCallback;
    Random random;

    protected Unit(char tile, String name, int healthCapacity, int attack, int defense) {
        super(tile);
        this.name = name;
        this.healthCapacity = healthCapacity;
        currentHealth = healthCapacity;
        this.attack = attack;
        this.defense = defense;
        random = new Random();
    }
    protected void initialize(Position position, MessageCallback messageCallback, ChangedPositionCallback changedPositionCallback){
        super.initialize(position);
        this.messageCallback = messageCallback;
        this.changedPositionCallback = changedPositionCallback;
    }

    protected int attack(){
        int roll =  random.nextInt(attack+1);
        messageCallback.call(name + " rolled " + roll + " attack");
        return roll;
    }

    public int defend(){
        int roll =  random.nextInt(defense+1);
        messageCallback.call(name + " rolled " + roll + " defense");
        return roll;
    }
    public void hitBySpecialAbility(int damageToTake){
        int def = defend();
        if(damageToTake - def > 0 ){
            reduceHealth(damageToTake-def);
        }
    }
    // Should be automatically called once the unit finishes its turn
    public abstract void processStep();

    // What happens when the unit dies
    public abstract void onDeath();

    // This unit attempts to interact with another tile.
    public void interact(Tile tile){
        tile.accept(this);
    }

    public void visit(Empty e)
    {
        Position temp = this.getPosition();
        this.setPosition(e.getPosition());
        e.setPosition(temp);
        changedPositionCallback.call(this,e.position);
    }
    public boolean isDead()
    {
        return currentHealth<=0;
    }
    public abstract void visit(Player p);
    public abstract void visit(Enemy e);

    // Combat against another unit.
    protected void battle(Unit u){
        int attackRoll = attack();
        int defenseRoll = u.defend();
        if(attackRoll-defenseRoll>0)
        {
            u.reduceHealth(attackRoll-defenseRoll);
        }

    }
    public void reduceHealth(int amount) {
        currentHealth-=amount;
        messageCallback.call(name + " recieved " +amount + " damage");
        checkIfDied();
    }
    private void checkIfDied()
    {
        if(currentHealth<=0)
        {
            onDeath();
        }
    }

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth(), getAttack(), getDefense());
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return currentHealth;
    }
}

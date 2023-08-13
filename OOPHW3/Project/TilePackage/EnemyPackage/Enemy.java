package Project.TilePackage.EnemyPackage;

import Project.CallbackPackage.ChangedPositionCallback;
import Project.CallbackPackage.EnemyDeathCallback;
import Project.CallbackPackage.GetTileCallback;
import Project.CallbackPackage.MessageCallback;
import Project.TilePackage.PlayerPackage.Player;
import Project.TilePackage.Position;
import Project.TilePackage.Unit;

public abstract class Enemy extends Unit {
    protected Player player;
    protected int experienceValue;
    EnemyDeathCallback enemyDeathCallback;
    protected GetTileCallback getTileCallback;

    protected Enemy(char tile, String name, int healthCapacity, int attack,int defense, int experienceValue) {
        super(tile, name, healthCapacity, attack, defense);
        this.experienceValue = experienceValue;
    }
    public int getExperienceValue()
    {
        return experienceValue;
    }
    public void initialize(Position position, MessageCallback messageCallback, EnemyDeathCallback enemyDeathCallback, ChangedPositionCallback changedPositionCallback, GetTileCallback getTileCallback) {
        super.initialize(position, messageCallback,changedPositionCallback);
        this.enemyDeathCallback = enemyDeathCallback;
        this.getTileCallback = getTileCallback;

    }
    public void initPlayer(Player player){
        this.player = player;
    }

    public abstract void playTurn();


    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }


    @Override
    public void processStep() {
        //notify board
    }

    @Override
    public void onDeath() {
        messageCallback.call(player.getName() + " killed enemy " + this.name);
        enemyDeathCallback.call(this);

    }

    @Override
    public void visit(Player p) {
        battle(p);
    }

    @Override
    public void visit(Enemy e) {

    }
}

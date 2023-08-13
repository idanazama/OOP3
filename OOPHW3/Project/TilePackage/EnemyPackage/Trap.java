package Project.TilePackage.EnemyPackage;

public class Trap extends Enemy {
    private int visibilityTime;
    private int invisibiltyTime;
    private int ticksCount;
    private boolean visible;
    private final char empty = '.';
    private char tempTile;
    public Trap(char tile, String name, int healthCapacity, int attack, int defense, int experienceValue,int visibilityTime,int invisibiltyTime) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibiltyTime = invisibiltyTime;
        ticksCount = 0;
        visible = true;
        tempTile = tile;
    }

    @Override
    public void playTurn() {
        visible = ticksCount<visibilityTime;
        if(ticksCount==(visibilityTime+invisibiltyTime))
        {
            ticksCount = 0;
        }
        else
        {
            ticksCount ++;
        }
        if(position.calculateRange(player.getPosition())<2)
        {
            battle(player);
        }
        if(visible){
            this.tile = tempTile;
        }
        else{
            this.tile = empty;
        }
    }
}

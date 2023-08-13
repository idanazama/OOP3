package Project.TilePackage.EnemyPackage;

import Project.TilePackage.HeroicUnit;

public class Boss extends Monster implements HeroicUnit {

    private int abilityFrequency;
    private int combatTicks;
    public Boss(char tile, String name, int healthCapacity, int attack, int defense, int experienceValue, int visionRange,int abilityFrequency) {
        super(tile, name, healthCapacity, attack, defense, experienceValue, visionRange);
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;

    }

    @Override
    public void playTurn() {
        if (position.calculateRange(player.getPosition()) < visionRange) {
            if(combatTicks == abilityFrequency){
                combatTicks = 0;
                castAbility();
            }
            else{
                combatTicks++;
                int dx = position.getX() - player.getPosition().getX();
                int dy = position.getY() - player.getPosition().getY();
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        moveLeft();
                    } else {
                        moveRight();
                    }
                } else {
                    if (dy > 0) {
                        moveUp();
                    } else {
                        moveDown();
                    }
                }
            }
        } else {
            performRandomMovement();
        }
    }
    @Override
    public void castAbility() {
        if(position.calculateRange(player.getPosition()) < visionRange){
            messageCallback.call(name + " is using his special ability!");
            player.hitBySpecialAbility(attack);
        }
    }
}

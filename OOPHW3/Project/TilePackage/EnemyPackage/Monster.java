package Project.TilePackage.EnemyPackage;

import Project.TilePackage.Position;
import Project.TilePackage.Tile;

public class Monster extends Enemy {


    protected int visionRange;
    public Monster(char tile, String name, int healthCapacity, int attack, int defense, int experienceValue , int visionRange ) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visionRange = visionRange;
    }

    @Override
    public void playTurn() {
        if (position.calculateRange(player.getPosition()) < visionRange) {
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
        } else {
            performRandomMovement();
        }
    }

    protected void moveLeft() {
        Position newPos = new Position(position.getX() - 1 , position.getY());
        Tile t = getTileCallback.call(newPos);
        t.accept(this);
    }

    protected void moveRight() {
        Position newPos = new Position(position.getX() + 1 , position.getY());
        Tile t = getTileCallback.call(newPos);
        t.accept(this);
    }

    protected void moveUp() {
        Position newPos = new Position(position.getX()  , position.getY()-1);
        Tile t = getTileCallback.call(newPos);
        t.accept(this);
    }

    protected void moveDown() {
        Position newPos = new Position(position.getX()  , position.getY()+1);
        Tile t = getTileCallback.call(newPos);
        t.accept(this);
    }

    protected void performRandomMovement() {
        int randomDirection = (int) (Math.random() * 4);

        switch (randomDirection) {
            case 0:
                moveLeft();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveUp();
                break;
            case 3:
                moveDown();
                break;
            default:
                // Stay at the same place (no movement)
                break;
        }
    }
}

package Project.TilePackage;

public class  Wall extends Tile{
    protected Wall(Position p) {
        super('#');
        initialize(p);
    }

    @Override
    public void accept(Unit unit) {
    }
}

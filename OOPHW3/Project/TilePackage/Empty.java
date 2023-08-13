package Project.TilePackage;

public class Empty extends Tile{
    public Empty(Position p) {
        super('.');
        initialize(p);
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}

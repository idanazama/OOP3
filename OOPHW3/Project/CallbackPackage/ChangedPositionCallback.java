package Project.CallbackPackage;

import Project.TilePackage.Position;
import Project.TilePackage.Unit;

public interface ChangedPositionCallback {
    public void call(Unit u, Position to);
}

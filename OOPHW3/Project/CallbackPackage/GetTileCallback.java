package Project.CallbackPackage;

import Project.TilePackage.Position;
import Project.TilePackage.Tile;

public interface GetTileCallback {
    public Tile call(Position p);
}

package Project.CallbackPackage;

import Project.TilePackage.EnemyPackage.Enemy;

import java.util.List;

public interface GetEnemiesCallback {
    List<Enemy> call();
}

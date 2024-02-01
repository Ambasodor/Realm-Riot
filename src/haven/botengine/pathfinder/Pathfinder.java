package haven.botengine.pathfinder;

import haven.Coord2d;
import haven.Gob;

public interface Pathfinder {

    void move(float x, float y);

    void moveTo(Coord2d coordinate);

    void moveTo(Gob gob);
    void moveToClay(float x, float y);
    void moveToClay(Coord2d coordinate);
    void moveToClay(Gob gob);

    void moveTo(float x, float y);
    void moveToMussel(Gob gob);

    void moveToMussel(float x, float y);
    void moveToMussel(Coord2d coordinate);

    void sidestep(double distance);

    void waitUntilStops();

}

package haven.botengine.entities;

import haven.Coord;
import haven.Coord2d;
import haven.Gob;

import static haven.OCache.posres;

public class DoorData {

    private final Gob building;
    private final Coord2d offset;
    private final int id;

    public DoorData(Gob building, Coord2d offset, int id) {
        this.building = building;
        this.offset = offset;
        this.id = id;
    }

    public static DoorData from(Gob gob) {
        switch (gob.getres().name) {
            case "gfx/terobjs/arch/logcabin": return new DoorData(gob, new Coord2d(22, 0), 16);
            case "gfx/terobjs/arch/timberhouse": return new DoorData(gob, new Coord2d(33, 0), 16);
            case "gfx/terobjs/arch/stonestead": return new DoorData(gob, new Coord2d(44, 0), 16);
            case "gfx/terobjs/arch/stonemansion": return new DoorData(gob, new Coord2d(48, 0), 16);
            case "gfx/terobjs/arch/greathall": return new DoorData(gob, new Coord2d(77, 28), 16);
            case "gfx/terobjs/arch/stonetower": return new DoorData(gob, new Coord2d(36, 0), 16);
            default: return null;
        }
    }

    public Coord2d getOffset() {
        return offset;
    }

    public int getId() {
        return id;
    }
    public Coord getDoorCoordinates() {
        return building.rc.add((offset).rot(building.a)).floor(posres);
    }

}
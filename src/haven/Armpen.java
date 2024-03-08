package haven;

import haven.ItemInfo;
import haven.res.ui.tt.wpn.info.WeaponInfo;

public class Armpen extends WeaponInfo {
    public static final Armpen NOPEN = new Armpen(null, 0.0);

    public static class Fac implements InfoFactory {
        @Override
        public ItemInfo build(Owner owner, Raw raw, Object... args) {
            return new Armpen(owner, ((Number) args[1]).doubleValue() * 0.01D);
        }
    }

    public final double deg;

    public Armpen(Owner owner, double deg) {
        super(owner);
        this.deg = deg;
    }

    public static Armpen mkinfo(Owner owner, Object... args) {
        return new Armpen(owner, ((Number) args[1]).doubleValue() * 0.01D);
    }

    public String wpntips() {
        return String.format("Armor penetration: %.1f%%", this.deg * 100.0D);
    }

    public int order() {
        return 100;
    }
}

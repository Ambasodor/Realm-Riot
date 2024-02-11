package haven.sprites;

import haven.*;
import haven.Composite;
import haven.render.Pipe;

import java.awt.*;
import java.util.Arrays;

public class LinePathSprite extends Sprite implements PView.Render2D {


    public static Color MAINCOLOR = OptWnd.myselfChaseVectorColorOptionWidget.currentColor;
    public static Color FOECOLOR = OptWnd.foeChaseVectorColorOptionWidget.currentColor;
    public static Color FRIENDCOLOR = OptWnd.friendChaseVectorColorOptionWidget.currentColor;
    public static Color UNKNOWNCOLOR = OptWnd.unknownChaseVectorColorOptionWidget.currentColor;
    private final LinMove linMove;

    public LinePathSprite(Gob gob, LinMove linMove) {
        super(gob, null);
        this.linMove = linMove;
    }

    private static final String[] IGNOREDCHASEVECTORS = {
            "gfx/terobjs/vehicle/cart",
            "gfx/terobjs/vehicle/wagon",
            "gfx/terobjs/vehicle/wheelbarrow",
    };

    public void draw(GOut g, Pipe state) {
        if (OptWnd.drawChaseVectorsCheckBox.a) {
            try {
                Gob gob = (Gob) owner;
                UI ui = gob.glob.sess.ui;
                if (ui != null) {
                    MapView mv = ui.gui.map;
                    if (mv != null) {
                        Moving lm = gob.getattr(Moving.class);
                        if (lm != null) {
                            Coord2d mc = mv.pllastcc;
                            if (mc != null) {
                                if (gob.isMe()) {
                                    final Coord3f pc = gob.getc();
                                    Coord pla = pc.round2();
                                    double x = mc.x - pc.x;
                                    double y = -(mc.y - pc.y);
                                    Coord ChaserCoord = mv.screenxf(pc).round2();
                                    Coord TargetCoord = mv.screenxf(mc).round2();
                                    Color chaserColor = MAINCOLOR;
                                    g.chcolor(MAINCOLOR);
                                    g.line(ChaserCoord, TargetCoord, 1.5);
                                }
                                if (gob.getres().name.equals("gfx/terobjs/vehicle/snekkja")) {
                                    for (Gob occupant : gob.occupants) {
                                        if (occupant.getPoses().contains("snekkjaman0")) {
                                            if (occupant.isMe()) {
                                                final Coord3f pc = gob.getc();
                                                Coord pla = pc.round2();
                                                double x = mc.x - pc.x;
                                                double y = -(mc.y - pc.y);
                                                Coord ChaserCoord = mv.screenxf(pc).round2();
                                                Coord TargetCoord = mv.screenxf(mc).round2();
                                                Color chaserColor = MAINCOLOR;
                                                g.chcolor(MAINCOLOR);
                                                g.line(ChaserCoord, TargetCoord, 1.5);
                                            }
                                        }
                                    }
                                }
                                if (gob.getres().name.equals("gfx/terobjs/vehicle/knarr")) {
                                    for (Gob occupant : gob.occupants) {
                                        if (occupant.getPoses().contains("knarrman9")) {
                                            if (occupant.isMe()) {
                                                final Coord3f pc = gob.getc();
                                                Coord pla = pc.round2();
                                                double x = mc.x - pc.x;
                                                double y = -(mc.y - pc.y);
                                                Coord ChaserCoord = mv.screenxf(pc).round2();
                                                Coord TargetCoord = mv.screenxf(mc).round2();
                                                Color chaserColor = MAINCOLOR;
                                                g.chcolor(MAINCOLOR);
                                                g.line(ChaserCoord, TargetCoord, 1.5);
                                            }
                                        }
                                    }
                                }
                                if (gob.getres().name.equals("gfx/terobjs/vehicle/rowboat")) {
                                    for (Gob occupant : gob.occupants) {
                                        if (occupant.getPoses().contains("rowboat-d") || occupant.getPoses().contains("rowing")) {
                                            if (occupant.isMe()) {
                                                final Coord3f pc = gob.getc();
                                                Coord pla = pc.round2();
                                                double x = mc.x - pc.x;
                                                double y = -(mc.y - pc.y);
                                                Coord ChaserCoord = mv.screenxf(pc).round2();
                                                Coord TargetCoord = mv.screenxf(mc).round2();
                                                Color chaserColor = MAINCOLOR;
                                                g.chcolor(MAINCOLOR);
                                                g.line(ChaserCoord, TargetCoord, 1.5);
                                            }
                                        }
                                    }
                                }
                                if (gob.getres().name.equals("gfx/terobjs/vehicle/coracle")) {
                                    for (Gob occupant : gob.occupants) {
                                        if (occupant.getPoses().contains("coracle-idle") || occupant.getPoses().contains("coraclerowan")) {
                                            if (occupant.isMe()) {
                                                final Coord3f pc = gob.getc();
                                                Coord pla = pc.round2();
                                                double x = mc.x - pc.x;
                                                double y = -(mc.y - pc.y);
                                                Coord ChaserCoord = mv.screenxf(pc).round2();
                                                Coord TargetCoord = mv.screenxf(mc).round2();
                                                Color chaserColor = MAINCOLOR;
                                                g.chcolor(MAINCOLOR);
                                                g.line(ChaserCoord, TargetCoord, 1.5);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    public boolean getGobPoses(Gob gob, String string) {
        Drawable d = gob.getattr(Drawable.class);

        if (d instanceof Composite) {
            Composite comp = (Composite) d;
            for (ResData rd : comp.oldposes) {
                try {
                    if (rd.res.get().name.contains(string)) {
                        return true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return false;
    }
}

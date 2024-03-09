package haven.automated;

import haven.Buff;
import haven.Coord;
import haven.Equipory;
import haven.Fightview;
import haven.GItem;
import haven.Glob;
import haven.ItemInfo;
import haven.Label;
import haven.Reflect;
import haven.TextEntry;
import haven.UI;
import haven.Widget;
import haven.Window;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class CleaveDamageCalc extends Window {

    private TextEntry value;
    public boolean stop;
    private final Label currentdmg;
    private final UI ui;

    public CleaveDamageCalc(UI ui) {
        super(new Coord(400, 50), "Cleave Damage Calculator");
        this.stop = false;
        this.ui = ui;

        Widget prev;
        prev = add(new Label("Set Armor Total:"), 5, 5);

        value = add(new TextEntry(UI.scale(100), "0") {
            public boolean keydown(KeyEvent ev) {
                if ((ev.getKeyChar() >= KeyEvent.VK_0 && ev.getKeyChar() <= KeyEvent.VK_9) || ev.getKeyChar() == '\b' || ev.getKeyChar() == KeyEvent.VK_DELETE) {
                    return (buf.key(ev));
                } else if (ev.getKeyChar() == '\n') {
                    return (true);
                }
                return (false);
            }
        }, prev.pos("ur").adds(2, 0));

        currentdmg = new Label("Current damage: No target");
        add(currentdmg, prev.pos("bl").adds(0, 2));
    }

    @Override
    public void tick(final double dt) {
        super.tick(dt);
        if (stop) return;
        try {
            List<String> errors = new ArrayList<>();

            /*Oppening check*/
            Fightview fv = ui.gui.fv;
            double bopp = 0;
            double ropp = 0;
            if (fv == null) {
                errors.add("No fight");
            } else {
                Fightview.Relation rel = fv.current;
                if (rel == null) {
                    errors.add("No targets");
                } else {
                    bopp = getBlueOppenings(rel);
                    ropp = getRedOppenings(rel);
                    //SUCCESS
                }
            }

            /*Weapon damage check*/
            GItem weap = weap();
            Integer dmgValue = null;
            Double penValue = null;
            if (weap == null) {
                errors.add("No weapon");
            } else {
                dmgValue = getDmg(weap);
                if (dmgValue == null) {
                    errors.add("Weapon has no damage");
                } else {
                    //SUCCESS
                }

                //Armpen check
                {
                    penValue = armpen(weap);
                }
            }

            if (!errors.isEmpty()) {
                currentdmg.settextwrap(String.join("\n", errors), 300);
            } else {
                List<String> sb = new ArrayList<>();
                {
                    double dmg = calculatedmg(dmgValue, penValue != null ? penValue : 0, getstrength(), weaponQ(weap), bopp, ropp);

                    DecimalFormat df = new DecimalFormat("#.##");
                    String result = df.format(dmg);
                    sb.add("Current damage: " + result);
                }
                currentdmg.settextwrap(String.join("\n", sb), 300);
            }
            pack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getValue() {
        String v = value.text();
        return (v.isEmpty() ? 0 : Integer.parseInt(v));
    }

    public double calculatedmg(int basedmg, double armpen, double blue, double red) {
        double op = Math.pow(1.0D - (1.0D - blue) * (1.0D - red), 2.0D);

        //double armorpen = (((basedmg * sqrt(sqrt((double) str * wpnQ) / 10)) * 1.5) * op) * 0.1D;

        double fdmg = (basedmg * 1.5) * op - getValue();
        return (Math.max(fdmg, 0));
    }

    public double calculatedmg(int loftarDMG, double armpen, int str, double wpnQ, double blue, double red) {
        double op = Math.pow(1.0D - (1.0D - blue) * (1.0D - red), 2.0D);

        //double armorpen = (((basedmg * sqrt(sqrt((double) str * wpnQ) / 10)) * 1.5) * op) * 0.1D;

//        double fixDmg = loftarDMG * sqrt(sqrt(str * wpnQ) / 10D) / sqrt(sqrt(wpnQ * wpnQ) / 10D);
        double fixDmg = loftarDMG * sqrt(sqrt(str / wpnQ));
        double fdmg = (fixDmg * 1.5) * op - getValue();

        return (Math.max(fdmg, 0));
    }

    public boolean isb12() {
        Equipory e = ui.gui.getequipory();
        if (e.slots[6] != null) {
            String name = e.lweap.getName();
            if (name != null) {
                if (name.contains("Battle")) {
                    return (true);
                }
            }
        }
        return (false);
    }

    public boolean iscutblade() {
        Equipory e = ui.gui.getequipory();
        if (e.slots[6] != null) {
            String name = e.lweap.getName();
            if (name != null) {
                if (name.contains("Cut")) {
                    return (true);
                }
            }
        }
        return (false);
    }

    public boolean isbronzesword() {
        Equipory e = ui.gui.getequipory();
        if (e.slots[6] != null) {
            String name = e.lweap.getName();
            if (name != null) {
                if (name.contains("Bronze")) {
                    return true;
                }
            }
        }
        if (e.slots[7] != null) {
            String name = e.rweap.getName();
            if (name != null) {
                if (name.contains("Bronze")) {
                    return true;
                }
            }
        }
        return (false);
    }

    public GItem getbronzesword() {
        Equipory e = ui.gui.getequipory();
        if (e.slots[6] != null) {
            String name = e.lweap.getName();
            if (name != null) {
                if (name.contains("Bronze")) {
                    return (e.slots[6].item);
                }
            }
        }
        if (e.slots[7] != null) {
            String name = e.rweap.getName();
            if (name != null) {
                if (name.contains("Bronze")) {
                    return (e.slots[7].item);
                }
            }
        }
        return (null);
    }

    public Integer getDmg(GItem wpn) {
        List<ItemInfo> info = wpn.info();
        return (ItemInfo.findall("Damage", info).stream().findFirst().map(inf -> (Integer) Reflect.getFieldValue(inf, "dmg")).orElse(null));
    }

    public double weaponQ(GItem wpn) {
        return (wpn.quality().q); //can produce a nullpointerexception if weapon can not have a quality
    }

    public Double armpen(GItem wpn) {
        List<ItemInfo> info = wpn.info();
        return (ItemInfo.findall("Armpen", info).stream().findFirst().map(inf -> (Double) Reflect.getFieldValue(inf, "deg")).orElse(null));
    }

    private int getstrength() {
        final Glob.CAttr strattr = ui.sess.glob.getcattr("str");
        return (strattr.comp);
    }

    public GItem weap() {
        if (isb12() || iscutblade()) {
            return (ui.gui.getequipory().lweap);
        }
        if (isbronzesword()) {
            return (getbronzesword());
        }
        return (null);
    }

    public void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ignored) {
        }
    }

    public double getBlueOppenings(Fightview.Relation rel) {
        return (getOppenings(rel, "dizzy"));
    }

    public double getRedOppenings(Fightview.Relation rel) {
        return (getOppenings(rel, "cornered"));
    }

    public double getOppenings(Fightview.Relation rel, String regName) {
        return (rel.buffs.getchilds(Buff.class).stream().filter(b -> b.res.get().name.matches(".*" + regName + ".*")).findFirst().map(buff -> (buff.ameter >= 0) ? Double.valueOf(buff.ameter / 100.0) : buff.getAmeteri().get()).orElse((double) 0));
    }

    @Override
    public void wdgmsg(Widget sender, String msg, Object... args) {
        if ((sender == this) && (Objects.equals(msg, "close"))) {
            stop();
            CleaveDamageCalc c = ui.gui.cleaveDamageCalc;
            if (c != null) {
                if (!c.stop)
                    c.stop();
                ui.gui.cleaveDamageCalc = null;
            }
        } else
            super.wdgmsg(sender, msg, args);
    }

    @Override
    public boolean globtype(char key, java.awt.event.KeyEvent ev) {
        if (key == KeyEvent.VK_ESCAPE) {
            hide();
            return (true);
        }
        return (super.globtype(key, ev));
    }

    public void stop() {
        stop = true;
        if (ui.gui.map.pfthread != null) {
            ui.gui.map.pfthread.interrupt();
        }
        this.reqdestroy();
    }
}

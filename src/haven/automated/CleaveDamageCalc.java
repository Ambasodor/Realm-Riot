package haven.automated;

import com.jcraft.jorbis.Info;
import haven.*;
import haven.botengine.GroovyScriptList;
import haven.res.ui.tt.wpn.Damage;
import haven.res.ui.tt.wpn.info.WeaponInfo;

import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static haven.Fightsess.blueopp;
import static haven.Fightsess.redopp;
import static haven.JOGLPanel.scriptRunnerjava;
import static haven.OptWnd.*;
import static java.lang.Math.sqrt;

public class CleaveDamageCalc extends Window implements Runnable {

    private String value;
    public boolean stop;
    private final Label currentdmg;
    private final UI ui;
    public static final Armpen NOPEN = new Armpen(null, 0.0);
    public double dmg = 0;
    public CleaveDamageCalc(UI ui) {
        super(new Coord(400, 50), "Cleave Damage Calculator");
        this.stop = false;
        this.ui = ui;
        this.value = "0";
        currentdmg = new Label("Current damage: No target");
        add(currentdmg, new Coord(5, 5));

        Widget prev;

        prev = add(new Label("Set Armor Total:"), 5, 25);

        prev = add(new TextEntry(UI.scale(100), value) {
            @Override
            protected void changed() {
                setValue(this.buf.line());
            }

            public boolean keydown(KeyEvent ev) {
                if ((ev.getKeyChar() >= KeyEvent.VK_0 && ev.getKeyChar() <= KeyEvent.VK_9) || ev.getKeyChar() == '\b' || ev.getKeyChar() == KeyEvent.VK_DELETE) {
                    return buf.key(ev);
                } else if (ev.getKeyChar() == '\n') {
                    return true;
                }
                return (false);
            }
        }, prev.pos("ur").adds(2, 0));
    }

    @Override
    public void run() {
        while (!stop) {
            if (ui != null) {
                if (ui.gui != null) {
                    if (ui.gui.fv != null && ui.gui.fv.current != null) {
                        double bopp = getBlueOppenings();
                        double ropp = getRedOppenings();
                        if (bopp <= 0 && ropp <= 0) {
                            currentdmg.settext("No oppenings");
                        } else {
                            if (isb12()) {
                                dmg = calculatedmg(b12dmg());
                            } else if (iscutblade()) {
                                dmg = calculatedmg(cutdmg());
                            } else if (isbronzesword()) {
                                dmg = calculatedmg(bronzedmg());
                            } else {
                                dmg = 0;
                            }
                            DecimalFormat df = new DecimalFormat("#.##");
                            String result = df.format(dmg);
                            currentdmg.settext("Current damage: " + result + " units.");
                        }
                    } else {
                        currentdmg.settext("No targets");
                    }

                    sleep(500);
                }
            }
        }
    }

    public void setValue(String value) {
        this.value = value;
    }
    public double calculatedmg(int basedmg){
        double openingweight = 0;
        double op = Math.pow(1.0D - (1.0D - getBlueOppenings()) * (1.0D - getRedOppenings()), 2.0D);
        double armorpen = (((basedmg * sqrt(sqrt((double) getstrength() * weaponQ())/10)) * 1.5) * op) * 0.1D;
        if ((((basedmg * sqrt(sqrt((double) getstrength() * weaponQ())/10)) * 1.5) * op - Integer.parseInt(value)) < 0) {
            return 0;
        } else if ((((basedmg * sqrt(sqrt((double) getstrength() * weaponQ())/10)) * 1.5) * op - Integer.parseInt(value)) >= 0){
            return ((basedmg * sqrt(sqrt((double) getstrength() * weaponQ())/10)) * 1.5) * op - Integer.parseInt(value);
        }
        return 0;
    }
    public boolean isb12(){
        Equipory e = ui.gui.getequipory();
        if (e.slots[6] != null) {
            String name = e.lweap.getName();
            if (name != null) {
                if (name.contains("Battle")) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean iscutblade(){
        Equipory e = ui.gui.getequipory();
        if (e.slots[6] != null) {
            String name = e.lweap.getName();
            if (name != null) {
                if (name.contains("Cut")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isbronzesword(){
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
        return false;
    }
    public GItem getbronzesword(){
        Equipory e = ui.gui.getequipory();
        if (e.slots[6] != null) {
            String name = e.lweap.getName();
            if (name != null) {
                if (name.contains("Bronze")) {
                    return e.slots[6].item;
                }
            }
        }
        if (e.slots[7] != null) {
            String name = e.rweap.getName();
            if (name != null) {
                if (name.contains("Bronze")) {
                    return e.slots[7].item;
                }
            }
        }
        return null;
    }
    public int b12dmg(){
        return 150;
    }
    public int cutdmg(){
        return 135;
    }
    public int bronzedmg(){
        return 90;
    }
    public double weaponQ(){
        if (weap() != null) {
            return weap().quality().q;
        }
        return 0;
    }
    public double armpen(){
        return weap().getinfo(Armpen.class).orElse(Armpen.NOPEN).deg;
    }
    private int getstrength() {
        final Glob.CAttr strattr = ui.sess.glob.getcattr("str");
        return strattr.comp;
    }
    public GItem weap() {
        if (isb12() || iscutblade()) {
            return ui.gui.getequipory().lweap;
        }
        if (isbronzesword()){
            return getbronzesword();
        }
        return null;
    }
    public void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ignored) {
        }
    }
    public double getBlueOppenings() {
        if (blueopp != -1){
            return (double) blueopp /100;
        }
        return 0;
    }
    public double getRedOppenings() {
        if (redopp != -1){
            return (double) redopp /100;
        }
        return 0;
    }
    @Override
    public void wdgmsg(Widget sender, String msg, Object... args) {
        if ((sender == this) && (Objects.equals(msg, "close"))) {
            if (ui.gui.cleaveDamageCalc != null) {
                ui.gui.cleaveDamageCalc.stop();
                ui.gui.cleaveDamageCalc.reqdestroy();
                ui.gui.cleaveDamageCalc = null;
                ui.gui.cleaveDamageThread = null;
            }
        } else
            super.wdgmsg(sender, msg, args);
    }

    @Override
    public boolean globtype(char key, java.awt.event.KeyEvent ev) {
        if (key == 27) {
            hide();
            return true;
        }
        return super.globtype(key, ev);
    }

    public void stop() {
        stop = true;
        if (ui.gui.map.pfthread != null) {
            ui.gui.map.pfthread.interrupt();
        }
        this.destroy();
    }
}

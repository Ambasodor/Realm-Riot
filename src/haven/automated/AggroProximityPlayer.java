package haven.automated;


import haven.*;

import java.util.*;
import java.util.stream.Collectors;

import static haven.OCache.posres;
import static haven.OptWnd.proximityaggroPVPCheckBox;
import static haven.OptWnd.proximityradiusSLIDER;
import static haven.automated.AUtils.rightClick;
//## CEDINER CODE, TY FOR HELP
public class AggroProximityPlayer implements Runnable {
    private final GameUI gui;

    public AggroProximityPlayer(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        //gui.menu.wdgmsg("act", "agrro"); //turn sword on
        gui.map.new Hittest(gui.ui.mc) {
            @Override
            protected void hit(final Coord pc, final Coord2d mc, final ClickData inf) {
                //- check for inf gob
                Fightview fv = gui.fv;
                int r = proximityradiusSLIDER.val;
                OCache oc = gui.map.glob.oc;
                Gob target = null;
                synchronized (oc) {
                    for (Gob gob : oc) {
                        //- check for player target
                        double dist = Coord2d.of(gob.getrc()).dist(mc);
                        if (dist <= r) {
                            if (target == null || Coord2d.of(target.getrc()).dist(mc) > dist) {
                                if (fv != null) {
                                    try {
                                        Fightview.Relation rel = fv.lsrel.getFirst();
                                        if (rel.gobid == gob.id) //если цель - текущий противник
                                            continue;   //пропускаем
                                    } catch (NoSuchElementException e) {
                                    }
                                    //вариант, если нужно выбрать цель не из списка текущих противников
                                /*for (Fightview.Relation rel : fv.lsrel) {
                                    if (rel.gobid == gob.id)
                                        continue;
                                }*/
                                }
                                target = gob;
                            }
                        }
                    }
                }
                if (target != null)
                    AUtils.attackGob(gui, target);
            }
        }.run();
    }
    }


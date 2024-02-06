package haven.botengine;

import haven.Button;
import haven.Window;
import haven.*;

import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static haven.JOGLPanel.*;
import static haven.OptWnd.*;

public class GroovyScriptList extends Window {
    public GroovyList groovyList;
    public List<GroovyListItem> scripts = new ArrayList<>(50);
    public static String choosed = null;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private boolean revery = false;
    private boolean rin = false;
    private String value;
    public Dropbox<String> TimeMarkerDropbox;

    public GroovyScriptList() {
        super(new Coord(300, 380), "Groovy Bot System");
        this.value = "";
        Widget prev;
        add(new STARTBUTTON(50, "Start", groovyList), new Coord(9, 2)).tooltip = RichText.render("Starts the selected task with your settings.\n$col[218,163,0]{Note:} $col[185,185,185]{If you want to start task by default, choose only task and press.}\n$col[218,163,0]{Note:} $col[185,185,185]{If you want to run a task and have it repeat EVERY x seconds, minutes, hours, days, select the Run Every x time setting and enter the time in the Set Time setting}\n$col[218,163,0]{Note:} $col[185,185,185]{If you want start task NOT INSTANTLY and repeat every x seconds, minutes, hours, days, select the Run IN x time setting and enter the time in the Set Time setting}", UI.scale(300));
        add(new REFRESHBUTTON(50,"Refresh", groovyList), new Coord(9, 27)).tooltip = RichText.render("Refreshes the list of tasks.\n$col[218,163,0]{Note:} $col[185,185,185]{You don't need to update the list every time you change something in the code of your task.}", UI.scale(300));
        add(new CANCELBUTTON(90,"Cancel task", groovyList), new Coord(9, 52)).tooltip = RichText.render("Cancels only tasks that were run with the run EVERY and run IN settings.\n$col[218,163,0]{Note:} $col[185,185,185]{Does not terminate tasks, does not cancel DEFAULT tasks, only for run EVERY and run IN settings. Before canceling, you must select ALL settings and task as at startup, only then it will be removed from the queue.}", UI.scale(300));
        add(new Label("How To run this tasks:"), new Coord(80, 0));
        CheckBox runevery = new CheckBox("Run EVERY X time") {
            {
                a = false;
            }

            public void set(boolean val) {
                revery = val ? true : false;
                a = val;
            }
        };
        add(runevery, UI.scale(80, 15)).tooltip = RichText.render("With this setting Task starts and repeats once every X seconds, minutes, hours, days, can be set in Set Time setting.", UI.scale(300));
        CheckBox runin = new CheckBox("Run IN X time") {
            {
                a = false;
            }

            public void set(boolean val) {
                rin = val ? true : false;
                a = val;
            }
        };
        add(runin, UI.scale(80, 35)).tooltip = RichText.render("With this setting Task starts after X seconds, minutes, hours, days and repeats once every X seconds, minutes, hours, days, can be set in Set Time setting.", UI.scale(300));
        prev = add(new Label("Set Time:"), 200, 0);
        prev = add(new TextEntry(UI.scale(40), value) {
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
        }, prev.pos("ur").adds(0, 0));
        add(TimeMarkerDropbox = new Dropbox<String>(UI.scale(40), timemark.size(), UI.scale(17)) {
            {
                super.change(timemark.get(timemarkSet));
            }
            @Override
            protected String listitem(int i) {
                return timemark.get(i);
            }
            @Override
            protected int listitems() {
                return timemark.size();
            }
            @Override
            protected void drawitem(GOut g, String item, int i) {
                g.aimage(Text.renderstroked(item.toString()).tex(), Coord.of(UI.scale(3), g.sz().y / 2), 0.0, 0.5);
            }
            @Override
            public void change(String item) {
                super.change(item);
                timemarkSet = timemark.indexOf(item);
            }
        }, prev.pos("ul").adds(0, 20)).tooltip = RichText.render("S - second, M - minutes, H - hours, D - days.", UI.scale(300));
        groovyList = new GroovyList(270, 12,this);
        add(groovyList, new Coord(10, 80));
    }

    @Override
    public void wdgmsg(Widget sender, String msg, Object... args) {
        if ((sender == this) && (Objects.equals(msg, "close"))) {
            hide();
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
    public void addConds(List<String> files) {
            for (int i = 0; i < files.size(); i++) {
                GroovyListItem qitem = new GroovyListItem(files.get(i));
                boolean dontadd = false;

                for (GroovyListItem item : groovyList.files) {
                    if (qitem.name.equals(item.name)) {
                        dontadd = true;
                    }
                }
                if (dontadd == false) {
                    groovyList.files.add(qitem);
                }
            }
    }
    public void setValue(String value) {
        this.value = value;
    }
    public void clearConds(){
        groovyList.files.clear();
    }
    private void runNow(String scriptName) {
        scriptRunnerjava.schedule(scriptName);
    }
    private void scheduleEvery(String scriptName, Duration time) {
        scriptRunnerjava.scheduleEvery(scriptName, time);
    }
    private void scheduleIn(String scriptName, Duration time) {
        scriptRunnerjava.scheduleIn(scriptName, time);
    }
    public boolean cancel(String task) {
        boolean cancelled = Optional.ofNullable(scriptRunnerjava.scheduledTasks.get(task))
                .map(scheduledTask -> scheduledTask.cancel(false))
                .orElse(false);

        if (cancelled) {
            scriptRunnerjava.scheduledTasks.remove(task);
        }

        return cancelled;
    }
    public void starts() {
        if (!rin && !revery && choosed != null) {
            runNow(choosed);
        }
        if (choosed == null){
            ui.gui.msg("Cannot run task because script not choosed!", msgRed);
        }
        if (rin && revery){
            ui.gui.msg("Cannot run task if two checkbox checked!", msgRed);
        }
        if (revery && !value.isEmpty()){
            StringBuilder tasksMessage = new StringBuilder(value + timemark.get(timemarkSet));
            scheduleEvery(choosed, Duration.parse("PT" + tasksMessage.toString()));
        }
        if (rin && !value.isEmpty()){
            StringBuilder tasksMessage = new StringBuilder(value + timemark.get(timemarkSet));
            scheduleIn(choosed, Duration.parse("PT" + tasksMessage.toString()));
        }
    }
    public void cancel(){
        if (choosed != null && revery && !value.isEmpty()) {
            cancel(choosed + "-every-" + value + timemark.get(timemarkSet));
            ui.gui.msg("Task cancelled from schedule!", msgYellow);
        }
        if (choosed != null && rin && !value.isEmpty()) {
            cancel(choosed + "-in-" + value + timemark.get(timemarkSet));
            ui.gui.msg("Task cancelled from schedule!", msgYellow);
        }
        if (choosed == null || value.isEmpty() || revery && rin) {
            ui.gui.msg("Choose correct task with same timings at start!", msgRed);
        }
    }
    public void refresh() {
        List<String> allscr = getAllScripts();
        clearConds();
        ui.gui.groovyScriptList.addConds(allscr);
    }
    public List<String> getAllScripts() {
        Path testFilePath = Paths.get("scripts");
        File[] listOfFiles = testFilePath.toFile().listFiles();
        List<String> files = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".groovy")) {
                files.add(file.getName().replace(".groovy", ""));
            }
        }
        if (!files.isEmpty()) {
            return files;
        }
        return List.of();
    }
    private class STARTBUTTON extends Button {
        public final GroovyList tgt;

        public STARTBUTTON(int w, String title, GroovyList tgt) {
            super(w, title);
            this.tgt = tgt;
        }

        @Override
        public void click() {
            starts();
        }
    }

    private class REFRESHBUTTON extends Button {
        public final GroovyList tgt;

        public REFRESHBUTTON(int w, String title, GroovyList tgt) {
            super(w, title);
            this.tgt = tgt;
        }

        @Override
        public void click() {
            refresh();
        }
    }

    private class CANCELBUTTON extends Button {
        public final GroovyList tgt;

        public CANCELBUTTON(int w, String title, GroovyList tgt) {
            super(w, title);
            this.tgt = tgt;
        }

        @Override
        public void click() {
            cancel();
        }
    }

    private static class GroovyList extends Listbox<GroovyListItem> {
        private static final Coord nameoff = new Coord(0, 5);
        public List<GroovyListItem> files = new ArrayList<>(50);
        private GroovyScriptList groovyList;

        public GroovyList(int w, int h, GroovyScriptList groovyList) {
            super(w, h, 24);
            this.groovyList = groovyList;
        }

        @Override
        protected GroovyListItem listitem(int idx) {
            return files.get(idx);
        }

        @Override
        protected int listitems() {
            return files.size();
        }


        @Override
        protected void drawbg(GOut g) {
            g.chcolor(0, 0, 0, 120);
            g.frect(Coord.z, sz);
            g.chcolor();
        }

        @Override
        protected void drawitem(GOut g, GroovyListItem item, int idx) {
            try {
                g.text(item.name, nameoff);
                g.chcolor();
            } catch (Loading e) {
            }
        }

        @Override
        public void change(GroovyListItem item) {
            if (item != null) {
                super.change(item);
                choosed = item.name;
            }
        }
    }

    public static class GroovyListItem implements Comparable<GroovyListItem> {
        public String name;

        public GroovyListItem(final String name) {
            this.name = name;
        }

        @Override
        public int compareTo(final GroovyListItem o) {
            return this.name.compareTo(o.name);
        }
    }
}

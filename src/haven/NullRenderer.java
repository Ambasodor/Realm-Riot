/*
 *  This file is part of the Haven & Hearth game client.
 *  Copyright (C) 2009 Fredrik Tolf <fredrik@dolda2000.com>, and
 *                     Björn Johannessen <johannessen.bjorn@gmail.com>
 *
 *  Redistribution and/or modification of this file is subject to the
 *  terms of the GNU Lesser General Public License, version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  Other parts of this source tree adhere to other copying
 *  rights. Please see the file `COPYING' in the root directory of the
 *  source tree for details.
 *
 *  A copy the GNU Lesser General Public License is distributed along
 *  with the source tree of which this file is a part in the file
 *  `doc/LPGL-3'. If it is missing for any reason, please see the Free
 *  Software Foundation's website at <http://www.fsf.org/>, or write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *  Boston, MA 02111-1307 USA
 */

package haven;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import haven.render.*;
import haven.render.States;
import haven.render.gl.*;
import haven.render.jogl.*;
import com.jogamp.opengl.GL;
import haven.*;

import java.util.Map;

import java.util.Map;

public class NullRenderer extends GLCanvas implements GLPanel, Console.Directory, UI.Context  {
    private static final boolean dumpbgl = true;
    public boolean aswap;
    private JOGLEnvironment env = null;
    private Area shape;
    private Pipe base, wnd;
    private final Loop main = new Loop(this);

    @Override
    public void setmousepos(Coord c) {

    }

    @Override
    public Map<String, Console.Command> findcmds() {
        return null;
    }

    public static class ProfileException extends Environment.UnavailableException {
        public final String availability;

        public ProfileException(Throwable cause) {
            super("No OpenGL suitable profile is available", cause);
            String a;
            try {
                a = GLProfile.glAvailabilityToString();
            } catch(Throwable t) {
                a = String.valueOf(t);
            }
            this.availability = a;
        }
    }

    private static GLCapabilities mkcaps() {
        GLProfile prof;
        try {
            prof = GLProfile.getMaxProgrammableCore(true);
        } catch(com.jogamp.opengl.GLException e) {
            try {
                /* If not core, let GLEnvironment handle that. */
                prof = GLProfile.getDefault();
            } catch(com.jogamp.opengl.GLException e2) {
                e2.addSuppressed(e);
                throw(new ProfileException(e2));
            }
        }
        GLCapabilities caps = new GLCapabilities(prof);
        caps.setDoubleBuffered(true);
        caps.setAlphaBits(8);
        caps.setRedBits(8);
        caps.setGreenBits(8);
        caps.setBlueBits(8);
        return(caps);
    }

    public NullRenderer() {
        super(mkcaps(), null, null);
        Audio.enabled = false;
        base = new BufPipe();
        base.prep(new FragColor<>(FragColor.defcolor)).prep(new DepthBuffer<>(DepthBuffer.defdepth));
        base.prep(FragColor.blend(new BlendMode()));
        addGLEventListener(new GLEventListener() {
            public void display(GLAutoDrawable d) {
                redraw(d.getGL());
            }

            public void init(GLAutoDrawable d) {
                setAutoSwapBufferMode(false);
                /* XXX: This apparently fixes a scaling problem on
                 * OSX, and doesn't seem to have any effect on
                 * other platforms. It seems like a weird
                 * workaround, and I do wonder if there isn't some
                 * underlying bug in JOGL instead, but it hasn't
                 * broken anything yet, so I guess why not. */
                setSurfaceScale(new float[] {1, 1});
            }

            public void reshape(GLAutoDrawable wdg, int x, int y, int w, int h) {
                Area area = Area.sized(new Coord(x, y), new Coord(w, h));
                shape = area;
                wnd = base.copy();
                wnd.prep(new States.Viewport(area)).prep(new Ortho2D(area));
            }

            public void dispose(GLAutoDrawable wdg) {
            }
        });
        setFocusTraversalKeysEnabled(false);
        newui(null);
    }

    private boolean iswap() {
        return(main.ui.gprefs.vsync.val);
    }

    private final haven.error.ErrorHandler errh = haven.error.ErrorHandler.find();
    private void setenv(JOGLEnvironment env) {
        if(this.env != null)
            this.env.dispose();
        this.env = env;
        if(main.ui != null)
            main.ui.env = env;

        if(errh != null) {
            GLEnvironment.Caps caps = env.caps();
            errh.lsetprop("gl.vendor", caps.vendor);
            errh.lsetprop("gl.version", caps.version);
            errh.lsetprop("gl.renderer", caps.renderer);
            errh.lsetprop("render.caps", caps);
        }
    }

    private void redraw(GL gl) {
        GLContext ctx = gl.getContext();
        GLEnvironment env;
        synchronized(this) {
            if((this.env == null) || (this.env.ctx != ctx)) {
                setenv(new JOGLEnvironment(gl, ctx, shape));
            }
            env = this.env;
            if(!env.shape().equals(shape))
                env.reshape(shape);
        }
    }

    public GLEnvironment env() {return(env);}
    public Area shape() {return(shape);}
    public Pipe basestate() {return(wnd);}

    public void glswap(haven.render.gl.GL gl) {
        boolean iswap = iswap();
        if(main.gldebug)
            haven.render.gl.GLException.checkfor(gl, null);
        if(iswap != aswap)
            ((WrappedJOGL)gl).getGL().setSwapInterval((aswap = iswap) ? 1 : 0);
        if(main.gldebug)
            haven.render.gl.GLException.checkfor(gl, null);
        NullRenderer.this.swapBuffers();
        if(main.gldebug)
            haven.render.gl.GLException.checkfor(gl, null);
    }

    private void renderloop() {
        synchronized (this) {
            notifyAll();
        }
        while (true) {
            long wst = System.nanoTime();
            main.ridletime += System.nanoTime() - wst;
        }
    }

    public void run() {
        Thread drawthread = new HackThread(NullRenderer.this::renderloop, "Render thread");
        drawthread.start();
        try {
            try {
                main.runHeadless();
            } finally {
                drawthread.interrupt();
                drawthread.join();
            }
        } catch(InterruptedException ignored) {}
    }

    public UI newui(UI.Runner fun) {
        return(main.newui(fun));
    }

    public void background(boolean bg) {
        main.bgmode = bg;
    }
}

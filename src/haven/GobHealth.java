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

import java.awt.Color;
import java.util.Random;
import java.awt.*;
import java.awt.image.BufferedImage;

import haven.render.*;
import haven.resutil.CrackTex;

public class GobHealth extends GAttrib implements Gob.SetupMod {
    public final float hp;
	public final Pipe.Op fx;
	private static final Text.Foundry gobhpf = new Text.Foundry(Text.sans.deriveFont(Font.BOLD), 12);

    public GobHealth(Gob g, float hp) {
	super(g);
	this.hp = hp;
	int level = 3 - Math.round(hp * 4);
	if(level >= 0) {
	    Random rnd = g.mkrandoom();
	    this.fx = Pipe.Op.compose(new CrackTex(CrackTex.imgs[Math.min(level, 2)],
						   new Color(0, 0, 0, 255),
						   Coord3f.of((rnd.nextFloat() * 2) - 1, (rnd.nextFloat() * 2) - 1, (rnd.nextFloat() * 2) - 1).norm(),
						   rnd.nextFloat() * (float)Math.PI * 2),
				      new MixColor(255, 0, 0, 64 - (Math.round(hp * 64))));
	} else {
	    this.fx = null;
	}
    }
    
    public Pipe.Op gobstate() {
	return(fx);
    }

    @OCache.DeltaType(OCache.OD_HEALTH)
    public static class $health implements OCache.Delta {
	public void apply(Gob g, OCache.AttrDelta msg) {
	    int hp = msg.uint8();
	    g.setattr(new GobHealth(g, hp / 4.0f));
		g.setattr(new GobHealthInfo(g));
	}
    }

	public BufferedImage text() {
		if(hp < 1) {
			int c = 15 + (int) Math.floor(hp * 240);
			return Utils.outline2(Text.renderstroked(String.format("%d%%", Math.round(100 * hp)), new Color(255, c, c), Color.BLACK, gobhpf).img, Color.BLACK, true);
		}
		return null;
	}
}

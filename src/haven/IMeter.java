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

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.Color;
import java.io.File;
import java.util.*;

public class IMeter extends LayerMeter {
    public static final Coord off = UI.scale(22, 7);
    public static final Coord fsz = UI.scale(101, 24);
    public static final Coord msz = UI.scale(75, 10);
    public final Indir<Resource> bg;
	MeterType type;
	private static final Text.Foundry tipF = new Text.Foundry(Text.sans, 10);
	private Tex tipTex;
	private boolean ponyAlarmPlayed = false;
	private boolean energyAlarmTriggered = false;
	private boolean dangerEnergyAlarmTriggered = false;
	public static int sparshp;
	public static boolean spar = false;

    @RName("im")
    public static class $_ implements Factory {
	public Widget create(UI ui, Object[] args) {
	    Indir<Resource> bg = ui.sess.getres((Integer)args[0]);
	    List<Meter> meters = decmeters(args, 1);
	    return (new IMeter(bg, meters));
	}
    }

    public IMeter(Indir<Resource> bg, List<Meter> meters) {
	super(fsz);
	this.bg = bg;
	set(meters);
    }

//    public void draw(GOut g) {
//	try {
//	    Tex bg = this.bg.get().flayer(Resource.imgc).tex();
//	    g.chcolor(0, 0, 0, 255);
//	    g.frect(off, msz);
//	    g.chcolor();
//	    for(Meter m : meters) {
//		int w = msz.x;
//		w = (int)Math.ceil(w * m.a);
//		g.chcolor(m.c);
//		g.frect(off, new Coord(w, msz.y));
//	    }
//	    g.chcolor();
//	    g.image(bg, Coord.z);
//	} catch(Loading l) {
//	}
//    }
public void draw(GOut g) {
	try {
		Resource res = this.bg.get();
		if (type == null) {
			determineType(res);
		}

		Tex bg = res.layer(Resource.imgc).tex();
		g.chcolor(0, 0, 0, 255);
		g.frect(off, msz);
		g.chcolor();
			/*for (Meter m : meters) {
				int w = msz.x;
				w = (int) Math.ceil(w * m.a);
				g.chcolor(m.c);
				g.frect(off, new Coord(w, msz.y));
			}*/
		for (Meter m : meters) {
			int w = msz.x;
			if (type != null) {
				//System.out.println("meter: " + type + " " + m.a);
				if (type.equals(MeterType.STAMINA)) {
					int w1 = (int) Math.ceil(w * m.a);
					int w2 = (int) (w * Math.max(m.a-0.25,0));
					int w3 = (int) (w * Math.max(m.a-0.50,0));

					g.chcolor(m.c.darker());
					g.frect(off, new Coord(w1, msz.y));
					g.chcolor(m.c);
					g.frect(new Coord(off.x+(w*25)/100, off.y), new Coord(w2, msz.y));
					g.chcolor(m.c.brighter());
					g.frect(new Coord(off.x+(w*50)/100, off.y), new Coord(w3, msz.y));
				} else {
					w = (int) Math.ceil(w * m.a);
					g.chcolor(m.c);
					g.frect(off, new Coord(w, msz.y));
				}
			}
		}
		if (tipTex != null) {
			g.chcolor();
			g.image(tipTex, sz.div(2).sub(tipTex.sz().div(2)).add(10, -1));
		}
		g.chcolor();
		g.image(bg, Coord.z, sz);
	} catch (Loading l) {
	}
}
	private enum MeterType {
		HEALTH, ENERGY, STAMINA, HORSE, UNKNOWN
	}
	public String getmeter() {
		KeyboundTip tt = (KeyboundTip) tooltip;
		return (tt.base);
	}
	private void determineType(Resource res) {
		if (type == null) {
			switch (res.basename()) {
				case "hp":
					type = MeterType.HEALTH;
					break;
				case "stam":
					type = MeterType.STAMINA;
					break;
				case "nrj":
					type = MeterType.ENERGY;
					break;
				case "häst":
					type = MeterType.HORSE;
					break;
				default:
					type = MeterType.UNKNOWN;
			}
		}
	}
	public static double characterSoftHealthPercent;
	public static String characterCurrentHealth;
	public void uimsg(String msg, Object... args) {
		if(msg == "set") {
			this.meters = decmeters(args, 0);
			if (!ponyAlarmPlayed) {
				try {
					Resource res = bg.get();
					if (res != null && res.name.equals("gfx/hud/meter/häst")) {
						if (OptWnd.ponyPowerSoundEnabledCheckbox.a && meters.get(0).a <= 0.10) {
						try {
							File file = new File("Alarms/" + OptWnd.ponyPowerSoundFilename.buf.line() + ".wav");
							if (file.exists()) {
								AudioInputStream in = AudioSystem.getAudioInputStream(file);
								AudioFormat tgtFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
								AudioInputStream pcmStream = AudioSystem.getAudioInputStream(tgtFormat, in);
								Audio.CS klippi = new Audio.PCMClip(pcmStream, 2, 2);
								((Audio.Mixer) Audio.player.stream).add(new Audio.VolAdjust(klippi, OptWnd.ponyPowerSoundVolumeSlider.val / 50.0));
							}
						} catch (Exception ignored) {
						}
						ponyAlarmPlayed = true;
						}
					}
				} catch (Loading e) {
				}
			}
			if (!energyAlarmTriggered) {
				try {
					Resource res = bg.get();
					if (res != null && res.name.equals("gfx/hud/meter/nrj")) {
						if (OptWnd.lowEnergySoundEnabledCheckbox.a && meters.get(0).a < 0.25 && meters.get(0).a > 0.20) {
							try {
								File file = new File("Alarms/" + OptWnd.lowEnergySoundFilename.buf.line() + ".wav");
								if (file.exists()) {
									AudioInputStream in = AudioSystem.getAudioInputStream(file);
									AudioFormat tgtFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
									AudioInputStream pcmStream = AudioSystem.getAudioInputStream(tgtFormat, in);
									Audio.CS klippi = new Audio.PCMClip(pcmStream, 2, 2);
									((Audio.Mixer) Audio.player.stream).add(new Audio.VolAdjust(klippi, OptWnd.lowEnergySoundVolumeSlider.val / 50.0));
								}
							} catch (Exception ignored) {
							}
							energyAlarmTriggered = true;
						}
					}
				} catch (Loading e) {
				}
			}
			if (!dangerEnergyAlarmTriggered) {
				try {
					Resource res = bg.get();
					if (res != null && res.name.equals("gfx/hud/meter/nrj")) {
						if (OptWnd.lowEnergySoundEnabledCheckbox.a && meters.get(0).a <= 0.20) {
							try {
								File file = new File("Alarms/" + OptWnd.lowEnergySoundFilename.buf.line() + ".wav");
								if (file.exists()) {
									AudioInputStream in = AudioSystem.getAudioInputStream(file);
									AudioFormat tgtFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
									AudioInputStream pcmStream = AudioSystem.getAudioInputStream(tgtFormat, in);
									Audio.CS klippi = new Audio.PCMClip(pcmStream, 2, 2);
									((Audio.Mixer) Audio.player.stream).add(new Audio.VolAdjust(klippi, OptWnd.lowEnergySoundVolumeSlider.val / 50.0));
								}
							} catch (Exception ignored) {
							}
							dangerEnergyAlarmTriggered = true;
						}
					}
				} catch (Loading e) {
				}
			}
			if (energyAlarmTriggered || dangerEnergyAlarmTriggered) { // ND: If at least one of the alarms has been triggered, check if we refilled the energy bar above 2500, to reset them in case we once again fall below it.
				try {
					Resource res = bg.get();
					if (res != null && res.name.equals("gfx/hud/meter/nrj")) {
						if (meters.get(0).a > 0.25) {
							energyAlarmTriggered = false;
							dangerEnergyAlarmTriggered = false;
						}
					}
				} catch (Loading e) {
				}
			}
		}  else {
			if (msg == "tip") {
				String value = ((String)args[0]).split(":")[1].replaceAll("(\\(.+\\))", "");
				if (value.contains("/")) { // ND: this part removes the HHP, so I only show the SHP and MHP
					String[] hps = value.split("/");
					String SHP = hps[0].trim();
					if (Double.parseDouble(SHP) > 0 && hps.length == 3){
						spar = false;
						String MHP = hps[2].trim();
						characterSoftHealthPercent = (Double.parseDouble(SHP)/((Double.parseDouble(MHP)/100)));
					} else if (hps.length == 4) {
						spar = true;
						String SHPS = hps[0].trim();
						String MHPS = hps[3].trim();
						characterSoftHealthPercent = (Double.parseDouble(SHPS)/((Double.parseDouble(MHPS)/100)));
						sparshp = 100;
					} else {
						spar = false;
						characterSoftHealthPercent = 0;
					}
					if (hps.length == 3) {
						spar = false;
						value = hps[0] + " / " + hps[hps.length - 1]; // ND: hps[0] is SHP, hps[1] is SHP, hps[2] (or hps[hps.length - 1]) is MHP
						characterCurrentHealth = value;
					} else {
						spar = true;
						value = hps[0] + " / " + hps[3]; // ND: hps[0] is SHP, hps[1] is SHP, hps[2] (or hps[hps.length - 1]) is MHP
						characterCurrentHealth = value;
					}
				}
				tipTex = Text.renderstroked2(value.trim(), Color.WHITE, Color.BLACK, tipF).tex();
			}
			super.uimsg(msg, args);
		}
	}

}

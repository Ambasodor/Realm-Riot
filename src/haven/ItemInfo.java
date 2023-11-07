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

import haven.res.ui.tt.attrmod.AttrMod;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.lang.reflect.*;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class ItemInfo {
	public static final Resource armor_hard = Resource.local().loadwait("gfx/hud/chr/custom/ahard");
	public static final Resource armor_soft = Resource.local().loadwait("gfx/hud/chr/custom/asoft");
	static final Pattern count_pattern = Pattern.compile("(?:^|[\\s])([0-9]*\\.?[0-9]+\\s*%?)");
    public final Owner owner;

    public interface Owner extends OwnerContext {
	public List<ItemInfo> info();
    }

    public interface ResOwner extends Owner {
	public Resource resource();
    }

    public interface SpriteOwner extends ResOwner {
	public GSprite sprite();
    }

    public static class Raw {
	public final Object[] data;
	public final double time;

	public Raw(Object[] data, double time) {
	    this.data = data;
	    this.time = time;
	}

	public Raw(Object[] data) {
	    this(data, Utils.rtime());
	}
    }

    @Resource.PublishedCode(name = "tt", instancer = FactMaker.class)
    public static interface InfoFactory {
	public ItemInfo build(Owner owner, Raw raw, Object... args);
    }

    public static class FactMaker extends Resource.PublishedCode.Instancer.Chain<InfoFactory> {
	public FactMaker() {super(InfoFactory.class);}
	{
	    add(new Direct<>(InfoFactory.class));
	    add(new StaticCall<>(InfoFactory.class, "mkinfo", ItemInfo.class, new Class<?>[] {Owner.class, Object[].class},
				 (make) -> new InfoFactory() {
					 public ItemInfo build(Owner owner, Raw raw, Object... args) {
					     return(make.apply(new Object[]{owner, args}));
					 }
				     }));
	    add(new StaticCall<>(InfoFactory.class, "mkinfo", ItemInfo.class, new Class<?>[] {Owner.class, Raw.class, Object[].class},
				 (make) -> new InfoFactory() {
					 public ItemInfo build(Owner owner, Raw raw, Object... args) {
					     return(make.apply(new Object[]{owner, raw, args}));
					 }
				     }));
	    add(new Construct<>(InfoFactory.class, ItemInfo.class, new Class<?>[] {Owner.class, Object[].class},
				(cons) -> new InfoFactory() {
					public ItemInfo build(Owner owner, Raw raw, Object... args) {
					    return(cons.apply(new Object[] {owner, args}));
					}
				    }));
	    add(new Construct<>(InfoFactory.class, ItemInfo.class, new Class<?>[] {Owner.class, Raw.class, Object[].class},
				(cons) -> new InfoFactory() {
					public ItemInfo build(Owner owner, Raw raw, Object... args) {
					    return(cons.apply(new Object[] {owner, raw, args}));
					}
				    }));
	}
    }

    public ItemInfo(Owner owner) {
	this.owner = owner;
    }

    public static class Layout {
	public final List<Tip> tips = new ArrayList<Tip>();
	private final Map<ID, Tip> itab = new HashMap<ID, Tip>();
	public final CompImage cmp = new CompImage();
	public int width = 0;

	public interface ID<T extends Tip> {
	    public T make();
	}

	@SuppressWarnings("unchecked")
	public <T extends Tip> T intern(ID<T> id) {
	    T ret = (T)itab.get(id);
	    if(ret == null) {
		itab.put(id, ret = id.make());
		add(ret);
	    }
	    return(ret);
	}

	public void add(Tip tip) {
	    tips.add(tip);
	    tip.prepare(this);
	}

	public BufferedImage render() {
	    Collections.sort(tips, new Comparator<Tip>() {
		    public int compare(Tip a, Tip b) {
			return(a.order() - b.order());
		    }
		});
	    for(Tip tip : tips)
		tip.layout(this);
	    return(cmp.compose());
	}
    }

    public static abstract class Tip extends ItemInfo {
	public Tip(Owner owner) {
	    super(owner);
	}

	public BufferedImage tipimg() {return(null);}
	public BufferedImage tipimg(int w) {return(tipimg());}
	public Tip shortvar() {return(null);}
	public void prepare(Layout l) {}
	public void layout(Layout l) {
	    BufferedImage t = tipimg(l.width);
	    if(t != null)
		l.cmp.add(t, new Coord(0, l.cmp.sz.y));
	}
	public int order() {return(100);}
    }

    public static class AdHoc extends Tip {
	public final Text str;

	public AdHoc(Owner owner, String str) {
	    super(owner);
	    this.str = Text.render(str);
	}

	public BufferedImage tipimg() {
	    return(str.img);
	}
    }

    public static class Name extends Tip {
	public final Text str;
	public final String original;

	public Name(Owner owner, Text str, String orig) {
		super(owner);
		original = orig;
		this.str = str;
	}

	public Name(Owner owner, Text str) {
		this(owner, str, str.text);
	}

	public Name(Owner owner, String str) {
	    this(owner, Text.render(str));
	}

	public BufferedImage tipimg() {
	    return(PUtils.strokeImg(str.img));
	}

	public int order() {return(0);}

	public Tip shortvar() {
	    return(new Tip(owner) {
		    public BufferedImage tipimg() {return(str.img);}
		    public int order() {return(0);}
		});
	}

	public static interface Dynamic {
	    public String name();
	}

	public static class Default implements InfoFactory {
	    public static String get(Owner owner) {
		if(owner instanceof SpriteOwner) {
		    GSprite spr = ((SpriteOwner)owner).sprite();
		    if(spr instanceof Dynamic)
			return(((Dynamic)spr).name());
		}
		if(!(owner instanceof ResOwner))
		    return(null);
		Resource res = ((ResOwner)owner).resource();
		Resource.Tooltip tt = res.layer(Resource.tooltip);
		if(tt == null)
		    throw(new RuntimeException("Item resource " + res + " is missing default tooltip"));
		return(tt.t);
	    }

	    public ItemInfo build(Owner owner, Raw raw, Object... args) {
		String nm = get(owner);
		return((nm == null) ? null : new Name(owner, nm));
	    }
	}
    }

    public static class Pagina extends Tip {
	public final String str;

	public Pagina(Owner owner, String str) {
	    super(owner);
	    this.str = str;
	}

	public BufferedImage tipimg(int w) {
	    return(RichText.render(str, w).img);
	}

	public void layout(Layout l) {
	    BufferedImage t = tipimg((l.width == 0) ? UI.scale(200) : l.width);
	    if(t != null)
		l.cmp.add(t, new Coord(0, l.cmp.sz.y + UI.scale(10)));
	}

	public int order() {return(10000);}
    }

    public static class Contents extends Tip {
	private static final Pattern PARSE = Pattern.compile("([\\d.]*) ([\\w]+) of ([\\w\\s]+)\\.?");
	public final List<ItemInfo> sub;
	private static final Text.Line ch = Text.render("Contents:");
	public Content content;
	public Contents(Owner owner, List<ItemInfo> sub) {
	    super(owner);
	    this.sub = sub;
		this.content = content();
	}
	
	public BufferedImage tipimg() {
	    BufferedImage stip = longtip(sub);
	    BufferedImage img = TexI.mkbuf(Coord.of(stip.getWidth(), stip.getHeight()).add(UI.scale(10, 15)));
	    Graphics g = img.getGraphics();
	    g.drawImage(ch.img, 0, 0, null);
	    g.drawImage(stip, UI.scale(10), UI.scale(15), null);
	    g.dispose();
	    return(img);
	}

	public Tip shortvar() {
	    return(new Tip(owner) {
		    public BufferedImage tipimg() {return(shorttip(sub));}
		    public int order() {return(100);}
		});
	}
		public Content content() {
			for (ItemInfo i : sub) {
				if(i instanceof Name) {
					Matcher m = PARSE.matcher(((Name) i).original);
					if(m.find()) {
						float count = 0;
						try {
							count = Float.parseFloat(m.group(1));
						} catch (Exception e) {CrashLogger.logCrash(Arrays.toString(e.getStackTrace()));}
						return new Content(m.group(3), m.group(2), count);
					}
				}
			}
			return Content.EMPTY;
		}

		public static class Content {
			public final String name;
			public final String unit;
			public final float count;

			public Content(String name, String unit, float count) {
				this.name = name;
				this.unit = unit;
				this.count = count;
			}

			public boolean is(String what) {
				if(name == null || what == null) {
					return false;
				}
				return name.contains(what);
			}

			public static final Content EMPTY = new Content(null, null, 0);
		}
		public static BufferedImage longtip(List<ItemInfo> info) { // ND: Added this here to overwrite method from ItemInfo and prevent an extra text stroke on contents tooltip
			Layout l = new Layout();
			for(ItemInfo ii : info) {
				if(ii instanceof Tip) {
					Tip tip = (Tip)ii;
					l.add(tip);
				}
			}
			if(l.tips.size() < 1)
				return(null);
			return(l.render());
		}
    }

    public static BufferedImage catimgs(int margin, BufferedImage... imgs) {
	int w = 0, h = -margin;
	for(BufferedImage img : imgs) {
	    if(img == null)
		continue;
	    if(img.getWidth() > w)
		w = img.getWidth();
	    h += img.getHeight() + margin;
	}
	BufferedImage ret = TexI.mkbuf(new Coord(w, h));
	Graphics g = ret.getGraphics();
	int y = 0;
	for(BufferedImage img : imgs) {
	    if(img == null)
		continue;
	    g.drawImage(img, 0, y, null);
	    y += img.getHeight() + margin;
	}
	g.dispose();
	return(ret);
    }

	public static BufferedImage catimgsh(int margin, BufferedImage... imgs) {
		return catimgsh(margin, 0, null, imgs);
	}

	public static BufferedImage catimgsh(int margin, int pad, Color bg, BufferedImage... imgs) {
		int w = 2 * pad - margin, h = 0;
		for(BufferedImage img : imgs) {
			if(img == null)
				continue;
			if(img.getHeight() > h)
				h = img.getHeight();
			w += img.getWidth() + margin;
		}
		BufferedImage ret = TexI.mkbuf(new Coord(w, h));
		Graphics g = ret.getGraphics();
		if(bg != null) {
			g.setColor(bg);
			g.fillRect(0, 0, w, h);
		}
		int x = pad;
		for(BufferedImage img : imgs) {
			if(img == null)
				continue;
			g.drawImage(img, x, (h - img.getHeight()) / 2, null);
			x += img.getWidth() + margin;
		}
		g.dispose();
		return(ret);
	}

    public static BufferedImage longtip(List<ItemInfo> info) {
	Layout l = new Layout();
	for(ItemInfo ii : info) {
	    if(ii instanceof Tip) {
		Tip tip = (Tip)ii;
		l.add(tip);
	    }
	}
	if(l.tips.size() < 1)
	    return(null);
	return(PUtils.strokeImg(l.render()));
    }

    public static BufferedImage shorttip(List<ItemInfo> info) {
	Layout l = new Layout();
	for(ItemInfo ii : info) {
	    if(ii instanceof Tip) {
		Tip tip = ((Tip)ii).shortvar();
		if(tip != null)
		    l.add(tip);
	    }
	}
	if(l.tips.size() < 1)
	    return(null);
	return(l.render());
    }

    public static <T> T find(Class<T> cl, List<ItemInfo> il) {
	for(ItemInfo inf : il) {
	    if(cl.isInstance(inf))
		return(cl.cast(inf));
	}
	return(null);
    }

    public static List<ItemInfo> buildinfo(Owner owner, Raw raw) {
	List<ItemInfo> ret = new ArrayList<ItemInfo>();
	Resource.Resolver rr = owner.context(Resource.Resolver.class);
	for(Object o : raw.data) {
	    if(o instanceof Object[]) {
		Object[] a = (Object[])o;
		ItemInfo inf;
		if(a[0] instanceof InfoFactory) {
		    inf = ((InfoFactory)a[0]).build(owner, raw, a);
		} else {
		    Resource ttres;
		    if(a[0] instanceof Integer) {
			ttres = rr.getres((Integer)a[0]).get();
		    } else if(a[0] instanceof Resource) {
			ttres = (Resource)a[0];
		    } else if(a[0] instanceof Indir) {
			ttres = (Resource)((Indir)a[0]).get();
		    } else {
			throw(new ClassCastException("Unexpected info specification " + a[0].getClass()));
		    }
		    InfoFactory f = ttres.getcode(InfoFactory.class, true);
		    inf = f.build(owner, raw, a);
		}
		if(inf != null)
		    ret.add(inf);
	    } else if(o instanceof String) {
		ret.add(new AdHoc(owner, (String)o));
	    } else {
		throw(new ClassCastException("Unexpected object type " + o.getClass() + " in item info array."));
	    }
	}
	return(ret);
    }

	@SuppressWarnings("unchecked")
	public static Map<Resource, Integer> getBonuses(List<ItemInfo> infos) {
		List<ItemInfo> slotInfos = ItemInfo.findall("ISlots", infos);
		List<ItemInfo> gilding = ItemInfo.findall("Slotted", infos);
		Map<Resource, Integer> bonuses = new HashMap<>();
		try {
			for (ItemInfo islots : slotInfos) {
				List<Object> slots = (List<Object>) Reflect.getFieldValue(islots, "s");
				for (Object slot : slots) {
					parseAttrMods(bonuses, (List) Reflect.getFieldValue(slot, "info"));
				}
			}
			for (ItemInfo info : gilding) {
				List<Object> slots = (List<Object>) Reflect.getFieldValue(info, "sub");
				parseAttrMods(bonuses, slots);
			}
			parseAttrMods(bonuses, ItemInfo.findall(AttrMod.class, infos));
		} catch (Exception e) {
			CrashLogger.logCrash(Arrays.toString(e.getStackTrace()));
		}
		Pair<Integer, Integer> wear = ItemInfo.getArmor(infos);
		if (wear != null) {
			bonuses.put(armor_hard, wear.a);
			bonuses.put(armor_soft, wear.b);
		}
		return bonuses;
	}
	public static Pair<Integer, Integer> getWear(List<ItemInfo> infos) {
		infos = findall("haven.res.ui.tt.wear.Wear", infos);
		for (ItemInfo info : infos) {
			if (Reflect.hasField(info, "m") && Reflect.hasField(info, "d")) {
				return new Pair<>(Reflect.getFieldValueInt(info, "d"), Reflect.getFieldValueInt(info, "m"));
			}
		}
		return null;
	}

	public static Pair<Integer, Integer> getArmor(List<ItemInfo> infos) {
		//loftar is wonderful sunshine and has same class name for wear and armor tooltips even though
		//they are different classes with different fields :)
		infos = findall("Wear", infos);
		for (ItemInfo info : infos) {
			if (Reflect.hasField(info, "hard") && Reflect.hasField(info, "soft")) {
				return new Pair<>(Reflect.getFieldValueInt(info, "hard"), Reflect.getFieldValueInt(info, "soft"));
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void parseAttrMods(Map<Resource, Integer> bonuses, List infos) {
		for (Object inf : infos) {
			List<Object> mods = (List<Object>) Reflect.getFieldValue(inf, "mods");
			if (mods != null) {
				for (Object mod : mods) {
					Resource attr = (Resource) Reflect.getFieldValue(mod, "attr");
					int value = Reflect.getFieldValueInt(mod, "mod");
					if (bonuses.containsKey(attr)) {
						bonuses.put(attr, bonuses.get(attr) + value);
					} else {
						bonuses.put(attr, value);
					}
				}
			}
		}
	}

	public static <T> List<T> findall(Class<T> cl, List<ItemInfo> il) {
		List<T> ret = new LinkedList<>();
		for (ItemInfo inf : il) {
			if (cl.isInstance(inf))
				ret.add(cl.cast(inf));
		}
		return ret;
	}

	public static List<ItemInfo> findall(String cl, List<ItemInfo> infos) {
		return infos.stream()
				.filter(inf -> Reflect.is(inf, cl))
				.collect(Collectors.toCollection(LinkedList::new));
	}

    public static List<ItemInfo> buildinfo(Owner owner, Object[] rawinfo) {
	return(buildinfo(owner, new Raw(rawinfo)));
    }

	public static String getCount(List<ItemInfo> infos) {
		String res = null;
		for (ItemInfo info : infos) {
			if(info instanceof Contents) {
				Contents cnt = (Contents) info;
				res = getCount(cnt.sub);
			} else if(info instanceof AdHoc) {
				AdHoc ah = (AdHoc) info;
				try {
					Matcher m = count_pattern.matcher(ah.str.text);
					if(m.find()) {
						res = m.group(1);
					}
				} catch (Exception e) {
					CrashLogger.logCrash(Arrays.toString(e.getStackTrace()));
				}
			} else if(info instanceof Name) {
				Name name = (Name) info;
				if (!name.str.text.contains("seed")){
					try {
						Matcher m = count_pattern.matcher(name.original);
						if(m.find()) {
							res = m.group(1);
						}
					} catch (Exception e) {
						CrashLogger.logCrash(Arrays.toString(e.getStackTrace()));
					}
				}
			}
			if(res != null) {
				return res.trim();
			}
		}
		return null;
	}
    
    private static String dump(Object arg) {
	if(arg instanceof Object[]) {
	    StringBuilder buf = new StringBuilder();
	    buf.append("[");
	    boolean f = true;
	    for(Object a : (Object[])arg) {
		if(!f)
		    buf.append(", ");
		buf.append(dump(a));
		f = false;
	    }
	    buf.append("]");
	    return(buf.toString());
	} else {
	    return(arg.toString());
	}
    }

    public static class AttrCache<R> implements Indir<R> {
	private final Supplier<List<ItemInfo>> from;
	private final Function<List<ItemInfo>, Supplier<R>> data;
	private List<ItemInfo> forinfo = null;
	private Supplier<R> save;

	public AttrCache(Supplier<List<ItemInfo>> from, Function<List<ItemInfo>, Supplier<R>> data) {
	    this.from = from;
	    this.data = data;
	}

	public R get() {
	    try {
		List<ItemInfo> info = from.get();
		if(info != forinfo) {
		    save = data.apply(info);
		    forinfo = info;
		}
		return(save.get());
	    } catch(Loading e) {
			CrashLogger.logCrash(Arrays.toString(e.getStackTrace()));
		return(null);
	    }
	}

	public static <I, R> Function<List<ItemInfo>, Supplier<R>> map1(Class<I> icl, Function<I, Supplier<R>> data) {
	    return(info -> {
		    I inf = find(icl, info);
		    if(inf == null)
			return(() -> null);
		    return(data.apply(inf));
		});
	}

	public static <I, R> Function<List<ItemInfo>, Supplier<R>> map1s(Class<I> icl, Function<I, R> data) {
	    return(info -> {
		    I inf = find(icl, info);
		    if(inf == null)
			return(() -> null);
		    R ret = data.apply(inf);
		    return(() -> ret);
		});
	}
		public static <R> Function<List<ItemInfo>, Supplier<R>> cache(Function<List<ItemInfo>, R> data) {
			return (info -> {
				R result = data.apply(info);
				return (() -> result);
			});
		}
    }

    public static interface InfoTip {
	public List<ItemInfo> info();
    }
}

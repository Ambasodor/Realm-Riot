/* Preprocessed source code */
import haven.*;
import haven.render.*;
import java.nio.*;
import static java.lang.Math.*;

/* >spr: DowseFx */
@haven.FromResource(name = "gfx/fx/dowse", version = 12)
public class DowseFx extends Sprite {
    static final VertexArray.Layout fmt =
	new VertexArray.Layout(new VertexArray.Layout.Input(Homo3D.vertex, new VectorFormat(3, NumberFormat.FLOAT32), 0, 0, 16),
			       new VertexArray.Layout.Input(VertexColor.color, new VectorFormat(4, NumberFormat.UNORM8), 0, 12, 16));
    public static final double ln = 2, r = 100;
    public final double a1, a2;
    private double a = 0;
    private final Model d1, d2;

    public DowseFx(Owner owner, Resource res, Message sdt) {
	super(owner, res);
	if(sdt.eom()) {
	    a1 = -PI / 8;
	    a2 = PI / 8;
	} else {
	    double a2 = -(sdt.uint8() / 256.0) * PI * 2;
	    double a1 = -(sdt.uint8() / 256.0) * PI * 2;
	    while(a2 < a1)
		a2 += PI * 2;
	    this.a1 = a1;
	    this.a2 = a2;
	}
	d1 = new Model(Model.Mode.TRIANGLE_FAN, new VertexArray(fmt, new VertexArray.Buffer(v1(), DataBuffer.Usage.STREAM)), null);
	d2 = new Model(Model.Mode.TRIANGLE_FAN, new VertexArray(fmt, new VertexArray.Buffer(v2(), DataBuffer.Usage.STREAM)), null);
    }

    private ByteBuffer v1() {
	ByteBuffer buf = ByteBuffer.allocate(128);
	buf.order(ByteOrder.nativeOrder());
	double r = this.r * (a / 0.75) * 3;
	byte alpha = Utils.f2u8(0.3f * (1 - (float)Utils.clip((a - 0.25) / 0.5, 0, 1)));
	buf.putFloat(0).putFloat(0).putFloat(0);
	buf.put((byte)255).put((byte)0).put((byte)0).put(alpha);
	for(double ca = 0; ca < (PI * 2) + (PI * 0x0.02p0); ca += PI * 0x0.04p0) {
	    buf = Utils.growbuf(buf, 16);
	    buf.putFloat((float)(cos(ca) * r)).putFloat((float)(sin(ca) * r)).putFloat(15);
	    buf.put((byte)255).put((byte)0).put((byte)0).put(alpha);
	}
	buf.flip();
	return(buf);
    }

    private ByteBuffer v2() {
	ByteBuffer buf = ByteBuffer.allocate(128);
	buf.order(ByteOrder.nativeOrder());
	byte alpha;
	if(a > 0.25)
	    alpha = Utils.f2u8(0.3f * (1 - (float)Utils.clip((a - 0.75) / 0.25, 0, 1)));
	else
	    alpha = 0;
	buf.putFloat(0).putFloat(0).putFloat(0);
	buf.put((byte)255).put((byte)0).put((byte)0).put(alpha);
	for(double ca = a1; ca < a2; ca += PI * 0x0.04p0) {
	    buf = Utils.growbuf(buf, 16);
	    buf.putFloat((float)(cos(ca) * r)).putFloat((float)(sin(ca) * r)).putFloat(15);
	    buf.put((byte)255).put((byte)0).put((byte)0).put(alpha);
	}
	buf = Utils.growbuf(buf, 16);
	buf.putFloat((float)(cos(a2) * r)).putFloat((float)(sin(a2) * r)).putFloat(15);
	buf.put((byte)255).put((byte)0).put((byte)0).put(alpha);
	buf.flip();
	return(buf);
    }

    public void added(RenderTree.Slot slot) {
	slot.ostate(Pipe.Op.compose(VertexColor.instance, States.maskdepth, Location.goback("gobx"),
				    /* Rendered.eyesort XXXRENDER */ Rendered.postpfx));
	slot.add(d1);
	slot.add(d2);
    }

    public void gtick(Render g) {
	g.update(d1.va.bufs[0], DataBuffer.Filler.of(v1()));
	g.update(d2.va.bufs[0], DataBuffer.Filler.of(v2()));
    }

    public boolean tick(double dt) {
	a += dt / ln;
	return(a >= 1);
    }
}

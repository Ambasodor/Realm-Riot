package haven.botengine.entities;

public class Health {

    private final int soft;
    private final int hard;
    private final int max;

    public Health(int soft, int hard, int max) {
        this.soft = soft;
        this.hard = hard;
        this.max = max;
    }

    public int getSoft() {
        return soft;
    }

    public int getHard() {
        return hard;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return soft + "/" + hard + "/" + max;
    }

}

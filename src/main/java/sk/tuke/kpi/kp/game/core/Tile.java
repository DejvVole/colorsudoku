package sk.tuke.kpi.kp.game.core;

public abstract class Tile {
    private TileStatus state;
    private int value;

    public Tile(int value, TileStatus state) {
        this.value = value;
        this.state = state;
    }

    public TileStatus getState() {
        return state;
    }

    public void setState(TileStatus state) {
        this.state = state;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

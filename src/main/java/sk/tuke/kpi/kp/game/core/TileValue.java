package sk.tuke.kpi.kp.game.core;

public enum TileValue {
    RED(1), YELLOW(2), GREEN(3), CYAN(4), BLUE(5), ORANGE(6), PURPLE(7), WHITE(8), BLACK(9),
    ;

    private final int value;
    TileValue(int value) {
        this.value = value;
    }

    /*
    * for prechadza farbami
    * */
    public static TileValue getColor(int value){
        for(TileValue value1 : TileValue.values()){
            if(value1.value == value) return value1;
        }
        throw new IllegalArgumentException("Color not found");
    }
}

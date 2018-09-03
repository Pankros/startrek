package ar.com.meli.startrek.entity;

public enum DirectionEnum {
    
    CLOCKWISE(1),
    COUNTER_CLOCKWISE(-1);
    
    private DirectionEnum(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

    private final int value;
}

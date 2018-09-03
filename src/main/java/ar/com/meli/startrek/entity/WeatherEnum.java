package ar.com.meli.startrek.entity;

public enum WeatherEnum {
    
    DROUDHT("DROUDHT"), 
    RAIN("RAIN"), 
    NORMAL("NORMAL"), 
    OPTIMUM("OPTIMUM");
    
    private final String value;
    
    private WeatherEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

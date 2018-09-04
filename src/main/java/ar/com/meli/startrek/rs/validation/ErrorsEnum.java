package ar.com.meli.startrek.rs.validation;

public enum ErrorsEnum {

    NEGATIVE_DAYS("You must provide a positive day."),
    NEGATIVE_PERIOD("You must provide a positive period."),
    NO_DAY("You must provide a day"),
    NEGATIVE_YEARS("You must provide a positive year."),
    NO_YEAR("You must provide a year"),
    NO_WEATHER("You must provide a valid weather value: [DROUDHT, RAIN, NORMAL, OPTIMUM or ALL]");
    
    private ErrorsEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

    private final String value;
}

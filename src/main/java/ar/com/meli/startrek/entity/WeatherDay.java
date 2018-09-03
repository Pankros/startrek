package ar.com.meli.startrek.entity;

public class WeatherDay {
    
    private Long day;
    
    private WeatherEnum weather;

    public WeatherDay() {}
    
    public WeatherDay(Long day, WeatherEnum weather) {
        this.day = day;
        this.weather = weather;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public WeatherEnum getWeather() {
        return weather;
    }

    public void setWeather(WeatherEnum weather) {
        this.weather = weather;
    }
    
    @Override
    public String toString() {
        return String.format("[day:%d, weather:%s]", day, weather);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        WeatherDay other = (WeatherDay) o;
        if (!other.day.equals(this.day))
            return false;
        if(!other.weather.equals(this.weather))
            return false;
        return true;
    }

}

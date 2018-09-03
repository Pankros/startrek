package ar.com.meli.startrek.dto;

import ar.com.meli.startrek.entity.WeatherEnum;

public class WeatherDayDTO {
    
    private Long day;
    
    private WeatherEnum weather;

    public WeatherDayDTO() {}
    
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
    
}

package ar.com.meli.startrek.dto;

import ar.com.meli.startrek.entity.WeatherEnum;

public class WeatherSeasonDTO {

    private WeatherEnum weather;
    
    private Long dayBegin;
    
    private Long dayEnd;

    public WeatherEnum getWeather() {
        return weather;
    }

    public void setWeather(WeatherEnum weather) {
        this.weather = weather;
    }

    public Long getDayBegin() {
        return dayBegin;
    }

    public void setDayBegin(Long dayBegin) {
        this.dayBegin = dayBegin;
    }

    public Long getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(Long dayEnd) {
        this.dayEnd = dayEnd;
    }
    
}

package ar.com.meli.startrek.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WeatherSeason {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private WeatherEnum weather;
    
    private Long dayBegin;
    
    private Long dayEnd;
    
    private Long maxPerimeterDay;

    
    
    public WeatherSeason(Long id, WeatherEnum weather, Long dayBegin, Long dayEnd, Long maxPerimeterDay) {
        this.id = id;
        this.weather = weather;
        this.dayBegin = dayBegin;
        this.dayEnd = dayEnd;
        this.maxPerimeterDay = maxPerimeterDay;
    }

    public WeatherSeason() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getMaxPerimeterDay() {
        return maxPerimeterDay;
    }

    public void setMaxPerimeterDay(Long maxPerimeterDay) {
        this.maxPerimeterDay = maxPerimeterDay;
    }
}
